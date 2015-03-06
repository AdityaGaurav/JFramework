package com.framework.site.objects.body.interfaces;

import com.framework.driver.event.HtmlElement;
import com.framework.site.data.Ships;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.site.objects.body.interfaces
 *
 * Name   : CompareShipBlock 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-16 
 *
 * Time   : 17:09
 *
 */

public interface ContentBlockComparing
{
	static final String LOGICAL_NAME = "Content Block Comparing";

	static final By ROOT_BY = By.cssSelector( ".content-block[class*=\"comparing\"]" );

	String getSectionName( int index );

	List<String> getSectionNames( final int index );

	void collapseAll();

	List<CompareSection> getExpandedSections();

	List<CompareSection> getCollapsedSections();

	CompareSection getSection( String name );

	List<CompareSection> getComparisonSections();

	interface CompareSection
	{
		public enum getStatus{ AVAILABLE, NOT_AVAILABLE, COMING_SOON }

		static final String LOGICAL_NAME = "Compare Section";

		String getSectionName();

		List<String> getParameters();

		boolean isExpanded();

		void expand();

		void collapse();

		List<Ships> getShipsSections();

		Map<String, Map.Entry<HtmlElement, Map<Ships, String>>> getRows();

		boolean indicatorNotVisible( Map<Ships,String> values );

		HtmlElement getTable();
	}

	interface QuickPreview
	{

	}

	interface CompareLabels
	{
		static final String LOGICAL_NAME = "Compare Labels";

		static final By ROOT_BY = By.id( "compare-labels" );

		boolean isVisible();

		List<Ships> getShipSections();
	}
}
