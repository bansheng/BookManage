package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reader {
	private StringProperty Rno, Rname;

	public Reader(String rno, String rname) {
		super();
		Rno = new SimpleStringProperty(rno);
		Rname = new SimpleStringProperty(rname);
	}

	public StringProperty getRno() {
		return Rno;
	}

	public StringProperty getRname() {
		return Rname;
	}
	
}
