package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model_location.Location;
import model_location.LocationStore;
import model_tag.Tag;
import model_tag.TagStore;
import model_user.User;
import model_user.UserStore;

public class HomeController implements Initializable{
	
	@FXML private AnchorPane homePane;
	
	@FXML private TextField searchA;
	@FXML private TextField searchB;
	@FXML private Button searchButton;
	@FXML private ImageView searchImage;
	@FXML private Button loginBtn;
	@FXML private Button registerBtn;
	@FXML private Button signoutBtn;
	
	@FXML private Button restBtn;
	@FXML private Hyperlink parkLink;
	@FXML private Hyperlink museumLink;
	@FXML private Hyperlink theaterLink;
	
	private LocationStore locStore = App.getLocStore();
	private TagStore tagStore = App.getTagStore();
	private UserStore userStore = App.getUserStore();
	private LocationStore searchResultStore;
	private ArrayList<Integer> keyList;
	private static ArrayList<Location> locList;
	private static String recentLoc;
	private static boolean loggedIn;
	private static User loggedUser;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		locStore = App.getLocStore();
		tagStore = App.getTagStore();
		userStore = App.getUserStore();
		loggedIn = App.getLogged();
		if(loggedIn) {
			loginBtn.setVisible(false);
			registerBtn.setVisible(false);
			signoutBtn.setVisible(true);
			loggedUser = App.getUser();
			System.out.println(loggedUser.toString());
		} else {
			loginBtn.setVisible(true);
			registerBtn.setVisible(true);
			signoutBtn.setVisible(false);
		}
	}
	
	@FXML
	public void handleSearch(ActionEvent event) {
		searchResultStore = new LocationStore();
		String fieldA = searchA.getText().toLowerCase();
		String fieldB = searchB.getText().toLowerCase();
		if(!fieldB.isEmpty()) {
			recentLoc = fieldB;
		}
		if(searchA.getText().isEmpty() && !searchB.getText().isEmpty()) { 
			searchForLocations(fieldB);
		} else if(!searchA.getText().isEmpty() && searchB.getText().isEmpty()) {
			
			searchForTags(fieldA);
			searchForTypes(fieldA);
			searchForName(fieldA);
		} else if(!searchA.getText().isEmpty() && !searchB.getText().isEmpty()) {
			searchForMulti(fieldA, fieldB);
		}
		mapToList();
		searchResultStore.display();
		URL url;
		if (locList.size() > 0) {
			try {
				url = new File("src/view/SearchPane.fxml").toURI().toURL();
				Parent root = FXMLLoader.load(url);
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (MalformedURLException e) {
				System.out.println("url not found!");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	public void handleHiddenButton(ActionEvent event) {
		System.out.println("bonk!");
		searchResultStore = new LocationStore();
		String fieldA = ((Button)event.getSource()).getText();
		String fieldB = recentLoc;
		searchForMulti(fieldA, fieldB);
		mapToList();
		searchResultStore.display();
		URL url;
		if (locList.size() > 0) {
			try {
				url = new File("src/view/SearchPane.fxml").toURI().toURL();
				Parent root = FXMLLoader.load(url);
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} catch (MalformedURLException e) {
				System.out.println("url not found!");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void searchForMulti(String fieldA, String fieldB) {
		for(Map.Entry<Integer, Location> entry : locStore.getLocationStore().entrySet()) {
			if(entry.getValue().getName().contains(fieldA)) { //name
				if(entry.getValue().getCity().contains(fieldB) || entry.getValue().getZipCode().contains(fieldB) || entry.getValue().getState().contains(fieldB) || entry.getValue().getStateInitials().contains(fieldB)) {
					searchResultStore.add(entry.getValue());
				}
			}
			
			if(entry.getValue().getType().contains(fieldA)) { //type
				if(entry.getValue().getCity().contains(fieldB) || entry.getValue().getZipCode().contains(fieldB) || entry.getValue().getState().contains(fieldB) || entry.getValue().getStateInitials().contains(fieldB)) {
					searchResultStore.add(entry.getValue());
				}
			}
			
			for(Map.Entry<Integer, Tag> tagEntry : entry.getValue().getTags().getTagStore().entrySet()) {
				if(tagEntry.getValue().getDesc().contentEquals(fieldA)) {
					if(entry.getValue().getCity().contains(fieldB) || entry.getValue().getZipCode().contains(fieldB) || entry.getValue().getState().contains(fieldB) || entry.getValue().getStateInitials().contains(fieldB)) {
						searchResultStore.add(entry.getValue());
					}
				}
			}	
		}
	}

	private boolean searchForName(String fieldA) {
		boolean checker = false;
		for(Map.Entry<Integer, Location> entry : locStore.getLocationStore().entrySet()) {
			if(entry.getValue().getName().toLowerCase().contains(fieldA)) {
				searchResultStore.add(entry.getValue());
				checker = true;
			}
		}	
		return checker;	
	}

	private boolean searchForTypes(String fieldA) {
		boolean checker = false;
		for(Map.Entry<Integer, Location> entry : locStore.getLocationStore().entrySet()) {
			if(entry.getValue().getType().toLowerCase().contains(fieldA)) {
				searchResultStore.add(entry.getValue());
				checker = true;
			}
		}
		return checker;
	}

	private boolean searchForTags(String fieldA) {
		boolean checker = false;
		for(Map.Entry<Integer, Location> entry : locStore.getLocationStore().entrySet()) {
			for(Map.Entry<Integer, Tag> tagEntry : entry.getValue().getTags().getTagStore().entrySet()) {
				if(tagEntry.getValue().getDesc().toLowerCase().contentEquals(fieldA)) {
					searchResultStore.add(entry.getValue());
					checker = true;
				}
			}
		}
		return checker;
	}

	private boolean searchForLocations(String fieldB) {
		boolean checker = false;
		for(Map.Entry<Integer, Location> entry : locStore.getLocationStore().entrySet()) {
			if(entry.getValue().getCity().toLowerCase().contains(fieldB) || entry.getValue().getZipCode().toLowerCase().contains(fieldB) || entry.getValue().getState().toLowerCase().contains(fieldB) || entry.getValue().getStateInitials().toLowerCase().contains(fieldB)) {
				searchResultStore.add(entry.getValue());
				checker = true;
			}
		}
		return checker;		
	}
	
	@FXML
	private void handleLogin(ActionEvent event) {
		URL url;
		try {
			url = new File("src/view/HomePane.fxml").toURI().toURL();
			LoginController.setPrevious(url);
			url = new File("src/view/LoginPane.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void handleRegister(ActionEvent event) {
		URL url;
		try {
			url = new File("src/view/HomePane.fxml").toURI().toURL();
			RegisterController.setPrevious(url);
			url = new File("src/view/RegisterPane.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void handleSignout(ActionEvent event) {
		loggedIn = false;
		App.setLogged(false);
		App.setUser(null);
		refreshButtons();
		
	}
	
	@FXML
	private void signoutTest(ActionEvent event) {
		loggedIn = false;
		refreshButtons();
	}
	
	private void refreshButtons() {
		if(loggedIn) {
			loginBtn.setVisible(false);
			registerBtn.setVisible(false);
			signoutBtn.setVisible(true);
		} else {
			loginBtn.setVisible(true);
			registerBtn.setVisible(true);
			signoutBtn.setVisible(false);
		}
	}

	
	private void mapToList() {
		keyList = new ArrayList<Integer>(searchResultStore.getLocationStore().keySet());
		locList= new ArrayList<Location>(searchResultStore.getLocationStore().values());
	}

	public ArrayList<Integer> getKeyList() {
		return keyList;
	}

	public void setKeyList(ArrayList<Integer> keyList) {
		this.keyList = keyList;
	}

	public static ArrayList<Location> getLocList() {
		return locList;
	}

	public void setLocList(ArrayList<Location> locList) {
		this.locList = locList;
	}
	
	public static void setRecentLoc(String zip) {
		recentLoc = zip;
	}
	
	

}
