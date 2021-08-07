package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import model.Carni;
import model.Farmer;
import model.Herbi;
import model.ICrosser2;
import model.Plant;
import crossersFactory.ICrosserFactory;

public class XmlParser {
	
	public XmlParser() {
	}

	public void saveGame() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		Stories_IRiverCrossingController mainCrossingController = Stories_IRiverCrossingController.getMainCrossingController();
		ICrosser2 tempCrosser;
		ICrosserFactory crossersFactory = new ICrosserFactory();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		 
		//Build Document
		Document document = builder.parse(new File("SavedProfiles.xml"));
		document.getDocumentElement().normalize();

		//Here comes the root node
		Element storiesRoot = document.getDocumentElement();

		Element storyTypeE;
		if( mainCrossingController.state.whichStory == 1 ) {
			storyTypeE = (Element)document.getElementsByTagName("story1").item(0);
		}else{
			storyTypeE = (Element)document.getElementsByTagName("story2").item(0);
		}

		int storyProfilesCount = Integer.parseInt( storyTypeE.getAttribute("profilesCount") );
		storyTypeE.setAttribute("profilesCount", String.valueOf(storyProfilesCount+1) );

		Element profileE = document.createElement("profile");
		Attr id = document.createAttribute("id");
		id.setValue( String.valueOf(storyProfilesCount+1) );
		profileE.setAttributeNode(id);
		Attr sails = document.createAttribute("sails");
		sails.setValue( String.valueOf(mainCrossingController.state.currentSailCount+mainCrossingController.state.loadedPastSailsCount) );
		profileE.setAttributeNode(sails);
		Attr boatPosition = document.createAttribute("boatPosition");
		boatPosition.setValue( mainCrossingController.state.boatPosition );
		profileE.setAttributeNode(boatPosition);
		Attr creationTime = document.createAttribute("creationTime");
		creationTime.setValue( new SimpleDateFormat("HH:mm, dd/MM/yyyy").format(Calendar.getInstance().getTime()) );
		profileE.setAttributeNode(creationTime);
		storyTypeE.appendChild(profileE);

		Element leftBankE = document.createElement("leftBank");
		Attr LcrossersCount = document.createAttribute("crossersCount");
		LcrossersCount.setValue( String.valueOf(mainCrossingController.state.currentLeftBankCrossers.size()) );
		leftBankE.setAttributeNode(LcrossersCount);
		profileE.appendChild(leftBankE);

		for( int i=0; i<mainCrossingController.state.currentLeftBankCrossers.size() ;i++ ) {
			tempCrosser = crossersFactory.createICrosser( mainCrossingController.state.currentLeftBankCrossers.get(i).getCrosserType() );
			tempCrosser = mainCrossingController.state.currentLeftBankCrossers.get(i);

			Element crosserE = document.createElement("crosser");
			Attr type = document.createAttribute("type");
			type.setValue( tempCrosser.getCrosserType() );
			crosserE.setAttributeNode(type);
			Attr weight = document.createAttribute("weight");
			weight.setValue( String.valueOf(tempCrosser.getWeight()) );
			crosserE.setAttributeNode(weight);
			leftBankE.appendChild(crosserE);
		}

		Element rightBankE = document.createElement("rightBank");
		Attr RcrossersCount = document.createAttribute("crossersCount");
		RcrossersCount.setValue( String.valueOf(mainCrossingController.state.currentRightBankCrossers.size()) );
		rightBankE.setAttributeNode(RcrossersCount);
		profileE.appendChild(rightBankE);

		for( int i=0; i<mainCrossingController.state.currentRightBankCrossers.size() ;i++ ) {
			tempCrosser = crossersFactory.createICrosser( mainCrossingController.state.currentRightBankCrossers.get(i).getCrosserType() );
			tempCrosser = mainCrossingController.state.currentRightBankCrossers.get(i);

			Element crosserE = document.createElement("crosser");
			Attr type = document.createAttribute("type");
			type.setValue( tempCrosser.getCrosserType() );
			crosserE.setAttributeNode(type);
			Attr weight = document.createAttribute("weight");
			weight.setValue( String.valueOf(tempCrosser.getWeight()) );
			crosserE.setAttributeNode(weight);
			rightBankE.appendChild(crosserE);
		}

