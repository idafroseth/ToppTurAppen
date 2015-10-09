package no.uio.inf5750.assignment2.gui.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

@Controller
@RequestMapping("/api")
public class ApiController{
	@Autowired
	private StudentSystem studentSystem;
	
	//It should return a collection of all students in at leat the JSON format,
	//Make sure that there i no loop when a student refer to a course and a course refer to a student by stopping the reqursion
	@RequestMapping(value="/student/{user}", method = RequestMethod.GET)
	@ResponseBody
	public Student getStudentByUsername(@PathVariable String user, HttpServletRequest request, HttpServletResponse Response){
		Student student = studentSystem.getStudentByName(user);
		return student;
	}
	@RequestMapping(value="/student", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> getAllStudents(){
		Collection<Student> students = studentSystem.getAllStudents();
		return students;
	}
	
	
//	@RequestMapping(value="student/{student}/location", method = RequestMethod.GET)
//	public String getLocation(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude){
//		studentSystem.getStudentByName(student);
//		return student.latitude; 
//	}
	
	@RequestMapping(value = "/course", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Course> getCourse(){
		Collection<Course> courses = studentSystem.getAllCourses();
		return courses;
	}
	
	@RequestMapping(value = "/degree", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Degree> getDegree(){
		Collection<Degree> degrees = studentSystem.getAllDegrees();
		return degrees;
	}
}
