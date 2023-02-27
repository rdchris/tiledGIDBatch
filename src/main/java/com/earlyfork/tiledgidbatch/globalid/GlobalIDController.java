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

    int runningGIDTotal = 0;

    public void updateGlobalIds(ArrayList<Document> docs) {
        int startingGID = 1;
        for (Document doc : docs) {
            this.updateGlobalId(doc, startingGID);
            startingGID += 10000;
        }

    }

    private void updateGlobalId(Document doc, int startingGID) {
        XPath xPath = XPathFactory.newInstance().newXPath();

        Node startDateNode = null;
        try {
            startDateNode = (Node) xPath.compile("/map/tileset").evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        Node firstgid = startDateNode.getAttributes().getNamedItem("firstgid");
        firstgid.setNodeValue(String.valueOf(startingGID));
        startDateNode.getAttributes().setNamedItem(firstgid);
    }

    public void resetForNewFile() {
        runningGIDTotal = 0;
    }

}
