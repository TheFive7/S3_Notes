module notes.s3_notes {
    requires javafx.controls;
    requires javafx.fxml;


    opens notes.s3_notes to javafx.fxml;
    exports notes.s3_notes;
}