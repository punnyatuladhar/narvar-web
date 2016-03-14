package avecode.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class ShopOrder implements Serializable{
	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "order_id", nullable = false, unique = true)
	private Integer id;
	
	@Column(nullable = false)
	private Long orderNum;
	
	@Column(nullable = false) 
	private Date placedDate;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LineItem> items = new ArrayList<LineItem>();

	public ShopOrder() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Date getPlacedDate() {
		return placedDate;
	}

	public void setPlacedDate(Date placedDate) {
		this.placedDate = placedDate;
	}

	public List<LineItem> getItems() {
		return items;
	}

	public void setItems(List<LineItem> items) {
		this.items = items;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof ShopOrder)) {
			return false;
		}
		
		if (this == obj) {
			return true;
		}
		
		ShopOrder that = (ShopOrder) obj;
		
		return this.orderNum == that.orderNum; 
	}
	
	@Override
	public int hashCode() {
		return 3 * (int)(this.orderNum%this.placedDate.hashCode());
	}
}
