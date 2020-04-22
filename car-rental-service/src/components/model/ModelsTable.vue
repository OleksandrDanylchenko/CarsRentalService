<template>
    <div>
        <b-row>
            <b-col>
                <b-button pill variant="outline-danger" @click="openDataModal(-1)">
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
                    <h1>Список моделей авто:</h1>
                    <b-table id="modelsTable" hover :items="models" :fields="fields"
                             :busy.sync="isBusy" primary-key="id">
                        <template v-slot:table-busy>
                            <div class="text-center text-danger mt-2">
                                <b-spinner class="align-middle"></b-spinner>
                                <strong>&nbsp;Завантаження...</strong>
                            </div>
                        </template>
                        <template v-slot:head(id)="id_header">
                            <strong class="text-danger">{{ id_header.label }}</strong>
                        </template>
                        <template v-slot:cell(id)="data">
                            <strong class="text-danger">{{ data.item.id }}</strong>
                        </template>
                        <template v-slot:cell(editModal)="data">
                            <b-button pill variant="outline-dark" @click="openDataModal(data.item.id)">
                                <i class="fa fa-edit"></i>
                            </b-button>
                        </template>
                        <template v-slot:cell(deleteModal)="data">
                            <b-button pill variant="outline-danger" @click="openDeleteModal(data.item.id)">
                                <i class="fa fa-trash"></i>
                            </b-button>
                        </template>
                    </b-table>
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
                fields: [
                    {key: 'id', label: 'ID', sortable: true},
                    {key: 'brand', label: 'Бренд', sortable: true},
                    {key: 'model', label: 'Модель', sortable: true},
                    {key: 'year', label: 'Рік', sortable: true},
                    {key: 'editModal', label: 'Змінити', thClass: 'text-center', tdClass: 'text-center'},
                    {key: 'deleteModal', label: 'Видалити', thClass: 'text-center', tdClass: 'text-center'},
                ],
                models: [],
                isBusy: true,
                processingId: Number.MIN_VALUE,

                messages: [],
                errors: []
            }
        },
        methods: {
            refreshModels() {
                this.isBusy = true;
                ModelDataService.retrieveAllModels().then(response => {
                    this.models = response.data;
                    this.isBusy = false;
                })
            },
            openDataModal(id) {
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
                    console.log(error);
                    this.errors.push(`Нову модель не було додано!`);
                });
                this.$bvModal.hide("modelModal");
            },
            updateModel(updateModel) {
                ModelDataService.updateModel(updateModel).then(() => {
                    this.messages.push(`Модель №${updateModel.id} змінено успішно`);
                    this.refreshModels();
                }).catch(error => {
                    console.log(error);
                    this.errors.push(`Модель №${updateModel.id} не було змінено!`);
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