package app;

import java.sql.Connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model_location.LocationStore;
import model_review.ReviewStore;
import model_tag.TagStore;
import model_user.User;
import model_user.UserStore;
import util.ConnectionUtil;
import util.Loader;

public class App extends Application {
	private static Connection conn;
	private static LocationStore locStore;
	private static TagStore tagStore;
	private static ReviewStore reviewStore;
	private static UserStore userStore;
	private static boolean isLogged;
	private static User currentUser;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		conn = null;
		conn = ConnectionUtil.getConnection();
		locStore = Loader.getLocationsFromDB();
		tagStore = Loader.getTagsFromDB();
		reviewStore = Loader.getReviewsFromDB();
		userStore = Loader.getUsersFromDB();
		isLogged = false;
		controller.HomeController.setRecentLoc("11201");
		Loader.addTagsTo(locStore, tagStore);
		Loader.addReviewsTo(locStore, reviewStore);
		Parent root = FXMLLoader.load(getClass().getResource("/view/HomePane.fxml"));
		Scene scene = new Scene(root, 1024, 768);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Handiverse");
		primaryStage.show();
	}
	
	public static LocationStore getLocStore() {
		return locStore;
	}
	
	public static TagStore getTagStore() {
		return tagStore;
	}
	
	public static ReviewStore getReviewStore() {
		return reviewStore;
	}
	
	public static UserStore getUserStore() {
		return userStore;
	}
	
	public static User getUser() {
		return currentUser;
	}
	
	public static void setUser(User user) {
		currentUser = user;
	}
	
	public static Boolean getLogged() {
		return isLogged;
	}
	
	public static void setLogged(Boolean log) {
		isLogged = log;
	}

	public static Connection getConn() {
		return conn;
	}	
	
}
