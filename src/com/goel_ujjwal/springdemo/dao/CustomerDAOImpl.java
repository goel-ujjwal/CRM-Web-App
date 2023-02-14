package com.goel_ujjwal.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goel_ujjwal.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		Session session = sessionFactory.getCurrentSession();

		Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);

		List<Customer> customers = query.getResultList();

		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		Session session = sessionFactory.getCurrentSession();
		
		// save the customer if its 'id' is empty: means new customer
		// update the customer if its 'id' is not empty
		session.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Customer theCustomer = session.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.createQuery("delete from Customer where id=:customerId").setParameter("customerId", theId).executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		Session session = sessionFactory.getCurrentSession();

		Query<Customer> query = null;
		
		// search if theSearchName is not empty
		if(theSearchName != null && theSearchName.trim().length() > 0) {
			
			// search for firstName or lastName...ignoring upper/lower case
			query = session.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class)
						   .setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		}
		// if theSearchName is empty return list of all customers
		else {	
			query = session.createQuery("from Customer", Customer.class);
		}
		
		// execute the query
		List<Customer> customers = query.getResultList();
		return customers;
	}

}
