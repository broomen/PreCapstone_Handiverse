package model_tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model_location.Location;
import model_location.LocationStore;
import util.ConnectionUtil;
import util.Loader;

public class Main {

	public static void main(String[] args) {
//		Connection conn = null;
//		conn = ConnectionUtil.getConnection();
//		LocationStore locStore = new LocationStore();
//		int tempID = 0;
//		String tempLocName = "";
//		String tempLocAdd = "";
//		String tempLocCity = "";
//		String tempLocState = "";
//		String tempLocZip = "";
//		String tempLocPhone = "";
//		int tempLocRating = 0;
//
//		int tempLocID = 0;
//		String tempName = "";
//		int tempKarma = 0;
//		TagStore tagStore = new TagStore();
//		
//		try {
//			Statement statement = conn.createStatement();
//			statement.setQueryTimeout(30);
//			ResultSet rs = statement.executeQuery("SELECT * FROM locations");
//			while(rs.next()) {
//				tempID = rs.getInt("locationID");
//				tempLocName = rs.getString(2);
//				tempLocAdd = rs.getString(3);
//				tempLocState = rs.getString(5);
//				tempLocCity = rs.getString(4);
//				tempLocZip = rs.getString(6);
//				tempLocPhone = rs.getString(7);
//				tempLocRating = rs.getInt(8);	
//				Location tempLocation = new Location(tempLocName, tempID, tempLocAdd, tempLocCity, tempLocState, tempLocZip, tempLocPhone, tempLocRating);
//				locStore.add(tempLocation);
//			}
//			Statement statement2 = conn.createStatement();
//			statement2.setQueryTimeout(30);
//			ResultSet rs2 = statement2.executeQuery("SELECT * FROM tags");
//			while(rs2.next()) {
//				tempID = rs2.getInt(1);
//				tempLocID = rs2.getInt(2);
//				tempName = rs2.getString(3);
//				tempKarma = rs2.getInt(4);
//				Tag tempTag = new Tag(tempID, tempLocID, tempName, tempKarma);
//				tagStore.add(tempTag);
//			}
//		}  catch (SQLException e) {
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		} finally {
//			util.ConnectionUtil.closeConnection(conn);
//		}
//		
//		Loader.addTagsTo(locStore, tagStore);		
//		System.out.println(locStore.getLocationStore().get(1));
//		System.out.println(locStore.getLocationStore().get(2));
//		System.out.println(locStore.getLocationStore().get(2).getTags().getTagStore().get(5).getKarma());
	}

}
