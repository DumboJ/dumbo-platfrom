package com.dumbo.app.rocketmq.consume;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 普通顺序消费consume
 * */
public class CommonConsume {
    public static void main(String[] args) throws MQClientException {
        //实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consume-test");
        //设置nameserver
        consumer.setNamesrvAddr("192.168.5.101:9876;192.168.5.102:9876;192.168.5.103:9876");
        //订阅一个或者多个topic/tag来过滤需要消费的消息
        consumer.subscribe("producer", "*");
        //注册回调处理从broker拉取的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s consume new msg:%s %n",Thread.currentThread().getName(),msgs);
                //标记消息被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Comsume started. %n");
    }
}
