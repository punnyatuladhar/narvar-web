package avecode.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import avecode.model.LineItem;
import avecode.model.ShopOrder;
import avecode.model.ui.OrderRequest;
import avecode.persistence.IProductDao;
import avecode.persistence.IShopOrderDao;

public class OrderManager implements IOrderManager {
	@Autowired
	IShopOrderDao shopOrderDao;
	@Autowired
	IProductDao productDao;
	
	@Override
	public void place(OrderRequest order) {
		// TODO Auto-generated method stub
		ShopOrder shopOrder = new ShopOrder();
		shopOrder.setOrderNum(order.orderNum);
		shopOrder.setPlacedDate(new Date(order.placedDate));
		
		for (OrderRequest.OrderItem item : order.items) {
			LineItem lineItem = new LineItem();
			lineItem.setLineNum(item.lineNum);
			lineItem.setQuantity(item.qty);
			lineItem.setOrder(shopOrder);
			lineItem.setProduct(productDao.get(item.sku));
			
			shopOrder.getItems().add(lineItem);
		}

		shopOrderDao.place(shopOrder);
	}

	@Override
	public void update(OrderRequest order) {
		// TODO Auto-generated method stub
		ShopOrder shopOrder = shopOrderDao.read(order.orderNum);
		shopOrder.setPlacedDate(new Date(order.placedDate));
		shopOrder.getItems().clear();
		
		for (OrderRequest.OrderItem item : order.items) {
			LineItem lineItem = new LineItem();
			lineItem.setLineNum(item.lineNum);
			lineItem.setQuantity(item.qty);
			lineItem.setOrder(shopOrder);
			lineItem.setProduct(productDao.get(item.sku));
			
			shopOrder.getItems().add(lineItem);
		}

		shopOrderDao.update(shopOrder);
	}

	@Override
	public OrderRequest read(String orderNum) {
		// TODO Auto-generated method stub
		OrderRequest req = new OrderRequest();
		ShopOrder order = shopOrderDao.read(Long.valueOf(orderNum));
		
		if (null == order) {
			return null;
		}
		
		req.orderNum = order.getOrderNum();
		req.placedDate = order.getPlacedDate().getTime();
		
		for (LineItem item : order.getItems()) {
			OrderRequest.OrderItem oItem = new OrderRequest.OrderItem();
			oItem.lineNum = item.getLineNum();
			oItem.qty = item.getQuantity();
			oItem.sku = item.getProduct().getSku();
			
			req.items.add(oItem);
		}

		return req;
	}

	@Override
	public List<OrderRequest> read() {
		// TODO Auto-generated method stub
		List<OrderRequest> all = new ArrayList<OrderRequest>();
		List<ShopOrder> orders = shopOrderDao.read();
		
		for (ShopOrder order : orders) {
			OrderRequest req = new OrderRequest();
			req.orderNum = order.getOrderNum();
			req.placedDate = order.getPlacedDate().getTime();
			
			for (LineItem item : order.getItems()) {
				OrderRequest.OrderItem oItem = new OrderRequest.OrderItem();
				oItem.lineNum = item.getLineNum();
				oItem.qty = item.getQuantity();
				oItem.sku = item.getProduct().getSku();
				
				req.items.add(oItem);
			}
			
			all.add(req);
		}

		return all;
	}
}
