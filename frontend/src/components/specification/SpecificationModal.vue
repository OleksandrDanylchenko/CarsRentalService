<template>
    <div>
        <b-modal id="specificationModal" no-close-on-backdrop
                 @show="loadValues" @hidden="resetForm">
            <template v-slot:modal-title>
                <h4>{{ actionMessage }}</h4>
            </template>

            <template v-slot:default>
                <ValidationObserver>
                    <b-form slot-scope="{ validate }" @submit.prevent="validate().then(handleSubmit)"
                            id="specificationForm">
                        <ValidationProvider rules="required|decimal:1|min_value:0.8|max_value:6|min:3"
                                            name="з об'ємом двигуна">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Об'єм двигуна">
                                    <b-form-input
                                            type="number"
                                            step="0.1"
                                            v-model="formSpecification.engineCapacity"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|integer|min_value:50|max_value:2000"
                                            name="з кількістю кінських сил">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Кінські сили">
                                    <b-form-input
                                            type="number"
                                            v-model="formSpecification.horsepowers"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required" name="з типом пального">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Тип пального">
                                    <b-form-select v-model="formSpecification.fuelType" :options="fuelTypes"
                                                   :state="errors[0] ? false : (valid ? true : null)"/>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|decimal:1|min_value:3|max_value:50|min:3"
                                            name="зі споживанням пального">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Споживання пального">
                                    <b-form-input
                                            type="number"
                                            step="0.1"
                                            v-model="formSpecification.fuelConsumption"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required" name="з типом трансмісії">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Тип трансмісії">
                                    <b-form-select v-model="formSpecification.transmissionType"
                                                   :options="transmissionTypes"
                                                   :state="errors[0] ? false : (valid ? true : null)"/>
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
                <b-button type="submit" form="specificationForm" variant="danger">
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
                formSpecification: {
                    id: null,
                    engineCapacity: null,
                    horsepowers: null,
                    fuelType: null,
                    fuelConsumption: null,
                    transmissionType: null,
                },
                fuelTypes: [
                    {value: "Бензин", text: "Бензин"},
                    {value: "Дизель", text: "Дизель"},
                    {value: "Газ/Бензин", text: "Газ/Бензин"},
                    {value: "Гібрид", text: "Гібрид"},
                    {value: "Електро", text: "Електро"}
                ],
                transmissionTypes: [
                    {value: "Механіка", text: "Механіка"},
                    {value: "Автомат", text: "Автомат"},
                    {value: "Тіптронік", text: "Тіптронік"},
                    {value: "Робот", text: "Робот"},
                    {value: "Варіатор", text: "Варіатор"}
                ],
                resource: "specifications"
            }
        },
        methods: {
            loadValues() {
                this.$nextTick(() => {
                    DataService.retrieveRecord(this.resource, this.processingId).then(response => {
                        this.formSpecification.engineCapacity = response.data.engineCapacity;
                        this.formSpecification.horsepowers = response.data.horsepowers;
                        this.formSpecification.fuelType = response.data.fuelType;
                        this.formSpecification.fuelConsumption = response.data.fuelConsumption;
                        this.formSpecification.transmissionType = response.data.transmissionType;
                    }).catch(error => {
                        console.log(error);
                        if (error.response.status !== 404)
                            this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                    });
                    this.formSpecification.id = this.processingId;
                })
            },
            resetForm() {
                this.formSpecification = {
                    id: null,
                    engineCapacity: null,
                    horsepowers: null,
                    fuelType: null,
                    fuelConsumption: null,
                    transmissionType: null,
                }
            },
            handleSubmit() {
                if (this.formSpecification.id < 0)
                    this.$emit('addSpecification', this.formSpecification);
                else
                    this.$emit('updateSpecification', this.formSpecification);
            }
        },
        computed: {
            actionMessage() {
                return this.processingId <= 0 ? 'Додайте нову конфігурацію авто' : 'Змініть конфігурацію авто';
            },
            action() {
                return this.processingId <= 0 ? 'Додати' : 'Змінити';
            }
        }
    }
</script>