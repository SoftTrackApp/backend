package ru.softtrack.util;

import java.io.File;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

public class DbConfig {
    private static PropertiesConfiguration config;
        
        static {
            try {
                Configurations configs = new Configurations();
                config = configs.properties(new File("database.properties"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    public static String getUrl() {
        return config.getString("db.url");
    }
    
    public static String getUser() {
        return config.getString("db.user");
    }
    
    public static String getPassword() {
        return config.getString("db.password");
    }
}
