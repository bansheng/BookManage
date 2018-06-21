package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pulish {
	private StringProperty Rno, Bname, reason;
	private StringProperty cost;
	public Pulish(String rno, String bname, String reason, int cost) {
		super();
		Rno = new SimpleStringProperty(rno);
		Bname = new SimpleStringProperty(bname);
		this.reason = new SimpleStringProperty(reason);
		this.cost = new SimpleStringProperty( "" + cost);
	}
	public StringProperty getRno() {
		return Rno;
	}
	public StringProperty getBname() {
		return Bname;
	}
	public StringProperty getReason() {
		return reason;
	}
	public StringProperty getCost() {
		return cost;
	}
	
	
}
