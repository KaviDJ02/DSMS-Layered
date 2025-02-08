package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.SessionBO;
import com.drivesmart.dsms.dto.SessionDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SessionViewController {

    public AnchorPane mainPane;
    public Label todaySessionsCount;
    public AnchorPane addSessionPane;
    public TextField studentTF;
    public TextField instructorTF;
    public TextField vehicleTF;
    public Label dateLabel;
    public VBox sessionsVbox;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private Label monthLabel;
    @FXML
    private ComboBox<Integer> hourComboBox;
    @FXML
    private ComboBox<Integer> minuteComboBox;

    private YearMonth currentYearMonth;

    private String clickedDay;

    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    public SessionViewController() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        currentYearMonth = YearMonth.now();
        updateCalendar();

        mainPane.setVisible(true);
        addSessionPane.setVisible(false);

        // Sample data for auto-completion
        List<String> students = sessionBO.getStudentsNames();
        List<String> instructors = sessionBO.getInstructorsNames();
        List<String> vehicles = sessionBO.getVehicles();

        // Adding auto-completion to text fields
        TextFields.bindAutoCompletion(studentTF, students);
        TextFields.bindAutoCompletion(instructorTF, instructors);
        TextFields.bindAutoCompletion(vehicleTF, vehicles);

        // Initialize hour and minute ComboBoxes
        hourComboBox.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList())));
        minuteComboBox.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList())));

        todaySessionsCount.setText(setSessionCount(LocalDate.now().toString()));

    }

    private void updateCalendar() throws SQLException, ClassNotFoundException {
        // Remove previous calendar entries
        calendarGrid.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7; // Adjusting for Sunday to be 0

        monthLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + currentYearMonth.getYear());

        int currentDay = 1;

        // Determine the maximum number of sessions in a day for the current month
        int maxSessions = IntStream.rangeClosed(1, daysInMonth)
                .map(day -> {
                    try {
                        return sessionBO.getSessionCount(currentYearMonth.atDay(day).toString());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .max()
                .orElse(1);

        // Loop through rows and columns to populate the calendar
        for (int row = 1; row <= 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 1 && col < dayOfWeek) {
                    continue; // Skip empty cells before the start of the month
                }
                if (currentDay > daysInMonth) {
                    break; // Stop once all days in the month are added
                }

                StackPane dayCell = new StackPane();
                dayCell.setPrefSize(100, 100);
                dayCell.setStyle("-fx-border-color: #a3bffa; -fx-border-width: 1px;");

                Label dayLabel = new Label(String.valueOf(currentDay));
                dayLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #ffffff;");
                StackPane.setAlignment(dayLabel, Pos.CENTER);

                // Calculate the transparency level based on the session count
                int sessionCount = sessionBO.getSessionCount(currentYearMonth.atDay(currentDay).toString());
                double transparency = maxSessions > 0 ? (double) sessionCount / maxSessions : 0;
                String backgroundColor = String.format("rgba(8, 221, 35, %.2f)", transparency);

                //rgba(8, 221, 35, 1) "rgba(18, 108, 244, %.2f)"

                dayCell.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-color: #a3bffa; -fx-border-width: 1px;");

                // Add tooltip for each day
                Tooltip tooltip = new Tooltip(setSessionCount(currentYearMonth.atDay(currentDay).toString()));
                Tooltip.install(dayLabel, tooltip);

                dayCell.getChildren().add(dayLabel);
                calendarGrid.add(dayCell, col, row);

                // Open session dialog when clicked
                final int day = currentDay;
                dayCell.setOnMouseClicked(event -> openSessionDialog(currentYearMonth.atDay(day)));

                currentDay++;
            }
        }
    }

    private void openSessionDialog(LocalDate date) {

        System.out.println("Manage sessions for: " + date);

        clickedDay = date.toString();

        mainPane.setVisible(false);
        addSessionPane.setVisible(true);

        addSessionCard();

        dateLabel.setText(date.toString());

    }

    @FXML
    public void prevMonthButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        currentYearMonth = currentYearMonth.minusMonths(1);
        updateCalendar();
    }

    @FXML
    public void nextMonthButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        currentYearMonth = currentYearMonth.plusMonths(1);
        updateCalendar();
    }

    public void createSessionBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        System.out.println("Create session button clicked");
        // Get the entered data
        String time = setTime();
        int student = sessionBO.getStudentId(studentTF.getText());
        int instructor = sessionBO.getInstructorId(instructorTF.getText());
        int vehicle = sessionBO.getVehicleId(vehicleTF.getText());

        SessionDTO session = new SessionDTO();
        session.setSessionDate(dateLabel.getText());
        session.setSessionTime(time);
        session.setStudentId(student);
        session.setEmployeeId(instructor);
        session.setVehicleId(vehicle);

        //TODO: Validate the data

        // Save the session data
        sessionBO.saveSession(session);

        // Clear text fields
        studentTF.clear();
        instructorTF.clear();
        vehicleTF.clear();

        // Refresh the session cards
        sessionsVbox.getChildren().clear();
        addSessionCard();

        updateCalendar();

        todaySessionsCount.setText(setSessionCount(LocalDate.now().toString()));
    }

    public void backOnAction(MouseEvent mouseEvent) {
        mainPane.setVisible(true);
        addSessionPane.setVisible(false);

        // Clear text fields
        studentTF.clear();
        instructorTF.clear();
        vehicleTF.clear();
    }

    public String setTime() {
        Integer hour = hourComboBox.getValue();
        Integer minute = minuteComboBox.getValue();

        String formattedTime = "-1";

        if (hour != null && minute != null) {
            LocalTime time = LocalTime.of(hour, minute);
            formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return formattedTime;
    }

    public void addSessionCard() {
        try {
            sessionsVbox.getChildren().clear();

            List<SessionDTO> sessions = sessionBO.getAllSessions(clickedDay); // Fetch session data from the model

            for (SessionDTO session : sessions) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SessionCard.fxml"));
                AnchorPane sessionCard = loader.load();

                // Get the controller and set session data
                SessionCardController controller = loader.getController();
                controller.setSessionData(session.getSessionTime(),sessionBO.getStudentName(session.getStudentId()), sessionBO.getInstructorName(session.getEmployeeId()), sessionBO.getVehicleModel(session.getVehicleId()));

                sessionsVbox.getChildren().add(sessionCard);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String setSessionCount(String date) throws SQLException, ClassNotFoundException {
        int count = sessionBO.getSessionCount(date);
        return count + " sessions";
    }
}
