package org.coenraets.cellar;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.coenraets.dao.WineDBDAO;
import org.coenraets.domain.Wine;
import org.springframework.stereotype.Component;

@Component
@Path("/wines")
public class WineResource {
	
	@Resource
	WineDBDAO wineDBDAO;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Wine> savePayment() {
		
//		String jsonStr = "{\"user\" : \"dilip\" , \"password\" : \"password123\"};";
//		BasicDBObject dbObject = (BasicDBObject) JSON.parse(jsonStr);
//		wineDBDAO.create(dbObject);
		
		List<Wine> wines = wineDBDAO.findAll();
		System.out.println(wines);
		
//		return Response.status(200).entity(wines.toString()).build();
		return wines;
	}
}
