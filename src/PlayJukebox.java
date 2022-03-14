import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class PlayJukebox {
	
	static AudioInputStream inputStream;
	static Clip clip;
	static String status;
	 long currentframe;
	PlayJukebox(){}

	public static void playPlayList(Connection conn,ArrayList<Playlist> ply) throws SQLException {
		
		Scanner sc=new Scanner(System.in);
		ArrayList<Playlist> songsList=new ArrayList<>();
		PlayJukebox pj=new PlayJukebox();
		System.out.println("Enter UserPlaylsit ID:");	
		String plyid=sc.nextLine();
		System.out.println("SongList");
		System.out.println("*********");
		ply.stream().filter(p->p.getPlyID().equals(plyid))
		.collect(Collectors.toList())
		.forEach(s->{System.out.println(s.getSongID()); songsList.add(s);});
		
		for(Playlist mysong:songsList) {
		
				pj.playSong(mysong.SongID);
			
		}
		sc.close();
	}
	
	public  void playSong(String songId) {

		try {
			@SuppressWarnings("resource")
			Scanner scanObj=new Scanner(System.in);
			System.out.print("\t\tPlaying");
				
			String url = "E:\\" + songId + ".wav";
			
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
				

				int choice = scanObj.nextInt();
				
				if(choice==1) {
					operations(choice, url);
				}else if(choice==2) {
					operations(choice, url);
				}else if(choice==3) {
					operations(choice, url);
				}else if(choice==4) {
					if(url.equals("E:\\"+null+"wav"))
						System.out.println("Track is Empty");
					//operations(choice, url);
					break;
						
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
				this.currentframe = PlayJukebox.clip.getMicrosecondPosition();
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
