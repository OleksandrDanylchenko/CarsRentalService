<template>
  <div>
    <ClientNavbar class="fadeInDown" v-wow />
    <div class="row">
      <div class="col text-center">
        <b-spinner
          variant="danger"
          style="width: 30rem; height: 30rem;"
          class="mb-3"
        />
        <div class="display-4 fadeInUp" v-wow>
          Даний розділ знаходиться у розробці.<br />Зверніться до адміністратора
        </div>
      </div>
    </div>
    <Footer class="fadeInUp" v-wow data-wow-delay="0.2s" />
  </div>
</template>

<script>
  import ClientNavbar from './ClientNavbar'
  import Footer from './Footer'

  export default {
  name: "MaintenancePage",
  components: {
    ClientNavbar,
    Footer,
  },
};
</script>

<style lang="css">
@import "../../../styles/main.css";
@import "../../../styles/animate.css";
</style>
