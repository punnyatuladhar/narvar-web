package avecode.model.ui;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductRequest {
	public String sku;
	public String name;
	public Float price;
}
