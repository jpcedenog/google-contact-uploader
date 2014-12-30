package org.example.google.contact.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * Properties provider for the entire application. Loads default values for the
 * properties and updates them only if the app.properties file is available
 *
 * @author JP Cedeno
 */
public class PropertiesProvider {

    private static PropertiesProvider provider;
    private Properties appProps;

    private PropertiesProvider() {
        /* Load default properties */
        appProps = new Properties();
        appProps.setProperty("email", "test@gmail.com");
        appProps.setProperty("password", "test1234");
        appProps.setProperty("maxFeedEntries", "10");
        appProps.setProperty("maxThreads", "100");

        try (InputStream input = new FileInputStream("app.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            this.updateOriginalPropertiesWithNewProperties(appProps, prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String property) {
        String result = null;
        if (provider == null) {
            provider = new PropertiesProvider();
        }
        if (provider.getAppProps().containsKey(property)) {
            result = provider.getAppProps().getProperty(property);
        }
        return result;
    }

    private void updateOriginalPropertiesWithNewProperties(Properties originalProps, Properties newProps) {
        for (Object key : newProps.keySet()) {
            if (originalProps.containsKey(key)) {
                originalProps.setProperty((String) key, newProps.getProperty((String) key));
            }
        }
    }

    public Properties getAppProps() {
        return appProps;
    }
}
