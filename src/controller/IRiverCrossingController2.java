package controller;

import main.*;
import model.*;

import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public interface IRiverCrossingController2 extends IRiverCrossingController {

	public void loadGamesList() throws ParserConfigurationException, SAXException, IOException, TransformerException;

}
