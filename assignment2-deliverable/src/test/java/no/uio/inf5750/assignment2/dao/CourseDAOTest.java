package no.uio.inf5750.assignment2.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = no.uio.inf5750.assignment2.configuration.RootConfig.class)
public class CourseDAOTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	CourseDAO hibernateCourseDAO = null;

	Course course;

	@Before
	public void init() {
		course = new Course("test", "test");
	}

	@Test
	public void courseIsInjected() {
		assertNotNull("HibernateCourseDAO is injected " + hibernateCourseDAO);
	}
	@Test
	public void getAllCourses() {
		Course course4 = new Course("test4", "test4");
		Course course5 = new Course("test5", "test5");
		hibernateCourseDAO.saveCourse(course4);
		hibernateCourseDAO.saveCourse(course5);
		List<Course> fetchedCourses = (ArrayList<Course>) hibernateCourseDAO.getAllCourses();
		assertEquals(2, fetchedCourses.size());
		assertTrue(fetchedCourses.contains(course4));
		assertTrue(fetchedCourses.contains(course5));
	}

	@Test
	public void saveCourse() {
		int id = hibernateCourseDAO.saveCourse(course);
		course = hibernateCourseDAO.getCourse(id);
		assertEquals(id, course.getId());
		// Save save course twice
		hibernateCourseDAO.saveCourse(course);
		int count = 0;
		for (Course courseInDb : hibernateCourseDAO.getAllCourses()) {
			if (courseInDb.getName().equals(course.getName())) {
				count++;
			}
		}
		assertEquals("There is more than one instance in the db", count, 1);

		// Save a empty course
		course = new Course();
		hibernateCourseDAO.saveCourse(course);
	}

	@Test
	public void delteCourse() {
		Course course2 = new Course("test2", "test2");
		int id = hibernateCourseDAO.saveCourse(course2);
		course2 = hibernateCourseDAO.getCourse(id);
		assertNotNull("Null before deletion", hibernateCourseDAO.getCourse(id));
		hibernateCourseDAO.delCourse(course2);
		assertNull("Null after deletion", hibernateCourseDAO.getCourse(id));
	}

	@Test
	public void getCourse() {
		Course course3 = new Course("test3", "test3");
		int id = hibernateCourseDAO.saveCourse(course3);
		assertEquals(course3, hibernateCourseDAO.getCourseByCourseCode("test3"));
		assertEquals(course3, hibernateCourseDAO.getCourseByName("test3"));
		assertNull(hibernateCourseDAO.getCourseByName(""));

	}

}
