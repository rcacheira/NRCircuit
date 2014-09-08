package isel.leic.poo.nrcircuit.model;

public class Link {

	public final Position origin;
	public final Position destiny;
	
	public Link(Position origin, Position destiny) {
		this.origin = origin;
		this.destiny = destiny;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof Link))
			return false;
		Link p = (Link)obj;
		
		return p.origin.equals(origin) && p.destiny.equals(destiny);
	}
	
	@Override
	public String toString() {
		return "Link:{ origin:" + origin + " destiny:" + destiny + "}";
	}
}
