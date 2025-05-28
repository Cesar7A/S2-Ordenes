package com.order.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private TemplateEngine templateEngine;

  public void sendOrderStatusUpdateEmail(String to, String orderId, String oldStatus, String newStatus, BigDecimal amount) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(to);
      helper.setSubject("Your Order " + orderId + " Status Updated");

      Context context = new Context();
      context.setVariable("orderId", orderId);
      context.setVariable("oldStatus", oldStatus);
      context.setVariable("newStatus", newStatus);
      context.setVariable("amount", amount);

      String htmlContent = templateEngine.process("status-update", context);
      helper.setText(htmlContent, true);

      mailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}