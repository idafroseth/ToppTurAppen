package no.uio.inf5750.assignment2.dao;

import java.util.Collection;

import no.uio.inf5750.assignment2.model.Student;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: StudentDAO.java 28 2007-08-23 11:06:31Z torgeilo $
 */
public interface StudentDAO
{
    /**
     * Persists a student. An unique id is generated if the object is persisted
     * for the first time, and which is both set in the given student object and
     * returned.
     * 
     * @param student the student to add for persistence.
     * @return the id of the student.
     */
    int saveStudent( Student student );

    /**
     * Returns a student.
     * 
     * @param id the id of the student to return.
     * @return the student or null if it doesn't exist.
     */
    Student getStudent( int id );

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
     * @return all students.
     */
    Collection<Student> getAllStudents();

    /**
     * Deletes a student.
     * 
     * @param student the student to delete.
     */
    void delStudent( Student student );
}
