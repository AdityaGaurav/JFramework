package com.framework.reporter;

import com.framework.config.Configurations;
import com.framework.reporter.utils.ReporterConstants;
import com.framework.reporter.utils.VelocityLogger;
import com.framework.reporter.utils.XMLStringBuffer;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.FieldMethodizer;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static org.apache.velocity.app.Velocity.init;
import static org.apache.velocity.app.Velocity.mergeTemplate;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter
 *
 * Name   : JHtmlReporter 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-14 
 *
 * Time   : 21:16
 *
 */

public class JHtmlReporter extends ScenarioListenerAdapter implements IReporter
{

	//region JHtmlReporter - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( JHtmlReporter.class );

	private static final String RESOURCES_PATH = "reporter/res/";

	private static final String TEMPLATES_PATH = "reporter/velocity/templates/";

	private static final String TEMPLATE_EXTENSION = ".vm";

	//endregion


	//region JHtmlReporter - Constructor Methods Section

	public JHtmlReporter()
	{
		super();
	}

	//endregion


	//region JHtmlReporter - IReporter Implementation Section

	/**
	 * Generates a set of HTML files that contain data about the outcome of the specified test suites.
	 *
	 * @param xmlSuites
	 * @param suites              Data about the test runs.
	 * @param outputDirectoryName The directory in which to create the report.
	 */
	@Override
	public void generateReport( List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectoryName )
	{
		try
		{
			initVelocity();
			deleteLastReport();
			generateDashboard( suites );
			generateSuites();
			generateTestNGXml( suites );
			generateTestContexts();
			copyResources();
		}
		catch ( Exception e )
		{
			logger.error( ExceptionUtils.getRootCauseMessage( e ) );
			throw new ReporterRuntimeException( e );
		}
		finally
		{
			getScenarioManager().terminate();
			Configurations.getInstance().terminate();
		}
	}

	private void initVelocity()
	{
		Velocity.setProperty( RuntimeConstants.RESOURCE_LOADER, "classpath" );
		Velocity.setProperty( "classpath.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
		Velocity.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
		Velocity.setProperty( RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName() );
		Velocity.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new VelocityLogger() );

		try
		{
			init();
		}
		catch ( Exception ex )
		{
			logger.error( ExceptionUtils.getRootCauseMessage( ex ) );
			throw new ReporterRuntimeException( ex.getMessage(), ex );
		}
	}

	/**
	 * Helper method that creates a Velocity context and initialises it with a reference to the ReportNG utils, report
	 * metadata and localised messages.
	 *
	 * @return An initialised Velocity context.
	 */
	private VelocityContext createContext()
	{
		try
		{
			//ToolManager velocityToolManager = new ToolManager();

			VelocityContext context = new VelocityContext();// velocityToolManager.createContext() );

			// adding a logger
			context.put( ReporterKeys.LOGGER_KEY.getLiteral(), LoggerFactory.getLogger( "Dashboard" ) );

			// adding environment information string values
			context.put( ReporterKeys.JAVA_VERSION_KEY.getLiteral(), SystemUtils.JAVA_VERSION );
			context.put( ReporterKeys.USER_NAME_KEY.getLiteral(), SystemUtils.USER_NAME );
			getScenarioManager().createInstant();
			context.put( ReporterKeys.TIMESTAMP_KEY.getLiteral(), getScenarioManager().getFormattedInstant() );
			context.put( ReporterKeys.OS_VERSION_KEY.getLiteral(), SystemUtils.OS_NAME + "/" + SystemUtils.OS_VERSION );
			context.put( ReporterKeys.USER_TIMEZONE_KEY.getLiteral(), SystemUtils.USER_TIMEZONE );
			context.put( ReporterKeys.USER_TIMEZONE_KEY.getLiteral(), SystemUtils.USER_TIMEZONE );
			context.put( ReporterKeys.REPORTER_VERSION.getLiteral(), ReporterConstants.REPORTER_VERSION );

			return context;
		}
		catch ( Exception e )
		{
			logger.error( ExceptionUtils.getRootCauseMessage( e ) );
			throw new ReporterRuntimeException( e );
		}
	}

	@SuppressWarnings ( "ResultOfMethodCallIgnored" )
	private void deleteLastReport()
	{
		boolean deleted = FileSystemUtils.deleteRecursively( new File( configuration.getOutputDirectory() ) );
		logger.info( "Last report < {} > was deleted -> {}.", deleted );
		new File( configuration.getOutputDirectory() ).mkdir();
	}

