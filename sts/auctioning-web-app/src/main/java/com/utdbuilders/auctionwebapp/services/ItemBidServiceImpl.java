package com.utdbuilders.auctionwebapp.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utdbuilders.auctionwebapp.AuctioningWebAppApplication;
import com.utdbuilders.auctionwebapp.model.ItemBid;

@Service
public class ItemBidServiceImpl implements ItemBidService{
	
	private RabbitTemplate rabbitTemplate;
	
	
	@Autowired
	public ItemBidServiceImpl(RabbitTemplate rabbitTemplate) {
		this.setRabbitTemplate(rabbitTemplate);
	}

	@Override
	public void sendItemMessage(ItemBid itemBid) {		
		rabbitTemplate.convertAndSend(AuctioningWebAppApplication.queueName,itemBid.toString());
	}

	public RabbitTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}

	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

}
