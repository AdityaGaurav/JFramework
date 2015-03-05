package com.framework.reporter.utils;

import com.framework.config.Configurations;
import com.framework.config.FrameworkConfiguration;
import com.framework.config.FrameworkProperty;


/**
 * Created with IntelliJ IDEA ( LivePerson : www.liveperson.com )
 *
 * Package: com.framework.reporter.utils
 *
 * Name   : ReporterConstants 
 *
 * User   : solmarkn / Dani Vainstein
 *
 * Date   : 2015-02-13 
 *
 * Time   : 15:40
 *
 */

public class ReporterConstants
{
	private static FrameworkConfiguration c = Configurations.getInstance();

	public static final String REPORTER_VERSION = c.getString( FrameworkProperty.REPORTER_VERSION );
	public static final Integer SUCCESS_RATE = c.getInteger( FrameworkProperty.SUCCESS_RATE, 100 );

	// ====================================================================================== //


	public static final String PIE_BACKGROUND_COLOR = c.getString( "pie.background.color" );
	public static final boolean PIE_USE_BACKGROUND_COLOR = c.getBoolean( "pie.use.background.color" );
	public static final String PIE_BACKGROUND_COLOR_STROKE = c.getString( "pie.background.color.stroke" );
	public static final String PIE_BACKGROUND_COLOR_STROKE_WIDTH = c.getString( "pie.background.color.stroke.width" );
	public static final String PIE_BACKGROUND_COLOR_FILL = c.getString( "pie.background.color.fill" );
	public static final String PIE_COLOR_FAILED = c.getString( "pie.colors.failed" );
	public static final String PIE_COLOR_PASSED = c.getString( "pie.colors.passed" );
	public static final String PIE_COLOR_SKIPPED = c.getString( "pie.colors.skipped" );
	public static final String PIE_COLOR_IGNORED = c.getString( "pie.colors.ignored" );
	public static final String PIE_COLOR_SUCCESS_PERCENTAGE = c.getString( "pie.colors.failed.with.success.percentage" );
	public static final String PIE_ENABLE_INTERACTIVITY = c.getString( "pie.enable.interactivity" );
	public static final String PIE_FONT_SIZE = c.getString( "pie.font.size" );
	public static final String PIE_FONT_NAME = c.getString( "pie.font.name" );
	public static final String PIE_HEIGHT = c.getString( "pie.height" );
	public static final String PIE_LEGEND_ALIGNMENT = c.getString( "pie.legend.alignment" );
	public static final String PIE_LEGEND_POSITION = c.getString( "pie.legend.position" );
	public static final String PIE_LEGEND_TEXT_STYLE_COLOR = c.getString( "pie.legend.text.style.color" );
	public static final String PIE_LEGEND_TEXT_STYLE_FONT_SIZE = c.getString( "pie.legend.text.style.font.size" );
	public static final String PIE_SLICE_TEXT = c.getString( "pie.slice.text" );
	public static final String PIE_SLICE_TEXT_STYLE_COLOR = c.getString( "pie.slice.text.style.color" );
	public static final String PIE_SLICE_TEXT_FONT_SIZE = c.getString( "pie.slice.text.font.size" );
	public static final String PIE_START_ANGLE = c.getString( "pie.start.angle" );
	public static final String PIE_TOOLTIP_TEXT = c.getString( "pie.tooltip.text" );
	public static final String PIE_TOOLTIP_TEXT_STYLE_COLOR = c.getString( "pie.tooltip.text.style.color" );
	public static final String PIE_TOOLTIP_TEXT_STYLE_FONT_SIZE = c.getString( "pie.tooltip.text.style.font.size" );
	public static final String PIE_TOOLTIP_TEXT_STYLE_FONT_BOLD = c.getString( "pie.tooltip.text.style.font.bold" );
	public static final String PIE_TOOLTIP_SHOW_COLOR_CODE = c.getString( "pie.tooltip.show.color.code" );
	public static final String PIE_OPTIONS_IS3D = c.getString( "pie.options.is3D" );
	public static final boolean PIE_OPTIONS_SHOW_TITLE = c.getBoolean( "pie.options.show.title" );
	public static final String PIE_OPTIONS_TITLE = c.getString( "pie.options.title" );

	// ====================================================================================== //

	public static final String GAUGE_COLORS_PLATE = c.getString( "gauge.colors.plate" );
	public static final String GAUGE_COLORS_MAJOR_TICKS = c.getString( "gauge.colors.major.ticks" );
	public static final String GAUGE_COLORS_MINOR_TICKS = c.getString( "gauge.colors.minor.ticks" );
	public static final String GAUGE_COLORS_TITLE = c.getString( "gauge.colors.title" );
	public static final String GAUGE_COLORS_UNITS = c.getString( "gauge.colors.units" );
	public static final String GAUGE_COLORS_NUMBERS = c.getString( "gauge.colors.numbers" );
	public static final String GAUGE_COLORS_NEEDLE_START = c.getString( "gauge.colors.needle.start" ).replace( ";", "," );
	public static final String GAUGE_COLORS_NEEDLE_END = c.getString( "gauge.colors.needle.end" ).replace( ";", "," );
	public static final String GAUGE_VALUES_FORMAT_INT = c.getString( "gauge.values.format.integer" );
	public static final String GAUGE_VALUES_FORMAT_DEC = c.getString( "gauge.values.format.decimal" );
	public static final String GAUGE_MINOR_TICKS = c.getString( "gauge.minor.ticks" );
	public static final String GAUGE_MAJOR_TICKS = c.getString( "gauge.major.ticks" ).replace( ";", "," );
	public static final String GAUGE_STROKE_TICKS = c.getString( "gauge.stroke.ticks" );
	public static final String GAUGE_WIDTH = c.getString( "gauge.width" );
	public static final String GAUGE_HEIGHT = c.getString( "gauge.height" );
	public static final String GAUGE_GLOW = c.getString( "gauge.glow" );
	public static final String GAUGE_UNITS = c.getString( "gauge.units" );
	public static final String GAUGE_PLATE = c.getString( "gauge.plate" );
	public static final String GAUGE_ANIMATION_DELAY = c.getString( "gauge.animation.delay" );
	public static final String GAUGE_ANIMATION_DURATION = c.getString( "gauge.animation.duration" );
	public static final String GAUGE_ANIMATION_FUNCTION = c.getString( "gauge.animation.function" );
	public static final String GAUGE_HIGHLIGHTS_FAIL_FROM = c.getString( "gauge.highlights.fail.from" );
	public static final String GAUGE_HIGHLIGHTS_FAIL_TO = c.getString( "gauge.highlights.fail.to" );
	public static final String GAUGE_HIGHLIGHTS_FAIL_COLOR = c.getString( "gauge.highlights.fail.color" );
	public static final String GAUGE_HIGHLIGHTS_SUCCESS_FROM = c.getString( "gauge.highlights.success.from" );
	public static final String GAUGE_HIGHLIGHTS_SUCCESS_TO = c.getString( "gauge.highlights.success.to" );
	public static final String GAUGE_HIGHLIGHTS_SUCCESS_COLOR = c.getString( "gauge.highlights.success.color" );
	public static final String GAUGE_HIGHLIGHTS_SUCCESS_PERCENTAGE_FROM = c.getString( "gauge.highlights.success.percentage.from" );
	public static final String GAUGE_HIGHLIGHTS_SUCCESS_PERCENTAGE_TO = c.getString( "gauge.highlights.success.percentage.to" );
	public static final String GAUGE_HIGHLIGHTS_SUCCESS_PERCENTAGE_COLOR = c.getString( "gauge.highlights.success.percentage.color" );
}
