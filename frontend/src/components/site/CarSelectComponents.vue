<template>
  <div>
    <ClientNavbar class="fadeInDown" v-wow data-wow-delay="0.2s" />
    <p class="display-4 text-center fadeInUp" v-wow data-wow-delay="0.2s">
      Пошук авто:
    </p>
    <div class="row">
      <div class="col-3">
        <b-card class="fadeInUp" v-wow data-wow-delay="0.2s">
          <b-card-text>
            <b-form-group class="fadeInUp" v-wow data-wow-delay="0.2s">
              <template v-slot:label>
                <h4>Бренд:</h4>
              </template>
              <b-form-checkbox-group
                v-model="modelFilter.brand"
                :options="brands"
                size="lg"
                stacked
                @change="filterCars"
              />
            </b-form-group>

            <b-form-group class="fadeInUp" v-wow data-wow-delay="0.2s">
              <template v-slot:label>
                <h4>Тип пального:</h4>
              </template>
              <b-form-checkbox-group
                v-model="specFilter.fuelType"
                :options="fuelTypes"
                size="lg"
                stacked
                @change="filterCars"
              />
            </b-form-group>
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
      fuelTypes: [],
      transmissionTypes: [],

      modelFilter: {
        brand: [],
        model: [],
        year: [],
        type: [],
      },
      specFilter: {
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
        let modelQuery = this.buildFilter(this.modelFilter);
        let specQuery = this.buildFilter(this.specFilter);
        this.filteredCars = this.cars
          .filter((car) => {
            for (let key in modelQuery) {
              if (
                car.model[key] === undefined ||
                !modelQuery[key].includes(car.model[key])
              ) {
                return false;
              }
            }
            return true;
          })
          .filter((car) => {
            for (let key in specQuery) {
              if (
                car.specification[key] === undefined ||
                !specQuery[key].includes(car.specification[key])
              ) {
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
        if (Array.isArray(filter[keys]) && filter[keys].length > 0) {
          query[keys] = filter[keys];
        }
      }
      return query;
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
</style>
