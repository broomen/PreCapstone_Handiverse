package model_tag;

public class Tag  implements Comparable<Tag>{
	
	private int id;
	private int locationID;
	private String desc;
	private int karma;
	
	private static int idCounter = 0;

	
	public Tag(String tagDesc) {
		this.id = idCounter++;
		this.locationID = 0;
		this.desc = tagDesc;
		this.karma = 0;
	}
	
	public Tag(int id, int locationID, String tagDesc, int karma) {
		this.id = id;
		this.locationID = locationID;
		this.desc = tagDesc;
		this.karma = karma;
		idCounter++;
	}

	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getKarma() {
		return karma;
	}
	
	public void setKarma(int karma) {
		this.karma = karma;
	}
	
	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public int getIDCounter() {
		return idCounter;
	}
	
	public void upvote() {
		this.karma++;
	}
	
	public void downvote() {
		this.karma--;
	}
	
	public void incID() {
		idCounter++;
	}

	@Override
	public String toString() {
		return desc;
	}

	public int compareTo(Tag o) {
		if (desc.compareTo(o.desc) == 0) {
			return 0;
		} else if (desc.compareTo(o.desc) > 0) {
			return 1;
		} else {
			return -1;
		}
	}
}
