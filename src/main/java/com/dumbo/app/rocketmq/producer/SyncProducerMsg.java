package com.dumbo.app.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * 生产 同步消息
 * */
public class SyncProducerMsg {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        //实例化客户端
        DefaultMQProducer producer = new DefaultMQProducer("producer-test");
        //NameServer 地址
        producer.setNamesrvAddr("192.168.5.101:9876;192.168.5.102:9876;192.168.5.103:9876");
        //启动producer实例
        producer.start();
        for (int i = 0; i < 200; i++) {
            Message msg = new Message("producer", "sync-msg", ("RocketMQ producer send msg" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult send = producer.send(msg);
            System.out.printf("%s%n",send);
        }
        producer.shutdown();
    }
}
