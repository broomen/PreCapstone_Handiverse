package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import app.App;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model_location.Location;
import model_review.Review;
import model_tag.Tag;
import model_user.User;

public class ReviewController implements Initializable{
	
	@FXML private AnchorPane revPane;
	
	@FXML private TextField searchA;
	@FXML private TextField searchB;
	@FXML private Button searchButton;
	@FXML private ImageView searchImage;
	@FXML private ImageView returnImage;
	
	@FXML private TextField nameField;
	@FXML private TextField dateField;
	@FXML private TextArea contentField;
	@FXML private RadioButton btn1;
	@FXML private RadioButton btn2;
	@FXML private RadioButton btn3;
	@FXML private RadioButton btn4;
	@FXML private RadioButton btn5;
	@FXML private Button submitBtn;
	
	@FXML private Button tagBtn;
	@FXML private TextField tagField;
	@FXML private FlowPane tagPaneField;
	private List<Button> btnList;
	
	private static boolean loggedIn;
	private static User loggedUser;
	private int tagCounter;
	
	private ToggleGroup tg;
	
	private static Location currentLocation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tagCounter = 0;
		setCurrentLocation(LocationController.getCurrentLocation());	
		tg = new ToggleGroup();
		tg.getToggles().addAll(btn1, btn2, btn3, btn4, btn5);
		nameField.setText(App.getUser().getUsername());
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		dateField.setText(format.format(date));
		nameField.setEditable(false);
		dateField.setEditable(false);
		btnList = new ArrayList<Button>();
	}
	
	public void handleSubmit(ActionEvent event) {
		String name = nameField.getText();
		String date = dateField.getText();
		RadioButton tempBtn = (RadioButton) tg.getSelectedToggle();
		int rating = Integer.parseInt(tempBtn.getText());
		String content = contentField.getText();
		Review review = new Review(currentLocation.getID(), content, name, date, rating);
		currentLocation.getReviews().add(review);
		addReviewToDB(review);
		for(int i = 0; i < btnList.size(); i++) {
			Tag tempTag = new Tag(currentLocation.getID(), btnList.get(i).getText());
			addTagsToDB(tempTag);
			currentLocation.getTags().add(tempTag);
		}
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
	
	private void addTagsToDB(Tag tag) {
		Connection conn = null;
		conn = util.ConnectionUtil.getConnection();
		try {
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("INSERT INTO tags " + "VALUES ('" + tag.getID() +"', '" + tag.getLocationID() + "', '" + tag.getDesc() + "', '" + tag.getKarma() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("null");
		} finally {
			util.ConnectionUtil.closeConnection(conn);
		}
		
	}

	private void addReviewToDB(Review review) {
		Connection conn = null;
		conn = util.ConnectionUtil.getConnection();
		try {
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("INSERT INTO reviews " + "VALUES ('" + review.getReviewID() +"', '" + review.getLocID() + "', '" +
			review.getContent() + "', '" + review.getReviewKarma() + "', '" + review.getReviewDate() + "', '" + review.getReviewAuthor() +
			"', '" + review.getRating() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("null");
		} finally {
			util.ConnectionUtil.closeConnection(conn);
		}
		
	}

	@FXML
	public void handleTagAdd(ActionEvent event) {
		String tagDesc = tagField.getText();
		tagPaneField.getChildren().clear();
		Button tempBtn;
		Font btnFont = Font.font("Verdana", 16);
		if(tagCounter <= 5) {
			tempBtn = new Button(tagDesc);
			tempBtn.setFont(btnFont);
			tempBtn.setMaxWidth(199);
			tempBtn.setPrefHeight(50);
			tempBtn.setPrefWidth(120);
			tempBtn.setMaxHeight(62);
			tempBtn.setWrapText(true);
			tempBtn.setOnAction(e -> {
				handleRemoval(e);
			});
			btnList.add(tempBtn);
		}
		tagPaneField.getChildren().addAll(btnList);
		
	}
	
	public void handleRemoval(ActionEvent event) {
		String tempText = ((Button)event.getSource()).getText();
		for(int i = 0; i < btnList.size(); i++) {
			if(btnList.get(i).getText().contentEquals(tempText)) {
				btnList.remove(i);
			}
		}
		tagPaneField.getChildren().clear();
		tagPaneField.getChildren().addAll(btnList);
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
		Image temp = new Image("File:E:\\Users\\Brick\\Documents\\homework\\CSE\\CSE248\\PreCapstoneHandiverse\\src\\images\\returnIcon2.png");
		returnImage.setImage(temp);
	}
	
	@FXML
	public void handleButtonExit(MouseEvent event) {
		Image temp = new Image("File:E:\\Users\\Brick\\Documents\\homework\\CSE\\CSE248\\PreCapstoneHandiverse\\src\\images\\returnIcon.png");
		returnImage.setImage(temp);
	}
	
	

}
