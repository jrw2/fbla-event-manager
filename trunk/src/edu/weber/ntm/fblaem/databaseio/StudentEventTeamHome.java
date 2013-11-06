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
 * Home object for domain model class StudentEventTeam.
 * @see edu.weber.ntm.fblaem.databaseio.StudentEventTeam
 * @author Hibernate Tools
 */
public class StudentEventTeamHome {

	private static final Log log = LogFactory
			.getLog(StudentEventTeamHome.class);

	private final SessionFactory sessionFactory;
	public StudentEventTeamHome(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public void persist(StudentEventTeam transientInstance) {
		log.debug("persisting StudentEventTeam instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(StudentEventTeam instance) {
		log.debug("attaching dirty StudentEventTeam instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StudentEventTeam instance) {
		log.debug("attaching clean StudentEventTeam instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(StudentEventTeam persistentInstance) {
		log.debug("deleting StudentEventTeam instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StudentEventTeam merge(StudentEventTeam detachedInstance) {
		log.debug("merging StudentEventTeam instance");
		try {
			StudentEventTeam result = (StudentEventTeam) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public StudentEventTeam findById(
			edu.weber.ntm.fblaem.databaseio.StudentEventTeamId id) {
		log.debug("getting StudentEventTeam instance with id: " + id);
		try {
			StudentEventTeam instance = (StudentEventTeam) sessionFactory
					.getCurrentSession().get(
							"edu.weber.ntm.fblaem.databaseio.StudentEventTeam",
							id);
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

	public List findByExample(StudentEventTeam instance) {
		log.debug("finding StudentEventTeam instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"edu.weber.ntm.fblaem.databaseio.StudentEventTeam")
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
