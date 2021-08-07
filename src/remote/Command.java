package remote;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public interface Command {

    void execute() throws ParserConfigurationException, SAXException, IOException, TransformerException;

}
