package avecode.persistence;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import avecode.model.ShopOrder;

public class ShopOrderDao implements IShopOrderDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void place(ShopOrder order) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(order);
		session.getTransaction().commit();
		session.close();
		session = null;
	}

	@Override
	public void update(ShopOrder order) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.merge(order);
		session.getTransaction().commit();
		session.close();
		session = null;
	}

	@Override
	public ShopOrder read(long orderNum) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		List orders = session.createCriteria(ShopOrder.class)
				.add(Restrictions.eq("orderNum",  orderNum))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		session.close();
		session = null;
	
		return orders.isEmpty() ? null : (ShopOrder) orders.get(0);
	}

	@Override
	public List<ShopOrder> read() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		List<ShopOrder> orders = session.createCriteria(ShopOrder.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		session.close();
		session = null;
		
		return orders.isEmpty() ? Collections.EMPTY_LIST : orders;
	}

}
