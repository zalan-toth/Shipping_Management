module org.example {
	requires javafx.controls;
	requires javafx.fxml;
	requires xstream;

	opens net.pyel to javafx.fxml;
	exports net.pyel;
	exports net.pyel.utils;
	opens net.pyel.utils to javafx.fxml;
	exports net.pyel.models;
	opens net.pyel.models to javafx.fxml;
}
