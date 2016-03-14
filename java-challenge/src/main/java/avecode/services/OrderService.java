package avecode.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import avecode.controller.IOrderManager;
import avecode.controller.IProductManager;
import avecode.model.ui.OrderRequest;

@Path("orders")
public class OrderService {
	@Autowired
	IOrderManager orderManager;
	@Autowired
	IProductManager productManager;
	
	@POST
	@Path("place")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String placeOrder(OrderRequest order) { 
		if (null == order) {
			return "FAILED";
		}
		try {
			orderManager.place(order);
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
	}
	
	@PUT
	@Path("modify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String modifyOrder(OrderRequest order) {
		if (null == order) {
			return null;
		}
		try {
			orderManager.update(order);
			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";
		}
	}
	
	@GET
	@Path("fetch")
	@Produces(MediaType.APPLICATION_JSON)
	public OrderRequest getOrder(@QueryParam("orderId") String orderNum) {
		if (null == orderNum || orderNum.isEmpty()) {
			return null;
		}
		try {
			return orderManager.read(orderNum);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GET
	@Path("fetch-all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderRequest> getOrders() {
		try {
			return orderManager.read();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
