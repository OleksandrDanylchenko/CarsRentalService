<template>
  <div>
    <div class="mt-4">
      <MessagesErrorsComponent
        :messages="messages"
        :errors="errors"
        @dismissMessages="dismissMessages"
        @dismissErrors="dismissErrors"
      />
      <b-row>
        <b-col>
          <b-row>
            <b-col class="d-flex align-items-center justify-content-between">
              <h1 class="mb-3 display-4">Список авто:</h1>
              <b-button pill variant="outline-danger" @click="openCarModal(-1)">
                <i class="fas fa-plus-circle"></i>&nbsp;Додати нове авто
              </b-button>
            </b-col>
          </b-row>
          <b-table
            id="carsTable"
            hover
            :items="cars"
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
            <template v-slot:cell(modelId)="data">
              {{ data.item.model.id }}
            </template>
            <template v-slot:cell(modelName)="data">
              {{ data.item.model.brand + " " + data.item.model.model }}
            </template>
            <template v-slot:cell(classId)="data">
              {{ data.item.modelClass.id }}
            </template>
            <template v-slot:cell(className)="data">
              {{ data.item.modelClass.name }}
            </template>
            <template v-slot:cell(specId)="data">
              {{ data.item.specification.id }}
            </template>
            <template v-slot:cell(specFields)="data">
              <b-button
                variant="outline-primary"
                v-b-toggle="'specs' + data.item.id"
                >Деталі</b-button
              >
              <b-collapse :id="'specs' + data.item.id" class="mt-2">
                <b-card>
                  <p>
                    Об'єм двигуна:
                    {{ data.item.specification.engineCapacity }}л.
                  </p>
                  <p>
                    Кінські сили: {{ data.item.specification.horsepowers }}к.с.
                  </p>
                  <p>Тип пального: {{ data.item.specification.fuelType }}</p>
                  <p>
                    Споживання пального:
                    {{ data.item.specification.fuelConsumption }}л./100км.
                  </p>
                  <p>
                    Тип трансмісії:
                    {{ data.item.specification.transmissionType }}
                  </p>
                </b-card>
              </b-collapse>
            </template>
            <template v-slot:cell(centerId)="data">
              {{ data.item.rentCenter.id }}
            </template>
            <template v-slot:cell(centerAddress)="data">
              {{ data.item.rentCenter.address }}
            </template>
            <template v-slot:cell(rentable)="data">
              <i v-if="data.item.rentable" class="fas fa-check-circle"></i>
              <i v-else class="fas fa-times-circle"></i>
            </template>
            <template v-slot:cell(editModal)="data">
              <b-button
                pill
                variant="outline-primary"
                @click="openCarModal(data.item.id)"
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
    <CarModal
      :processingId="processingId"
      @addCar="addCar"
      @updateCar="updateCar"
      @addError="addError"
    />
    <DeleteModal :processingId="processingId" @deleteRecord="deleteCar" />
  </div>
</template>

<script>
  import MessagesErrorsComponent from '../common/MessagesErrorsComponent'
  import { MessagesErrorsDismissMixin } from '../../mixins/MessagesErrorsDismissMixin'
  import CarModal from './CarModal'
  import DeleteModal from '../common/DeleteModal'
  import DataService from '../../../service/DataService'

  export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "CarsList",
  components: {
    MessagesErrorsComponent,
    CarModal,
    DeleteModal,
  },
  data() {
    return {
      fields: [
        {
          key: "id",
          label: "ID",
          sortable: true,
          thClass: "text-danger text-center",
          tdClass: "text-danger text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "dayPrice",
          label: "Ціна за день оренди",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "modelId",
          label: "Код моделі",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "modelName",
          label: "Назва моделі",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "classId",
          label: "Код класу",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "className",
          label: "Назва класу",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "specId",
          label: "Код конфігурації",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "specFields",
          label: "Конфігурація",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "centerId",
          label: "Код центру оренди",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "centerAddress",
          label: "Адреса центру оренди",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "rentable",
          label: "Доступна для оренди",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "editModal",
          label: "Змінити",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
        {
          key: "deleteModal",
          label: "Видалити",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;",
        },
      ],
      cars: [],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      resource: "cars",

      messages: [],
      errors: [],
    };
  },
  methods: {
    refreshCars() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.resource)
        .then((response) => {
          this.cars = response.data;
          this.isBusy = false;
        })
        .catch((error) => {
          console.log(error);
          if (error.response.status === 404) {
            this.addError(`Таблиця автомобілів не містить записів`);
          } else {
            this.addError(
              `Сталася непередбачувана помилка завантаження таблиці`
            );
          }
        });
    },
    openCarModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("carModal");
    },
    addCar(newCar) {
      this.isBusy = true;
      DataService.addRecord(this.resource, newCar)
        .then(() => {
          this.addMessage(`Новий автомобіль додано успішно`);
          this.refreshCars();
        })
        .catch((error) => {
          console.log(error);
          this.addError(
            `Новий автомобіль містить інформацію, що суперечить обмеженням`
          );
        });
      this.isBusy = false;
      this.$bvModal.hide("carModal");
    },
    updateCar(updateCar) {
      this.isBusy = true;
      DataService.updateRecord(this.resource, updateCar)
        .then(() => {
          this.addMessage(`Модель №${updateCar.id} змінено успішно`);
          this.refreshCars();
        })
        .catch((error) => {
          console.log(error);
          this.addError(
            `Змінюваний автомобіль містить інформацію, що суперечить обмеженням`
          );
        });
      this.isBusy = false;
      this.$bvModal.hide("carModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteCar(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.resource, id)
        .then(() => {
          this.addMessage(`Видалення автомобіля №${id} виконано успішно`);
          this.refreshCars();
        })
        .catch((error) => {
          console.log(error);
          this.addError(`Видалення автомобіля №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    },
  },
  created() {
    this.refreshCars();
  },
};
</script>

<style lang="css">
@import "../../../styles/main.css";
</style>
