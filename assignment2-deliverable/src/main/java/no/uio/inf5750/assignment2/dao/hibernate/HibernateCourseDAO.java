package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
//import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;

@Service
@Transactional
@Component("hibernateCourseDAO")
public class HibernateCourseDAO implements CourseDAO {

	static Logger logger = Logger.getLogger(HibernateCourseDAO.class);

	@Autowired
	public SessionFactory sessionFactory;

	@Autowired
	public HibernateTransactionManager transactionManager;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public int saveCourse(Course course) {
		// TODO Auto-generated method stub
		logger.debug("Saving course");
		try {
			if (getCourseByCourseCode(course.getCourseCode()) == null && !course.getCourseCode().isEmpty()
					&& !course.getName().isEmpty()) {
				return (Integer) sessionFactory.getCurrentSession().save(course);
			}
			return -1;
		} catch (Exception re) {
			logger.error("Attached failed" + re);
			return -1;
		}
	}

	@Override
	public Course getCourse(int id) {
		// TODO Auto-generated method stub
		try {
			return (Course) sessionFactory.getCurrentSession().get(Course.class, id);
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

			return null;
		}
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		// NEW QUERY
		try {

			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Course.class);
			criteria.add(Restrictions.eq("courseCode", courseCode));
			return (Course) criteria.uniqueResult();
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

			return null;
		}
	}

	@Override
	public Course getCourseByName(String name) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Course.class);
			criteria.add(Restrictions.eq("name", name));
			return (Course) criteria.uniqueResult();
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

			return null;
		}
	}

	@Override
	@Transactional
	public Collection<Course> getAllCourses() {
		// TODO Auto-generated method stub
		try {
			return (List<Course>) sessionFactory.getCurrentSession().createQuery("FROM Course ORDER by id DESC").list();
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

			return null;
		}
	}

	@Override
	public void delCourse(Course course) {
		try {
			sessionFactory.getCurrentSession().delete(course);
		} catch (RuntimeException e) {
			logger.error("Attached failed " + e);

		}
	}
}
