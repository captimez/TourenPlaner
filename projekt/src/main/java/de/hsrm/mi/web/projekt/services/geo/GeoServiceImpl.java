package de.hsrm.mi.web.projekt.services.geo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class GeoServiceImpl implements GeoService{
    public static final Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);
    WebClient webClient = WebClient.create("https://nominatim.openstreetmap.org");

    @Override
public List<GeoAdresse> findeAdressen(String ort) {
    if (ort == null) {
        logger.error("Ort ist null");
        return new ArrayList<>();
    } else {
        logger.info("ORT IST: " + ort);
        try {
            var response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search")
                                             .queryParam("q", ort)
                                             .queryParam("format", "json")
                                             .queryParam("countrycodes", "de")
                                             .build())
                .retrieve()
                .bodyToFlux(GeoAdresse.class)
                .filter(geoAdresse -> "city".equals(geoAdresse.addresstype()) || 
                                      "village".equals(geoAdresse.addresstype()) || 
                                      "town".equals(geoAdresse.addresstype()))
                .collectList()
                .block();

            return response;
        } catch (WebClientResponseException e) {
            logger.error("Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return new ArrayList<>();
        }
    }
}

    
}
