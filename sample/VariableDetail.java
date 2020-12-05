package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;

public class VariableDetail {
	private CheckBox check;
	private Hyperlink  nom_variable;
	private String type;
	private String null_;
	public VariableDetail(String n,String t,String last) {
		this.check=new CheckBox();
		this.nom_variable=new Hyperlink(n);
		this.type=t;
		this.null_=last;
	}
	public CheckBox getCheck() {
		return check;
	}
	public void setCheck(CheckBox check) {
		this.check = check;
	}
	public Hyperlink getNom_variable() {
		return nom_variable;
	}
	public void setNom_variable(Hyperlink nom_variable) {
		this.nom_variable = nom_variable;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getNull_() {
		return null_;
	}
	public void setNull_(String null_) {
		this.null_ = null_;
	}
	


}
