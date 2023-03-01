package com.earlyfork.tiledgidbatch.globalid;

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
public class NodesController {


    public void updateDataNodes(ArrayList<Document> doc, Map<String, LinkedList<TilesetChangeset>> tilesetChangesetHashMap) {

        for (Document document : doc) {
            LinkedList<TilesetChangeset> tilesetChangesets = tilesetChangesetHashMap.get(document.getDocumentURI());
            //this.clearMaps();
            this.updateDataNodesPerDoc(document, tilesetChangesets);
        }


    }

    private void updateDataNodesPerDoc(Document document, LinkedList<TilesetChangeset> tilesetChangesets) {
        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodeListToUpdate = null;
        try {
            nodeListToUpdate = (NodeList) xPath.compile("/map/layer/data").evaluate(document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < nodeListToUpdate.getLength(); i++) {
            String nodeValue = nodeListToUpdate.item(i).getChildNodes().item(i).getNodeValue();
            String[] splitDataValue = nodeValue.split(",");
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

            StringBuilder sb = new StringBuilder();
            for (String s : splitDataValue) {
                sb.append(s).append(",");
            }

            // modify the node
            nodeListToUpdate.item(i).getChildNodes().item(i).setNodeValue(sb.toString());

            // break into Arraylist
            // check to see if has been already updated
        }


    }
}
