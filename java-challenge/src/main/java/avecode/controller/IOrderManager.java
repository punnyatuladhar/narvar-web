package avecode.controller;

import java.util.List;

import avecode.model.ui.OrderRequest;

public interface IOrderManager {
	public void place(OrderRequest order);	
	public void update(OrderRequest order);
	public OrderRequest read(String orderNum);
	public List<OrderRequest> read();
}
