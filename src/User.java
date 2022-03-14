import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class User 
{
	String UserID;
	String UserName;
	String Password;
	String Email;
	
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	public User(String userID, String userName, String password, String email) {
	
		UserID = userID;
		UserName = userName;
		Password = password;
		Email = email;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [UserID=" + UserID + ", UserName=" + UserName + ", Password=" + Password + ", Email=" + Email
				+ "]";
	}
	
	public void initialize()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter the User ID (Example:USER1000):");
		UserID=sc.next();
		
		System.out.println("Enter the UserName:");
		UserName=sc.next();
		
		System.out.println("Enter Password:");
		Password=sc.next();
		
		System.out.println("Enter the Email");
		Email=sc.next();
		
		
		sc.close();
	}
	
	public static int signup(Connection conn, User user) throws SQLException
	{
		PreparedStatement psmt=conn.prepareStatement("insert into Userr values(?,?,?,?)");
		
		psmt.setString(1, user.getUserID());
		psmt.setString(2, user.getUserName());
		psmt.setString(3, user.getPassword());
		psmt.setString(4, user.getEmail());
		int roweff=psmt.executeUpdate();
		psmt.close();
		
		return roweff;
	}

}
