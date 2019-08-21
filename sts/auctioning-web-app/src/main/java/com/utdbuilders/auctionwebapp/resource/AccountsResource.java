package com.utdbuilders.auctionwebapp.resource;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utdbuilders.auctionwebapp.model.Accounts;
import com.utdbuilders.auctionwebapp.repository.AccountsRepository;

@RestController
@RequestMapping("/auctionwebapp/accounts")
public class AccountsResource {

	private AccountsRepository accountsRepository;
	
	public AccountsResource(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}
	
	@GetMapping("/all")
	public List<Accounts> getAll(){
		return accountsRepository.findAll();
	}	
	
	@GetMapping("/{accountId}")
	public Accounts getAccountByAccountId(@PathVariable (value="accountId") Long accountId) {
		return accountsRepository.findOne(accountId);
	}

	@GetMapping("/google/{googleId}")
	public Accounts getAccount(@PathVariable (value="googleId") String googleId) {
		List<Object> result = accountsRepository.getIdAndUserNameFromGoogleId(googleId);
		Iterator itr = result.iterator();
		while(itr.hasNext()) {
			Object [] obj = (Object[]) itr.next();
			Accounts acc = new Accounts();
			acc.setId(Long.parseLong(String.valueOf(obj[0])));
			acc.setUserName(String.valueOf(obj[1]));
			acc.setGoogleId(googleId);			
			return acc;
		}
		
		return null;
	}
		
	@PostMapping("/create")
	public Accounts createAccount(@Valid @RequestBody Accounts account) {
		
		if(account.getId() != null) {
			Accounts acc = accountsRepository.findOne(account.getId());
			if(acc != null) {
				acc.setCity(account.getCity());
				acc.setFirstName(account.getFirstName());
				acc.setLastName(account.getLastName());
				acc.setCity(account.getCity());
				acc.setState(account.getState());
				acc.setCountry(account.getCountry());
				acc.setZipcode(account.getZipcode());
				acc.setEmail(account.getEmail());
				acc.setStreetAddress(account.getStreetAddress());
				this.accountsRepository.save(acc);
			}
		}
		else
			this.accountsRepository.save(account);		
		return account;				
	}
	
}
