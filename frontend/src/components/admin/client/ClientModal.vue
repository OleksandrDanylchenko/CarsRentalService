<template>
    <div>
        <b-modal id="clientModal" no-close-on-backdrop
                 @show="loadValues" @hidden="resetForm">
            <template v-slot:modal-title>
                <h2>{{ actionMessage }}</h2>
            </template>

            <template v-slot:default>
                <ValidationObserver>
                    <b-form slot-scope="{ validate }" @submit.prevent="validate().then(handleSubmit)"
                            id="clientForm">
                        <ValidationProvider rules="required|alpha_spaces|max:150"
                                            name="імені">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Ім'я">
                                    <b-form-input
                                            type="text"
                                            v-model="formClient.firstName"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|alpha_spaces|max:150"
                                            name="прізвища">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Прізвище">
                                    <b-form-input
                                            type="text"
                                            v-model="formClient.secondName"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|max:150" name="адреси">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Адреса">
                                    <b-form-input
                                            type="text"
                                            v-model="formClient.address"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|integer|min_value:1|max_value:2147483647"
                                            name="з кількістю поїздок">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Кількість поїздок">
                                    <b-form-input
                                            type="number"
                                            v-model="formClient.ridesAmount"
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
                <b-button type="submit" form="clientForm" variant="danger">
                    {{ action }}
                </b-button>
            </template>
        </b-modal>
    </div>
</template>

<script>
    import DataService from "../../../service/DataService";

    export default {
        props: ['processingId'],
        data() {
            return {
                formClient: {
                    id: null,
                    firstName: null,
                    secondName: null,
                    address: null,
                    ridesAmount: null
                },
                resource: "clients"
            }
        },
        methods: {
            loadValues() {
                this.$nextTick(() => {
                    DataService.retrieveRecord(this.resource, this.processingId).then(response => {
                        this.formClient.firstName = response.data.firstName;
                        this.formClient.secondName = response.data.secondName;
                        this.formClient.address = response.data.address;
                        this.formClient.ridesAmount = response.data.ridesAmount;
                    }).catch(error => {
                        console.log(error);
                        if (error.response.status !== 404)
                            this.$emit('addError', `Модельне вікно спричинило помилку ${error}`);
                    });
                    this.formClient.id = this.processingId;
                })
            },
            resetForm() {
                this.formClient = {
                    id: null,
                    firstName: null,
                    secondName: null,
                    address: null,
                    ridesAmount: null
                }
            },
            handleSubmit() {
                if (this.formClient.id < 0)
                    this.$emit('addClient', this.formClient);
                else
                    this.$emit('updateClient', this.formClient);
            }
        },
        computed: {
            actionMessage() {
                return this.processingId <= 0 ? 'Додайте нового клієнта' : 'Змініть клієнта';
            },
            action() {
                return this.processingId <= 0 ? 'Додати' : 'Змінити';
            }
        }
    }
</script>