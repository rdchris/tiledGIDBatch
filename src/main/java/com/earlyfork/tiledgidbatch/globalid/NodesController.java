package com.earlyfork.tiledgidbatch.globalid;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

@Component
public class NodesController {


    public void updateDataNodes(Document doc, String oldNodeValue, String nextTileSetGID, int runningTotalGID) {
        XPath xPath = XPathFactory.newInstance().newXPath();

        try {
            NodeList nodeListToUpdate = (NodeList) xPath.compile("/map/layer/data").evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Lets see");
    }
}
