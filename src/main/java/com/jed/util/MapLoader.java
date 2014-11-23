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

import java.io.InputStream;
import java.util.ArrayList;

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
     *
     */
    private static final String LEVEL_ONE_PATH = "/POC_MAP.tmx";

    /**
     * 
\     * @return gameMap
     */
    public static GameMap loadMap() {
        final GameMap map = new GameMap();

        Document doc = null;
        try (final InputStream resourceAsStream = MapLoader.class.getResourceAsStream(LEVEL_ONE_PATH)) {
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(resourceAsStream);
        } catch (Exception e) {
            LOGGER.error("{}", e);
            System.exit(StatusCode.ERROR.getStatusCode());
        }

        final Element docElement = doc.getDocumentElement();
        map.setWidth(Integer.parseInt(docElement.getAttribute("width")));
        map.setHeight(Integer.parseInt(docElement.getAttribute("height")));

        int tileImageWidth = 0;
        int tileImageHeight = 0;
        map.setTileHeight(Integer.parseInt(docElement.getAttribute("tileheight")));
        map.setTileWidth(Integer.parseInt(docElement.getAttribute("tilewidth")));

        map.setTiles(new ArrayList<>());//FIXME

        //Load Map properties
        final NodeList mapNodes = docElement.getChildNodes();
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
                        nameNodeTextContent  = nameNode.getTextContent();//TODO see if nameNode != null is necessary.
                        if (nameNodeTextContent != null && nameNodeTextContent.equals("gravity")) {
                            Node valueNode = eachPropertyNode.getAttributes().getNamedItem("value");
                            if (valueNode != null && valueNode.getTextContent() != null) {
                                map.setGravity(Float.valueOf(valueNode.getTextContent()));
                            }
                        }
                    }
                }
            }
        }


        //TODO: Must ultimately be updated to handle multiple layers and tilesets...

        //Get the tileset file location
        final NodeList imageNodes = docElement.getElementsByTagName("image");
        for (int i = 0; i < imageNodes.getLength(); i++) {
            Node eachImageNode = imageNodes.item(i);
            if (eachImageNode.getNodeType() == Node.ELEMENT_NODE) {
                //TODO Make asset location relative
                map.setTileSetPath(
                        eachImageNode.getAttributes()
                                .getNamedItem("source").getTextContent());

                tileImageWidth =
                        Integer.parseInt(eachImageNode.getAttributes().getNamedItem("width").getTextContent());

                tileImageHeight =
                        Integer.parseInt(eachImageNode.getAttributes().getNamedItem("height").getTextContent());

            }
        }


        //Get the tile layout data
        final NodeList dataNodes = docElement.getElementsByTagName("data");
        int rowIndex = 0, columnIndex = 0, tileCount = 0;
        final int textureWidth = Util.getClosestPowerOfTwo(tileImageWidth);
        final int textureHeight = Util.getClosestPowerOfTwo(tileImageHeight);
        //TODO START pc 2014-10-31 test me
        final float textureWidthOverMapTileWidth = (float) textureWidth / map.getTileWidth();
        final float glWidth = 1 / textureWidthOverMapTileWidth;
        final float textureHeightOverMapTileHeight = (float) textureHeight / map.getTileHeight();
        final float glHeight = 1 / textureHeightOverMapTileHeight;
        //TODO END pc 2014-10-31 test me
        final int tilesAcross = tileImageWidth / map.getTileWidth();

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

                        map.getTiles().add(new MapTile(
                                new Vector(columnIndex * map.getTileHeight(), rowIndex * map.getTileWidth()),

                                new PolygonBoundary(
                                        new Vector(0, 0),
                                        new Vector[]{
                                                new Vector(0, 0),
                                                new Vector(map.getTileWidth(), 0),
                                                new Vector(map.getTileWidth(), map.getTileHeight()),
                                                new Vector(0, map.getTileHeight())
                                        })
                                ,
                                glX, glY, glWidth, glHeight,
                                tileId,
                                map
                        ));

                        if (columnIndex == map.getWidth() - 1) {
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
