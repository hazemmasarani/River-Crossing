package commands;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.XmlParser;
import remote.Command;

public class LoadGameListCommand implements Command {

	XmlParser xmlParser;

    public LoadGameListCommand(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    @Override
    public void execute() throws ParserConfigurationException, SAXException, IOException {
    	xmlParser.loadGamesList();
    }

}
