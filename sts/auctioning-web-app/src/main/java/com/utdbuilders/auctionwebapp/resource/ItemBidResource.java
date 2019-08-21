package com.utdbuilders.auctionwebapp.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.utdbuilders.auctionwebapp.model.ItemBid;
import com.utdbuilders.auctionwebapp.model.Items;
import com.utdbuilders.auctionwebapp.repository.ItemBidRepository;
import com.utdbuilders.auctionwebapp.services.ItemBidService;

@RestController
@RequestMapping("/auctionwebapp/itemBid")
public class ItemBidResource {

	private ItemBidService itemBidService;
	private ItemBidRepository itemBidRepository;
	
	@Autowired
	public ItemBidResource(ItemBidService itemBidService,ItemBidRepository itemBidRepository) {
		this.itemBidService = itemBidService;
		this.itemBidRepository = itemBidRepository;
	}
	
	@CacheEvict(value="item", key="#itemBid.getItemId()")
	@PostMapping("/create/")
	public ItemBid createItemBid(@Valid @RequestBody ItemBid itemBid) {
		itemBidService.sendItemMessage(itemBid);		
		return itemBid;		
	}	
	
	@GetMapping("/item/{id}")
	public List<ItemBid> getBidsForItem(@PathVariable (value="id") Long id) {
		List<Object> result = itemBidRepository.getAllBidsForItem(id);
		Iterator itr = result.iterator();
		List<ItemBid> itemBids = new ArrayList<ItemBid>();
		
		while(itr.hasNext()) {
			Object [] obj = (Object[]) itr.next();
			ItemBid itemBid = new ItemBid();
								
			itemBid.setBidId(Long.parseLong(String.valueOf(obj[0])));
			itemBid.setAccountId(Long.parseLong(String.valueOf(obj[1])));
			itemBid.setBiddingPrice(new BigDecimal(String.valueOf(obj[2])));
			itemBid.setItemId(Long.parseLong(String.valueOf(obj[3])));							
			itemBids.add(itemBid);			
		}
		
		return itemBids;
	}
}
