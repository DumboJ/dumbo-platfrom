package com.dumbo.app.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 生产 同步消息
 * */
public class SyncProducerMsg {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //实例化消息生产者producer
        DefaultMQProducer producer = new DefaultMQProducer("producer-test");
        //NameServer地址
        producer.setNamesrvAddr("192.168.5.101:9876;192.168.5.102:9876;192.168.5.103:9876");
        //启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            //创建消息，指定Topic，Tag和消息体
            Message msg = new Message("producer", "sync-msg", ("RocketMQ producer msg:" + i).getBytes(StandardCharsets.UTF_8));
            //发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            //SendResult返回消息是否发送成功
            System.out.printf("%s%n",sendResult);
        }
        producer.shutdown();
    }
}
