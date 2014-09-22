package no.uio.inf5750.assignment2.dao;

import java.util.Collection;

import no.uio.inf5750.assignment2.model.Degree;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: DegreeDAO.java 28 2007-08-23 11:06:31Z torgeilo $
 */
public interface DegreeDAO
{
    /**
     * Persists a degree. An unique id is generated if the object is persisted
     * for the first time, and which is both set in the given degree object and
     * returned.
     * 
     * @param degree the degree to add for persistence.
     * @return the id of the degree.
     */
    int saveDegree( Degree degree );

    /**
     * Returns a degree.
     * 
     * @param id the id of the degree to return.
     * @return the degree or null if it doesn't exist.
     */
    Degree getDegree( int id );

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
     * @return all degrees.
     */
    Collection<Degree> getAllDegrees();

    /**
     * Deletes a degree.
     * 
     * @param degree the degree to delete.
     */
    void delDegree( Degree degree );
}
