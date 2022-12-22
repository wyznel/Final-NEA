import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Scanner;

public class Mailer {
    public static String deleteAccountCode;

    public static void send(String to, String data, String which){
        Main.log("Sending email...");
        String subject;
        String body;

        switch(which){
            case "CREATE" -> {
                subject = "Account Creation & Email Confirmation";
                body = "To complete account creation, enter this code into the console: `" + data + "`\n\nThis code will expire in 5 minutes.";
            }
            case "CREATE_SUCCESS" -> {
                subject = "New Account: '" + data + "' Has been created.";
                body = "Hi " + data + ", \nYou have finished the account creation section, have fun playing!";
            }
            case "DELETE_APPROVAL" -> {
                subject = "Account Deletion (APPROVAL)";
                body = "To complete account deletion, enter this code into the console: '" + data + "'";
                Main.log("Sent an email regarding the deletion of your account. ");
            }
            case "DELETE_SUCCESS" ->{
                subject = "Account Deletion - Success";
                body = "Your account: '" + data + "' has successfully been deleted. \n\nIf this wasn't you, please go to the \"DELETE ACCOUNT\" section and enter this four digit code: '" + deleteAccountCode + "' into the \"USERNAME\" field." ;
                Main.log("Sent an email regarding the deletion of your account. ");
            }
            case "FORGOT_USERNAME" -> {
                subject = "Account Retrieval (USERNAME)";
                body = "Looks like you forgot your username!\nDon't worry, we found it.\n\nYour Username is: '" + data + "'";
            }
            case "FORGOT_PASSWORD_CODE" -> {
                subject = "Account Retrieval (PASSWORD-CODE)";
                body = "Looks like you've forgotten your password!\nDon't worry, just enter this code into the console: '" + data + "'";
            }
            case "FORGOT_PASSWORD_NORMAL" -> {
                subject = "Account Retrieval (PASSWORD)";
                body = "Don't worry, we've found your password! \n\nYour password is: '" + data + "'";
            }
            case "ACCOUNT_RECOVERY_CODE" -> {
                subject = "ACCOUNT RECOVERY (CODE)";
                body = "To re-activate your account, please enter this code into the console: " + data;
            }
            case "ACCOUNT_RECOVERY_SUCCESS" -> {
                subject = "Account Recovery - Success";
                body = "Welcome back " + data + "! It's good to have you back.";
            }
            default -> {
                subject = "Hmm, somethings gone wrong!";
                body = "If you see this email, an error has occurred. Please respond to this email with steps taken just before this happened!";
            }

        }

        String from = "neadicerollgame@gmail.com";
        String password = "vqkgpjphylutmmff";

        //Get properties obj
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Session
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(from, password);
            }
        });

        //compose message
        try{
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            //send message
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
