package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import java.util.List;

@Transactional
@Component("hibernateStudentDAO")
public class HibernateStudentDAO implements StudentDAO {
	static Logger logger = Logger.getLogger(HibernateCourseDAO.class);

	@Autowired
	public SessionFactory sessionFactory;
	
	public HibernateStudentDAO() {

	}

	public HibernateStudentDAO(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	@Override
	public int saveStudent(Student student) {
		// TODO Auto-generated method stub
		try {
			if ((getStudentByName(student.getName())) == null) { // &&student.getName().isEmpty()){
				int id = (Integer) sessionFactory.getCurrentSession().save(student);
				return id;
			} else {
				return -1;
			}

		} catch (RuntimeException re) {
			logger.error("Attached failed" + re);
			return -1;
		}

	}

	@Override
	public Student getStudent(int id) {
		// TODO Auto-generated method stub
		try {
			return (Student) sessionFactory.getCurrentSession().get(Student.class, id);
		} catch (RuntimeException re) {
			logger.error("Attached failed" + re);

			return null;
		}
	}

	@Override
	public Student getStudentByName(String name) {
		// TODO Auto-generated method stub
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Student.class);
			criteria.add(Restrictions.eq("name", name));
			return (Student) criteria.uniqueResult();
		} catch (RuntimeException re) {
			logger.error("Attached failed" + re);

			return null;
		}
	}

	@Override
	@Transactional
	public Collection<Student> getAllStudents() {
		// TODO Auto-generated method stub
		try {
			return (List<Student>) sessionFactory.getCurrentSession().createQuery("FROM Student ORDER by id DESC")
					.list();
		} catch (RuntimeException re) {
			logger.error("Attached failed" + re);

			return null;
		}
	}

	@Override
	public void delStudent(Student student) {
		// TODO Auto-generated method stub
		try {
			sessionFactory.getCurrentSession().delete(student);
		} catch (RuntimeException re) {
			logger.error("Attached failed" + re);

		}

	}

	public void updateStudent(int studentId, String name) {
		try {
			Student changeStudent = getStudent(studentId);
			changeStudent.setName(name);
			sessionFactory.getCurrentSession().update(changeStudent);
		} catch (RuntimeException re) {
			logger.error("Attached failed" + re);

		}
	}

}
