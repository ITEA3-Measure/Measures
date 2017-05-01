package org.measure.localsystemmeasure.impl;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class RabbitExchangeMonitor {
	ConnectionFactory rFact;
	Connection rConn;
	Channel rChn;
	Consumer rCons;
	
	int msgCount;
	
	public RabbitExchangeMonitor(String host, String username, String password, String exchange) throws IOException, TimeoutException {
		msgCount = 0;
		
		String queueName = exchange + ".monitor";
		rFact = new ConnectionFactory();
		rFact.setHost(host);
		rFact.setUsername(username);
		rFact.setPassword(password);
		
		rConn = rFact.newConnection();
		
		rChn = rConn.createChannel();
		
		rChn.queueDeclare(queueName, false, true, false, null);
		rChn.queueBind(queueName, exchange, "#");
		
		rCons = new DefaultConsumer(rChn) {
		  @Override
		  public void handleDelivery(String consumerTag, Envelope envelope,
		                             BasicProperties properties, byte[] body)
		      throws IOException {
		    msgCount++;
		  }
		};
		rChn.basicConsume(queueName, true, rCons);
	}
	
	public int GetMessageCount() {
		int ret = msgCount;
		msgCount = 0;
		return ret;
	}
}
