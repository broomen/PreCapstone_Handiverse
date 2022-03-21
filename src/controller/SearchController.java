package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import app.App;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import model_location.Location;
import model_location.LocationStore;
import model_tag.Tag;
import model_user.User;

public class SearchController implements Initializable{
	
	@FXML private AnchorPane searchPane;
	
	@FXML private TextField searchA;
	@FXML private TextField searchB;
	@FXML private Button searchButton;
	@FXML private ImageView searchImage;
	@FXML private Button loginBtn;
	@FXML private Button registerBtn;
	@FXML private Button signoutBtn;
	
	@FXML private Label resultLbl;
	
	@FXML private ImageView returnImage;
	
	@FXML private ListView<Location> searchResults;
	
//	@FXML private VBox listBox;
	private static boolean loggedIn;
	private static User loggedUser;
	
	private LocationStore locStore = App.getLocStore();
	private LocationStore searchResultStore;
	private static String recentLoc;
	private ArrayList<Integer> keyList;
	private ArrayList<Location> locList;
	private List<Image> listOfImages;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		locList = HomeController.getLocList();
		resultLbl.setVisible(false);
		refreshButtons();
		if(locList.size() > 0) {
			searchResults.setVisible(true);
			searchResults.getItems().addAll(locList);
			ImageView testview;
			searchResults.setCellFactory(new Callback<ListView<Location>, ListCell<Location>>() {
	            @Override
	            public ListCell<Location> call(ListView<Location> p) {
	                return new ListCell<Location>() {
	                    @Override
	                    protected void updateItem(Location item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (item != null) {
	                            setText(item.toString());

	                            // decide to add a new styleClass
	                            // getStyleClass().add("costume style");
	                            // decide the new font size
	                            setFont(Font.font(18));
	                            Image temp = new Image("File:C:\\Users\\Nick\\eclipse-workspace\\PreCapstone_Handiverse\\src\\images\\" + String.valueOf(item.getID()) + ".jpg"); //FIX ON LAPTOP FOR DISPLAY
	                            ImageView tempView = new ImageView(temp);
	                            tempView.setPreserveRatio(true);
	                            tempView.setFitWidth(100);
	                            tempView.setFitHeight(100);
	                            setGraphic(tempView);
	                        }
	                    }
	                };
	            }
	        });	
		} else {
			searchResults.setVisible(false);
			resultLbl.setVisible(true);
		}
			
	}
	
	public void handleLocationSelect(MouseEvent event) {
		Location tempLocation = (Location) searchResults.getSelectionModel().getSelectedItem();
		if(tempLocation == null) {
			System.out.println("null");
		} else {
			URL url;
				try {
					controller.LocationController.setCurrentLocation(tempLocation);
					url = new File("src/view/LocationPane.fxml").toURI().toURL();
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
	
	public void handleHomeButton(MouseEvent event) {
		try {
			URL url = new File("src/view/HomePane.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (MalformedURLException e) {
			System.out.println("url not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleBack(ActionEvent event) {
		try {
			URL url = new File("src/view/HomePane.fxml").toURI().toURL();
			Parent root = FXMLLoader.load(url);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (MalformedURLException e) {
			System.out.println("url not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleButtonHover(MouseEvent event) {
		Image temp = new Image("File:C:\\Users\\Nick\\eclipse-workspace\\PreCapstone_Handiverse\\src\\images\\returnIcon2.png");
		returnImage.setImage(temp);
	}
	
	@FXML
	public void handleButtonExit(MouseEvent event) {
		Image temp = new Image("File:C:\\Users\\Nick\\eclipse-workspace\\PreCapstone_Handiverse\\src\\images\\returnIcon.png");
		returnImage.setImage(temp);
	}
	
	@FXML
	private void handleLogin(ActionEvent event) {
		URL url;
		try {
			url = new File("src/view/SearchPane.fxml").toURI().toURL();
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
			url = new File("src/view/SearchPane.fxml").toURI().toURL();
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
//		searchResultStore.display();
		URL url;
			try {
				HomeController.setLocList(locList);
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
	
	private void searchForMulti(String fieldA, String fieldB) {
		for(Map.Entry<Integer, Location> entry : locStore.getLocationStore().entrySet()) {
			if(entry.getValue().getName().toLowerCase().contains(fieldA)) { //name
				if(entry.getValue().getCity().toLowerCase().contains(fieldB) || entry.getValue().getZipCode().toLowerCase().contains(fieldB) || entry.getValue().getState().toLowerCase().contains(fieldB) || entry.getValue().getStateInitials().toLowerCase().contains(fieldB)) {
					searchResultStore.add(entry.getValue());
				}
			}
			
			if(entry.getValue().getType().toLowerCase().contains(fieldA)) { //type
				if(entry.getValue().getCity().toLowerCase().contains(fieldB) || entry.getValue().getZipCode().toLowerCase().contains(fieldB) || entry.getValue().getState().toLowerCase().contains(fieldB) || entry.getValue().getStateInitials().toLowerCase().contains(fieldB)) {
//				if(entry.getValue().getCity().contains(fieldB) || entry.getValue().getZipCode().contains(fieldB) || entry.getValue().getState().contains(fieldB) || entry.getValue().getStateInitials().contains(fieldB)) {
					searchResultStore.add(entry.getValue());
				}
			}
			
			for(Map.Entry<Integer, Tag> tagEntry : entry.getValue().getTags().getTagStore().entrySet()) {
				if(tagEntry.getValue().getDesc().toLowerCase().contentEquals(fieldA)) {
					if(entry.getValue().getCity().toLowerCase().contains(fieldB) || entry.getValue().getZipCode().toLowerCase().contains(fieldB) || entry.getValue().getState().toLowerCase().contains(fieldB) || entry.getValue().getStateInitials().toLowerCase().contains(fieldB)) {
//					if(entry.getValue().getCity().contains(fieldB) || entry.getValue().getZipCode().contains(fieldB) || entry.getValue().getState().contains(fieldB) || entry.getValue().getStateInitials().contains(fieldB)) {
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
	
	private void mapToList() {
		keyList = new ArrayList<Integer>(searchResultStore.getLocationStore().keySet());
		locList= new ArrayList<Location>(searchResultStore.getLocationStore().values());
	}
	
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		locList = HomeController.getLocList();
//		URL imageurl;
//		try {
////			imageurl = new File("@../../images/searchIcon.png").toURI().toURL();
//			//FileInputStream inputStream = new FileInputStream();
//			Image tempImage = new Image(new FileInputStream("@../../images/searchIcon.png"));
//			String tempImageString;
//			for (int i = 0; i < locList.size(); i++) {
//				searchResults.getItems().add(locList.get(i));
//				tempImageString = "@../../images/" + locList.get(i).getID() + ".jpg";
//				tempImage = new Image(new FileInputStream(tempImageString));
//				listOfImages.add(tempImage);
//			}
//			searchResults.setCellFactory(param -> new ListCell<Location>() {
//				private ImageView imageView = new ImageView();
//
//				@Override
//				public void updateItem(Location location, boolean empty) {
//					super.updateItem(location, empty);
//					if (empty) {
//						setGraphic(null);
//					} else {
//						imageView.setImage(listOfImages.get(0));
//						setGraphic(imageView);
//						listOfImages.remove(0);
//					}
//				}
//			});
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			System.out.println("shits fucked");
//		}
//
////		listBox = new VBox(searchResults);
//	}
//	
//	static class Cell extends ListCell<String> {
//		HBox hbox = new HBox();
//		Label lbl = new Label("");
//		Pane pane = new Pane();
//		Image profile = new Image("/images/0.jpg");
//		ImageView img = new ImageView(profile);
//		
//		public Cell() {
//			super();
//			hbox.getChildren().addAll(img, lbl, pane);
//			hbox.setHgrow(pane,  Priority.ALWAYS);
//			
//		}
//		
//		public void updateItem(String name, boolean empty) {
//			super.updateItem(name, empty);
//			setText(null);
//			setGraphic(null);
//			
//			if(name != null && !empty) {
//				
//			}
//		}
//		
//	}

}
