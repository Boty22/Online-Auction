package com.utdbuilders.auctionwebapp;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.utdbuilders.auctionwebapp.listener.ItemBidListener;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;


@EnableJpaRepositories(basePackages="com.utdbuilders.auctionwebapp.repository")
@EnableCaching
@SpringBootApplication
public class AuctioningWebAppApplication {
	
	public static final String topicExchangeName = "spring-boot-exchange";
	public static final String queueName = "spring-boot";
	
	@Bean
	Queue queue() {
		return new Queue(queueName,false);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}
	
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}
	
	@Bean	
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}
	
	@Bean
	MessageListenerAdapter listenerAdapter(ItemBidListener receiver) {
		return new MessageListenerAdapter(receiver, "onMessage");
	}
		
	public static void main(String[] args) {				
		
		SpringApplication.run(AuctioningWebAppApplication.class, args);
		
	}
}
