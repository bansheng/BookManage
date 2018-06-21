package application.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Borrow {
	private StringProperty Borrowno, Bookno, Bookname, date, date2;
	private StringProperty price;
	private BooleanProperty valid;
	public Borrow(String borrowno, String bookno, String bookname, String date, String date2,
			int price, boolean valid) {
		super();
		Borrowno = new SimpleStringProperty(borrowno);
		Bookno = new SimpleStringProperty(bookno);
		Bookname = new SimpleStringProperty(bookname);
		this.date = new SimpleStringProperty(date);
		this.date2 = new SimpleStringProperty(date2);
		this.price = new SimpleStringProperty("" + price);
		this.valid = new SimpleBooleanProperty(valid);
	}
	public StringProperty getBorrowno() {
		return Borrowno;
	}
	public StringProperty getBookno() {
		return Bookno;
	}
	public StringProperty getBookname() {
		return Bookname;
	}
	public StringProperty getDate() {
		return date;
	}
	public StringProperty getDate2() {
		return date2;
	}
	public StringProperty getPrice() {
		return price;
	}
	public BooleanProperty getValid() {
		return valid;
	}
	
}
