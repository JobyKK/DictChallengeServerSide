package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.Dictionary;
import model.Statistics;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DictionaryDAO {

	private Integer userID;
	//private HibernateUtil hibernateUtil;
	private ArrayList<Dictionary> dictionaryList = null;
	
	public DictionaryDAO(Integer userID){
		this.userID = userID;
	}
	
	public JSONObject getJsonDictioanries() throws JSONException, SQLException{
		getDictionaries();
		JSONObject jo = new JSONObject();
		/*jo.addProperty("page", page);
		jo.addProperty("total", totalPageString);
		jo.addProperty("records", count);*/

		JSONArray jArray = new JSONArray();
		for(Dictionary dictionary : dictionaryList){
			Logger.getLogger("dao.DictionaryDAO").info(dictionary.getName() + "AJAJAJJAJJAJA");
			JSONObject row = new JSONObject();
			System.out.println(row.toString());
			row.put("name", dictionary.getName());
			row.put("owner", dictionary.getOwner());
			row.put("wordListPath", dictionary.getWordListPath());
			row.put("bestTime", dictionary.getBestTime());
			row.put("language1", dictionary.getLanguageByLanguage1().getId());
			row.put("language2", dictionary.getLanguageByLanguage2().getId());
			row.put("maxScore", dictionary.getMaxScore());
			//row.put("users_set", dictionary.getUserDictionaries());
			System.out.println(row.toString());
			jArray.put(row);
		}
		jo.put("rows", jArray);
		System.out.println(jo.toString());
		return jo;
	}
	
	public void getDictionaries(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			String query = "select d from Dictionary d "
					+ "where d.id in "
					+ "(select ud.id.dictionaryid from UserDictionary ud "
					+ "where ud.id.userid like :id)";
			tx = session.beginTransaction();
					List<Dictionary> dictionariesList = 
						session.createQuery(query).setParameter("id", userID).setTimeout(5).list();
					ArrayList<Dictionary> dictionaries = new ArrayList<Dictionary>();
					for(Dictionary dictionary : dictionariesList){
						//Logger.getLogger("dao.DictionaryDAO").info(dictionary.getName());
						dictionaries.add(dictionary);
					}
					dictionaryList = dictionaries;
		}catch(HibernateException e){
			if(tx != null) tx.rollback();
		}
		finally{
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
	}
	
}
