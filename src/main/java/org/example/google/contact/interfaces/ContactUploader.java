package org.example.google.contact.interfaces;

import java.io.IOException;
import java.util.List;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;

/**
 *
 * Provider of operations to manage contacts
 *
 * @author JP Cedeno
 *
 */
public interface ContactUploader {

    /**
     *
     * Uploads a list of contacts in batch
     *
     * @param contacts The list of contacts
     * @return The feed returned by the insertion service
     * @throws ServiceException
     * @throws IOException
     */
    public ContactFeed uploadContactsInBatch(ContactsService service,
            List<ContactEntry> contacts) throws ServiceException, IOException;

    /**
     *
     * Uploads a single contact
     *
     * @param contact The contact to be uploaded
     * @return
     * @throws ServiceException
     * @throws IOException
     */
    public ContactEntry uploadSingleContact(ContactEntry contact)
            throws ServiceException, IOException;
}
