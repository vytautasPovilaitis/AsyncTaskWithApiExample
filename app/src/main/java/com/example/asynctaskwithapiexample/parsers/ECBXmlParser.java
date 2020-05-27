package com.example.asynctaskwithapiexample.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ECBXmlParser{
    public static List<String> getECBCurrencyRates(InputStream stream) throws IOException {
        List<String> result = new ArrayList<>();
        try {
            DocumentBuilderFactory xmlDocFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlDocBuilder = xmlDocFactory.newDocumentBuilder();
            Document doc = xmlDocBuilder.parse(stream);

            NodeList rateNodes = doc.getElementsByTagName("Cube");
            for (int i = 2; i < rateNodes.getLength(); ++i) {
                NamedNodeMap rateAttributes = rateNodes.item(i).getAttributes();
                String currencyName = rateAttributes.getNamedItem("currency").getNodeValue();
                String rate = rateAttributes.getNamedItem("rate").getNodeValue();
                result.add(String.format("Currency name: %s, rate %s", currencyName, rate));
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return result;
    }
}

