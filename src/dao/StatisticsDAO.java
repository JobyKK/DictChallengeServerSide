package dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Statistics;
import model.User;

public class StatisticsDAO {

	private Integer userID;
	//private HibernateUtil hibernateUtil;
	private Statistics statistics = null;
	
	public StatisticsDAO(Integer userID){
		this.userID = userID;
	}
	
	public JSONObject getJsonStatistics() throws JSONException, SQLException{
		getStatistics();
		JSONObject jo = new JSONObject();
		/*jo.addProperty("page", page);
		jo.addProperty("total", totalPageString);
		jo.addProperty("records", count);*/

		jo.put("id",statistics.getId());
		jo.put("maxScore",statistics.getMaxScore());
		jo.put("bestTime",statistics.getBestTime());
		jo.put("dictionariesCount",statistics.getDictionariesCount());
		jo.put("friendsCount",statistics.getFriendsCount());
		System.out.println(jo.toString());
		return jo;
	}
	
	public Statistics getStatistics(){
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = null;
			try{
				String query = "select s from Statistics s where s.id like :id";
				tx = session.beginTransaction();
				List<Statistics> statisticsList = 
						session.createQuery(query).setParameter("id", userID).list();
				for(Statistics s : statisticsList)
					statistics = s;
				//user.setStatistics(statistics);
			}catch(HibernateException e){
				if(tx != null) tx.rollback();
			}finally{
				session.close();
			}
		/*}
		if(user.isSignedIn()){
			Statistics statistics = user.getStatistics();
			items.add("Max score - " + statistics.getMaxScore());
			items.add("Best time - " + statistics.getBestTime());
			items.add("Dictionaries - " + statistics.getDictionariesCount());
			items.add("Friends - " + statistics.getFriendsCount());
		}*/
		return statistics;
	}
	
}
