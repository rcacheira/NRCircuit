package isel.leic.poo.nrcircuit.android.common;

public class TileActionEvent {

	public enum TileEvent{
		TILE_TOUCH,
		LINKED_TILE_TOUCH,
		TILE_LINK
	}
	
	public final int row;
	public final int column;
	public final TileEvent event;
	
	public TileActionEvent(TileEvent event, int row, int column) {
		this.event = event;
		this.row = row;
		this.column = column;
	}

}
