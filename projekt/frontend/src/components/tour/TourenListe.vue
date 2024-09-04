<script setup lang="ts">
    /* __placeholder__ */
    import type { ITourDTD } from "@/stores/ITourDTD";
    import TourListZeile from "./TourListZeile.vue"
    import { computed, onMounted } from "vue";

    const props = defineProps<{
        touren: ITourDTD[],
        suche: string
    }>();
    
    const filteredTouren = computed(() => {
        if(Array.isArray(props.touren)){
            return props.touren.filter( tour => {
            return tour.startOrtName.toLowerCase().includes(props.suche.toLowerCase()) || tour.zielOrtName.toLowerCase().includes(props.suche.toLowerCase())
        });
        }else{
            return []
        }
        
    });

    
</script>
<template>
    <table class="tour-tabelle" >
        <thead>
            <tr>
                <th >ID</th>
                <th>AbfahrtsZeit</th>
                <th>Preis</th>
                <th>Plätze</th>
                <th>StartOrt</th>
                <th>ZielOrt</th>
                <th>Anbieter</th>
                <th>Aktionen</th>
            </tr>
        </thead>
        <tbody>
            <TourListZeile :tour="tour" v-for="tour in filteredTouren" ></TourListZeile>
        </tbody>
    </table>
</template>