package edu.weber.ntm.fblaem.databaseio;

// Generated Nov 1, 2013 7:05:48 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Event.
 * @see edu.weber.ntm.fblaem.databaseio.Event
 * @author Hibernate Tools
 */
public class EventHome {

	private static final Log log = LogFactory.getLog(EventHome.class);

	private final SessionFactory sessionFactory;
	public EventHome(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public void persist(Event transientInstance) {
		log.debug("persisting Event instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Event instance) {
		log.debug("attaching dirty Event instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Event instance) {
		log.debug("attaching clean Event instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Event persistentInstance) {
		log.debug("deleting Event instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Event merge(Event detachedInstance) {
		log.debug("merging Event instance");
		try {
			Event result = (Event) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Event findById(java.lang.Integer id) {
		log.debug("getting Event instance with id: " + id);
		try {
			Event instance = (Event) sessionFactory.getCurrentSession().get(
					"edu.weber.ntm.fblaem.databaseio.Event", id);
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

	public List findByExample(Event instance) {
		log.debug("finding Event instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("edu.weber.ntm.fblaem.databaseio.Event")
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
