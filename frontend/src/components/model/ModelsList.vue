<template>
    <div>
        <b-row>
            <b-col>
                <b-button pill variant="outline-danger" @click="openModelModal(-1)">
                    <i class="fas fa-plus-circle"></i>&nbsp;Додати нову модель авто
                </b-button>
            </b-col>
            <b-col>
                <!--                TODO Search-->
            </b-col>
        </b-row>
        <div class="mt-4">
            <MessagesErrorsComponent :messages="messages" :errors="errors"
                                     @dismissMessages="dismissMessages" @dismissErrors="dismissErrors"/>
            <b-row>
                <b-col>
                    <h1 v-if="models.length < 1">
                        У системі поки не представлено доступних моделей
                    </h1>
                    <div v-else>
                        <h1>Доступні моделі авто:</h1>
                        <b-card-group columns>
                            <div v-for="model in models" :key="model.id">
                                <b-card :img-src="require('@/assets/modelsPhoto/' + model.imageName)"
                                        :img-alt="model.model + ' photo'"
                                        img-top>
                                    <b-card-title class="text-center mb-0">
                                        <div class="h1">{{ model.brand }} {{ model.model }}</div>
                                        <h3 class="mb-0">{{ model.year }}</h3>
                                    </b-card-title>
                                    <template v-slot:footer>
                                        <div class="d-flex justify-content-center">
                                            <b-button pill variant="outline-dark" @click="openModelModal(model.id)">
                                                <i class="fa fa-edit"></i>
                                            </b-button>
                                            <b-button pill class="ml-3" variant="outline-danger"
                                                      @click="openDeleteModal(model.id)">
                                                <i class="fa fa-trash"></i>
                                            </b-button>
                                        </div>
                                    </template>
                                </b-card>
                            </div>
                        </b-card-group>
                    </div>
                </b-col>
            </b-row>
        </div>
        <ModelModal :processingId="processingId" @addModel="addModel" @updateModel="updateModel"></ModelModal>
        <DeleteModal :processingId="processingId" @deleteRecord="deleteModel"/>
    </div>
</template>

<script>
    import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
    import {MessagesErrorsDismissMixin} from "../mixins/MessagesErrorsDismissMixin"

    import ModelModal from "./ModelModal"
    import DeleteModal from "../common/DeleteModal"

    import ModelDataService from "../../service/ModelDataService";

    export default {
        mixins: [MessagesErrorsDismissMixin],
        name: "ModelsList",
        components: {
            MessagesErrorsComponent,
            ModelModal,
            DeleteModal
        },
        data() {
            return {
                models: [],
                processingId: Number.MIN_VALUE,

                messages: [],
                errors: []
            }
        },
        methods: {
            refreshModels() {
                ModelDataService.retrieveAllModels().then(response => {
                    this.models = response.data;
                }).catch(error => {
                    if (error.response.status === 404) {
                        console.log(`Таблиця моделей не містить записів`);
                    } else {
                        this.errors.push(`Сталася непередбачувана помилка завантаження таблиці`);
                    }
                });
            },
            openModelModal(id) {
                this.dismissMessages();
                this.dismissErrors();
                this.processingId = id;
                this.$bvModal.show("modelModal");
            },
            addModel(newModel) {
                ModelDataService.addModel(newModel).then(() => {
                    this.messages.push(`Нову модель додано успішно`);
                    this.refreshModels();
                }).catch(error => {
                    if (error.response.status === 409) {
                        this.errors.push(`Таблиця уже містить модель ${JSON.parse(error.response.config.data).model}`);
                    } else {
                        this.errors.push(`Нова модель містить інформацію, що суперечить обмеженням`);
                    }
                });
                this.$bvModal.hide("modelModal");
            },
            updateModel(updateModel) {
                ModelDataService.updateModel(updateModel).then(() => {
                    this.messages.push(`Модель №${updateModel.id} змінено успішно`);
                    this.refreshModels();
                }).catch(error => {
                    if (error.response.status === 409) {
                        this.errors.push(`Таблиця уже містить модель ${JSON.parse(error.response.config.data).model}`);
                    } else {
                        this.errors.push(`Змінювана модель містить інформацію, що суперечить обмеженням`);
                    }
                });
                this.$bvModal.hide("modelModal");
            },
            openDeleteModal(id) {
                this.processingId = id;
                this.dismissMessages();
                this.dismissErrors();
                this.$bvModal.show("deleteModal");
            },
            deleteModel(id) {
                ModelDataService.deleteModel(id).then(() => {
                    this.messages.push(`Видалення запису №${id} виконано успішно`);
                    this.refreshModels();
                }).catch(error => {
                    console.log(error);
                    this.errors.push(`Видалення запису №${id} не виконано!`);
                });
                this.$bvModal.hide("deleteModal");
            }
        },
        created() {
            this.refreshModels();
        }
    }
</script>

<style lang="css">
    @import "../../styles/main.css";
</style>