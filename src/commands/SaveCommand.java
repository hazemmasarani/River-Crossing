package commands;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import controller.XmlParser;
import remote.Command;

public class SaveCommand implements Command {

	XmlParser xmlParser;

    public SaveCommand(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    @Override
    public void execute() throws ParserConfigurationException, SAXException, IOException, TransformerException {
    	xmlParser.saveGame();
    }

}
