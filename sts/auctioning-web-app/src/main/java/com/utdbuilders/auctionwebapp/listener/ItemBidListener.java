package com.utdbuilders.auctionwebapp.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.utdbuilders.auctionwebapp.model.ItemBid;
import com.utdbuilders.auctionwebapp.model.Items;
import com.utdbuilders.auctionwebapp.repository.ItemBidRepository;
import com.utdbuilders.auctionwebapp.repository.ItemsRepository;

@Component
public class ItemBidListener implements MessageListener{
	
	@Autowired
	private ItemBidRepository itemBidRepository;	
	@Autowired
	private ItemsRepository itemsRepository;

	@Override
	public void onMessage(Message message) {
		Gson gson = new Gson();
		ItemBid itemBid = gson.fromJson(new String(message.getBody()), ItemBid.class);			
		Items item = itemsRepository.findOne(itemBid.getItemId());
		if(item.getPriceSold() == null || (item.getPriceSold().compareTo(itemBid.getBiddingPrice()) == -1))
			{		
				item.setPriceSold(itemBid.getBiddingPrice());
				itemsRepository.save(item);
				itemBidRepository.save(itemBid);
			}
	}

}
