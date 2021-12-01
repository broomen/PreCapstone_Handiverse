package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model_location.Location;
import model_location.LocationStore;
import model_tag.Tag;
import model_tag.TagStore;

public class Loader {
	
	public void loadLocations() {
		Connection conn = null;
		conn = util.ConnectionUtil.getConnection();
		try {
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			
		} catch (SQLException e) {
			System.out.println("nope!");
			e.printStackTrace();
		}
		
	}
	
	public static void addTagsTo(LocationStore locStore, TagStore tagStore) {
		Location tempLocation = new Location(10);
		Tag tempTag = new Tag("yay");
		for(int i = 1; i < locStore.size() + 1; i++) {
			tempLocation = locStore.getLocationStore().get(i);
			for(int j = 1; j < tagStore.size() + 1; j++) {
				tempTag = tagStore.getTagStore().get(j);
				if(tempTag.getLocationID() == tempLocation.getID()) {
					locStore.getLocationStore().get(i).getTags().add(tempTag);
				}				
			}
		}
	}
	
	public static LocationStore getLocationsFromDB() {
		Connection conn = null;
		conn = ConnectionUtil.getConnection();
		LocationStore locStore = new LocationStore();
		int tempID = 0;
		String tempLocName = "";
		String tempLocAdd = "";
		String tempLocCity = "";
		String tempLocState = "";
		String tempLocInitials = "";
		String tempLocZip = "";
		String tempLocPhone = "";
		String tempLocType = "";
		int tempLocRating = 0;
		try {
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("SELECT * FROM locations");
			while(rs.next()) {
				tempID = rs.getInt("locationID");
				tempLocName = rs.getString(2);
				tempLocAdd = rs.getString(3);
				tempLocState = rs.getString(5);
				tempLocCity = rs.getString(4);
				tempLocInitials = rs.getString(6);
				tempLocZip = rs.getString(7);
				tempLocPhone = rs.getString(8);
				tempLocType = rs.getString(9);
				tempLocRating = rs.getInt(10);	
				Location tempLocation = new Location(tempLocName, tempID, tempLocAdd, tempLocCity, tempLocState, tempLocInitials, tempLocZip, tempLocPhone, tempLocType, tempLocRating);
				locStore.add(tempLocation);
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			util.ConnectionUtil.closeConnection(conn);
		}
		return locStore;
	}
	
	public static TagStore getTagsFromDB() {
		Connection conn = null;
		conn = ConnectionUtil.getConnection();
		int tempLocID = 0;
		int tempID = 0;
		String tempName = "";
		int tempKarma = 0;
		TagStore tagStore = new TagStore();
		try {
			Statement statement2 = conn.createStatement();
			statement2.setQueryTimeout(30);
			ResultSet rs2 = statement2.executeQuery("SELECT * FROM tags");
			while(rs2.next()) {
				tempID = rs2.getInt(1);
				tempLocID = rs2.getInt(2);
				tempName = rs2.getString(3).toLowerCase();
				tempKarma = rs2.getInt(4);
				Tag tempTag = new Tag(tempID, tempLocID, tempName, tempKarma);
				tagStore.add(tempTag);
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			util.ConnectionUtil.closeConnection(conn);
		}
		return tagStore;
	}

}
