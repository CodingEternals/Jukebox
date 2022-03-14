import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class JukeBoxTest {
	JukeBox juke=new JukeBox();
	Song song=new Song();
	ArrayList<Song> songlist=new ArrayList<Song>();
	
	
 	@Test
 	public void searchbyArtist() throws Exception
 	{
 		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","tanuj@1995");
		Song song1=new Song("SONG1001","Ananya","Dancing Pop","Antimatter","Deny Me","02:50");
		songlist.add(song1);
		ArrayList<Song> list=Song.searchByArtist(conn,songlist, "Ananya");	//Searching for Artist name
		assertTrue(list.size()>0);
		ArrayList<Song> list1=Song.searchByArtist(conn,songlist, "kartike");	//Searching for Artist name
		assertFalse(list1.size()>0);
 	}
 	
 	@Test
 	public void searchbygGenre() throws Exception
 	{
 		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","tanuj@1995");
		Song song1=new Song("SONG1001","Ananya","Dancing Pop","Antimatter","Deny Me","02:50");
		Song song2=new Song("SONG1003","Adele","Soul Music", "25","Hello","04:55");
		songlist.add(song1);
		songlist.add(song2);
		ArrayList<Song> list=Song.searchByGenre(conn,songlist, "Soul Music");	//Searching for Genre name
		assertTrue(list.size()>0);
 	}
 	
 	@Test
 	public void searchByAlbum() throws Exception
 	{
 		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","tanuj@1995");
		Song song1=new Song("SONG1001","Ananya","Dancing Pop","Antimatter","Deny Me","02:50");
		songlist.add(song1);
		ArrayList<Song> list1=Song.searchByAlbum(conn,songlist, "kartike");	
		assertFalse(list1.size()>0);
 	}
 	
 	@Test
 	public void searchBySongName() throws Exception
 	{
 		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","tanuj@1995");
		Song song1=new Song("SONG1001","Ananya","Dancing Pop","Antimatter","Deny Me","02:50");
		songlist.add(song1);
		ArrayList<Song> list1=Song.searchBySong(conn,songlist, "Deny Me");	
		assertTrue(list1.size()>0);
 	}
}
