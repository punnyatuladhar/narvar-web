package avecode.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import avecode.model.Product;
import avecode.model.ui.ProductRequest;
import avecode.persistence.IProductDao;

public class ProductManager implements IProductManager {

	@Autowired
	IProductDao productDao;
	
	@Override
	public ProductRequest get(String sku) {
		// TODO Auto-generated method stub
		Product prod = productDao.get(sku);
		if (null == prod) {
			return null;
		}
		
		ProductRequest req = new ProductRequest();
		req.name = prod.getName();
		req.price = prod.getPrice();
		req.sku = prod.getSku();
		
		return req;
	}

	@Override
	public List<ProductRequest> getAll() {
		// TODO Auto-generated method stub
		List<Product> prods = productDao.getAll();
		List<ProductRequest> reqs = new ArrayList<ProductRequest>();
		
		for (Product prod : prods) {
			ProductRequest req = new ProductRequest();
			req.name = prod.getName();
			req.price = prod.getPrice();
			req.sku = prod.getSku();
			
			reqs.add(req);
		}
		
		return reqs;
	}
}
