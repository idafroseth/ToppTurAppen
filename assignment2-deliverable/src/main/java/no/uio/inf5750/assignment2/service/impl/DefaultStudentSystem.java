package no.uio.inf5750.assignment2.service.impl;

import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateStudentDAO;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateCourseDAO;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateDegreeDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Service
@ContextConfiguration(classes = no.uio.inf5750.assignment2.configuration.StudentSystemConfiguration.class)
@Component("studentSystem")
public class DefaultStudentSystem implements StudentSystem {

	@Autowired
	private CourseDAO hibernateCourseDAO;
	@Autowired
	private StudentDAO hibernateStudentDAO;
	@Autowired
	private DegreeDAO hibernateDegreeDAO;
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(DefaultStudentSystem.class);

	@Override
	public int addCourse(String courseCode, String name) {
		// TODO Auto-generated method stub
		// Maybe have a check if the course already exists with that type and
		// value

		if (getCourseByCourseCode(courseCode) != null) {
			updateCourse(getCourseByCourseCode(courseCode).getId(), courseCode, name);
			return getCourseByCourseCode(courseCode).getId();
		} else if (getCourseByName(name) != null) {
			updateCourse(getCourseByName(name).getId(), courseCode, name);
			return getCourseByName(name).getId();
		} else {
			Course newCourse = new Course(courseCode.toUpperCase(), name);
			return hibernateCourseDAO.saveCourse(newCourse);
		}
	}

	@Transactional
	public void updateCourse(int courseId, String courseCode, String name) {
		// TODO Auto-generated method stub
		Course change = hibernateCourseDAO.getCourse(courseId);
		change.setName(name);
		change.setCourseCode(courseCode.toUpperCase());
	}

	@Transactional
	public Course getCourse(int courseId) {
		// TODO Auto-generated method stub
		return hibernateCourseDAO.getCourse(courseId);
	}

	@Transactional
	@Override
	public Course getCourseByCourseCode(String courseCode) {
		// TODO Auto-generated method stub
		return hibernateCourseDAO.getCourseByCourseCode(courseCode.toUpperCase());
	}

	@Transactional
	@Override
	public Course getCourseByName(String name) {
		// TODO Auto-generated method stub
		return hibernateCourseDAO.getCourseByName(name);
	}

	@Transactional
	@Override
	public Collection<Course> getAllCourses() {
		// TODO Auto-generated method stub
		// return (Collection) courses;
		return hibernateCourseDAO.getAllCourses();
	}

	@Transactional
	@Override
	public void delCourse(int courseId) {
		// TODO Auto-generated method stub
		// We have to check all dependencies to the course and delte these??
		// remove students from course

		String hqlStudents = "SELECT s from Student s " + "join s.courses c " + "where c.id = :courseId";
		Query queryStudents = sessionFactory.getCurrentSession().createQuery(hqlStudents);
		queryStudents.setInteger("courseId", courseId);
		List<Student> studentsAttendingCourse = queryStudents.list();
		for (Student student : studentsAttendingCourse) {
			removeAttendantFromCourse(courseId, student.getId());
		}

		for (Student student : hibernateCourseDAO.getCourse(courseId).getAttendants()) {
			removeAttendantFromCourse(courseId, student.getId());
		}
		String hql = "SELECT d from Degree d " + "join d.requiredCourses c " + "where c.id = :courseId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("courseId", courseId);
		List<Degree> degreesWithCourse = query.list();
		for (Degree degree : degreesWithCourse) {
			removeRequiredCourseFromDegree(degree.getId(), courseId);
		}

		// remove required courses in degree
		hibernateCourseDAO.delCourse(hibernateCourseDAO.getCourse(courseId));
	}

	@Transactional
	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		// // TODO Auto-generated method stub
		// //first check if the student exist
		Course course = getCourse(courseId);
		Set<Student> attendants;
		Student student = getStudent(studentId);
		Set<Course> courses;

