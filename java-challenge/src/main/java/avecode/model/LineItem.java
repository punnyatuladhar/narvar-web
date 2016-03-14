package avecode.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class LineItem implements Serializable{
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private Integer id;

	@Column (nullable = false)
	private Integer lineNum;
	
	@Column (nullable = false)
	private Integer quantity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private ShopOrder order;

	public LineItem() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ShopOrder getOrder() {
		return order;
	}

	public void setOrder(ShopOrder order) {
		this.order = order;
	}
	
	public Integer getLineNum() {
		return lineNum;
	}

	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof LineItem)) {
			return false;
		}
		
		if (this == obj) {
			return true;
		}
		
		LineItem that = (LineItem) obj;
		
		return this.lineNum == that.lineNum && this.order.equals(that.order) && this.product.equals(that.product); 
	}
	
	@Override
	public int hashCode() {
		return 31 * this.product.hashCode()/this.order.hashCode() + this.lineNum;
	}
}
