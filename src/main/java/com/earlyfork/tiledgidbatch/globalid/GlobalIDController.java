package com.earlyfork.tiledgidbatch.globalid;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

        NodeList nodeListToUpdate = null;
        try {
            nodeListToUpdate = (NodeList) xPath.compile("/map/tileset").evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        if (nodeListToUpdate == null) {
            System.out.println("No tileset node found for " + doc.getDocumentURI());
            return;
        }

        int runningGIDTotal = startingGID;
        for (int i = 0; i < nodeListToUpdate.getLength(); i++) {

            if (nodeListToUpdate.item(i) == null) {
                continue;
            }
            this.updateNode(nodeListToUpdate.item(i), runningGIDTotal);
            runningGIDTotal += 10000;
        }
        
    }

    private void updateNode(Node node, int gidValue) {
        Node firstgid = node.getAttributes().getNamedItem("firstgid");
        firstgid.setNodeValue(String.valueOf(gidValue));
        node.getAttributes().setNamedItem(firstgid);
    }


}
