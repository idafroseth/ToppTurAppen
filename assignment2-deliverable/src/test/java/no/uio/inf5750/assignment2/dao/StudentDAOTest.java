package no.uio.inf5750.assignment2.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = no.uio.inf5750.assignment2.configuration.StudentSystemConfiguration.class)
public class StudentDAOTest {

	@Autowired
	private StudentDAO hibernateStudentDAO = null;

	public StudentDAOTest() {

	}

	Student student;

	@Before
	public void init() {
		student = new Student("test");
	}

	@Test
	public void studentIsInjected() {
		assertNotNull("HibernateStudentDAO is injected " + hibernateStudentDAO);
		// assertNotNull("SessionFacgtory is injected" +
		// hibernateStudentDAO.sessionFactory);
	}
	@Test
	public void getAllStudents() {
		Student arrayStudent1 = new Student("ArrayStudent1");
		Student arrayStudent2 = new Student("ArrayStudent2");
		hibernateStudentDAO.saveStudent(arrayStudent1);
		hibernateStudentDAO.saveStudent(arrayStudent2);
		List<Student> fetchedCourses = (ArrayList<Student>) hibernateStudentDAO.getAllStudents();
		assertEquals(2, fetchedCourses.size());
		assertTrue(fetchedCourses.contains(arrayStudent1));
		assertTrue(fetchedCourses.contains(arrayStudent2));
	}
	@Test
	public void saveStudent() {
		int id = hibernateStudentDAO.saveStudent(student);
		student = hibernateStudentDAO.getStudent(id);
		assertEquals(id, student.getId());

		// Save same student twice
		hibernateStudentDAO.saveStudent(student);
		int count = 0;
		for (Student studentInDb : hibernateStudentDAO.getAllStudents()) {
			if (studentInDb.getName().toUpperCase().equals(student.getName().toUpperCase())) {
				count++;
			}
		}
		assertEquals("There is more than one instance in the db", count, 1);
	}

	@Test
	public void delteStudent() {
		Student student2 = new Student("test2");
		int id = hibernateStudentDAO.saveStudent(student2);
		student2 = hibernateStudentDAO.getStudent(id);
		assertNotNull("Null before deletion", hibernateStudentDAO.getStudent(id));
		hibernateStudentDAO.delStudent(student2);
		assertNull("Null after deletion", hibernateStudentDAO.getStudent(id));
	}

	@Test
	public void getStudent() {
		Student student3 = new Student("test3");
		int id = hibernateStudentDAO.saveStudent(student3);
		assertEquals(student3, hibernateStudentDAO.getStudentByName("test3"));

		// Test to get a non existing student
		assertNull(hibernateStudentDAO.getStudent(-id));
		assertNull(hibernateStudentDAO.getStudentByName(""));

	}


}