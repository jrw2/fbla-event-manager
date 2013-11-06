package edu.weber.ntm.fblaem.databaseio;

// Generated Nov 1, 2013 7:05:48 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Student.
 * @see edu.weber.ntm.fblaem.databaseio.Student
 * @author Hibernate Tools
 */
public class StudentHome {

	private static final Log log = LogFactory.getLog(StudentHome.class);

	private final SessionFactory sessionFactory;
	public StudentHome(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public void persist(Student transientInstance) {
		log.debug("persisting Student instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Student instance) {
		log.debug("attaching dirty Student instance");
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			tx.rollback();
			throw re;
		}
		tx.commit();
	}

	public void attachClean(Student instance) {
		log.debug("attaching clean Student instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Student persistentInstance) {
		log.debug("deleting Student instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Student merge(Student detachedInstance) {
		log.debug("merging Student instance");
		try {
			Student result = (Student) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Student findById(java.lang.Integer id) {
		log.debug("getting Student instance with id: " + id);
		try {
			Student instance = (Student) sessionFactory.getCurrentSession()
					.get("edu.weber.ntm.fblaem.databaseio.Student", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Student instance) {
		log.debug("finding Student instance by example");
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("edu.weber.ntm.fblaem.databaseio.Student")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			tx.commit();
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			tx.commit();
			throw re;
		}
	}
}
