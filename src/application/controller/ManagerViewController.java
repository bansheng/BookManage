package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import application.Main;
import application.model.AutoCompleteTextField;
import application.model.AutoCompleteTextFieldBuilder;
import application.model.Book;
import application.model.Pulish;
import application.model.Reader;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class ManagerViewController {
	@FXML
	private ListView<String> listview;
	@FXML
	private ListView<String> BookRank;
	@FXML
	private TextArea tf_Borrow_Rlimit;
	@FXML
	private TextField tf_Borrow_Rno;
	@FXML
	private TextField tf_Borrow_Bookno;
	@FXML
	private TextField tf_Borrow_amount;
	@FXML
	private TextField tf_Borrow_BooKname;
	@FXML
	private TextField tf_Borrow_Cost;
	@FXML
	private CheckBox cb_isBroken;
	@FXML
	private Button btn_addBook;
	@FXML
	private Button btn_removeBook;
	@FXML
	private Button btn_borrowBook;
	@FXML
	private TextField tf_Giveback_Rno;
	@FXML
	private TextField tf_Giveback_Bookno;
	@FXML
	private TextField tf_Giveback_Date;
	@FXML
	private TextField tf_Giveback_Bookname;
	@FXML
	private TextField tf_Giveback_price;
	@FXML
	private TextField tf_Giveback_Bno;
	@FXML
	private TextField tf_Pulish_code;
	@FXML
	private TextField tf_Pulish_name;
	@FXML
	private TableView<Pulish> table_pulish;
	@FXML
	private TableColumn<Pulish, String> Pcolumn_code;
	@FXML
	private TableColumn<Pulish, String> Pcolumn_bookname;
	@FXML
	private TableColumn<Pulish, String> Pcolumn_cost;
	@FXML
	private TableColumn<Pulish, String> Pcolumn_reason;
	@FXML
	private TableView<Book> table_Book;
	@FXML
	private TextField tf_Book_name;
	@FXML
	private TextField tf_Book_num;
	@FXML
	private TableColumn<Book, String> Bcolumn_code;
	@FXML
	private TableColumn<Book, String> Bcolumn_name;
	@FXML
	private TableColumn<Book, String> Bcolumn_auther;
	@FXML
	private TableColumn<Book, String> Bcolumn_type;
	@FXML
	private TableColumn<Book, String> Bcolumn_des;
	@FXML
	private TableColumn<Book, String> Bcolumn_state;
	@FXML
	private TableColumn<Book, String> Bcolumn_price;
	@FXML
	private TableView<Reader> table_reader;
	@FXML
	private TableColumn<Reader, String> Rcolumn_code;
	@FXML
	private TableColumn<Reader, String> Rcolumn_name;
	@FXML
	private TextField tf_reader_code;
	@FXML
	private TextField tf_reader_name;
	@FXML
	private TextField tf_reader_password;
	@FXML
	private TextField tf_reader_phone;
	@FXML
	private TextField tf_reader_age;
	@FXML
	private TextField tf_reader_sex;
	@FXML
	private TextField tf_reader_cost;
	@FXML
	private TextArea tf_reader_limit;
	@FXML
	private Button btn_search;
	@FXML
	private Button btn_delete;
	@FXML
	private Button btn_confirm_delete;
	@FXML
	private Button btn_create;
	@FXML
	private Button btn_confirm_create;
	@FXML
	private Button btn_modify;
	@FXML
	private Button btn_confirm_modify;
	@FXML
	private Label lb_name;
	@FXML
	private Button btn_exit;
	@FXML
	private Tab tab_borrow;
	@FXML
	private Tab tab_return;
	@FXML
	private Tab tab_pulish;
	@FXML
	private Tab tab_reader;
	@FXML
	private Tab tab_book;

	private static String managename; // ����Ա����
	Main m;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ���Է�����޸����ڸ�ʽ
	private ObservableList<Integer> booksNo = FXCollections.observableArrayList(); // ��ӽ�ȥ���鼮����б�
	private ObservableList<String> booksList = FXCollections.observableArrayList(); // ���׼�������鼮�����б�
	private ObservableList<Integer> bookscost = FXCollections.observableArrayList(); // ���׼�������鼮�۸��б�
	// �������������鼮���

	private ObservableList<String> bookRank = FXCollections.observableArrayList(); // �鼮�������

	private ObservableList<Pulish> PulishData = FXCollections.observableArrayList();
	private ObservableList<Integer> PulishNo = FXCollections.observableArrayList(); // �������
	private ObservableList<Book> BookData = FXCollections.observableArrayList();
	private ObservableList<Reader> ReaderData = FXCollections.observableArrayList();

	private ArrayList<String> autoBook = new ArrayList<String>();
	private AutoCompleteTextField autoBookName; // ͼ���Ƽ�

	@FXML
	private void initialize() {
		Pcolumn_code.setCellValueFactory(cellData -> cellData.getValue().getRno());
		Pcolumn_code.setStyle("-fx-alignment: CENTER;");
		Pcolumn_bookname.setCellValueFactory(cellData -> cellData.getValue().getBname());
		Pcolumn_bookname.setStyle("-fx-alignment: CENTER;");
		Pcolumn_cost.setCellValueFactory(cellData -> cellData.getValue().getCost());
		Pcolumn_cost.setStyle("-fx-alignment: CENTER;");
		Pcolumn_reason.setCellValueFactory(cellData -> cellData.getValue().getReason());
		Pcolumn_reason.setStyle("-fx-alignment: CENTER;");
		Bcolumn_code.setCellValueFactory(cellData -> cellData.getValue().getCode());
		Bcolumn_code.setStyle("-fx-alignment: CENTER;");
		Bcolumn_name.setCellValueFactory(cellData -> cellData.getValue().getName());
		Bcolumn_name.setStyle("-fx-alignment: CENTER;");
		Bcolumn_auther.setCellValueFactory(cellData -> cellData.getValue().getAuther());
		Bcolumn_auther.setStyle("-fx-alignment: CENTER;");
		Bcolumn_type.setCellValueFactory(cellData -> cellData.getValue().getType());
		Bcolumn_type.setStyle("-fx-alignment: CENTER;");
		Bcolumn_des.setCellValueFactory(cellData -> cellData.getValue().getDes());
		Bcolumn_des.setStyle("-fx-alignment: CENTER;");
		Bcolumn_state.setCellValueFactory(cellData -> cellData.getValue().getIsIn());
		Bcolumn_state.setStyle("-fx-alignment: CENTER;");
		Bcolumn_price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
		Bcolumn_price.setStyle("-fx-alignment: CENTER;");
		Rcolumn_code.setCellValueFactory(cellData -> cellData.getValue().getRno());
		Rcolumn_code.setStyle("-fx-alignment: CENTER;");
		Rcolumn_name.setCellValueFactory(cellData -> cellData.getValue().getRname());
		Rcolumn_name.setStyle("-fx-alignment: CENTER;");
		BookRecommend();
		
		booksList.add("---------------���鵥------------");
		listview.setItems(booksList);
	}

	public void setManageName(String name) {
		managename = name;
		lb_name.setText(managename);
	}

	public void setMain(Main m1) {
		m = m1;
	}

	// Event Listener on TextField[#tf_Borrow_Rno].onKeyPressed
	@FXML
	public void findLimitKey(KeyEvent event) {
		// TODO Autogenerated
		if (event.getCode() == KeyCode.ENTER) {
			findLimit(null);
		}
	}

	// ���鵥����
	@FXML
	public void addBook(MouseEvent event) {
		// TODO Autogenerated
		if (tf_Borrow_Rno == null || tf_Borrow_Rno.getText().equals("")) {
			return;
		} else if (!tf_Borrow_Rlimit.getText().trim().equals("��")) {
			// System.out.println("����������");
			JOptionPane.showMessageDialog(null, "���������ƣ����Ƚ��ɷ���", "��Ϣ��ʾ", 0);
			return;
		}
		int price = 0;
		boolean isfound = false;
		String auther = "";
		if (tf_Borrow_Bookno.getText() != null && !tf_Borrow_Bookno.getText().trim().equals("")) // ���������
		{
			String sql = "select * from dbo.book where Bookno = ? and Isin = ? and valid = ?";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, Integer.parseInt(tf_Borrow_Bookno.getText()));
				psmt.setBoolean(2, true);
				psmt.setBoolean(3, true);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					isfound = true;
					tf_Borrow_Bookno.setText(Main.addzero(rs.getInt("Bookno")));
					tf_Borrow_BooKname.setText(rs.getString("Bname").trim());
					auther = rs.getString("Bauther").trim();
					price = rs.getInt("Bcost");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (tf_Borrow_BooKname.getText() != null && !tf_Borrow_BooKname.getText().trim().equals("")) // ����������
		{
			String sql = "select * from dbo.book where Bname = ? and Isin = ? and valid = ?";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql);
				psmt.setString(1, tf_Borrow_BooKname.getText());
				psmt.setBoolean(2, true);
				psmt.setBoolean(3, true);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					isfound = true;
					tf_Borrow_Bookno.setText(Main.addzero(rs.getInt("Bookno")));
					auther = rs.getString("Bauther").trim();
					price = rs.getInt("Bcost");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// else {//���벻�Ϸ�
		// JOptionPane.showMessageDialog(null, "û���ҵ��������鼮", "��Ϣ��ʾ", 0);
		// }
		int bookno = Integer.parseInt(tf_Borrow_Bookno.getText());
		if (!isfound) { // û�в�ѯ��
			JOptionPane.showMessageDialog(null, "û���ҵ��������鼮���߸��鼮���ڹ���!", "��Ϣ��ʾ", 0);
			tf_Borrow_Bookno.clear();
			tf_Borrow_BooKname.clear();
			return;
		} else if (booksNo.contains(bookno)) {
			JOptionPane.showMessageDialog(null, "���鼮�Ѿ����б���\n �����ظ�����鼮", "��Ϣ��ʾ", 0);
			return;
		}

		booksList.add("�� " + tf_Borrow_BooKname.getText().trim() + " �� --" + auther + "   �۸�: " + price + " Ԫ");
		booksNo.add(bookno);
		bookscost.add(price);
		listview.setItems(booksList);
		tf_Borrow_amount.setText(Integer.parseInt(tf_Borrow_amount.getText()) + price + "");
		tf_Borrow_Bookno.clear();
		// tf_Borrow_BooKname.clear();
	}

	// ���鵥�Ƴ�
	@FXML
	public void removeBook(MouseEvent event) {
		// TODO Autogenerated
		if (booksList.isEmpty()) {
			// System.out.println("�鵥Ϊ��!");
			JOptionPane.showMessageDialog(null, "�鵥Ϊ��!", "��Ϣ��ʾ", 0);
			return;
		}

		int index = listview.getSelectionModel().getSelectedIndex();
		if (index == -1 || index == 0)
			return;
		booksList.remove(index);
		booksNo.remove(index-1);
		int price = bookscost.get(index-1);
		bookscost.remove(index-1);
		tf_Borrow_amount.setText(Integer.parseInt(tf_Borrow_amount.getText()) - price + "");
		tf_Borrow_Bookno.clear();
		tf_Borrow_BooKname.clear();
	}

	/**
	 * ͼ���Ƽ�
	 */
	@FXML
	public void BookRecommend() {
		autoBookName = AutoCompleteTextFieldBuilder.build(tf_Borrow_BooKname);
		autoBook.clear();
		bookRank.clear();
		bookRank.add("----ͼ���Ƚ��----");
		String sql = "select Bname, Btimes from dbo.book where valid = ? and isIn = ? order by Btimes DESC";
		PreparedStatement psmt;
		int count = 0;
		try {
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, true);
			psmt.setBoolean(2, true);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				autoBook.add(rs.getString("Bname").trim());
				count++;
				if (count > 4)
					break;
			}

			sql = "select Bname, Btimes from dbo.book where valid = ? order by Btimes DESC";
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, true);
			rs = psmt.executeQuery();
			count = 0;
			while (rs.next()) {
				bookRank.add("��" + rs.getString("Bname").trim() + "��--" + rs.getInt("Btimes") + "��");
				count++;
				if (count > 10)
					break;
			}
			BookRank.setItems(bookRank);
			autoBookName.setCacheDataList(autoBook);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �ҵ����ߵĽ�������
	@FXML
	public void findLimit(MouseEvent event) {
		int Rno = 0;
		try {
			Rno = Integer.parseInt(tf_Borrow_Rno.getText());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			return;
		}

		// ������༭��
		tf_Borrow_Rno.setEditable(false);
		// ��������б��е�����
		booksList.clear();
		booksNo.clear();
		booksList.clear();
		booksList.add("---------------���鵥------------");
		listview.setItems(booksList);
		bookscost.clear();
		String sql = "select Rno, Rcost, Rlimit from dbo.reader where Rno = ? and Rvalid = ?";
		PreparedStatement psmt;
		try {
			int limit = 0;
			psmt = Main.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, Rno);
			psmt.setBoolean(2, true);
			ResultSet rs = psmt.executeQuery();
			boolean isfound = false;
			if (rs.wasNull())
				isfound = false;
			else {
				while (rs.next()) {
					isfound = true;
					limit = rs.getInt("Rlimit");
					tf_Borrow_Rno.setText(Main.addzero(rs.getInt("Rno")));
					tf_Borrow_Cost.setText(rs.getInt("Rcost") + "");
					tf_Borrow_amount.setText("0");
				}
			}

			if (!isfound) {
				JOptionPane.showMessageDialog(null, "�Ҳ���������Ϣ!", "��Ϣ��ʾ", 0);
				tf_Borrow_Rno.setEditable(true);
				return;
			}

			String limits = limit == 0 ? "��" : "";
			if (limit != 0) {
				sql = "select Preason from dbo.pulishment where Rno = ? and Pvalid = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, Rno);
				psmt.setBoolean(2, true);
				rs = psmt.executeQuery();
				while (rs.next()) {
					limits += rs.getString("Preason") + "\n";
				}
			}

			tf_Borrow_Rlimit.setText(limits);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ���飬һ�ν�౾
	@FXML
	public void BorrowBook(MouseEvent event) {
		// TODO Autogenerated
		if (booksNo.isEmpty()) {
			return;
		}

		// �鿴����Ƿ񳬱�
		int amount = Integer.parseInt(tf_Borrow_amount.getText());
		int limit = Integer.parseInt(tf_Borrow_Cost.getText());
		if (amount > limit) {
			JOptionPane.showMessageDialog(null, "����������������޶�\n���ʵ�ɾ���鼮", "��Ϣ��ʾ", 1);
			return;
		}

		// �����ҵ�������±�
		int count = 0;
		try {
			String sql = "select count(*) I from dbo.borrow";
			PreparedStatement psmt;
			psmt = Main.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("I"); // �ҵ����ı��
			}
			int Rno = Integer.parseInt(tf_Borrow_Rno.getText());
			int times = 0;
			for (int index = 0; index < booksNo.size(); index++) {
				// ��ѯ�鼮������
				sql = "select Btimes from dbo.book where Bookno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, booksNo.get(index));
				rs = psmt.executeQuery();
				while (rs.next()) {
					times = rs.getInt("Btimes");
				}

				// �����޸��鼮״̬
				sql = "update dbo.book set isIn = ?, Btimes = ? where Bookno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, false);
				psmt.setInt(2, times + 1);
				psmt.setInt(3, booksNo.get(index));
				psmt.execute();

				// ���޸�����
				sql = "insert into dbo.borrow values(?, ?, ?, ?, ?, ?, ?)";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, index + count + 1);
				psmt.setInt(2, Rno);
				psmt.setInt(3, booksNo.get(index));
				psmt.setInt(4, bookscost.get(index));
				psmt.setString(5, dateFormat.format(new Date()));
				psmt.setString(6, dateFormat.format(new Date()));
				psmt.setBoolean(7, true);
				psmt.execute();
			}

			// ���Ҫ�޸Ķ��ߵ�����޶�
			sql = "update dbo.reader set Rcost = ? where Rno = ?";
			psmt = Main.con.prepareStatement(sql);
			psmt.setInt(1, limit - amount);
			psmt.setInt(2, Rno);
			psmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "����ɹ�!", "��Ϣ��ʾ", 0);
		BorrowclearAll();
		BookRecommend();
	}

	// �������б���Ϣ
	@FXML
	public void BorrowclearAll() {
		// TODO Autogenerated
		tf_Borrow_Rno.clear();
		tf_Borrow_Rlimit.clear();
		tf_Borrow_Cost.clear();
		tf_Borrow_Cost.clear();
		tf_Borrow_Bookno.clear();
		tf_Borrow_BooKname.clear();
		tf_Borrow_amount.setText("0");
		listview.getItems().clear();
		booksList.add("---------------���鵥------------");
		listview.setItems(booksList);
		booksNo.clear();
		bookscost.clear();
		tf_Borrow_Rno.setEditable(true);
	}

	@FXML
	public void findBorrowKey(KeyEvent event) {
		// TODO Autogenerated
		if (event.getCode() == KeyCode.ENTER) {
			findBorrow();
		}
	}

	// �ҵ����߽���鼮����Ϣ��������ţ����ƣ��������
	@FXML
	public void findBorrow() {
		// TODO Autogenerated
		returnClearAll(false);
		boolean isfound = false;
		if (tf_Giveback_Bookno.getText() != null && !tf_Giveback_Bookno.getText().trim().equals("")) // ���������
		{
			// ���鼮���Ѱ��
			String sql = "select dbo.borrow.Bookno Bookno, Rno, dbo.borrow.Bcost Bcost, date, Bname, Bno"
					+ " from dbo.borrow, dbo.book where dbo.borrow.Bookno = ?"
					+ " and isIn = ? and valid = ? and Bvalid = ?" + " and dbo.borrow.Bookno = dbo.book.Bookno";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, Integer.parseInt(tf_Giveback_Bookno.getText()));
				psmt.setBoolean(2, false);
				psmt.setBoolean(3, true);
				psmt.setBoolean(4, true);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					isfound = true;
					tf_Giveback_Bno.setText(Main.addzero(rs.getInt("Bno")));
					tf_Giveback_Bookno.setText(Main.addzero(rs.getInt("Bookno")));
					tf_Giveback_Bookname.setText(rs.getString("Bname").trim());
					tf_Giveback_Rno.setText(Main.addzero(rs.getInt("Rno")));
					tf_Giveback_Date.setText(rs.getString("date").substring(0, 19));
					tf_Giveback_price.setText("" + rs.getString("Bcost"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (tf_Giveback_Bookname.getText() != null && !tf_Giveback_Bookname.getText().trim().equals("")) // ����������
		{
			String sql = "select dbo.borrow.Bookno Bookno, Rno, dbo.borrow.Bcost Bcost, date, Bname, Bno"
					+ " from dbo.book, dbo.borrow" + " where Bname = ? and isIn = ? and valid = ?" + " and Bvalid = ?"
					+ " and dbo.book.Bookno = dbo.borrow.Bookno";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql);
				psmt.setString(1, tf_Giveback_Bookname.getText().trim());
				psmt.setBoolean(2, false);
				psmt.setBoolean(3, true);
				psmt.setBoolean(4, true);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					isfound = true;
					tf_Giveback_Bno.setText(Main.addzero(rs.getInt("Bno")));
					tf_Giveback_Bookno.setText(Main.addzero(rs.getInt("Bookno")));
					tf_Giveback_Bookname.setText(rs.getString("Bname").trim());
					tf_Giveback_Rno.setText(Main.addzero(rs.getInt("Rno")));
					tf_Giveback_Date.setText(rs.getString("date"));
					tf_Giveback_price.setText("" + rs.getString("Bcost"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!isfound)
			JOptionPane.showMessageDialog(null, "��ѯ����ָ��������Ϣ", "��Ϣ��ʾ", 1);
	}

	// �鼮�黹���
	public void returnClearAll(boolean All) {
		// TODO Autogenerated
		if (All) {
			tf_Giveback_Bookno.clear();
			tf_Giveback_Bookname.clear();
		}
		tf_Giveback_Rno.clear();
		tf_Giveback_Date.clear();
		tf_Giveback_price.clear();
		tf_Giveback_Bno.clear();
	}

	// �鼮�黹
	@FXML
	public void returnBook(MouseEvent event) {
		// TODO Autogenerated
		// ����������
		Date now = new Date();
		Date before;
		long gap = 0;
		int Rno = 1;
		int Bookno = 1;
		int Bno = 1;
		int price = 0;
		try {
			Rno = Integer.parseInt(tf_Giveback_Rno.getText());
			Bookno = Integer.parseInt(tf_Giveback_Bookno.getText());
			Bno = Integer.parseInt(tf_Giveback_Bno.getText());
			before = dateFormat.parse(tf_Giveback_Date.getText());
			price = Integer.parseInt(tf_Giveback_price.getText());
			gap = (long) ((now.getTime() - before.getTime())) / (24 * 3600 * 1000);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (cb_isBroken.isSelected() || gap > 30) {
			// �鼮�����ȼ����ڹ���
			int pay = price;
			String info = "�黹ʱ�����!";
			if (cb_isBroken.isSelected()) {
				info = "�鼮��!";
			} else
				pay = (int) (price * 0.2);

			int count = 0;
			String sql = "select count(*) I from dbo.pulishment";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					count = rs.getInt("I");
				}
				sql = "insert dbo.pulishment values(?, ?, ?, ?, ?, ?, ?)";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, count + 1);
				psmt.setInt(2, Rno);
				psmt.setInt(3, Bookno);
				psmt.setBoolean(4, cb_isBroken.isSelected());
				psmt.setBoolean(5, true);
				psmt.setInt(6, pay);
				psmt.setString(7, info + " ��" + tf_Giveback_Bookname.getText() + "��");
				psmt.execute();
				JOptionPane.showMessageDialog(null, info + "���⳥" + pay + "Ԫ", "��Ϣ��ʾ", 0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// �޸�ͼ��� isin
		if (cb_isBroken.isSelected()) {
			String sql = "update dbo.book set valid = ? where Bookno = ?";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, false);
				psmt.setInt(2, Bookno);
				psmt.execute();
				// ɾ��ͼ��
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String sql = "update dbo.book set isin = ? where Bookno = ?";
			PreparedStatement psmt;
			try {
				// �޸�ͼ��״̬
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, true);
				psmt.setInt(2, Bookno);
				psmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int amount = 0;
		int limit = 0;
		try { // �ҵ��û����,���û�����
			String sql = "select Rcost, Rlimit from dbo.reader  where Rno = ? and Rvalid = ?";
			PreparedStatement psmt;
			psmt = Main.con.prepareStatement(sql);
			psmt.setInt(1, Rno);
			psmt.setBoolean(2, true);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				amount = rs.getInt("Rcost");
				limit = rs.getInt("Rlimit");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�û���Ϣ��ѯʧ��", "��Ϣ��ʾ", 1);
			return;
		}

		// �޸Ķ��߱� limit cost
		if (cb_isBroken.isSelected() || gap > 30) {
			String sql = "update dbo.reader set Rcost = ?, Rlimit = ? where Rno = ?";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, amount + price);
				psmt.setInt(2, limit + 1);
				psmt.setInt(3, Rno);
				psmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String sql = "update dbo.reader set Rcost = ? where Rno = ?";
			PreparedStatement psmt;
			try {
				//
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, amount + price);
				psmt.setInt(2, Rno);
				psmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// �޸Ľ���� bvalid
		try {
			String sql = "update dbo.borrow set Bvalid = ?, date2 = ? where Bno = ?";
			PreparedStatement psmt;
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, false);
			psmt.setString(2, dateFormat.format(new Date()));
			psmt.setInt(3, Bno);
			psmt.execute();

			JOptionPane.showMessageDialog(null, "����ɹ���", "��Ϣ��ʾ", 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "������ִ���", "��Ϣ��ʾ", 0);
		}
		returnClearAll(true);
	}

	@FXML
	public void Reborrow_Book(MouseEvent event) {
		// ���Ȼ��� ����
		// TODO Autogenerated
		// ����������
		Date now = new Date();
		Date before;
		long gap = 0;
		int Rno = 1;
		int Bookno = 1;
		int Bno = 1;
		int price = 0;
		try {
			Rno = Integer.parseInt(tf_Giveback_Rno.getText());
			Bookno = Integer.parseInt(tf_Giveback_Bookno.getText());
			Bno = Integer.parseInt(tf_Giveback_Bno.getText());
			before = dateFormat.parse(tf_Giveback_Date.getText());
			price = Integer.parseInt(tf_Giveback_price.getText());
			gap = (long) ((now.getTime() - before.getTime())) / (24 * 3600 * 1000);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (cb_isBroken.isSelected()) {
			System.out.println("���ˣ��������裡");
			return;
		}
		if (gap > 30) {
			System.out.println("���ڣ��Ƚ�����");
			returnBook(null);
			return;
		}

		// ����
		// �޸Ľ����
		try {
			String sql = "update dbo.borrow set Bvalid = ?, date2 = ? where Bno = ?";
			PreparedStatement psmt;
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, false);
			psmt.setString(2, dateFormat.format(new Date()));
			psmt.setInt(3, Bno);
			psmt.execute();

			int count = 0;
			// �ҵ�������
			sql = "select count(*) I from dbo.borrow";
			psmt = Main.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("I"); // �ҵ����ı��
			}

			// �ٽ�������
			sql = "insert into dbo.borrow values(?, ?, ?, ?, ?, ?, ?)";
			psmt = Main.con.prepareStatement(sql);
			psmt.setInt(1, count + 1);
			psmt.setInt(2, Rno);
			psmt.setInt(3, Bookno);
			psmt.setInt(4, price);
			psmt.setString(5, dateFormat.format(new Date()));
			psmt.setString(6, dateFormat.format(new Date()));
			psmt.setBoolean(7, true);
			psmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ������ѯ
	@FXML
	public void pulish_search(MouseEvent event) {
		// TODO Autogenerated
		boolean isfound = false;
		int Rno = 1;
		if (tf_Pulish_code.getText() != null && !tf_Pulish_code.getText().trim().equals("")) // ���������
		{
			// �ɶ��߱��Ѱ��
			try {
				Rno = Integer.parseInt(tf_Pulish_code.getText());
				isfound = true;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (tf_Pulish_name.getText() != null && !tf_Pulish_name.getText().trim().equals("")) // ����������
		{
			String sql = "select Rno from dbo.reader where Rname = ? and Rvalid = ?";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql);
				psmt.setString(1, tf_Pulish_name.getText().trim());
				psmt.setBoolean(2, true);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					Rno = rs.getInt("Rno");
					isfound = true;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (isfound)
			pulish_Load(1, Rno);
		else
			JOptionPane.showMessageDialog(null, "������ѯ����", "��Ϣ��ʾ", 0);
	}

	// ��������
	public void pulish_Load(int choice, int Rno) {
		PulishData.clear();
		PulishNo.clear();
		try {
			String sql;
			PreparedStatement psmt = null;
			switch (choice) {
			case 0:
				sql = "select Pno, Rno, Bname, pcost, preason from dbo.pulishment left join dbo.book on"
						+ " dbo.pulishment.Bookno = dbo.Book.Bookno where Pvalid = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, true);
				break;
			case 1:
				sql = "select Pno, Rno, Bname, pcost, preason from dbo.pulishment left join dbo.book on"
						+ " dbo.pulishment.Bookno = dbo.Book.Bookno  where Pvalid = ? and Rno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, true);
				psmt.setInt(2, Rno);
				break;
			}

			String bookName, pulishReason;
			int pulishcost, pno;
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				pno = rs.getInt("Pno");
				Rno = rs.getInt("Rno");
				bookName = rs.getString("Bname");
				pulishReason = rs.getString("preason").trim();
				pulishcost = rs.getInt("pcost");
				Pulish p = new Pulish(Main.addzero(Rno), bookName, pulishReason, pulishcost);
				PulishData.add(p);
				PulishNo.add(pno);
			}

			table_pulish.setItems(PulishData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�������ش���", "��Ϣ��ʾ", 0);
		}
	}

	// �����޸�
	@FXML
	public void pulish_pay(MouseEvent event) {
		// TODO Autogenerated
		int index = table_pulish.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		else {
			try {
				// �޸ķ�����
				String sql = "update dbo.pulishment set Pvalid = ? where Pno = ?";
				PreparedStatement psmt;
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, false);
				psmt.setInt(2, PulishNo.get(index));
				psmt.execute();

				// �����ҵ����ߵ�����
				int limit = 0;
				int Rno = Integer.parseInt(PulishData.get(index).getRno().get());
				sql = "select Rlimit from dbo.reader where Rno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, Rno);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					limit = rs.getInt("Rlimit");
				}

				// �޸Ķ�������
				sql = "update dbo.reader set Rlimit = ? where Rno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, limit - 1);
				psmt.setInt(2, Rno);
				psmt.execute();

				PulishData.remove(index);
				PulishNo.remove(index);
				table_pulish.setItems(PulishData);
				JOptionPane.showMessageDialog(null, "�������ɳɹ�", "��Ϣ��ʾ		", 0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "����ɾ��ʧ��", "��Ϣ��ʾ", 0);
			}
		}
	}

	// �鼮�б�ɸѡ
	@FXML
	public void book_Search() {
		if (tf_Book_num.getText() != null && !tf_Book_num.getText().trim().equals("")) // ���������
		{
			// ���鼮���Ѱ��
			int Bookno = 0;
			try {
				Bookno = Integer.parseInt(tf_Book_num.getText());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "�鼮��Ų�����", "��Ϣ��ʾ", 0);
			}
			ObservableList<Book> searchBookData = FXCollections.observableArrayList();
			for (Book b : BookData) {
				if (Integer.parseInt(b.getCode().get()) == Bookno) {
					searchBookData.add(b);
				}
			}
			table_Book.setItems(searchBookData);

		} else if (tf_Book_name.getText() != null && !tf_Book_name.getText().trim().equals("")) // ����������
		{
			String name = tf_Book_name.getText();
			ObservableList<Book> searchBookData = FXCollections.observableArrayList();
			for (Book b : BookData) {
				if (b.getName().get().contains(name)) {
					searchBookData.add(b);
				}
			}
			table_Book.setItems(searchBookData);
		} else
			book_Load();
	}

	// �鼮�б����
	public void book_Load() {
		tf_Book_name.clear();
		tf_Book_num.clear();
		BookData.clear();
		try {
			int Bno, price;
			String Bname, auther, type, des;
			boolean isIn;
			String sql = "select * from dbo.Book where valid = ?";
			PreparedStatement stmt = Main.con.prepareStatement(sql);
			stmt.setBoolean(1, true);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Bno = rs.getInt("Bookno");
				Bname = rs.getString("Bname");
				price = rs.getInt("Bcost");
				auther = rs.getString("Bauther");
				type = rs.getString("Btype");
				des = rs.getString("Bdesc");
				isIn = rs.getBoolean("IsIn");
				Book b = new Book(Main.addzero(Bno), Bname.trim(), auther.trim(), type.trim(), des.trim(), isIn, price);
				BookData.add(b);
			}

			table_Book.setItems(BookData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ��������˽�һ��
	@FXML
	public void newBookIn(MouseEvent event) {
		m.showBookview(this);
	}

	// �鼮�ļ�����
	@FXML
	public void BookOut(MouseEvent event) {
		FileChooser fileChooser1 = new FileChooser();
		fileChooser1.setTitle("Save Books");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser1.getExtensionFilters().add(extFilter);
		File file = fileChooser1.showSaveDialog(Main.fileStage);

		if (file == null)
			return;
		try {
			FileOutputStream fos = new FileOutputStream(file);// ��������ļ������ڻ��Զ������ļ�
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");// �Ͷ�ȡһ��������ת�������ֽں��ַ���
			BufferedWriter bw = new BufferedWriter(osw);// ������д�뻺����

			for (Book b : BookData) {
				bw.write(b.getName().get() + "\n");
				bw.write(b.getAuther().get() + "\n");
				bw.write(b.getPrice().get() + "\n");
				bw.write(b.getType().get() + "\n");
				bw.write(b.getDes().get() + "\n");
			}
			JOptionPane.showMessageDialog(null, "�����鼮�ɹ�", "��Ϣ��ʾ", 1);
			bw.close();// ������һ�� �����򿪵��ȹر� �ȴ򿪵ĺ�ر�
			osw.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void newBookFileIn(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(Main.fileStage);
		// System.out.println(file);

		if (file.exists()) { // �ļ�����
			try (FileInputStream fis = new FileInputStream(file)) {// �ļ������� �����ֽ���

				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				// inputstreamReader��һ���ֽ��������ֽ������ַ���ת����ʱ�򣬾���Ҫ�ƶ�һ�����뷽ʽ����Ȼ�ͻ�����
				BufferedReader br = new BufferedReader(isr);// �ַ�������

				// һ�β��������鼮��ע���ظ��鼮,���Ȱ��б������鼮�����ߺ�����ȫ����¼����
				// ��Ϊ�ȶ��ظ��鼮����Ϣ
				ObservableList<String> newBookname = FXCollections.observableArrayList();
				ObservableList<String> newBookauther = FXCollections.observableArrayList();

				for (Book b : BookData) {
					newBookname.add(b.getName().get());
					newBookauther.add(b.getAuther().get());
				}

				int index = 0; // �ҵ�����ͼ�����ʼ�±�
				String sql = "select Bookno from dbo.Book";
				PreparedStatement psmt = Main.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = psmt.executeQuery();
				if (rs.last()) {
					index = rs.getInt("Bookno");
				}

				String Name, Auther, type, des;
				int price;
				while ((Name = br.readLine()) != null) {
					Auther = br.readLine();
					price = Integer.parseInt(br.readLine());
					type = br.readLine();
					des = br.readLine();
					// �������һ�����鼮

					// if(newBookname.contains(Name) && newBookauther.contains(Auther)
					// && newBookname.indexOf(Name) == newBookauther.indexOf(Auther)) {
					// // ��ȫ��ͬ
					// continue;
					// }
					// else {
					index++;
					sql = "insert into dbo.Book values(?,?,?,?,?,?,?,?)";
					psmt = Main.con.prepareStatement(sql);
					psmt.setInt(1, index);
					psmt.setString(2, Name);
					psmt.setInt(3, price);
					psmt.setString(4, Auther);
					psmt.setString(5, type);
					psmt.setString(6, des);
					psmt.setBoolean(7, true);
					psmt.setBoolean(8, true);
					psmt.setInt(9, 0);
					psmt.execute();
					// }
				}
				JOptionPane.showMessageDialog(null, "�����鼮�ɹ�", "��Ϣ��ʾ", 1);
				book_Load();

				br.close();// ��󽫸����̹߳ر�
				isr.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// ��������˽�һ��
	public void oldBookOut(MouseEvent event) {
		int index = table_Book.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			return;
		}
		if (table_Book.getItems().get(index).getIsIn().get().equals("���")) {
			JOptionPane.showMessageDialog(null, "δ�黹���鼮����ɾ��", "��Ϣ��ʾ", 0);
			return;
		}
		Book b = table_Book.getItems().get(index);
		String sql = "update dbo.Book set valid = ? where Bookno = ?";
		PreparedStatement psmt;
		try {
			int a = Integer.parseInt(b.getCode().get());
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, false);
			psmt.setInt(2, a);
			psmt.execute();
			// ���¼����鼮
			book_Load();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �����б����
	public void reader_Load() {
		// TODO Autogenerated
		ReaderData.clear();
		reader_ClearAll();
		reader_Modify(false); // �������޸�

		// ��ťʹ�ܿ���
		btn_confirm_modify.setDisable(true);
		btn_confirm_create.setDisable(true);
		btn_confirm_delete.setDisable(true);
		btn_search.setDisable(false);
		btn_delete.setDisable(false);
		btn_create.setDisable(false);
		btn_modify.setDisable(false);
		try {
			String Rno, Rname;
			String sql = "select Rno, Rname from dbo.reader where Rvalid= ?";
			PreparedStatement stmt = Main.con.prepareStatement(sql);
			stmt.setBoolean(1, true);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Rno = Main.addzero(rs.getInt("Rno"));
				Rname = rs.getString("Rname");
				Reader r = new Reader(Rno, Rname);
				ReaderData.add(r);
			}
			table_reader.setItems(ReaderData);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "������Ϣ����ʧ��", "��Ϣ��ʾ", 0);
		}
	}

	// ������Ϣ���
	public void reader_ClearAll() {
		tf_reader_code.clear();
		tf_reader_name.clear();
		tf_reader_password.clear();
		tf_reader_phone.clear();
		tf_reader_age.clear();
		tf_reader_sex.clear();
		tf_reader_cost.clear();
		tf_reader_limit.clear();
	}

	// ������Ϣ�����޸ĺ���
	public void reader_Modify(boolean permit) {
		tf_reader_name.setEditable(permit);
		tf_reader_password.setEditable(permit);
		tf_reader_phone.setEditable(permit);
		tf_reader_age.setEditable(permit);
		tf_reader_sex.setEditable(permit);
		tf_reader_cost.setEditable(permit);
	}

	// ������Ϣ��ѯ
	@FXML
	public void reader_search(MouseEvent event) {
		// TODO Autogenerated
		int index = table_reader.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			return;
		} else {
			try {
				String sql = "select * from dbo.reader where Rno = ?";
				PreparedStatement psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, Integer.parseInt(ReaderData.get(index).getRno().get()));
				ResultSet rs = psmt.executeQuery();
				int Rno = 1;
				int limit = 0;
				while (rs.next()) {
					Rno = rs.getInt("Rno");
					tf_reader_code.setText(Main.addzero(Rno));
					tf_reader_name.setText(rs.getString("Rname").trim());
					tf_reader_password.setText(rs.getString("Rpassword"));
					tf_reader_phone.setText(rs.getString("Rphone"));
					tf_reader_age.setText(rs.getString("Rage"));
					tf_reader_sex.setText(rs.getString("Rsex"));
					tf_reader_cost.setText(rs.getString("Rcost"));
					limit = rs.getInt("Rlimit");
				}

				String limits = limit == 0 ? "��" : "";
				if (limit != 0) {
					sql = "select Preason from dbo.pulishment where Rno = ? and Pvalid = ?";
					psmt = Main.con.prepareStatement(sql);
					psmt.setInt(1, Rno);
					psmt.setBoolean(2, true);
					rs = psmt.executeQuery();
					while (rs.next()) {
						limits += rs.getString("Preason") + "\n";
					}
				}

				tf_reader_limit.setText(limits);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ������Ϣ����
	@FXML
	public void reader_update(MouseEvent event) {
		// TODO Autogenerated
		int index = table_reader.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			return;
		}
		reader_search(null); // ���Ȳ�ѯ
		reader_Modify(true);
		// ��ťʹ�ܿ���
		btn_confirm_modify.setDisable(false);
		btn_confirm_create.setDisable(true);
		btn_confirm_delete.setDisable(true);
		btn_search.setDisable(true);
		btn_delete.setDisable(true);
		btn_create.setDisable(true);
		btn_modify.setDisable(true);
	}

	// ȡ�������޸Ļ����½�
	@FXML
	public void reader_cancle(MouseEvent event) {
		// TODO Autogenerated
		reader_Load();
	}

	// ���ߴ���
	@FXML
	public void reader_create(MouseEvent event) {
		// ��ťʹ�ܿ���
		btn_confirm_modify.setDisable(true);
		btn_confirm_create.setDisable(false);
		btn_confirm_delete.setDisable(true);
		btn_search.setDisable(true);
		btn_delete.setDisable(true);
		btn_create.setDisable(true);
		btn_modify.setDisable(true);

		tf_reader_name.setEditable(true);
		tf_reader_password.setEditable(true);
		tf_reader_phone.setEditable(true);
		tf_reader_age.setEditable(true);
		tf_reader_sex.setEditable(true);
		tf_reader_cost.setEditable(true);
		String sql = "select Rno from dbo.reader";
		PreparedStatement psmt;
		try {
			psmt = Main.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = psmt.executeQuery();
			int count = 0;
			if (rs.wasNull()) {
				count = 0;
			} else {
				rs.last();
				count = rs.getInt("Rno");
			}
			tf_reader_code.setText(Main.addzero(count + 1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ȷ��ɾ��������Ϣ
	@FXML
	public void reader_delete(MouseEvent event) {
		// TODO Autogenerated
		// ��ťʹ�ܿ���
		btn_confirm_modify.setDisable(true);
		btn_confirm_create.setDisable(true);
		btn_confirm_delete.setDisable(false);
		btn_search.setDisable(true);
		btn_delete.setDisable(true);
		btn_create.setDisable(true);
		btn_modify.setDisable(true);

	}

	// ����ɾ��
	@FXML
	public void reader_confirm_delete(MouseEvent event) {
		// TODO Autogenerated
		int index = table_reader.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			return;
		}

		try {
			int Rno = Integer.parseInt(ReaderData.get(index).getRno().get());
			// ���Ƚ���pulishment
			int count = 0;
			String sql = "select count(*) I from dbo.pulishment where Rno = ? and Pvalid = ?";
			PreparedStatement psmt;
			psmt = Main.con.prepareStatement(sql);
			psmt.setInt(1, Rno);
			psmt.setBoolean(2, true);
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt("I");
			}

			if (count > 0) {
				System.out.println("��������δ���ɵ�!");
				JOptionPane.showMessageDialog(null, "���Ƚ��ɷ���", "��Ϣ��ʾ", 0);
				book_Load();
				return;
			}

			// ���½����
			int countb = 0;
			sql = "select count(*) I from dbo.borrow where Rno = ? and Bvalid = ?";
			psmt = Main.con.prepareStatement(sql);
			psmt.setInt(1, Rno);
			psmt.setBoolean(2, true);
			rs = psmt.executeQuery();

			while (rs.next()) {
				countb = rs.getInt("I");
			}
			if (countb > 0) {
				System.out.println("ͼ�黹��δ�黹��!");
				JOptionPane.showMessageDialog(null, "���ȹ黹ͼ�����ѡ����ͼ�飡", "��Ϣ��ʾ", 0);
				book_Load();
				return;
			}

			// ���¶�����Ϣ
			sql = "update dbo.reader set Rvalid = ? where Rno = ?";
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, false);
			psmt.setInt(2, Rno);
			psmt.execute();

			JOptionPane.showMessageDialog(null, "����ɾ���ɹ�", "��Ϣ��ʾ", 1);
			tab_selection_change5();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("�����ɾ��ʧ��");
		}

	}

	// ȷ�ϸ��¶�����Ϣ
	@FXML
	public void reader_confirm_update(MouseEvent event) {
		// TODO Autogenerated
		String sql = "update dbo.reader set Rpassword = ?, Rphone = ?, Rname = ?, Rage = ?, Rsex = ?, Rcost = ?"
				+ " where Rno = ? ";
		PreparedStatement psmt;
		try {
			psmt = Main.con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(tf_reader_code.getText()));
			psmt.setString(1, tf_reader_password.getText());
			psmt.setString(2, tf_reader_phone.getText());
			psmt.setString(3, tf_reader_name.getText());
			psmt.setInt(4, Integer.parseInt(tf_reader_age.getText()));
			psmt.setString(5, tf_reader_sex.getText());
			psmt.setInt(6, Integer.parseInt(tf_reader_cost.getText()));
			psmt.setInt(7, Integer.parseInt(tf_reader_code.getText()));
			psmt.execute();
			JOptionPane.showMessageDialog(null, "�����޸ĳɹ�", "��Ϣ��ʾ", 1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�����޸�ʧ�ܣ�������Ϣ�Ƿ�Ϸ�!", "��Ϣ��ʾ", 0);
		}
		reader_Load();
	}

	// ������Ϣȷ�ϴ���
	@FXML
	public void reader_confirm_create(MouseEvent event) {
		// TODO Autogenerated
		String sql = "insert into dbo.reader values(?,?,?,?, ?,?,?,?,?)";
		PreparedStatement psmt;
		try {
			psmt = Main.con.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(tf_reader_code.getText()));
			psmt.setString(2, tf_reader_password.getText());
			psmt.setString(3, tf_reader_phone.getText());
			psmt.setString(4, tf_reader_name.getText());
			psmt.setInt(5, Integer.parseInt(tf_reader_age.getText()));
			psmt.setString(6, tf_reader_sex.getText());
			psmt.setInt(7, 0);
			psmt.setInt(8, Integer.parseInt(tf_reader_cost.getText()));
			psmt.setBoolean(9, true);
			psmt.execute();
			JOptionPane.showMessageDialog(null, "�����½��ɹ�", "��Ϣ��ʾ", 1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "�����½�ʧ�ܣ�������Ϣ�Ƿ�Ϸ�!", "��Ϣ��ʾ", 0);
		}
		reader_Load();
	}

	// �˳�ϵͳ
	@FXML
	public void exit(MouseEvent event) {
		// TODO Autogenerated
		Main.primaryStage.close();
	}

	// tab�任��ʱ�򴥷�,ע����������Ĵ�������ֻ���������Ƴ�
	// ������tab
	// ����tab
	@FXML
	public void tab_selection_change1() {
		// TODO Autogenerated
		if (tab_borrow.isSelected()) {
			// ����ͼ���Ƽ�
			BookRecommend();
			
		}
	}

	// ����tab
	@FXML
	public void tab_selection_change2() {
		// TODO Autogenerated
		if (tab_return.isSelected()) {
			returnClearAll(true);
		}
	}

	// ����tab
	@FXML
	public void tab_selection_change3() {
		// TODO Autogenerated
		if (tab_pulish.isSelected()) {
			pulish_Load(0, 0);
		}
	}

	// �鼮tab
	@FXML
	public void tab_selection_change4() {
		// TODO Autogenerated
		if (tab_book.isSelected()) {
			book_Load();
		}
	}

	// ����tab
	@FXML
	public void tab_selection_change5() {
		// TODO Autogenerated
		if (tab_reader.isSelected()) {
			reader_Load();
		}
	}
}