	protected void copyResources() throws IOException
	{
		Enumeration<URL> urls = getClass().getClassLoader().getResources( RESOURCES_PATH );
		try
		{
			while( urls.hasMoreElements() )
			{
				File src = new File( urls.nextElement().toURI() );
				FileSystemUtils.copyRecursively( src, new File( configuration.getOutputDirectory(), "../" ) );
			}
		}
		catch ( URISyntaxException e )
		{
			logger.error( e.getMessage() );
		}
	}

	/**
	 * Generate the specified output file by merging the specified Velocity template with the supplied context.
	 * @param file
	 * @param templateName
	 * @param context
	 * @throws Exception
	 */
	private void generateFile( File file, String templateName, VelocityContext context ) throws Exception
	{
		Writer writer = new BufferedWriter( new FileWriter( file ) );
		try
		{
			mergeTemplate( TEMPLATES_PATH + Templates.COMMON_HEAD_VM.getLiteral(), SystemUtils.FILE_ENCODING, context, writer );
			mergeTemplate( TEMPLATES_PATH + templateName, SystemUtils.FILE_ENCODING, context, writer );
			mergeTemplate( TEMPLATES_PATH + Templates.COMMON_FOOTER_VM.getLiteral(), SystemUtils.FILE_ENCODING, context, writer );
			writer.flush();
		}
		catch ( Exception ex )
		{
			logger.error( ExceptionUtils.getRootCauseMessage( ex ) );
			throw new ReporterRuntimeException( ex );
		}
		finally
		{
			writer.close();
		}
	}

	private void generateDashboard( List<ISuite> suites ) throws Exception
	{

		logger.info( "creating html file {}", Templates.INDEX_VM );
		VelocityContext context = createContext();

		// adding constants configuration value
		context.put( ReporterKeys.CONSTANTS_KEY.getLiteral(),
				new FieldMethodizer( "com.framework.reporter.utils.ReporterConstants" ) );
		context.put( ReporterKeys.SCENARIO_UTIL_KEY.getLiteral(), getScenarioManager() );
		context.put( ReporterKeys.SUITES_KEY.getLiteral(), getScenarioManager().getSuitesAsList() );

		generateFile( new File( configuration.getOutputDirectory(), Templates.INDEX_VM.getLiteral() ),
				Templates.INDEX_VM.getLiteral() + TEMPLATE_EXTENSION, context );
	}


	private void generateSuites( ) throws Exception
	{
		logger.info( "creating html file {}", Templates.SUITES_VM );
		VelocityContext context = createContext();

		context.put( ReporterKeys.SUITES_KEY.getLiteral(), getScenarioManager().getSuitesAsList() );
		generateFile( new File( configuration.getOutputDirectory(), Templates.SUITES_VM.getLiteral() ),
				Templates.SUITES_VM.getLiteral() + TEMPLATE_EXTENSION, context );
	}

	private void generateTestContexts() throws Exception
	{
		List<Suite> suites = getScenarioManager().getSuitesAsList();
		for( Suite suite : suites )
		{
			List<TestContext> testContexts = suite.getTestContextAsList();
			for( int contextIndex = 0; contextIndex < testContexts.size(); contextIndex ++ )
			{
				String fileName = String.format( Templates.TEST_CONTEXT_PATTERN_VM.getLiteral(), contextIndex );
				logger.info( "creating html file {}", fileName );
				VelocityContext context = createContext();
				context.put( ReporterKeys.TEST_CONTEXT_KEY.getLiteral(), testContexts.get( contextIndex ) );
				generateFile( new File( configuration.getOutputDirectory(), fileName ),
						Templates.TEST_CONTEXT_VM.getLiteral() + TEMPLATE_EXTENSION, context );
			}
		}
	}

	private void generateTestNGXml( List<ISuite> suites ) throws Exception
	{
		logger.info( "creating html file < \"testng.xml.html\" >" );
		List<String> content = Lists.newArrayList();
		List<String> names = Lists.newArrayList();
		List<String> files = Lists.newArrayList();

		for( ISuite suite : suites )
		{
			XmlSuite xmlSuite = suite.getXmlSuite();
			String prettyContent = toXmlHtmlStyle( xmlSuite );
			content.add( prettyContent );
			names.add( xmlSuite.getName() );
			files.add( xmlSuite.getFileName() );
		}

		VelocityContext context = createContext();
		context.put( ReporterKeys.XML_SUITE_NAMES_KEY.getLiteral(), names );
		context.put( ReporterKeys.XML_SUITE_FILES_KEY.getLiteral(), files );
		context.put( ReporterKeys.XML_SUITE_CONTENTS_KEY.getLiteral(), content );

		generateFile( new File( configuration.getOutputDirectory(), Templates.TEST_NG_XML.getLiteral() ),
				Templates.TEST_NG_XML.getLiteral() + TEMPLATE_EXTENSION, context );
	}

