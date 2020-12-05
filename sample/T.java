package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;

public class T {
private CheckBox check;
private Hyperlink  nom_table;
private String type;
private String create_the;
private String last_modif;

public T(String n,String t,String c,String last) {
	this.check=new CheckBox();
	this.nom_table=new Hyperlink(n);
	this.type=t;
	this.create_the=c;
	this.last_modif=last;
}
public CheckBox getCheck() {
	return check;
}
public void setCheck(CheckBox check) {
	this.check = check;
}
public Hyperlink getNom_table() {
	return nom_table;
}
public void setNom_table(Hyperlink nom_table) {
	this.nom_table = nom_table;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getCreate_the() {
	return create_the;
}
public void setCreate_the(String create_the) {
	this.create_the = create_the;
}
public String getLast_modif() {
	return last_modif;
}
public void setLast_modif(String last_modif) {
	this.last_modif = last_modif;
}

}
