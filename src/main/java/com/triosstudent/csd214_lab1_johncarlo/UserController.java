package com.triosstudent.csd214_lab1_johncarlo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private TableView<UserModel> userTable;
    @FXML
    private TableColumn<UserModel, Long> id;
    @FXML
    private TableColumn<UserModel, String> name;
    @FXML
    private TableColumn<UserModel, String> email;
    @FXML
    private TableColumn<UserModel, LocalDate> dob;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
    }

    @FXML
    protected void onLoadUsersButtonClick() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/csd214_lab1_johncarlo";
        String dbUser = "admin";
        String dbPassword = "admin";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM user";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                LocalDate dob = resultSet.getDate("dob").toLocalDate();
                userTable.getItems().add(new UserModel(id, name, email, dob));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
