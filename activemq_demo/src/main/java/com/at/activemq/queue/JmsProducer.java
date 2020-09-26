package com.at.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author abs
 * @Date 2020/8/16 - 23:15
 */
public class JmsProducer {
    private static final String ACTIVEMQ_URL = "tcp://192.168.84.200:61616";
    private static final String QUEUE_NAME = "queue1";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection("admin", "admin");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(queue);

        for (int i = 0; i < 3; i++) {
            TextMessage message = session.createTextMessage("send msg " + i);
            producer.send(message);
        }
        producer.close();
        session.close();
        connection.close();
        System.out.println("**** MQ 消息发送结束 ****");
    }
}