		// create the xml file
		//transform the DOM Object to an XML File
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(new File("SavedProfiles.xml"));
			//StreamResult streamResult = new StreamResult(System.out);

			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadGame() throws ParserConfigurationException, SAXException, IOException {
		Stories_IRiverCrossingController mainCrossingController = Stories_IRiverCrossingController.getMainCrossingController();
		for( int i=mainCrossingController.state.currentLeftBankCrossers.size(); i>0 ;i-- ) {
			mainCrossingController.state.currentLeftBankCrossers.remove(i-1);
		}
		
		ICrosser2 tempCrosser;
		ICrosserFactory crossersFactory = new ICrosserFactory();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		 
		//Build Document
		Document document = builder.parse(new File("SavedProfiles.xml"));
		document.getDocumentElement().normalize();

		//Here comes the root node
		Element storiesRoot = document.getDocumentElement();

		Element storyTypeE;
		if( mainCrossingController.state.whichStory == 1 ) {
			storyTypeE = (Element)document.getElementsByTagName("story1").item(0);
		}else{
			storyTypeE = (Element)document.getElementsByTagName("story2").item(0);
		}

		int storyProfilesCount = Integer.parseInt( storyTypeE.getAttribute("profilesCount") );
		Element profileE = document.createElement("profile");
		Element crosserE = document.createElement("crosser");

		for( int i=0; i<storyProfilesCount ;i++ ) {
	      	if(i==0) profileE = getFirstChild( storyTypeE, "profile");
	      	else profileE = getNextSibiling( profileE, "profile");

	      	if( profileE.getAttribute("id").equals( String.valueOf(mainCrossingController.loadProfileId) ) ) break;
		}
		mainCrossingController.state.loadedPastSailsCount = Integer.parseInt( profileE.getAttribute("sails") );
		mainCrossingController.state.boatPosition = profileE.getAttribute("boatPosition");

		Element leftBankE = getFirstChild( profileE, "leftBank");

      	for( int i=0; i<Integer.parseInt( leftBankE.getAttribute("crossersCount") ) ;i++ ) {
	      	if(i==0) crosserE = getFirstChild( leftBankE, "crosser");
	      	else crosserE = getNextSibiling( crosserE, "crosser");

      		String type = crosserE.getAttribute("type");
			if ( type.equals("farmer") ) tempCrosser = crossersFactory.createICrosser("farmer");
			else if ( type.equals("carni") ) tempCrosser = crossersFactory.createICrosser("carni");
			else if ( type.equals("herbi") ) tempCrosser = crossersFactory.createICrosser("herbi");
			else tempCrosser = crossersFactory.createICrosser("plant");

			tempCrosser.setWeight( Double.parseDouble( crosserE.getAttribute("weight") ) );
	    	mainCrossingController.state.currentLeftBankCrossers.add( tempCrosser );
		}

		Element rightBankE = getFirstChild( profileE, "rightBank");

      	for( int i=0; i<Integer.parseInt( rightBankE.getAttribute("crossersCount") ) ;i++ ) {
			if(i==0) crosserE = getFirstChild( rightBankE, "crosser");
	      	else crosserE = getNextSibiling( crosserE, "crosser");

      		String type = crosserE.getAttribute("type");
			tempCrosser = crossersFactory.createICrosser( type );

			tempCrosser.setWeight( Double.parseDouble( crosserE.getAttribute("weight") ) );
	    	mainCrossingController.state.currentRightBankCrossers.add( tempCrosser );
		}
	}
	
	public void loadGamesList() throws ParserConfigurationException, SAXException, IOException {
		Stories_IRiverCrossingController mainCrossingController = Stories_IRiverCrossingController.getMainCrossingController();
		ArrayList<String> loadedProfiles = new ArrayList<String>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		 
		//Build Document
		Document document = builder.parse(new File("SavedProfiles.xml"));
		document.getDocumentElement().normalize();

		//Here comes the root node
		Element storiesRoot = document.getDocumentElement();

		Element storyTypeE;
		if( mainCrossingController.state.whichStory == 1 ) {
			storyTypeE = (Element)document.getElementsByTagName("story1").item(0);
		}else{
			storyTypeE = (Element)document.getElementsByTagName("story2").item(0);
		}

		int storyProfilesCount = Integer.parseInt( storyTypeE.getAttribute("profilesCount") );
		Element profileE = document.createElement("profile");

		if( storyProfilesCount==0 ) loadedProfiles.add("No saved games found.");

      	for( int i=0; i<storyProfilesCount ;i++ ) {
	      	if(i==0) profileE = getFirstChild( storyTypeE, "profile");
	      	else profileE = getNextSibiling( profileE, "profile");

	      	String profileInfo;
	      	profileInfo = profileE.getAttribute("id") + ". Type: ";
			if( mainCrossingController.state.whichStory == 1 ) profileInfo = profileInfo + "story1";
			else if( mainCrossingController.state.whichStory == 2 ) profileInfo = profileInfo + "story2";

			profileInfo = profileInfo +  ", Number of sails: " + profileE.getAttribute("sails");
			profileInfo = profileInfo +  ", Date created: " + profileE.getAttribute("creationTime") + ".";
			loadedProfiles.add( profileInfo );
      }

      	mainCrossingController.loadedGamesList = loadedProfiles;
	}
	
	private Element getFirstChild(Element parent, String name) {
	    for(Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
	        if(child instanceof Element && name.equals(child.getNodeName())) return (Element) child;
	    }
	    return null;
	}
	
	private Element getNextSibiling(Element firstChild, String name) {
	    for(Node child = firstChild.getNextSibling(); child != null; child = child.getNextSibling()) {
	        if(child instanceof Element && name.equals(child.getNodeName())) return (Element) child;
	    }
	    return null;
	}
}
