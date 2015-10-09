package no.uio.inf5750.assignment2.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import no.uio.inf5750.assignment2.dao.*;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateCourseDAO;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateDegreeDAO;
import no.uio.inf5750.assignment2.dao.hibernate.HibernateStudentDAO;
import no.uio.inf5750.assignment2.service.StudentSystem;
import no.uio.inf5750.assignment2.service.impl.DefaultStudentSystem;

@Configuration
@EnableTransactionManagement
@ComponentScan("no.uio.inf5750.assignment2") 
public class StudentSystemConfiguration {

}
