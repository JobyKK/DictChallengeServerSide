package controller;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.mysql.jdbc.log.Log;

import java.util.logging.Logger;

import model.User;

public class UsersData {

	private static LinkedList<User> users;

	public UsersData(){
		users = new LinkedList<User>();
	}
	
	public static void addUser(User user){
		users.add(user);
	}
	
	public static LinkedList<User> getUsers(){
		return users;
	}
	
	public static void invokeCheckConnected(){
		/*Logger.getLogger("controller.UsersData").info("start checking");
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			scheduler.scheduleAtFixedRate(
					new Runnable() {
						public void run() { checkConnected(); }
					}, 
					0,
					10,
					TimeUnit.SECONDS);*/
    }//perfetto!
		
	private static void checkConnected()
	{
		Logger.getLogger("controller.UsersData").info("checked");
		for(int i = 0;i < users.size();i++){
			// Check connection, remove on dead
				if(users.get(i).getSession() == null){
					System.out.println(users.get(i)+" removed due to lack of connection.");
				    users.remove(i);
				}
			}
	}
	
}
