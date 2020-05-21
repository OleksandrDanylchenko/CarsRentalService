<template>
  <div>
    <ClientNavbar class="fadeInDown" v-wow data-wow-delay="0.2s" />
    <p class="display-4 text-center fadeInUp" v-wow data-wow-delay="0.2s">
      Пошук авто:
    </p>
    <div class="row">
      <div class="col-2">
        <b-card class="fadeInUp" v-wow data-wow-delay="0.2s">
          <b-card-text>
            <h4>Бренд:</h4>
            <v-select
              multiple
              v-model="modelFilter.brand"
              :options="brands"
              label="text"
              :reduce="(model) => model.value"
              @input="filterCars"
              class="style-chooser mb-3"
            >
              <div slot="no-options">Бренд відсутній</div>
            </v-select>

            <h4>Модель:</h4>
            <v-select
              multiple
              v-model="modelFilter.model"
              :options="models"
              label="text"
              :reduce="(model) => model.value"
              @input="filterCars"
              class="style-chooser mb-3"
            >
              <div slot="no-options">Модель відсутня</div>
            </v-select>

            <h4 class="fadeInUp" v-wow data-wow-delay="0.2s">Модельний рік:</h4>
            <b-input-group
              class="fadeInUp rounded border border-danger mb-3"
              v-wow
              data-wow-delay="0.2s"
            >
              <b-form-select
                v-model="modelFilter.year.min"
                :options="years"
                class="filterDropDown"
                @change="filterCars"
              >
                <template v-slot:first>
                  <b-form-select-option :value="null">
                    Від
                  </b-form-select-option>
                </template>
              </b-form-select>
              <b-form-select
                v-model="modelFilter.year.max"
                :options="years"
                class="filterDropDown"
                @change="filterCars"
              >
                <template v-slot:first>
                  <b-form-select-option :value="null">
                    До
                  </b-form-select-option>
                </template>
              </b-form-select>
            </b-input-group>

            <h4 class="fadeInUp" v-wow data-wow-delay="0.2s">Тип авто:</h4>
            <v-select
              multiple
              v-model="modelFilter.type"
              :options="types"
              label="text"
              :reduce="(model) => model.value"
              @input="filterCars"
              class="style-chooser mb-3 fadeInUp"
              v-wow
              data-wow-delay="0.2s"
            >
              <div slot="no-options">Тип авто відсутній</div>
            </v-select>

            <h4 class="fadeInUp" v-wow data-wow-delay="0.2s">Тип пального:</h4>
            <v-select
              multiple
              v-model="specFilter.fuelType"
              :options="fuelTypes"
              label="text"
              :reduce="(spec) => spec.value"
              @input="filterCars"
              class="style-chooser mb-3 fadeInUp"
              v-wow
              data-wow-delay="0.2s"
            >
              <div slot="no-options">Тип пального відсутній</div>
            </v-select>

            <h4 class="fadeInUp" v-wow data-wow-delay="0.2s">Тип КПП:</h4>
            <v-select
              multiple
              v-model="specFilter.transmissionType"
              :options="transmissionTypes"
              label="text"
              :reduce="(spec) => spec.value"
              @input="filterCars"
              class="style-chooser mb-3 fadeInUp"
              v-wow
              data-wow-delay="0.2s"
            >
              <div slot="no-options">Тип КПП відсутній</div>
            </v-select>
          </b-card-text>
        </b-card>
      </div>
      <div class="col-auto">
        <div v-for="car in filteredCars" :key="car.id">
          <div v-if="car.model.imageName != null && car.rentable">
            <b-card
              :img-src="require('@/assets/modelsPhotos/' + car.model.imageName)"
              :img-alt="car.model.model + ' photo'"
              img-left
              img-width="550px"
              class="mb-3 fadeInUp mw-100"
              data-wow-delay="0.2s"
              v-wow
            >
              <b-card-title>
                <div class="d-flex justify-content-between align-items-center">
                  <h2>{{ car.model.brand + " " + car.model.model }}</h2>
                </div>
                <div class="d-flex justify-content-between">
                  <div class="d-flex align-items-end">
                    <h4>{{ car.model.year }}</h4>
                    <h4 class="ml-2">{{ car.model.type }}</h4>
                  </div>
                  <h4 class="text-warning">{{ car.modelClass.name }}</h4>
                </div>
              </b-card-title>
              <b-card-text>
                <b-card-text>
                  <h4 class="">Характеристики:</h4>
                  <div class="row">
                    <div class="col-2">
                      <div class="d-flex align-items-end mb-3">
                        <img
                          src="../../assets/specsIcons/engine.svg"
                          alt=""
                          width="35px"
                        />
                        <h5 class="ml-2">
                          {{ car.specification.engineCapacity }}л.
                        </h5>
                      </div>
                      <div class="d-flex align-items-start">
                        <img
                          src="../../assets/specsIcons/turbine.svg"
                          alt=""
                          width="35px"
                        />
                        <h5 class="ml-2">
                          {{ car.specification.horsepowers }} к.с.
                        </h5>
                      </div>
                    </div>
                    <div class="col-4 pr-0">
                      <div class="d-flex align-items-end mb-3">
                        <img
                          src="../../assets/specsIcons/fuel_type.svg"
                          alt=""
                          width="35px"
                        />
                        <h5 class="ml-2">{{ car.specification.fuelType }}</h5>
                      </div>
                      <div class="d-flex align-items-end">
                        <img
                          src="../../assets/specsIcons/fuel_consumption.svg"
                          alt=""
                          width="35px"
                        />
                        <h5 class="ml-2">
                          {{ car.specification.fuelConsumption }} л./100км.
                        </h5>
                      </div>
                    </div>
                    <div class="col-4 d-flex align-items-end pl-0">
                      <div class="d-flex align-items-end mb-3">
                        <img
                          src="../../assets/specsIcons/transmission.svg"
                          alt=""
                          width="35px"
                        />
                        <h5 class="ml-2">
                          {{ car.specification.transmissionType }}
                        </h5>
                      </div>
                    </div>
                    <div
                      class="col-2 d-flex justify-content-end align-items-center"
                    >
                      <b-button variant="danger">
                        <div class="d-flex align-items-baseline text-left p-2">
                          <h4 class="m-0">₴{{ car.dayPrice }}</h4>
                          <div class="my-0">/день</div>
                        </div>
                      </b-button>
                    </div>
                  </div>
                </b-card-text>
              </b-card-text>
            </b-card>
          </div>
        </div>
      </div>
    </div>
    <Footer class="fadeInUp" v-wow data-wow-delay="0.2s" />
  </div>
