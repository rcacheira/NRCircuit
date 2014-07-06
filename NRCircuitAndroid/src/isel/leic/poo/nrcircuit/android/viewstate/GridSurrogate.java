package isel.leic.poo.nrcircuit.android.viewstate;

import isel.leic.poo.nrcircuit.model.Grid;
import isel.leic.poo.nrcircuit.model.Path;

import java.util.LinkedList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class whose instance is used to serialize Grid on Android
 * 
 * Because level is now loaded on file, is only needed to parcel Paths
 */
public class GridSurrogate implements Parcelable {

	public static final Parcelable.Creator<GridSurrogate> CREATOR = new Creator<GridSurrogate>() {
		
		@Override
		public GridSurrogate[] newArray(int size) {
			return new GridSurrogate[size];
		}
		
		@Override
		public GridSurrogate createFromParcel(Parcel source) {
			return new GridSurrogate(source);
		}
	};
	
	private List<Path> paths;
	
	private final PathSurrogate[] pathsSur;
	
	/**
	 * Initiates an instance with the information extracted from the given {@link Parcel}
	 * instance.
	 * 
	 * @param source the parcel containing the grid information 
	 */
	private GridSurrogate(Parcel source) {
		pathsSur = PathSurrogate.CREATOR.newArray(source.readInt());
		for (int i = 0; i < pathsSur.length; i++) {
			pathsSur[i] = PathSurrogate.CREATOR.createFromParcel(source);
		}
		paths = null;
	}
	
	/**
	 * Initiates an instance with the given arguments. 
	 * 
	 * @param grid the associated {@link Grid} instance
	 */
	public GridSurrogate(Grid grid){
		paths = new LinkedList<Path>();
		for (Path path : grid) {
			paths.add(path);
		}
		pathsSur = null;
	}
	
	public List<Path> getPaths(Grid grid){
		if(paths == null){
			paths = new LinkedList<Path>();
			for (PathSurrogate pathSur : pathsSur) {
				paths.add(pathSur.getPath(grid));
			}
		}
		return paths;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if(paths != null){
			dest.writeInt(paths.size());
			for (Path path : paths) {
				new PathSurrogate(path).writeToParcel(dest, flags);
			}
		}
		else{
			dest.writeInt(pathsSur.length);
			for (PathSurrogate pathSurrogate : pathsSur) {
				pathSurrogate.writeToParcel(dest, flags);
			}
		}
	}

}
