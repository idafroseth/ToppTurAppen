package no.uio.inf5750.assignment2.gui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import no.uio.inf5750.assignment2.model.Student;


@Path("/api/student")
public class ApiController{
	
	@RequestMapping(value="student/{user}", method = RequestMethod.GET)
	@ResponseBody
	public Student getStudentByUsername(@PathVariable String user, HttpServletRequest request, HttpServletResponse Response){
		Student student = studentService.getStudent(user);
		return student;
	}
}