	//endregion


	//region JHtmlReporter - TEST_NG_XML_VM methods

	private String toXmlHtmlStyle( XmlSuite xmlSuite )
	{
		XMLStringBuffer xsb = new XMLStringBuffer( true );

		xsb.setDocTypeHtml( "suite SYSTEM \"" + Parser.TESTNG_DTD_URL + '\"' );

		Properties props = new Properties();
		props.setProperty( "name", xmlSuite.getName() );
		if ( xmlSuite.getVerbose() != null )
		{
			setProperty( props, "verbose", xmlSuite.getVerbose().toString(), XmlSuite.DEFAULT_VERBOSE.toString() );
		}
		if ( xmlSuite.getParallel() != null )
		{
			setProperty( props,
					"parallel", xmlSuite.getParallel(), XmlSuite.DEFAULT_PARALLEL );
		}
		if ( xmlSuite.getConfigFailurePolicy() != null )
		{
			setProperty( props, "configfailurepolicy",
					xmlSuite.getConfigFailurePolicy(), XmlSuite.DEFAULT_CONFIG_FAILURE_POLICY );
		}
		if ( xmlSuite.getConfigFailurePolicy() != null )
		{
			setProperty( props, "thread-count",
					String.valueOf( xmlSuite.getThreadCount() ), XmlSuite.DEFAULT_THREAD_COUNT.toString() );
		}
		if ( xmlSuite.getConfigFailurePolicy() != null )
		{
			setProperty( props, "data-provider-thread-count",
					String.valueOf( xmlSuite.getDataProviderThreadCount() ),
					XmlSuite.DEFAULT_DATA_PROVIDER_THREAD_COUNT.toString() );
		}
		if ( ! XmlSuite.DEFAULT_JUNIT.equals( xmlSuite.isJUnit() ) )
		{
			props.setProperty( "junit", xmlSuite.isJUnit() != null ? xmlSuite.isJUnit().toString() : "false" );
		}
		setProperty( props, "skipfailedinvocationcounts",
				xmlSuite.skipFailedInvocationCounts().toString(),
				XmlSuite.DEFAULT_SKIP_FAILED_INVOCATION_COUNTS.toString() );

		if ( null != xmlSuite.getObjectFactory() )
		{
			props.setProperty( "object-factory", xmlSuite.getObjectFactory().getClass().getName() );
		}
		if ( ! StringUtils.isEmpty( xmlSuite.getParentModule() ) )
		{
			props.setProperty( "parent-module", xmlSuite.getParentModule() );
		}
		setProperty( props, "allow-return-values", String.valueOf( xmlSuite.getAllowReturnValues() ),
				XmlSuite.DEFAULT_ALLOW_RETURN_VALUES.toString() );

		xsb.pushHtml( "suite", props );

		/** printing listeners */

		if ( hasElements( xmlSuite.getListeners() ) )
		{
			xsb.pushHtml( "listeners" );
			for ( String listenerName : xmlSuite.getListeners() )
			{
				Properties listenerProps = new Properties();
				listenerProps.setProperty( "class-name", listenerName );
				xsb.addEmptyElementHtml( "listener", listenerProps );
			}
			xsb.popHtml( "listeners" );
		}

		dumpParameters( xsb, xmlSuite.getParameters() );

		if ( hasElements( xmlSuite.getXmlPackages() ) )
		{
			xsb.pushHtml( "packages" );
			for ( XmlPackage pack : xmlSuite.getXmlPackages() )
			{
				String packXml = toPackageXmlHtml( pack, "   " );
				xsb.getStringBuffer().append( packXml );
			}
			xsb.popHtml( "packages" );
		}

		/** groups */

		List<String> included = xmlSuite.getIncludedGroups();
		List<String> excluded = xmlSuite.getExcludedGroups();
		if ( hasElements( included ) || hasElements( excluded ) )
		{
			xsb.pushHtml( "groups" );
			xsb.pushHtml( "run" );
			for ( String g : included )
			{
				xsb.addEmptyElementHtml( "include", "name", g );
			}
			for ( String g : excluded )
			{
				xsb.addEmptyElementHtml( "exclude", "name", g );
			}
			xsb.popHtml( "run" );
			xsb.popHtml( "groups" );
		}

		for ( XmlTest test : xmlSuite.getTests() )
		{
			String testHtml = toTestXmlHtml( test, "   " );
			xsb.getStringBuffer().append( testHtml );
		}

		/** Additional suites files */

		List<String> suiteFiles = xmlSuite.getSuiteFiles();
		if ( suiteFiles.size() > 0 )
		{
			xsb.pushHtml( "suite-files" );
			for ( String sf : suiteFiles )
			{
				Properties prop = new Properties();
				prop.setProperty( "path", sf );
				xsb.addEmptyElementHtml( "suite-file", prop );
			}
			xsb.popHtml( "suite-files" );
		}

		xsb.popHtml( "suite" );
		return xsb.toXMLHtml();
	}

