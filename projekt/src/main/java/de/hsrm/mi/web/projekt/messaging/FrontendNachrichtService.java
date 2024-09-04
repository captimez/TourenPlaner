package de.hsrm.mi.web.projekt.messaging;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class FrontendNachrichtService {
    public static final Logger logger = LoggerFactory.getLogger(FrontendNachrichtService.class);
    @Autowired
    SimpMessagingTemplate messaging;

    public void sendEvent(FrontendNachrichtEvent ev){
        logger.info(ev.toString());
        messaging.convertAndSend("/topic/tour", ev);
    }
}
