package com.warehouse.softwareconfiguration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = { "com.warehouse.softwareconfiguration" })
@EntityScan(basePackages = { "com.warehouse.softwareconfiguration" })
@EnableMongoRepositories(basePackages = { "com.warehouse.softwareconfiguration" })
@AutoConfigureDataMongo
public class SoftwareTestConfiguration {
}
