package isel.leic.poo.nrcircuit.android.views;

import android.graphics.RectF;
import isel.leic.poo.nrcircuit.android.common.Tile;
import isel.leic.poo.nrcircuit.android.common.TileFactory;
import isel.leic.poo.nrcircuit.android.views.tiles.TileConnector;
import isel.leic.poo.nrcircuit.android.views.tiles.TileFinalTerminal;
import isel.leic.poo.nrcircuit.android.views.tiles.TileFork;
import isel.leic.poo.nrcircuit.android.views.tiles.TileNumberedConnector;
import isel.leic.poo.nrcircuit.android.views.tiles.TileOneWayConnector;
import isel.leic.poo.nrcircuit.android.views.tiles.TileProhibitedPlace;
import isel.leic.poo.nrcircuit.android.views.tiles.TileTunnel;
import isel.leic.poo.nrcircuit.model.Circuit;
import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.ProhibitedPlace;
import isel.leic.poo.nrcircuit.model.connectors.Connector;
import isel.leic.poo.nrcircuit.model.connectors.NumberedConnector;
import isel.leic.poo.nrcircuit.model.connectors.OneWayConnector;
import isel.leic.poo.nrcircuit.model.terminals.FinalTerminal;
import isel.leic.poo.nrcircuit.model.terminals.Fork;
import isel.leic.poo.nrcircuit.model.terminals.Tunnel;

/**
 * Class that implements the factory of tiles for a tile. 
 */
public class CircuitTileFactory implements TileFactory {

	/**
	 * The model instance.
	 */
	Circuit model;
	
	/**
	 * Sets the puzzle instance to be used when instantiating tiles
	 * 
	 * @param model
	 */
	public CircuitTileFactory(Circuit model) {
		this.model = model;
	}

	@Override
	public Tile createTile(int row, int column, CircuitView parent, RectF tileBounds) {
		Place place = model.getPlaceAtPosition(Position.get(row, column));
		
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
		if(place instanceof NumberedConnector){
			return new TileNumberedConnector(parent, tileBounds, ((NumberedConnector)place).orderNumber);
		}
		if(place instanceof Connector){
			return new TileConnector(parent, tileBounds);
		}
		if(place instanceof Fork){
			return new TileFork(parent, tileBounds, ((Fork)place).getOrientation());
		}
		if(place instanceof Tunnel){
			return new TileTunnel(parent, tileBounds);
		}
		throw new IllegalStateException("no tile defined for place type");
	}

}
