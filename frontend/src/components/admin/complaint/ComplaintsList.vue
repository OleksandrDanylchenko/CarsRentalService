<template>
    <div>
        <b-row>
            <b-col>
                <b-button pill variant="outline-danger" @click="openComplaintModal(-1)">
                    <i class="fas fa-plus-circle"></i>&nbsp;Додати нову скаргу
                </b-button>
            </b-col>
        </b-row>
        <div class="mt-4">
            <MessagesErrorsComponent :messages="messages" :errors="errors"
                                     @dismissMessages="dismissMessages" @dismissErrors="dismissErrors"/>
            <b-row>
                <b-col>
                    <h1 class="mb-3 display-4">Список скарг:</h1>
                    <b-table id="complaintsTable" hover :items="complaints" :fields="fields"
                             :busy.sync="isBusy" primary-key="id">
                        <template v-slot:table-busy>
                            <div class="text-center text-danger mt-2">
                                <b-spinner type="grow" class="align-middle" style="width: 3rem; height: 3rem;"/>
                                <strong>&nbsp;Завантаження таблиці...</strong>
                            </div>
                        </template>
                        <template v-slot:cell(rentId)="data">
                            {{ data.item.rent.id }}
                        </template>
                        <template v-slot:cell(rentClient)="data">
                            {{ data.item.rent.client.firstName + ' ' + data.item.rent.client.secondName }}
                        </template>
                        <template v-slot:cell(rentCar)="data">
                            {{ data.item.rent.car.model.brand + ' ' + data.item.rent.car.model.model }}
                        </template>
                        <template v-slot:cell(rentCost)="data">
                            {{ data.item.rent.totalPrice }}
                        </template>
                        <template v-slot:cell(editModal)="data">
                            <b-button pill variant="outline-dark" @click="openComplaintModal(data.item.id)">
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
        <ComplainModal :processingId="processingId" @addComplaint="addComplaint"
                       @updateComplaint="updateComplaint" @addError="addError"/>
        <DeleteModal :processingId="processingId" @deleteRecord="deleteComplaint"/>
    </div>
</template>

<script>
    import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
    import {MessagesErrorsDismissMixin} from "../../mixins/MessagesErrorsDismissMixin"
    import ComplainModal from "./ComplaintModal";
    import DeleteModal from "../common/DeleteModal"
    import DataService from "../../../service/DataService";

    export default {
        mixins: [MessagesErrorsDismissMixin],
        name: "ComplaintsList",
        components: {
            MessagesErrorsComponent,
            ComplainModal,
            DeleteModal
        },
        data() {
            return {
                fields: [
                    {key: 'id', label: 'ID', sortable: true, thClass: 'text-danger', tdClass: 'text-danger'},
                    {key: 'theme', label: 'Тема', sortable: true},
                    {key: 'text', label: 'Тип трансмісії', sortable: true},
                    {key: 'rentId', label: 'Код оренди', sortable: true},
                    {key: 'rentClient', label: 'Клієнт', sortable: true},
                    {key: 'rentCar', label: 'Авто', sortable: true},
                    {key: 'rentCost', label: 'Загальна ціна', sortable: true},
                    {key: 'editModal', label: 'Змінити', thClass: 'text-center', tdClass: 'text-center'},
                    {key: 'deleteModal', label: 'Видалити', thClass: 'text-center', tdClass: 'text-center'},
                ],
                complaints: [],
                isBusy: true,
                processingId: Number.MIN_VALUE,
                resource: 'complaints',

                messages: [],
                errors: []
            }
        },
        methods: {
            refreshComplaints() {
                this.isBusy = true;
                DataService.retrieveAllRecords(this.resource).then(response => {
                    this.complaints = response.data;
                    this.isBusy = false;
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404) {
                        this.addError(`Таблиця скарг не містить записів`);
                    } else {
                        this.addError(`Сталася непередбачувана помилка завантаження таблиці`);
                    }
                });
            },
            openComplaintModal(id) {
                this.dismissMessages();
                this.dismissErrors();
                this.processingId = id;
                this.$bvModal.show("complaintModal");
            },
            addComplaint(newComplaint) {
                this.isBusy = true;
                DataService.addRecord(this.resource, newComplaint).then(() => {
                    this.addMessage(`Нову скаргу додано успішно`);
                    this.refreshComplaints();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Нова скарга містить інформацію, що суперечить обмеженням`);
                });
                this.isBusy = false;
                this.$bvModal.hide("complaintModal");
            },
            updateComplaint(updateComplaint) {
                this.isBusy = true;
                DataService.updateRecord(this.resource, updateComplaint).then(() => {
                    this.addMessage(`Скаргу №${updateComplaint.id} змінено успішно`);
                    this.refreshComplaints();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Змінювана скарга містить інформацію, що суперечить обмеженням`);
                });
                this.isBusy = false;
                this.$bvModal.hide("complaintModal");
            },
            openDeleteModal(id) {
                this.processingId = id;
                this.dismissMessages();
                this.dismissErrors();
                this.$bvModal.show("deleteModal");
            },
            deleteComplaint(id) {
                this.isBusy = true;
                DataService.deleteRecord(this.resource, id).then(() => {
                    this.addMessage(`Видалення скарги №${id} виконано успішно`);
                    this.refreshComplaints();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Видалення скарги №${id} не виконано!`);
                });
                this.isBusy = false;
                this.$bvModal.hide("deleteModal");
            }
        },
        created() {
            this.refreshComplaints();
        }
    }
</script>

<style lang="css">
    @import "../../../styles/main.css";
</style>