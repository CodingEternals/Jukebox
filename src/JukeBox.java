import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class JukeBox {

	public static void main(String arg[]) {
		Scanner sc = new Scanner(System.in);
		Song sonng = new Song();
		Podcast pod = new Podcast();
		Playlist plist = new Playlist();

		try {
			String un = null;
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "tanuj@1995");
			Statement statementobj = conn.createStatement();

			User user = new User();

			System.out.println("\t\t====================================\t\t\t\t");
			System.out.println("\t\t\t**Welcome To JukeBox**\t\t\t\t");
			System.out.println("\t\t====================================\t\t\t\t\n");

			System.out.println("Welcome To the World of Music and Entertainment. New to JubeBox ? Singup Now!\n");

			

				try {
					do
					{
					System.out.println("Select:\t\t1.Signup\t\t2.Login");
					int select = sc.nextInt();
					
					switch (select) {
					case 1:
						user.initialize();
						int roweff = User.signup(conn, user);
						if (roweff > 0)
							System.out.println("User Succefully registered");
						else
							System.out.println(":Please Enter complete details or in Proper format");
						break;

					case 2:
						String temp = null;
						do {
							System.out.println("Enter UserID");
							un = sc.next();
							System.out.println("Enter Password");
							String ps = sc.next();
							ResultSet rs1 = statementobj.executeQuery("select* from Userr");
							while (rs1.next()) {
								if (rs1.getString(1).equals(un) && rs1.getString(3).equals(ps)) {
									System.out.println("\t\t************************************\t\t\t\t");
									System.out.println("\t\t\tWelcome " + rs1.getString(2) + " Enjoy the beat");
									System.out.println("\t\t************************************\t\t\t\t");
									temp = un;
									break;
								}
							}
							if (temp == null)
								throw new UserNotFoundException(":Please Check your Username or Password again");

						} while (temp == null);
					}
					if (select == 2)
						break;
				}while(true);
				} 
				
				catch (Exception e) {
					e.getMessage();
				}
				
			
			do {
				System.out.println("\t1.Song Menu\t\t2.Podcast Menu\t\t3.Playlist Menu\t\t4.Exit\n");
				int press = sc.nextInt();
				switch (press) {
				case 1: {
					do {
						System.out.println(
								"\n1.Song Insertion\n2.Display All Songs\n3.Search By Artist\n4.Search By Genre\n5.Search By Album\n6.Search By Song Name\n7.Exit");
						int Choice = sc.nextInt();
						ResultSet rs = statementobj.executeQuery("select* from Song");
						ArrayList<Song> sg = new ArrayList<Song>();

						while (rs.next()) {
							sg.add(new Song(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getString(6)));
						}

						switch (Choice) {

						case 1:
							int roweff = Song.songInsertion(conn);
							if (roweff > 0)
								System.out.println("Song Inserted");
							else
								System.out.println("Error Occured");
							break;

						case 2:
							Song.displaySong(conn);
							sonng.playTheMusic();

							break;

						case 3:
							System.out.println("Enter the Artist Name:");
							String arName = sc.next();
							ArrayList<Song> artist = Song.searchByArtist(conn, sg, arName);
							if (artist.size() == 0)
								throw new SearchNotFoundException(":Sorry! Atrist Not Found");
							sonng.playTheMusic();
							break;

						case 4:
							System.out.println("Enter the Genre Name:");
							String gName = sc.next();
							ArrayList<Song> genre = Song.searchByGenre(conn, sg, gName);
							if (genre.size() == 0)
								throw new SearchNotFoundException(":Sorry! Genre Not Found");
							sonng.playTheMusic();
							break;

						case 5:
							System.out.println("Enter the Album Name:");
							String aName = sc.next();
							ArrayList<Song> album = Song.searchByAlbum(conn, sg, aName);
							if (album.size() == 0)
								throw new SearchNotFoundException(":Sorry! Album Not Found");
							sonng.playTheMusic();

							break;

						case 6:
							System.out.println("Enter the Song Name:");
							String sName = sc.next();
							ArrayList<Song> songName = Song.searchBySong(conn, sg, sName);
							if (songName.size() == 0)
								throw new SearchNotFoundException(":Sorry! Song Not Found");
							sonng.playTheMusic();
							break;
						}
						if (Choice == 7)
							break;
					} while (true);
				}
					break;

				case 2: {
					do {
						System.out.println(
								"\n1.Podcast Insertion\n2.Display All Podcast\n3.Search By Podcast Name\n4.Search By Celebrity Name\n5.Search By Published Date\n6.Exit");
						int makeChoice = sc.nextInt();
						ResultSet rs1 = statementobj.executeQuery("select* from Podcast");
						ArrayList<Podcast> pd = new ArrayList<Podcast>();

						while (rs1.next()) {
							pd.add(new Podcast(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4),
									rs1.getString(5)));
						}

						switch (makeChoice) {

						case 1:
							int roweff = Podcast.podInsertion(conn);
							if (roweff > 0)
								System.out.println("Podcast Inserted");
							else
								System.out.println("Error Occured");
							break;

						case 2:
							Podcast.displayPodcast(conn);
							pod.playPodcast();

							break;

						case 3:
							System.out.println("Enter the Podcast Name:");
							String Name = sc.next();
							ArrayList<Podcast> podcastName = Podcast.searchByPodcastName(conn, pd, Name);
							if (podcastName.size() == 0)
								throw new SearchNotFoundException(":Sorry! Podcast Name Not Found");
							pod.playPodcast();
							break;

						case 4:
							System.out.println("Enter the Celebrity Name:");
							String celname = sc.next();
							ArrayList<Podcast> celebrityName = Podcast.searchByCelebrityName(conn, pd, celname);
							if (celebrityName.size() == 0)
								throw new SearchNotFoundException(":Sorry! Celebrity Name Not Found");
							pod.playPodcast();
							break;

						case 5:
							System.out.println("Enter the Podcast Publish Date:");
							String date = sc.next();
							ArrayList<Podcast> publishdate = Podcast.searchByPublishedDate(conn, pd, date);
							if (publishdate.size() == 0)
								throw new SearchNotFoundException(":Sorry! Date Not Found");
							pod.playPodcast();
							break;
						}
						if (makeChoice == 6)
							break;
					} while (true);
				}
					break;

				case 3: {

					System.out.println(
							"Choice\n1.Create PlayList\n2.Display Created Playlist and Add Song to Playlist\n3.PLay Playlist");
					int choices = sc.nextInt();
					ResultSet res = statementobj.executeQuery("select* from Playlist");
					ArrayList<Playlist> ply = new ArrayList<Playlist>();
					while (res.next()) {
						ply.add(new Playlist(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
								res.getString(5)));
					}

					switch (choices) {
					case 1:
						int roweff = UserPlaylist.createUserPlaylist(conn, un);
						if (roweff > 0)
							System.out.println("New Userplaylist Sucessfully created.Now you can add songs");
						else
							System.out.println("Failure occured in Palylist creation!");
						break;

					case 2:
						Playlist.Display(conn);
						System.out.println("Enter Playlist id");
						String playlistId = sc.next();
						int roweff1 = Playlist.addSongtoCreatedPlaylist(conn, un, playlistId);
						if (roweff1 > 0)
							System.out.println("New Song added to your playlist");
						else
							System.out.println("Fail to add Songs");
						break;

					case 3:
						plist.playPlayList(conn, ply);
						break;
					}

				}
				}
				if (press == 4)
					System.out.println("\"\\nThankyou for Using JukeBox...Vist again!\"");
				break;
			} while (true);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		sc.close();

	}

}
