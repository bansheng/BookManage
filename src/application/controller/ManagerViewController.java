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

	private static String managename; // 管理员名称
	Main m;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
	private ObservableList<Integer> booksNo = FXCollections.observableArrayList(); // 添加进去的书籍编号列表
	private ObservableList<String> booksList = FXCollections.observableArrayList(); // 添加准备租借的书籍名称列表
	private ObservableList<Integer> bookscost = FXCollections.observableArrayList(); // 添加准备租借的书籍价格列表
	// 以上三个用于书籍租借

	private ObservableList<String> bookRank = FXCollections.observableArrayList(); // 书籍租借排行

	private ObservableList<Pulish> PulishData = FXCollections.observableArrayList();
	private ObservableList<Integer> PulishNo = FXCollections.observableArrayList(); // 罚单编号
	private ObservableList<Book> BookData = FXCollections.observableArrayList();
	private ObservableList<Reader> ReaderData = FXCollections.observableArrayList();

	private ArrayList<String> autoBook = new ArrayList<String>();
	private AutoCompleteTextField autoBookName; // 图书推荐

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
		
		booksList.add("---------------借书单------------");
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

	// 借书单增加
	@FXML
	public void addBook(MouseEvent event) {
		// TODO Autogenerated
		if (tf_Borrow_Rno == null || tf_Borrow_Rno.getText().equals("")) {
			return;
		} else if (!tf_Borrow_Rlimit.getText().trim().equals("无")) {
			// System.out.println("借书有限制");
			JOptionPane.showMessageDialog(null, "借书有限制，请先缴纳罚金", "消息提示", 0);
			return;
		}
		int price = 0;
		boolean isfound = false;
		String auther = "";
		if (tf_Borrow_Bookno.getText() != null && !tf_Borrow_Bookno.getText().trim().equals("")) // 编号有内容
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
		} else if (tf_Borrow_BooKname.getText() != null && !tf_Borrow_BooKname.getText().trim().equals("")) // 名称有内容
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
		// else {//输入不合法
		// JOptionPane.showMessageDialog(null, "没有找到这样的书籍", "消息提示", 0);
		// }
		int bookno = Integer.parseInt(tf_Borrow_Bookno.getText());
		if (!isfound) { // 没有查询到
			JOptionPane.showMessageDialog(null, "没有找到这样的书籍或者该书籍不在馆中!", "消息提示", 0);
			tf_Borrow_Bookno.clear();
			tf_Borrow_BooKname.clear();
			return;
		} else if (booksNo.contains(bookno)) {
			JOptionPane.showMessageDialog(null, "该书籍已经在列表中\n 不能重复添加书籍", "消息提示", 0);
			return;
		}

		booksList.add("《 " + tf_Borrow_BooKname.getText().trim() + " 》 --" + auther + "   价格: " + price + " 元");
		booksNo.add(bookno);
		bookscost.add(price);
		listview.setItems(booksList);
		tf_Borrow_amount.setText(Integer.parseInt(tf_Borrow_amount.getText()) + price + "");
		tf_Borrow_Bookno.clear();
		// tf_Borrow_BooKname.clear();
	}

	// 借书单移除
	@FXML
	public void removeBook(MouseEvent event) {
		// TODO Autogenerated
		if (booksList.isEmpty()) {
			// System.out.println("书单为空!");
			JOptionPane.showMessageDialog(null, "书单为空!", "消息提示", 0);
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
	 * 图书推荐
	 */
	@FXML
	public void BookRecommend() {
		autoBookName = AutoCompleteTextFieldBuilder.build(tf_Borrow_BooKname);
		autoBook.clear();
		bookRank.clear();
		bookRank.add("----图书热借榜----");
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
				bookRank.add("《" + rs.getString("Bname").trim() + "》--" + rs.getInt("Btimes") + "次");
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

	// 找到读者的借书限制
	@FXML
	public void findLimit(MouseEvent event) {
		int Rno = 0;
		try {
			Rno = Integer.parseInt(tf_Borrow_Rno.getText());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			return;
		}

		// 不允许编辑了
		tf_Borrow_Rno.setEditable(false);
		// 首先清除列表中的数据
		booksList.clear();
		booksNo.clear();
		booksList.clear();
		booksList.add("---------------借书单------------");
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
				JOptionPane.showMessageDialog(null, "找不到读者信息!", "消息提示", 0);
				tf_Borrow_Rno.setEditable(true);
				return;
			}

			String limits = limit == 0 ? "无" : "";
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

	// 借书，一次借多本
	@FXML
	public void BorrowBook(MouseEvent event) {
		// TODO Autogenerated
		if (booksNo.isEmpty()) {
			return;
		}

		// 查看金额是否超标
		int amount = Integer.parseInt(tf_Borrow_amount.getText());
		int limit = Integer.parseInt(tf_Borrow_Cost.getText());
		if (amount > limit) {
			JOptionPane.showMessageDialog(null, "超过允许租借的最大限额\n请适当删除书籍", "消息提示", 1);
			return;
		}

		// 首先找到租借表的下标
		int count = 0;
		try {
			String sql = "select count(*) I from dbo.borrow";
			PreparedStatement psmt;
			psmt = Main.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("I"); // 找到最大的编号
			}
			int Rno = Integer.parseInt(tf_Borrow_Rno.getText());
			int times = 0;
			for (int index = 0; index < booksNo.size(); index++) {
				// 查询书籍租借次数
				sql = "select Btimes from dbo.book where Bookno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, booksNo.get(index));
				rs = psmt.executeQuery();
				while (rs.next()) {
					times = rs.getInt("Btimes");
				}

				// 首先修改书籍状态
				sql = "update dbo.book set isIn = ?, Btimes = ? where Bookno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, false);
				psmt.setInt(2, times + 1);
				psmt.setInt(3, booksNo.get(index));
				psmt.execute();

				// 再修改租借表
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

			// 最后还要修改读者的租借限额
			sql = "update dbo.reader set Rcost = ? where Rno = ?";
			psmt = Main.con.prepareStatement(sql);
			psmt.setInt(1, limit - amount);
			psmt.setInt(2, Rno);
			psmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "借书成功!", "消息提示", 0);
		BorrowclearAll();
		BookRecommend();
	}

	// 清空租界列表信息
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
		booksList.add("---------------借书单------------");
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

	// 找到读者借的书籍的信息，包括编号，名称，租借日期
	@FXML
	public void findBorrow() {
		// TODO Autogenerated
		returnClearAll(false);
		boolean isfound = false;
		if (tf_Giveback_Bookno.getText() != null && !tf_Giveback_Bookno.getText().trim().equals("")) // 编号有内容
		{
			// 由书籍编号寻找
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
		} else if (tf_Giveback_Bookname.getText() != null && !tf_Giveback_Bookname.getText().trim().equals("")) // 名称有内容
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
			JOptionPane.showMessageDialog(null, "查询不到指定借书信息", "消息提示", 1);
	}

	// 书籍归还清空
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

	// 书籍归还
	@FXML
	public void returnBook(MouseEvent event) {
		// TODO Autogenerated
		// 建立罚单表
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
			// 书籍损坏优先级高于过期
			int pay = price;
			String info = "归还时间过期!";
			if (cb_isBroken.isSelected()) {
				info = "书籍损坏!";
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
				psmt.setString(7, info + " 《" + tf_Giveback_Bookname.getText() + "》");
				psmt.execute();
				JOptionPane.showMessageDialog(null, info + "需赔偿" + pay + "元", "消息提示", 0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 修改图书表 isin
		if (cb_isBroken.isSelected()) {
			String sql = "update dbo.book set valid = ? where Bookno = ?";
			PreparedStatement psmt;
			try {
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, false);
				psmt.setInt(2, Bookno);
				psmt.execute();
				// 删除图书
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String sql = "update dbo.book set isin = ? where Bookno = ?";
			PreparedStatement psmt;
			try {
				// 修改图书状态
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
		try { // 找到用户余额,和用户限制
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
			JOptionPane.showMessageDialog(null, "用户信息查询失败", "消息提示", 1);
			return;
		}

		// 修改读者表 limit cost
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

		// 修改借书表 bvalid
		try {
			String sql = "update dbo.borrow set Bvalid = ?, date2 = ? where Bno = ?";
			PreparedStatement psmt;
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, false);
			psmt.setString(2, dateFormat.format(new Date()));
			psmt.setInt(3, Bno);
			psmt.execute();

			JOptionPane.showMessageDialog(null, "还书成功！", "消息提示", 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "还书出现错误", "消息提示", 0);
		}
		returnClearAll(true);
	}

	@FXML
	public void Reborrow_Book(MouseEvent event) {
		// 首先还书 罚单
		// TODO Autogenerated
		// 建立罚单表
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
			System.out.println("坏了，不能续借！");
			return;
		}
		if (gap > 30) {
			System.out.println("过期，先交罚单");
			returnBook(null);
			return;
		}

		// 续借
		// 修改借书表
		try {
			String sql = "update dbo.borrow set Bvalid = ?, date2 = ? where Bno = ?";
			PreparedStatement psmt;
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, false);
			psmt.setString(2, dateFormat.format(new Date()));
			psmt.setInt(3, Bno);
			psmt.execute();

			int count = 0;
			// 找到租借表编号
			sql = "select count(*) I from dbo.borrow";
			psmt = Main.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("I"); // 找到最大的编号
			}

			// 再建立租借表
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

	// 罚单查询
	@FXML
	public void pulish_search(MouseEvent event) {
		// TODO Autogenerated
		boolean isfound = false;
		int Rno = 1;
		if (tf_Pulish_code.getText() != null && !tf_Pulish_code.getText().trim().equals("")) // 编号有内容
		{
			// 由读者编号寻找
			try {
				Rno = Integer.parseInt(tf_Pulish_code.getText());
				isfound = true;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (tf_Pulish_name.getText() != null && !tf_Pulish_name.getText().trim().equals("")) // 名称有内容
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
			JOptionPane.showMessageDialog(null, "罚单查询错误", "消息提示", 0);
	}

	// 罚单加载
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
			JOptionPane.showMessageDialog(null, "罚单加载错误", "消息提示", 0);
		}
	}

	// 罚单修改
	@FXML
	public void pulish_pay(MouseEvent event) {
		// TODO Autogenerated
		int index = table_pulish.getSelectionModel().getSelectedIndex();
		if (index == -1)
			return;
		else {
			try {
				// 修改罚单表
				String sql = "update dbo.pulishment set Pvalid = ? where Pno = ?";
				PreparedStatement psmt;
				psmt = Main.con.prepareStatement(sql);
				psmt.setBoolean(1, false);
				psmt.setInt(2, PulishNo.get(index));
				psmt.execute();

				// 首先找到读者的数据
				int limit = 0;
				int Rno = Integer.parseInt(PulishData.get(index).getRno().get());
				sql = "select Rlimit from dbo.reader where Rno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, Rno);
				ResultSet rs = psmt.executeQuery();
				while (rs.next()) {
					limit = rs.getInt("Rlimit");
				}

				// 修改读者数据
				sql = "update dbo.reader set Rlimit = ? where Rno = ?";
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, limit - 1);
				psmt.setInt(2, Rno);
				psmt.execute();

				PulishData.remove(index);
				PulishNo.remove(index);
				table_pulish.setItems(PulishData);
				JOptionPane.showMessageDialog(null, "罚单缴纳成功", "消息提示		", 0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "罚单删除失败", "消息提示", 0);
			}
		}
	}

	// 书籍列表筛选
	@FXML
	public void book_Search() {
		if (tf_Book_num.getText() != null && !tf_Book_num.getText().trim().equals("")) // 编号有内容
		{
			// 由书籍编号寻找
			int Bookno = 0;
			try {
				Bookno = Integer.parseInt(tf_Book_num.getText());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "书籍编号不合理", "消息提示", 0);
			}
			ObservableList<Book> searchBookData = FXCollections.observableArrayList();
			for (Book b : BookData) {
				if (Integer.parseInt(b.getCode().get()) == Bookno) {
					searchBookData.add(b);
				}
			}
			table_Book.setItems(searchBookData);

		} else if (tf_Book_name.getText() != null && !tf_Book_name.getText().trim().equals("")) // 名称有内容
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

	// 书籍列表更新
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

	// 新书入库了解一下
	@FXML
	public void newBookIn(MouseEvent event) {
		m.showBookview(this);
	}

	// 书籍文件导出
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
			FileOutputStream fos = new FileOutputStream(file);// 这里如果文件不存在会自动创建文件
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");// 和读取一样这里是转化的是字节和字符流
			BufferedWriter bw = new BufferedWriter(osw);// 这里是写入缓冲区

			for (Book b : BookData) {
				bw.write(b.getName().get() + "\n");
				bw.write(b.getAuther().get() + "\n");
				bw.write(b.getPrice().get() + "\n");
				bw.write(b.getType().get() + "\n");
				bw.write(b.getDes().get() + "\n");
			}
			JOptionPane.showMessageDialog(null, "导出书籍成功", "消息提示", 1);
			bw.close();// 和上面一样 这里后打开的先关闭 先打开的后关闭
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

		if (file.exists()) { // 文件存在
			try (FileInputStream fis = new FileInputStream(file)) {// 文件输入流 这是字节流

				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				// inputstreamReader是一个字节流，将字节流和字符流转化的时候，就需要制定一个编码方式，不然就会乱码
				BufferedReader br = new BufferedReader(isr);// 字符缓冲区

				// 一次插入所有书籍，注意重复书籍,首先把列表里面书籍的作者和书名全部记录下来
				// 作为比对重复书籍的信息
				ObservableList<String> newBookname = FXCollections.observableArrayList();
				ObservableList<String> newBookauther = FXCollections.observableArrayList();

				for (Book b : BookData) {
					newBookname.add(b.getName().get());
					newBookauther.add(b.getAuther().get());
				}

				int index = 0; // 找到插入图书的起始下标
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
					// 允许添加一样的书籍

					// if(newBookname.contains(Name) && newBookauther.contains(Auther)
					// && newBookname.indexOf(Name) == newBookauther.indexOf(Auther)) {
					// // 完全相同
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
				JOptionPane.showMessageDialog(null, "导入书籍成功", "消息提示", 1);
				book_Load();

				br.close();// 最后将各个线程关闭
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

	// 旧书出库了解一下
	public void oldBookOut(MouseEvent event) {
		int index = table_Book.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			return;
		}
		if (table_Book.getItems().get(index).getIsIn().get().equals("离馆")) {
			JOptionPane.showMessageDialog(null, "未归还的书籍不能删除", "消息提示", 0);
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
			// 重新加载书籍
			book_Load();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 读者列表加载
	public void reader_Load() {
		// TODO Autogenerated
		ReaderData.clear();
		reader_ClearAll();
		reader_Modify(false); // 不允许修改

		// 按钮使能控制
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
			JOptionPane.showMessageDialog(null, "读者信息加载失败", "消息提示", 0);
		}
	}

	// 读者信息清空
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

	// 读者信息允许修改函数
	public void reader_Modify(boolean permit) {
		tf_reader_name.setEditable(permit);
		tf_reader_password.setEditable(permit);
		tf_reader_phone.setEditable(permit);
		tf_reader_age.setEditable(permit);
		tf_reader_sex.setEditable(permit);
		tf_reader_cost.setEditable(permit);
	}

	// 读者信息查询
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

				String limits = limit == 0 ? "无" : "";
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

	// 读者信息更新
	@FXML
	public void reader_update(MouseEvent event) {
		// TODO Autogenerated
		int index = table_reader.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			return;
		}
		reader_search(null); // 首先查询
		reader_Modify(true);
		// 按钮使能控制
		btn_confirm_modify.setDisable(false);
		btn_confirm_create.setDisable(true);
		btn_confirm_delete.setDisable(true);
		btn_search.setDisable(true);
		btn_delete.setDisable(true);
		btn_create.setDisable(true);
		btn_modify.setDisable(true);
	}

	// 取消读者修改或者新建
	@FXML
	public void reader_cancle(MouseEvent event) {
		// TODO Autogenerated
		reader_Load();
	}

	// 读者创建
	@FXML
	public void reader_create(MouseEvent event) {
		// 按钮使能控制
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

	// 确认删除读者信息
	@FXML
	public void reader_delete(MouseEvent event) {
		// TODO Autogenerated
		// 按钮使能控制
		btn_confirm_modify.setDisable(true);
		btn_confirm_create.setDisable(true);
		btn_confirm_delete.setDisable(false);
		btn_search.setDisable(true);
		btn_delete.setDisable(true);
		btn_create.setDisable(true);
		btn_modify.setDisable(true);

	}

	// 读者删除
	@FXML
	public void reader_confirm_delete(MouseEvent event) {
		// TODO Autogenerated
		int index = table_reader.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			return;
		}

		try {
			int Rno = Integer.parseInt(ReaderData.get(index).getRno().get());
			// 首先交清pulishment
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
				System.out.println("罚单还有未缴纳的!");
				JOptionPane.showMessageDialog(null, "请先缴纳罚单", "消息提示", 0);
				book_Load();
				return;
			}

			// 更新借书表
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
				System.out.println("图书还有未归还的!");
				JOptionPane.showMessageDialog(null, "请先归还图书或者选择损坏图书！", "消息提示", 0);
				book_Load();
				return;
			}

			// 更新读者信息
			sql = "update dbo.reader set Rvalid = ? where Rno = ?";
			psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, false);
			psmt.setInt(2, Rno);
			psmt.execute();

			JOptionPane.showMessageDialog(null, "读者删除成功", "消息提示", 1);
			tab_selection_change5();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("还书表删除失败");
		}

	}

	// 确认更新读者信息
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
			JOptionPane.showMessageDialog(null, "读者修改成功", "消息提示", 1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "读者修改失败，请检查信息是否合法!", "消息提示", 0);
		}
		reader_Load();
	}

	// 读者信息确认创建
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
			JOptionPane.showMessageDialog(null, "读者新建成功", "消息提示", 1);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "读者新建失败，请检查信息是否合法!", "消息提示", 0);
		}
		reader_Load();
	}

	// 退出系统
	@FXML
	public void exit(MouseEvent event) {
		// TODO Autogenerated
		Main.primaryStage.close();
	}

	// tab变换的时候触发,注意这个函数的触发条件只针对移入和移出
	// 的两个tab
	// 借书tab
	@FXML
	public void tab_selection_change1() {
		// TODO Autogenerated
		if (tab_borrow.isSelected()) {
			// 进行图书推荐
			BookRecommend();
			
		}
	}

	// 还书tab
	@FXML
	public void tab_selection_change2() {
		// TODO Autogenerated
		if (tab_return.isSelected()) {
			returnClearAll(true);
		}
	}

	// 罚单tab
	@FXML
	public void tab_selection_change3() {
		// TODO Autogenerated
		if (tab_pulish.isSelected()) {
			pulish_Load(0, 0);
		}
	}

	// 书籍tab
	@FXML
	public void tab_selection_change4() {
		// TODO Autogenerated
		if (tab_book.isSelected()) {
			book_Load();
		}
	}

	// 读者tab
	@FXML
	public void tab_selection_change5() {
		// TODO Autogenerated
		if (tab_reader.isSelected()) {
			reader_Load();
		}
	}
}
