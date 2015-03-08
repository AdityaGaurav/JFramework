package com.framework.reporter;

import com.framework.config.Configurations;
import com.framework.utils.string.ToLogStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.Stack;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.utils
 *
 * Name   : ConfigurationMethod 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-18 
 *
 * Time   : 22:04
 *
 */

public class ConfigurationMethod
{

	//region ConfigurationMethod - Variables Declaration and Initialization Section.

	private static final Logger logger = LoggerFactory.getLogger( ConfigurationMethod.class );

	private final ITestNGMethod method;

	private final TestContext parentContext;

	private final Suite parentSuite;

	private Stack<ConfigurationInstanceMethod> configurationInstancesStack = new Stack<>();

	private final String type;

	//endregion


	//region ConfigurationMethod - Constructor Methods Section

	/**
	 * this constructor related to BeforeSuite and AfterSuite configuration methods
	 *
	 * @param result   the {@link org.testng.ITestResult} instance.
	 * @param parent   the parent {@link Suite}
	 */
	public ConfigurationMethod( final ITestResult result, final Suite parent )
	{
		// information for the method
		this.method = result.getMethod();
		this.type = "@" + Configurations.getAnnotation( result.getMethod() );
		this.parentContext = null;
		this.parentSuite = parent;
	}

	public ConfigurationMethod( final ITestResult result, final TestContext parent )
	{
		// information for the method
		this.method = result.getMethod();
		this.type = Configurations.getAnnotation( result.getMethod() );
		this.parentContext = parent;
		this.parentSuite = null;
	}

	//endregion


	//region ConfigurationMethod - ITestNGMethod Implementation Methods Section

	ITestNGMethod getTestNGMethod()
	{
		return method;
	}

	String getName()
	{
		return method.getMethodName();
	}

	public String getType()
	{
		return type;
	}

	public TestContext getParentContext()
	{
		return parentContext;
	}

	public Suite getParentSuite()
	{
		return parentSuite;
	}

	//endregion


	//region ConfigurationMethod - Service Methods Section

	void terminate()
	{
		configurationInstancesStack.clear();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder( this, ToLogStringStyle.LOG_LINE_STYLE )
				.append( "name", null != method ? method.getMethodName() : "N/A" )
				.append( "type", null != type ? type : "N/A" )
				.append( "testClass", getTestNGMethod() != null ? getTestNGMethod().getTestClass().getName() : "N/A" )
				.toString();
	}

	@Override
	public int hashCode()
	{
		int result = method.getMethodName().hashCode();
		if( null != parentContext )
		{
			result = 31 * result + parentContext.getName().hashCode();
		}
		if( null != parentSuite )
		{
			result = 31 * result + parentSuite.hashCode();
		}
		result = 31 * result + type.hashCode();
		return result;
	}

	//endregion


	//region ConfigurationMethod - ConfigurationInstanceMethod Implementation Section

	ConfigurationInstanceMethod getCurrentConfigurationInstanceMethod()
	{
		return configurationInstancesStack.peek();
	}

	void createConfigurationMethodInstance( ITestResult result )
	{
		ConfigurationInstanceMethod currentInstance = new ConfigurationInstanceMethod( result, this );
		this.configurationInstancesStack.push( currentInstance );
	}

	//endregion



}
