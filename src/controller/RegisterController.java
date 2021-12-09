package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

public class RegisterController implements Initializable {
	
	@FXML private AnchorPane registerPane;
	
	@FXML private TextField nameFld;
	@FXML private PasswordField passFld;
	@FXML private TextField emailFld;
	@FXML private Label takenLbl1;
	@FXML private Button registerBtn;
	
	private static URL previousPane;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		takenLbl1.setVisible(false);		
	}
	
	@FXML
	public void handleRegister(ActionEvent event) {
		String username = nameFld.getText();
		if(App.getUserStore().getUserStore().containsKey(username)) {
			takenLbl1.setVisible(true);
		} else {
			String password = passFld.getText();
			String email = emailFld.getText();
			User tempUser = new User(username, password, email);
			addUserToDB(tempUser);
			App.getUserStore().add(tempUser);
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
		}
	}
	
	private void addUserToDB(User user) {
		Connection conn = null;
		conn = util.ConnectionUtil.getConnection();
		try {
			Statement statement = conn.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("INSERT INTO users " + "VALUES ('" + user.getId() +"', '" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getEmail() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("null");
		} finally {
			util.ConnectionUtil.closeConnection(conn);
		}
		
	}

	public static void setPrevious(URL url) {
		previousPane = url;
	}

}
