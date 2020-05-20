<template>
  <div>
    <p class="display-4 text-center fadeInUp" v-wow>Популярний вибір:</p>
    <b-card-group deck>
      <div class="d-flex justify-content-center flex-wrap">
        <div v-for="car in popularCars" :key="car.id">
          <div v-if="car.model.imageName != null">
            <b-card
              :img-src="require('@/assets/modelsPhotos/' + car.model.imageName)"
              :img-alt="car.model.model + ' photo'"
              img-top
              class="fadeInUp mb-3"
              v-wow
            >
              <b-card-title>
                <h3>{{ car.model.brand + " " + car.model.model }}</h3>
              </b-card-title>
              <b-card-text>
                <div class="d-flex justify-content-between align-items-center">
                  <div>
                    <h4>{{ car.model.year }}</h4>
                    <h4>{{ car.model.type }}</h4>
                  </div>
                  <b-button variant="danger">
                    <div class="text-left">Від</div>
                    <div class="d-flex align-items-baseline text-left">
                      <h4>₴{{ car.dayPrice }}</h4>
                      <div class="my-0">/день</div>
                    </div>
                  </b-button>
                </div>
              </b-card-text>
            </b-card>
          </div>
        </div>
      </div>
    </b-card-group>

    <div>

    </div>
  </div>
</template>

<script>
  import DataService from '../../service/DataService'

  export default {
  name: "Landing",
  data() {
    return {
      popularCars: [],
      carsResource: "cars",
      cardGroupKey: 0,
    };
  },
  methods: {
    loadCars() {
      DataService.retrieveAllRecords(this.carsResource)
        .then((response) => {
          this.popularCars = this._.uniqBy(response.data, "model.model").slice(
            0,
            6
          );
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

.card {
  max-width: 500px;
}

.features-list > * > li::before {
  content: "";
  display: inline-block;
  width: 30px;
  height: 30px;
  vertical-align: middle;
  background-image: url(https://svgshare.com/i/LKE.svg);
  background-repeat: no-repeat;
  background-size: 100%;
  margin-right: 15px;
}
</style>
