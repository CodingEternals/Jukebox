import java.io.File;
import java.io.IOException;
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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Playlist {
	
	 AudioInputStream inputStream;
	 static Clip clip;
	 String status;
	 long currentframe;
	
	int ID;
	String SongID;
	String PodcastID;
	String UserID;
	String PlyID;
	
	Playlist(){}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


	public String getSongID() {
		return SongID;
	}

	public void setSongID(String songID) {
		SongID = songID;
	}

	public String getPodcastID() {
		return PodcastID;
	}

	public void setPodcastID(String podcastID) {
		PodcastID = podcastID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getPlyID() {
		return PlyID;
	}

	public void setPlyID(String plyID) {
		PlyID = plyID;
	}

	
	public Playlist(int iD, String songID, String podcastID, String userID, String plyID) {
		super();
		ID = iD;
		SongID = songID;
		PodcastID = podcastID;
		UserID = userID;
		PlyID = plyID;
	}

	@Override
	public String toString() {
		return "Playlist [ID=" + ID + ", SongID=" + SongID + ", PodcastID=" + PodcastID + ", UserID=" + UserID
				+ ", PlyID=" + PlyID + "]";
	}

	public void initialize()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter SongID:");
		SongID=sc.next();
		
		System.out.println("Enter PodcastID:");
		PodcastID=sc.next();
		
		sc.close();
	}
	
	public static void Display(Connection conn) throws SQLException
	{
		Statement statement=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet res=statement.executeQuery("select* from Playlist");
		System.out.println("\n***********************************************************************************************************************");
		System.out.printf("%-20s %-20s %-20s %-20s %-20s","ID","SongID","PodcastID","UserID","PlyID");
		System.out.println("\n***********************************************************************************************************************");
		while(res.next())
		{
			System.out.printf("%-20s %-20s %-20s %-20s %-20s",res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5));
		}
	}

	public static int addSongtoCreatedPlaylist(Connection conn, String un, String playlistId) throws SQLException 
	{
		Playlist pla=new Playlist();
		pla.initialize();
		
		PreparedStatement pstm=conn.prepareStatement("insert into Playlist (SongID,PodcastID,UserID,PlyID) values(?,?,?,?)");
		pstm.setString(1,pla.getSongID());
		pstm.setString(2,pla.getPodcastID());
		pstm.setString(3, un);
		pstm.setString(4, playlistId);
		int roweff=pstm.executeUpdate();
		pstm.close();
		
		return roweff;
	}

	public void playPlayList(Connection conn,ArrayList<Playlist> ply) throws SQLException, LineUnavailableException, UnsupportedAudioFileException, IOException 
	{
		@SuppressWarnings("resource")
		Scanner sc=new Scanner(System.in);
		ArrayList<Playlist> songsList=new ArrayList<>();
		
		System.out.println("Enter UserPlaylsit ID:");	
		String plyid=sc.nextLine();
		System.out.println("SongList");
		System.out.println("*********");
		ply.stream().filter(p->p.getPlyID().equals(plyid))
		.collect(Collectors.toList())
		.forEach(s->{System.out.println(s.getSongID()); songsList.add(s);});
		
		for (int i = 0; i < songsList.size();) {

			@SuppressWarnings("resource")
			Scanner scanObj = new Scanner(System.in);
			System.out.print("\t\tPlaying");

			String url = "E:\\" + songsList.get(i).getSongID() + ".wav";
			System.out.println(url);
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
				System.out.print("\t\t4. Next");
				System.out.print("\t\t5. Previous");
				System.out.print("\t\t6. Stop");

				int choice = scanObj.nextInt();
              
				switch (choice) {
				case 1:
					this.currentframe = Playlist.clip.getMicrosecondPosition();
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
					File file1 = new File(url);

					inputStream = AudioSystem.getAudioInputStream(file1.getAbsoluteFile());

					clip.open(inputStream);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					clip.start();
					status = "play";
					break;
				case 4:
					currentframe = 0L;
					clip.stop();
					clip.close();
					i++;
					break;
				case 5:
					currentframe = 0L;
					clip.stop();
					clip.close();
					i--;
					break;
				case 6:
					currentframe = 0L;
					clip.stop();
					clip.close();
					i=-1;
					break;
				}
				break;
			}
		}
		System.out.print("\nThankyou for Using JukeBox...Vist again!");
	}
}
		
	
	
		

