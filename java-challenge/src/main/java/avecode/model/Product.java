package avecode.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Product implements Serializable {
	private static final long serialVersionUID = 7381724607300234811L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "product_id", nullable = false, unique = true)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String sku;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, precision = 2)
	private Float price;

	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public String getSku() {
		return sku;
	}



	public void setSku(String sku) {
		this.sku = sku;
	}



	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof Product)) {
			return false;
		}
		
		if (this == obj) {
			return true;
		}
		
		Product that = (Product) obj;
		
		return this.sku.equals(that.sku); 
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 17 * this.name.hashCode()/this.price.hashCode();
	}
}
