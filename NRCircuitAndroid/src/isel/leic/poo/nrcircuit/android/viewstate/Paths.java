package isel.leic.poo.nrcircuit.android.viewstate;

import isel.leic.poo.nrcircuit.model.Circuit;
import isel.leic.poo.nrcircuit.model.Path;

import java.util.LinkedList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Paths implements Parcelable {

	public static final Parcelable.Creator<Paths> CREATOR = new Creator<Paths>() {
		
		@Override
		public Paths[] newArray(int size) {
			return new Paths[size];
		}
		
		@Override
		public Paths createFromParcel(Parcel source) {
			return new Paths(source);
		}
	};
	
	private List<Path> paths;
	
	private final PathSurrogate[] pathsSur;
	
	private Paths(Parcel source) {
		pathsSur = PathSurrogate.CREATOR.newArray(source.readInt());
		for (int i = 0; i < pathsSur.length; i++) {
			pathsSur[i] = PathSurrogate.CREATOR.createFromParcel(source);
		}
		paths = null;
	}
	
	public Paths(Circuit circuit){
		paths = new LinkedList<Path>();
		for (Path path : circuit.getGrid()) {
			paths.add(path);
		}
		pathsSur = null;
	}
	
	public List<Path> getPaths(Circuit circuit){
		if(paths == null){
			paths = new LinkedList<Path>();
			for (PathSurrogate pathSur : pathsSur) {
				paths.add(pathSur.getPath(circuit));
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
