package com.earlyfork.tiledgidbatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;

@SpringBootApplication
public class TiledgidbatchApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(TiledgidbatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //read
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new File("Hae-Catacombs01.tmx"));

        //update
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node startDateNode = (Node) xPath.compile("/data/startdate").evaluate(doc, XPathConstants.NODE);
        startDateNode.setTextContent("29/07/2015");

        xPath = XPathFactory.newInstance().newXPath();
        Node endDateNode = (Node) xPath.compile("/data/enddate").evaluate(doc, XPathConstants.NODE);
        endDateNode.setTextContent("29/07/2015");

        //Then save the Document back to the file...
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource domSource = new DOMSource(doc);
        StreamResult sr = new StreamResult(new File("Hae-Catacombs01.tmx"));
        transformer.transform(domSource, sr);
    }
}
