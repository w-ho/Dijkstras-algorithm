package webBasedRouting;

import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class Parser {

    private String elementName = "node";
    private String attrName = "name";
    private String link = "link";
    private String start = "start";

    NodeList listOfNode;
    NodeList listOfLinks;

    NodeHandler nodeHandler = new NodeHandler();

    public Parser() {
        Document xmlDoc = getDocument("./src/main/resources/static/graph.xml");
        NodeList listOfNode = xmlDoc.getElementsByTagName("node");
        NodeList listOfLinks = xmlDoc.getElementsByTagName("link");
        getElementAndAttribute(listOfNode, elementName, attrName);
        getElementAndAttribute(listOfLinks, link, start);
    }

    private Document getDocument(String docString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(docString));
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private void getElementAndAttribute(NodeList listOfNode, String elementName, String attrName){
        for (int i = 0; i < listOfNode.getLength(); i++) {
            Node showNode = listOfNode.item(i);
            Element showElement = (Element) showNode;
            linkOrNodeCreation(elementName, showElement);
        }
    }

    private void makeNodesIntoList(Element showElement){
        String name = showElement.getAttribute("name");
        int x = Integer.parseInt(showElement.getElementsByTagName("x").item(0).getTextContent());
        int y = Integer.parseInt(showElement.getElementsByTagName("y").item(0).getTextContent());
        nodeHandler.nodeCreator(name , x, y);
    }

    private void getLinkForNodes(Element showElement){
        String start = showElement.getAttribute("start");
        String end = showElement.getAttribute("end");
        int weight = Integer.parseInt(showElement.getElementsByTagName("weight").item(0).getTextContent());
        nodeHandler.createlink(start, end, weight);
        nodeHandler.createlink(end, start, weight);
    }


    private void linkOrNodeCreation(String elementName, Element showElement){
        if (elementName.equals("node")){
            makeNodesIntoList(showElement);
        } else if ( elementName.equals("link")){
            getLinkForNodes(showElement);
        }

    }
}