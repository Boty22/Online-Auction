package com.utdbuilders.auctionwebapp.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utdbuilders.auctionwebapp.model.BiddingDuration;
import com.utdbuilders.auctionwebapp.model.Items;

@Repository
public interface BiddingDurationRepository extends JpaRepository<BiddingDuration, Long>{		
	
	@Query("select i.id, i.name, i.description, i.startingPrice, b.startTime as bidStartTime, b.endTime as bidEndTime from Items as i, BiddingDuration as b where i.date = b.date and b.date=:pStartDate")
	List<Object> getAuctionItemsForDate(@Param("pStartDate") java.sql.Date pStartDate);
		
}

//select i.id, i.name, i.description, i.startingPrice, b.startTime as bidStartTime, b.endTime as bidEndTime from Items as i, BiddingDuration as b where i.date = b.date