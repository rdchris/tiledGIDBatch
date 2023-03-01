package com.earlyfork.tiledgidbatch.globalid;

import com.earlyfork.tiledgidbatch.pojos.TilesetChangeset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.LinkedList;

@Component
public class GlobalIDController {

    int runningTotalGID = 1;
    int globalIdIncrement = 15000;

    @Autowired
    NodesController nodesController;

    public LinkedList<TilesetChangeset> updateGlobalIds(ArrayList<Document> docs) {

        LinkedList<TilesetChangeset> tilesetChangesetLinkedList = new LinkedList<TilesetChangeset>();

        for (Document doc : docs) {
            this.updateGlobalIdsInDoc(doc, tilesetChangesetLinkedList);
            runningTotalGID += globalIdIncrement;
        }

        return tilesetChangesetLinkedList;

    }

    private void updateGlobalIdsInDoc(Document doc, LinkedList<TilesetChangeset> tilesetChangesetLinkedList) {
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


        for (int i = 0; i < nodeListToUpdate.getLength(); i++) {

            if (nodeListToUpdate.item(i) == null) {
                continue;
            }
            String oldNodeValue = this.getFirstGidNodeValue(nodeListToUpdate.item(i));

            this.updateNode(nodeListToUpdate.item(i), runningTotalGID);

            String nextTileSetGID = null;
            if (nodeListToUpdate.item(i + 1) != null) {
                nextTileSetGID = this.getFirstGidNodeValue(nodeListToUpdate.item(i + 1));
            } else {
                nextTileSetGID = "4294967295";
            }

            // saving off this change
            TilesetChangeset tilesetChangeset = new TilesetChangeset(Integer.valueOf(oldNodeValue), Integer.valueOf(nextTileSetGID), runningTotalGID);
            tilesetChangesetLinkedList.add(tilesetChangeset);

            runningTotalGID += globalIdIncrement;
        }

    }

    private void updateNode(Node node, int gidValue) {

        Node firstgid = node.getAttributes().getNamedItem("firstgid");

        firstgid.setNodeValue(String.valueOf(gidValue));
        node.getAttributes().setNamedItem(firstgid);


    }

    private String getFirstGidNodeValue(Node node) {
        Node firstgid = node.getAttributes().getNamedItem("firstgid");
        return firstgid.getNodeValue();
    }


}
