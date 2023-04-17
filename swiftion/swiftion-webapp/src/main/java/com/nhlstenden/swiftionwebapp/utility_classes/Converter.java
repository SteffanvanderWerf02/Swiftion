package com.nhlstenden.swiftionwebapp.utility_classes;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/*****************************************************************
 * This class is the converter class. It converts the message on
 * JSON and XML format
 ****************************************************************/
public class Converter {
    public static JSONObject stringToJson(String jsonString) {
        System.out.println("json string " + jsonString);
        JSONObject obj = new JSONObject(jsonString);
        return obj;
    }

    public static JSONObject xmlToObject(String xmlString) {
        JSONObject obj = XML.toJSONObject(xmlString);
        JSONArray arr = new JSONArray();

        if (!obj.getJSONObject("root").has("data")) {
            obj.getJSONObject("root").put("data", arr);
        } else if (obj.getJSONObject("root").get("data") instanceof JSONObject) {
            arr.put(obj.getJSONObject("root").getJSONObject("data"));
            obj.getJSONObject("root").put("data", arr);
        }

        return obj.getJSONObject("root");
    }

    public static JSONObject convertOutputToObject(String output, String mode) {
        JSONObject obj = null;
        if (mode.equalsIgnoreCase("json")) {
            obj = Converter.stringToJson(output);
        } else if (mode.equalsIgnoreCase("xml")) {
            obj = Converter.xmlToObject(output);
        }
        return obj;
    }

    public static String buildReturnMessage(String mode, String message, String status) {
        String messageReturn = null;
        if (mode.equalsIgnoreCase("json")) {
            messageReturn = buildJSONReturnMessage(message, status);
        } else if (mode.equalsIgnoreCase("xml")) {
            messageReturn = buildXMLReturnMessage(message, status);
        }

        return messageReturn;
    }

    public static String buildJSONReturnMessage(String message, String status) {
//              System.out.println(String.format("{\"data\":[{\"message\":\"%s\"}],\"status\":\"%s\"}", message, status));
        return String.format("{\"data\":[{\"message\":\"%s\"}],\"status\":\"%s\"}", message, status);
    }

    public static String buildXMLReturnMessage(String message, String status) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("root");
            document.appendChild(root);

            Element data = document.createElement("data");
            root.appendChild(data);

            Element messageElement = document.createElement("message");
            messageElement.appendChild(document.createTextNode(message));
            data.appendChild(messageElement);

            Element statusElement = document.createElement("status");
            statusElement.appendChild(document.createTextNode(status));
            root.appendChild(statusElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4" );

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new StringWriter());
            transformer.transform(domSource, streamResult);
            System.out.println(streamResult.getWriter().toString());
            return streamResult.getWriter().toString();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}