	private String toXmlIncludeHtml( XmlInclude include, String indent )
	{
		XMLStringBuffer xsb = new XMLStringBuffer( true, indent );

		Properties p = new Properties();
		p.setProperty( "name", include.getName() );
		List<Integer> invocationNumbers = include.getInvocationNumbers();
		if ( invocationNumbers != null && invocationNumbers.size() > 0 )
		{
			p.setProperty( "invocation-numbers", XmlClass.listToString( invocationNumbers ).toString() );
		}

		if ( ! include.getLocalParameters().isEmpty() )
		{
			xsb.pushHtml( "include", p );
			dumpParameters( xsb, include.getLocalParameters() );
			xsb.popHtml( "include" );
		}
		else
		{
			xsb.addEmptyElementHtml( "include", p );
		}

		return xsb.toXMLHtml();
	}

	private String toClassXmlHtml( XmlClass xmlClass, String indent )
	{
		XMLStringBuffer xsb = new XMLStringBuffer( true, indent );
		Properties prop = new Properties();
		prop.setProperty( "name", xmlClass.getName() );

		boolean hasMethods = ! xmlClass.getIncludedMethods().isEmpty() || ! xmlClass.getExcludedMethods().isEmpty();
		boolean hasParameters = ! xmlClass.getLocalParameters().isEmpty();
		if ( hasParameters || hasMethods )
		{
			xsb.pushHtml( "class", prop );
			dumpParameters( xsb, xmlClass.getLocalParameters() );
			if ( hasMethods )
			{
				xsb.pushHtml( "methods" );
				for ( XmlInclude m : xmlClass.getIncludedMethods() )
				{
					String includeXml = toXmlIncludeHtml( m, xsb.getCurrentIndent() );
					xsb.getStringBuffer().append( includeXml );
				}

				for ( String m : xmlClass.getExcludedMethods() )
				{
					Properties p = new Properties();
					p.setProperty( "name", m );
					xsb.addEmptyElementHtml( "exclude", p );
				}
				xsb.popHtml( "methods" );
			}
			xsb.popHtml( "class" );
		}
		else
		{
			xsb.addEmptyElementHtml( "class", prop );
		}

		return xsb.toXMLHtml();
	}

