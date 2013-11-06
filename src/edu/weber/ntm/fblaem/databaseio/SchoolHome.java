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
 * Home object for domain model class School.
 * @see edu.weber.ntm.fblaem.databaseio.School
 * @author Hibernate Tools
 */
public class SchoolHome {

	private static final Log log = LogFactory.getLog(SchoolHome.class);

	private final SessionFactory sessionFactory;
	public SchoolHome(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}


	public void persist(School transientInstance) {
		log.debug("persisting School instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(School instance) {
		log.debug("attaching dirty School instance");
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			tx.commit();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			tx.rollback();
			throw re;
		}
	}

	public void attachClean(School instance) {
		log.debug("attaching clean School instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(School persistentInstance) {
		log.debug("deleting School instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public School merge(School detachedInstance) {
		log.debug("merging School instance");
		try {
			School result = (School) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public School findById(java.lang.Integer id) {
		log.debug("getting School instance with id: " + id);
		Transaction  tx = sessionFactory.getCurrentSession().beginTransaction();
		try {
			School instance = (School) sessionFactory.getCurrentSession().get(
					"edu.weber.ntm.fblaem.databaseio.School", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			tx.commit();
			throw re;
		}
	}

	public List findByExample(School instance) {
		log.debug("finding School instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("edu.weber.ntm.fblaem.databaseio.School")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
