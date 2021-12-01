package model_location;

import java.util.Map;

import javafx.scene.image.ImageView;
import model_tag.Tag;
import model_tag.TagStore;

public class Location {
	
	private String name;
	private int id;
	private String address;
	private String city;
	private String state;
	private String stateInitials;
	private String zipCode;
	private String phone;
	private String type;
	private int rating;
	private TagStore tags;
//	private ImageView img;
	
	private static int idCounter = 0;
	
//	public Location(String name, String address, String city, String state, String zipCode, String phone, int rating, TagStore tags) {
//		this.name = name;
//		this.address = address;
//		this.state = state;
//		this.city = city;
//		this.zipCode = zipCode;
//		this.phone = phone;
//		this.rating = rating;
//		this.tags = tags;
//		id = String.valueOf(idCounter++);
//	}
	
	public Location(String name, int id, String address, String city, String state, String stateInitials, String zipCode, String phone,
			String type, int rating) {
		this.name = name;
		this.id = id;
		this.address = address;
		this.city = city;
		this.state = state;
		this.stateInitials = stateInitials;
		this.zipCode = zipCode;
		this.phone = phone;
		this.type = type;
		this.rating = rating;
		this.tags = new TagStore();
//		System.out.println(this.getClass().getResource(""));
//		this.img = new ImageView("src/images/" + String.valueOf(id) + ".jpg");
	}

	public Location(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateInitials() {
		return stateInitials;
	}

	public void setStateInitials(String stateInitials) {
		this.stateInitials = stateInitials;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public TagStore getTags() {
		return tags;
	}
	
	public void setTags(TagStore tags) {
		this.tags = tags;
	}
	
//	public ImageView getImg() {
//		return img;
//	}
//
//	public void setImg(ImageView img) {
//		this.img = img;
//	}

	@Override
	public String toString() {
		String tagString = "";
		for(Map.Entry<Integer, Tag> entry : tags.getTagStore().entrySet()) {
			tagString += entry.getValue().getDesc() + ", ";
			
		}
		return /*"Location [name=" +*/ name + "\n" + id + "\n" + address + "\n" + city + ", " + state
				+ ", " + stateInitials + ", " + zipCode + "\n" + phone + "\n" + type + "\n" + rating + "/5" /*, tags=" + tagString + "]"*/;
	}

	public int compareTo(Location o) {
		if (name.compareTo(String.valueOf(o.id)) == 0) {
			return 0;
		} else if (String.valueOf(id).compareTo(String.valueOf(o.id)) > 0) {
			return 1;
		} else {
			return -1;
		}
	}
	

}
