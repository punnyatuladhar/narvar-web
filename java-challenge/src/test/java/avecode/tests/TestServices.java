package avecode.tests;

import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import avecode.model.ui.OrderRequest;
import avecode.model.ui.ProductRequest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestServices {

	private static final Logger logger = Logger.getLogger(TestServices.class);

	@Test
	public void test1CreateOrder() {
		logger.info("----------------TESTING NEW ORDER----------------------");
		
		OrderRequest req = new OrderRequest();
		req.orderNum = 1099;
		req.placedDate = new Date().getTime();
		
		OrderRequest.OrderItem item = new OrderRequest.OrderItem();
		item.lineNum = 9;
		item.qty = 2;
		item.sku = "353453kl4hkjh345";

		OrderRequest.OrderItem item2 = new OrderRequest.OrderItem();
		item2.lineNum = 7;
		item2.qty = 1;
		item2.sku = "36666666666hkjh345";

		req.items.add(item);
		req.items.add(item2);
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/").path("orders").path("place");
		Response res = target.request().post(Entity.json(req));	
		Assert.assertEquals("SUCCESS", res.readEntity(String.class));
		res.close();
		
		target = client.target("http://localhost:8080/").path("orders").path("fetch").queryParam("orderId", req.orderNum);
		Response resp = target.request().get();
		OrderRequest placedOrder = resp.readEntity(OrderRequest.class);
		res.close();

		Assert.assertNotNull(placedOrder);
		Assert.assertTrue(areEqual(item, placedOrder.items));
		Assert.assertTrue(areEqual(item2, placedOrder.items));
	}

	@Test
	public void test2ModifyOrder() {
		logger.info("----------------TESTING MODIFIYING ORDER----------------------");
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/").path("orders").path("fetch").queryParam("orderId", 1099);
		Response resp = target.request().get();
		OrderRequest placedOrder = resp.readEntity(OrderRequest.class);
		resp.close();

		Assert.assertNotNull(placedOrder);
		long time = new Date().getTime() + 2*60*60*1000;
		placedOrder.placedDate = time;
		if (placedOrder.items.get(1).sku.equals("353453kl4hkjh345")) {
			placedOrder.items.get(1).qty = 10;
		} else {
			placedOrder.items.get(0).qty = 10;
		}
		
		target = client.target("http://localhost:8080/").path("orders").path("modify");
		Response res = target.request().put(Entity.json(placedOrder));
		Assert.assertEquals("SUCCESS", res.readEntity(String.class));
		res.close();

		target = client.target("http://localhost:8080/").path("orders").path("fetch").queryParam("orderId", 1099);
		Response response = target.request().get();
		OrderRequest modOrder = response.readEntity(OrderRequest.class);
		response.close();
		
		Assert.assertNotNull(modOrder);
		Assert.assertEquals(time,  modOrder.placedDate);
		if (modOrder.items.get(0).sku.equals("353453kl4hkjh345")) {
			Assert.assertEquals(10, modOrder.items.get(0).qty);
		} else {
			Assert.assertEquals(10, modOrder.items.get(1).qty);
		}
	}
	
	@Test
	public void test3RetrieveAllOrders() {
		logger.info("----------------TESTING GETTING ALL ORDERS----------------------");
		
		OrderRequest req = new OrderRequest();
		req.orderNum = 2099;
		long time2 = new Date().getTime() +  3600 * 1000;
		req.placedDate = time2;
		
		OrderRequest.OrderItem item = new OrderRequest.OrderItem();
		item.lineNum = 2;
		item.qty = 5;
		item.sku = "353453kl4hkjh345";

		OrderRequest.OrderItem item2 = new OrderRequest.OrderItem();
		item2.lineNum = 6;
		item2.qty = 3;
		item2.sku = "36666666666hkjh345";

		req.items.add(item);
		req.items.add(item2);
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/").path("orders").path("place");
		Response res = target.request().post(Entity.json(req));	
		Assert.assertEquals("SUCCESS", res.readEntity(String.class));
		res.close();
		
		target = client.target("http://localhost:8080/").path("orders").path("fetch-all");
		Response response = target.request().get();
		List<OrderRequest> orders = response.readEntity(new GenericType<List<OrderRequest>>(){});
		response.close();
		
		Assert.assertEquals(2, orders.size());
		Assert.assertTrue(orders.get(0).orderNum == 1099 || orders.get(1).orderNum == 1099);
		Assert.assertTrue(orders.get(0).orderNum == 2099 || orders.get(1).orderNum == 2099);
	}

	@Test
	public void test4FetchProduct() {
		logger.info("----------------TESTING GETTING A PRODUCT----------------------");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/").path("products").path("fetch").queryParam("sku", "36666666666hkjh345");
		Response resp = target.request().get();
		ProductRequest prod = resp.readEntity(ProductRequest.class);
		resp.close();
		
		Assert.assertNotNull(prod);
		Assert.assertEquals("keyboard",  prod.name);
	}
	
	@Test
	public void test5Catalog() {
		logger.info("----------------TESTING GETTING CATALOG----------------------");
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/").path("products").path("catalog");
		Response resp = target.request().get();
		List<ProductRequest> prods = resp.readEntity(new GenericType<List<ProductRequest>>(){});
		resp.close();
		
		Assert.assertEquals(2,  prods.size());
		Assert.assertTrue(prods.get(0).name.equals("iPad") || prods.get(1).name.equals("iPad"));
		Assert.assertTrue(prods.get(0).name.equals("keyboard") || prods.get(1).name.equals("keyboard"));
	}
	
	@Test 
	public void test6NullProduct() {
		logger.info("----------------TESTING NULL PRODUCT ----------------------");
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/").path("products").path("fetch").queryParam("sku", null);
		Response resp = target.request().get();
		ProductRequest prod = resp.readEntity(ProductRequest.class);
		resp.close();
		
		Assert.assertNull(prod);
	}

	@Test 
	public void test7InvalidProduct() {
		logger.info("----------------TESTING INVALID PRODUCT----------------------");
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/").path("products").path("fetch").queryParam("sku", "sd9s9g7df");
		Response resp = target.request().get();
		ProductRequest prod = resp.readEntity(ProductRequest.class);
		resp.close();
		
		Assert.assertNull(prod);
	}

	private boolean areEqual(OrderRequest.OrderItem exp, List<OrderRequest.OrderItem> items) {
		for (OrderRequest.OrderItem act : items) {
			if (exp.sku.equals(act.sku)) {
				return exp.lineNum == act.lineNum && exp.qty == exp.qty;
			};
		}
		
		return false;
	}
}
