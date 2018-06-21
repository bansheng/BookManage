package application.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private StringProperty code, name, auther, type, des; 
	private StringProperty isIn;
	private StringProperty price;
	public Book(String code, String name, String auther, String type,
			String des, boolean isIn, int price) {
		super();
		this.code = new SimpleStringProperty(code);
		this.name = new SimpleStringProperty(name);
		this.auther = new SimpleStringProperty(auther);
		this.type = new SimpleStringProperty(type);
		this.des = new SimpleStringProperty(des);
		this.isIn = new SimpleStringProperty(isIn? "ÔÚ¹Ý": "Àë¹Ý");
		this.price = new SimpleStringProperty("" + price);
	}
	public StringProperty getCode() {
		return code;
	}
	public StringProperty getName() {
		return name;
	}
	public StringProperty getAuther() {
		return auther;
	}
	public StringProperty getType() {
		return type;
	}
	public StringProperty getDes() {
		return des;
	}
	public StringProperty getIsIn() {
		return isIn;
	}
	public StringProperty getPrice() {
		return price;
	}
	
}
