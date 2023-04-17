package com.nhlstenden.swiftionwebapp.utility_classes;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

/*****************************************************************
 * The MessageHandler class is responsible for handling messages
 * that are sent to the user with redirects
 ****************************************************************/
public class MessageHandler {

    public static void redirectMessageHandler(HttpSession session, Model model, String mode) {
        if (session.getAttribute("SESSION-MESSAGE") != null){
            System.out.println("message is: " + session.getAttribute("SESSION-MESSAGE"));
            model.addAttribute("messageHandling", Converter.convertOutputToObject(session.getAttribute("SESSION-MESSAGE").toString(), mode).get("data"));
            session.removeAttribute("SESSION-MESSAGE");
        }
    }
}