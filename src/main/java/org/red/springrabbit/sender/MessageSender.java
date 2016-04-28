package org.red.springrabbit.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessageSender {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("rabbitmq-produce.xml");
		AmqpTemplate amqpTemplate = (AmqpTemplate)context.getBean("amqpTemplate");
		String msg = "我最爱大白兔。";
		amqpTemplate.send("queue_one_key", new Message(msg.getBytes(), new MessageProperties()));
	    System.out.println("发送消息:"+msg);
	}

}
