package controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model_user.User;

public class LoginController implements Initializable {

	@FXML private AnchorPane registerPane;
	
	@FXML private TextField nameFld;
	@FXML private PasswordField passFld;
	@FXML private Label failLbl1;
	@FXML private Label failLbl2;
	@FXML private Button loginBtn;
	
	private static URL previousPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		failLbl1.setVisible(false);		
		failLbl2.setVisible(false);	
	}
	
	public void handleLogin(ActionEvent event) {
		failLbl1.setVisible(false);		
		failLbl2.setVisible(false);	
		String username = nameFld.getText();
		String password = passFld.getText();
		if(App.getUserStore().getUserStore().containsKey(username)) {
			if(App.getUserStore().getUserStore().get(username).getPassword().contentEquals(password)) {
				App.setLogged(true);
				App.setUser(App.getUserStore().getUserStore().get(username));
				try {
					Parent root = FXMLLoader.load(previousPane);
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
			} else {
				failLbl2.setVisible(true);
			}
		} else {
			failLbl1.setVisible(true);
		}
	}
	
	public static void setPrevious(URL url) {
		previousPane = url;
	}

}
