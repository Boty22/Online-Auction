package com.utdbuilders.auctionwebapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utdbuilders.auctionwebapp.model.Items;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long>{	
	
}
