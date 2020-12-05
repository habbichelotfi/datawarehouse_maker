package sample;

import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class Variables {
	private TextField nom;
	private ChoiceBox<String> type;
	private TextField taille;
	private CheckBox ifnull;
	private ChoiceBox<String> index;
	private CheckBox ia;
	private ChoiceBox<String> reference;
	

	public ChoiceBox<String> getReference() {
		return reference;
	}

	public void setReference(ChoiceBox<String> reference) {
		this.reference = reference;
	}

	public Variables(String nom,List<String> type,String taille,List<String> index,List<String> ref) {
		this.nom=new TextField(nom);
		this.type=new ChoiceBox<String>();
		for(int i=0;i<type.size();i++) {
			this.type.getItems().add(type.get(i));
		}
		this.taille=new TextField(taille);
	

		this.ifnull=new CheckBox();
		this.index=new ChoiceBox<String>();
		for(int i=0;i<index.size();i++) {
			this.index.getItems().add(index.get(i));
		}

		this.ia=new CheckBox();
		this.reference=new ChoiceBox<String>();
		for(int i=0;i<ref.size();i++) {
			this.reference.getItems().add(ref.get(i));
		}
	}
	
	public TextField getNom() {
		return nom;
	}
	public void setNom(TextField nom) {
		this.nom = nom;
	}
	public ChoiceBox getType() {
		return type;
	}
	public void setType(ChoiceBox type) {
		this.type = type;
	}
	public TextField getTaille() {
		return taille;
	}
	public void setTaille(TextField taille) {
		this.taille = taille;
	}

	public CheckBox getIfnull() {
		return ifnull;
	}
	public void setIfnull(CheckBox ifnull) {
		this.ifnull = ifnull;
	}
	public ChoiceBox getIndex() {
		return index;
	}
	public void setIndex(ChoiceBox index) {
		this.index = index;
	}
	public CheckBox getIa() {
		return ia;
	}
	public void setIa(CheckBox ia) {
		this.ia = ia;
	}
	



}
