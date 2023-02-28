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
import java.util.Collection;

@Component
public class XmlFileIO {
    public ArrayList<Document> readInTMXFiles(Collection<File> tmxFiles) {

        //read
        ArrayList<Document> listOfTMXFilesAsDocs = new ArrayList<>();

        for (File file : tmxFiles) {
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc = documentBuilder.parse(file);
                listOfTMXFilesAsDocs.add(doc);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (IOException | ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }

        return listOfTMXFilesAsDocs;

    }

    public void saveTMXFiles(Collection<File> tmxFiles, ArrayList<Document> documents) throws TransformerException {

        // Array list of TMX file
        ArrayList<File> fileArrayList = new ArrayList<>();
        fileArrayList.addAll(tmxFiles);

        // Array list of XML documents
        ArrayList<Document> xmlDocumentArrayList = new ArrayList<>();
        xmlDocumentArrayList.addAll(documents);

        if (xmlDocumentArrayList.size() != fileArrayList.size()) {
            System.out.println("CRITICAL ERROR!!! ArrayLists on FileSave do not match! Sizes are");
            System.out.println("fileArraylist.size() : " + fileArrayList.size());
            System.out.println("xmlDocumentArrayList.size() : " + xmlDocumentArrayList.size());
        }
        // using for i to access both arraylists of the same index
        for (int i = 0; i < fileArrayList.size(); i++) {
            //Then save the Document back to the file...
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource domSource = new DOMSource(xmlDocumentArrayList.get(i));
            StreamResult sr = new StreamResult(fileArrayList.get(i));

            transformer.transform(domSource, sr);
        }

    }
}
