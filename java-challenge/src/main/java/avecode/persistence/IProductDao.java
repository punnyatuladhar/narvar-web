package avecode.persistence;

import java.util.List;

import avecode.model.Product;

public interface IProductDao {
	public void populate();
	public Product get(String sku);
	public List<Product> getAll();
}
