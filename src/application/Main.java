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

	public static java.sql.Connection con;// ���ݿ⽨��������
	public static Stage primaryStage; // ������stage
	public static Stage findstage, regstage, bookstage; //�һ�����
	public static Stage fileStage = new Stage();
	
	/**
	 *  ���������رչر����ݿ�����
	 */
	public static void main(String[] args) {
		launch(args);
		try {
			if(con==null) return;
			if (!con.isClosed()) {
				con.close();
				System.out.println("�Ͽ����ݿ�����!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * ��ʾ��¼������
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
	 * ��ʾע�����
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
			regstage.setTitle("������Աע��");
			regstage.setScene(scene);
			regstage.show();
			
			RegisterController controller = loader.getController();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʾ�һ��������
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
			findstage.setTitle("�һ�����");
			findstage.setScene(scene);
			findstage.show();
			
			FindwordController controller = loader.getController();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʾ�һ��������
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
			bookstage.setTitle("�������");
			bookstage.setScene(scene);
			bookstage.show();
			
			NewBookController controller = loader.getController();
			controller.setMC(mc);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʾ���߽���
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
	 * ��ʾ����Ա����
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
	 * ��ʼ�����ݿ����ӣ�Ӧ�ùرյ�ʱ���ͷ�
	 */
	public void sql_initial() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String strCon = "jdbc:sqlserver://localhost:1433;databaseName=book";
			String strUserName = "sa"; // ���ݿ���û�����
			String strPWD = "808511"; // ���ݿ������
			con = java.sql.DriverManager.getConnection(strCon, strUserName, strPWD);
			if(con==null) {
				System.out.println("���ݿ�����ʧ��!");
				primaryStage.close();
			}
			System.out.println("��˳�����ӵ����ݿ⡣");
		}
		// �����쳣�����д���
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
		Main.primaryStage.setTitle("ͼ�����ϵͳ");
		sql_initial();
		showLoginview();
	}
}
