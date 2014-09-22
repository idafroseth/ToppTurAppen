package no.uio.inf5750.assignment2.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: Degree.java 29 2007-08-23 19:39:08Z torgeilo $
 */
public class Degree
{
    private int id;

    /**
     * Required and unique.
     */
    private String type;

    private Set<Course> requiredCourses = new HashSet<Course>();

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public Degree()
    {
    }

    public Degree( String type )
    {
        this.type = type;
    }

    // -------------------------------------------------------------------------
    // Equals and hashcode
    // -------------------------------------------------------------------------

    @Override
    public int hashCode()
    {
        return type.hashCode();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }

        if ( o == null )
        {
            return false;
        }

        if ( !(o instanceof Degree) )
        {
            return false;
        }

        final Degree other = (Degree) o;

        return type.equals( other.getType() );
    }

    // -------------------------------------------------------------------------
    // Setters and getters
    // -------------------------------------------------------------------------

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    public Set<Course> getRequiredCourses()
    {
        //requiredCourses = new HashSet<Course>( requiredCourses ); // Rehash hack

        return requiredCourses;
    }

    public void setRequiredCourses( Set<Course> requiredCourses )
    {
        this.requiredCourses = requiredCourses;
    }
}
