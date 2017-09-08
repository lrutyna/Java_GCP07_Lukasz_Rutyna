package model;



import java.util.List;



import org.hibernate.Session;
import org.hibernate.query.Query; 

public class ManageUser {
	
	public ManageUser(){
	}
	
	public void addUser(User user){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
	
	public void deleteUser(User user){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.delete(user);
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateUser(User user){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<User> readUsers(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from User");
		List<User> users = query.list();
		
        session.getTransaction().commit();
		session.close();
		
		return users;
	}
}
