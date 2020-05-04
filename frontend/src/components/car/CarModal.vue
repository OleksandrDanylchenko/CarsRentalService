<template>
    <div>
        <b-modal id="carModal" no-close-on-backdrop
                 @show="loadValues" @hidden="resetForm">
            <template v-slot:modal-title>
                <h4>{{ actionMessage }}</h4>
            </template>

            <template v-slot:default>
                <ValidationObserver>
                    <b-form slot-scope="{ validate }" @submit.prevent="validate().then(handleSubmit)" id="carForm">
                        <ValidationProvider rules="required" name="з моделлю">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Модель авто">
                                    <b-form-select v-model="formCar.model.id" :options="availableModels"
                                                   :state="errors[0] ? false : (valid ? true : null)"/>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required" name="з конфігурацією">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Конфігурація авто">
                                    <b-form-select v-model="formCar.specification.id" :options="availableSpecifications"
                                                   :state="errors[0] ? false : (valid ? true : null)"/>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|integer|min_value:50|max_value:400000"
                                            name="з добовим тарифом">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Добовий тариф">
                                    <b-form-input
                                            type="number"
                                            v-model="formCar.day_price"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Доступна для оренди" class="d-flex align-items-center">
                                    <b-form-radio v-model="formCar.rentable" name="rentable-radio" value="true"
                                                  class="ml-2">
                                        Так
                                    </b-form-radio>
                                    <b-form-radio v-model="formCar.rentable" name="rentable-radio" value="false"
                                                  class="ml-2">
                                        Ні
                                    </b-form-radio>
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
                <b-button type="submit" form="carForm" variant="danger">
                    {{ action }}
                </b-button>
            </template>
        </b-modal>
    </div>
</template>

<script>
    import DataService from "../../service/DataService";

    export default {
        props: ['processingId'],
        data() {
            return {
                formCar: {
                    id: null,
                    day_price: null,
                    rentable: null,
                    model: {
                        id: null,
                        model: null
                    },
                    specification: {
                        id: null
                    },
                    rentCenter: {
                        id: null
                    }
                },
                availableModels: [],
                availableSpecifications: [],
                resource: "cars",
                modelsResource: "models",
                specificationsResource: "specifications"
            }
        },
        methods: {
            loadValues() {
                this.$nextTick(() => {
                    DataService.retrieveRecord(this.resource, this.processingId).then(response => {
                        this.formCar.day_price = response.data.dayPrice;
                        this.formCar.rentable = response.data.rentable;
                        this.formCar.model.id = response.data.model.id;
                        this.formCar.specification.id = response.data.specification.id;
                        this.formCar.rentCenter.id = response.data.rentCenter.id;
                    }).catch(error => {
                        console.log(error);
                        if (error.response.status !== 404) {
                            this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                            this.$bvModal.hide("carModal");
                        }
                    });
                    this.formCar.id = this.processingId;
                })
            },
            loadChoices() {
                DataService.retrieveAllRecords(this.modelsResource).then(response => {
                    response.data.forEach(record => {
                        let modelOption = {value: record.id, text: record.model};
                        this.availableModels.push(modelOption);
                    })
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404)
                        this.$emit('addError', `Таблиця моделей не містить записів, тому не можна створити авто.`);
                    else
                        this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                    this.$bvModal.hide("carModal");
                });
                DataService.retrieveAllRecords(this.specificationsResource).then(response => {
                    response.data.forEach(record => {
                        let specificationOption = {value: record.id, text: record.id};
                        this.availableSpecifications.push(specificationOption);
                    })
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404)
                        this.$emit('addError', `Таблиця конфігурацій не містить записів, тому не можна створити авто.`);
                    else
                        this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                    this.$bvModal.hide("carModal");
                });
            },
            resetForm() {
                this.formCar = {
                    id: null,
                    day_price: null,
                    rentable: null,
                    model: {
                        id: null,
                        model: null
                    },
                    specification: {
                        id: null
                    },
                    rentCenter: {
                        id: null
                    }
                }
            },
            handleSubmit() {
                if (this.formCar.id < 0)
                    this.$emit('addSpecification', this.formCar);
                else
                    this.$emit('updateSpecification', this.formCar);
            }
        },
        computed: {
            actionMessage() {
                return this.processingId <= 0 ? 'Додайте новий автомобіль' : 'Змініть автомобіль';
            },
            action() {
                return this.processingId <= 0 ? 'Додати' : 'Змінити';
            }
        },
        created() {
            this.loadChoices();
        }
    }
</script>