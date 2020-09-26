package com.at.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author abs
 * @Date 2020/9/26 - 20:07
 */
public class JmsProducer {
    private static final String MQ_URL = "tcp://192.168.84.200:61616";
    private static final String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();;

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
        for (int i = 0; i < 3; i++) {
            TextMessage message = session.createTextMessage("send msg " + i);
            producer.send(message);
        }

        System.out.println("结束生产");
        producer.close();
        session.close();
        connection.close();
    }
}
