package com.framework.config;

import java.io.File;
import java.io.IOException;


/**
 * Loads configurations values from local files into the environment variables.
 */
public interface LocalPreferences
{
	File locatePreferences() throws IOException;
}
