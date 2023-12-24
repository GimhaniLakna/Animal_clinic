package lk.ijse.controller.spa;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.AppointmentDTO;
import lk.ijse.dto.tm.SpaAppointmentTM;
import lk.ijse.model.Appointment;
import lk.ijse.model.SpaAppointmentModel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class SpaAppointmentFormController {
    public TextField txtTime;
    public TextField txtDate;
    public TextField txtID;
    public TableColumn colID;
    public TableColumn colDate;
    public TableColumn colPrice;
    public TableColumn colTime;
    public ComboBox <String> txtCustomerID;
    public TableView<SpaAppointmentTM> tblAppoinments;
    public TableColumn colCustomerID;

    public Label lblDate;

    public Label lblTime;
    static SpaAppointmentFormController appointmentFormController;
    public TableColumn colCustomerName;
    public TableColumn colAnimalId;
    public TableColumn colStatus;

    public void initialize(){
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAnimalId.setCellValueFactory(new PropertyValueFactory<>("animalId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        appointmentFormController=this;
        generateRealTime();
        loadData();
        generateRealTime();
    }

    public void loadData(){
        SpaAppointmentModel spaAppointmentModel = new SpaAppointmentModel();
        ArrayList<SpaAppointmentTM> all = spaAppointmentModel.getAllAppintment();
        tblAppoinments.getItems().setAll(FXCollections.observableArrayList(all));
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        Appointment appointment=new Appointment();
        AppointmentDTO appointmentDTO =appointment.searchAppointment(id);

        if (appointmentDTO==null){
            new Alert(Alert.AlertType.ERROR,"Empty value...").show();

        }else {
            txtTime.setText(String.valueOf(appointmentDTO.getTime()));
            txtDate.setText(appointmentDTO.getDate());
            txtTime.setText(appointmentDTO.getTime());

        }
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/spa/SpaAppointmentMaintainForm.fxml")))));
        stage.show();
    }

    private void generateRealTime() {
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            lblTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
