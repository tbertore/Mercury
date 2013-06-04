package org.mercury;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mercury.gfx.Sprite;
import org.mercury.gfx.SpriteSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * A class which allows loading of game resources from an XML file. Each
 * resource tag should meet the following attribute requirements.
 * </p>
 * <ul>
 * <li>SpriteSheet
 * <ul>
 * <li>id - The string used to reference this SpriteSheet by other resources.
 * <li>size - The width/height of the SpriteSheet. SpriteSheets must have the
 * same width and height.
 * <li>path - The path to the SpriteSheet's image file.
 * </ul>
 * <li>Sprite
 * <ul>
 * <li>id - The string used to reference this Sprite by other resources.
 * <li>size - The width/height of the Sprite. Sprites must have the same width
 * and height.
 * <li>sheet - The SpriteSheet to load this Sprite from.
 * <li>x - The Sprite's x index in the SpriteSheet.
 * <li>y - The Sprite's y index in the SpriteSheet.
 * </ul>
 * </ul>
 * 
 * @author tbertorelli
 * 
 */
public class ResourceManager {
	private HashMap<String, SpriteSheet> sheets;
	private HashMap<String, Sprite> sprites;

	/**
	 * Constructs a new ResourceManager with empty resource mappings.
	 * 
	 */
	public ResourceManager() {
		sheets = new HashMap<String, SpriteSheet>();
		sprites = new HashMap<String, Sprite>();
	}

	/**
	 * Loads all resources specified in the XML file with the specified path.
	 * 
	 * @param xmlPath
	 *            The path of the XML file.
	 */
	public void load(String xmlPath) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			InputStream in = ResourceManager.class.getResourceAsStream(xmlPath);
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			Document document = docBuilder.parse(in);

			document.getDocumentElement().normalize();
			for (Element e : getElementsByTag(document, "spritesheet"))
				loadSpriteSheet(e);
			for (Element e : getElementsByTag(document, "sprite"))
				loadSprite(e);

			unloadAllSpriteSheets();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Utility method which returns an array of all XML element nodes (A
	 * complete tag) with the specified tag name.
	 * 
	 * @param doc
	 *            The document to search in.
	 * @param tag
	 *            The tag name to match.
	 * @return An ArrayList of all elements found.
	 */
	private ArrayList<Element> getElementsByTag(Document doc, String tag) {
		ArrayList<Element> elements = new ArrayList<Element>();
		NodeList list = doc.getElementsByTagName(tag);
		int totalResources = list.getLength();
		for (int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++) {
			Node resourceNode = list.item(resourceIdx);
			if (resourceNode.getNodeType() == Node.ELEMENT_NODE) {
				Element resourceElement = (Element) resourceNode;
				elements.add(resourceElement);
			}
		}
		return elements;
	}

	/**
	 * Removes the SpriteSheet mapping (and consequently, all the SpriteSheet
	 * objects). SpriteSheet objects are no longer required after all loading is
	 * done, as all images have been extracted from it.
	 * 
	 */
	private void unloadAllSpriteSheets() {
		sheets = null;
	}

	/**
	 * Builds a SpriteSheet from the specified xml resource element and loads it
	 * into the SpriteSheet map.
	 * 
	 * @param resource
	 *            The XML tag to load from.
	 */
	private void loadSpriteSheet(Element resource) {
		String path = resource.getAttribute("path");
		String id = resource.getAttribute("id");
		int size = Integer.valueOf(resource.getAttribute("size"));
		SpriteSheet sheet = new SpriteSheet(path, size);

		sheets.put(id, sheet);
	}

	/**
	 * Builds a Sprite from the specified xml resource. Note that this method
	 * requires the Sprite's SpriteSheet to have been loaded beforehand.
	 * 
	 * @param resource
	 *            The XML tag to load from.
	 */
	private void loadSprite(Element resource) {
		String sheet = resource.getAttribute("sheet");
		String id = resource.getAttribute("id");
		int size = Integer.valueOf(resource.getAttribute("size"));
		int x = Integer.valueOf(resource.getAttribute("x"));
		int y = Integer.valueOf(resource.getAttribute("y"));
		Sprite sprite = new Sprite(size, size);
		sprite.load(sheets.get(sheet), x, y);
		sprites.put(id, sprite);

	}

}
