package avecode.model.ui;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderRequest {
	public long orderNum;
	public long placedDate;
	public List<OrderItem> items = new ArrayList<OrderItem>();
	
	@XmlRootElement
	public static class OrderItem {
		public int lineNum;
		public int qty;
		public String sku;
	}
}
