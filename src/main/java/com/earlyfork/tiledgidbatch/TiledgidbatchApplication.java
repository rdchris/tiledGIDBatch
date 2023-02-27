package com.earlyfork.tiledgidbatch;

import com.earlyfork.tiledgidbatch.xml.XmlFileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;

@SpringBootApplication
public class TiledgidbatchApplication implements CommandLineRunner {

    @Autowired
    XmlFileIO xmlFileIO;

    public static void main(String[] args) {
        SpringApplication.run(TiledgidbatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ArrayList<Document> documents = xmlFileIO.readInTMXFiles();

        Document doc = documents.get(0);

        //update
        XPath xPath = XPathFactory.newInstance().newXPath();
        Node startDateNode = (Node) xPath.compile("/map/tileset").evaluate(doc, XPathConstants.NODE);
        Node firstgid = startDateNode.getAttributes().getNamedItem("firstgid");
        firstgid.setNodeValue("123");
        startDateNode.getAttributes().setNamedItem(firstgid);


        //Then save the Document back to the file...
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource domSource = new DOMSource(doc);
        StreamResult sr = new StreamResult(new File("Hae-Catacombs01.tmx"));
        transformer.transform(domSource, sr);
    }
}
