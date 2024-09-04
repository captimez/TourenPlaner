<template>
<h1>Tour {{ tourid  }}: {{ tour?.startOrtName }} - {{ tour?.zielOrtName }}</h1>
<span>Abfahrt am: {{ tour?.abfahrDateTime }}</span><br>
<span>Preis: {{ tour?.preis }} für {{ tour?.distanz ? Math.round(tour?.distanz) : 0 }}km.</span><br>
<span>Anbieter der Tour ist: {{ tour?.anbieterName }}</span><br>
<span>Verfügbare Plaetze: {{ tour?.plaetze }} Gebucht: {{ tour?.buchungen }}</span><br>

</template>
<script setup lang ="ts">
import { useInfo } from '@/composables/useInfo';
import { useTourenStore } from '@/stores/tourenstore';
import { computed, onMounted, ref } from 'vue';


const { info, setzeInfo } = useInfo()
const props = defineProps<{
    tourid: string,
}>();



const tourenStore = useTourenStore();


onMounted(async () => {
  console.log(tourenStore.state.ok)
  if(!tourenStore.state.ok){
    await tourenStore.updateTourListe()
  }
})
const tourliste = computed(() => tourenStore.tourliste)
const tour  = computed(() => {
  return tourliste.value.find(tour => tour.id == parseInt(props.tourid))
});


if (tour.value && tour.value.distanz !== undefined && tour.value.distanz !== null && tour.value.distanz >= 300) {
  setzeInfo("Achtung Tour ist länger als 300km!");
}

</script>
<style>
</style>