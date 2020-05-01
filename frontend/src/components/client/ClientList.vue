<template>
    <div>
        <b-row>
            <b-col>
                <b-button pill variant="outline-danger" @click="openClientModal(-1)">
                    <i class="fas fa-plus-circle"></i>&nbsp;Додати нового клієнта
                </b-button>
            </b-col>
        </b-row>
        <div class="mt-4">
            <MessagesErrorsComponent :messages="messages" :errors="errors"
                                     @dismissMessages="dismissMessages" @dismissErrors="dismissErrors"/>
            <b-row>
                <b-col>
                    <h1 class="mb-3 display-4">Список клієнтів:</h1>
                    <b-table id="clientsTable" hover :items="clients" :fields="fields"
                             :busy.sync="isBusy" primary-key="id">
                        <template v-slot:table-busy>
                            <div class="text-center text-danger mt-2">
                                <b-spinner type="grow" class="align-middle" style="width: 3rem; height: 3rem;"/>
                                <strong>&nbsp;Завантаження таблиці...</strong>
                            </div>
                        </template>
                        <template v-slot:cell(editModal)="data">
                            <b-button pill variant="outline-dark" @click="openClientModal(data.item.id)">
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
        <ClientModal :processingId="processingId" @addClient="addClient"
                     @updateClient="updateClient" @addError="addError"/>
        <DeleteModal :processingId="processingId" @deleteRecord="deleteClient"/>
    </div>
</template>

<script>
    import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
    import {MessagesErrorsDismissMixin} from "../mixins/MessagesErrorsDismissMixin"
    import ClientModal from "./ClientModal"
    import DeleteModal from "../common/DeleteModal"
    import DataService from "../../service/DataService";

    export default {
        mixins: [MessagesErrorsDismissMixin],
        name: "ClientsList",
        components: {
            MessagesErrorsComponent,
            ClientModal,
            DeleteModal
        },
        data() {
            return {
                fields: [
                    {key: 'id', label: 'ID', sortable: true, thClass: 'text-danger', tdClass: 'text-danger'},
                    {key: 'firstName', label: 'Ім\'я', sortable: true},
                    {key: 'secondName', label: 'Прізвище', sortable: true},
                    {key: 'address', label: 'Адреса', sortable: true},
                    {key: 'ridesAmount', label: 'Кількість поїздок', sortable: true},
                    {key: 'editModal', label: 'Змінити', thClass: 'text-center', tdClass: 'text-center'},
                    {key: 'deleteModal', label: 'Видалити', thClass: 'text-center', tdClass: 'text-center'},
                ],
                clients: [],
                isBusy: true,
                processingId: Number.MIN_VALUE,
                resource: 'clients',

                messages: [],
                errors: []
            }
        },
        methods: {
            refreshClients() {
                this.isBusy = true;
                DataService.retrieveAllRecords(this.resource).then(response => {
                    this.clients = response.data;
                    this.isBusy = false;
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404) {
                        this.addError(`Таблиця специфікацій не містить записів`);
                    } else {
                        this.addError(`Сталася непередбачувана помилка завантаження таблиці`);
                    }
                });
            },
            openClientModal(id) {
                this.dismissMessages();
                this.dismissErrors();
                this.processingId = id;
                this.$bvModal.show("clientModal");
            },
            addClient(newClient) {
                this.isBusy = true;
                DataService.addRecord(this.resource, newClient).then(() => {
                    this.addMessage(`Нову модель додано успішно`);
                    this.refreshClients();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Нова модель містить інформацію, що суперечить обмеженням`);
                });
                this.isBusy = false;
                this.$bvModal.hide("clientModal");
            },
            updateClient(updateClient) {
                this.isBusy = true;
                DataService.updateRecord(this.resource, updateClient).then(() => {
                    this.addMessage(`Модель №${updateClient.id} змінено успішно`);
                    this.refreshClients();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Змінювана модель містить інформацію, що суперечить обмеженням`);
                });
                this.isBusy = false;
                this.$bvModal.hide("clientModal");
            },
            openDeleteModal(id) {
                this.processingId = id;
                this.dismissMessages();
                this.dismissErrors();
                this.$bvModal.show("deleteModal");
            },
            deleteClient(id) {
                this.isBusy = true;
                DataService.deleteRecord(this.resource, id).then(() => {
                    this.addMessage(`Видалення запису №${id} виконано успішно`);
                    this.refreshClients();
                }).catch(error => {
                    console.log(error);
                    this.addError(`Видалення запису №${id} не виконано!`);
                });
                this.isBusy = false;
                this.$bvModal.hide("deleteModal");
            }
        },
        created() {
            this.refreshClients();
        }
    }
</script>

<style lang="css">
    @import "../../styles/main.css";
</style>