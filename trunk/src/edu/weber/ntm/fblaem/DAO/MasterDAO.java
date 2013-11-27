package edu.weber.ntm.fblaem.DAO;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.weber.ntm.fblaem.databaseio.HibernateUtil;
import edu.weber.ntm.fblaem.databaseio.Login;
import edu.weber.ntm.fblaem.databaseio.Teacher;

public abstract class MasterDAO {

	protected static Session sf;
	protected HttpServletRequest request;
	protected Transaction tx;
	protected Login login;
	protected Teacher teacher;
	
	public MasterDAO() {
	}
	
	public MasterDAO(HttpServletRequest request) {
	}
	
	public abstract void process();
	
	protected void getUser(String remoteUser){
		
		this.login = (Login) sf.createQuery("from Login as login where login.username=" + remoteUser);
		
	}
	
	protected void endSession(){
		
		tx.commit();
		
	}
	
	// Sessions
	protected void initializeSession(){
		
		sf = HibernateUtil.getSessionFactory().getCurrentSession();
		tx = sf.beginTransaction();
		getUser(request.getRemoteUser());
		teacher = login.getTeacher(); // Teacher / Admin
		
	}

}
