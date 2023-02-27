package com.earlyfork.tiledgidbatch.xml;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class XmlFileIO {
    public ArrayList<Document> readInTMXFiles() {
        //read
        ArrayList<Document> listOfTMXFilesAsDocs = new ArrayList<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(new File("Hae-Catacombs01.tmx"));
            listOfTMXFilesAsDocs.add(doc);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        return listOfTMXFilesAsDocs;

    }
}