	private String toTestXmlHtml( XmlTest test, String indent )
	{
		XMLStringBuffer xsb = new XMLStringBuffer( true, indent );

		Properties p = new Properties();

		p.setProperty( "name", test.getName() );
		setProperty( p, "junit", String.valueOf( test.isJUnit() ), XmlSuite.DEFAULT_JUNIT.toString() );
		if ( test.getParallel() != null )
		{
			setProperty( p, "parallel", test.getParallel(), XmlSuite.DEFAULT_PARALLEL );
		}
		setProperty( p, "verbose", String.valueOf( test.getVerbose() ), XmlSuite.DEFAULT_VERBOSE.toString() );
		if ( null != test.getTimeOut() )
		{
			p.setProperty( "time-out", test.getTimeOut() );
		}
		if ( test.getPreserveOrder() != null && ! XmlSuite.DEFAULT_PRESERVE_ORDER.equals( test.getPreserveOrder() ) )
		{
			p.setProperty( "preserve-order", test.getPreserveOrder() );
		}
		if ( test.getThreadCount() != - 1 )
		{
			p.setProperty( "thread-count", Integer.toString( test.getThreadCount() ) );
		}

		xsb.pushHtml( "test", p );

		if ( null != test.getMethodSelectors() && !  test.getMethodSelectors().isEmpty() )
		{
			xsb.pushHtml( "method-selectors" );
			for ( XmlMethodSelector selector :  test.getMethodSelectors() )
			{
				//xsb.getStringBuffer().append( selector.toXml( indent + "    " ) );
			}

			xsb.popHtml( "method-selectors" );
		}

		dumpParameters( xsb, test.getTestParameters() );

		// groups
		if ( ! test.getMetaGroups().isEmpty()
				|| ! test.getIncludedGroups().isEmpty()
				|| ! test.getExcludedGroups().isEmpty()
				|| ! test.getXmlDependencyGroups().isEmpty() )
		{
			xsb.pushHtml( "groups" );

			for ( String metaGroupName : test.getMetaGroups().keySet() )
			{
				Properties metaGroupProp = new Properties();
				metaGroupProp.setProperty( "name", metaGroupName );
				xsb.pushHtml( "define", metaGroupProp );
				for ( String groupName : test.getMetaGroups().get( metaGroupName ) )
				{
					Properties includeProps = new Properties();
					includeProps.setProperty( "name", groupName );
					xsb.addEmptyElementHtml( "include", includeProps );
				}

				xsb.popHtml( "define" );
			}

			// run
			if ( ! test.getIncludedGroups().isEmpty() || ! test.getExcludedGroups().isEmpty() )
			{
				xsb.pushHtml( "run" );

				for ( String includeGroupName : test.getIncludedGroups() )
				{
					Properties includeProps = new Properties();
					includeProps.setProperty( "name", includeGroupName );
					xsb.addEmptyElementHtml( "include", includeProps );
				}

				for ( String excludeGroupName : test.getExcludedGroups() )
				{
					Properties excludeProps = new Properties();
					excludeProps.setProperty( "name", excludeGroupName );
					xsb.addEmptyElementHtml( "exclude", excludeProps );
				}

				xsb.popHtml( "run" );
			}

			if ( test.getXmlDependencyGroups() != null && ! test.getXmlDependencyGroups().isEmpty() )
			{
				xsb.push( "dependencies" );
				for ( Map.Entry<String, String> entry : test.getXmlDependencyGroups().entrySet() )
				{
					xsb.addEmptyElementHtml( "group", "name", entry.getKey(), "depends-on", entry.getValue() );
				}
				xsb.popHtml( "dependencies" );
			}

			xsb.popHtml( "groups" );
		}

		if ( null != test.getPackages() && ! test.getPackages().isEmpty() )
		{
			xsb.pushHtml( "packages" );
			for ( XmlPackage pack : test.getXmlPackages() )
			{
				String packString = toPackageXmlHtml( pack, "   " );
				xsb.getStringBuffer().append( packString );
			}

			xsb.popHtml( "packages" );
		}

		// classes
		if ( null != test.getXmlClasses() && ! test.getXmlClasses().isEmpty() )
		{
			xsb.pushHtml( "classes" );
			for ( XmlClass cls : test.getXmlClasses() )
			{
				String classHtml = toClassXmlHtml( cls, xsb.getCurrentIndent() );
				xsb.getStringBuffer().append( classHtml );
			}
			xsb.popHtml( "classes" );
		}
		xsb.popHtml( "test" );

		return xsb.toXMLHtml();
	}

	private String toPackageXmlHtml( XmlPackage pack, String indent )
	{
		XMLStringBuffer xsb = new XMLStringBuffer( true, indent );
		Properties p = new Properties();
		p.setProperty( "name", pack.getName() );

		if ( pack.getInclude().isEmpty() && pack.getExclude().isEmpty() )
		{
			xsb.addEmptyElementHtml( "package", p );
		}
		else
		{
			xsb.pushHtml( "package", p );
			for ( String m : pack.getInclude() )
			{
				Properties includeProp = new Properties();
				includeProp.setProperty( "name", m );
				xsb.addEmptyElementHtml( "include", includeProp );
			}
			for ( String m : pack.getExclude() )
			{
				Properties excludeProp = new Properties();
				excludeProp.setProperty( "name", m );
				xsb.addEmptyElementHtml( "exclude", excludeProp );
			}

			xsb.popHtml( "package" );
		}

		return xsb.toXMLHtml();
	}

	private void setProperty( Properties p, String name, String value, String def )
	{
		if ( ! def.equals( value ) && value != null )
		{
			p.setProperty( name, value );
		}
	}

	private void dumpParameters( XMLStringBuffer xsb, Map<String, String> parameters )
	{
		// parameters
		if ( ! parameters.isEmpty() )
		{
			for ( Map.Entry<String, String> para : parameters.entrySet() )
			{
				Properties paramProps = new Properties();
				paramProps.setProperty( "name", para.getKey() );
				paramProps.setProperty( "value", para.getValue() );
				xsb.addEmptyElementHtml( "parameter", paramProps );
			}
		}
	}

	private boolean hasElements(Collection<?> c) {
		return c != null && ! c.isEmpty();
	}

	//endregion

}
