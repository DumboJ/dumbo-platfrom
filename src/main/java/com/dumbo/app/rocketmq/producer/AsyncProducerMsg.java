package com.dumbo.app.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.*;

/**
 * 生产 异步消息
 * */
public class AsyncProducerMsg {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("producer-test");
        producer.setNamesrvAddr("192.168.5.101:9876;192.168.5.102:9876;192.168.5.103:9876");
        producer.start();
        //异步发送失败不重发
        producer.setRetryTimesWhenSendAsyncFailed(0);
        int msgCount = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(msgCount);
        for (int i = 0; i < msgCount; i++) {
            final int index = i;
            Message message = new Message("producer", "async-msg","buy_one".getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息，SendCallback 接收异步响应回调
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    //不能打出msgid
//                    System.out.printf("buy OK %s %n", index, sendResult.getMsgId());
                    //格式化打出msgid
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    countDownLatch.countDown();
                    System.out.printf("buy Exception %s %n", index, throwable);
                    throwable.printStackTrace();
                }
            });
        }
        //等待5s
        countDownLatch.await(5, TimeUnit.SECONDS);
        //不再发送消息则关闭实例
        producer.shutdown();
    }
}
