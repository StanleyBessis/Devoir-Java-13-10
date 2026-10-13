module sio.devoir1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens sio.devoir1 to javafx.fxml;
    exports sio.devoir1;
}