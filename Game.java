import java.util.Date;
import java.text.SimpleDateFormat;

public class Game implements Comparable<Game>{
private
	//Following is received from GET gameDetails/gameID
	int seasonYear;
	int gameId;

	boolean detailsFilled = false;
	Date date = null; //YYYY MM DD when game was played, also how a game is looked up
	String dateString = null;
	String arena; //Location of the game
	String city; //City of the game
	Team homeTeam = null;
	int homeID;
	int homeScore;
	Team awayTeam = null;
	int awayID;
	int awayScore;

	//Following is recieved from GET statistics/games/gameId
	int homeFBPts; //Home Fastbreak Points
	int homePtsinPaint; //Home Points in the Paint
	int homeBiggestLead; //Home Biggest Lead
	int home2ndChancePts; //Home 2nd Chance Points
	int homePtsFrmTurnovers; //Home Points Off Turnover
	int homeLongestRun; //Home Longest Scoring Run
	int homeFGM; //Home Field Goals Made
	int homeFGA; //Home Field Goals Attempted
	double homeFGP;
	int homeFTM; //Home Free Throws Made
	int homeFTA; //Home Free Throws Attempted
	double homeFTP; //Home Free Throw Percentage
	int home3PtM; //Home Three Points Made
	int home3PtA; //Home Three Points Attempted
	double home3PtP; //Home Three Point Percentage
	int homeOffReb; //Home Offensive Rebounds
	int homeDefReb; //Home Defensive Rebounds
	int homeReb; //Home Total Rebounds
	int homeAssists; //Home Assists
	int homeFouls; //Home Fouls
	int homeSteals; //Home Steals
	int homeTO; //Home Turnovers
	int homeBlks; //Home Blocks
	int awayFBPts; //Away Fastbreak Points
	int awayPtsinPaint; //Away Points in the Paint
	int awayBiggestLead; //Away Biggest Lead
	int away2ndChancePts; //Away 2nd Chance Points
	int awayPtsFrmTurnovers; //Away Points Off Turnover
	int awayLongestRun; //Away Longest Scoring Run
	int awayFGM; //Away Field Goals Made
	int awayFGA; //Away Field Goals Attempted
	double awayFGP;
	int awayFTM; //Away Free Throws Made
	int awayFTA; //Away Free Throws Attempted
	double awayFTP; //Away Free Throw Percentage
	int away3PtM; //Away Three Points Made
	int away3PtA; //Away Three Points Attempted
	double away3PtP; //Away Three Point Percentage
	int awayOffReb; //Away Offensive Rebounds
	int awayDefReb; //Away Defensive Rebounds
	int awayReb; //Away Total Rebounds
	int awayAssists; //Away Assists
	int awayFouls; //Away Fouls
	int awaySteals; //Away Steals
	int awayTO; //Away Turnovers
	int awayBlks; //Away Blocks
	
