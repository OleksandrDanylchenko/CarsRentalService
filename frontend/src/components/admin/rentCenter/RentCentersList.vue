<template>
  <div>
    <b-row>
      <b-col>
        <b-button
          pill
          variant="outline-danger"
          @click="openRentCenterModal(-1)"
        >
          <i class="fas fa-plus-circle"></i>&nbsp;Додати новий центр оренди
        </b-button>
      </b-col>
    </b-row>
    <div class="mt-4">
      <MessagesErrorsComponent
        :messages="messages"
        :errors="errors"
        @dismissMessages="dismissMessages"
        @dismissErrors="dismissErrors"
      />
      <b-row>
        <b-col>
          <h1 class="mb-3 display-4">Список центрів оренди:</h1>
          <b-table
            id="centersTable"
            hover
            :items="rentCenters"
            :fields="fields"
            :busy.sync="isBusy"
            primary-key="id"
          >
            <template v-slot:table-busy>
              <div class="text-center text-danger mt-2">
                <b-spinner
                  type="grow"
                  class="align-middle"
                  style="width: 3rem; height: 3rem;"
                />
                <strong>&nbsp;Завантаження таблиці...</strong>
              </div>
            </template>
            <template v-slot:cell(editModal)="data">
              <b-button
                pill
                variant="outline-dark"
                @click="openRentCenterModal(data.item.id)"
              >
                <i class="fa fa-edit"></i>
              </b-button>
            </template>
            <template v-slot:cell(deleteModal)="data">
              <b-button
                pill
                variant="outline-danger"
                @click="openDeleteModal(data.item.id)"
              >
                <i class="fa fa-trash"></i>
              </b-button>
            </template>
          </b-table>
        </b-col>
      </b-row>
    </div>
    <RentCentersList
      :processingId="processingId"
      @addCenter="addCenter"
      @updateCenter="updateCenter"
      @addError="addError"
    />
    <DeleteModal :processingId="processingId" @deleteRecord="deleteCenter" />
  </div>
</template>

<script>
  import MessagesErrorsComponent from '../common/MessagesErrorsComponent'
  import { MessagesErrorsDismissMixin } from '../../mixins/MessagesErrorsDismissMixin'
  import RentCentersList from './RentCenterModal'
  import DeleteModal from '../common/DeleteModal'
  import DataService from '../../../service/DataService'

  export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "CentersList",
  components: {
    MessagesErrorsComponent,
    RentCentersList,
    DeleteModal,
  },
  data() {
    return {
      fields: [
        {
          key: "id",
          label: "ID",
          sortable: true,
          thClass: "text-danger",
          tdClass: "text-danger",
        },
        { key: "address", label: "Адреса", sortable: true },
        { key: "phoneNumber", label: "Номер телефона", sortable: true },
        {
          key: "editModal",
          label: "Змінити",
          thClass: "text-center",
          tdClass: "text-center",
        },
        {
          key: "deleteModal",
          label: "Видалити",
          thClass: "text-center",
          tdClass: "text-center",
        },
      ],
      rentCenters: [],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      resource: "rent_centers",

      messages: [],
      errors: [],
    };
  },
  methods: {
    refreshCenters() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.resource)
        .then((response) => {
          this.rentCenters = response.data;
          this.isBusy = false;
        })
        .catch((error) => {
          console.log(error);
          if (error.response.status === 404) {
            this.addError(`Таблиця центрів оренди не містить записів`);
          } else {
            this.addError(
              `Сталася непередбачувана помилка завантаження таблиці`
            );
          }
        });
    },
    openRentCenterModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("centerModal");
    },
    addCenter(newCenter) {
      this.isBusy = true;
      DataService.addRecord(this.resource, newCenter)
        .then(() => {
          this.addMessage(`Новий центр оренди додано успішно`);
          this.refreshCenters();
        })
        .catch((error) => {
          console.log(error);
          this.addError(
            `Новий центр оренди містить інформацію, що суперечить обмеженням`
          );
        });
      this.isBusy = false;
      this.$bvModal.hide("centerModal");
    },
    updateCenter(updateCenter) {
      this.isBusy = true;
      DataService.updateRecord(this.resource, updateCenter)
        .then(() => {
          this.addMessage(`Центр оренди №${updateCenter.id} змінено успішно`);
          this.refreshCenters();
        })
        .catch((error) => {
          console.log(error);
          this.addError(
            `Змінюваний центр оренди містить інформацію, що суперечить обмеженням`
          );
        });
      this.isBusy = false;
      this.$bvModal.hide("centerModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteCenter(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.resource, id)
        .then(() => {
          this.addMessage(`Видалення центру оренди №${id} виконано успішно`);
          this.refreshCenters();
        })
        .catch((error) => {
          console.log(error);
          this.addError(`Видалення центру оренди №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    },
  },
  created() {
    this.refreshCenters();
  },
};
</script>

<style lang="css">
@import "../../../styles/main.css";
</style>
