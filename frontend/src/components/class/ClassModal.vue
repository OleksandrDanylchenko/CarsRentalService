<template>
    <div>
        <b-modal id="classModal" no-close-on-backdrop
                 @show="loadValues" @hidden="resetForm">
            <template v-slot:modal-title>
                <h4>{{ actionMessage }}</h4>
            </template>

            <template v-slot:default>
                <ValidationObserver>
                    <b-form slot-scope="{ validate }" @submit.prevent="validate().then(handleSubmit)"
                            id="classForm">
                        <ValidationProvider rules="required|integer|min_value:1|max_value:40000"
                                            name="мінімальної ціни">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Мінімальна ціна">
                                    <b-form-input
                                            type="number"
                                            v-model="formClass.minPrice"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|integer|min_value:1|max_value:40000"
                                            name="максимальної ціни">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Максимальна ціна">
                                    <b-form-input
                                            type="number"
                                            v-model="formClass.maxPrice"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|alpha_spaces|max:150" name="назви класу">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Назва">
                                    <b-form-input
                                            type="text"
                                            v-model="formClass.name"
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
                <b-button type="submit" form="classForm" variant="danger">
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
                formClass: {
                    id: null,
                    minPrice: null,
                    maxPrice: null,
                    name: null
                },
                resource: "classes"
            }
        },
        methods: {
            loadValues() {
                this.$nextTick(() => {
                    DataService.retrieveRecord(this.resource, this.processingId).then(response => {
                        this.formClass.minPrice = response.data.minPrice;
                        this.formClass.maxPrice = response.data.maxPrice;
                        this.formClass.name = response.data.name;
                    }).catch(error => {
                        console.log(error);
                        if (error.response.status !== 404)
                            this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                    });
                    this.formClass.id = this.processingId;
                })
            },
            resetForm() {
                this.formClass = {
                    id: null,
                    minPrice: null,
                    maxPrice: null,
                    name: null,
                }
            },
            handleSubmit() {
                if (this.formClass.id < 0)
                    this.$emit('addClass', this.formClass);
                else
                    this.$emit('updateClass', this.formClass);
            }
        },
        computed: {
            actionMessage() {
                return this.processingId <= 0 ? 'Додайте новий клас авто' : 'Змініть клас авто';
            },
            action() {
                return this.processingId <= 0 ? 'Додати' : 'Змінити';
            }
        }
    }
</script>

<style lang="css">
    @import "../../styles/uploadBadge.css";
</style>