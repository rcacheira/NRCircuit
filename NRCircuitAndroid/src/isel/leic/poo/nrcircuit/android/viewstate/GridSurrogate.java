package isel.leic.poo.nrcircuit.android.viewstate;

import isel.leic.poo.nrcircuit.model.Grid;
import isel.leic.poo.nrcircuit.model.Link;

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
	
	private List<Link> links;
	private LinkSurrogate[] linksSur;
	
	/**
	 * Initiates an instance with the information extracted from the given {@link Parcel}
	 * instance.
	 * 
	 * @param source the parcel containing the grid information 
	 */
	private GridSurrogate(Parcel source) {
		linksSur = LinkSurrogate.CREATOR.newArray(source.readInt());
		for (int i = 0; i < linksSur.length; i++) {
			linksSur[i] = LinkSurrogate.CREATOR.createFromParcel(source);
		}
		links = null;
	}
	
	/**
	 * Initiates an instance with the given arguments. 
	 * 
	 * @param grid the associated {@link Grid} instance
	 */
	public GridSurrogate(Grid grid){
		links = new LinkedList<Link>();
		for (Link link : grid.getLinks()) {
			links.add(link);
		}
		linksSur = null;
	}
	
	public List<Link> getLinks(){
		if(links == null){
			links = new LinkedList<Link>();
			for (LinkSurrogate linkSur : linksSur) {
				links.add(linkSur.getLink());
			}
		}
		return links;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		System.out.println("GridSurrogate.writeToParcel");
		if(links != null){
			System.out.println("saving links");
			dest.writeInt(links.size());
			for (Link link : links) {
				System.out.println("Saving: " + link);
				new LinkSurrogate(link).writeToParcel(dest, flags);
			}
		}
		else{
			dest.writeInt(linksSur.length);
			for (LinkSurrogate linkSurrogate : linksSur) {
				linkSurrogate.writeToParcel(dest, flags);
			}
		}
	}

}
