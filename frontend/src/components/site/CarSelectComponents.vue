<template>
    <div>
        <ClientNavbar />
        <Footer />
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
    Footer
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
