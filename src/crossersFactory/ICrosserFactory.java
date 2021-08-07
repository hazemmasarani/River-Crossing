package crossersFactory;

import model.Carni;
import model.Farmer;
import model.Herbi;
import model.ICrosser2;
import model.Plant;

public class ICrosserFactory {

	public ICrosserFactory() {
	}
	
	public ICrosser2 createICrosser(String type) {
	
		if( type == null ) return null;
		if( type.equals("farmer") ) return new Farmer();
		else if( type.equals("carni") ) return new Carni();
		else if( type.equals("herbi") ) return new Herbi();
		else if( type.equals("plant") ) return new Plant();
		
		return null;
	}
	
}
