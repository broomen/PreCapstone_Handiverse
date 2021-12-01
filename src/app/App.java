package app;

import java.sql.Connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model_location.LocationStore;
import model_tag.TagStore;
import util.ConnectionUtil;
import util.Loader;

public class App extends Application {
	private static Connection conn;
	private static LocationStore locStore;
	private static TagStore tagStore;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		conn = null;
		conn = ConnectionUtil.getConnection();
		locStore = Loader.getLocationsFromDB();
		tagStore = Loader.getTagsFromDB();
		Loader.addTagsTo(locStore, tagStore);
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

	public static Connection getConn() {
		return conn;
	}	
	
}
