package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import application.controller.*;

/**
 * @author DYD
 *
 */
public class Main extends Application {

	public static java.sql.Connection con;// 数据库建立的连接
	public static Stage primaryStage; // 主界面stage
	public static Stage findstage, regstage, bookstage; //找回密码
	public static Stage fileStage = new Stage();
	
	/**
	 *  主函数，关闭关闭数据库连接
	 */
	public static void main(String[] args) {
		launch(args);
		try {
			if(con==null) return;
			if (!con.isClosed()) {
				con.close();
				System.out.println("断开数据库连接!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * 显示登录主界面
	 */
	public void showLoginview() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/login.fxml"));
			AnchorPane loginLayout = (AnchorPane) loader.load();
			
			// Show the scene containing the login layout.
			Scene scene = new Scene(loginLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			LoginController controller = loader.getController();
			controller.setMain(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示注册界面
	 */
	public void showRegsterview() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/register.fxml"));
			AnchorPane regLayout = (AnchorPane) loader.load();

			// Show the scene containing the login layout.
			Scene scene = new Scene(regLayout);
			regstage = new Stage();
			regstage.setTitle("管理人员注册");
			regstage.setScene(scene);
			regstage.show();
			
			RegisterController controller = loader.getController();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示找回密码界面
	 */
	public void showfindview() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/findword.fxml"));
			AnchorPane regLayout = (AnchorPane) loader.load();

			// Show the scene containing the login layout.
			Scene scene = new Scene(regLayout);
			findstage = new Stage();
			findstage.setTitle("找回密码");
			findstage.setScene(scene);
			findstage.show();
			
			FindwordController controller = loader.getController();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示找回密码界面
	 */
	public void showBookview(ManagerViewController mc) {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/newbook.fxml"));
			AnchorPane regLayout = (AnchorPane) loader.load();

			// Show the scene containing the login layout.
			Scene scene = new Scene(regLayout);
			bookstage = new Stage();
			bookstage.setTitle("新书入库");
			bookstage.setScene(scene);
			bookstage.show();
			
			NewBookController controller = loader.getController();
			controller.setMC(mc);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示读者界面
	 */
	public void showReaderview(int Rno) {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/readerview.fxml"));
			AnchorPane readerLayout = (AnchorPane) loader.load();

			// Show the scene containing the login layout.
			Scene scene = new Scene(readerLayout);
			primaryStage.close();
			primaryStage.setScene(scene);
			primaryStage.show();
			
			ReaderViewController controller = loader.getController();
			controller.setRno(Rno);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示管理员界面
	 */
	public void showManagerview(String name) {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/managerview.fxml"));
			AnchorPane managerLayout = (AnchorPane) loader.load();

			// Show the scene containing the login layout.
			Scene scene = new Scene(managerLayout);
			primaryStage.close();
			primaryStage.setScene(scene);
			primaryStage.show();
			
			ManagerViewController controller = loader.getController();
			controller.setManageName(name);
			controller.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化数据库连接，应用关闭的时候释放
	 */
	public void sql_initial() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String strCon = "jdbc:sqlserver://localhost:1433;databaseName=book";
			String strUserName = "sa"; // 数据库的用户名称
			String strPWD = "808511"; // 数据库的密码
			con = java.sql.DriverManager.getConnection(strCon, strUserName, strPWD);
			if(con==null) {
				System.out.println("数据库连接失败!");
				primaryStage.close();
			}
			System.out.println("已顺利连接到数据库。");
		}
		// 捕获异常并进行处理
		catch (java.lang.ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		} catch (java.sql.SQLException se) {
			System.out.println(se.getMessage());
		}
	}
	
	public static String addzero(int index) {
		String s = "" + index;
		int a = 6-s.length();
		while((a--) != 0) {
			s = "0"+s;
		}
		return s;
	}

	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("图书管理系统");
		sql_initial();
		showLoginview();
	}
}
