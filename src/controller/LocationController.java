package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model_location.Location;

public class LocationController implements Initializable{
	
	@FXML private AnchorPane locPane;
	
	@FXML private TextField searchA;
	@FXML private TextField searchB;
	@FXML private Button searchButton;
	@FXML private ImageView searchImage;
	
	@FXML private Label nameLbl;
	@FXML private Label addressLbl1;
	@FXML private Label addressLbl2;
	@FXML private Label phoneLbl;
	@FXML private Label ratingLbl;
	
	private static Location currentLocation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nameLbl.setText(currentLocation.getName());
		addressLbl1.setText(currentLocation.getAddress());
		addressLbl2.setText(currentLocation.getCity() + ", " + currentLocation.getStateInitials() + ", " + currentLocation.getZipCode());
		phoneLbl.setText(currentLocation.getPhone());
		ratingLbl.setText(String.valueOf(currentLocation.getRating()) + "/5");
		
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public static void setCurrentLocation(Location loc) {
		currentLocation = loc;
	}
	
	

	
	
}
