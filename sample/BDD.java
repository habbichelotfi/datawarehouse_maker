package sample;

import java.sql.Date;
import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;

public class BDD {
	private CheckBox check;
private Hyperlink bdd_name;
private String  date;

public BDD(String bdd_name, String date) {
	super();
	this.bdd_name = new Hyperlink(bdd_name);
	this.date = date;
	this.setCheck(new CheckBox());
}
public Hyperlink getBdd_name() {
	return bdd_name;
}
public void setBdd_name(Hyperlink bdd_name) {
	this.bdd_name = bdd_name;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}

public Hyperlink secondNameProperty() {
	return bdd_name;
}
public String lastNameProperty() {
	return date;
}

public CheckBox getCheck() {
	return check;
}
public void setCheck(CheckBox check) {
	this.check = check;
}

}
