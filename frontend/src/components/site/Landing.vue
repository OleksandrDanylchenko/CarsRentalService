<template>
  <div>
    <div>
      <p class="display-4 text-center fadeInUp" v-wow>Популярний вибір:</p>
      <b-card-group columns>
        <div v-for="car in popularCars" :key="car.id">
          <div v-if="car.model.imageName != null">
            <b-card
              :img-src="require('@/assets/modelsPhotos/' + car.model.imageName)"
              :img-alt="car.model.model + ' photo'"
              img-top
              class="fadeInUp"
              v-wow
            >
              <b-card-title class="mb-0">
                <div class="d-flex justify-content-between align-items-center">
                  <div>
                    <h2>{{ car.model.brand + " " + car.model.model }}</h2>
                    <h4>{{ car.model.year }}</h4>
                    <h5>{{ car.model.type }}</h5>
                  </div>
                  <b-button variant="danger">
                    <div class="text-left" v-resize-text>Від</div>
                    <div class="d-flex align-items-baseline text-left">
                      <h4>₴{{ car.dayPrice }}</h4>
                      <div class="my-0">/день</div>
                    </div>
                  </b-button>
                </div>
              </b-card-title>
            </b-card>
          </div>
        </div>
      </b-card-group>


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
    };
  },
  methods: {
    loadCars() {
      DataService.retrieveAllRecords(this.carsResource)
        .then((response) => {
          this.popularCars = this._.uniqBy(response.data, "model.model").slice(
            0,
            3
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

@media (min-width: 1px) {
  .card-columns {
    column-count: 1;
  }
}

@media (min-width: 1000px) {
  .card-columns {
    column-count: 2;
  }
}

@media (min-width: 1505px) {
  .card-columns {
    column-count: 3;
  }
}
</style>
