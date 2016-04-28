package org.red.rabbitmq.test.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	
	//队列名称
	private final static String QUEUE_NAME = "workqueue_persistence";
	
	
	public static void main(String[] args) throws Exception {
		//创建连接工厂
				ConnectionFactory factory = new ConnectionFactory();
			    //设置RabbitMQ服务器的地址
				factory.setHost("114.80.89.87");
				factory.setUsername("waimai");
				factory.setPassword("waimai");
				factory.setPort(7004);
				//创建一个连接
				Connection connection = factory.newConnection();
				//创建一个频道
				Channel channel = connection.createChannel();
				//指定一个队列
				//设置队列的持久化
				boolean durable = true;
				channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
				//发送消息内容
				for(int i=0;i<10;i++){
					String message = "我是测试消息，I'm coming!";
					String dots = "";
					for(int j=0;j<=i;j++){
						dots += ".";
					}
					message += message+i+dots;
					//设置消息持久化
					channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
					System.out.println("发送消息内容:"+message);
				}
				channel.close();
				connection.close();
	}
	

}
