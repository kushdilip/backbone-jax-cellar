package org.coenraets.dao;

import java.util.List;

import org.coenraets.domain.Wine;

import com.mongodb.BasicDBObject;

public interface WineDBDAO {
		
	List<Wine> findAll();
	
	public List<Wine> findByName(String name);
	
	public Wine findById(String id);
	
	public Wine save(Wine wine);
	
	public Wine create(Wine wine);
	
	public Wine update(Wine wine);
	
	public boolean remove(String id);
}
