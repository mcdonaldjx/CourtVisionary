import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Period;

public class Player  implements Comparable<Player>{
	String firstName;
	String lastName;
	String fullName;
	String country;
	String born;
	String position;
	String college;
	Team playsfor = null;
	LocalDate birthDate;
	int age; //calculate by subtracting current date from birthdate
	int yearsPro;
	int id = -1;
	double heightM;
	double weightKilo;
	boolean active = false;
	int heightFt;
	int heightIn;
	double weight;
	int jerseyNum = -1;
	int teamId;
	int rookieYear;
	private boolean statsFilled = false;
	/*Stats below*/
	int gamesPlayed= 0; //Games played
	int totalPoints= 0;
	double totalMinutesPlayed= 0;
	int totalFieldGoalsMade= 0;
	int totalFieldGoalAttempted= 0;
	int totalFreeThrowsMade= 0;
	int totalFreeThrowsAttempted= 0;
	int totalFreeThrowPoints= 0;
	int total3ptMade= 0;
	int total3ptAttempted= 0;
	int total3ptPoints= 0;
	int totalOffensiveRebounds= 0;
	int totalDefensiveRebounds= 0;
	int totalRebounds= 0;
	int totalAssists= 0;
	int totalSteals= 0;
	int totalBlocks= 0;
	/*Career Averages*/
	double avgPoints= 0;
	double avgMinutesPlayed= 0;
	double avgFieldGoalsMade= 0;
	double avgFieldGoalsAttempted= 0;
	double avgFreeThrowsMade= 0;
	double avgFreeThrowsAttempted= 0;
	double avgFreeThrowPoints= 0;
	double avg3ptMade= 0;
	double avg3ptAttempted= 0;
	double avgOffensiveRebounds= 0;
	double avgDefensiveRebounds= 0;
	double avgRebounds= 0;
	double avgAssists= 0;
	double avgSteals= 0;
	double avgBlocks= 0;
	
