package org.example.google.contact.impl;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.util.AuthenticationException;
import org.example.google.contact.util.PropertiesProvider;

/**
 *
 * Creates an instance of a Google contacts service. Credentials must be
 * specified in app.properties file
 *
 * @author JP Cedeno
 */
public class GoogleContactsServiceProvider {

    public ContactsService getContactsService() throws AuthenticationException {
        ContactsService service = new ContactsService("SampleApp");
        service.setUserCredentials(PropertiesProvider.getProperty("email"), PropertiesProvider.getProperty("password"));
        return service;
    }
}
