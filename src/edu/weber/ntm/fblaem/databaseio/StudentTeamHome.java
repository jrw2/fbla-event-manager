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
 * Home object for domain model class studentTeam.
 * @see edu.weber.ntm.fblaem.databaseio.StudentTeam
 * @author Hibernate Tools
 */
public class StudentTeamHome {

	private static final Log log = LogFactory
			.getLog(StudentTeamHome.class);

	private final SessionFactory sessionFactory;
	public StudentTeamHome(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	public void persist(StudentTeam transientInstance) {
		log.debug("persisting StudentTeams instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(StudentTeam instance) {
		log.debug("attaching dirty StudentTeam instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(StudentTeam instance) {
		log.debug("attaching clean StudentTeam instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(StudentTeam persistentInstance) {
		log.debug("deleting StudentTeam instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StudentTeam merge(StudentTeam detachedInstance) {
		log.debug("merging StudentTeam instance");
		try {
			StudentTeam result = (StudentTeam) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public StudentTeam findById(
			edu.weber.ntm.fblaem.databaseio.StudentTeamId id) {
		log.debug("getting studentTeam instance with id: " + id);
		try {
			StudentTeam instance = (StudentTeam) sessionFactory
					.getCurrentSession().get(
							"edu.weber.ntm.fblaem.databaseio.studentTeam",
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

	public List findByExample(StudentTeam instance) {
		log.debug("finding studentTeam instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"edu.weber.ntm.fblaem.databaseio.studentTeam")
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
