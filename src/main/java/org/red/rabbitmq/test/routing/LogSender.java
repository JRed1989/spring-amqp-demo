package org.red.rabbitmq.test.routing;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class LogSender {
	
  private final static String EXCHANGE_NAME = "routing_log";
	
  private final static String[] LOG_TYPES = {"INFO","WARNING","ERROR"};

	
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
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		//发送多条消息
		for(int i=0;i<=6;i++){
			String logType = getLogType();
			String message = logType+"_LOG"+UUID.randomUUID().toString();
			channel.basicPublish(EXCHANGE_NAME, logType, null, message.getBytes());
			System.out.println("发送消息内容:"+message);
		}
		channel.close();
		connection.close();
	}
	
  private static String getLogType(){
	  int index = new Random().nextInt(3);
	  return LOG_TYPES[index];
  }

}
