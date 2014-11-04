package com.jed.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jed.actor.PolygonBoundary;
import com.jed.state.GameMap;
import com.jed.state.MapTile;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class MapLoader {

    /**
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MapLoader.class);

    /**
     * TODO refactor location of constant in Java source code.
     */
    public static final String RESOURCES_DIRECTORY = "src/main/resources/";

    /**
     * 
     * @param path path to game map file
     * @return gameMap
     */
    public static GameMap loadMap(String path) {
        GameMap map = new GameMap();

        Document doc = null;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(path);
        } catch (Exception e) {
            LOGGER.error("Failed to load map file: " + path, e);
            System.exit(1);
        }

        Element docElement = doc.getDocumentElement();
        map.width = Integer.parseInt(docElement.getAttribute("width"));
        map.height = Integer.parseInt(docElement.getAttribute("height"));

        int tileImageWidth = 0;
        int tileImageHeight = 0;
        map.tileHeight = Integer.parseInt(docElement.getAttribute("tileheight"));
        map.tileWidth = Integer.parseInt(docElement.getAttribute("tilewidth"));

        map.tiles = new MapTile[map.width * map.height];

        //Load Map properties
        NodeList mapNodes = docElement.getChildNodes();
        String nameNodeTextContent = null;//TODO once loop is refactored get rid of nameNodeTextContent
        //FIXME this loop needs refactoring.
        for (int i = 0; i < mapNodes.getLength(); i++) {
            Node eachMapNode = mapNodes.item(i);
            if (eachMapNode.getNodeType() == Node.ELEMENT_NODE &&
                    eachMapNode.getNodeName().equals("properties")) {
                NodeList propertyNodes = eachMapNode.getChildNodes();
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node eachPropertyNode = propertyNodes.item(j);
                    if (eachPropertyNode.getNodeType() == Node.ELEMENT_NODE &&
                            eachPropertyNode.getNodeName().equals("property")) {
                        Node nameNode = eachPropertyNode.getAttributes().getNamedItem("name");
                        nameNodeTextContent  = nameNode.getTextContent();
                        if (nameNode != null && nameNodeTextContent != null && nameNodeTextContent.equals("gravity")) {
                            Node valueNode = eachPropertyNode.getAttributes().getNamedItem("value");
                            if (valueNode != null && valueNode.getTextContent() != null) {
                                map.gravity = Float.valueOf(valueNode.getTextContent());
                            }
                        }
                    }
                }
            }
        }


        //TODO: Must ultimately be updated to handle multiple layers and tilesets...

        //Get the tileset file location
        NodeList imageNodes = docElement.getElementsByTagName("image");
        for (int i = 0; i < imageNodes.getLength(); i++) {
            Node eachImageNode = imageNodes.item(i);
            if (eachImageNode.getNodeType() == Node.ELEMENT_NODE) {
                //TODO Make asset location relative
                map.setTileSetPath(
                        RESOURCES_DIRECTORY + eachImageNode.getAttributes()
                                .getNamedItem("source").getTextContent());

                tileImageWidth =
                        Integer.parseInt(eachImageNode.getAttributes().getNamedItem("width").getTextContent());

                tileImageHeight =
                        Integer.parseInt(eachImageNode.getAttributes().getNamedItem("height").getTextContent());

            }
        }


        //Get the tile layout data
        NodeList dataNodes = docElement.getElementsByTagName("data");
        int rowIndex = 0, columnIndex = 0, tileCount = 0;
        int textureWidth = Util.getClosestPowerOfTwo(tileImageWidth);
        int textureHeight = Util.getClosestPowerOfTwo(tileImageHeight);
        //TODO START pc 2014-10-31 test me
        float textureWidthOverMapTileWidth = (float) textureWidth / map.tileWidth;
        float glWidth = 1 / textureWidthOverMapTileWidth;
        float textureHeightOverMapTileHeight = (float) textureHeight / map.tileHeight;
        float glHeight = 1 / textureHeightOverMapTileHeight;
        //TODO END pc 2014-10-31 test me
        int tilesAcross = tileImageWidth / map.tileWidth;

        for (int i = 0; i < dataNodes.getLength(); i++) {
            Node eachDataNode = dataNodes.item(i);
            if (eachDataNode.getNodeType() == Node.ELEMENT_NODE) {
                for (int j = 0; j < eachDataNode.getChildNodes().getLength(); j++) {
                    Node eachTileNode = eachDataNode.getChildNodes().item(j);
                    if (eachTileNode.getNodeType() == Node.ELEMENT_NODE) {

                        int tileId = Integer.parseInt(
                                eachTileNode.getAttributes().getNamedItem("gid").getTextContent());

                        float row = (tileId - 1) / (float) tilesAcross;
                        int tileRow = (int) Math.floor(row);
                        int tileColumn = (int) ((row % 1) * tilesAcross);

                        float glX = glWidth * tileColumn;
                        float glY = glHeight * tileRow;

                        map.tiles[tileCount++] = new MapTile(
                                new Vector(columnIndex * map.tileHeight, rowIndex * map.tileWidth),

                                new PolygonBoundary(
                                        new Vector(0, 0),
                                        new Vector[]{
                                                new Vector(0, 0),
                                                new Vector(map.tileWidth, 0),
                                                new Vector(map.tileWidth, map.tileHeight),
                                                new Vector(0, map.tileHeight)
                                        })
                                ,
                                glX, glY, glWidth, glHeight,
                                tileId,
                                map
                        );

                        if (columnIndex == map.width - 1) {
                            columnIndex = 0;
                            rowIndex++;
                        } else {
                            columnIndex++;
                        }

                    }
                }
            }
        }

        return map;
    }
}
