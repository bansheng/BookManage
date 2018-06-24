package application.controller;

import javafx.fxml.FXML;

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

import javax.swing.JOptionPane;

import application.Main;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.PasswordField;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {
	@FXML
	private PasswordField tf_password;
	@FXML
	private TextField tf_acount;
	@FXML
	private Button btn_find;
	@FXML
	private Button btn_login;
	@FXML
	private Button btn_register;
	@FXML
	private CheckBox cb_manage;
	
	Main m;
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize() {
		File file = new File("my.ini");
		//是否存在
		if(file.exists()){
			try (FileInputStream fis = new FileInputStream(file)) {//文件输入流 这是字节流
			
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
				//inputstreamReader是一个字节流，将字节流和字符流转化的时候，就需要制定一个编码方式，不然就会乱码
				BufferedReader br = new BufferedReader(isr);//字符缓冲区
				
				tf_acount.setText(br.readLine());
				tf_password.setText(br.readLine());
				if("1".equals(br.readLine())) {
					cb_manage.setSelected(true);
				}
				else cb_manage.setSelected(false);
				
				br.close();//最后将各个线程关闭
				isr.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Event Listener on PasswordField[#tf_password].onKeyPressed
	@FXML
	public void tryLoginKey(KeyEvent event) {
		// TODO Autogenerated
		if(event.getCode() == KeyCode.ENTER) {
			tryLogin(null);
		}
	}
	// Event Listener on Button[#btn_find].onMouseClicked
	@FXML
	public void findPassword(MouseEvent event) {
		// TODO Autogenerated
		m.showfindview();
	}
	// Event Listener on Button[#btn_login].onMouseClicked
	@FXML
	public void tryLogin(MouseEvent event) {
		// TODO Autogenerated
		String acount = tf_acount.getText();
		int code = 1;
		try {
			code = Integer.parseInt(acount);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String password = tf_password.getText();
		
		PreparedStatement psmt;
		if(cb_manage.isSelected()) {
			
			String sql = "select * from dbo.manager where mcode = ?";
			try {
//				m.sql_initial("manager", "111402");
				
				psmt = Main.con.prepareStatement(sql);
				psmt.setString(1, acount);
				// 执行SQL语句
				ResultSet rs = psmt.executeQuery();

				while (rs.next()) {
					if (rs.getString("mpassword").equals(password)) {
						// 登录成功
						File newfile = new File("my.ini");
					    FileOutputStream fos = new FileOutputStream(newfile);//这里如果文件不存在会自动创建文件
					    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//和读取一样这里是转化的是字节和字符流
					    BufferedWriter bw = new BufferedWriter(osw);//这里是写入缓冲区
	
					    bw.write(acount + "\n");//写入字符串
					    bw.write(password + "\n");//写入字符串
					    bw.write(1 + "\n");//写入是否选中
	
					    bw.close();//和上面一样 这里后打开的先关闭 先打开的后关闭
					    osw.close();
					    fos.close();
					    System.out.println("配置文件已经修改");
					    
					    m.showManagerview(rs.getString("Mname"));
						System.out.println("进入管理员界面!");
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "编号或者登录口令错误!", "消息提示", 0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "编号或者登录口令错误!", "消息提示", 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			String sql = "select * from dbo.reader where Rno = ?";
			try {
				
//				m.sql_initial("ding", "808511");
				
				psmt = Main.con.prepareStatement(sql);
				psmt.setInt(1, code);
				// 执行SQL语句
				ResultSet rs = psmt.executeQuery();

				while (rs.next()) {
					if (rs.getString("Rpassword").trim().equals(password)) {
						// 登录成功
						File newfile = new File("my.ini");
					    FileOutputStream fos = new FileOutputStream(newfile);//这里如果文件不存在会自动创建文件
					    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//和读取一样这里是转化的是字节和字符流
					    BufferedWriter bw = new BufferedWriter(osw);//这里是写入缓冲区
	
					    bw.write(acount + "\n");//写入字符串
					    bw.write(password + "\n");//写入字符串
					    bw.write(0 + "\n");//写入是否选中
	
					    bw.close();//和上面一样 这里后打开的先关闭 先打开的后关闭
					    osw.close();
					    fos.close();
					    System.out.println("配置文件已经修改");
					    
					    m.showReaderview(rs.getInt("Rno"));
						System.out.println("进入读者界面!");
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "编号或者登录口令错误!", "消息提示", 0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "编号或者登录口令错误!", "消息提示", 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// Event Listener on Button[#btn_register].onMouseClicked
	@FXML
	public void register(MouseEvent event) {
		// TODO Autogenerated
		m.showRegsterview();
	}
	
	public void setMain(Main m) {
		this.m = m;
	}
}
