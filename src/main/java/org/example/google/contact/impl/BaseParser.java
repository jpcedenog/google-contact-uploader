package org.example.google.contact.impl;

import com.csvreader.CsvReader;
import com.google.gdata.data.contacts.ContactEntry;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.example.google.contact.interfaces.Parser;

/**
 *
 * Partially implements {@link Parser}, and provides a method to read a CSV file
 * containing contacts information, line by line.
 *
 * @author JP Cedeno
 */
public abstract class BaseParser implements Parser {

    @Override
    public final List<ContactEntry> parseContactsCSVfile(String fileName) throws FileNotFoundException, IOException {

        List<ContactEntry> contacts = new ArrayList<>();
        CsvReader csvContacts = new CsvReader(fileName);

        //Read the the headers line
        csvContacts.readHeaders();

        while (csvContacts.readRecord()) {
            //Use a custom implementation to create a contact entry
            ContactEntry contact = createContactEntry(csvContacts);
            if (contact != null) {
                contacts.add(contact);
            }
        }

        return contacts;
    }

    protected abstract ContactEntry createContactEntry(CsvReader csvContacts);
}
