<template>
  <div>
    <ClientNavbar class="fadeInDown" v-wow data-wow-delay="0.2s" />
    <p class="display-4 text-center fadeInUp" v-wow data-wow-delay="0.2s">
      Пошук авто:
    </p>
    <div class="row">
      <div class="col-3">
        <b-card>
          <b-card-text> </b-card-text>
        </b-card>
      </div>
      <div class="col-auto">
        <div v-for="car in cars" :key="car.id">
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
  // import _ from "lodash";

export default {
  name: "CarSelectComponents",
  components: {
    ClientNavbar,
    Footer,
  },
  data() {
    return {
      cars: [],
      filteredCars: [],
      fuelTypes: [],
      transmissionTypes: [],
      carsResource: "cars",
    };
  },
  methods: {
    loadCars() {
      DataService.retrieveAllRecords(this.carsResource)
        .then((response) => {
          this.cars = response.data;
          this.parseFuelTypes();
          this.parseTransmissionTypes();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    parseFuelTypes() {
      this.cars
        .filter((car) => car.rentable)
        .forEach((car) => {
          const typeOption = {
            value: car.specification.fuelType,
            text: car.specification.fuelType,
          };
          if (this.fuelTypes.every((type) => type.value !== typeOption.value)) {
            this.fuelTypes.push(typeOption);
          }
        });
    },
    parseTransmissionTypes() {
      this.cars
        .filter((car) => car.rentable)
        .forEach((car) => {
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
