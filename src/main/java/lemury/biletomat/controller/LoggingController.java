package lemury.biletomat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lemury.biletomat.model.users.Coordinator;
import lemury.biletomat.model.users.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

//import oracle.jrockit.jfr.events.EventControl;

public class LoggingController {
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Label informationLabel;
    @FXML
    private Button loginButton;


    @FXML
    private void handleLoggingAction(ActionEvent event) throws IOException {
        Optional<User> optionalUser = User.findByLogin(login.getText(), password.getText());

        if (!optionalUser.isPresent()) {
            informationLabel.setText("Bledny login/haslo");
            informationLabel.setVisible(true);
        } else {
            informationLabel.setText("Zalogowano");
            informationLabel.setVisible(true);
            User user = optionalUser.get();
            if (!user.getUserType().equalsIgnoreCase("u")) {
                user = Coordinator.findById(user.id()).get();
            }

            user.updateTickets();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserPane.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(loader.load()));
            UserController userController = loader.getController();
            userController.setUser(user);
            //userController.setTickets(Ticket.getTicketsList(user));
            stage.show();
        }
    }

    @FXML
    public void handleEnter(ActionEvent event) throws IOException {
        this.handleLoggingAction(event);
    }
}