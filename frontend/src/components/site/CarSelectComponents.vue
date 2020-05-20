<template>
  <div>
    <ClientNavbar class="fadeInDown" v-wow data-wow-delay="0.2s" />
    <p class="display-4 text-center fadeInUp" v-wow data-wow-delay="0.2s">
      Пошук авто:
    </p>
    <div v-for="car in cars" :key="car.id">
      <div v-if="car.model.imageName != null">
        <b-card
          :img-src="require('@/assets/modelsPhotos/' + car.model.imageName)"
          :img-alt="car.model.model + ' photo'"
          img-left
          img-width="600px"
          class="mb-3 fadeInUp mw-100"
          data-wow-delay="0.2s"
          v-wow
        >
          <b-card-title>
            <div class="d-flex justify-content-between align-items-center">
              <h2>{{ car.model.brand + " " + car.model.model }}</h2>
              <h3 class="text-warning">{{ car.modelClass.name }}</h3>
            </div>
            <div class="d-flex align-items-center p-0 m-0">
              <h4>{{ car.model.year }}</h4>
              <h4 class="ml-2">{{ car.model.type }}</h4>
            </div>
          </b-card-title>
          <b-card-text>
            <b-card-text>
              <h4 class="">Характеристики:</h4>
              <div class="row">
                <div class="col-2">
                  <div class="d-flex align-items-center mb-3">
                    <img
                      src="../../assets/specsIcons/engine.svg"
                      alt=""
                      width="40px"
                    />
                    <h5 class="ml-2">
                      {{ car.specification.engineCapacity }} л.
                    </h5>
                  </div>
                  <div class="d-flex align-items-center">
                    <img
                      src="../../assets/specsIcons/turbine.svg"
                      alt=""
                      width="40px"
                    />
                    <h5 class="ml-2">
                      {{ car.specification.horsepowers }} к.с.
                    </h5>
                  </div>
                </div>
                <div class="col-2">
                  <div class="d-flex align-items-center mb-3">
                    <img
                      src="../../assets/specsIcons/fuel_type.svg"
                      alt=""
                      width="40px"
                    />
                    <h5 class="ml-2">{{ car.specification.fuelType }}</h5>
                  </div>
                  <div class="d-flex align-items-center">
                    <img
                      src="../../assets/specsIcons/fuel_consumption.svg"
                      alt=""
                      width="40px"
                    />
                    <h5 class="ml-2">
                      {{ car.specification.fuelConsumption }} к.с.
                    </h5>
                  </div>
                </div>
                <div class="col-2 d-flex align-items-center">
                  <div class="d-flex align-items-center mb-3">
                    <img
                      src="../../assets/specsIcons/transmission.svg"
                      alt=""
                      width="40px"
                    />
                    <h5 class="ml-2">
                      {{ car.specification.transmissionType }}
                    </h5>
                  </div>
                </div>
                <div
                  class="col-6 d-flex justify-content-end align-items-center"
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
      cars: [],
      carsResource: "cars",
    };
  },
  methods: {
    loadCars() {
      DataService.retrieveAllRecords(this.carsResource)
        .then((response) => {
          this.cars = response.data;
        })
        .catch((error) => {
          console.log(error);
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
