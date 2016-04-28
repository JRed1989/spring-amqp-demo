package org.red.rabbitmq.test.topic;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class LogSender {
	 private final static String EXCHANGE_NAME = "topic_log";
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
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			
			String[] routing_keys = new String[] { "kernal.info", "cron.warning",  
	                "auth.info", "kernal.critical" };
			for(String rkey : routing_keys){
				String message = UUID.randomUUID().toString();
				channel.basicPublish(EXCHANGE_NAME, rkey, null, message.getBytes());
				System.out.println("发送消息内容:"+message);
			}
			channel.close();
			connection.close();
		}
}
