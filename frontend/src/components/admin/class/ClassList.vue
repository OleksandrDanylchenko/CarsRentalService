<template>
  <div>
    <b-row>
      <b-col>
        <b-button pill variant="outline-danger" @click="openClassModal(-1)">
          <i class="fas fa-plus-circle"></i>&nbsp;Додати нову конфігурацію
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
          <h1 class="mb-3 display-4">Список класів авто:</h1>
          <b-table
            id="classesTable"
            hover
            :items="classes"
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
                @click="openClassModal(data.item.id)"
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
    <ClassModal
      :processingId="processingId"
      @addClass="addClass"
      @updateClass="updateClass"
      @addError="addError"
    />
    <DeleteModal :processingId="processingId" @deleteRecord="deleteClass" />
  </div>
</template>

<script>
  import MessagesErrorsComponent from '../common/MessagesErrorsComponent'
  import { MessagesErrorsDismissMixin } from '../../mixins/MessagesErrorsDismissMixin'
  import ClassModal from './ClassModal'
  import DeleteModal from '../common/DeleteModal'
  import DataService from '../../../service/DataService'

  export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "ClassesList",
  components: {
    MessagesErrorsComponent,
    ClassModal,
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
        { key: "name", label: "Назва класу авто", sortable: true },
        { key: "startPrice", label: "Початкова ціна", sortable: true },
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
      classes: [],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      resource: "classes",

      messages: [],
      errors: [],
    };
  },
  methods: {
    refreshClasses() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.resource)
        .then((response) => {
          this.classes = response.data;
          this.isBusy = false;
        })
        .catch((error) => {
          console.log(error);
          if (error.response.status === 404) {
            this.addError(`Таблиця класів не містить записів`);
          } else {
            this.addError(
              `Сталася непередбачувана помилка завантаження таблиці`
            );
          }
        });
    },
    openClassModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("classModal");
    },
    addClass(newClass) {
      this.isBusy = true;
      DataService.addRecord(this.resource, newClass)
        .then(() => {
          this.addMessage(`Новий клас авто додано успішно`);
          this.refreshClasses();
        })
        .catch((error) => {
          console.log(error);
          this.addError(
            `Новий клас авто містить інформацію, що суперечить обмеженням`
          );
        });
      this.isBusy = false;
      this.$bvModal.hide("classModal");
    },
    updateClass(updateClass) {
      this.isBusy = true;
      DataService.updateRecord(this.resource, updateClass)
        .then(() => {
          this.addMessage(`Клас №${updateClass.id} змінено успішно`);
          this.refreshClasses();
        })
        .catch((error) => {
          console.log(error);
          this.addError(
            `Змінюваний клас містить інформацію, що суперечить обмеженням`
          );
        });
      this.isBusy = false;
      this.$bvModal.hide("classModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteClass(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.resource, id)
        .then(() => {
          this.addMessage(`Видалення класу №${id} виконано успішно`);
          this.refreshClasses();
        })
        .catch((error) => {
          console.log(error);
          this.addError(`Видалення класу №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    },
  },
  created() {
    this.refreshClasses();
  },
};
</script>

<style lang="css">
@import "../../../styles/main.css";
</style>
