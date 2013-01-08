package org.coenraets.dao;

import java.util.List;

import org.coenraets.domain.Wine;

import com.mongodb.BasicDBObject;

public interface WineDBDAO {
	
	void create(BasicDBObject dbObject);
	
	List<Wine> findAll();
}