		if (course == null || student == null) {
			return;
		}
		if (course.getAttendants().isEmpty()) {
			attendants = new HashSet();
		} else {
			attendants = course.getAttendants();
		}
		if (student.getCourses().isEmpty()) {
			courses = new HashSet();
		} else {
			courses = student.getCourses();
		}
		courses.add(course);
		student.setCourses(courses);
		attendants.add(student);
		course.setAttendants(attendants);
		sessionFactory.getCurrentSession().update(course);
		sessionFactory.getCurrentSession().update(student);
	}

	@Transactional
	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		// TODO Auto-generated method stub

		Course course = getCourse(courseId);
		Student student = getStudent(studentId);
		if (course == null || student == null) {
			logger.error("The course or student did not exist");
			return;
		}
		Set<Student> studentsInCourse = course.getAttendants();
		// Set<Course> courses = student.getCourses();
		// courses.remove(course);
		studentsInCourse.remove(student);
		sessionFactory.getCurrentSession().update(course);
		// sessionFactory.getCurrentSession().update(student);

	}

	@Transactional
	@Override
	public int addDegree(String type) {
		// TODO Auto-generated method stub
		return hibernateDegreeDAO.saveDegree(new Degree(type));
	}

	@Transactional
	@Override
	public void updateDegree(int degreeId, String type) {
		// TODO Auto-generated method stub
		// Create Query
		hibernateDegreeDAO.getDegree(degreeId);

	}

	@Transactional
	@Override
	public Degree getDegree(int degreeId) {
		// TODO Auto-generated method stub
		return hibernateDegreeDAO.getDegree(degreeId);
	}

	@Transactional
	@Override
	public Degree getDegreeByType(String type) {
		// TODO Auto-generated method stub
		return hibernateDegreeDAO.getDegreeByType(type);
	}

	@Transactional
	@Override
	public Collection<Degree> getAllDegrees() {
		// TODO Auto-generated method stub
		return hibernateDegreeDAO.getAllDegrees();
	}

	@Transactional
	@Override
	public void delDegree(int degreeId) {
		// TODO Auto-generated method stub
		// first remove all the attendants to the degree
		System.out.println("Trying to find students enrolled in degree");
		String hql = "SELECT s from Student s " + "join s.degrees d " + "where d.id = :degreeId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("degreeId", degreeId);
		List<Student> studentsAttendingCourse = query.list();
		for (Student student : studentsAttendingCourse) {
			removeDegreeFromStudent(student.getId(), degreeId);
		}

		// Then find all courses that are mandatory
		for (Course courseInDegree : getDegree(degreeId).getRequiredCourses()) {
			removeRequiredCourseFromDegree(degreeId, courseInDegree.getId());
		}
		// Then remove the degree
		hibernateDegreeDAO.delDegree(hibernateDegreeDAO.getDegree(degreeId));
	}

	@Override
	@Transactional
	public void addRequiredCourseToDegree(int degreeId, int courseId) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.getCurrentSession();
		Degree degree = getDegree(degreeId);
		if (degree == null) {
			return;
		}
		Set<Course> requiredCourses = degree.getRequiredCourses();
		if (requiredCourses.isEmpty()) {
			requiredCourses = new HashSet();
		}
		requiredCourses.add(getCourse(courseId));

		degree.setRequiredCourses(requiredCourses);
		session.update(degree);

	}

	@Transactional
	@Override
	public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Degree.class);
		Degree degree = getDegree(degreeId);
		if (degree == null) {
			return;
		}
		Set<Course> requiredCourses = degree.getRequiredCourses();
		if (requiredCourses.isEmpty()) {
			requiredCourses = new HashSet();
		}
		requiredCourses.remove(getCourse(courseId));
		degree.setRequiredCourses(requiredCourses);
		session.update(degree);

	}

	@Transactional
	@Override
	public int addStudent(String name) {
		// TODO Auto-generated method stub
		return hibernateStudentDAO.saveStudent(new Student(capitalize(name)));
	}

	@Transactional
	@Override
	public void updateStudent(int studentId, String name) {
		((HibernateStudentDAO) hibernateStudentDAO).updateStudent(studentId, capitalize(name));

		// TODO Auto-generated method stub
	}

	@Transactional
	@Override
	public Student getStudent(int studentId) {
		// TODO Auto-generated method stub
		return hibernateStudentDAO.getStudent(studentId);
	}

	@Transactional
	@Override
	public Student getStudentByName(String name) {
		// TODO Auto-generated method stub
		return hibernateStudentDAO.getStudentByName(capitalize(name));
	}

	@Transactional
	@Override
	public Collection<Student> getAllStudents() {
		// TODO Auto-generated method stub
		// return hibernateStudentDAO.getAllStudents();
		return hibernateStudentDAO.getAllStudents();
	}

	@Transactional
	@Override
	public void delStudent(int studentId) {
		// TODO Auto-generated method stub
		for (Course course : hibernateStudentDAO.getStudent(studentId).getCourses()) {
			removeAttendantFromCourse(course.getId(), studentId);
		}
		hibernateStudentDAO.delStudent(hibernateStudentDAO.getStudent(studentId));

	}

	@Transactional
	@Override
	public void addDegreeToStudent(int studentId, int degreeId) {
		// TODO Auto-generated method stub
		logger.debug("DEBUG****Trying to add degree to student");
		if (getDegree(degreeId) == null || getStudent(studentId) == null) {
			logger.error("The student or the degree did not exist");
			return;
		}
		if (studentFulfillsDegreeRequirements(studentId, degreeId)) {

			Student student = getStudent(studentId);
			Set<Degree> degrees = student.getDegrees();
			if (degrees.isEmpty()) {
				degrees = new HashSet();
			} else {
			}
			degrees.add(getDegree(degreeId));
			student.setDegrees(degrees);
			sessionFactory.getCurrentSession().update(student);

		} else {
			logger.debug("DEBUG****Failed to add student to degree");
		}

	}

	@Transactional
	@Override
	public void removeDegreeFromStudent(int studentId, int degreeId) {
		// TODO Auto-generated method stub

		if (getDegree(degreeId) == null) {
			logger.error("The degree with ID " + degreeId + " does not exist");
			return;
		}
		Student student = getStudent(studentId);
		Set<Degree> degrees;
		if (student.getDegrees().isEmpty()) {
			degrees = new HashSet();
		} else {
			degrees = student.getDegrees();
		}
		degrees.remove(getDegree(degreeId));
		student.setDegrees(degrees);
		sessionFactory.getCurrentSession().update(student);
	}
	
	@Transactional
	@Override
	public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {

		Student student = getStudent(studentId);
		Degree degree = getDegree(degreeId);

		Set<Course> studentHaveCourses = student.getCourses();
		Set<Course> degreeRequiredCourses = degree.getRequiredCourses();
		if (degreeRequiredCourses.isEmpty()) {
			return true;
		} else if (studentHaveCourses.isEmpty()) {
			return false;
		}

		if (studentHaveCourses.containsAll(degreeRequiredCourses)) {
			logger.debug("DEBUG****StudentFulfills degree Req");
			return true;
		} else {
			logger.debug("DEBUG**** Student do notFulfills degree Req");
			return false;
		}

	}
	@Transactional
	public void setStudentLocation(int studentId, String latitude, String longitude) {
		try{
			Student changeStudent = getStudent(studentId);
			changeStudent.setLongitude(longitude);
			changeStudent.setLatitude(latitude);
			sessionFactory.getCurrentSession().update(changeStudent);
		} catch (RuntimeException re) {
			logger.error("Attached failed" + re);
		}
	}

	private String capitalize(String name) {
		String[] names = name.split("\\s");
		if (name.length() == 0) {
			return "";
		} else {
			String capitalizedName = "";
			for (String s : names) {
				if (s.length() < 1) {
					capitalizedName += s.toUpperCase();
				} else {
					capitalizedName += " " + s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

				}
			}
			return capitalizedName;
		}

	}


	
}
