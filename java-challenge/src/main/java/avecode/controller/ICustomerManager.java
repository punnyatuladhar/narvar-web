package avecode.controller;

import java.util.List;

import avecode.model.ui.CustomerRequest;

public interface ICustomerManager {
	public String add(CustomerRequest customer);
	public List<CustomerRequest> get();
	public String update(String name, CustomerRequest req);
	public String delete(String name);
}
