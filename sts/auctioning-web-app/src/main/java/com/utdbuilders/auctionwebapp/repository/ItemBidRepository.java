package com.utdbuilders.auctionwebapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utdbuilders.auctionwebapp.model.ItemBid;

@Repository
public interface ItemBidRepository extends JpaRepository<ItemBid, Long>{	
	@Query("select ib.bidId, ib.accountId, ib.biddingPrice, ib.itemId  from Items i, ItemBid ib where i.id = ib.itemId and ib.itemId=:pItemId")
	List<Object> getAllBidsForItem(@Param("pItemId") Long itemId);
}
