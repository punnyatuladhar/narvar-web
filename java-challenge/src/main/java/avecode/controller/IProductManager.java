package avecode.controller;

import java.util.List;

import avecode.model.ui.ProductRequest;

public interface IProductManager {
	public ProductRequest get(String sku);
	public List<ProductRequest> getAll();
}
