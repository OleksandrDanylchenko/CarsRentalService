<template>
    <div>
        <b-row>
            <b-col>
                <b-button pill variant="outline-danger" @click="openRentModal(-1)">
                    <i class="fas fa-plus-circle"></i>&nbsp;Додати нову оренду
                </b-button>
            </b-col>
        </b-row>
        <div class="mt-4">
            <MessagesErrorsComponent :messages="messages" :errors="errors"
                                     @dismissMessages="dismissMessages" @dismissErrors="dismissErrors"/>
            <b-row>
                <b-col>
                    <h1 class="mb-3 display-4">Список записів про оренду:</h1>
                    <b-table id="rentsTable" hover :items="rents" :fields="fields"
                             :busy.sync="isBusy" primary-key="id">
                        <template v-slot:cell(carId)="data">
                            {{ data.item.car.id }}
                        </template>
                        <template v-slot:cell(carModel)="data">
                            {{ data.item.car.model.brand }} {{ data.item.car.model.model }}
                        </template>
                        <template v-slot:cell(clientId)="data">
                            {{ data.item.client.id }}
                        </template>
                        <template v-slot:cell(clientName)="data">
                            {{ data.item.client.firstName }} {{ data.item.client.secondName }}
                        </template>
                        <template v-slot:table-busy>
                            <div class="text-center text-danger mt-2">
                                <b-spinner type="grow" class="align-middle" style="width: 3rem; height: 3rem;"/>
                                <strong>&nbsp;Завантаження таблиці...</strong>
                            </div>
                        </template>
                        <template v-slot:cell(editModal)="data">
                            <b-button pill variant="outline-dark" @click="openRentModal(data.item.id)">
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
        <RentModal :processingId="processingId" @addRent="addRent"
                   @updateRent="updateRent" @addError="addError"/>
        <DeleteModal :processingId="processingId" @deleteRecord="deleteRent"/>
    </div>
</template>

<script>
    import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
    import {MessagesErrorsDismissMixin} from "../../mixins/MessagesErrorsDismissMixin"
    import RentModal from "./RentModal";
    import DeleteModal from "../common/DeleteModal"
    import DataService from "../../../service/DataService";

    export default {
        mixins: [MessagesErrorsDismissMixin],
        name: "RentsList",
        components: {
            MessagesErrorsComponent,
            RentModal,
            DeleteModal
        },
        data() {
            return {
                fields: [
                    {key: 'id', label: 'ID', sortable: true, thClass: 'text-danger', tdClass: 'text-danger'},
                    {key: 'carId', label: 'Код авто', sortable: true},
                    {key: 'carModel', label: 'Модель авто', sortable: true},
                    {key: 'clientId', label: 'Код клієнта', sortable: true},
                    {key: 'clientName', label: 'Повне ім\'я клієнта', sortable: true},
                    {key: 'rentStart', label: 'Початок оренди', sortable: true},
                    {key: 'rentEnd', label: 'Кінець оренди', sortable: true},
                    {key: 'daysDelta', label: 'К-сть днів оренди', sortable: true},
                    {key: 'totalPrice', label: 'Загальна ціна оренди', sortable: true},
                    {key: 'editModal', label: 'Змінити', thClass: 'text-center', tdClass: 'text-center'},
                    {key: 'deleteModal', label: 'Видалити', thClass: 'text-center', tdClass: 'text-center'},
                ],
                rents: [],
                isBusy: true,
                processingId: Number.MIN_VALUE,
                resource: 'rents',

                messages: [],
                errors: []
            }
        },
        methods: {
            refreshRents() {
                this.isBusy = true;
                DataService.retrieveAllRecords(this.resource).then(response => {
                    console.log(response.data);
                    this.rents = response.data;
                    this.isBusy = false;
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404) {
                        this.addError(`Таблиця оренд не містить записів`);
                    } else {
                        this.addError(`Сталася непередбачувана помилка завантаження таблиці`);
                    }
                });
            },
            openRentModal(id) {
                this.dismissMessages();
                this.dismissErrors();
                this.processingId = id;
                this.$bvModal.show("rentModal");
            },
            addRent(newRent) {
                this.isBusy = true;
                DataService.addRecord(this.resource, newRent).then(() => {
                    this.addMessage(`Нову оренду додано успішно`);
                    this.refreshRents();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Новий запис оренди містить інформацію, що суперечить обмеженням`);
                });
                this.isBusy = false;
                this.$bvModal.hide("rentModal");
            },
            updateRent(updateRent) {
                this.isBusy = true;
                DataService.updateRecord(this.resource, updateRent).then(() => {
                    this.addMessage(`Запис оренди №${updateRent.id} змінено успішно`);
                    this.refreshRents();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Змінюваний запис оренди містить інформацію, що суперечить обмеженням`);
                });
                this.isBusy = false;
                this.$bvModal.hide("rentModal");
            },
            openDeleteModal(id) {
                this.processingId = id;
                this.dismissMessages();
                this.dismissErrors();
                this.$bvModal.show("deleteModal");
            },
            deleteRent(id) {
                this.isBusy = true;
                DataService.deleteRecord(this.resource, id).then(() => {
                    this.addMessage(`Видалення запису оренди №${id} виконано успішно`);
                    this.refreshRents();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Видалення запису оренди №${id} не виконано!`);
                });
                this.isBusy = false;
                this.$bvModal.hide("deleteModal");
            }
        },
        created() {
            this.refreshRents();
        }
    }
</script>

<style lang="css">
    @import "../../../styles/main.css";
</style>