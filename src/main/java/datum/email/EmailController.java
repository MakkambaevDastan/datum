//package datum.email;
//
//// Java Program to Create Rest Controller that
//// Defines various API for Sending Mail
//
////package com.SpringBootEmail.controller;
//
//// Importing required classes
////import com.SpringBootEmail.Entity.EmailDetails;
////import com.SpringBootEmail.service.EmailService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class EmailController {
//
//    private final EmailService emailService;
//
//    @PostMapping("/sendMail")
//    public String sendMail(@RequestBody EmailDetails details) {
//        return emailService.sendSimpleMail(details);
//    }
//
//    @PostMapping("/sendMailWithAttachment")
//    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
//        return emailService.sendMailWithAttachment(details);
//
//    }
//}