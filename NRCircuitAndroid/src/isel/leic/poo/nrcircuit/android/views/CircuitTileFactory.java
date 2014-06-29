package isel.leic.poo.nrcircuit.android.views;

import android.graphics.RectF;
import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.common.TileFactory;
import isel.leic.poo.nrcircuit.android.views.tiles.TileConnector;
import isel.leic.poo.nrcircuit.android.views.tiles.TileFinalTerminal;
import isel.leic.poo.nrcircuit.android.views.tiles.TileOneWayConnector;
import isel.leic.poo.nrcircuit.android.views.tiles.TileProhibitedPlace;
import isel.leic.poo.nrcircuit.model.Circuit;
import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.ProhibitedPlace;
import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;

public class CircuitTileFactory implements TileFactory {

	Circuit model;
	
	public CircuitTileFactory(Circuit model) {
		this.model = model;
	}

	@Override
	public Tile createTile(int row, int column, CircuitView parent, RectF tileBounds) {
		Place place = model.getPlaceAtPosition(row, column);
		
		if(place instanceof ProhibitedPlace){
			return new TileProhibitedPlace(parent, tileBounds);
		}
		if(place instanceof FinalTerminal){
			return new TileFinalTerminal(parent, tileBounds, 
					((FinalTerminal)place).getLetter());
		}
		if(place instanceof OneWayConnector){
			return new TileOneWayConnector(parent, tileBounds, 
					((OneWayConnector)place).getOrientation());
		}
		if(place instanceof Connector){
			return new TileConnector(parent, tileBounds);
		}
		
		throw new IllegalStateException();
	}

}
