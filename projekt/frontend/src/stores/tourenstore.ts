import { ref, computed, reactive, readonly, onMounted } from 'vue';
import { defineStore } from 'pinia';
import type { ITourDTD } from './ITourDTD';
import { useInfo } from '@/composables/useInfo';
import { Client, type Message } from '@stomp/stompjs';
import type { IFrontendNachrichtEvent } from '@/services/IFrontendNachrichtEvent';

export const useTourenStore = defineStore('tourenstore', () => {
  const { setzeInfo } = useInfo();
  const wsurl = `ws://${window.location.host}/stompbroker`;
  const DEST = "/topic/tour";

  const tourdata = reactive({
    tourliste: [] as ITourDTD[],
    ok: false,
  });

  const startTourLiveUpdate = () => {
    const stompClient = new Client({
      brokerURL: wsurl,
      debug: (str) => {
        console.log(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    stompClient.onConnect = (frame) => {
      console.log("Web Socket connected!");
      stompClient.subscribe(DEST, (message) => {
        const eventobjekt: IFrontendNachrichtEvent = JSON.parse(message.body);
        console.log(JSON.stringify(eventobjekt));
        if (eventobjekt.operation === "CREATE" || eventobjekt.operation === "DELETE" ) {
          updateTourListe();
        }
      });
    };

    stompClient.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
      setzeInfo('Verbindung zum WebSocket-Server fehlgeschlagen.');
    };

    stompClient.activate();
  };

  const updateTourListe = async () => {
    try {
      const response = await fetch('/api/tour');
      if (!response.ok) {
        throw new Error(response.statusText);
      }
      const data = await response.json();
      tourdata.tourliste = data;
      tourdata.ok = true;
    } catch (error) {
      console.log(error);
      setzeInfo('Fehler beim Abrufen der Tourdaten.');
      tourdata.tourliste = [];
      tourdata.ok = false;
    }
  };

  onMounted(() => {
    if (!tourdata.ok) {
      updateTourListe();
      startTourLiveUpdate();
    }
  });

  return {
    updateTourListe,
    state: readonly(tourdata),
    tourliste: computed(() => tourdata.tourliste),
  };
});
