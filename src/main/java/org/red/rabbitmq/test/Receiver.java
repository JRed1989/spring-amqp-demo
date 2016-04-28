package org.red.rabbitmq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Receiver {
	
	private static final String QUEUE_NAME="test_queue";
	
	
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//创建工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置RabbitMQ服务器所在地址
		factory.setHost("localhost");
		//创建一个连接
		Connection connection = factory.newConnection();
		//创建一个频道
		Channel channel = connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("等待接受消息内容......");
		//创建消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//指定消费队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String content = new String(delivery.getBody());
			System.out.println("接受消息内容:"+content);
			
		}
		
	}

}
