package remote;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class RemoteControl {

    private Command command;

    public void addCommand(Command command ) {
    	this.command = command;
    }

    public void pushButton() throws ParserConfigurationException, SAXException, IOException, TransformerException {
    	command.execute();
    }

}
