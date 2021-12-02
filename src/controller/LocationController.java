package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model_location.Location;
import model_review.Review;
import model_review.ReviewStore;
import model_tag.Tag;
import model_tag.TagStore;

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
	@FXML private ImageView locPic;
	@FXML private Button createReviewBtn;

	@FXML private TextField searchFilter;
	@FXML private CheckBox tagCheckBox;
	@FXML private CheckBox reviewCheckBox;
	@FXML private Button filterBtn;
	
	@FXML private HBox btnBox;
	@FXML private FlowPane btnPane;
	@FXML private ListView<Review> reviewListView;
	
	private List<Button> btnList;	
	private ArrayList<Review> reviewList;
	
	private static Location currentLocation;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nameLbl.setText(currentLocation.getName());
		addressLbl1.setText(currentLocation.getAddress());
		addressLbl2.setText(currentLocation.getCity() + ", " + currentLocation.getStateInitials() + ", " + currentLocation.getZipCode());
		phoneLbl.setText(currentLocation.getPhone());
		ratingLbl.setText(String.valueOf(currentLocation.getRating()) + "/5");
		buildTags(currentLocation.getTags());
		getPicture();
		buildReviews(currentLocation.getReviews());
				
		
	}

	private void buildReviews(ReviewStore reviews) {
		reviewListView.getItems().clear();
		reviewList = new ArrayList<Review>(reviews.getReviewStore().values());
		reviewListView.getItems().addAll(reviewList);
		reviewListView.setCellFactory(new Callback<ListView<Review>, ListCell<Review>>() {
            @Override
            public ListCell<Review> call(ListView<Review> p) {
                return new ListCell<Review>() {
                    @Override
                    protected void updateItem(Review item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                        	Text label = new Text(item.toString());
                            label.setWrappingWidth(reviewListView.getWidth()-40);
                            label.setFont(Font.font(19));
                            setGraphic(label);

                            // decide to add a new styleClass
                            // getStyleClass().add("costume style");
                            // decide the new font size
//                            setFont(Font.font(18));
//                            setWrapText(true);
                            
//                            Image temp = new Image("File:E:\\Users\\Brick\\Documents\\homework\\CSE\\CSE248\\PreCapstoneHandiverse\\src\\images\\" + String.valueOf(item.getID()) + ".jpg"); //FIX ON LAPTOP FOR DISPLAY
//                            ImageView tempView = new ImageView(temp);
//                            tempView.setPreserveRatio(true);
//                            tempView.setFitWidth(100);
//                            tempView.setFitHeight(100);
//                            setGraphic(tempView);
                        }
                    }
                };
            }
        });
	}

	private void getPicture() {
		Image temp = new Image("File:E:\\Users\\Brick\\Documents\\homework\\CSE\\CSE248\\PreCapstoneHandiverse\\src\\images\\" + String.valueOf(currentLocation.getID()) + ".jpg"); //FIX ON LAPTOP FOR DISPLAY
        locPic.setPreserveRatio(true);
        locPic.setFitWidth(175);
        locPic.setFitHeight(175);
        locPic.setImage(temp);
	}

	private void buildTags(TagStore tags) {
		btnPane.getChildren().clear();
		btnList = new ArrayList<Button>();
		int counter = 0;
		Button tempBtn;
		Font btnFont = Font.font("Verdana", 12);
		for(Map.Entry<Integer, Tag> entry : tags.getTagStore().entrySet()) {
			if(counter >= 8) {
				break;
			}
			tempBtn = new Button(entry.getValue().getDesc() + " | (" + String.valueOf(entry.getValue().getKarma()) + ")");
			
			tempBtn.setFont(btnFont);
			tempBtn.setMaxWidth(199);
			tempBtn.setPrefHeight(60);
			tempBtn.setPrefWidth(190);
			tempBtn.setMaxHeight(62);
			tempBtn.setWrapText(true);
			tempBtn.setOnAction(e -> {
				handleEndorsement(e);
			});
			btnList.add(tempBtn);
		}
		btnPane.getChildren().addAll(btnList);
		
	}
	
	public void handleCreateReview(ActionEvent event) {
		try {
			URL url = new File("src/view/ReviewPane.fxml").toURI().toURL();
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
	
	public void handleEndorsement(ActionEvent e) {
		String tempText = ((Button)e.getSource()).getText();
		tempText = tempText.substring(0, tempText.indexOf("|") - 1);
		for(Map.Entry<Integer, Tag> entry : currentLocation.getTags().getTagStore().entrySet()) {
			if(entry.getValue().getDesc().contains(tempText)) {
				entry.getValue().upvote();
				((Button)e.getSource()).setText(entry.getValue().getDesc() + " | (" + String.valueOf(entry.getValue().getKarma()) + ")");
			}
		}
	}
	
	public void handleFiltering() {
		String filter = searchFilter.getText().toLowerCase();
		ReviewStore filteredReviews = new ReviewStore();
		TagStore filteredTags = new TagStore();
		if(tagCheckBox.isSelected() && !reviewCheckBox.isSelected()) {
			for(Map.Entry<Integer, Tag> entry : currentLocation.getTags().getTagStore().entrySet()) {
				if(entry.getValue().getDesc().toLowerCase().contains(filter)) {
					filteredTags.add(entry.getValue());
				}
			}
			buildTags(filteredTags);
		} else if (!tagCheckBox.isSelected() && reviewCheckBox.isSelected()) {
			for(Map.Entry<Integer, Review> entry : currentLocation.getReviews().getReviewStore().entrySet()) {
				if(entry.getValue().getContent().toLowerCase().contains(filter)) {
					filteredReviews.add(entry.getValue());
				}
			}
			buildReviews(filteredReviews);
		} else if (tagCheckBox.isSelected() && reviewCheckBox.isSelected()) {
			for(Map.Entry<Integer, Tag> entry : currentLocation.getTags().getTagStore().entrySet()) {
				if(entry.getValue().getDesc().toLowerCase().contains(filter)) {
					filteredTags.add(entry.getValue());
				}
			}
			buildTags(filteredTags);
			for(Map.Entry<Integer, Review> entry : currentLocation.getReviews().getReviewStore().entrySet()) {
				if(entry.getValue().getContent().toLowerCase().contains(filter)) {
					filteredReviews.add(entry.getValue());
				}
			}
			buildReviews(filteredReviews);
		}
		
	}

	public static Location getCurrentLocation() {
		return currentLocation;
	}

	public static void setCurrentLocation(Location loc) {
		currentLocation = loc;
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
