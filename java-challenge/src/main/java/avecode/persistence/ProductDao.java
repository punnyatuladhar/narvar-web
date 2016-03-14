package avecode.persistence;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import avecode.model.Product;

public class ProductDao implements IProductDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void populate() {
		Product prod = new Product();
		prod.setName("iPad");
		prod.setSku("353453kl4hkjh345");
		prod.setPrice(23.32f);
		
		Product prod2 = new Product();
		prod2.setName("keyboard");
		prod2.setSku("36666666666hkjh345");
		prod2.setPrice(99.99f);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(prod);
		session.save(prod2);
		session.flush();
		session.clear();
		session.getTransaction().commit();
		session.close();
		session = null;
	}

	@Override
	public Product get(String sku) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		List prods = session.createCriteria(Product.class)
				.add(Restrictions.eq("sku",  sku))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		session.close();
		session = null;
		
		return prods.isEmpty() ? null : (Product) prods.get(0);
	}
	
	@Override
	public List<Product> getAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		List<Product> prods = session.createCriteria(Product.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		session.close();
		session = null;
		
		return prods.isEmpty() ? Collections.EMPTY_LIST : prods;
	}
}
