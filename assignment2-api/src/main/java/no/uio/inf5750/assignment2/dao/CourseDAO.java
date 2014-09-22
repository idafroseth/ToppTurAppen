package no.uio.inf5750.assignment2.dao;

import java.util.Collection;

import no.uio.inf5750.assignment2.model.Course;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: CourseDAO.java 28 2007-08-23 11:06:31Z torgeilo $
 */
public interface CourseDAO
{
    /**
     * Persists a course. An unique id is generated if the object is persisted
     * for the first time, and which is both set in the given course object and
     * returned.
     * 
     * @param course the course to add for persistence.
     * @return the id of the course.
     */
    int saveCourse( Course course );

    /**
     * Returns a course.
     * 
     * @param id the id of the course to return.
     * @return the course or null if it doesn't exist.
     */
    Course getCourse( int id );

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
     * @param courseCode the course code of the course to return.
     * @return the course code or null if it doesn't exist.
     */
    Course getCourseByName( String name );

    /**
     * Returns all courses.
     * 
     * @return all courses.
     */
    Collection<Course> getAllCourses();

    /**
     * Deletes a course.
     * 
     * @param course the course to delete.
     */
    void delCourse( Course course );
}
