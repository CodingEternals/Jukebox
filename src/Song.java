import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Song 
{
	 AudioInputStream inputStream;
	 static Clip clip;
	 String status;
	 long currentframe;
	
	String SongID;
	String ArtistName;
	String Genre;
	String Album;
	String SongName;
	String SongDuration;
	
	public String getSongID() {
		return SongID;
	}
	public void setSongID(String songID) {
		SongID = songID;
	}
	public String getArtistName() {
		return ArtistName;
	}
	public void setArtistName(String artistName) {
		ArtistName = artistName;
	}
	public String getGenre() {
		return Genre;
	}
	public void setGenre(String genre) {
		Genre = genre;
	}
	public String getAlbum() {
		return Album;
	}
	public void setAlbum(String album) {
		Album = album;
	}
	public String getSongName() {
		return SongName;
	}
	public void setSongName(String songName) {
		SongName = songName;
	}
	public String getSongDuration() {
		return SongDuration;
	}
	public void setSongDuration(String songDuration) {
		SongDuration = songDuration;
	}
	
	public Song(String songID, String artistName, String genre, String album, String songName, String songDuration) {

		SongID = songID;
		ArtistName = artistName;
		Genre = genre;
		Album = album;
		SongName = songName;
		SongDuration = songDuration;
	}
	public Song() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
	return "SongID=" + SongID + ", ArtistName=" + ArtistName + ", Genre=" + Genre + ", Album=" + Album
			+ ", SongName=" + SongName + ", SongDuration=" + SongDuration;
	}
	
	public void initialize()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter the ArtistName:");
		ArtistName=sc.nextLine();
		
		System.out.println("Enter the Song Genre:");
		Genre=sc.nextLine();
		
		System.out.println("Enter the Album:");
		Album=sc.nextLine();
		
		System.out.println("Enter the Song Name:");
		SongName=sc.nextLine();
		
		System.out.println("Enter the Song Duration:");
		SongDuration=sc.nextLine();
		
		sc.close();
		
	}
	public static int songInsertion(Connection conn)
	{
		int roweff = 0;
		try
		{
		String songId="";
		Song s=new Song();
		s.initialize();
		
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
		roweff=pstm.executeUpdate();
		pstm.close();
		 
		}
		catch(Exception e)
		{
			System.out.println("Exception Arised"+e);
		}
		return roweff;
	}
	
	
	public static void displaySong(Connection conn) throws SQLException 
	{
		Statement statement=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=statement.executeQuery("select * from Song");
		System.out.println("\n***************************************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-30s %-30s %-15s","SongID","Artist","Genre","Album","Song Name","Duration");
		System.out.println("\n***************************************************************************************************************************************");
		while(rs.next()) {
			System.out.printf("%-20s %-20s %-20s %-30s %-30s %-15s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
		}
	}
	
	public static ArrayList<Song> searchByArtist(Connection conn,ArrayList<Song> sg, String name) throws SQLException 
	{
		ArrayList<Song> list=new ArrayList<>();
		System.out.println("\n***************************************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-30s %-30s %-15s","SongID","Artist","Genre","Album","Song Name","Duration");
		System.out.println("\n***************************************************************************************************************************************");
		sg.stream().filter(p->p.getArtistName().contains(name))
		.collect(Collectors.toList())
		.forEach(s->{System.out.printf("\n%-20s %-20s %-20s %-30s %-30s %-15s\n",s.getSongID(),s.getArtistName(),s.getGenre(),s.getAlbum(),s.getSongName(),s.getSongDuration()); list.add(s);});
		
		return list;
		
	}
	public static ArrayList<Song> searchByGenre(Connection conn, ArrayList<Song> sg, String genre) throws SearchNotFoundException 
	{
		ArrayList<Song> list1=new ArrayList<>();
		System.out.println("\n***************************************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-30s %-30s %-15s","SongID","Artist","Genre","Album","Song Name","Duration");
		System.out.println("\n***************************************************************************************************************************************");
		sg.stream().filter(p->p.getGenre().contains(genre))
		.forEach(s->{System.out.printf("%-20s %-20s %-20s %-30s %-30s %-15s\n",s.getSongID(),s.getArtistName(),s.getGenre(),s.getAlbum(),s.getSongName(),s.getSongDuration()); list1.add(s);});
		
		return list1;
		
	}
	public static  ArrayList<Song> searchByAlbum(Connection conn, ArrayList<Song> sg,String album) throws SearchNotFoundException {
	
		ArrayList<Song> list2=new ArrayList<>();
		System.out.println("\n***************************************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-30s %-30s %-15s","SongID","Artist","Genre","Album","Song Name","Duration");
		System.out.println("\n***************************************************************************************************************************************");
		sg.stream().filter(p->p.getAlbum().contains(album))
		.forEach(s->{System.out.printf("%-20s %-20s %-20s %-30s %-30s %-15s\n",s.getSongID(),s.getArtistName(),s.getGenre(),s.getAlbum(),s.getSongName(),s.getSongDuration()); list2.add(s);});
		
		return list2;
	}
	public static  ArrayList<Song> searchBySong(Connection conn, ArrayList<Song> sg, String songName) throws SearchNotFoundException {
		
			ArrayList<Song> list3=new ArrayList<>();
			System.out.println("\n***************************************************************************************************************************************");
			System.out.printf("\n%-20s %-20s %-20s %-30s %-30s %-15s","SongID","Artist","Genre","Album","Song Name","Duration");
			System.out.println("\n***************************************************************************************************************************************");
			sg.stream().filter(p->p.getSongName().contains(songName))
			.forEach(s->{System.out.printf("%-20s %-20s %-20s %-30s %-30s %-15s\n",s.getSongID(),s.getArtistName(),s.getGenre(),s.getAlbum(),s.getSongName(),s.getSongDuration()); list3.add(s);});
		
		
		return list3;
	}
	
	
	public void playTheMusic() {
		try {
			
			@SuppressWarnings("resource")
			Scanner scanObj = new Scanner(System.in);
			System.out.print("\n\t\tEnter SongID:  ");
			String songID = scanObj.next();

			System.out.print("\t\tPlaying");
			String url = "E:\\" + songID + ".wav";
			clip = AudioSystem.getClip();
			File file = new File(url);

			inputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());

			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
			status = "play";

			while (true) {
				System.out.print("\n\t\t1. Pause");
				System.out.print("\t\t2. Resume");
				System.out.print("\t\t3. Restart");
				System.out.print("\t\t4. Stop");

				int choice = scanObj.nextInt();
				operations(choice, url);
				if (choice == 4) {
					System.out.print("\n\t\tContinue?(Y/N) ");
					String plays = scanObj.next();
					if (plays.equalsIgnoreCase("Y")) {
						playTheMusic();
					} else if (plays.equalsIgnoreCase("N")) {
						break;
					} else {
						System.out.print("\n\t\tEnter 3");
						continue;
					}
				}
			}
		} catch (Exception e) {
			System.out.print("\n\t\tThankyou for using Jukebox");
		}
	}
	
	public void operations(int choice, String url) 
	{
		try {
			switch (choice) {
			case 1:
				this.currentframe = Song.clip.getMicrosecondPosition();
				clip.stop();
				status = "paused";
				break;
			case 2:

				clip.setMicrosecondPosition(currentframe);
				clip.start();
				status = "play";
				break;

			case 3:
				clip.stop();
				clip.close();

				clip = AudioSystem.getClip();
				File file = new File(url);

				inputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());

				clip.open(inputStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				clip.start();
				status = "play";
				break;
			case 4:
				currentframe = 0L;
				clip.stop();
				clip.close();
				break;

			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
}
