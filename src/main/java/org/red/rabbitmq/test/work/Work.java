package org.red.rabbitmq.test.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Work {

	private static final String QUEUE_NAME="workqueue_persistence";
	
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		//创建工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置RabbitMQ服务器所在地址
		factory.setHost("114.80.89.87");
		factory.setUsername("waimai");
		factory.setPassword("waimai");
		factory.setPort(7004);
		//创建一个连接
		Connection connection = factory.newConnection();
		//创建一个频道
		Channel channel = connection.createChannel();
		//设置最大服务转发数量
		channel.basicQos(1);
		//声明队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println("等待接受消息内容......");
		//创建消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//指定消费队列
		//设置手动应答
		boolean ack = false;
		channel.basicConsume(QUEUE_NAME, ack, consumer);
		
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String content = new String(delivery.getBody());
			System.out.println("接受消息内容:"+content);
			dowork(content);
			//发送确认
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			System.out.println("处理完成");
			
		}
		
	}
	
	
	/**
	 * 每个点耗时1s
	 * @param task
	 * @throws InterruptedException 
	 */
	public static void dowork(String task) throws InterruptedException{
		if(task!=null&&!"".equals(task)){
			for(char ch:task.toCharArray()){
				if(ch == '.'){
					Thread.sleep(1000);
				}
			}
		}
	}
	
	
	
	
	
}
