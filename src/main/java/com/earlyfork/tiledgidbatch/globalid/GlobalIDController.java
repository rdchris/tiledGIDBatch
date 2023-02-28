package com.earlyfork.tiledgidbatch.globalid;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;

@Component
public class GlobalIDController {


    public void updateGlobalIds(ArrayList<Document> docs) {
        int startingGID = 1;
        for (Document doc : docs) {
            this.updateGlobalIdsInDoc(doc, startingGID);
            startingGID += 10000;
        }

    }

    private void updateGlobalIdsInDoc(Document doc, int startingGID) {
        XPath xPath = XPathFactory.newInstance().newXPath();

        Node startDateNode = null;
        try {
            startDateNode = (Node) xPath.compile("/map/tileset").evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        if (startDateNode == null) {
            System.out.println("No tileset node found for " + doc.getDocumentURI());
            return;
        }

        this.updateNode(startDateNode, startingGID);

        // Update all siblings
        int runningGIDTotal = startingGID;
        while (startDateNode.getNextSibling() != null) {
            Node nextSibling = startDateNode.getNextSibling();
            runningGIDTotal += 10000;
            this.updateNode(nextSibling, runningGIDTotal);
        }
    }

    private void updateNode(Node node, int gidValue) {
        Node firstgid = node.getAttributes().getNamedItem("firstgid");
        firstgid.setNodeValue(String.valueOf(gidValue));
        node.getAttributes().setNamedItem(firstgid);
    }


}
