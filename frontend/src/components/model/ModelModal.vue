<template>
    <div>
        <b-modal id="modelModal" no-close-on-backdrop
                 @show="loadValues" @hidden="resetForm">
            <template v-slot:modal-title>
                <h3>{{ actionMessage }}</h3>
            </template>

            <template v-slot:default>
                <ValidationObserver>
                    <b-form slot-scope="{ validate }" @submit.prevent="validate().then(handleSubmit)" id="modelForm">
                        <ValidationProvider rules="required|alpha_spaces|max:150" name="бренду">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Бренд">
                                    <b-form-input
                                            type="text"
                                            v-model="formModel.brand"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|max:150" name="моделі">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Модель">
                                    <b-form-input
                                            type="text"
                                            v-model="formModel.model"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider rules="required|numeric|min_value:2008|max_value:2020" name="року">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Рік">
                                    <b-form-input
                                            type="text"
                                            v-model="formModel.year"
                                            :state="errors[0] ? false : (valid ? true : null)">
                                    </b-form-input>
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                            </b-form-group>
                        </ValidationProvider>

                        <ValidationProvider :rules="photoUploadRules" name="з фото моделі">
                            <b-form-group slot-scope="{ valid, errors }">
                                <b-input-group prepend="Фото моделі">
                                    <b-form-file class="text-nowrap text-truncate"
                                                 v-model="modelImage"
                                                 placeholder=""
                                                 drop-placeholder="Перетягніть файл сюди..."
                                                 accept=".jpg, .png, .gif, .bmp, .jpeg"
                                                 no-drop
                                                 :state="errors[0] ? false : (valid ? true : null)"
                                                 @change="onPhotoLoad"
                                    />
                                    <b-form-invalid-feedback>
                                        {{ errors[0] }}
                                    </b-form-invalid-feedback>
                                </b-input-group>
                                <b-img v-if="previewImage" v-bind="previewImagePattern" :src="previewImage"
                                       rounded alt="Upload preview"></b-img>
                            </b-form-group>
                        </ValidationProvider>
                    </b-form>
                </ValidationObserver>
            </template>

            <template v-slot:modal-footer="{ cancel }">
                <b-button variant="dark" @click="cancel()">
                    Скасувати
                </b-button>
                <b-button type="submit" form="modelForm" variant="danger">
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
                formModel: {
                    id: null,
                    brand: null,
                    model: null,
                    year: null
                },
                modelImage: null,
                previewImage: null,
                previewImagePattern: {
                    center: true,
                    fluidGrow: true,
                    width: 770,
                    height: 570,
                    class: 'mt-3'
                },
                resource: "models"
            }
        },
        methods: {
            loadValues() {
                this.$nextTick(() => {
                    DataService.retrieveRecord(this.resource, this.processingId).then(response => {
                        this.formModel.brand = response.data.brand;
                        this.formModel.model = response.data.model;
                        this.formModel.year = response.data.year;
                    }).catch(error => {
                        console.log(error);
                        if (error.response.status !== 404)
                            this.$emit('addError', `On editing of ${this.id} occurred ${error}`);
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
                this.modelImage = null;
                this.previewImage = null;
            },
            handleSubmit() {
                const form = new FormData();
                form.append('model', JSON.stringify(this.formModel));
                form.append('modelImage', this.modelImage);
                if (this.formModel.id < 0)
                    this.$emit('addModel', form);
                else
                    this.$emit('updateModel', form);
            },
            onPhotoLoad(e) {
                const image = e.target.files[0];
                this.previewImage = URL.createObjectURL(image);
            }
        },
        computed: {
            actionMessage() {
                return this.processingId <= 0 ? 'Додайте нову модель авто' : 'Змініть модель авто';
            },
            action() {
                return this.processingId <= 0 ? 'Додати' : 'Змінити';
            },
            photoUploadRules() {
                return this.processingId <= 0 ? 'required|image' : 'image';
            }
        }
    }
</script>

<style lang="css">
    @import "../../styles/uploadBadge.css";
</style>