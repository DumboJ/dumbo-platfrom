package com.dumbo.app.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 单向发送消息：主要作为不特别关心发送结果的场景，例如日志发送
 * */
public class OnewayProducerMsg {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("producer-test");
        producer.setNamesrvAddr("192.168.5.101:9876;192.168.5.102:9876;192.168.5.103:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("producer","one-way-msg",("MQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送单向消息，没有返回结果
            producer.sendOneway(msg);
        }
        producer.shutdown();
    }
}
