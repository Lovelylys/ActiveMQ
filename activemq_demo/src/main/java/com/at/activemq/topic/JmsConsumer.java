package com.at.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author abs
 * @Date 2020/9/26 - 20:14
 */
public class JmsConsumer {
    private final static String MQ_URL = "tcp://192.168.84.200:61616";
    private final static String TOPIC_NAME = "topic01";
    public static void main(String[] args) throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                try {
                    System.out.println(((TextMessage) message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        System.out.println("消费结束");
        consumer.close();
        session.close();
        connection.close();
    }
}
