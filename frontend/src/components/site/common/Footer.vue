<template>
  <footer class="footer">
    <div class="row mt-5 mb-3">
      <div class="col mx-5">
        <hr class="bg-light" />
        <div class="d-flex justify-content-between align-items-center">
          <b-link to="/">
            <img
              src="https://i.ibb.co/z4x4vWg/Asset-3.png"
              alt=""
              width="150px"
              class="d-block"
            />
          </b-link>
          <div>Copyright © Alexander Danylchenko 2020</div>
          <b-link
            href="https://github.com/OlexanderD/CarsRentalService"
            class="text-danger text-decoration-none"
            target="_blank"
          >
            <div class="d-flex align-items-center">
              <i class="fab fa-github fa-3x mr-3" /><span>OlexanderD</span>
            </div>
          </b-link>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "Footer",
};
</script>
