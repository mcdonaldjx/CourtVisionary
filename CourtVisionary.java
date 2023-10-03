import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.text.SimpleDateFormat;

public class CourtVisionary {
	
	public static CourtVisionGUI window = new CourtVisionGUI();
	public static List<Player> players;
	public static List<Team> teams;
	public static List<Game> seasonGames;
	
	public static void main(String[] args) throws Exception{
		Date today = new Date();
		String date = new SimpleDateFormat("MM dd yyyy").format(today);
		int currMonth = Integer.parseInt(date.substring(0,2));
		int seasonYr = Integer.parseInt(date.substring(date.lastIndexOf(" ")+1));
		if(currMonth < 10){
			seasonYr--;
		}
		teams = new LinkedList<Team>();  //Teams of the current season
		players = new LinkedList<Player>(); //Players of the current season
		seasonGames = new LinkedList<Game>();
		int totalGames = 0;
		String hostURL = "https://api-nba-v1.p.rapidapi.com/";
	        String host = "api-nba-v1.p.rapidapi.com";
	        String rAPIKey =""; //INSERT API KEY FROM RAPIDAPI IN THESE QUOTES!
	        int totalTeams = 0;
	        int totalPlayers = 0;
	       	Request request;
	       	Response response;
	        String rBody = new String();
	        OkHttpClient client = new OkHttpClient();     
	       //Creating teams first
	        request = new Request.Builder()
	        	.url("https://api-nba-v1.p.rapidapi.com/teams/league/standard")
	        	.get()
	        	.addHeader("x-rapidapi-key", rAPIKey)
	        	.addHeader("x-rapidapi-host", host)
	        	.build();
	
	        response = client.newCall(request).execute();
	        rBody = response.body().string();
	
	        totalTeams = Integer.parseInt(rBody.substring(rBody.indexOf("results")+9,rBody.indexOf("filters")-2));
	        String[] teamsRaw = (rBody.substring(rBody.indexOf("teams\":")+8)).split("\"}");
	        populateTeams(teamsRaw,teams,totalTeams);
	        
	      //Getting all the players in the League
	        
	         request = new Request.Builder()
	         	.url("https://api-nba-v1.p.rapidapi.com/players/league/standard")
	         	.get()
	        	.addHeader("x-rapidapi-key", rAPIKey)
	        	.addHeader("x-rapidapi-host", host)
	         	.build();
	
	         response = client.newCall(request).execute();
	         rBody = response.body().string(); //Because according to documentation you can only use Response.body().string() once*/
	        
	         String tempStr = rBody.substring(rBody.indexOf("results")+10, rBody.indexOf("filters")-3).trim();
	         totalPlayers = Integer.parseInt(tempStr);
	         String[] playersRaw = rBody.substring(rBody.indexOf("[{")+2).split("}}},");
	         populatePlayers(playersRaw, totalPlayers);
	        
	        //Get Team records for current year via GET standings/standard/currentYear (-1 year if current month is before July)
	        request = new Request.Builder()
	        		.url(hostURL+"standings/standard/"+seasonYr)
	        		.get()
	        		.addHeader("x-rapidapi-key", rAPIKey)
	            	.addHeader("x-rapidapi-host", host)
	            	.build();
	        response = client.newCall(request).execute();
	        rBody = response.body().string();
	        totalTeams = teams.size();
	        String[] standingsRaw = rBody.substring(rBody.indexOf("[{")).split("league");
	        String standing = new String();
	        int tempID = 0;
	        for(int i = 0; i < standingsRaw.length; i++) {
	        	standing = standingsRaw[i];
	        	if(standing.indexOf("teamId") > -1) {
	        		tempID = Integer.parseInt(standing.substring(standing.indexOf("teamId")+9, standing.indexOf("\",\"win")));
	        		Team temp = getTeam(teams,tempID);
	        		if(temp != null) {
		        		temp.populateStats(standing.substring(standing.indexOf("\"win\"")));
	        		}
	        	}
	        }
	        for(Team t : teams)
	        Collections.sort(t.roster,new Comparator<Player>(){
	            public int compare(Player p1,Player p2){
	            	int result = p1.getLastName().compareToIgnoreCase(p2.getLastName());
	        		if(result != 0) {
	        			return 0;
	        		}
	        		return p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
	            }});
	        
	        //Getting all the games of the season
	       
	        request = new Request.Builder()
	        		.url(hostURL+"games/league/standard/"+seasonYr)
	        		.get()
	        		.addHeader("x-rapidapi-key", rAPIKey)
	            	.addHeader("x-rapidapi-host", host)
	            	.build();
	        response = client.newCall(request).execute();
	        rBody = response.body().string();
	        totalGames = Integer.parseInt(rBody.substring(rBody.indexOf("\"results\":")+10, rBody.indexOf(",\"filters\"")));
	        String[] gamesRaw = rBody.substring(rBody.lastIndexOf(":[{")).split("}}}");
	        populateGames(gamesRaw,seasonGames,totalGames, seasonYr);
	    	SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
					window.GUI.setVisible(true);
	        
	            }
	        });
	}
	public static void populateGames(String[] gamesRaw, List<Game> seasonGames, int  total, int seasonYr) throws Exception {
		String gameR = new String();
		for(int i = 0; i < gamesRaw.length; i++) {
			if(gamesRaw[i].contains("seasonYear") && gamesRaw[i].contains("Finished") && gamesRaw[i].contains(Integer.toString(seasonYr))) {
				gameR = gamesRaw[i].substring(gamesRaw[i].indexOf("seasonYear"));
				Game g = new Game(gameR);
				g.setHomeTeam(getTeam(teams,g.homeID));
				g.setAwayTeam(getTeam(teams,g.awayID));
				seasonGames.add(g);
			}
			
		}
		Collections.sort(seasonGames);
		window.setGameComboBox(seasonGames);
		
	}
	public static void populatePlayers(String[] playersRaw, int total) {
		players = new ArrayList<Player>(total);
		String playerT = new String();
		for(int i = 0; i < playersRaw.length; i++) {
			if(playersRaw[i].contains("playerId") && !playersRaw[i].contains("\"teamId\":null")) {
				playerT = playersRaw[i];				
				Player p = new Player(playerT);
				Team t = getTeam(teams, p.teamId);
				if(p.rookieYear > 0) {					
					p.setPlaysFor(t);
					t.addToRoster(p);
					players.add(p);
				}
			}
		}
		Collections.sort(players);
		window.setPlayerComboBoxA(players);

	}	
	public static void populateTeams(String[] teamsRaw, List<Team> teams, int total) {
		for(int i = 0; i < total; i++) {
			String teamR = teamsRaw[i].substring(teamsRaw[i].indexOf("city"));
			if(teamR.contains("nbaFranchise\":\"1") && teamR.contains("allStar\":\"0")){
				Team t = new Team(teamR);
				teams.add(t);
			}
		}
		Collections.sort(teams);
		window.setTeamComboBox(teams);
	}
	public static Team getTeam(List<Team> teams,int lookup) {
		Team temp = null;
		for(int i = 0; i < teams.size(); i++) {
			temp = teams.get(i);
			if(temp != null && temp.teamID == lookup) {
				return temp;
			}
		}
		return temp;
	}
}
