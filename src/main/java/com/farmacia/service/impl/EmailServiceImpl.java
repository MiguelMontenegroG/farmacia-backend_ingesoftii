package com.farmacia.service.impl;
import com.farmacia.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarCodigo(String destinatario, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de verificación - FarmaNet");
        mensaje.setText("Tu código de verificación es: " + codigo + "\n\nGracias por registrarte en FarmaNet 💊");

        mailSender.send(mensaje);
    }
}
