package br.ufs.dcomp.farms.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@SuppressWarnings("unused")
public class FarmsMail {

    final static String ACCOUNT_CONFIRMATION_EMAIL_TEMPLATE_HTML = "templates/account-confirmation-email-template.html";
    final static String INVITE_MEMBER_EMAIL_TEMPLATE_HTML = "templates/invite-register-email.html";
    final static String REQUEST_NEW_PASSWORD_EMAIL_TEMPLATE_HTML = "templates/new-password-email.html";

    /**
     * Method to send an Email.
     *
     * @param dsMailTo
     * @param dsSubject
     * @param dsBodyMessage
     */
    public static void sendMailText(String dsMailTo, String dsSubject, String dsBodyMessage) {

        String farmsMailSmtpHost = FarmsProperties.load().getProperty("farms.mail.smtp.host");
        String farmsMailFromName = FarmsProperties.load().getProperty("farms.mail.contact.name");
        String farmsMailFrom = FarmsProperties.load().getProperty("farms.mail.contact");
        String farmsMailPassword = FarmsProperties.load().getProperty("farms.mail.password");

        // Set mail properties.
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", farmsMailSmtpHost);
        props.put("mail.smtp.user", farmsMailFrom);
        props.put("mail.smtp.password", farmsMailPassword);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("contact.farms@gmail.com", "farmsX21@");
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(farmsMailFrom));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(dsMailTo));

            // Set Subject: header field
            message.setSubject(dsSubject);

            // Now set the actual message
            message.setText(dsBodyMessage);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    /**
     * Send registration confirmation email.
     *
     * @param nmResearcher
     * @param dsMailTo
     * @param cdUuid
     */
    public static void sendAccountConfirmationEmail(String nmResearcher, String dsMailTo, String cdUuid) {
        String farmsSiteUrl = FarmsProperties.load().getProperty("farms.site.url");
        String dsSubject = "Confirm email";
        // Set key values.
        Map<String, String> bodyKeyValueMap = new HashMap<>();
        bodyKeyValueMap.put("{{researcher-name}}", nmResearcher);
        bodyKeyValueMap.put("{{url-site}}", farmsSiteUrl);
        String urlEmailConfirmation = farmsSiteUrl + "#/confirmation?u=" + cdUuid;
        bodyKeyValueMap.put("{{url-email-confirmation}}", urlEmailConfirmation);
        new FarmsMail().sendMail(dsMailTo, dsSubject, bodyKeyValueMap, 1);
    }

    /**
     * Send invite email.
     *
     * @param dsMailTo
     */
    public static void sendInviteEmail(String dsMailTo) {
        String farmsSiteUrl = FarmsProperties.load().getProperty("farms.site.url");
        String dsSubject = "Invite email";
        // Set key values.
        Map<String, String> bodyKeyValueMap = new HashMap<>();
        bodyKeyValueMap.put("{{url-site}}", farmsSiteUrl);
        new FarmsMail().sendMail(dsMailTo, dsSubject, bodyKeyValueMap, 2);
    }

    /**
     * Send email to new Password
     *
     * @param nmResearcher
     * @param dsMailTo
     * @param cdUuid
     */
    public static void sendNewPasswordEmail(String nmResearcher, String dsMailTo, String cdUuid) {
        String farmsSiteUrl = FarmsProperties.load().getProperty("farms.site.url");
        String dsSubject = "Change Your Password of FARMS";
        // Set key values.
        Map<String, String> bodyKeyValueMap = new HashMap<>();
        bodyKeyValueMap.put("{{researcher-name}}", nmResearcher);
        bodyKeyValueMap.put("{{url-site}}", farmsSiteUrl);
        String urlEmailConfirmation = farmsSiteUrl + "#/newPassword?u=" + cdUuid;
        bodyKeyValueMap.put("{{url-email-confirmation}}", urlEmailConfirmation);
        new FarmsMail().sendMail(dsMailTo, dsSubject, bodyKeyValueMap, 3);
    }

    /**
     * Method to send an Email.
     *
     * @param dsMailTo
     * @param dsSubject
     * @param bodyKeyValueMap
     * @param type
     */
    public void sendMail(String dsMailTo, String dsSubject, Map<String, String> bodyKeyValueMap,
            int type) {
        try {
            String farmsMailSmtpHost = FarmsProperties.load().getProperty("farms.mail.smtp.host");
            String farmsMailFromName = FarmsProperties.load().getProperty("farms.mail.contact.name");
            String farmsMailFrom = FarmsProperties.load().getProperty("farms.mail.contact");
            String farmsMailPassword = FarmsProperties.load().getProperty("farms.mail.password");

            // Set mail properties.
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", farmsMailSmtpHost);
            props.put("mail.smtp.user", farmsMailFrom);
            props.put("mail.smtp.password", farmsMailPassword);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);

            try {
                // Set email data.
                message.setFrom(new InternetAddress(farmsMailFrom, farmsMailFromName));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(dsMailTo));
                message.setSubject(dsSubject);
                MimeMultipart multipart = new MimeMultipart();
                BodyPart messageBodyPart = new MimeBodyPart();

                // HTML mail content.
                ClassLoader classLoader = this.getClass().getClassLoader();
                File htmlTemplateFile = null;
                if (type == 1) {
                    htmlTemplateFile = new File(classLoader.getResource(ACCOUNT_CONFIRMATION_EMAIL_TEMPLATE_HTML).getFile());
                }

                if (type == 2) {
                    htmlTemplateFile = new File(classLoader.getResource(INVITE_MEMBER_EMAIL_TEMPLATE_HTML).getFile());
                }

                if (type == 3) {
                    htmlTemplateFile = new File(classLoader.getResource(REQUEST_NEW_PASSWORD_EMAIL_TEMPLATE_HTML).getFile());
                }

                String htmlText = readEmailFromHtml(htmlTemplateFile.getPath(), bodyKeyValueMap);
                messageBodyPart.setContent(htmlText, "text/html");

                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);

                // Connect to smtp server and send Email.
                Transport transport = session.getTransport("smtp");
                transport.connect(farmsMailSmtpHost, farmsMailFrom, farmsMailPassword);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                System.out.println("Mail sent successfully...");
            } catch (MessagingException ex) {
                ex.printStackTrace();
            } catch (Exception ae) {
                ae.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method to replace the values for keys
     *
     * @param filePath
     * @param input
     * @return
     */
    protected String readEmailFromHtml(String filePath, Map<String, String> input) {
        String msg = readContentFromFile(filePath);
        try {
            Set<Entry<String, String>> entries = input.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return msg;
    }

    /**
     * Method to read HTML file as a String.
     *
     * @param fileName
     * @return
     */
    private String readContentFromFile(String fileName) {
        StringBuffer contents = new StringBuffer();
        try {
            // Use buffering, reading one line at a time.
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            try {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return contents.toString();
    }
}
