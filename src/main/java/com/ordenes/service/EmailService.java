package com.ordenes.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarConfirmacionOrden(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        // En pruebas, puedes imprimir en consola en lugar de enviar
        System.out.println("[SIMULACIÓN] Correo a " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Contenido: " + cuerpo);

        // Descomenta esto si tienes Mailtrap o SMTP real:
        mailSender.send(mensaje);
    }
}
