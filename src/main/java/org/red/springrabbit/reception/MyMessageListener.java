package org.red.springrabbit.reception;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class MyMessageListener  implements ChannelAwareMessageListener{

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		String content = new String(message.getBody());
		System.out.println("接收消息:"+content);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
		System.out.println("接收方确认处理消息完毕。");
	}

}
