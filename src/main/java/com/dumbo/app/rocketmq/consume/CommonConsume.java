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
        //初始化消费者客户端
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-test");
        //设置NameServer
        consumer.setNamesrvAddr("192.168.5.101:9876;192.168.5.102:9876;192.168.5.103:9876");
        //订阅topic-tag
        consumer.subscribe("producer","*");
        //注册消息监听，从broker拉取消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s consumer msg info:%s %n",Thread.currentThread().getName(),msgs);
                //标记消息消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Consume started %n");
    }
}
