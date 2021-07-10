/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Sony
 */
public class PodesavanjaLoader {

    private Properties properties;
    private static PodesavanjaLoader instance;

    private PodesavanjaLoader() {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static PodesavanjaLoader getInstance() {
        if (instance == null) {
            instance = new PodesavanjaLoader();
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key, "N/A");
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void saveProperties() throws FileNotFoundException, IOException {
        properties.store(new FileOutputStream("config.properties"), "Atributi sačuvani iz forme.");
    }
}