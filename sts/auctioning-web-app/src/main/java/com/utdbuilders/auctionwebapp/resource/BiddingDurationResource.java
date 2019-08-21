package com.utdbuilders.auctionwebapp.resource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utdbuilders.auctionwebapp.model.Items;
import com.utdbuilders.auctionwebapp.model.BiddingDuration;
import com.utdbuilders.auctionwebapp.model.BiddingSchedule;
import com.utdbuilders.auctionwebapp.repository.BiddingDurationRepository;

@RestController
@RequestMapping("/auctionwebapp/bidding")
public class BiddingDurationResource {

	@Autowired
	private BiddingDurationRepository biddingDurationRepository;
	
	public final static BigInteger ONE_BILLION = new BigInteger ("1000000000");
	 
	public BiddingDurationResource(BiddingDurationRepository biddingDurationRepository) {
		this.biddingDurationRepository = biddingDurationRepository;
	}
	
	@GetMapping("/all")
	public List<BiddingDuration> getAll(){
		return biddingDurationRepository.findAll();
	}	
	
	@GetMapping("/{biddingId}")
	public BiddingDuration getBidSchedule(@PathVariable (value="biddingId") Long biddingId, @Valid @RequestBody BiddingDuration item) {
		return biddingDurationRepository.findOne(biddingId);		
	}
		
	@PostMapping("/create")
	public BiddingDuration createBidSchedule(@Valid @RequestBody BiddingDuration schedule) {		
		this.biddingDurationRepository.save(schedule);		
		return schedule;				
	}

	
	@PostMapping("/getSchedules")
	public List<Items> getSchedules(@Valid @RequestBody BiddingSchedule schedule) {
		
		java.sql.Date startDate = schedule.getStartDate();
		List<Items> items = getItemsByDate(startDate);
		return items;
	}
	
	@PostMapping("/getSchedulesBetweenDates")
	public List<Items> getSchedulesDateRange(@Valid @RequestBody BiddingSchedule schedule) {
		
		java.sql.Date startDate = schedule.getStartDate();
		java.sql.Date endDate = schedule.getEndDate();
		java.sql.Date indexDate = startDate;
		
		
		List<Items> items = new ArrayList<Items>();
		
		while(indexDate.compareTo(endDate) <= 0) {
			items.addAll(getItemsByDate(indexDate));
			indexDate = getNextDate(indexDate);
		}		
		
		return items;
	}
	
	private List<Items> getItemsByDate(java.sql.Date date){
		
		List<Object> result = biddingDurationRepository.getAuctionItemsForDate(date);
		Iterator itr = result.iterator();
		List<Items> items = new ArrayList<Items>();
		int itemIndex = 0;
		long timePerItem = 0;
		
		java.sql.Timestamp sTime;
		java.sql.Timestamp eTime;
		

		long sTimeInLong;
		long eTimeInLong;
		long totalTimeInMinutes;
		
		while(itr.hasNext()) {
			Object [] obj = (Object[]) itr.next();
			Items item = new Items();
			
			sTime = java.sql.Timestamp.valueOf(String.valueOf(obj[4]));
			eTime = java.sql.Timestamp.valueOf(String.valueOf(obj[5]));
			
			sTimeInLong = sTime.getTime();
			eTimeInLong = eTime.getTime();
			totalTimeInMinutes = (eTimeInLong-sTimeInLong)/(1000*60);
			timePerItem = (totalTimeInMinutes*1000*60)/result.size();
			
			item.setId(Long.parseLong(String.valueOf(obj[0])));			
			item.setName(String.valueOf(obj[1]));			
			item.setDescription(String.valueOf(obj[2]));
			item.setStartingPrice(new BigDecimal(String.valueOf(obj[3])));
			item.setStartTime(new java.sql.Timestamp(sTimeInLong + timePerItem*itemIndex));
			item.setEndTime(new java.sql.Timestamp(sTimeInLong + timePerItem*(itemIndex+1)));			
			
			items.add(item);
			itemIndex++;
			
		}
		
		return items;
	}
	
	private java.sql.Date getNextDate(java.sql.Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTimeInMillis());
		
	}
}
