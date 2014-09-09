package isel.leic.poo.nrcircuit.android.viewstate;

import isel.leic.poo.nrcircuit.model.Link;
import isel.leic.poo.nrcircuit.model.Position;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class whose instance is used to serialize Link on Android 
 */
public class LinkSurrogate implements Parcelable{
	
	public static final Parcelable.Creator<LinkSurrogate> CREATOR = new Parcelable.Creator<LinkSurrogate>() {

		@Override
		public LinkSurrogate[] newArray(int size) {
			return new LinkSurrogate[size];
		}
		
		@Override
		public LinkSurrogate createFromParcel(Parcel source) {
			return new LinkSurrogate(source);
		}
		
	};
	
	private final Position origin;
	private final Position destiny;
	
	public LinkSurrogate(Parcel source) {
		origin = readPosition(source);
		destiny = readPosition(source);
	}
	
	public LinkSurrogate(Link link){
		this.origin = link.origin;
		this.destiny = link.destiny;
	}

	private void writePosition(Position position, Parcel dest){
		dest.writeInt(position.row);
		dest.writeInt(position.column);
	}
	
	private Position readPosition(Parcel source){
		return Position.get(source.readInt(), source.readInt());
	}
	
	public Link getLink(){
		return new Link(origin, destiny);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		writePosition(origin, dest);
		writePosition(destiny, dest);
	}

}
