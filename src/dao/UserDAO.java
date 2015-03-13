package dao;
 
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import sun.util.logging.resources.logging;
 
public class UserDAO {
	public User user = null;
	
	
	
	public UserDAO(){
		
	}
	
	public List<User> getUsers(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> users = null;
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         users = session.createQuery("FROM User").list(); 
	         for (User user : users){
	            System.out.println("Email: " + user.getEmail()); 
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return users;
	}
 
	public boolean checkUser(String email, String password){
		boolean flag = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			String query = "select u from User u where u.email like :email"; 
			tx = session.beginTransaction();
			List<User> users = session.createQuery(query).setParameter("email", email).list();
			for (User u : users)
				if(u.getPassword().equals(password)){
					Logger.getLogger("dao.UserDAO").info(u.getEmail() + " " + u.getPassword());
					flag = true;
					user = u;
				}
			tx.commit();
		}catch (HibernateException e) {
	        if (tx != null) tx.rollback();
	        e.printStackTrace(); 
	    }finally {
	    	session.close(); 
	    }
		return flag;
	}
	
	
}