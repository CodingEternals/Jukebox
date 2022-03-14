import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class UserPlaylist {
	String PlyID;
	String PlaylistName;
	String UserID;

	public String getUserPlyID() {
		return PlyID;
	}

	public void setUserPlyID(String userPlyID) {
		PlyID = userPlyID;
	}

	public String getPlaylistName() {
		return PlaylistName;
	}

	public void setPlaylistName(String playlistName) {
		PlaylistName = playlistName;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public UserPlaylist(String userPlyID, String playlistName, String userID) {

		PlyID = userPlyID;
		PlaylistName = playlistName;
		UserID = userID;
	}

	public UserPlaylist() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserPlaylist [UserPlyID=" + PlyID + ", PlaylistName=" + PlaylistName + ", UserID=" + UserID + "]";
	}
	
	
	public void initialize()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter the User Playlist Name:");
		PlaylistName=sc.nextLine();
		sc.close();
	}
	

	public static int createUserPlaylist(Connection conn, String un) throws SQLException
	{
		
		String uplyId="";
		UserPlaylist uply=new UserPlaylist();
		uply.initialize();
		System.out.println(uply.getPlaylistName());
		Statement statementobj=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=statementobj.executeQuery("select PlyID from Userplaylist order by PlyID ");
		while(rs.next())
		{
		uplyId=rs.getString(1); //sop-> UPLY1001-UPLY1002
		}
		System.out.println(uplyId);
		PreparedStatement pstm=conn.prepareStatement("insert into Userplaylist values(?,?,?)");
		pstm.setString(1,"UPLY"+Integer.toString(Integer.parseInt(uplyId.substring(4))+1));
		pstm.setString(2, uply.getPlaylistName());
		pstm.setString(3, un);
		int roweff=pstm.executeUpdate();
		pstm.close();
		
		return roweff;
	
	}

	
	public static void DisplayUserPlaylsit(Connection conn, ArrayList<UserPlaylist> upl) throws SQLException
	{
		Statement statementobj=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=statementobj.executeQuery("select *from Userplaylist");
		System.out.println("\n***********************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-20s %-20s","ID","SongID","PodcastID","UserID","PlyID");
		System.out.println("\n***********************************************************************************************************************\n");
		while(rs.next())
		{
			System.out.printf("%-20s %-20s %-20s\n",rs.getString(1),rs.getString(2),rs.getString(3));
		}
	}

}
