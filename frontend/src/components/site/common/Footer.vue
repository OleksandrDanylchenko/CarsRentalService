<template>
  <footer class="footer">
    <div class="row">
      <div class="col">
        <h4>Про нас</h4>
        <b-navbar-brand @click="returnHome">
          <b-img
            src="https://bit.ly/2Ww22LJ"
            fluid
            alt="Alex Rentals"
            width="130px"
          />
        </b-navbar-brand>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "Footer",
};
</script>
