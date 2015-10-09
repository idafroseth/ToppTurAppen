package no.uio.inf5750.assignment2.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mchange.v2.c3p0.ComboPooledDataSource;


//beans are typically the middle-tier and data-tier components that drive the back end of the application
@Configuration
@ComponentScan(basePackages={"no.uio.inf5750.assignment2"},
    excludeFilters={
        @Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
    })
public class RootConfig {
	@Bean
	public LocalSessionFactoryBean sessionFactory(ComboPooledDataSource dataSource) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		// sessionFactory.setPackagesToScan(new String[]
		// {"no.uio.inf5750.assignment2",
		// "no.uio.inf5750.assignment2.dao.hibernate"});
		sessionFactory.setMappingResources(
				new String[] { "hibernate/Course.hbm.xml", "hibernate/Degree.hbm.xml", "hibernate/Student.hbm.xml" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public ComboPooledDataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("org.postgresql.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataSource.setJdbcUrl("jdbc:postgresql:inf5750");
		dataSource.setUser("dhis");
		dataSource.setPassword("dhis");

		return dataSource;
	}

	@Bean(name = "transactionManager")
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.hbm2ddl.auto", "create-drop");
				setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
				// setProperty("hibernate.globally_quoted_identifiers", "true");
			}
		};
	}

}
