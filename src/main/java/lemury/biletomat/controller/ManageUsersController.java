package lemury.biletomat.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lemury.biletomat.model.users.Coordinator;
import lemury.biletomat.model.users.User;

import java.io.IOException;


public class ManageUsersController {
    private User user;
    private ObservableList<User> users;
    private ObservableList<Coordinator> coordinators;

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Integer> userIDColumn;
    @FXML
    private TableColumn<User, String> userFirstNameColumn;
    @FXML
    private TableColumn<User, String> userLastNameColumn;
    @FXML
    private TableColumn<User, String> userLoginColumn;
    @FXML
    private TableColumn<User, String> userPasswordColumn;

    @FXML
    private TableView<Coordinator> coordinatorsTable;
    @FXML
    private TableColumn<Coordinator, Integer> coordinatorIDColumn;
    @FXML
    private TableColumn<Coordinator, String> coordinatorFirstNameColumn;
    @FXML
    private TableColumn<Coordinator, String> coordinatorLastNameColumn;
    @FXML
    private TableColumn<Coordinator, String> coordinatorLoginColumn;
    @FXML
    private TableColumn<Coordinator, String> coordinatorPasswordColumn;
    @FXML
    private TableColumn<Coordinator, String> coordinatorDepartmentColumn;

    @FXML
    private Button deleteUserButton;
    @FXML
    private Button deleteCoordinatorButton;

    @FXML
    private Button addUserButton;
    @FXML
    private Button logoutButton;

    @FXML
    protected void initialize() {
        userIDColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().id()));
        userFirstNameColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().firstName()));
        userLastNameColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().lastName()));
        userLoginColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getLogin()));
        userPasswordColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getPassword()));

        coordinatorIDColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().id()));
        coordinatorFirstNameColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().firstName()));
        coordinatorLastNameColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().lastName()));
        coordinatorLoginColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getLogin()));
        coordinatorPasswordColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getPassword()));
        coordinatorDepartmentColumn.setCellValueFactory(dataValue -> new SimpleObjectProperty<>(dataValue.getValue().getDepartment().name()));

        deleteUserButton.disableProperty().bind(Bindings.isEmpty(usersTable.getSelectionModel().getSelectedItems()));
        deleteCoordinatorButton.disableProperty().bind(Bindings.isEmpty(coordinatorsTable.getSelectionModel().getSelectedItems()));

        setUsers(User.getUsersList());
        setCoordinators(Coordinator.getCoordinatorsList());
    }


    public void setUser(User user) {
        this.user = user;
    }

    private void setUsers(ObservableList<User> users) {
        this.users = users;
        usersTable.setItems(users);
    }

    private void setCoordinators(ObservableList<Coordinator> coordinators) {
        this.coordinators = coordinators;
        coordinatorsTable.setItems(coordinators);
    }

    @FXML
    public void handleLogoutAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoggingPane.fxml"));
        Parent logging = loader.load();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();

        Scene scene = new Scene(logging);
        Stage appStage = new Stage();
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    private void handleUserDeleteAction(ActionEvent actionEvent) {
        ObservableList<User> toRemove = usersTable.getSelectionModel().getSelectedItems();

        for (User user : toRemove) {
            User.deleteUserById(user.id());
        }
        this.users.removeAll(toRemove);
    }

    @FXML
    private void handleCoordinatorDeleteAction(ActionEvent actionEvent) {
        ObservableList<Coordinator> toRemove = coordinatorsTable.getSelectionModel().getSelectedItems();

        for (Coordinator coordinator : toRemove) {
            User.deleteUserById(coordinator.id());
        }
        this.coordinators.removeAll(toRemove);
    }

    @FXML
    private void handleAddUserAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddUserPane.fxml"));
        Parent addUser = loader.load();

        AddUserController controller = loader.getController();
        controller.setCoordinators(coordinators);
        controller.setUsers(users);

        Scene scene = new Scene(addUser);
        Stage appStage = new Stage();
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    protected void handleBackAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserPane.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(new Scene(loader.load()));
        UserController userController = loader.<UserController>getController();
        userController.setUser(user);
        //userController.setTickets(Ticket.getTicketsList(user));
        stage.show();
    }
}
