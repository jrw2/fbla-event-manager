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
 * Home object for domain model class Team.
 * @see edu.weber.ntm.fblaem.databaseio.Team
 * @author Hibernate Tools
 */
public class TeamHome {

	private static final Log log = LogFactory.getLog(TeamHome.class);

	private final SessionFactory sessionFactory;
	public TeamHome(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public void persist(Team transientInstance) {
		log.debug("persisting Team instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Team instance) {
		log.debug("attaching dirty Team instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Team instance) {
		log.debug("attaching clean Team instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Team persistentInstance) {
		log.debug("deleting Team instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Team merge(Team detachedInstance) {
		log.debug("merging Team instance");
		try {
			Team result = (Team) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Team findById(java.lang.Integer id) {
		log.debug("getting Team instance with id: " + id);
		try {
			Team instance = (Team) sessionFactory.getCurrentSession().get(
					"edu.weber.ntm.fblaem.databaseio.Team", id);
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

	public List findByExample(Team instance) {
		log.debug("finding Team instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("edu.weber.ntm.fblaem.databaseio.Team")
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
