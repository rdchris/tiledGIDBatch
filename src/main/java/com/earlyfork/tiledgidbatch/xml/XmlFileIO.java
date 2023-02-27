package com.earlyfork.tiledgidbatch.xml;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

    public void saveTMXFiles(ArrayList<Document> documents) throws TransformerException {

        for (Document doc : documents) {
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
}
