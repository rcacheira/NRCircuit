package isel.leic.poo.nrcircuit.android.viewstate;

import isel.leic.poo.nrcircuit.model.Grid;
import isel.leic.poo.nrcircuit.model.Path;
import isel.leic.poo.nrcircuit.model.Place;
import isel.leic.poo.nrcircuit.model.Position;
import isel.leic.poo.nrcircuit.model.terminals.Terminal;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class whose instance is used to serialize Path on Android 
 */
public class PathSurrogate implements Parcelable{
	
	public static final Parcelable.Creator<PathSurrogate> CREATOR = new Parcelable.Creator<PathSurrogate>() {

		@Override
		public PathSurrogate[] newArray(int size) {
			return new PathSurrogate[size];
		}
		
		@Override
		public PathSurrogate createFromParcel(Parcel source) {
			return new PathSurrogate(source);
		}
		
	};
	
	private Path path;
	
	private final int size;
	private final char letter;
	private final Position[] positions;

	private void writePosition(Position position, Parcel dest){
		dest.writeInt(position.row);
		dest.writeInt(position.column);
	}
	
	private Position readPosition(Parcel source){
		return Position.get(source.readInt(), source.readInt());
	}
	
	public PathSurrogate(Parcel source) {
		//AS letter is ASCII char there is no problem with this conversion
		letter = (char) source.readByte();
		positions = new Position[size = source.readInt()];
		for (int i = 0; i < size; i++) {
			positions[i] = readPosition(source);
		}
		path = null;
	}
	
	public PathSurrogate(Path path){
		this.path = path;
		letter = 0;
		size = 0;
		positions = null;
	}
	
	public Path getPath(Grid grid){
		if(path == null){
			int i = 0;
			if(!(grid.getPlaceAtPosition(positions[i]) instanceof Terminal))
				throw new IllegalStateException("first path position should be a terminal");
			path = new Path((Terminal)grid.getPlaceAtPosition(positions[i]));
			for(i = 1; i<size; i++)
				path.add(grid.getPlaceAtPosition(positions[i]));
		}
		return path;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if(path != null){
			//AS letter is ASCII char there is no problem with this conversion
			dest.writeByte((byte)path.getLetter());
			dest.writeInt(path.size());
			for (Place place : path) {
				writePosition(place.position, dest);
			}
		}
		else{
			//AS letter is ASCII char there is no problem with this conversion
			dest.writeByte((byte)letter);
			dest.writeInt(size);
			for (int i = 0; i< size; i++) {
				writePosition(positions[i], dest);
			}
		}
	}

}
