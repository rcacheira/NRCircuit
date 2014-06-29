package isel.leic.poo.nrcircuit.android.common;

import isel.leic.poo.nrcircuit.android.views.CircuitView;
import android.graphics.RectF;

public interface TileFactory {

	public Tile createTile(int row, int column, CircuitView parent, RectF tileBounds);

}
