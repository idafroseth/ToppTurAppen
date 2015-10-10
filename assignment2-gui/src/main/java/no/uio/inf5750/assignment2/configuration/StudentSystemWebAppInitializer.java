package no.uio.inf5750.assignment2.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class StudentSystemWebAppInitializer extends 
AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

	//The listener
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {RootConfig.class};

		}


//	Configure the DispatcherServlet
//	which is asked to load the application context using the defined bean  
//	 DispatcherServlet is expected to load beans containing web components such as controllers, view resolvers, and handler mappings,
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {StudentSystemConfiguration.class, MvcDispatcherServletConfig.class};		
	}


}
