package org.red.rabbitmq.test.exchange;

import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class LogSender {
	private final static String EXCHANGE_NAME = "ex_log";
	
	public static void main(String[] args) throws Exception, TimeoutException {
		//创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
	    //设置RabbitMQ服务器的地址
		factory.setHost("localhost");
		//创建一个连接
		Connection connection = factory.newConnection();
		//创建一个频道
		Channel channel = connection.createChannel();
		
		//声明交换机和类型
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String message = new Date().toLocaleString()+"日志记录";
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		channel.close();
		connection.close();
	}
	

}
