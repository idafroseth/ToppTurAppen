package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

//Controller to serve REST resources
@Controller
@EnableWebMvc
@RequestMapping("/api")
public class ApiController extends WebMvcConfigurerAdapter{

	@Autowired
	private StudentSystem studentSystem;

	
//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> httpMessageConverters) {
//        httpMessageConverters.add(new BookCaseMessageConverter(new MediaType("text", "csv")));
//    }

	//There is basically two way of doing rest in Spring. One is content negotiation where a view render the model 
	//into a representation. Here we define the content negotiation that are used by the view or 
	//The second approach is by message conversion where a message converter transform an object returned from the controller
	//Then we use @ResponseBody before the return value def or over the method header
//	@Override
//	public void configureContentNegotiation(
//	    ContentNegotiationConfigurer configurer) {
//	  configurer.defaultContentType(MediaType.APPLICATION_JSON);
//	}
	
	
	// It should return a collection of all students in at leat the JSON format,
	// Make sure that there i no loop when a student refer to a course and a
	// course refer to a student by stopping the reqursion
	// Should it be the users name or student id??
	//When using @ResponseBody we ensure that spring does not use a view to render the response, but use
	//The message converter that suits best (We are going to return json in this execerise)
	@RequestMapping(value = "/student/{user}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Student getStudentByUsername(@PathVariable Integer user) {
		Student student = studentSystem.getStudent(user);
		return student;
	}

	@RequestMapping(value = "/student", method = RequestMethod.GET, produces="application/json")
	public	@ResponseBody Collection<Student> getAllStudents() {
		System.out.println("******************GETTING ALL THE STUDENTS*********");
		return studentSystem.getAllStudents();
	}

	@RequestMapping(value = "/student/{student}/location", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Collection<Student> getLocation(@PathVariable Integer student, @RequestParam(value = "latitude", required = false) String latitude,
			@RequestParam(value = "longitude", required = false) String longitude) {
		System.out.println("***********************");
		System.out.println("Student ID" + student);
		System.out.println("Latitude:" + latitude);
		System.out.println("Longitude:" + longitude);
		studentSystem.setStudentLocation(student, latitude, longitude);
		return studentSystem.getAllStudents();
	}

	@RequestMapping(value = "/course", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Collection<Course> getCourse() {
		Collection<Course> courses = studentSystem.getAllCourses();
		return courses;
	}

	@RequestMapping(value = "/degree", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody Collection<Degree> getDegree() {
		Collection<Degree> degrees = studentSystem.getAllDegrees();
		return degrees;
	}
}
