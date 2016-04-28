package org.red.rabbitmq.test.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class LogReceiver {
	
	private final static String EXCHANGE_NAME = "topic_log";

	public static void main(String[] args) throws Exception {
		//创建工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置RabbitMQ服务器所在地址
		factory.setHost("localhost");
		//创建一个连接
		Connection connection = factory.newConnection();
		//创建一个频道
		Channel channel = connection.createChannel();
		//声明交换机以及其类型
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		//创建一个非持久的，唯一的，可以自动删除的队列
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME,"kernal.*");
		System.out.println("等待接受消息内容......");
		//创建消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		//指定消费队列
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String content = new String(delivery.getBody());
			System.out.println("接受消息内容:"+content+"router_key="+delivery.getEnvelope().getRoutingKey());
			System.out.println("处理完成");
		}
	}
	
	
	

}
