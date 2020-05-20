<template>
  <div>
    <b-modal
      id="centerModal"
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
            id="centerForm"
          >
            <ValidationProvider rules="required|max:150" name="адреси">
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Адреса">
                  <b-form-input
                    type="text"
                    v-model="formCenter.address"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>
          </b-form>
        </ValidationObserver>

        <ValidationObserver>
          <b-form
            slot-scope="{ validate }"
            @submit.prevent="validate().then(handleSubmit)"
            id="centerForm"
          >
            <ValidationProvider
              rules="required|regex:^\+?3?8?(0\d{9})$|max:15"
              name="мобільного телефону"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Мобільний телефон">
                  <b-form-input
                    type="text"
                    v-model="formCenter.phoneNumber"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
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
        <b-button type="submit" form="centerForm" variant="danger">
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
      formCenter: {
        id: null,
        address: null,
        phoneNumber: null,
      },
      resource: "rent_centers",
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.resource, this.processingId)
          .then((response) => {
            this.formCenter.address = response.data.address;
            this.formCenter.phoneNumber = response.data.phoneNumber;
          })
          .catch((error) => {
            console.log(error);
            if (error.response.status !== 404)
              this.$emit(
                "addError",
                `Модельне вікно спричинило помилку ${error}`
              );
          });
        this.formCenter.id = this.processingId;
      });
    },
    resetForm() {
      this.formCenter = {
        id: null,
        address: null,
        phoneNumber: null,
      };
    },
    handleSubmit() {
      if (this.formCenter.id < 0) this.$emit("addCenter", this.formCenter);
      else this.$emit("updateCenter", this.formCenter);
    },
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0
        ? "Додайте новий центр оренди авто"
        : "Змініть центр оренди";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    },
  },
};
</script>
