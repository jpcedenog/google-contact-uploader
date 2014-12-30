package org.example.google.contact;

import org.example.google.contact.util.Utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.example.google.contact.interfaces.ContactUploader;
import org.example.google.contact.interfaces.Parser;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import org.example.google.contact.impl.ContactManagementFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Uploads a list of contacts from a CSV file
 *
 * @author jpcedeno
 *
 */
public class GoogleContactsUploader {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleContactsUploader.class);

    public static void main(String args[]) {

        try {
            ContactManagementFactory contactManagementFactory = ContactManagementFactory.getInstance();
            Parser parser = contactManagementFactory.getParser(ContactManagementFactory.ParserType.SIMPLE);
            ContactUploader contactsUploader = contactManagementFactory.getContactUploader(ContactManagementFactory.ContactUploaderType.MULTI_THREADED);
            ContactsService contactsService = contactManagementFactory.getContactsService();

            GoogleContactsUploader uploader = new GoogleContactsUploader();

            if (args.length > 0) {
                Utils.printTimeStamp("Starting process...");
                uploader.uploadContacts(args[0], parser, contactsUploader, contactsService);
                Utils.printTimeStamp("Contacts were uploaded successfully!");
            } else {
                System.out.println("\nPlease specify the name of the input CSV file. The file should contain the following columns: \"full name\", \"email\", \"phone\"");
            }

        } catch (AuthenticationException e) {
            System.out.println("Authentication error: Please verify your Google credentials in the app.properties file");
        } catch (FileNotFoundException e) {
            System.out.println("File '" + args[0] + "' not found");
        } catch (IOException e) {
            System.out.println("Exception when trying to access the file. " + e.getMessage());
        } catch (ServiceException e) {
            System.out.println("Service Exception. Finishing application. " + e.getMessage());
        }
    }

    public void uploadContacts(String fileName, Parser parser, ContactUploader contactsUploader, ContactsService contactsService) throws IOException, ServiceException {

        List<ContactEntry> contacts;
        //Parse file
        contacts = parser.parseContactsCSVfile(fileName);
        LOG.debug("Information has been parsed (" + contacts.size() + " records)...");

        long startTime, endTime;

        startTime = System.currentTimeMillis();
        contactsUploader.uploadContactsInBatch(contactsService, contacts);
        endTime = System.currentTimeMillis();

        LOG.debug("All contacts were uploaded...");
        LOG.debug("\n\nTotal upload time: " + (endTime - startTime) + " ms");
    }
}
