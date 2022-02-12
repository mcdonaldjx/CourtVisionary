import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Team implements Comparable<Team> {
	protected
		int teamID = -1;
		String fullName;
		String abbrev;
		String city;
		String nickname;
		String logoURL = new String("https://mediacentral.nba.com/wp-content/uploads/logos/NBA.jpg");
		ImageIcon logo = new ImageIcon(logoURL);
		LinkedList<Player> roster = new LinkedList<Player>(); //via GET players for the season by team ID
		Player[] rosterArr;
		LinkedList<Game> games = new LinkedList<Game>();
		String conference;
		int confSeed;
		String confSeedOrd;
		int confWins;
		int confLs;
		String division;
		String divSeedOrd;
		int divSeed;
		int divWins;
		int divLs;
		double divGB;
		int last10Wins;
		int last10Ls;
		int seasonYear;
		int wins;
		int homeWins;
		int homeLs;
		int awayWins;
		int awayLs;
		int losses;
		double gamesBehind;
		double winPercentage;
		double LossPercentage;
		int championships;
		int gamesPlayed;


	public Team(String response) {
		String[] fields = response.split(",");
		//////System.out.println(response);
		setCity(fields[0].substring(fields[0].indexOf(":")+2,fields[0].lastIndexOf("\"")));
		setFullName(fields[1].substring(fields[1].indexOf(":")+2,fields[1].lastIndexOf("\"")));
		setTeamID(Integer.parseInt(fields[2].substring(fields[2].indexOf(":")+2,fields[2].lastIndexOf("\""))));
		setNickname(fields[3].substring(fields[3].indexOf(":")+2,fields[3].lastIndexOf("\"")));
		setLogoURL(fields[4].substring(fields[4].indexOf(":")+2,fields[4].lastIndexOf("\"")).replace("/",""));
		try {
			if(!logoURL.equals(null)) {
				logo = new ImageIcon(new URL(logoURL));
			}
		} catch (Exception e) {
			
		}
		setAbbrev(fields[5].substring(fields[5].indexOf(":")+2,fields[5].lastIndexOf("\"")));
		setConference(fields[8].substring(fields[8].indexOf("confName\":\"")+11,fields[8].lastIndexOf("\"")));
		setDivision(fields[9].substring(fields[9].indexOf(":")+2));
		//printInfo();
		//////System.out.println();
	}
	
	public void populateStats(String response) {
		String[] stats = response.split("\",\"");
		
		for(int i = 0; i < stats.length; i++) {
			stats[i] = stats[i].replaceAll(" ","").trim();
		}
		setWins(Integer.parseInt(stats[0].substring(stats[0].lastIndexOf("\"")+1)));
		setLosses(Integer.parseInt(stats[1].substring(stats[1].lastIndexOf("\"")+1)));
		//////System.out.println("\tRegular Season W-L: "+wins+losses);
		setGamesBehind(Double.parseDouble(stats[2].substring(stats[2].lastIndexOf("\"")+1)));
		//////System.out.println("\tGames Behind: "+gamesBehind);
		setLast10Wins(Integer.parseInt(stats[3].substring(stats[3].lastIndexOf("\"")+1)));
		setLast10Ls(Integer.parseInt(stats[4].substring(stats[4].lastIndexOf("\"")+1)));
		setSeasonYear(Integer.parseInt(stats[6].substring(stats[6].lastIndexOf("\"")+1)));
		//////System.out.println("\tSeason:"+seasonYear+"-"+(seasonYear+1)));
		setConfSeed(Integer.parseInt(stats[8].substring(stats[8].lastIndexOf("\"")+1)));
		//////System.out.println("\tConference Seed: "+confSeed);
		setConfWins(Integer.parseInt(stats[9].substring(stats[9].lastIndexOf("\"")+1)));
		String temp = stats[10].substring(stats[10].indexOf("loss\":")+7, stats[10].indexOf("loss\":")+9);
		temp = temp.replaceAll("[\\D]","");
		setConfLs(Integer.parseInt(temp));
		setDivision(stats[10].substring(stats[10].lastIndexOf("\"")+1));
		//////System.out.println("\tConference W-L: "+confWins+"-"+confLs);
		setDivSeed(Integer.parseInt(stats[11].substring(stats[11].lastIndexOf("\"")+1)));
		//////System.out.println("\tDivision Seed: "+divSeed);
		setDivWins(Integer.parseInt(stats[12].substring(stats[12].lastIndexOf("\"")+1)));
		setDivLs(Integer.parseInt(stats[13].substring(stats[13].lastIndexOf("\"")+1)));
		//////System.out.println("\tDivision W-L: "+divWins+"-"+divLs);
		setDivGB(Double.parseDouble(stats[14].substring(stats[14].lastIndexOf(":")+2)));
		//////System.out.println("\t"+divGB);
		setHomeWins(Integer.parseInt(stats[16].substring(stats[16].lastIndexOf("\"")+1)));
		setHomeLs(Integer.parseInt(stats[17].substring(stats[17].indexOf("\":\"")+3, stats[17].indexOf("}")-1)));
		//////System.out.println("\t@ Home W-L:"+homeWins+"-"+homeLs);
		setAwayWins(Integer.parseInt(stats[17].substring(stats[17].lastIndexOf("\"")+1)));
		setAwayLs(Integer.parseInt(stats[18].substring(stats[17].indexOf("\":\"")+3, stats[17].indexOf("}")-1)));
		//////System.out.println("\t"+awayLs);
		
	}
	
	public int getConfSeed() {
		return confSeed;
	}

	public void setConfSeed(int confSeed) {
		this.confSeed = confSeed;
		String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		if(confSeed == 11 || confSeed == 12 || confSeed == 12) {
	       setConfSeedOrd(Integer.toString(confSeed) + "th");		}	
		else{
			setConfSeedOrd(confSeed + suffixes[confSeed % 10]);
		}
		
	}

	public int getConfWins() {
		return confWins;
	}

	public void setConfWins(int confWins) {
		this.confWins = confWins;
	}

	public int getConfLs() {
		return confLs;
	}

	public void setConfLs(int confLs) {
		this.confLs = confLs;
	}

	public int getDivSeed() {
		return divSeed;
	}

	public void setDivSeed(int divSeed) {
		this.divSeed = divSeed;
		String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		if(divSeed == 11 || divSeed == 12 || divSeed == 12) {
	       setDivSeedOrd(Integer.toString(divSeed) + "th");
		}	
		else{
	        setDivSeedOrd(divSeed + suffixes[divSeed % 10]);
		}
	}

	public int getDivWins() {
		return divWins;
	}

	public void setDivWins(int divWins) {
		this.divWins = divWins;
	}

	public int getDivLs() {
		return divLs;
	}

	public void setDivLs(int divLs) {
		this.divLs = divLs;
	}

	public double getDivGB() {
		return divGB;
	}

	public void setDivGB(double divGB) {
		this.divGB = divGB;
	}

	public int getLast10Wins() {
		return last10Wins;
	}

	public void setLast10Wins(int last10Wins) {
		this.last10Wins = last10Wins;
	}

	public int getLast10Ls() {
		return last10Ls;
	}

	public void setLast10Ls(int last10Ls) {
		this.last10Ls = last10Ls;
	}

	public int getSeasonYear() {
		return seasonYear;
	}

	public void setSeasonYear(int seasonYear) {
		this.seasonYear = seasonYear;
	}

	public int getHomeWins() {
		return homeWins;
	}

	public void setHomeWins(int homeWins) {
		this.homeWins = homeWins;
	}

	public int getHomeLs() {
		return homeLs;
	}

	public void setHomeLs(int homeLs) {
		this.homeLs = homeLs;
	}

	public int getAwayWins() {
		return awayWins;
	}

	public void setAwayWins(int awayWins) {
		this.awayWins = awayWins;
	}

	public int getAwayLs() {
		return awayLs;
	}

	public void setAwayLs(int awayLs) {
		this.awayLs = awayLs;
	}

	public double getLossPercentage() {
		return LossPercentage;
	}

	public void setLossPercentage() {
		DecimalFormat df = new DecimalFormat("#.###");
		LossPercentage = Double.parseDouble(df.format((losses)/(wins+losses)));
	}

	public int getTeamID() {
		return teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public LinkedList<Player> getRoster() {
		return roster;
	}

	public void setRoster(LinkedList<Player> roster) {
		this.roster = roster;
	}

	public String getFullName() {
		return fullName;
	}

	private void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAbbrev() {
		return abbrev;
	}

	private void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	public String getConference() {
		return conference;
	}

	private void setConference(String conference) {
		this.conference = conference.substring(0,1).toUpperCase()+conference.substring(1);
	}

	public String getDivision() {
		return division;
	}

	private void setDivision(String division) {
		if(division.length() > 1)
			this.division = division.substring(0,1).toUpperCase()+division.substring(1);
	}

	public String getCity() {
		return city;
	}

	private void setCity(String city) {
		this.city = city.substring(0,1).toUpperCase()+city.substring(1);
	}

	public String getLogoURL() {
		return logoURL;
	}

	private void setLogoURL(String newlogoURL) {
		if(newlogoURL.length() < 1) {
			logoURL = null;
		}
		else
			newlogoURL = newlogoURL.replaceAll("\\\\","/");
			logoURL = newlogoURL;
	}

	public int getWins() {
		return wins;
	}

	private void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	private void setLosses(int losses) {
		this.losses = losses;
	}

	public double getGamesBehind() {
		return gamesBehind;
	}

	private void setGamesBehind(double gamesBehind) {
		this.gamesBehind = gamesBehind;
	}

	public double getWinPercentage() {
		return winPercentage;
	}

	private void setWinPercentage() {
		DecimalFormat df = new DecimalFormat("#.###");
		winPercentage = Double.parseDouble(df.format((wins/(wins+losses))));
	}
	
	public void printInfo() {
		//System.out.println("\tCity: "+getCity());
		//System.out.println("\tFull Name: "+getFullName());
		//System.out.println("\tTeam ID: "+getTeamID());
		//System.out.println("\tNickname:"+getNickname());
		//System.out.println("\tLogo (URL): "+getLogoURL());
		//System.out.println("\tAbbreviation: "+getAbbrev());
		//System.out.println("\tConference: "+getConference());
		//System.out.println("\tDivision: "+getDivision());
		//System.out.println();
	}
	public int getChampionships() {
		return championships;
	}

	private void setChampionships(int championships) {
		this.championships = championships;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = wins + losses;
	}
	public String getConfSeedOrd() {
		return confSeedOrd;
	}

	public String getDivSeedOrd() {
		return divSeedOrd;
	}

	public void setConfSeedOrd(String confSeedOrd) {
		this.confSeedOrd = confSeedOrd;
	}

	public void setDivSeedOrd(String divSeedOrd) {
		this.divSeedOrd = divSeedOrd;
	}

	public String toString() {
		return fullName+" ("+abbrev+")";
	}

	@Override
	public int compareTo(Team t) {
		if(getFullName() == null || t.getFullName() == null) {
			return 0;
		}
		return getFullName().compareTo(t.getFullName());
	}

	public void addToRoster(Player p) {
		//System.out.println("Adding "+p.getFullName()+" to "+getFullName());
		roster.add(p);
	}

	
	
}
