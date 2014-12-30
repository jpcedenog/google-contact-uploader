package org.example.google.contact.impl;

import java.io.IOException;

import org.example.google.contact.interfaces.Parser;
import com.csvreader.CsvReader;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Implementation of {@link Parser}
 *
 * @author jpcedeno
 *
 */
public class SimpleParser extends BaseParser {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleParser.class);

    /**
     *
     * Creates a ContactEntry using full name, email, and phone
     *
     * @param csvContacts
     * @return
     */
    @Override
    protected ContactEntry createContactEntry(CsvReader csvContacts) {
        ContactEntry contact = null;

        try {
            contact = new ContactEntry();

            Name name = new Name();
            name.setFullName(new FullName(csvContacts.get("full name"), null));
            contact.setName(name);

            Email primaryMail = new Email();
            primaryMail.setAddress(csvContacts.get("email"));
            primaryMail.setRel("http://schemas.google.com/g/2005#home");
            primaryMail.setPrimary(true);
            contact.addEmailAddress(primaryMail);

            PhoneNumber phone = new PhoneNumber();
            phone.setPhoneNumber(csvContacts.get("phone"));
            phone.setLabel("Label");
            contact.addPhoneNumber(phone);

        } catch (IOException e) {
            LOG.error("Cannot create contact entry: " + e.getMessage());
            e.printStackTrace();
        }
        return contact;
    }
}
