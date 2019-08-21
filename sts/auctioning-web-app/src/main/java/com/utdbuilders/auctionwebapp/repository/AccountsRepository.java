package com.utdbuilders.auctionwebapp.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utdbuilders.auctionwebapp.model.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long>{	
	@Query("select a.id, a.userName, a.googleId from Accounts a where a.googleId =:googleId")
	List<Object> getIdAndUserNameFromGoogleId(@Param("googleId") String googleId);
}