	public Game() {
		
	}
	public Game(String response) throws Exception {
		response = response.replaceAll("\"","");
		//System.out.println(response);
		String[] stats = response.split(",");
		seasonYear = Integer.parseInt(stats[0].replaceAll("[^0-9]",""));
		gameId = Integer.parseInt(stats[2].replaceAll("[^0-9]",""));
		stats[3] = stats[3].replaceAll("\\n","");
		dateString = stats[3].substring(stats[3].indexOf(":")+1, stats[3].lastIndexOf("T"));
		SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");  
		date = dateFormat.parse(dateString.trim());
		arena = stats[5].substring(stats[5].indexOf(":")+1);
		city = stats[6].substring(stats[6].lastIndexOf(":")+1) +", "+ stats[7].substring(stats[7].indexOf(":")+1);
		awayID = Integer.parseInt(stats[16].replaceAll("[^0-9]",""));
		homeID = Integer.parseInt(stats[22].replaceAll("[^0-9]",""));
		if(response.contains("Finished")) {
			awayScore = Integer.parseInt(stats[21].replaceAll("[^0-9]",""));
			homeScore = Integer.parseInt(stats[27].replaceAll("[^0-9]",""));
		}
		//System.out.println(seasonYear+" Season:"+"\nGame ID: "+gameId+"\nDate: "+date.toString()+"\n"+arena+"\n"+city+""+"\nTeam "+awayID+" @ Team "+ homeID+"\nScore: "+awayScore+" - "+homeScore+"\n");

	}
	public boolean isDetailed() {
		return detailsFilled;
	}
	public void populateGameDetails(String[] gameStats) {
			awayFBPts = Integer.parseInt(gameStats[2].substring(gameStats[2].indexOf(":")+1));
			awayPtsinPaint = Integer.parseInt(gameStats[3].substring(gameStats[3].indexOf(":")+1));
			awayBiggestLead =Integer.parseInt(gameStats[4].substring(gameStats[4].indexOf(":")+1));
			away2ndChancePts = Integer.parseInt(gameStats[5].substring(gameStats[5].indexOf(":")+1));
			awayPtsFrmTurnovers = Integer.parseInt(gameStats[6].substring(gameStats[6].indexOf(":")+1));
			awayLongestRun = Integer.parseInt(gameStats[7].substring(gameStats[7].indexOf(":")+1));
			awayScore = Integer.parseInt(gameStats[8].substring(gameStats[8].indexOf(":")+1));
			awayFGM = Integer.parseInt(gameStats[9].substring(gameStats[9].indexOf(":")+1));
			awayFGA = Integer.parseInt(gameStats[10].substring(gameStats[10].indexOf(":")+1));
			awayFGP = Double.parseDouble(gameStats[11].substring(gameStats[11].indexOf(":")+1));
			awayFTM = Integer.parseInt(gameStats[12].substring(gameStats[12].indexOf(":")+1));
			awayFTA = Integer.parseInt(gameStats[13].substring(gameStats[13].indexOf(":")+1));
			awayFTP = Double.parseDouble(gameStats[14].substring(gameStats[14].indexOf(":")+1));
			away3PtM = Integer.parseInt(gameStats[15].substring(gameStats[15].indexOf(":")+1));
			away3PtA = Integer.parseInt(gameStats[16].substring(gameStats[16].indexOf(":")+1));
			away3PtP = Double.parseDouble(gameStats[17].substring(gameStats[17].indexOf(":")+1));
			awayOffReb = Integer.parseInt(gameStats[18].substring(gameStats[18].indexOf(":")+1));
			awayDefReb = Integer.parseInt(gameStats[19].substring(gameStats[19].indexOf(":")+1));
			awayReb = Integer.parseInt(gameStats[20].substring(gameStats[20].indexOf(":")+1));
			awayAssists = Integer.parseInt(gameStats[21].substring(gameStats[21].indexOf(":")+1));
			awayFouls = Integer.parseInt(gameStats[22].substring(gameStats[22].indexOf(":")+1));
			awaySteals = Integer.parseInt(gameStats[23].substring(gameStats[23].indexOf(":")+1));
			awayTO = Integer.parseInt(gameStats[24].substring(gameStats[24].indexOf(":")+1));
			awayBlks = Integer.parseInt(gameStats[25].substring(gameStats[25].indexOf(":")+1));
			
			homeFBPts = Integer.parseInt(gameStats[30].substring(gameStats[30].indexOf(":")+1));
			homePtsinPaint = Integer.parseInt(gameStats[31].substring(gameStats[31].indexOf(":")+1));
			homeBiggestLead =Integer.parseInt(gameStats[32].substring(gameStats[32].indexOf(":")+1));
			home2ndChancePts = Integer.parseInt(gameStats[33].substring(gameStats[33].indexOf(":")+1));
			homePtsFrmTurnovers = Integer.parseInt(gameStats[34].substring(gameStats[34].indexOf(":")+1));
			homeLongestRun = Integer.parseInt(gameStats[35].substring(gameStats[35].indexOf(":")+1));
			homeScore = Integer.parseInt(gameStats[36].substring(gameStats[36].indexOf(":")+1));
			homeFGM = Integer.parseInt(gameStats[37].substring(gameStats[37].indexOf(":")+1));
			homeFGA = Integer.parseInt(gameStats[38].substring(gameStats[38].indexOf(":")+1));
			homeFGP = Double.parseDouble(gameStats[39].substring(gameStats[39].indexOf(":")+1));
			homeFTM = Integer.parseInt(gameStats[40].substring(gameStats[40].indexOf(":")+1));
			homeFTA = Integer.parseInt(gameStats[41].substring(gameStats[41].indexOf(":")+1));
			homeFTP = Double.parseDouble(gameStats[42].substring(gameStats[42].indexOf(":")+1));
			home3PtM = Integer.parseInt(gameStats[43].substring(gameStats[43].indexOf(":")+1));
			home3PtA = Integer.parseInt(gameStats[44].substring(gameStats[44].indexOf(":")+1));
			home3PtP = Double.parseDouble(gameStats[45].substring(gameStats[45].indexOf(":")+1));
			homeOffReb = Integer.parseInt(gameStats[46].substring(gameStats[46].indexOf(":")+1));
			homeDefReb = Integer.parseInt(gameStats[47].substring(gameStats[47].indexOf(":")+1));
			homeReb = Integer.parseInt(gameStats[48].substring(gameStats[48].indexOf(":")+1));
			homeAssists = Integer.parseInt(gameStats[49].substring(gameStats[49].indexOf(":")+1));
			homeFouls = Integer.parseInt(gameStats[50].substring(gameStats[50].indexOf(":")+1));
			homeSteals = Integer.parseInt(gameStats[51].substring(gameStats[51].indexOf(":")+1));
			homeTO = Integer.parseInt(gameStats[52].substring(gameStats[52].indexOf(":")+1));
			homeBlks = Integer.parseInt(gameStats[53].substring(gameStats[53].indexOf(":")+1));
			detailsFilled = true;
	}
	public int getSeasonYear() {
		return seasonYear;
	}
	public void setSeasonYear(int seasonYear) {
		this.seasonYear = seasonYear;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getArena() {
		return arena;
	}
	public void setArena(String arena) {
		this.arena = arena;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Team getHomeTeam() {
		return homeTeam;
	}
	public int getHomeID() {
		return homeID;
	}
	public int getAwayID() {
		return awayID;
	}
	public int getHomeFTM() {
		return homeFTM;
	}
	public int getHomeFTA() {
		return homeFTA;
	}
	public int getAwayFTM() {
		return awayFTM;
	}
	public int getAwayFTA() {
		return awayFTA;
	}
	public void setHomeFTM(int homeFTM) {
		this.homeFTM = homeFTM;
	}
	public void setHomeFTA(int homeFTA) {
		this.homeFTA = homeFTA;
	}
	public void setAwayFTM(int awayFTM) {
		this.awayFTM = awayFTM;
	}
	public void setAwayFTA(int awayFTA) {
		this.awayFTA = awayFTA;
	}
	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}
	public int getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}
	public int getAwayScore() {
		return awayScore;
	}
	public void setAwayScore(int awayScore) {
		this.awayScore = awayScore;
	}
	public int getHomeFBPts() {
		return homeFBPts;
	}
	public void setHomeFBPts(int homeFBPts) {
		this.homeFBPts = homeFBPts;
	}
	public int getHomePtsinPaint() {
		return homePtsinPaint;
	}
	public void setHomePtsinPaint(int homePtsinPaint) {
		this.homePtsinPaint = homePtsinPaint;
	}
	public int getHomeBiggestLead() {
		return homeBiggestLead;
	}
	public void setHomeBiggestLead(int homeBiggestLead) {
		this.homeBiggestLead = homeBiggestLead;
	}
	public int getHome2ndChancePts() {
		return home2ndChancePts;
	}
	public void setHome2ndChancePts(int home2ndChancePts) {
		this.home2ndChancePts = home2ndChancePts;
	}
	public int getHomePtsFrmTurnovers() {
		return homePtsFrmTurnovers;
	}
	public void setHomePtsFrmTurnovers(int homePtsFrmTurnovers) {
		this.homePtsFrmTurnovers = homePtsFrmTurnovers;
	}
	public int getHomeLongestRun() {
		return homeLongestRun;
	}
	public void setHomeLongestRun(int homeLongestRun) {
		this.homeLongestRun = homeLongestRun;
	}
	public int getHomeFGM() {
		return homeFGM;
	}
	public void setHomeFGM(int homeFGM) {
		this.homeFGM = homeFGM;
	}
	public int getHomeFGA() {
		return homeFGA;
	}
	public void setHomeFGA(int homeFGA) {
		this.homeFGA = homeFGA;
	}
	public double getHomeFTP() {
		return homeFTP;
	}
	public void setHomeFTP(double homeFTP) {
		this.homeFTP = homeFTP;
	}
	public int getHome3PtM() {
		return home3PtM;
	}
	public void setHome3PtM(int home3PtM) {
		this.home3PtM = home3PtM;
	}
	public int getHome3PtA() {
		return home3PtA;
	}
	public void setHome3PtA(int home3PtA) {
		this.home3PtA = home3PtA;
	}
	public double getHome3PtP() {
		return home3PtP;
	}
	public void setHome3PtP(double home3PtP) {
		this.home3PtP = home3PtP;
	}
	public int getHomeOffReb() {
		return homeOffReb;
	}
	public void setHomeOffReb(int homeOffReb) {
		this.homeOffReb = homeOffReb;
	}
	public int getHomeDefReb() {
		return homeDefReb;
	}
	public void setHomeDefReb(int homeDefReb) {
		this.homeDefReb = homeDefReb;
	}
	public int getHomeReb() {
		return homeReb;
	}
	public void setHomeReb(int homeReb) {
		this.homeReb = homeReb;
	}
	public int getHomeAssists() {
		return homeAssists;
	}
	public void setHomeAssists(int homeAssists) {
		this.homeAssists = homeAssists;
	}
	public int getHomeFouls() {
		return homeFouls;
	}
	public void setHomeFouls(int homeFouls) {
		this.homeFouls = homeFouls;
	}
	public int getHomeSteals() {
		return homeSteals;
	}
	public void setHomeSteals(int homeSteals) {
		this.homeSteals = homeSteals;
	}
	public int getHomeTO() {
		return homeTO;
	}
	public void setHomeTO(int homeTO) {
		this.homeTO = homeTO;
	}
	public int getHomeBlks() {
		return homeBlks;
	}
	public void setHomeBlks(int homeBlks) {
		this.homeBlks = homeBlks;
	}
	public int getAwayFBPts() {
		return awayFBPts;
	}
	public void setAwayFBPts(int awayFBPts) {
		this.awayFBPts = awayFBPts;
	}
	public int getAwayPtsinPaint() {
		return awayPtsinPaint;
	}
	public void setAwayPtsinPaint(int awayPtsinPaint) {
		this.awayPtsinPaint = awayPtsinPaint;
	}
	public int getAwayBiggestLead() {
		return awayBiggestLead;
	}
	public void setAwayBiggestLead(int awayBiggestLead) {
		this.awayBiggestLead = awayBiggestLead;
	}
	public int getAway2ndChancePts() {
		return away2ndChancePts;
	}
	public void setAway2ndChancePts(int away2ndChancePts) {
		this.away2ndChancePts = away2ndChancePts;
	}
	public int getAwayPtsFrmTurnovers() {
		return awayPtsFrmTurnovers;
	}
	public void setAwayPtsFrmTurnovers(int awayPtsFrmTurnovers) {
		this.awayPtsFrmTurnovers = awayPtsFrmTurnovers;
	}
	public int getAwayLongestRun() {
		return awayLongestRun;
	}
	public void setAwayLongestRun(int awayLongestRun) {
		this.awayLongestRun = awayLongestRun;
	}
	public double getHomeFGP() {
		return homeFGP;
	}
	public double getAwayFGP() {
		return awayFGP;
	}
	public void setHomeFGP(int homeFGP) {
		this.homeFGP = homeFGP;
	}
	public void setAwayFGP(int awayFGP) {
		this.awayFGP = awayFGP;
	}
	public int getAwayFGM() {
		return awayFGM;
	}
	public void setAwayFGM(int awayFGM) {
		this.awayFGM = awayFGM;
	}
	public int getAwayFGA() {
		return awayFGA;
	}
	public void setAwayFGA(int awayFGA) {
		this.awayFGA = awayFGA;
	}
	public double getAwayFTP() {
		return awayFTP;
	}
	public void setAwayFTP(double awayFTP) {
		this.awayFTP = awayFTP;
	}
	public int getAway3PtM() {
		return away3PtM;
	}
	public void setAway3PtM(int away3PtM) {
		this.away3PtM = away3PtM;
	}
	public int getAway3PtA() {
		return away3PtA;
	}
	public void setAway3PtA(int away3PtA) {
		this.away3PtA = away3PtA;
	}
	public double getAway3PtP() {
		return away3PtP;
	}
	public void setAway3PtP(double away3PtP) {
		this.away3PtP = away3PtP;
	}
	public int getAwayOffReb() {
		return awayOffReb;
	}
	public void setAwayOffReb(int awayOffReb) {
		this.awayOffReb = awayOffReb;
	}
	public int getAwayDefReb() {
		return awayDefReb;
	}
	public void setAwayDefReb(int awayDefReb) {
		this.awayDefReb = awayDefReb;
	}
	public int getAwayReb() {
		return awayReb;
	}
	public void setAwayReb(int awayReb) {
		this.awayReb = awayReb;
	}
	public int getAwayAssists() {
		return awayAssists;
	}
	public void setAwayAssists(int awayAssists) {
		this.awayAssists = awayAssists;
	}
	public int getAwayFouls() {
		return awayFouls;
	}
	public void setAwayFouls(int awayFouls) {
		this.awayFouls = awayFouls;
	}
	public int getAwaySteals() {
		return awaySteals;
	}
	public void setAwaySteals(int awaySteals) {
		this.awaySteals = awaySteals;
	}
	public int getAwayTO() {
		return awayTO;
	}
	public void setAwayTO(int awayTO) {
		this.awayTO = awayTO;
	}
	public int getAwayBlks() {
		return awayBlks;
	}
	public void setAwayBlks(int awayBlks) {
		this.awayBlks = awayBlks;
	}
	
	public String toString() {
		return dateString+": "+awayTeam.getAbbrev()+" @ "+ homeTeam.getAbbrev();
	}
	@Override
	public int compareTo(Game g) {
		if(getDate() == null || g.getDate() == null) {
			return 0;
		}
		return getDate().compareTo(g.getDate());
	}
}
