package org.red.rabbitmq.test.exchange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class SaveLogReceiver {
	
	private final static String EXCHANGE_NAME = "ex_log";

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
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		//创建一个非持久的，唯一的，可以自动删除的队列
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");
		System.out.println("等待接受消息内容......");
		//创建消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		//指定消费队列
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String content = new String(delivery.getBody());
			printToFile(content);
			System.out.println("处理完成");
		}
	}
	
	
	/**
	 * 将内容写入到文件
	 * @param msg
	 * @throws IOException 
	 */
	public static void printToFile(String msg) throws IOException{
		String basePath = SaveLogReceiver.class.getClassLoader().getResource("").getPath();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filePath = basePath+"/"+sdf.format(new Date())+".log";
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(("接受消息内容:"+msg+"\r\n").getBytes());
		fos.flush();
		fos.close();
	}
	
	

}
