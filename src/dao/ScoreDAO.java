package dao;

import model.UserDictionary;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ScoreDAO {

	public static void submitScore(Float score,Integer userId, Integer DictionaryId){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
		String query = "select ud from UserDictionary ud where ud.id.userid like :id and "
				+ "ud.id.dictionaryid like :dictid"; 
		tx = session.beginTransaction();
		//user.getChosenDictionary().getId()
		UserDictionary ud = (UserDictionary) session.createQuery(query).setParameter("id", userId).
				setParameter("dictid", DictionaryId).list().get(0);
		ud.setMaxScore(score);
		session.update(ud);
		tx.commit();
		}catch (HibernateException e) {
	         if (tx != null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	}
	
}
