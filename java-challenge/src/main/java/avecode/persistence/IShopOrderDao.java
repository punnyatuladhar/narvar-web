package avecode.persistence;

import java.util.List;

import avecode.model.ShopOrder;

public interface IShopOrderDao {
	public void place(ShopOrder order);	
	public void update(ShopOrder order);
	public ShopOrder read(long orderNum);
	public List<ShopOrder> read();
}
