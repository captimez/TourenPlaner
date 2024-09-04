<script setup lang="ts">
    import type { ITourDTD } from '@/stores/ITourDTD';
    const props = defineProps<{
        tour: ITourDTD
    }>()
    
</script>

<template>
    <tr>
        <td>
            {{ tour.id }}
        </td>
        <td>
            {{ tour.abfahrDateTime }}
        </td>
        <td>
            {{ tour.preis }}
        </td>
        <td>
            {{ tour.plaetze }}
        </td>
        <td>
            {{ tour.startOrtName }}
        </td>
        <td>
            {{ tour.zielOrtName }}
        </td>
        <td>
            {{ tour.anbieterName }}
        </td>
        <td>
            <RouterLink :to="`/tour/${tour.id}`"><button>Details</button></RouterLink>
        </td>
    </tr>
    
</template>