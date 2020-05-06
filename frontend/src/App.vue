<template>
    <div id="app">
        <link rel="stylesheet"
              href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
              integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
              crossorigin="anonymous">
        <b-container fluid class="px-5">
            <b-navbar type="light" id="contactsBar">
                <b-navbar-nav>
                    <b-nav-item class="mr-md-5 mr-sm-4" href="https://bit.ly/3dgM5zu" target="_blank">
                        <i class="fab fa-facebook-f fa-lg"/>
                    </b-nav-item>
                    <b-nav-item class="mr-md-5 mr-sm-4" href="https://bit.ly/3b6mh80" target="_blank">
                        <i class="fab fa-instagram fa-lg"/>
                    </b-nav-item>
                    <b-nav-item href="https://github.com/OlexanderD" target="_blank">
                        <i class="fab fa-github fa-lg"/>
                    </b-nav-item>
                </b-navbar-nav>

                <b-navbar-nav class="ml-auto">
                    <b-nav-item-dropdown right no-caret>
                        <template slot="button-content">
                            <i class="fas fa-phone fa-lg"/>
                        </template>
                        <b-dropdown-item href="tel:+380967994839">+380967994839</b-dropdown-item>
                        <b-dropdown-item href="tel:+380957996239">+380957996239</b-dropdown-item>
                        <b-dropdown-item href="tel:+380977997839">+380977997839</b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown class="ml-md-5 ml-sm-4" right no-caret>
                        <template slot="button-content">
                            <i class="fas fa-envelope fa-lg"/>
                        </template>
                        <b-dropdown-item href="mailto:olexander.danilchenko@gmail.com">
                            olexander.danilchenko@gmail.com
                        </b-dropdown-item>
                        <b-dropdown-item href="mailto:alexrentsmail@gmail.com">
                            alexrentsmail@gmail.com
                        </b-dropdown-item>
                    </b-nav-item-dropdown>

                    <b-nav-item-dropdown class="ml-md-5 ml-sm-4" right no-caret>
                        <template slot="button-content">
                            <i class="fas fa-map-marker-alt fa-lg"></i>
                        </template>
                        <b-dropdown-item v-for="center in rentCenters" :key="center.id"
                                         :href="'https://www.google.com/maps/search/?api=1&query=' + center.encodedAddress"
                                         target="_blank">
                            {{ center.address }}
                        </b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
            </b-navbar>
            <!--            <b-navbar variant="faded" type="light">-->
            <!--                <b-navbar-brand href="http://localhost:8081/">Alex Cars Rental</b-navbar-brand>-->
            <!--            </b-navbar>-->

            <router-view/>
        </b-container>
    </div>
</template>

<script>
    import DataService from "./service/DataService";

    export default {
        name: "App",
        data() {
            return {
                rentCenters: [],
                centersResource: "rent_centers"
            }
        },
        methods: {
            loadRentCenters() {
                DataService.retrieveAllRecords(this.centersResource).then(response => {
                    response.data.forEach(record => {
                        let center = {
                            id: record.id,
                            address: record.address,
                            encodedAddress: encodeURI(record.address)
                        };
                        this.rentCenters.push(center);
                    })
                }).catch(error => {
                    console.log(error);
                    this.rentCenters.push("Список центрів оренди невдовзі оновиться. " +
                        "Для подальшої довідки зв'яжіться з нами через вказані номери телефонів чи електронну пошту");
                });
            }
        },
        created() {
            this.loadRentCenters();
        }
    };
</script>