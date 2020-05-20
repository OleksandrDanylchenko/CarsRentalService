<template>
  <div>
    <b-navbar class="pb-0" type="dark" id="contactsBar">
      <b-navbar-nav>
        <b-nav-item
          class="mr-md-5 mr-sm-4"
          href="https://bit.ly/3dgM5zu"
          target="_blank"
        >
          <i class="fab fa-facebook-f fa-lg" />
        </b-nav-item>
        <b-nav-item
          class="mr-md-5 mr-sm-4"
          href="https://bit.ly/3b6mh80"
          target="_blank"
        >
          <i class="fab fa-instagram fa-lg" />
        </b-nav-item>
        <b-nav-item href="https://github.com/OlexanderD" target="_blank">
          <i class="fab fa-github fa-lg" />
        </b-nav-item>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <b-nav-item-dropdown right no-caret>
          <template v-slot:button-content>
            <i class="fas fa-phone fa-lg" />
          </template>
          <b-dropdown-item href="tel:+380967994839"
            >+380967994839</b-dropdown-item
          >
          <b-dropdown-item href="tel:+380957996239"
            >+380957996239</b-dropdown-item
          >
          <b-dropdown-item href="tel:+380977997839"
            >+380977997839</b-dropdown-item
          >
        </b-nav-item-dropdown>

        <b-nav-item-dropdown class="ml-md-5 ml-sm-4" right no-caret>
          <template v-slot:button-content>
            <i class="fas fa-envelope fa-lg" />
          </template>
          <b-dropdown-item href="mailto:olexander.danilchenko@gmail.com">
            olexander.danilchenko@gmail.com
          </b-dropdown-item>
          <b-dropdown-item href="mailto:alexrentsmail@gmail.com">
            alexrentsmail@gmail.com
          </b-dropdown-item>
        </b-nav-item-dropdown>

        <b-nav-item-dropdown class="ml-md-5 ml-sm-4" right no-caret>
          <template v-slot:button-content>
            <i class="fas fa-map-marker-alt fa-lg"></i>
          </template>
          <b-dropdown-item
            v-for="center in rentCenters"
            :key="center.id"
            :href="
              'https://www.google.com/maps/search/?api=1&query=' +
              center.encodedAddress
            "
            target="_blank"
          >
            {{ center.address }}
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-navbar>

    <hr class="my-0" />

    <b-navbar class="pt-0 mb-2" toggleable="md" type="dark">
      <b-navbar-brand @click="returnHome">
        <b-img
          src="https://bit.ly/2Ww22LJ"
          fluid
          alt="Alex Rentals"
          width="130px"
        />
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav class="ml-auto">
          <div
            class="d-flex justify-content-between align-items-center mt-sm-0 mt-3"
          >
            <b-nav-item @click="returnHome">
              <i class="fas fa-home fa-3x" />
            </b-nav-item>

            <b-nav-item-dropdown
              class="ml-xl-5 ml-lg-4 ml-md-3 ml-md-2 ml-0"
              right
              no-caret
            >
              <template v-slot:button-content>
                <i class="fas fa-car fa-3x" />
              </template>
              <b-dropdown-item href="#">Список авто</b-dropdown-item>
              <b-dropdown-item href="#">Знайти авто</b-dropdown-item>
              <b-dropdown-item href="#">Випадкове авто</b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item-dropdown
              class="ml-xl-5 ml-lg-4 ml-md-3 ml-md-2 ml-0"
              right
              no-caret
            >
              <template v-slot:button-content>
                <i class="fas fa-question-circle fa-3x" />
              </template>
              <b-dropdown-item href="#">Про нас</b-dropdown-item>
              <b-dropdown-item href="#">FAQ</b-dropdown-item>
            </b-nav-item-dropdown>

            <b-nav-item
              class="ml-xl-5 ml-lg-4 ml-md-3 ml-md-2 ml-0"
              @click="goContacts"
            >
              <i class="fas fa-info-circle fa-3x" />
            </b-nav-item>
          </div>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
  import DataService from '../../service/DataService'

  export default {
  name: "Navbar",
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
    returnHome() {
      this.$emit("returnHome");
    },
    goContacts() {
      this.$emit("goContacts");
    },
  },
  created() {
    this.loadRentCenters();
  },
};
</script>
