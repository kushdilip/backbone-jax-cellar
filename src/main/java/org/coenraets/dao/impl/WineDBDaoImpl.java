package org.coenraets.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.coenraets.dao.WineDBDAO;
import org.coenraets.domain.Wine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository("wineDBDAO")
public class WineDBDaoImpl implements WineDBDAO {
	
	@Autowired
	private MongoDbFactory mongoDbFactory;
	
	@Resource
	Gson gson;
	
	@Override
	public void create(BasicDBObject dbObject) {
		DB db = mongoDbFactory.getDb();
		DBCollection collection = db.getCollection("wine");
		collection.insert(dbObject);
	}

	@Override
	public List<Wine> findAll() {
		List<Wine> list = new ArrayList<Wine>();		
		DB db = mongoDbFactory.getDb();
		DBCursor wineCursor = db.getCollection("wine").find();
		
		for (DBObject dbObject : wineCursor) {
			list.add(processDbObject(dbObject));
		}
		
		return list;
	}
	
	
	protected Wine processDbObject(DBObject dbObject) {
		Wine wine = gson.fromJson(dbObject.toString(), Wine.class);
		wine.setId(dbObject.get("_id").toString());
		
		return wine;
	}
	
	
	
}
