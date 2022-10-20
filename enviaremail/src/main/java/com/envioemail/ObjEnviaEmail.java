package com.envioemail;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjEnviaEmail {

    private String userName = "j.brito@estudante.firjan.senai.br";
    private String senha = "porra2323";
    private String listaDestinatarios = "";
    private String nomeRemetente = "";
    private String assuntoEmail = "";
    private String textoEmail = "";

    

    public  ObjEnviaEmail (String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail){

        this.listaDestinatarios = listaDestinatarios;
        this.nomeRemetente = nomeRemetente;
        this.assuntoEmail  = assuntoEmail;
        this.textoEmail = textoEmail;


    }

    public void enviarEmail(boolean envioHtml) throws Exception {
        try {

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");/* Autorização */
            properties.put("mail.smtp.starttls", "true"); /* Autenticação */
            properties.put("mail.smtp.host", "smtp.gmail.com"); /* Sercidor gmail Google */
            properties.put("mail.smtp.port", "465");/* Porta do servidor */
            properties.put("mail.smtp.socketFactory.port", "465");/* Expecifica a porta a ser conectada pelo socket */
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");/*
                                                                                               * Classe socket de
                                                                                               * conexão ao SMTP
                                                                                               */

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, senha);
                }
            });

            Address[] toUser = InternetAddress.parse(listaDestinatarios);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName, nomeRemetente)); /* Quem está enviano */
            message.setRecipients(Message.RecipientType.TO, toUser);/* Email de destino */
            message.setSubject(assuntoEmail);/* Assunto do e-mail */
            
            if(envioHtml){
               message.setContent(textoEmail,"text/html; charset=utf-8");
            }else{
                message.setText(textoEmail);
            }
            message.setText(textoEmail);

                Transport.send(message);

            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        /*================================================================= */
        public void enviarEmailAnexo(boolean envioHtml) throws Exception {
        try {

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");/* Autorização */
            properties.put("mail.smtp.starttls", "true"); /* Autenticação */
            properties.put("mail.smtp.host", "smtp.gmail.com"); /* Sercidor gmail Google */
            properties.put("mail.smtp.port", "465");/* Porta do servidor */
            properties.put("mail.smtp.socketFactory.port", "465");/* Expecifica a porta a ser conectada pelo socket */
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");/*
                                                                                               * Classe socket de
                                                                                               * conexão ao SMTP
                                                                                               */

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, senha);
                }
            });

            Address[] toUser = InternetAddress.parse(listaDestinatarios);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName, nomeRemetente)); /* Quem está enviano */
            message.setRecipients(Message.RecipientType.TO, toUser);/* Email de destino */
            message.setSubject(assuntoEmail);/* Assunto do e-mail */
            MimeBodyPart corpodoEmail = new MimeBodyPart();

            
            if(envioHtml){
               corpodoEmail.setContent(textoEmail,"text/html; charset=utf-8");
            }else{
                corpodoEmail.setText(textoEmail);
            }
            
            MimeBodyPart anexoEmail = new MimeBodyPart();
            anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladordePDF(), "application/pdf")));  
            anexoEmail.setFileName("anexEmail.pdf");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(corpodoEmail);
            multipart.addBodyPart(anexoEmail);
            message.setContent(multipart);

                
                
            
            Transport.send(message);

            

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("OOPS!!");
        }

        
    }
   private FileInputStream simuladordePDF() throws Exception{
    Document document = new Document();
    File file = new File("anexcriado.pdf");
    file.createNewFile();
    PdfWriter.getInstance(document, new FileOutputStream(file));
    document.open();
    document.add(new Paragraph("Conteudo pdf"));
    document.close();

    return new FileInputStream(file);


   }
}
