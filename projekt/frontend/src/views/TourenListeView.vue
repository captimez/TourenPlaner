<template>
  <h1 class="title">Das aktuelle Mitfahrangebot</h1>
  <div class="Suchfeld">
    <input type="text" v-model="suche" placeholder="Suche..."></input><button v-on:click="reset">Reset</button>
  </div>
  
  <TourenListe :touren="tourliste" :suche="suche"></TourenListe>
</template>

<script setup lang="ts">
/*
 * TourenListe-Komponente bitte in src/components/tour selbst implementieren
 */
import TourenListe from '@/components/tour/TourenListe.vue'
import { ref, onMounted, computed } from 'vue'
import { useTourenStore } from '@/stores/tourenstore';



const tourenStore = useTourenStore()
const suche = ref("")
const reset = () => {
  suche.value = ""
}

onMounted(() => {
  if(tourenStore.state.ok){
    tourenStore.updateTourListe()
  }
})
const tourliste = computed(() => tourenStore.tourliste)
</script>

<style scoped></style>
