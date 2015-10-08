package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Course;

@Transactional
@Component("hibernateDegreeDAO")
public class HibernateDegreeDAO implements DegreeDAO {
	static Logger logger = Logger.getLogger(HibernateCourseDAO.class);

	@Autowired
	public SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public int saveDegree(Degree degree) {
		// TODO Auto-generated method stub
		try {
			if (getDegreeByType(degree.getType()) == null && !degree.getType().isEmpty()) {
				return (Integer) sessionFactory.getCurrentSession().save(degree);
			}
			return -1;
		} catch (Exception e) {
			logger.error("Attached failed" + e);
			return -1;
		}

	}

	@Override
	public Degree getDegree(int id) {
		// TODO Auto-generated method stub
		try {
			return (Degree) sessionFactory.getCurrentSession().get(Degree.class, id);
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

			return null;

		}
	}

	@Override
	public Degree getDegreeByType(String type) {
		// TODO Auto-generated method stub
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Degree.class);
			criteria.add(Restrictions.eq("type", type));
			return (Degree) criteria.uniqueResult();
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

			return null;
		}
	}

	@Override
	@Transactional
	public Collection<Degree> getAllDegrees() {
		// TODO Auto-generated method stub
		try {
			return (List<Degree>) sessionFactory.getCurrentSession().createQuery("FROM Degree ORDER by id DESC").list();
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

			return null;
		}
	}

	@Override
	public void delDegree(Degree degree) {
		// TODO Auto-generated method stub
		try {
			if (degree == null) {
				logger.error("The deleted degree did not exist");
			}
			sessionFactory.getCurrentSession().delete(degree);
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

		}
	}

}
