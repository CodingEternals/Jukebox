import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SongInsertion 
{
	public static void main(String[] args)
	{
	try
	{
	String songId="";
	Song s=new Song();
	s.initialize();
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","tanuj@1995");
	Statement statementobj=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	ResultSet rs=statementobj.executeQuery("select SongId from Song order by songId");
	while(rs.next()) 
	{
			songId=rs.getString(1);
	}

	PreparedStatement pstm=conn.prepareStatement("insert into Song values(?,?,?,?,?,?)");	
	pstm.setString(1, "SONG"+Integer.toString(Integer.parseInt(songId.substring(4))+1));
	pstm.setString(2, s.getArtistName());
	pstm.setString(3, s.getGenre());
	pstm.setString(4, s.getAlbum());
	pstm.setString(5, s.getSongName());
	pstm.setString(6, s.getSongDuration());
	pstm.executeUpdate();
	pstm.close();
	
	}
	catch(Exception e)
	{
		System.out.println("Exception Arised"+e);
	}
	

}
}
