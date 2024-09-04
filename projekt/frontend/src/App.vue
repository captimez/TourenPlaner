<script setup lang="ts">
import { onMounted, ref, type Ref } from 'vue';
import { useInfo } from './composables/useInfo'
import { RouterLink, RouterView } from 'vue-router';



const { loescheInfo, setzeInfo, info } = useInfo()

const loeschen=() =>{
  loescheInfo()
}
const hello = () => {
  setzeInfo("Das ist ein Composable")
}


</script>

<template>
  <head>
        <link href="./assets/main.css" th:rel="stylesheet" />
  </head>
  

  <header  class="header-container">
        <div class="header-links">
            <a >Benutzer</a>
            <a >Orte</a>
            <RouterLink to="/tour">Touren</RouterLink>
        </div>
        <div class="language-links">
            <a>DE</a>
            <a>EN</a> 
            <a>NL</a>
        </div>
    </header>

    <div class="info" v-if="info !='' ">
      <span  >{{info}}</span>
      <button v-on:click="loeschen" class="close-btn">&times;</button>
    </div>
    <button v-on:click="hello">Hello</button>


    <router-view></router-view>
  

    <footer>
        <a >Impressum</a>
        <a >Kontakt</a>
    </footer>
</template>


