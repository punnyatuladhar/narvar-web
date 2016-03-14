package avecode.model.ui;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerRequest {
	public String name;
	public String street;
	public String city;
	public int zip;
}
