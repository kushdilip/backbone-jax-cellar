package org.coenraets.cellar;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.coenraets.dao.WineDBDAO;
import org.coenraets.domain.Wine;
import org.springframework.stereotype.Component;

@Component
@Path("/")
public class WineResource {

	@Resource
	WineDBDAO dao;

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Wine> savePayment() {

		List<Wine> wines = dao.findAll();
		return wines;
	}

	@GET
	@Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Wine> findByName(@PathParam("query") String query) {
		System.out.println("findByName: " + query);
		return dao.findByName(query);
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Wine findById(@PathParam("id") String id) {
		System.out.println("findById " + id);
		return dao.findById(id);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Wine create(Wine wine) {
		return dao.create(wine);
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Wine update(Wine wine) {
		System.out.println("Updating wine: " + wine.getName());
		dao.update(wine);
		return wine;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("id") String id) {
		dao.remove(id);
	}
}
