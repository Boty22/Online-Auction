package com.utdbuilders.auctionwebapp.resource;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.utdbuilders.auctionwebapp.model.Accounts;
import com.utdbuilders.auctionwebapp.model.Items;
import com.utdbuilders.auctionwebapp.repository.AccountsRepository;
import com.utdbuilders.auctionwebapp.repository.ItemsRepository;

@RestController
@RequestMapping("/auctionwebapp/items")
public class ItemsResource {

	private ItemsRepository itemsRepository;
	@Autowired
	private AccountsRepository accountsRepository;
	
	public ItemsResource(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}
	
	@GetMapping("/")
	public List<Items> getAll(){		
		return itemsRepository.findAll();
	}	
	
	@Cacheable(value="item", key="#id")
	@GetMapping("/{id}")
	public Items getItem(@PathVariable (value="id") Long id) {
		return itemsRepository.findOne(id);		
	}
	
	@PostMapping("/create/{accountId}")
	public Items createItem(@PathVariable (value="accountId") Long accountId, @Valid @RequestBody Items item) {
		
		Accounts acc = accountsRepository.findOne(accountId);
		if(acc != null) {
			item.setOwner(acc);			
			this.itemsRepository.save(item);		
			return item;
		}else {						
			return null;
		}							
	}
	
	@PostMapping("/delete/{itemId}")
	public Items deleteItem(@PathVariable (value="itemId") Long itemId) {
		Items item = itemsRepository.findOne(itemId);	
		itemsRepository.delete(itemId);		
		return item;
	}

}
