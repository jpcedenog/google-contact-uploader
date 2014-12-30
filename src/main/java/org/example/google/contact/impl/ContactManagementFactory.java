package org.example.google.contact.impl;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.util.AuthenticationException;
import org.example.google.contact.interfaces.ContactUploader;
import org.example.google.contact.interfaces.Parser;

/**
 *
 * Factory to produce the objects required by the upload process: Parser,
 * Contact uploader, and Google Contacts service. This is the entry point for
 * additional implementations of Parser or ContactUploader
 *
 * @author JP Cedeno
 */
public class ContactManagementFactory {

    private static ContactManagementFactory factory;

    public static enum ParserType {

        SIMPLE
    }

    public static enum ContactUploaderType {

        MULTI_THREADED;
    }

    private ContactManagementFactory() {
    }

    public static ContactManagementFactory getInstance() {
        if (factory == null) {
            factory = new ContactManagementFactory();
        }
        return factory;
    }

    public Parser getParser(ParserType parserType) {
        switch (parserType) {
            case SIMPLE:
                return new SimpleParser();
            default:
                return new SimpleParser();
        }
    }

    public ContactUploader getContactUploader(ContactUploaderType contactUploaderType) {
        switch (contactUploaderType) {
            case MULTI_THREADED:
                return new MultiThreadContactUploader();
            default:
                return new MultiThreadContactUploader();
        }
    }

    public ContactsService getContactsService() throws AuthenticationException {
        return new GoogleContactsServiceProvider().getContactsService();
    }
}
