package org.coenraets.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bson.BSONObject;
import org.bson.types.ObjectId;
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
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

@Repository("dao")
public class WineDBDaoImpl implements WineDBDAO {

	@Autowired
	private MongoDbFactory mongoDbFactory;

	@Resource
	Gson gson;

	@Override
	public List<Wine> findAll() {
		List<Wine> list = new ArrayList<Wine>();
		DB db = mongoDbFactory.getDb();
		DBCursor wineCursor = db.getCollection("wine").find();

		for (DBObject dbObject : wineCursor) {
			list.add(processDbObject(dbObject));
		}
		
		wineCursor.close();
		
		return list;
	}

	@Override
	public List<Wine> findByName(String name) {
		List<Wine> list = new ArrayList<Wine>();
		DB db = mongoDbFactory.getDb();
		BasicDBObject query = new BasicDBObject("name", name);
		DBCursor wineCursor = db.getCollection("wine").find(query);

		for (DBObject dbObject : wineCursor) {
			list.add(processDbObject(dbObject));
		}
		wineCursor.close();

		return list;
	}

	@Override
	public Wine findById(String id) {
		Wine wine = null;
		DB db = mongoDbFactory.getDb();
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
		
		
		DBObject wineDbObject = db.getCollection("wine").findOne(query);
		
		if(wineDbObject != null){
			wine = processDbObject(wineDbObject);
		}
		
		return wine;
	}

	@Override
	public Wine save(Wine wine) {
		return wine.getId() != null ? update(wine) : create(wine);
	}

	@Override
	public Wine create(Wine wine) {
		DB db = mongoDbFactory.getDb();

		BasicDBObject doc = new BasicDBObject();
		doc.put("name", wine.getName());
		doc.put("grapes", wine.getGrapes());
		doc.put("country", wine.getCountry());
		doc.put("region", wine.getRegion());
		doc.put("year", wine.getYear());
		doc.put("picture", wine.getPicture());
		doc.put("description", wine.getDescription());

		db.getCollection("wine").insert(doc);
		wine.setId(doc.get("_id").toString());
		
		return wine;
	}

	@Override
	public Wine update(Wine wine) {
		DB db = mongoDbFactory.getDb();

		BasicDBObject doc = new BasicDBObject();
		doc.put("name", wine.getName());
		doc.put("grapes", wine.getGrapes());
		doc.put("country", wine.getCountry());
		doc.put("region", wine.getRegion());
		doc.put("year", wine.getYear());
		doc.put("picture", wine.getPicture());
		doc.put("description", wine.getDescription());
		
		db.getCollection("wine").update(new BasicDBObject("_id",new ObjectId(wine.getId())), doc);
		
		return findById(wine.getId());
	}

	@Override
	public boolean remove(String id) {
		boolean isReturned = Boolean.FALSE;
		
		DB db = mongoDbFactory.getDb();
		DBCollection collection = db.getCollection("wine");
	
		collection.remove(new BasicDBObject("_id", new ObjectId(id)));
	
		if(findById(id) != null){
			isReturned = true;
		}
		
		return isReturned;
	}

	protected Wine processDbObject(DBObject dbObject) {
		Wine wine = gson.fromJson(dbObject.toString(), Wine.class);
		wine.setId(dbObject.get("_id").toString());
		
		return wine;
	}

}
