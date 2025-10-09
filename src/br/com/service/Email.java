package br.com.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Email {

    // Aqui você coloca o e-mail remetente e a senha de app da sua conta
    private static final String REMETENTE = "sistemaestoquejava@gmail.com";
    private static final String SENHA_APP = "hwgj grnj paun nqkd";

    public static void enviarEmail(String destinatario, String assunto, String mensagemTexto) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE, SENHA_APP);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMETENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);
            message.setText(mensagemTexto);

            Transport.send(message);
            System.out.println("✅ E-mail enviado com sucesso para " + destinatario);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("❌ Falha ao enviar o e-mail para " + destinatario);
        }
    }
}