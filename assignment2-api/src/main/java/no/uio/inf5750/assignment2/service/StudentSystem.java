package no.uio.inf5750.assignment2.service;

import java.util.Collection;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: StudentSystem.java 28 2007-08-23 11:06:31Z torgeilo $
 */
public interface StudentSystem
{
    /**
     * Adds a course.
     * 
     * @param courseCode the course code of the course to add.
     * @param name the name of the course to add.
     * @return the generated id of the added course.
     */
    int addCourse( String courseCode, String name );

    /**
     * Updates a course.
     * 
     * @param courseId the id of the course to update.
     * @param courseCode the course code to update.
     * @param name the name to update.
     */
    void updateCourse( int courseId, String courseCode, String name );

    /**
     * Returns a course.
     * 
     * @param courseId the id of the course to return.
     * @return the course or null if it doesn't exist.
     */
    Course getCourse( int courseId );

    /**
     * Returns a course with a specific course code.
     * 
     * @param courseCode the course code of the course to return.
     * @return the course code or null if it doesn't exist.
     */
    Course getCourseByCourseCode( String courseCode );

    /**
     * Returns a course with a specific name.
     * 
     * @param name the name of the course that needs to be found
     * @return the course code or null if it doesn't exist.
     */
    Course getCourseByName( String name );

    /**
     * Returns all courses.
     * 
     * @return all courses or an empty Collection if no course exists.
     */
    Collection<Course> getAllCourses();

    /**
     * Removes all references to the course from degrees and students and
     * deletes the course.
     * 
     * @param courseId the id of the course to delete.
     */
    void delCourse( int courseId );

    /**
     * Adds an attendant to a course and a course to a student.
     * 
     * @param courseId the id of the course.
     * @param studentId the id of the student.
     */
    void addAttendantToCourse( int courseId, int studentId );

    /**
     * Removes an attendant from a course and a course from a student.
     * 
     * @param courseId the id of the course.
     * @param studentId the id of the student.
     */
    void removeAttendantFromCourse( int courseId, int studentId );

    /**
     * Adds a degree.
     * 
     * @param type the type of the degree to add.
     * @return the generated id of the added degree.
     */
    int addDegree( String type );

    /**
     * Updates a degree.
     * 
     * @param degreeId the id of the degree to update.
     * @param type the type to update.
     */
    void updateDegree( int degreeId, String type );

    /**
     * Returns a degree.
     * 
     * @param degreeId the id of the degree to return.
     * @return the degree or null if it doesn't exist.
     */
    Degree getDegree( int degreeId );

    /**
     * Returns a degree with a specific type.
     * 
     * @param type the type of the degree to return.
     * @return the degree or null if it doesn't exist.
     */
    Degree getDegreeByType( String type );

    /**
     * Returns all degrees.
     * 
     * @return all degrees or an empty Collection if no degree exists.
     */
    Collection<Degree> getAllDegrees();

    /**
     * Removes all references to the degree from students and deletes the
     * degree.
     * 
     * @param degreeId the id of the degree to delete.
     */
    void delDegree( int degreeId );

    /**
     * Adds a required course to a degree.
     * 
     * @param degreeId the id of the degree.
     * @param courseId the id of the course.
     */
    void addRequiredCourseToDegree( int degreeId, int courseId );

    /**
     * Removes a required course from a degree.
     * 
     * @param degreeId the id of the degree.
     * @param courseId the id of the course.
     */
    void removeRequiredCourseFromDegree( int degreeId, int courseId );

    /**
     * Adds a student.
     * 
     * @param name the name of the student to add.
     * @return the generated id of the added student.
     */
    int addStudent( String name );

    /**
     * Updates a student.
     * 
     * @param studentId the id of the student to update.
     * @param name the name to update.
     */
    void updateStudent( int studentId, String name );

    /**
     * Returns a student.
     * 
     * @param studentId the id of the student to return.
     * @return the student or null if it doesn't exist.
     */
    Student getStudent( int studentId );

    /**
     * Returns a student with a specific name.
     * 
     * @param name the name of the student to return.
     * @return the student or null if it doesn't exist.
     */
    Student getStudentByName( String name );

    /**
     * Returns all students.
     * 
     * @return all students or an empty Collection if no student exists.
     */
    Collection<Student> getAllStudents();

    /**
     * Removes all references to the student from courses and deletes the
     * student.
     * 
     * @param studentId the id of the student to delete.
     */
    void delStudent( int studentId );

    /**
     * Adds a degree to a student.
     * 
     * @param studentId the id of the student.
     * @param degreeId the id of the degree.
     */
    void addDegreeToStudent( int studentId, int degreeId );

    /**
     * Removes a degree from a student.
     * 
     * @param studentId the id of the student.
     * @param degreeId the id of the degree.
     */
    void removeDegreeFromStudent( int studentId, int degreeId );

    /**
     * Checks if a student has the required courses of a degree.
     * 
     * @param studentId the id of the student.
     * @param degreeId the id of the degree.
     * @return true/false.
     */
    boolean studentFulfillsDegreeRequirements( int studentId, int degreeId );
}
