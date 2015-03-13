package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.HibernateUtil;
 
public class AppStartUp implements ServletContextListener{
 
    UsersData usersData = null;
 
    public void contextDestroyed(ServletContextEvent arg0) {
        // Do cleanup operations here
        usersData = null;
        System.out.println("Cleanup activity: PrintTask instance set to null");
    }
 
    public void contextInitialized(ServletContextEvent arg0) {
        // Invoke the daemon/background process code
    	new HibernateUtil();
        usersData = new UsersData();
        usersData.invokeCheckConnected();
    }
 
}