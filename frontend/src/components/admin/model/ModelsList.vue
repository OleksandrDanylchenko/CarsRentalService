<template>
    <div>
        <b-row>
            <b-col>
                <b-button pill variant="outline-danger" @click="openModelModal(-1)">
                    <i class="fas fa-plus-circle"></i>&nbsp;Додати нову модель авто
                </b-button>
            </b-col>
        </b-row>
        <div class="mt-4">
            <MessagesErrorsComponent :messages="messages" :errors="errors"
                                     @dismissMessages="dismissMessages" @dismissErrors="dismissErrors"
                                     @addError="addError"/>
            <b-row>
                <b-col>
                    <div>
                        <h1 class="mb-3 display-4">Доступні моделі авто:</h1>
                        <b-card-group columns>
                            <div v-for="model in models" :key="model.id">
                                <div v-if="model.imageName != null">
                                    <b-card :img-src="require('@/assets/modelsPhotos/' + model.imageName)"
                                            :img-alt="model.model + ' photo'" img-top
                                            :header="'ID: ' + model.id">
                                        <b-card-title class="text-center mb-0">
                                            <div class="h2">{{ model.brand }} {{ model.model }}</div>
                                            <h3 class="mb-0">{{ model.year }}</h3>
                                            <h5 class="mb-0">{{ model.type }}</h5>
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
                            </div>
                        </b-card-group>
                    </div>
                </b-col>
            </b-row>
        </div>
        <ModelModal :processingId="processingId" @addModel="addModel" @updateModel="updateModel" @addError="addError"/>
        <DeleteModal :processingId="processingId" @deleteRecord="deleteModel"/>
    </div>
</template>

<script>
    import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
    import {MessagesErrorsDismissMixin} from "../../mixins/MessagesErrorsDismissMixin"

    import ModelModal from "./ModelModal"
    import DeleteModal from "../common/DeleteModal"

    import DataService from "../../../service/DataService";

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
                resource: "models",

                messages: [],
                errors: []
            }
        },
        methods: {
            refreshModels() {
                DataService.retrieveAllRecords(this.resource).then(response => {
                    this.models = response.data;
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404) {
                        this.addError(`Таблиця моделей поки не містить записів`);
                    } else {
                        this.addError(`Сталася непередбачувана помилка завантаження таблиці`);
                    }
                });
            },
            openModelModal(id) {
                this.dismissMessages();
                this.dismissErrors();
                this.processingId = id;
                this.$bvModal.show("modelModal");
            },
            addModel(addForm) {
                DataService.addRecord(this.resource, addForm).then(() => {
                    this.addMessage("Нову модель додано успішно");
                    this.refreshModels();
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 409) {
                        this.addError(`Таблиця уже містить модель ${JSON.parse(error.response.config.data).model}`);
                    } else {
                        this.addError(`Нова модель містить інформацію, що суперечить обмеженням`);
                    }
                });
                this.$bvModal.hide("modelModal");
            },
            updateModel(updateForm) {
                DataService.updateRecord(this.resource, updateForm).then(() => {
                    this.addMessage(`Модель №${JSON.parse(updateForm.get('model')).id} змінено успішно`);
                    this.refreshModels();
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 409) {
                        this.addError(`Таблиця уже містить модель ${JSON.parse(error.response.config.data).model}`);
                    } else {
                        this.addError(`Змінювана модель містить інформацію, що суперечить обмеженням`);
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
                DataService.deleteRecord(this.resource, id).then(() => {
                    this.addMessage(`Видалення запису №${id} виконано успішно`);
                    this.refreshModels();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Видалення запису №${id} не виконано!`);
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
    @import "../../../styles/main.css";
</style>