	public Player(String response) {
		if(response == null) {
		}
		else {
			response = response.replaceAll("null,","\"null\",");
			//response = response.replaceAll("\\","");
			//////System.out.println("Response:"+response);
			String[] statsRaw = response.split(",");
			for(int i =0; i < statsRaw.length; i++) {
				////System.out.println("\t"+i+": "+statsRaw[i]);
				if(i == 2 || i == 3 || i == 6 || i == 12 || i == 13 || i == 9) { //Integer
					statsRaw[i] = statsRaw[i].substring(statsRaw[i].lastIndexOf(":")+1).replaceAll( "[\\D]", "").trim();
				}
				else if(i == 7) {
					statsRaw[i] = statsRaw[i].substring(statsRaw[i].lastIndexOf(":")+1).replaceAll( "[^-0-9]+","").trim();
				}
				else if(i == 10 || i == 11) { //Double
					statsRaw[i] = statsRaw[i].substring(statsRaw[i].lastIndexOf(":")+1).replaceAll( "[^\\d.]", "").trim();
				}
				else { //Just letters
					statsRaw[i] = statsRaw[i].substring(statsRaw[i].lastIndexOf(":")+1).replaceAll( "[^\\'|a-zA-Z| |.|-]", "").trim();
				}
				statsRaw[i] = statsRaw[i].substring(statsRaw[i].lastIndexOf(":")+1).replaceAll( "\\\"", "").trim();
				////////System.out.println(statsRaw[i]);
			}
			////////System.out.println("StatsRaw length = "+statsRaw.length);
			if(statsRaw[0].length() > 1) {
				firstName = statsRaw[0];
			}
			
			//////System.out.println("\tFirst Name: "+firstName);
			lastName = statsRaw[1];
			//////System.out.println("\tLast Name: "+lastName);
			if(!statsRaw[2].isEmpty() && !statsRaw[2].contains("null")) {
				teamId = Integer.parseInt(statsRaw[2]);
			}
			//////System.out.println("\tTeam ID: "+teamId);
			//////System.out.println("StatsRaw[3] = "+statsRaw[3]);
			if(!statsRaw[3].isEmpty() && !statsRaw[3].contains("null"))
				yearsPro  = Integer.parseInt(statsRaw[3]);
			//////System.out.println("\tYears Pro: "+yearsPro);
			
			college  = statsRaw[4];
			//////System.out.println("\tCollege: "+college);
			country  = statsRaw[5];
			//////System.out.println("\tCountry: "+country);
			statsRaw[6] = statsRaw[6].replaceAll(" ","");
			if(!statsRaw[6].isEmpty() && !statsRaw[6].contains("null"))
				id  = Integer.parseInt(statsRaw[6]);
			//////System.out.println("\tID: "+id);
			born = statsRaw[7].replaceAll(" ",""); 
			//////System.out.println("\tStatsRaw[7]: "+statsRaw[7]);
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				birthDate = LocalDate.parse(born, formatter);
				////System.out.println("\t"+birthDate);
				Period p = Period.between(birthDate,LocalDate.now());
				age = p.getYears();
			}
			catch(Exception e) {
				age = -1;
			}
			//////System.out.println("\tBorn: "+born);
			statsRaw[10].replaceAll(" ","");
			if(statsRaw[9].length() > 0) {
				rookieYear = Integer.parseInt(statsRaw[9]);
			}
			if(!statsRaw[10].isEmpty() && !statsRaw[10].contains("null")) {
				heightM  = Double.parseDouble(statsRaw[10]);
				double imperialHeight = heightM * 3.2808;
				heightFt = (int)imperialHeight;
				heightIn = (int)(imperialHeight - heightFt);
			}
			//////System.out.println("\tHeight in Meters: "+heightM);
			statsRaw[11] = statsRaw[11].replaceAll(" ","");
			if(!statsRaw[11].contains("null") && !statsRaw[11].isEmpty()) {
				weightKilo  =  Double.parseDouble(statsRaw[11]);
				weight = weightKilo * 2.2046;
				weight =  Math.round(weight * 100.0) / 100.0;
				
			}
			//////System.out.println("\tWeight in Kilo: "+weightKilo);
			statsRaw[12] = statsRaw[12].replaceAll(" ","");
			if(!statsRaw[12].contains("null") && !statsRaw[12].isEmpty()) {
				jerseyNum  = Integer.parseInt(statsRaw[12]);
			}
			//////System.out.println("\tJersey Num: "+jerseyNum);
			if(statsRaw[13].contains("1")) {
				active = true;
			}
			//////System.out.println("\tActive: "+Boolean.toString(active));
			String posTemp = statsRaw[statsRaw.length-1];
			if(posTemp.contains("-")) {
				posTemp.replaceAll("-","/");
				posTemp = position;
			}
			else if(!posTemp.contains("-") && (posTemp.contains("G") || posTemp.contains("F") || posTemp.contains("C"))){
				position = posTemp;
			}
			else {
				position = "N/A";
			}
			//////System.out.println("\tPosition: "+position);
		}
		
	}
	public String getFirstName() {
		if(firstName != null) {
			return firstName;
		}
		else {
			return lastName;
		}
	}
	public String getLastName() {
		return lastName;
	}
	public String getFullName() {
		if(firstName == (null)) {
			return lastName;
		}
		else if(lastName == (null)) {
			return firstName;
		}
		else {
			return firstName + " " + lastName;
		}
		
	}
	public String getPosition() {
		return position;
	}
	public Team getPlaysfor() {
		return playsfor;
	}
	public int getAge() {
		return age;
	}
	public int getHeightFt() {
		return heightFt;
	}
	public int getHeightIn() {
		return heightIn;
	}
	public double getWeight() {
		return weight;
	}
	public int getJerseyNum() {
		return jerseyNum;
	}
	public int getTeamId() {
		return teamId;
	}
	public String getBorn() {
		return born;
	}
	public String getCollege() {
		return college;
	}
	public int getYearsPro() {
		return yearsPro;
	}
	public int getId() {
		return id;
	}
	public double getHeightM() {
		return heightM;
	}
	public boolean isActive() {
		return active;
	}
	public int getRookieYear() {
		return rookieYear;
	}
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	public double getTotalMinutesPlayed() {
		return totalMinutesPlayed;
	}
	public int getTotalFieldGoalsMade() {
		return totalFieldGoalsMade;
	}
	public double getTotalFieldGoalAttempted() {
		return totalFieldGoalAttempted;
	}
	public int getTotalFreeThrowsMade() {
		return totalFreeThrowsMade;
	}
	public int getTotalFreeThrowsAttempted() {
		return totalFreeThrowsAttempted;
	}
	public int getTotalFreeThrowPoints() {
		return totalFreeThrowPoints;
	}
	public int getTotal3ptMade() {
		return total3ptMade;
	}
	public int getTotal3ptAttempted() {
		return total3ptAttempted;
	}
	public int getTotal3ptPoints() {
		return total3ptPoints;
	}
	public int getTotalOffensiveRebounds() {
		return totalOffensiveRebounds;
	}
	public int getTotalDefensiveRebounds() {
		return totalDefensiveRebounds;
	}
	public int getTotalRebounds() {
		return totalRebounds;
	}
	public int getTotalAssists() {
		return totalAssists;
	}
	public int getTotalSteals() {
		return totalSteals;
	}
	public int getTotalBlocks() {
		return totalBlocks;
	}
	public double getAvgPoints() {
		return avgPoints;
	}
	public double getAvgMinutesPlayed() {
		return avgMinutesPlayed;
	}
	public double getAvgFieldGoalsMade() {
		return avgFieldGoalsMade;
	}
	public double getAvgFieldGoalsAttempted() {
		return avgFieldGoalsAttempted;
	}
	public double getAvgFreeThrowsMade() {
		return avgFreeThrowsMade;
	}
	public double getAvgFreeThrowsAttempted() {
		return avgFreeThrowsAttempted;
	}
	public double getAvgFreeThrowPoints() {
		return avgFreeThrowPoints;
	}
	public double getAvg3ptMade() {
		return avg3ptMade;
	}
	public double getAvg3ptAttempted() {
		return avg3ptAttempted;
	}
	public double getAvgOffensiveRebounds() {
		return avgOffensiveRebounds;
	}
	public double getAvgDefensiveRebounds() {
		return avgDefensiveRebounds;
	}
	public double getAvgTotalRebounds() {
		return avgRebounds;
	}
	public double getAvgAssists() {
		return avgAssists;
	}
	public double getAvgSteals() {
		return avgSteals;
	}
	public double getAvgBlocks() {
		return avgBlocks;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	public void setTotalMinutesPlayed(double totalMinutesPlayed) {
		this.totalMinutesPlayed = totalMinutesPlayed;
	}
	public void setTotalFieldGoalsMade(int totalFieldGoalsMade) {
		this.totalFieldGoalsMade = totalFieldGoalsMade;
	}
	public void setTotalFieldGoalAttempted(int totalFieldGoalAttempted) {
		this.totalFieldGoalAttempted = totalFieldGoalAttempted;
	}
	public void setTotalFreeThrowsMade(int totalFreeThrowsMade) {
		this.totalFreeThrowsMade = totalFreeThrowsMade;
	}
	public void setTotalFreeThrowsAttempted(int totalFreeThrowsAttempted) {
		this.totalFreeThrowsAttempted = totalFreeThrowsAttempted;
	}
	public void setTotalFreeThrowPoints(int totalFreeThrowPoints) {
		this.totalFreeThrowPoints = totalFreeThrowPoints;
	}
	public void setTotal3ptMade(int total3ptMade) {
		this.total3ptMade = total3ptMade;
	}
	public void setTotal3ptAttempted(int total3ptAttempted) {
		this.total3ptAttempted = total3ptAttempted;
	}
	public void setTotal3ptPoints(int total3ptPoints) {
		this.total3ptPoints = total3ptPoints;
	}
	public void setTotalOffensiveRebounds(int totalOffensiveRebounds) {
		this.totalOffensiveRebounds = totalOffensiveRebounds;
	}
	public void setTotalDefensiveRebounds(int totalDefensiveRebounds) {
		this.totalDefensiveRebounds = totalDefensiveRebounds;
	}
	public void setTotalRebounds(int totalTotalRebounds) {
		this.totalRebounds = totalTotalRebounds;
	}
	public void setTotalAssists(int totalAssists) {
		this.totalAssists = totalAssists;
	}
	public void setTotalSteals(int totalSteals) {
		this.totalSteals = totalSteals;
	}
	public void setTotalBlocks(int totalBlocks) {
		this.totalBlocks = totalBlocks;
	}
	public void setAvgPoints(double avgPoints) {
		this.avgPoints = avgPoints / gamesPlayed;
	}
	public void setPlaysFor(Team t) {
		playsfor = t;
	}	
	public void setAverages() {
		avgMinutesPlayed = (double) totalMinutesPlayed / gamesPlayed;
		////System.out.println("Total Minutes: "+totalMinutesPlayed);
		avgFieldGoalsMade = (double) totalFieldGoalsMade / gamesPlayed;
		avgFieldGoalsAttempted = (double) totalFieldGoalAttempted / gamesPlayed;
		avgFreeThrowsMade = (double) totalFreeThrowsMade / gamesPlayed;
		avgFreeThrowsAttempted = (double) totalFreeThrowsAttempted / gamesPlayed;
		avg3ptMade = (double) total3ptMade / gamesPlayed;
		avg3ptAttempted = (double) total3ptAttempted / gamesPlayed;
		avgOffensiveRebounds = (double) totalOffensiveRebounds / gamesPlayed;
		avgDefensiveRebounds = (double) totalDefensiveRebounds / gamesPlayed;
		avgRebounds = (double) totalRebounds / gamesPlayed;
		avgAssists = (double) totalAssists / gamesPlayed;
		avgSteals = (double) totalSteals / gamesPlayed;
		avgBlocks = (double) totalBlocks / gamesPlayed;
		statsFilled = true;
	}

	public String toString() {
		if(firstName != null) {
			return firstName + " " + lastName;
		}
		else {
			return lastName;
		}
	}
	@Override
	public int compareTo(Player p2) {
		int result = getLastName().compareToIgnoreCase(p2.getLastName());
		if(result != 0) {
			return 0;
		}
		return getFirstName().compareToIgnoreCase(p2.getFirstName());
	}
	public String getCountry() {
		return country;
	}
	
	public boolean areStatsFilled() {
		return statsFilled;
	}
	
	public void populateStats(int gamesPlayed, String[] gameStats) {
		for(int i = 0; i < gameStats.length; i++) {
			String[] tempStats = gameStats[i].substring(gameStats[i].indexOf(",")+1).split(",");
			//////System.out.println("Game "+i+":");
			for(int j = 0; j < tempStats.length; j++) {
				String s = tempStats[j].substring(0,tempStats[j].lastIndexOf("\""));
				s = s.replaceAll("\"","");
				String stat = s.substring(s.indexOf(":")+1);
				//////System.out.println("\t"+s);
				//Grab whatever is in quotes
				
				if(s.contains("points") && !stat.equals(null) && !stat.equals("")) {
					totalPoints += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
					
				}
				else if(s.contains("min") && !stat.equals(null) && !stat.equals("")) { //Minutes were/weren't played
					if(!stat.equals(null) && stat.length() >= 1) {
						int secondStart = stat.lastIndexOf(":");
						if(secondStart != -1) {
							
							totalMinutesPlayed += Double.parseDouble(stat.substring(0,secondStart));
							Double seconds = Double.parseDouble(stat.substring(secondStart+1))/60.0;
							totalMinutesPlayed += seconds;
						}
						else {
							totalMinutesPlayed += Double.parseDouble(stat);
						}
					}
					
				}
				else if(s.contains("fgm") && !stat.equals(null) && !stat.equals("")) {
					
					totalFieldGoalsMade += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("fga") && !stat.equals(null) && !stat.equals("")) {
					totalFieldGoalAttempted += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("ftm") && !stat.equals(null) && !stat.equals("")) {
					totalFreeThrowsMade += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("fta") && !stat.equals(null) && !stat.equals("")) {
					totalFreeThrowsAttempted += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("tpm") && !stat.equals(null) && !stat.equals("")) {
					total3ptMade += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("tpa") && !stat.equals(null) && !stat.equals("")) {
					total3ptAttempted += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("offReb") && !stat.equals(null) && !stat.equals("")) {
					totalOffensiveRebounds += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("defReb") && !stat.equals(null) && !stat.equals("")) {
					totalDefensiveRebounds += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("totReb") && !stat.equals(null) && !stat.equals("")) {
					totalRebounds += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("assists") && !stat.equals(null) && !stat.equals("")) {
					totalAssists += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("steals") && !stat.equals(null) && !stat.equals("")) {
					totalSteals += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
				else if(s.contains("blocks") && !stat.equals(null) && !stat.equals("")) {
					totalBlocks += Integer.parseInt(s.substring(s.lastIndexOf(":")+1));
				}
			}
		}
		this.gamesPlayed = gamesPlayed;
		setAverages();
		
		
	}
}
