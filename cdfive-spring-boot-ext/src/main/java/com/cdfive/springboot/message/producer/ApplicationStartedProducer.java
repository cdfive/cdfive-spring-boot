package com.cdfive.springboot.message.producer;

import com.cdfive.springboot.message.vo.ApplicationStartedMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @author cdfive
 */
@Slf4j
@ConditionalOnClass({JmsMessagingTemplate.class, Queue.class, ActiveMQQueue.class})
@Component
public class ApplicationStartedProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("applicationStartedQueue")
    private Queue applicationStartedQueue;

    @Bean
    public Queue applicationStartedQueue() {
        return new ActiveMQQueue("applicationStartedQueue");
    }

    public void send(ApplicationStartedMessageVo messageVo) {
        jmsMessagingTemplate.convertAndSend(applicationStartedQueue, messageVo);
    }
}
