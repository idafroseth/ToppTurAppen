package no.uio.inf5750.assignment2.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Torgeir Lorange Ostby
 * @version $Id: Student.java 29 2007-08-23 19:39:08Z torgeilo $
 */
public class Student
{
    private int id;

    /**
     * Required and unique.
     */
    private String name;

    private Set<Course> courses = new HashSet<Course>();

    private Set<Degree> degrees = new HashSet<Degree>();

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public Student()
    {
    }

    public Student( String name )
    {
        this.name = name;
    }

    // -------------------------------------------------------------------------
    // Equals and hashcode
    // -------------------------------------------------------------------------

    @Override
    public int hashCode()
    {
        return name.hashCode();
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

        if ( !(o instanceof Student) )
        {
            return false;
        }

        final Student other = (Student) o;

        return name.equals( other.getName() );
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

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public Set<Course> getCourses()
    {
        //courses = new HashSet<Course>( courses ); // Rehash hack

        return courses;
    }

    public void setCourses( Set<Course> courses )
    {
        this.courses = courses;
    }

    public Set<Degree> getDegrees()
    {
        //degrees = new HashSet<Degree>( degrees ); // Rehash hack

        return degrees;
    }

    public void setDegrees( Set<Degree> degrees )
    {
        this.degrees = degrees;
    }
}
