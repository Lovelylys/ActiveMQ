package com.at.activemq.queue;

import javafx.scene.text.Text;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author abs
 * @Date 2020/9/26 - 17:58
 */
public class JmsConsumer {
    private static final String MQ_URL = "tcp://192.168.84.200:61616";
    private static final String QUEUE_NAME = "queue1";

    public static void main(String[] args) throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);

        // 阻塞式消费
        /*while(true) {
            TextMessage message = (TextMessage)consumer.receive(4000L);
            if (message == null) {
                break;
            }
            System.out.println(message);
        }*/

        consumer.setMessageListener(message -> {
            if (message != null && message instanceof TextMessage) {
                try {
                    System.out.println("消费事件" + ((TextMessage) message).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        // 保证控制台不灭  不然activemq 还没连上就关掉了连接
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
        System.out.println("消费结束");
    }
}
