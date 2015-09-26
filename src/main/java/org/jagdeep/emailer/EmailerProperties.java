/**
 * @author jagdeepjain
 *
 */
package org.jagdeep.emailer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class EmailerProperties {
    
    public HashMap<String, String> getProperties() throws IOException {
        
        HashMap<String, String> properties = new HashMap<String, String>();
        Properties emailer = new Properties();
        String emailerProperties = "emailer.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(emailerProperties);
        
        if (inputStream != null) {
            emailer.load(inputStream);
        } else {
            throw new FileNotFoundException("Property file '" + emailerProperties + "' not found.");
        }
        
        properties.put("emailReceiver", emailer.getProperty("email.receiver"));
        properties.put("emailValid", emailer.getProperty("email.valid"));
        properties.put("emailInvalid", emailer.getProperty("email.invalid"));

        properties.put("applicationVersion", emailer.getProperty("application.version"));
        properties.put("mailSmtpHost", emailer.getProperty("mail.smtp.host"));
        properties.put("mailSmtpSocketFactoryPort", emailer.getProperty("mail.smtp.socketFactory.port"));
        properties.put("mailSmtpSocketFactoryClass", emailer.getProperty("mail.smtp.socketFactory.class"));
        properties.put("mailSmtpAuth", emailer.getProperty("mail.smtp.auth"));
        properties.put("mailSmtpPort", emailer.getProperty("mail.smtp.port"));
        properties.put("userName", emailer.getProperty("userName"));
        properties.put("password", emailer.getProperty("password"));
        properties.put("sentFrom", emailer.getProperty("sentFrom"));
        properties.put("emailSubject", emailer.getProperty("email.subject"));
        properties.put("emailBodyMessage", emailer.getProperty("email.body.message"));
        properties.put("emailBannerPath", emailer.getProperty("email.banner.path"));
        properties.put("emailFooter", emailer.getProperty("email.footer"));

        
        return properties;
        
    }
    
}
