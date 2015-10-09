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
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = no.uio.inf5750.assignment2.configuration.RootConfig.class)
public class DegreeDAOTest {

	@Autowired
	private DegreeDAO hibernateDegreeDAO = null;

	Degree degree;

	@Before
	public void init() {
		degree = new Degree("test");
	}

	@Test
	public void degreeIsInjected() {
		assertNotNull("HibernateDegreeDAO is not injected " + hibernateDegreeDAO);
		// assertNotNull("SessionFacgtory is injected" +
		// hibernateDegreeDAO.sessionFactory);
	}
	@Test
	public void getAllDegrees() {
		Degree arrayDegree1 = new Degree("ArrayDegree1");
		Degree arrayDegree2 = new Degree("ArrayDegree2");
		hibernateDegreeDAO.saveDegree(arrayDegree1);
		hibernateDegreeDAO.saveDegree(arrayDegree2);
		List<Degree> fetchedCourses = (ArrayList<Degree>) hibernateDegreeDAO.getAllDegrees();
		assertEquals(2, fetchedCourses.size());
		assertTrue(fetchedCourses.contains(arrayDegree1));
		assertTrue(fetchedCourses.contains(arrayDegree2));
	}

	@Test
	public void saveDegree() {
		int id = hibernateDegreeDAO.saveDegree(degree);
		degree = hibernateDegreeDAO.getDegree(id);
		assertEquals(id, degree.getId());

		// Save save degree twice
		hibernateDegreeDAO.saveDegree(degree);
		int count = 0;
		for (Degree degreeInDb : hibernateDegreeDAO.getAllDegrees()) {
			if (degreeInDb.getType().equals(degree.getType())) {
				count++;
			}
		}
		assertEquals("There is more than one instance in the db", count, 1);
	}

	@Test
	public void delteDegree() {
		Degree degree2 = new Degree("test2");
		int id = hibernateDegreeDAO.saveDegree(degree2);
		degree2 = hibernateDegreeDAO.getDegree(id);
		assertNotNull("Null before deletion", hibernateDegreeDAO.getDegree(id));
		hibernateDegreeDAO.delDegree(degree2);
		assertNull("Null after deletion", hibernateDegreeDAO.getDegree(id));

		// Trying to add a degree that are not present
		// hibernateDegreeDAO.delDegree(degree2);
	}

	@Test
	public void getDegree() {
		Degree degree3 = new Degree("test3");
		int id = hibernateDegreeDAO.saveDegree(degree3);
		assertEquals(degree3, hibernateDegreeDAO.getDegreeByType("test3"));
		// Test to get a non existing degree
		assertNull(hibernateDegreeDAO.getDegreeByType(""));

	}

}
