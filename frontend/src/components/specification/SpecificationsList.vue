<template>
    <div>
        <b-row>
            <b-col>
                <b-button pill variant="outline-danger" @click="openSpecificationModal(-1)">
                    <i class="fas fa-plus-circle"></i>&nbsp;Додати нову конфігурацію
                </b-button>
            </b-col>
        </b-row>
        <div class="mt-4">
            <MessagesErrorsComponent :messages="messages" :errors="errors"
                                     @dismissMessages="dismissMessages" @dismissErrors="dismissErrors"/>
            <b-row>
                <b-col>
                    <h1 class="mb-3 display-4">Список конфігурацій авто:</h1>
                    <b-table id="specificationsTable" hover :items="specifications" :fields="fields"
                             :busy.sync="isBusy" primary-key="id">
                        <template v-slot:table-busy>
                            <div class="text-center text-danger mt-2">
                                <b-spinner type="grow" class="align-middle" style="width: 3rem; height: 3rem;"/>
                                <strong>&nbsp;Завантаження таблиці...</strong>
                            </div>
                        </template>
                        <template v-slot:cell(editModal)="data">
                            <b-button pill variant="outline-dark" @click="openSpecificationModal(data.item.id)">
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
        <SpecificationModal :processingId="processingId" @addSpecification="addSpecification"
                            @updateSpecification="updateSpecification" @addError="addError"/>
        <DeleteModal :processingId="processingId" @deleteRecord="deleteSpecification"/>
    </div>
</template>

<script>
    import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
    import {MessagesErrorsDismissMixin} from "../mixins/MessagesErrorsDismissMixin"
    import SpecificationModal from "./SpecificationModal"
    import DeleteModal from "../common/DeleteModal"
    import DataService from "../../service/DataService";

    export default {
        mixins: [MessagesErrorsDismissMixin],
        name: "SpecificationsList",
        components: {
            MessagesErrorsComponent,
            SpecificationModal,
            DeleteModal
        },
        data() {
            return {
                fields: [
                    {key: 'id', label: 'ID', sortable: true, thClass: 'text-danger', tdClass: 'text-danger'},
                    {key: 'engineCapacity', label: 'Об\'єм двигуна (л.)', sortable: true},
                    {key: 'horsepowers', label: 'Кінскі сили', sortable: true},
                    {key: 'fuelType', label: 'Тип пального', sortable: true},
                    {key: 'fuelConsumption', label: 'Споживання пального (л./100км.)', sortable: true},
                    {key: 'transmissionType', label: 'Тип трансмісії', sortable: true},
                    {key: 'editModal', label: 'Змінити', thClass: 'text-center', tdClass: 'text-center'},
                    {key: 'deleteModal', label: 'Видалити', thClass: 'text-center', tdClass: 'text-center'},
                ],
                specifications: [],
                isBusy: true,
                processingId: Number.MIN_VALUE,
                resource: 'specifications',

                messages: [],
                errors: []
            }
        },
        methods: {
            refreshSpecifications() {
                this.isBusy = true;
                DataService.retrieveAllRecords(this.resource).then(response => {
                    this.specifications = response.data;
                    this.isBusy = false;
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404) {
                        this.addError(`Таблиця конфігурацій не містить записів`);
                    } else {
                        this.addError(`Сталася непередбачувана помилка завантаження таблиці`);
                    }
                });
            },
            openSpecificationModal(id) {
                this.dismissMessages();
                this.dismissErrors();
                this.processingId = id;
                this.$bvModal.show("specificationModal");
            },
            addSpecification(newSpecification) {
                this.isBusy = true;
                DataService.addRecord(this.resource, newSpecification).then(() => {
                    this.addMessage(`Нову конфігурацію додано успішно`);
                    this.refreshSpecifications();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Нова конфігурація містить інформацію, що суперечить обмеженням`);
                });
                this.isBusy = false;
                this.$bvModal.hide("specificationModal");
            },
            updateSpecification(updateSpecification) {
                this.isBusy = true;
                DataService.updateRecord(this.resource, updateSpecification).then(() => {
                    this.addMessage(`Конфігурацію №${updateSpecification.id} змінено успішно`);
                    this.refreshSpecifications();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Змінювана конфігурація містить інформацію, що суперечить обмеженням`);
                });
                this.isBusy = false;
                this.$bvModal.hide("specificationModal");
            },
            openDeleteModal(id) {
                this.processingId = id;
                this.dismissMessages();
                this.dismissErrors();
                this.$bvModal.show("deleteModal");
            },
            deleteSpecification(id) {
                this.isBusy = true;
                DataService.deleteRecord(this.resource, id).then(() => {
                    this.addMessage(`Видалення конфігурації №${id} виконано успішно`);
                    this.refreshSpecifications();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Видалення конфігурації №${id} не виконано!`);
                });
                this.isBusy = false;
                this.$bvModal.hide("deleteModal");
            }
        },
        created() {
            this.refreshSpecifications();
        }
    }
</script>

<style lang="css">
    @import "../../styles/main.css";
</style>