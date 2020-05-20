<template>
  <div id="app">
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
      integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
      crossorigin="anonymous"
    />
    <b-container fluid class="px-5">
      <router-view />
    </b-container>
  </div>
</template>

<script>
  import DataService from './service/DataService'

  export default {
  name: "App",
  data() {
    return {
      rentCenters: [],
      centersResource: "rent_centers",
    };
  },
  methods: {
    loadRentCenters() {
      DataService.retrieveAllRecords(this.centersResource)
        .then((response) => {
          response.data.forEach((record) => {
            let center = {
              id: record.id,
              address: record.address,
              encodedAddress: encodeURI(record.address),
            };
            this.rentCenters.push(center);
          });
        })
        .catch((error) => {
          console.log(error);
          this.rentCenters.push(
            "Список центрів оренди невдовзі оновиться. " +
              "Для подальшої довідки зв'яжіться з нами через вказані номери телефонів чи електронну пошту"
          );
        });
    },
  },
  created() {
    this.loadRentCenters();
  },
};
</script>
