package com.utdbuilders.auctionwebapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.utdbuilders.auctionwebapp.resource")
@Configuration
public class Config {


}
