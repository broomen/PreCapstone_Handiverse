package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model_location.Location;
import model_review.Review;

public class ReviewController implements Initializable{
	
	@FXML private AnchorPane revPane;
	
	@FXML private TextField searchA;
	@FXML private TextField searchB;
	@FXML private Button searchButton;
	@FXML private ImageView searchImage;
	
	@FXML private TextField nameField;
	@FXML private TextField dateField;
	@FXML private TextArea contentField;
	@FXML private RadioButton btn1;
	@FXML private RadioButton btn2;
	@FXML private RadioButton btn3;
	@FXML private RadioButton btn4;
	@FXML private RadioButton btn5;
	@FXML private Button submitBtn;
	
	private ToggleGroup tg;
	
	private static Location currentLocation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCurrentLocation(LocationController.getCurrentLocation());	
		tg = new ToggleGroup();
		tg.getToggles().addAll(btn1, btn2, btn3, btn4, btn5);
	}
	
	public void handleSubmit(ActionEvent event) {
		String name = nameField.getText();
		String date = dateField.getText();
		RadioButton tempBtn = (RadioButton) tg.getSelectedToggle();
		int rating = Integer.parseInt(tempBtn.getText());
		String content = contentField.getText();
		Review review = new Review(currentLocation.getID(), content, name, date, rating);
		currentLocation.getReviews().add(review);
		try {
			URL url = new File("src/view/LocationPane.fxml").toURI().toURL();
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
	

	public static Location getCurrentLocation() {
		return currentLocation;
	}

	public static void setCurrentLocation(Location currentLocation) {
		ReviewController.currentLocation = currentLocation;
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
	

}
