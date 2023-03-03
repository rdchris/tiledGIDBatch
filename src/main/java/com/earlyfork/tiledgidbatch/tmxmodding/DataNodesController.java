package com.earlyfork.tiledgidbatch.tmxmodding;

import com.earlyfork.tiledgidbatch.pojos.TilesetChangeset;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

@Component
public class DataNodesController {


    public void updateDataNodes(ArrayList<Document> doc, Map<String, LinkedList<TilesetChangeset>> tilesetChangesetHashMap) {

        for (Document document : doc) {
            LinkedList<TilesetChangeset> tilesetChangesets = tilesetChangesetHashMap.get(document.getDocumentURI());
            //this.clearMaps();
            this.updateDataNodesPerDoc(document, tilesetChangesets);
        }


    }

    private void updateDataNodesPerDoc(Document document, LinkedList<TilesetChangeset> tilesetChangesets) {

        // get the list of xml data nodes
        NodeList nodeListToUpdate = this.getNodeListOfDataNodes(document);

        // parse through all Data nodes
        for (int i = 0; i < nodeListToUpdate.getLength(); i++) {

//            if (nodeListToUpdate.item(i).getChildNodes().item(0) == null) {
//                throw new RuntimeException(document.getDocumentURI() + " had a null node on " + i);
//            }

            String nodeValue = nodeListToUpdate.item(i).getChildNodes().item(0).getNodeValue();

            // determine how where the '/n' s need to go
            String[] splitDataValue = nodeValue.split(",");
            int rowLengthFoData = this.countRowLengthOfData(splitDataValue);

            // resplit, this time ripping out the \n as they will blow up integer parsing
            splitDataValue = nodeValue.replace("\n", "").split(",");
            Boolean[] booleanArray = new Boolean[splitDataValue.length];

            for (int ii = 0; ii < splitDataValue.length; ii++) {

                int intDataValueToReview = Integer.parseInt(splitDataValue[ii]);

                // we ignore zeros as they are nothing
                if (intDataValueToReview > 0) {
                    Iterator<TilesetChangeset> iterator = tilesetChangesets.iterator();
                    while (iterator.hasNext()) {
                        TilesetChangeset next = iterator.next();

                        int oldnextGid = next.getOldnextGid();
                        int newFirstGid = next.getNewFirstGidValue();

                        if ((intDataValueToReview >= newFirstGid) && (intDataValueToReview > oldnextGid)) {
                            if (booleanArray[ii] == null || booleanArray[ii] == false) {
                                // We found where a change needs to be made!
                                intDataValueToReview += next.getGidDelta();
                                // prevent this data element from being changed again
                                booleanArray[ii] = true;
                                continue;
                            }
                        }


                    }
                }

            }

            // puts the /n's back and appends the byte array back into a string
            String newNodeValue = this.convertStringArrayBackIntoNodeValue(splitDataValue, rowLengthFoData);

            // modify the node
            nodeListToUpdate.item(i).getChildNodes().item(0).setNodeValue(newNodeValue);

            // break into Arraylist
            // check to see if has been already updated
        }


    }

    private int countRowLengthOfData(String[] splitDataValue) {
        boolean foundFirstEntry = false;
        for (int i = 0; i < splitDataValue.length; i++) {
            if (splitDataValue[i].contains("\n")) {
                if (foundFirstEntry == false) {
                    foundFirstEntry = true;
                } else {
                    // We found the second /n! this is how long the rows are!
                    return i;
                }
            }
        }
        throw new RuntimeException("Could not find second \n in data node for " + splitDataValue.toString());
    }

    private String convertStringArrayBackIntoNodeValue(String[] splitDataValue, int rowLengthFoData) {
        // the very first time we need to add a new row
        boolean addNewRow = true;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < splitDataValue.length; i++) {

            if (addNewRow) {
                sb.append("\n");
                addNewRow = false;
            }

            sb.append(splitDataValue[i]).append(",");

            // If every 17th row we need a new line, then if the index is mod 17
            // aka 17, 34 then add a new line
            // NOTE mod 0 mod anything is always true
            // note the reason we are adding +1 is because we are counting 0
            if ((i + 1) % rowLengthFoData == 0) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private NodeList getNodeListOfDataNodes(Document document) {

        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeListToUpdate = null;
        try {
            nodeListToUpdate = (NodeList) xPath.compile("/map/layer/data").evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
        return nodeListToUpdate;
    }
}
