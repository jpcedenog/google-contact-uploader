package org.example.google.contact.impl;

import com.google.gdata.data.contacts.ContactEntry;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author JP Cedeno
 */
public class SimpleParserTest {

    SimpleParser simpleParser;
    List<ContactEntry> contacts;

    public SimpleParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        simpleParser = new SimpleParser();
        try {
            contacts = simpleParser.parseContactsCSVfile("test_contacts.csv");

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test that all the records are parsed
     */
    @Test
    public void testNumberOfParsedRecords() {
        assertEquals("Test file contains 1 record, but SimpleParser found " + contacts.size(), 1, contacts.size());
    }

    /**
     * Test the contact information is retrieved correctly
     */
    @Test
    public void testContactInformation() {
        ContactEntry contact = contacts.get(0);
        
        assertEquals("Contact's name is incorrect", "John Smith", contact.getName().getFullName().getValue());
        
        assertEquals("Contact should 1 and only 1 email address", 1, contact.getEmailAddresses().size());
        assertEquals("Contact's email is incorrect", "john.smith@example.org", contact.getEmailAddresses().get(0).getAddress());
        
        assertEquals("Contact should 1 and only 1 phone number", 1, contact.getPhoneNumbers().size());
        assertEquals("Contact's phone number is incorrect", "111.111.1111", contact.getPhoneNumbers().get(0).getPhoneNumber());
    }
}