</template>

<script>
  import DataService from '../../service/DataService'
  import ClientNavbar from './common/ClientNavbar'
  import Footer from './common/Footer'
  import _ from 'lodash'

  export default {
  name: "CarSelectComponents",
  components: {
    ClientNavbar,
    Footer,
  },
  data() {
    return {
      carsResource: "cars",
      cars: [],
      filteredCars: [],

      brands: [],
      models: [],
      types: [],
      fuelTypes: [],
      transmissionTypes: [],

      modelFilter: {
        brand: [],
        model: [],
        year: {
          min: null,
          max: null,
        },
        type: [],
      },
      specFilter: {
        engineCapacity: {
          min: null,
          max: null,
        },
        horsepowers: {
          min: null,
          max: null,
        },
        fuelType: [],
        transmissionTypes: [],
      },
    };
  },
  methods: {
    loadCars() {
      DataService.retrieveAllRecords(this.carsResource)
        .then((response) => {
          this.filteredCars = this.cars = response.data.filter(
            (car) => car.rentable
          );
          this.parseBrands();
          this.parseModels();
          this.parseTypes();
          this.parseFuelTypes();
          this.parseTransmissionTypes();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    parseBrands() {
      this.cars.forEach((car) => {
        const brandOption = {
          value: car.model.brand,
          text: car.model.brand,
        };
        if (this.brands.every((type) => type.value !== brandOption.value)) {
          this.brands.push(brandOption);
        }
      });
      this.brands = this.alphabetSort(this.brands);
    },
    parseModels() {
      this.cars.forEach((car) => {
        const modelOption = {
          value: car.model.model,
          text: _.truncate(car.model.model, {
            length: 20,
          }),
        };
        if (this.models.every((type) => type.value !== modelOption.value)) {
          this.models.push(modelOption);
        }
      });
      this.models = this.alphabetSort(this.models);
    },
    parseTypes() {
      this.cars.forEach((car) => {
        const typeOption = {
          value: car.model.type,
          text: _.truncate(car.model.type, {
            length: 20,
          }),
        };
        if (this.types.every((type) => type.value !== typeOption.value)) {
          this.types.push(typeOption);
        }
      });
      this.types = this.alphabetSort(this.types);
    },
    parseFuelTypes() {
      this.cars.forEach((car) => {
        const typeOption = {
          value: car.specification.fuelType,
          text: car.specification.fuelType,
        };
        if (this.fuelTypes.every((type) => type.value !== typeOption.value)) {
          this.fuelTypes.push(typeOption);
        }
      });
      this.fuelTypes = this.alphabetSort(this.fuelTypes);
    },
    parseTransmissionTypes() {
      this.cars.forEach((car) => {
        const transmissionOption = {
          value: car.specification.transmissionType,
          text: car.specification.transmissionType,
        };
        if (
          this.transmissionTypes.every(
            (type) => type.value !== transmissionOption.value
          )
        ) {
          this.transmissionTypes.push(transmissionOption);
        }
      });
      this.transmissionTypes = this.alphabetSort(this.transmissionTypes);
    },
    alphabetSort(array) {
      return array.sort((a, b) => a.text.localeCompare(b.text));
    },
    filterCars() {
      this.$nextTick(() => {
        const modelMinMaxFields = ["year"];
        const specMinMaxFields = ["engineCapacity", "horsepowers"];

        let modelQuery = this.buildFilter(this.modelFilter);
        let specQuery = this.buildFilter(this.specFilter);
        this.filteredCars = this.cars
          .filter((car) => {
            for (let key in modelQuery) {
              if (car.model[key] === undefined) {
                return false;
              } else if (modelMinMaxFields.includes(key)) {
                if (
                  modelQuery[key]["min"] !== null &&
                  car.model[key] < modelQuery[key]["min"]
                ) {
                  return false;
                }
                if (
                  modelQuery[key]["max"] !== null &&
                  car.model[key] > modelQuery[key]["max"]
                ) {
                  return false;
                }
              } else if (!modelQuery[key].includes(car.model[key])) {
                return false;
              }
            }
            return true;
          })
          .filter((car) => {
            for (let key in specQuery) {
              if (car.specification[key] === undefined) {
                return false;
              } else if (specMinMaxFields.includes(key)) {
                if (
                  specQuery[key]["min"] !== null &&
                  car.specification[key] < specQuery[key]["min"]
                ) {
                  return false;
                }
                if (
                  specQuery[key]["max"] !== null &&
                  car.specification[key] > specQuery[key]["max"]
                ) {
                  return false;
                }
              } else if (!specQuery[key].includes(car.specification[key])) {
                return false;
              }
            }
            return true;
          });
      }, 0);
    },
    buildFilter(filter) {
      let query = {};
      for (let keys in filter) {
        if (
          filter[keys].constructor === Object ||
          (filter[keys].constructor === Array && filter[keys].length > 0)
        ) {
          query[keys] = filter[keys];
        }
      }
      return query;
    },
  },
  computed: {
    years() {
      const year = new Date().getFullYear();
      return Array.from(
        { length: year - 2000 },
        (value, index) => 2001 + index
      ).reverse();
    },
  },
  created() {
    this.loadCars();
  },
};
</script>

<style lang="css">
@import "../../styles/main.css";
@import "../../styles/animate.css";

.style-chooser .vs__search::placeholder,
.style-chooser .vs__dropdown-toggle {
  background: #3c3d3d;
  border: solid #e74c3c 0.1px;
}

.style-chooser .vs__search {
  color: white;
  font-size: 1.1em;
}

.style-chooser .vs__dropdown-option--highlight {
  background: #e74c3c;
}

.style-chooser .vs__dropdown-menu {
  background: #a7a7a7;
  border: solid #e74c3c 0.1px;
  font-size: 1.1em;
  color: #23221c;
  text-align: center;
}

.style-chooser .vs__selected {
  background: #a7a7a7;
  color: #23221c;
}

.style-chooser .vs__clear,
.style-chooser .vs__open-indicator {
  fill: #e74c3c;
}

.filterDropDown {
  background: #3c3d3d;
  color: white;
}
</style>
