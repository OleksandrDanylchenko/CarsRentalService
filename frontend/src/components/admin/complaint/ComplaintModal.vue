<template>
  <div>
    <b-modal
      id="complaintModal"
      no-close-on-backdrop
      @show="loadValues"
      @hidden="resetForm"
    >
      <template v-slot:modal-title>
        <h4>{{ actionMessage }}</h4>
      </template>

      <template v-slot:default>
        <ValidationObserver>
          <b-form
            slot-scope="{ validate }"
            @submit.prevent="validate().then(handleSubmit)"
            id="complaintForm"
          >
            <ValidationProvider rules="required" name="з записом про оренду">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Запис про оренду">
                  <b-form-select
                    v-model="formComplaint.rent.id"
                    :options="availableRents"
                    :state="errors[0] ? false : valid ? true : null"
                  />
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider rules="required|max:200" name="теми скарги">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Тема">
                  <b-form-input
                    type="text"
                    v-model="formComplaint.theme"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider rules="required|max:8000" name="тексту скарги">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Текст">
                  <b-form-textarea
                    v-model="formComplaint.text"
                    :state="errors[0] ? false : valid ? true : null"
                    rows="3"
                    max-rows="50"
                  ></b-form-textarea>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>
          </b-form>
        </ValidationObserver>
      </template>

      <template v-slot:modal-footer="{ cancel }">
        <b-button variant="dark" @click="cancel()">
          Скасувати
        </b-button>
        <b-button type="submit" form="complaintForm" variant="danger">
          {{ action }}
        </b-button>
      </template>
    </b-modal>
  </div>
</template>

<script>
  import DataService from '../../../service/DataService'

  export default {
  props: ["processingId"],
  data() {
    return {
      formComplaint: {
        id: null,
        theme: null,
        text: null,
        rent: {
          id: null,
        },
      },
      availableRents: [],
      resource: "complaints",
      rentsResource: "rents",
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.resource, this.processingId)
          .then((response) => {
            this.formComplaint.theme = response.data.theme;
            this.formComplaint.text = response.data.text;
            this.formComplaint.rent.id = response.data.rent.id;
          })
          .catch((error) => {
            console.log(error);
            if (error.response.status !== 404) {
              this.$emit(
                "addError",
                `Модельне вікно спричинило помилку ${error}`
              );
              this.$bvModal.hide("complaintModal");
            }
          });
        this.formComplaint.id = this.processingId;
      });
    },
    loadChoices() {
      DataService.retrieveAllRecords(this.rentsResource)
        .then((response) => {
          response.data.forEach((record) => {
            let rentOption = {
              value: record.id,
              text:
                "№" +
                record.id +
                " " +
                record.client.firstName +
                " " +
                record.client.secondName,
            };
            this.availableRents.push(rentOption);
          });
        })
        .catch((error) => {
          console.log(error);
          if (error.response.status === 404)
            this.$emit(
              "addError",
              `Таблиця записів про оренду не містить записів, тому не можна створити скаргу.`
            );
          else
            this.$emit(
              "addError",
              `Модельне вікно спричинило помилку ${error}`
            );
          this.$bvModal.hide("complaintModal");
        });
    },
    resetForm() {
      this.formComplaint = {
        id: null,
        theme: null,
        text: null,
        rent: {
          id: null,
        },
      };
    },
    handleSubmit() {
      if (this.formComplaint.id < 0)
        this.$emit("addComplaint", this.formComplaint);
      else this.$emit("updateComplaint", this.formComplaint);
    },
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0 ? "Додайте нову скаргу" : "Змініть скаргу";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    },
  },
  created() {
    this.loadChoices();
  },
};
</script>
