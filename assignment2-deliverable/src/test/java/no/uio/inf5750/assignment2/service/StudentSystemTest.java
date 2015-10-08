package no.uio.inf5750.assignment2.service;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.collection.PersistentSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import no.uio.inf5750.assignment2.model.*;

/**
 * All the test regarding insertion and deletion are really done in each
 * component test classes, like course, student and degree, and it is no point
 * in repeating these test But the logic with case-insensitivity are example of
 * functionality that are in the studentsystem logic, and this is what I will
 * test here.
 * 
 * @author Ida Marie Frøseth
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = no.uio.inf5750.assignment2.configuration.StudentSystemConfiguration.class)
public class StudentSystemTest {

	@Autowired
	StudentSystem studentSystem = null;

	Degree degree1 = new Degree("degree1");
	Degree degree2 = new Degree("degree2");
	Student student1 = new Student("student1");
	Student student2 = new Student("student2");
	Course course1 = new Course("CORUSE1", "course1");
	Course course2 = new Course("COURSE2", "course2");

	@Before
	public void init() {
		degree1.setId(studentSystem.addDegree(degree1.getType()));
		degree2.setId(studentSystem.addDegree(degree2.getType()));
		course1.setId(studentSystem.addCourse(course1.getCourseCode(), course1.getName()));
		course2.setId(studentSystem.addCourse(course2.getCourseCode(), course2.getName()));
		student1.setId(studentSystem.addStudent(student1.getName()));
		student2.setId(studentSystem.addStudent(student2.getName()));
	}

	@Test
	public void isInjected() {
		assertNotNull("Student system bean is null", studentSystem);
	}

	@Test
	public void insertCourse() {
		// Course course1 = new Course("INF1","test1");

		int id = studentSystem.addCourse("INF1", "test1");
		assertNotNull("The course id null", studentSystem.getCourse(id));

		// Insert same course three times
		assertNotNull("The course id null", studentSystem.getCourseByName(course1.getName()));
		studentSystem.addCourse(course1.getCourseCode(), course1.getName());
		int count = 0;
		for (Course courseInDb : studentSystem.getAllCourses()) {
			if (courseInDb.getName().toUpperCase().equals(course1.getName().toUpperCase())) {
				count++;
			}
		}
		assertEquals("There is more than one instance in the db", 1, count);

		// Insert a course with a null name
		int id2 = 50;
		id2 = studentSystem.addCourse("", null);
		assertNull(studentSystem.getCourse(id2));
		assertEquals(id2, -1);
	}

	public void inCaseSensitivity() {
		assertEquals(studentSystem.getCourseByName("course1"), studentSystem.getCourseByName("cOuRse1"));
		assertEquals(studentSystem.getCourseByCourseCode("CORUSE1"), studentSystem.getCourseByCourseCode("cOuRse1"));
		assertEquals(studentSystem.getStudentByName("student1"), studentSystem.getStudentByName("sTudEnt1"));
		assertEquals(studentSystem.getDegreeByType("degree1"), studentSystem.getDegreeByType("dEgRee1"));
	}

	@Test
	public void testingStudentCourseRelation() {
		Student student3 = new Student("student3");
		student3.setId(studentSystem.addStudent("student3"));
		// assertNotNull(studentSystem.getCourse(course1.getId()));
		assertEquals(student3.getId(), studentSystem.getStudentByName(student3.getName()).getId());
		studentSystem.addAttendantToCourse(course1.getId(), student3.getId());
		Set<Course> courses = studentSystem.getStudent(student3.getId()).getCourses();
		assertTrue(courses.contains(studentSystem.getCourse(course1.getId())));

		// Add the same attendant to the same course and check that it is only
		// one course present
		studentSystem.addAttendantToCourse(course1.getId(), student3.getId());
		courses = studentSystem.getStudent(student3.getId()).getCourses();
		int count = 0;
		for (Course course : courses) {
			count++;
		}
		assertEquals(1, count);

		// remove course with attendants
		studentSystem.delCourse(course1.getId());
		courses = studentSystem.getStudent(student3.getId()).getCourses();
		assertFalse(courses.contains(studentSystem.getCourse(course1.getId())));

		// Delete a student that attend at course
		studentSystem.addAttendantToCourse(course2.getId(), student3.getId());
		Set<Student> studentsInCourse = studentSystem.getCourse(course2.getId()).getAttendants();
		assertTrue(studentsInCourse.contains(studentSystem.getStudent(student3.getId())));
		studentSystem.delStudent(student3.getId());
		assertFalse(studentsInCourse.contains(studentSystem.getStudent(student3.getId())));

	}

	@Test
	public void testingStudentDegreeRelation() {

		int studentId = studentSystem.getStudentByName(student1.getName()).getId();
		int degreeId = studentSystem.getDegreeByType(degree1.getType()).getId();
		int courseId = studentSystem.getCourseByName(course2.getName()).getId();

		// test to enroll a student to a degree
		studentSystem.addAttendantToCourse(courseId, studentId);
		studentSystem.addRequiredCourseToDegree(degreeId, courseId);
		studentSystem.addDegreeToStudent(studentId, degreeId);
		Set<Degree> degrees = studentSystem.getStudent(studentId).getDegrees();
		assertTrue(degrees.contains(studentSystem.getDegree(degreeId)));

		// remove degree where students are enrolled and have required courses
		Degree deltedDegree = studentSystem.getDegree(degreeId);
		studentSystem.delDegree(degreeId);
		degrees = studentSystem.getStudent(studentId).getDegrees();
		assertFalse(degrees.contains(deltedDegree));
	}

	/**
	 * Testing the relationship between course, degree and students and that
	 * there is a clean up if the course is deleted.
	 */
	@Test
	public void testingCourseDegreeRelation() {
		Course course5 = new Course("course5", "course5");
		Student student5 = new Student("student5");
		Degree degree5 = new Degree("degree5");
		int courseId = studentSystem.addCourse(course5.getCourseCode(), course5.getName());
		int studentId = studentSystem.addStudent(student5.getName());
		int degreeId = studentSystem.addDegree(degree5.getType());

		studentSystem.addRequiredCourseToDegree(degreeId, courseId);
		studentSystem.addAttendantToCourse(courseId, studentId);
		studentSystem.addDegreeToStudent(studentId, degreeId);

		// testing that the course is in the required courses list for the
		// degree and that the student are enrolled in the course
		assertTrue(studentSystem.getDegree(degreeId).getRequiredCourses().contains(studentSystem.getCourse(courseId)));
		assertTrue(studentSystem.getStudent(studentId).getCourses().contains(studentSystem.getCourse(courseId)));

		studentSystem.delCourse(courseId);
		// testing that the course is removed from the required courses list
		// after deletion
		assertFalse(studentSystem.getDegree(degreeId).getRequiredCourses().contains(studentSystem.getCourse(courseId)));
		// testing that the student is not enrolled in the course after deletion
		assertFalse(studentSystem.getStudent(studentId).getCourses().contains(studentSystem.getCourse(courseId)));

	}
}
