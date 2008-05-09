/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openmrs.util.OpenmrsUtil;

/**
 * Methods use by the Openmrs tests
 */
public class TestUtil {

	/**
	 * Mimics org.openmrs.web.Listener.getRuntimeProperties()
	 * 
	 * @param webappName name to use when looking up the runtime properties env
	 *        var or filename
	 * @return Properties runtime
	 */
	public static Properties getRuntimeProperties(String webappName) {

		Properties props = new Properties();

		try {
			FileInputStream propertyStream = null;

			// Look for environment variable
			// {WEBAPP.NAME}_RUNTIME_PROPERTIES_FILE
			String env = webappName.toUpperCase() + "_RUNTIME_PROPERTIES_FILE";

			String filepath = System.getenv(env);

			if (filepath != null) {
				try {
					propertyStream = new FileInputStream(filepath);
				} catch (IOException e) {
				}
			}

			// env is the name of the file to look for in the directories
			String filename = webappName + "-runtime.properties";

			if (propertyStream == null) {
				filepath = OpenmrsUtil.getApplicationDataDirectory() + filename;
				try {
					propertyStream = new FileInputStream(filepath);
				} catch (IOException e) {
				}
			}

			// look in current directory last
			if (propertyStream == null) {
				filepath = filename;
				try {
					propertyStream = new FileInputStream(filepath);
				} catch (IOException e) {
				}
			}

			if (propertyStream == null)
				throw new IOException("Could not open '" + filename
				        + "' in user or local directory.");

			props.load(propertyStream);
			propertyStream.close();

		} catch (IOException e) {
		}

		return props;
	}
	
	/**
     * Convert the given xml output to a string that is assignable to a
     * single String variable
     * 
     * @param output multi line string to convert
     */
    public static void printAssignableToSingleString(String output) {
    	output = output.replace("\n", "\\n");
		output = output.replace("\"", "\\\"");
		System.out.println(output);
    }
    
    /**
     * Convert the given multi-line output to lines of StringBuilder.append lines
     * 
     * From an input of this this:
     * asdf
     *  asdfasdf
     * asdf"asdf"
     * 
     * To this:
     * StringBuilder correctOutput = new StringBuilder();
     * correctOutput.append("asdf\n");
     * correctOutput.append(" asdfasdf\n");
     * correctOutput.append("asdf\"asdf\"\n");
     * 
     * @param output multi line string to convert
     */
    public static void printStringBuilderOutput(String output) {
    	output = output.replace("\"", "\\\"");
    	String[] lines = output.split("\n");
    	
    	System.out.println("StringBuilder correctOutput = new StringBuilder();");
    	for (String line : lines) {
    		System.out.print("correctOutput.append(\"");
    		System.out.print(line);
    		System.out.println("\");\\n");
    	}
    	
    }
	
}
