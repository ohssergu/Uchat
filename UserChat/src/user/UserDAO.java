package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	 DataSource dataSource;
	 
	 public UserDAO() {
		 try {
			 InitialContext initContext = new InitialContext();
			 Context envContext = (Context) initContext.lookup("java:/comp/env");
			 dataSource = (DataSource) envContext.lookup("jdbc/UserChat");
		 }	catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
	 
	 public int login(String userID, String userPassword) {
		 Connection conn= null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 String SQL = "SELECT * FROM USER WHERE userID = ?";
		 try {
			 conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, userID);
			 rs = pstmt.executeQuery();
			 if (rs.next()) {
				 if(rs.getString("userPassword").equals(userPassword)) {
					 return 1; //�α��ο� ����
				 }
				 return 2; //��й�ȣ�� Ʋ��
			 } else {
				 return 0; // �ش� ����ڰ� �������� ����
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 try {
				 if(rs != null) rs.close();
				 if(pstmt != null) pstmt.close();
				 if(conn != null) conn.close();
			 }	catch (Exception e) {
				 e.printStackTrace();
			 }
		 }
		 return -1; // �����ͺ��̽� ����
	 }
	 
	 public int registerCheck(String userID) {
		 Connection conn= null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 String SQL = "SELECT * FROM USER WHERE userID = ?";
		 try {
			 conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, userID);
			 rs = pstmt.executeQuery();
			 if (rs.next() || userID.equals("")) {
				 return 0; // �̹� �����ϴ� ȸ��
			 } else {
				 return 1; //���� ������ ȸ�� ���̵�
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 try {
				 if(rs != null) rs.close();
				 if(pstmt != null) pstmt.close();
				 if(conn != null) conn.close();
			 }	catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		 return -1; // �����ͺ��̽� ����
	 }
	 
	 public int register(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile, String userPhone) {
		 Connection conn= null;
		 PreparedStatement pstmt = null;
		 String SQL = "INSERT INTO USER VALUES (?,?,?,?,?,?,?,?)";
		 try {
			 conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, userID);
			 pstmt.setString(2, userPassword);
			 pstmt.setString(3, userName);
			 pstmt.setInt(4, Integer.parseInt(userAge));
			 pstmt.setString(5, userGender);
			 pstmt.setString(6, userEmail);
			 pstmt.setString(7, userProfile);
			 pstmt.setString(8, userPhone);
			 return pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 try {
				 if(pstmt != null) pstmt.close();
				 if(conn != null) conn.close();
			 }	catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		 return -1; // �����ͺ��̽� ����
	 }
	 
	 public UserDTO getUser(String userID) {
		 UserDTO user = new UserDTO();
		 Connection conn= null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 String SQL = "SELECT * FROM USER WHERE userID = ?";
		 try {
			 conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, userID);
			 rs = pstmt.executeQuery();
			 if (rs.next()) {
				 user.setUserID(userID);
				 user.setUserPassword(rs.getString("userPassword"));
				 user.setUserName(rs.getString("userName"));
				 user.setUserAge(rs.getInt("userAge"));
				 user.setUserGender(rs.getString("userGender"));
				 user.setUserEmail(rs.getString("userEmail"));
				 user.setUserProfile(rs.getString("userProfile"));
				 user.setUserPhone(rs.getString("userPhone"));
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 try {
				 if(rs != null) rs.close();
				 if(pstmt != null) pstmt.close();
				 if(conn != null) conn.close();
			 }	catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		 return user;
	 }
	 
	 public int update(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile, String userPhone) {
		 Connection conn= null;
		 PreparedStatement pstmt = null;
		 String SQL = "UPDATE USER SET userPassword = ?, userName = ?, userAge = ?, userGender = ?, userEmail = ? , userProfile = ? , userPhone = ? WHERE userID = ?";
		 try {
			 conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, userPassword);
			 pstmt.setString(2, userName);
			 pstmt.setInt(3, Integer.parseInt(userAge));
			 pstmt.setString(4, userGender);
			 pstmt.setString(5, userEmail);
			 pstmt.setString(6, userProfile);
			 pstmt.setString(7, userPhone);
			 pstmt.setString(8, userID);
			 return pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 try {
				 if(pstmt != null) pstmt.close();
				 if(conn != null) conn.close();
			 }	catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		 return -1; // �����ͺ��̽� ����
	 }
	 
	 public int delete(String userID) {
		 Connection conn= null;
		 PreparedStatement pstmt = null;
		 String SQL = "delete from user where userid = ?";
		 try {
			 conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, userID);
			 return pstmt.executeUpdate();
			 
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 try {
				 if(pstmt != null) pstmt.close();
				 if(conn != null) conn.close();
			 }	catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		 return -1; 
	 }
	 
	 public int profile(String userID, String userProfile) {
		 Connection conn= null;
		 PreparedStatement pstmt = null;
		 String SQL = "UPDATE USER SET userProfile = ? WHERE userID = ?";
		 try {
			 conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, userProfile);
			 pstmt.setString(2, userID);
			 return pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 try {
				 if(pstmt != null) pstmt.close();
				 if(conn != null) conn.close();
			 }	catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		 return -1; // �����ͺ��̽� ����
	 }
	 
	 public String getProfile(String userID) {
		 Connection conn= null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 String SQL = "SELECT userProfile FROM USER WHERE userID = ?";
		 try {
			 conn = dataSource.getConnection();
			 pstmt = conn.prepareStatement(SQL);
			 pstmt.setString(1, userID);
			 rs = pstmt.executeQuery();
			 if (rs.next()) {
				if(rs.getString("userProfile").equals("")) {
					return "http://localhost:8080/UserChat/images/icon.png";
				}
				return "http://localhost:8080/UserChat/upload/" + rs.getString("userProfile");
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }finally {
			 try {
				 if(rs != null) rs.close();
				 if(pstmt != null) pstmt.close();
				 if(conn != null) conn.close();
			 }	catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		 return "http://localhost:8080/UserChat/images/icon.png";
	 }
}












