package org.example.google.contact.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;

/**
 * 
 * Interface to parse a CSV file containing a list of contacts
 * 
 * @author JP Cedeno
 *
 */
public interface Parser {

	/**
	 * 
	 * Parses the CSV file 'fileName' containing contacts information
	 * 
	 * @param fileName
	 *            The name of the file to be parsed
	 * @return The list of contact entries returned by the contact feed
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<ContactEntry> parseContactsCSVfile(String fileName)
			throws FileNotFoundException, IOException;
}
