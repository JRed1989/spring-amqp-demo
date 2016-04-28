package org.red.springrabbit.reception;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessageReceiver {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("rabbitmq-consumer.xml");

	}

}
