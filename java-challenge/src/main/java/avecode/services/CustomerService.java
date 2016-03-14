package avecode.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import avecode.controller.ICustomerManager;
import avecode.model.ui.CustomerRequest;

@Path("customers")
public class CustomerService {
	@Autowired
	ICustomerManager customerMgr;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCustomer(CustomerRequest customer) { 
		String res = customerMgr.add(customer);
		if (res.equals("OK")) {
			return Response.status(Response.Status.CREATED).build();
		} 
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
	}
	
	@GET
	//@Path("{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		List<CustomerRequest> cust = customerMgr.get();
		if (null != cust) {
			return Response.ok().entity(cust).build();
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Customer couldn't be retrieved.").build();
	}

	@PUT
	@Path("{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("name") String name, CustomerRequest req) {
		String res = customerMgr.update(name, req);
		if (res.equals("OK")) {
			return Response.ok().build();
		} 
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
	}
	
	@DELETE
	@Path("{name}")
	public Response update(@PathParam("name") String name) {
		String res = customerMgr.delete(name);
		if (res.equals("OK")) {
			return Response.ok().build();
		} 
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
	}
}
