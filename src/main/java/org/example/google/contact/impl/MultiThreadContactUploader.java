package org.example.google.contact.impl;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.example.google.contact.interfaces.ContactUploader;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.ServiceException;
import org.example.google.contact.util.PropertiesProvider;
import org.example.google.contact.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Implementation of {@link ContactManager#}. It uses threads to optimize the
 * upload of contacts.
 *
 * @author jpcedeno
 *
 */
public class MultiThreadContactUploader implements ContactUploader {

    private static final Logger LOG = LoggerFactory.getLogger(MultiThreadContactUploader.class);

    /* The maximum number of entries that can be uploaded in one feed */
    private final int MAX_FEED_ENTRIES;

    /* The maximum number of threads that should be initiated in order to process the contacts upload */
    private final int MAX_THREADS;

    public MultiThreadContactUploader() {
        this.MAX_FEED_ENTRIES = Utils.parseInt(PropertiesProvider.getProperty("maxFeedEntries"));
        this.MAX_THREADS = Utils.parseInt(PropertiesProvider.getProperty("maxThreads"));
    }

    @Override
    public ContactFeed uploadContactsInBatch(ContactsService service,
            List<ContactEntry> contacts) {

        ContactFeed response = new ContactFeed();
        Thread[] threads = new Thread[MAX_THREADS];
        int batchSize = contacts.size() / MAX_THREADS; /* Calculate the size of the batch to be sent to each thread based on the number of threads */

        if (batchSize <= 0) {
            batchSize = contacts.size();
        }

        /* Prepare each thread and start it */
        for (int i = 0, j = 0; i < contacts.size(); i += batchSize, j++) {
            threads[j] = new Thread(new RunnableContactManager(service,
                    contacts.subList(i, i + batchSize), MAX_FEED_ENTRIES));
            threads[j].start();
        }
        return response;
    }

    @Override
    public ContactEntry uploadSingleContact(ContactEntry contact)
            throws ServiceException, IOException {
        // TODO Yet to be implemented...
        return null;
    }

    /**
     *
     * Runnable implementation for contact manager
     *
     * @author jpcedeno
     *
     */
    private class RunnableContactManager implements Runnable {

        private ContactsService service;
        private List<ContactEntry> contacts;
        private ContactFeed response = new ContactFeed();
        private int maxFeed;

        public RunnableContactManager(ContactsService service,
                List<ContactEntry> contacts, int maxFeed) {

            if (service == null || contacts == null) {
                throw new IllegalArgumentException(
                        "Service and Contact list cannot be null");
            }
            this.service = service;
            this.contacts = contacts;
            this.maxFeed = maxFeed;
        }

        @Override
        public void run() {
            ContactFeed request = new ContactFeed();
            ContactEntry contact;

            try {
                for (int i = 0; i < this.contacts.size(); i++) {
                    contact = this.contacts.get(i);

                    // Prepare each contact for upload
                    BatchUtils.setBatchId(contact, "create");
                    BatchUtils.setBatchOperationType(contact,
                            BatchOperationType.INSERT);
                    request.getEntries().add(contact);

                    // if batch is full, upload it
                    if ((i + 1) % this.maxFeed == 0
                            || i == (this.contacts.size() - 1)) {

                        ContactFeed tmpResponse = this.service
                                .batch(new URL(
                                                "https://www.google.com/m8/feeds/contacts/default/full/batch"),
                                        request);
                        this.response.getEntries().addAll(
                                tmpResponse.getEntries());
                        request = new ContactFeed();
                    }
                }
            } catch (IOException | ServiceException e) {
                LOG.error("Exception while sending batch to feed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
