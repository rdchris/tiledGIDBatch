package com.earlyfork.tiledgidbatch.globalid;

import com.earlyfork.tiledgidbatch.pojos.TilesetChangeset;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.LinkedList;

@Component
public class NodesController {


    public void updateDataNodes(Document doc, LinkedList<TilesetChangeset> tilesetChangesets) {

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeListToUpdate = null;
        try {
            nodeListToUpdate = (NodeList) xPath.compile("/map/layer/data").evaluate(doc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < nodeListToUpdate.getLength(); i++) {
            nodeListToUpdate.item(i).getChildNodes().item(0).getNodeValue();
            // break into Arraylist
            // check to see if has been already updated
        }

        System.out.println("Lets see");
    }
}
