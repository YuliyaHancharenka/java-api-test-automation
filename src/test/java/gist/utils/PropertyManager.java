package gist.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final Properties properties;
    private static final String PROPERTIES_FILE_PATH = "/application.properties";

    static {
        properties = new Properties();
        InputStream is = PropertyManager.class.getResourceAsStream(PROPERTIES_FILE_PATH);
        try {
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("The app properties file cannot be read from '" + PROPERTIES_FILE_PATH, e);
        }
    }

    public static String get (String propertyName) {
        return properties.getProperty(propertyName);
    }
}