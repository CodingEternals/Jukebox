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

public class Podcast 
{
	 AudioInputStream inputStream;
	 static Clip clip;
	 String status;
	 long currentframe;
	 
	String PodcastID;
	String PodcastName;
	String CelebrityName;
	String PodcastDuration;
	String PublishedDate;
	
	public String getPodcastID() {
		return PodcastID;
	}
	public void setPodcastID(String podcastID) {
		PodcastID = podcastID;
	}
	public String getPodcastName() {
		return PodcastName;
	}
	public void setPodcastName(String podcastName) {
		PodcastName = podcastName;
	}
	public String getCelebrityName() {
		return CelebrityName;
	}
	public void setCelebrityName(String celebrityName) {
		CelebrityName = celebrityName;
	}
	public String getPodcastDuration() {
		return PodcastDuration;
	}
	public void setPodcastDuration(String podcastDuration) {
		PodcastDuration = podcastDuration;
	}
	public String getPublishedDate() {
		return PublishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		PublishedDate = publishedDate;
	}
	
	public Podcast(String podcastID, String podcastName, String celebrityName, String podcastDuration,
			String publishedDate) {
	
		PodcastID = podcastID;
		PodcastName = podcastName;
		CelebrityName = celebrityName;
		PodcastDuration = podcastDuration;
		PublishedDate = publishedDate;
	}
	
	public Podcast() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Podcast [PodcastID=" + PodcastID + ", PodcastName=" + PodcastName + ", CelebrityName=" + CelebrityName
				+ ", PodcastDuration=" + PodcastDuration + ", PublishedDate=" + PublishedDate + "]";
	}
	
	public void initialize()
	{
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter the Podcast Name:");
		PodcastName=sc.nextLine();
		
		System.out.println("Enter the Celebrity Name:");
		CelebrityName=sc.nextLine();
		
		System.out.println("Enter the Podcast Duration:");
		PodcastDuration=sc.nextLine();
		
		System.out.println("Enter the PublishedDate:");
		PublishedDate=sc.nextLine();
		
		sc.close();
		
	}
	public static int podInsertion(Connection conn) {
		// TODO Auto-generated method stub
		int roweff = 0;
		try
		{
		String podId="";
		Podcast p=new Podcast();
		p.initialize();
		
		Statement statementobj=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=statementobj.executeQuery("select PodcastID from Podcast order by PodcastID");
		while(rs.next()) 
		{
				podId=rs.getString(1);
		}

		PreparedStatement pstm=conn.prepareStatement("insert into Podcast values(?,?,?,?,?)");	
		pstm.setString(1, "POD"+Integer.toString(Integer.parseInt(podId.substring(4))+1));
		pstm.setString(2, p.getPodcastName());
		pstm.setString(3, p.getCelebrityName());
		pstm.setString(4, p.getPodcastDuration());
		pstm.setString(5, p.getPublishedDate());
		
		roweff=pstm.executeUpdate();
		pstm.close();
		 
		}
		catch(Exception e)
		{
			System.out.println("Exception Arised"+e);
		}
		return roweff;
	}
	public static void displayPodcast(Connection conn) throws SQLException
	{
		Statement statement=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs1=statement.executeQuery("select* from Podcast"); 
		System.out.println("\n***********************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-30s %-30s","PodcastID","Podcast Name","Celebrity Name","Podcast Duration","Published Date");
		System.out.println("\n***********************************************************************************************************************\n");
		while(rs1.next())
	    {
			System.out.printf("%-20s %-20s %-20s %-30s %-30s\n",rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5));
	    }
		
	}
	public static ArrayList<Podcast> searchByPodcastName(Connection conn, ArrayList<Podcast> pd,String podcastName) throws SearchNotFoundException 
	{
		
		ArrayList<Podcast> list=new ArrayList<>();
		System.out.println("\n***********************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-30s %-30s","PodcastID","Podcast Name","Celebrity Name","Podcast Duration","Published Date");
		System.out.println("\n***********************************************************************************************************************");
		pd.stream().filter(p->p.getPodcastName().contains(podcastName))
		.collect(Collectors.toList())
		.forEach(p->{System.out.printf("\n%-20s %-20s %-20s %-30s %-30s",p.getPodcastID(),p.getPodcastName(),p.getCelebrityName(),p.getPodcastDuration(),p.getPublishedDate()); list.add(p);});
		return list;
	}
	public static ArrayList<Podcast> searchByCelebrityName(Connection conn, ArrayList<Podcast> pd,String celebrityName) throws SearchNotFoundException 
	{
		
		ArrayList<Podcast> list2=new ArrayList<>();
		System.out.println("\n***********************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-30s %-30s","PodcastID","Podcast Name","Celebrity Name","Podcast Duration","Published Date");
		System.out.println("\n***********************************************************************************************************************");
		pd.stream().filter(p->p.getCelebrityName().contains(celebrityName))
		.forEach(p->{System.out.printf("\n%-20s %-20s %-20s %-30s %-30s",p.getPodcastID(),p.getPodcastName(),p.getCelebrityName(),p.getPodcastDuration(),p.getPublishedDate()); list2.add(p);});
		
		return list2;
	}
	public static ArrayList<Podcast> searchByPublishedDate(Connection conn, ArrayList<Podcast> pd,String date) throws SearchNotFoundException 
	{
	
		ArrayList<Podcast> list1=new ArrayList<>();
		System.out.println("\n***********************************************************************************************************************");
		System.out.printf("\n%-20s %-20s %-20s %-30s %-30s","PodcastID","Podcast Name","Celebrity Name","Podcast Duration","Published Date");
		System.out.println("\n***********************************************************************************************************************");
		pd.stream().filter(p->p.getPublishedDate().contains(date))
		.forEach(p->{System.out.printf("\n%-20s %-20s %-20s %-30s %-30s",p.getPodcastID(),p.getPodcastName(),p.getCelebrityName(),p.getPodcastDuration(),p.getPublishedDate()+"\n"); list1.add(p);});
		
		return list1;
	}
	
	
	public void playPodcast() {
		try {
			
			@SuppressWarnings("resource")
			Scanner scanObj = new Scanner(System.in);
			System.out.print("\n\t\tEnter PodcastID:  ");
			String podID = scanObj.next();

			System.out.print("\t\tPlaying");
			String url = "E:\\" + podID + ".wav";
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
						playPodcast();
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
	
	public void operations(int choice, String url) {
		try {
			switch (choice) {
			case 1:
				this.currentframe = Podcast.clip.getMicrosecondPosition();
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
