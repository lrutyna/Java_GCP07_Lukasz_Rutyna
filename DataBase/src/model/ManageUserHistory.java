package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class ManageUserHistory {

	public ManageUserHistory(){
	}
	
	public void addUserHistory(UserHistory userHistory){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.save(userHistory);
		session.getTransaction().commit();
		
		session.close();
	}
	
	public void deleteUserHistory(UserHistory history){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.delete(history);
		session.getTransaction().commit();
		session.close();
	}
	
	public void updateUserHistory(UserHistory userHistory){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.update(userHistory);
		session.getTransaction().commit();
		session.close();
	}

	public List<UserHistory> readUserHistory() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from UserHistory");
		List<UserHistory> histories = query.list();
		
        session.getTransaction().commit();
		session.close();
		
		return histories;
	}
}
