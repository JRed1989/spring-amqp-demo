package org.red.rabbitmq.test;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	
	private final static String QUEUE_NAME="test_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
	    //设置RabbitMQ服务器的地址
		factory.setHost("localhost");
		//创建一个连接
		Connection connection = factory.newConnection();
		//创建一个频道
		Channel channel = connection.createChannel();
		//指定一个队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//发送消息内容
		for(int i=0;i<10;i++){
			String message = "我是测试消息，I'm coming!"+i+new Random().nextFloat();
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("发送消息内容:"+message);
		}
		channel.close();
		connection.close();
		
	}

}
