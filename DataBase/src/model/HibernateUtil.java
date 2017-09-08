package model;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

		private static SessionFactory sessionFactory;
		private static StandardServiceRegistryBuilder serviceRegistry;
		
		static{
			try {
				Configuration configuration = new Configuration().configure();
				configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(UserAddress.class);
				configuration.addAnnotatedClass(UserHistory.class);
				
				serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
				sessionFactory = configuration.buildSessionFactory(serviceRegistry.build());

			} catch (HibernateException e) {

				System.err.println("Nie udalo sie stworzyc obiektu SessionFactory");
		    }
		}

		public static SessionFactory getSessionFactory() {
			return sessionFactory;
		}
	}

