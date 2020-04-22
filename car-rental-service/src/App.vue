<template>
    <div id="app">
        <link rel="stylesheet"
              href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
              integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
              crossorigin="anonymous">
        <div class="container">
            <router-view/>
        </div>
    </div>
</template>

<script>
    export default {
        name: "app"
    };
</script>