package com.earlyfork.tiledgidbatch.globalid;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

@Component
public class GlobalIDController {

    public void updateGlobalId(Document doc) {
        XPath xPath = XPathFactory.newInstance().newXPath();

        Node startDateNode = null;
        try {
            startDateNode = (Node) xPath.compile("/map/tileset").evaluate(doc, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        Node firstgid = startDateNode.getAttributes().getNamedItem("firstgid");
        firstgid.setNodeValue("1b");
        startDateNode.getAttributes().setNamedItem(firstgid);
    }

}
