package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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

public class SearchController implements Initializable{
	
	@FXML private AnchorPane searchPane;
	
	@FXML private TextField searchA;
	@FXML private TextField searchB;
	@FXML private Button searchButton;
	@FXML private ImageView searchImage;
	
	@FXML private ListView<Location> searchResults;
	
//	@FXML private VBox listBox;
	
	private ArrayList<Integer> keyList;
	private ArrayList<Location> locList;
	private List<Image> listOfImages;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		locList = HomeController.getLocList();
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
                            Image temp = new Image("File:E:\\Users\\Brick\\Documents\\homework\\CSE\\CSE248\\PreCapstoneHandiverse\\src\\images\\" + String.valueOf(item.getID()) + ".jpg"); //FIX ON LAPTOP FOR DISPLAY
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
