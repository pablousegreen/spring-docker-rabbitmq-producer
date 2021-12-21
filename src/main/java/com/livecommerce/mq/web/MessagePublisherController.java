package com.livecommerce.mq.web;

import com.livecommerce.mq.CustomMessage;
import com.livecommerce.mq.MQConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Slf4j
@RestController()
public class MessagePublisherController {

    public static final String PUBLISH = "/publish-message";
    @Autowired
    private RabbitTemplate template;

    Logger logger = getLogger(MessagePublisherController.class);

    @PostMapping(value = PUBLISH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String publicMessage(@RequestBody CustomMessage message){
        logger.info("Welcome /publish {} "+message);
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);
        logger.info("Fishihed to /publish {} "+message);
        return "Ok Message Pubished";
    }
}
