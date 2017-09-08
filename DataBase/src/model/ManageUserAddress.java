package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class ManageUserAddress {
	
	public ManageUserAddress(){
	}
	
	public void addUser(UserAddress userAddress){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.save(userAddress);
		session.getTransaction().commit();
		
		session.close();
	}
	
	public void deleteUserAddress(UserAddress address){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.delete(address);
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateUserAddress(UserAddress userAddress){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.update(userAddress);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<UserAddress> readUsersAddresses(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from UserAddress");
		List<UserAddress> addresses = query.list();
		
        session.getTransaction().commit();
		session.close();
		
		return addresses;
	}
}
