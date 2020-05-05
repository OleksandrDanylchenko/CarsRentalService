<template>
    <div>
        <b-modal id="rentModal" no-close-on-backdrop
                 @show="loadValues" @hidden="resetForm">
            <template v-slot:modal-title>
                <h4>{{ actionMessage }}</h4>
            </template>

            <template v-slot:default>
                <ValidationObserver>
                    <b-form slot-scope="{ validate }" @submit.prevent="validate().then(handleSubmit)" id="rentForm">
                        <ValidationProvider rules="required" name="з авто">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Орендне авто">
                                    <b-form-select v-model="formRent.car.id" :options="availableCars"
                                                   :state="errors[0] ? false : (valid ? true : null)"/>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required" name="з клієнтом">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Клієнт">
                                    <b-form-select v-model="formRent.client.id"
                                                   :options="availableClients"
                                                   :state="errors[0] ? false : (valid ? true : null)"/>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required" name="з датою початку оренди">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Дата початку оренди">
                                    <b-form-input
                                            type="text"
                                            v-model="formRent.rentStart"
                                            placeholder="📅"
                                            onfocus="(this.type='date')"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required" name="з датою закінчення оренди">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Дата закінчення оренди">
                                    <b-form-input
                                            type="text"
                                            v-model="formRent.rentEnd"
                                            placeholder="📅"
                                            onfocus="(this.type='date')"
                                            :state="errors[0] ? false : (valid ? true : null)">
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
                <b-button type="submit" form="rentForm" variant="danger">
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
                formRent: {
                    id: null,
                    rentStart: null,
                    rentEnd: null,
                    client: {
                        id: null,
                        firstName: null,
                        secondName: null
                    },
                    car: {
                        id: null,
                        brand: null,
                        model: null
                    }
                },
                availableClients: [],
                availableCars: [],
                availableCenters: [],
                resource: "rents",
                clientsResource: "clients",
                carsResource: "cars"
            }
        },
        methods: {
            loadValues() {
                this.$nextTick(() => {
                    DataService.retrieveRecord(this.resource, this.processingId).then(response => {
                        this.formRent.rentStart = response.data.rentStart;
                        this.formRent.rentEnd = response.data.rentEnd;
                        this.formRent.client.id = response.data.client.id;
                        this.formRent.car.id = response.data.car.id;
                    }).catch(error => {
                        console.log(error);
                        if (error.response.status !== 404) {
                            this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                            this.$bvModal.hide("rentModal");
                        }
                    });
                    this.formRent.id = this.processingId;
                })
            },
            loadChoices() {
                DataService.retrieveAllRecords(this.clientsResource).then(response => {
                    response.data.forEach(record => {
                        let clientOption = {value: record.id, text: record.firstName + ' ' + record.secondName};
                        this.availableClients.push(clientOption);
                    })
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404)
                        this.$emit('addError', `Таблиця клієнтів не містить записів, тому не можна створити запис про оренду.`);
                    else
                        this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                    this.$bvModal.hide("rentModal");
                });
                DataService.retrieveAllRecords(this.carsResource).then(response => {
                    response.data.forEach(record => {
                        let carOption = {value: record.id, text: record.model.brand + ' ' + record.model.model};
                        this.availableCars.push(carOption);
                    })
                }).catch(error => {
                    console.log(error);
                    if (error.response.status === 404)
                        this.$emit('addError', `Таблиця конфігурацій не містить записів, тому не можна створити авто.`);
                    else
                        this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                    this.$bvModal.hide("rentModal");
                });
            },
            resetForm() {
                this.formRent = {
                    id: null,
                    rentStart: null,
                    rentEnd: null,
                    client: {
                        id: null,
                        firstName: null,
                        secondName: null
                    },
                    car: {
                        id: null,
                        brand: null,
                        model: null
                    }
                }
            },
            handleSubmit() {
                if (this.formRent.id < 0)
                    this.$emit('addRent', this.formRent);
                else
                    this.$emit('updateRent', this.formRent);
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