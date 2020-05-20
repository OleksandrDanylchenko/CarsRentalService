<template>
  <div>
    <b-modal
      id="discountModal"
      no-close-on-backdrop
      @show="loadValues"
      @hidden="resetForm"
    >
      <template v-slot:modal-title>
        <h2>{{ actionMessage }}</h2>
      </template>

      <template v-slot:default>
        <ValidationObserver>
          <b-form
            slot-scope="{ validate }"
            @submit.prevent="validate().then(handleSubmit)"
            id="discountForm"
          >
            <ValidationProvider
              rules="required|integer|min_value:1|max_value:2147483647"
              name="з кількістю оренд"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Кількість оренд">
                  <b-form-input
                    type="number"
                    v-model="formDiscount.rentsAmount"
                    :state="errors[0] ? false : valid ? true : null"
                  >
                  </b-form-input>
                  <b-form-invalid-feedback>
                    {{ errors[0] }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </ValidationProvider>

            <ValidationProvider
              rules="required|integer|min_value:1|max_value:100"
              name="з відсотком знижки"
            >
              <b-form-group slot-scope="{ valid, errors }">
                <b-input-group prepend="Відсоток знижки">
                  <b-form-input
                    type="number"
                    v-model="formDiscount.discountPercents"
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
        <b-button type="submit" form="discountForm" variant="danger">
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
      formDiscount: {
        id: null,
        rentsAmount: null,
        discountPercents: null,
      },
      resource: "discounts",
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.resource, this.processingId)
          .then((response) => {
            this.formDiscount.rentsAmount = response.data.rentsAmount;
            this.formDiscount.discountPercents = response.data.discountPercents;
          })
          .catch((error) => {
            console.log(error);
            if (error.response.status !== 404)
              this.$emit(
                "addError",
                `Модельне вікно спричинило помилку ${error}`
              );
          });
        this.formDiscount.id = this.processingId;
      });
    },
    resetForm() {
      this.formDiscount = {
        id: null,
        rentsAmount: null,
        discountPercents: null,
      };
    },
    handleSubmit() {
      if (this.formDiscount.id < 0)
        this.$emit("addDiscount", this.formDiscount);
      else this.$emit("updateDiscount", this.formDiscount);
    },
  },
  computed: {
    actionMessage() {
      return this.processingId <= 0
        ? "Додайте нового клієнта"
        : "Змініть клієнта";
    },
    action() {
      return this.processingId <= 0 ? "Додати" : "Змінити";
    },
  },
};
</script>
