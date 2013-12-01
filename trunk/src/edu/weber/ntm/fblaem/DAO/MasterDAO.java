package edu.weber.ntm.fblaem.DAO;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;

import edu.weber.ntm.fblaem.databaseio.EventInstance;
import edu.weber.ntm.fblaem.databaseio.HibernateUtil;
import edu.weber.ntm.fblaem.databaseio.Login;
import edu.weber.ntm.fblaem.databaseio.Teacher;

public abstract class MasterDAO {
	
	protected static final String TYPE_EVENT_REGISTRATION = "eventRegistration";
	protected static final String TYPE_ADMINISTRATION = "admin";

	protected static Session sf;
	protected HttpServletRequest request;
	protected Transaction tx;
	protected Login login;
	protected Teacher teacher;
	
	public MasterDAO() {
	}
	
	public MasterDAO(HttpServletRequest request) {
		this.request = request;
	}
	
	public abstract void process();
	
	// Sessions
	protected void initializeSession(){
		
		sf = HibernateUtil.getSessionFactory().getCurrentSession();
		tx = sf.beginTransaction();
		getUser(request.getRemoteUser());
		teacher = login.getTeacher(); // Teacher / Admin
		
	}
	
	protected void endSession(){
		
		tx.commit();
		
	}

	protected void getUser(String remoteUser){
		
		this.login = (Login) sf.createQuery("from Login as login where login.username='" + remoteUser + "'").uniqueResult();

	}
	
}	
