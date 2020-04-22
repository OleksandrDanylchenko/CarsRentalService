<template>
    <div>
        <b-modal id="modelModal" no-close-on-backdrop @show="loadValues" @hidden="resetForm">
            <template v-slot:modal-title>
                <h3>{{ actionMessage }}</h3>
            </template>

            <template v-slot:default>
                <ValidationObserver>
                    <b-form slot-scope="{ validate }" @submit.prevent="validate().then(handleSubmit)" id="modelForm">
                        <ValidationProvider rules="required|alpha_spaces|max:150" name="бренду">
                            <b-form-group slot-scope="{ valid, errors }" label="Бренд">
                                <b-form-input
                                        type="text"
                                        v-model="formModel.brand"
                                        :state="errors[0] ? false : (valid ? true : null)"
                                        placeholder="Уведіть назву бренду">
                                </b-form-input>
                                <b-form-invalid-feedback>
                                    {{ errors[0] }}
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|alpha_spaces|max:150" name="моделі">
                            <b-form-group slot-scope="{ valid, errors }" label="Модель">
                                <b-form-input
                                        type="text"
                                        v-model="formModel.model"
                                        :state="errors[0] ? false : (valid ? true : null)"
                                        placeholder="Уведіть назву моделі">
                                </b-form-input>
                                <b-form-invalid-feedback>
                                    {{ errors[0] }}
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|numeric|min_value:2008|max_value:2020" name="року">
                            <b-form-group
                                    slot-scope="{ valid, errors }" label="Рік">
                                <b-form-input
                                        type="text"
                                        v-model="formModel.year"
                                        :state="errors[0] ? false : (valid ? true : null)"
                                        placeholder="Уведіть рік модельного ряду">
                                </b-form-input>
                                <b-form-invalid-feedback>
                                    {{ errors[0] }}
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </ValidationProvider>
                    </b-form>
                </ValidationObserver>
            </template>

            <template v-slot:modal-footer="{ cancel }">
                <b-button variant="dark" @click="cancel()">
                    Скасувати
                </b-button>
                <b-button type="submit" form="modelForm" variant="danger" >
                    {{ action }}
                </b-button>
            </template>
        </b-modal>
    </div>
</template>

<script>
    import ModelDataService from "../../service/ModelDataService";

    export default {
        props: ['processingId'],
        data() {
            return {
                formModel: {
                    id: null,
                    brand: null,
                    model: null,
                    year: null
                }
            }
        },
        methods: {
            loadValues() {
                this.$nextTick(() => {
                    ModelDataService.retrieveModel(this.processingId).then(response => {
                        this.formModel.brand = response.data.brand;
                        this.formModel.model = response.data.model;
                        this.formModel.year = response.data.year;
                    }).catch(error => {
                        console.log(`On editing of ${this.id} occurred ${error}`)
                    });
                    this.formModel.id = this.processingId;
                })
            },
            resetForm() {
                this.formModel = {
                    id: null,
                    brand: null,
                    model: null,
                    year: null
                }
            },
            handleSubmit() {
                if (this.formModel.id < 0)
                    this.$emit('addModel', this.formModel);
                else
                    this.$emit('updateModel', this.formModel);
            }
        },
        computed: {
            actionMessage() {
                return this.processingId <= 0 ? 'Додайте нову модель авто' : 'Змініть модель авто';
            },
            action() {
                return this.processingId <= 0 ? 'Додати' : 'Змінити';
            }
        }
    }
</script>