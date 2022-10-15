import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.awt.event.ItemEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class CourtVisionGUI {
	public JFrame GUI = new JFrame();
	public List<Player> playerList = new ArrayList<Player>();
	public List<Team> teamList = new ArrayList<Team>();
	public List<Game> gameList = new ArrayList<Game>();
	public Player playerA;
	public Player playerB;
	public Team teamA;
	public Team teamB;
	private JTable statTable = new JTable();
	private JTable bioTable = new JTable();
	private JLabel singleTeamLogo = new JLabel();
	private DefaultComboBoxModel<Player> singlePlayerModel = new DefaultComboBoxModel<Player>();
	private DefaultComboBoxModel<Team> singleTeamModel = new DefaultComboBoxModel<Team>();
	private DefaultComboBoxModel<Game> singleGameModel = new DefaultComboBoxModel<Game>();
	private DefaultListModel<Player> rosterList = new DefaultListModel<Player>();
	private DefaultTableModel bioTableModel = new DefaultTableModel();
	private DefaultTableModel statTableModel = new DefaultTableModel();
	private DefaultTableModel gameBoxScoreModel = new DefaultTableModel(24,2);
	private JTable teamTable = new JTable();
	int mode = 1;
	private JTable gameTable;
	private JTable compPlayerTable;
	//Comparison Models
	private DefaultComboBoxModel<Player> playerAModel = new DefaultComboBoxModel<Player>();
	private DefaultComboBoxModel<Player> playerBModel = new DefaultComboBoxModel<Player>();
	private DefaultComboBoxModel<Team> teamAModel = new DefaultComboBoxModel<Team>();
	private DefaultComboBoxModel<Team> teamBModel = new DefaultComboBoxModel<Team>();
	
	
	
	public CourtVisionGUI() {
		initialize();
	}

/**
 * Initialize the contents of the frame.
 */
public void initialize() {
	GUI.setForeground(Color.BLACK);
	GUI.setResizable(false);
	GUI.setTitle("Court Visionary");
	GUI.setBackground(Color.BLACK);
	GUI.setBounds(100, 100, 780, 486);
	GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	GUI.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
	
	
	
	JTabbedPane modeTabs = new JTabbedPane(JTabbedPane.TOP);
	//modeTabs.setSelectedIndex(0);
	JPanel compPanel = new JPanel();
	compPanel.setBackground(Color.DARK_GRAY);
	
	JPanel singleLPanel = new JPanel();
	singleLPanel.setBackground(Color.DARK_GRAY);
	modeTabs.addTab("Single Lookup", null, singleLPanel, null);
	singleLPanel.setLayout(null);
	
	JPanel singleSearchPanel = new JPanel();
	singleSearchPanel.setBounds(7, 34, 743, 377);
	singleSearchPanel.setBorder(null);
	singleSearchPanel.setBackground(Color.DARK_GRAY);
	singleLPanel.add(singleSearchPanel);
	
	CardLayout cl_singleSearchPanel = new CardLayout(); 
	singleSearchPanel.setLayout(cl_singleSearchPanel);
	
	JRadioButton singleTeamSearch = new JRadioButton("Teams");
	singleTeamSearch.setBounds(341, 7, 75, 23);
	singleTeamSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(mode != 2) {
				mode = 2;
				cl_singleSearchPanel.show(singleSearchPanel, "singleTeam");
			}
		}
	});
	singleTeamSearch.setForeground(Color.WHITE);
	singleTeamSearch.setBackground(Color.DARK_GRAY);
	singleLPanel.add(singleTeamSearch);
	
	
	JRadioButton singleGameSearch = new JRadioButton("Games");
	singleGameSearch.setBounds(549, 7, 75, 23);
	singleGameSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(mode != 3) {
				mode = 3;
				cl_singleSearchPanel.show(singleSearchPanel, "singleGame");
			}
		}
	});
	singleGameSearch.setForeground(Color.WHITE);
	singleGameSearch.setBackground(Color.DARK_GRAY);
	singleLPanel.add(singleGameSearch);
	
	
	
	
	
	modeTabs.addTab("Compare", compPanel);
	
	JRadioButton singlePlayerSearch = new JRadioButton("Players",true);
	singlePlayerSearch.setBounds(133, 7, 75, 23);
	
	singlePlayerSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(mode != 1) {
				mode = 1;
				cl_singleSearchPanel.show(singleSearchPanel, "singlePlayer");
			}
		}
	});
	singlePlayerSearch.setForeground(Color.WHITE);
	singlePlayerSearch.setBackground(Color.DARK_GRAY);
	singleLPanel.add(singlePlayerSearch);
	
	ButtonGroup singleRadios = new ButtonGroup();
	singleRadios.add(singlePlayerSearch);
	singleRadios.add(singleTeamSearch);
	singleRadios.add(singleGameSearch);
	compPanel.setLayout(null);
	
	
	
	
	JPanel compSearch = new JPanel();
	compSearch.setBounds(7, 34, 743, 377);
	compSearch.setBorder(null);
	compSearch.setBackground(Color.DARK_GRAY);
	compPanel.add(compSearch);
	
	CardLayout compareCardLayout = new CardLayout();
	compSearch.setLayout(compareCardLayout);
	
	modeTabs.setForegroundAt(1, Color.BLACK);
	modeTabs.setBackgroundAt(1, Color.DARK_GRAY);
	modeTabs.setBackground(Color.DARK_GRAY);
	GUI.getContentPane().add(modeTabs, "cell 0 0,grow");
	
	JRadioButton compPlayerSearch = new JRadioButton("Players");
	compPlayerSearch.setSelected(true);
	compPlayerSearch.setBounds(199, 7, 80, 23);
	compPlayerSearch.setForeground(Color.WHITE);
	compPlayerSearch.setBackground(Color.DARK_GRAY);
	compPanel.add(compPlayerSearch,"comparePlayers");
	compPlayerSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(mode != 4) {
				mode = 4;
				compareCardLayout.show(compSearch, "comparePlayers");
			}
		}
	});
	
	
	JRadioButton compTeamSearch = new JRadioButton("Teams");
	compTeamSearch.setBounds(478, 7, 80, 23);
	compTeamSearch.setForeground(Color.WHITE);
	compTeamSearch.setBackground(Color.DARK_GRAY);
	compPanel.add(compTeamSearch,"compareTeams");
	
	compTeamSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(mode != 5) {
				mode = 5;
				compareCardLayout.show(compSearch, "compareTeams");
			}
		}
	});
	
	
	
	ButtonGroup compRadios = new ButtonGroup();
	compRadios.add(compPlayerSearch);
	compRadios.add(compTeamSearch);
	
	
	JPanel singlePlayerPanel = new JPanel();
	singlePlayerPanel.setBackground(Color.DARK_GRAY);
	JPanel singleTeamPanel = new JPanel();
	singleTeamPanel.setBackground(Color.DARK_GRAY);
	JPanel singleGamePanel = new JPanel();
	singleGamePanel.setBackground(Color.DARK_GRAY);
	singleSearchPanel.add(singlePlayerPanel,"singlePlayer");
	singlePlayerPanel.setLayout(null);
	
	JTabbedPane playerTab = new JTabbedPane(JTabbedPane.TOP);
	playerTab.setBounds(7, 180, 727, 192);
	singlePlayerPanel.add(playerTab);
	
	
	JPanel bioTab = new JPanel();
	bioTab.setForeground(Color.WHITE);
	bioTab.setBackground(Color.BLACK);
	playerTab.addTab("Bio", null, bioTab, null);
	bioTab.setLayout(null);
	
	bioTable = new JTable();
	bioTable.setBounds(10, 11, 702, 142);
	bioTable.setModel(bioTableModel = new DefaultTableModel(
		new Object[][] {
			{"Name:", null},
			{"Country:", null},
			{"Age:", null},
			{"College/Origin:", null},
			{"Rookie Year:", null},
			{"Years Pro:", null},
			{"Height (Ft in):", null},
			{"Weight (lbs):", null},
		},
		new String[] {
			"Property", "Value"
		}
	) {
		boolean[] columnEditables = new boolean[] {
			false, false
		};
		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}
	});
	bioTable.setForeground(Color.WHITE);
	bioTable.setBackground(Color.BLACK);
	bioTab.add(bioTable);
	
	
	JPanel statsTab = new JPanel();
	statsTab.setForeground(Color.WHITE);
	statsTab.setBackground(Color.BLACK);
	playerTab.addTab("Career Stats", null, statsTab, null);
	
	statsTab.setLayout(null);
	
	statTable = new JTable();
	statTable.setBounds(10, 11, 702, 142);
	statTable.setModel(statTableModel = new DefaultTableModel(
		new Object[][] {
			{"Games Played", null},
			{"Minutes Played", null},
			{"Field Goals Made", null},
			{"Field Goals Attempts", null},
			{"Free Throws Made", null},
			{"Free Throws Attempts", null},
			{"3 Pt Made", null},
			{"3 Pt Attempts", null},
			{"Offensive Rebounds", null},
			{"Defensive Rebounds", null},
			{"Rebounds", null},
			{"Assists", null},
			{"Steals", null},
			{"Blocks", null},
		},
		new String[] {
			"Stat", "Career Average (per game)"
		}
	) {
		boolean[] columnEditables = new boolean[] {
			false, false
		};
		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}
	});
	statTable.getColumnModel().getColumn(0).setPreferredWidth(122);
	statTable.getColumnModel().getColumn(1).setPreferredWidth(99);
	statTable.setForeground(Color.WHITE);
	statTable.setBackground(Color.BLACK);
	statTable.setRowSelectionAllowed(false);
	statTable.setEnabled(true);
	statTable.setColumnSelectionAllowed(false);
	statTable.setCellSelectionEnabled(false);
	statTable.setForeground(Color.WHITE);
	statTable.setBackground(Color.BLACK);
	statTable.setBounds(0, 0, 722, 164);
	
	JScrollPane statScrollPane = new JScrollPane(statTable);
	statScrollPane.setBounds(0, 0, 722, 164);
	statsTab.add(statScrollPane);

	JLabel singlePlayerLName = new JLabel("",SwingConstants.CENTER);
	singlePlayerLName.setForeground(Color.WHITE);
	singlePlayerLName.setBounds(294, 151, 154, 22);
	singlePlayerPanel.add(singlePlayerLName);		
	
	JLabel singlePlayerFName = new JLabel("",SwingConstants.CENTER);
	singlePlayerFName.setFont(new Font("Tahoma", Font.BOLD, 18));
	singlePlayerFName.setForeground(Color.WHITE);
	singlePlayerFName.setBounds(294, 127, 154, 22);
	singlePlayerPanel.add(singlePlayerFName);	
	
	JLabel singlePosition = new JLabel("Position",SwingConstants.CENTER);
	singlePosition.setForeground(Color.WHITE);
	singlePosition.setBounds(469, 155, 46, 14);
	singlePlayerPanel.add(singlePosition);
	singleSearchPanel.add(singleTeamPanel, "singleTeam");
	singleTeamPanel.setLayout(null);
	
	
	
	JLabel singlePlayerLogo = new JLabel("",SwingConstants.CENTER);
	singlePlayerLogo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
	singlePlayerLogo.setForeground(Color.WHITE);
	singlePlayerLogo.setBounds(334, 49, 75, 75);
	singlePlayerPanel.add(singlePlayerLogo);
	singleSearchPanel.add(singleGamePanel, "singleGame");
	singleGamePanel.setLayout(null);
	
	JLabel singleCity = new JLabel("City");
	singleCity.setHorizontalAlignment(SwingConstants.CENTER);
	singleCity.setForeground(Color.WHITE);
	singleCity.setBounds(320, 44, 103, 14);
	singleTeamPanel.add(singleCity);
	
	JLabel singleTeamName = new JLabel("Name");
	singleTeamName.setForeground(Color.WHITE);
	singleTeamName.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamName.setBounds(320, 59, 103, 14);
	singleTeamPanel.add(singleTeamName);
	JLabel singleTeamAbbrev = new JLabel("Abbrev");
	singleTeamAbbrev.setForeground(Color.WHITE);
	singleTeamAbbrev.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamAbbrev.setBounds(433, 59, 46, 14);
	singleTeamPanel.add(singleTeamAbbrev);
	
	JLabel singleJersey = new JLabel("Jersey", SwingConstants.CENTER);
	singleJersey.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
	singleJersey.setForeground(Color.WHITE);
	singleJersey.setBounds(469, 131, 46, 14);
	singlePlayerPanel.add(singleJersey);
			
	
	JComboBox<Player> singlePlayerComboBox = new JComboBox<Player>();
	singlePlayerComboBox.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			playerA = (Player) singlePlayerComboBox.getSelectedItem();
			if(playerA != null) {
				if(playerA.getFirstName().length() > 0) {
					singlePlayerFName.setText(playerA.getFirstName());
				}
				singlePlayerLName.setText(playerA.getLastName());
				Image image = playerA.getPlaysfor().logo.getImage(); // transform it 
				Image transformImg = image.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				singlePlayerLogo.setIcon(new ImageIcon(transformImg));
				if(playerA.getPosition() == null || playerA.getPosition().length() < 1) {
					singlePosition.setText("N/A");
				}
				else {
					singlePosition.setText(playerA.getPosition());
				}
				if(playerA.getJerseyNum() > -1) {
					//singleJersey.setVisible(true);
					singleJersey.setText("#"+Integer.toString(playerA.getJerseyNum()));
				}
				else {
					//singleJersey.setVisible(false);
				}
				bioTableModel.setValueAt(playerA.getFullName(),0,1);
				bioTableModel.setValueAt(playerA.getCountry(),1,1);
				int tempAge = playerA.getAge();
				if(tempAge == -1) {
					bioTableModel.setValueAt("? years old",2,1);
				}
				else {
					bioTableModel.setValueAt(playerA.getAge()+" yrs old",2,1);
				}
				bioTableModel.setValueAt(playerA.getCollege(),3,1);
				bioTableModel.setValueAt(playerA.getRookieYear(),4,1);
				bioTableModel.setValueAt(playerA.getYearsPro()+" yrs",5,1);
				int tempInt = playerA.getHeightFt();
				if(tempInt == 0) {
					bioTableModel.setValueAt("??? ft ??? in",6,1);
				}
				else {
					bioTableModel.setValueAt(playerA.getHeightFt()+"ft "+playerA.getHeightIn()+" in",6,1);
				}
				double tempDouble = playerA.getWeight();
				if(tempDouble == 0) {
					bioTableModel.setValueAt("??? lbs",7,1);
				}
				else {
					bioTableModel.setValueAt(playerA.getWeight()+" lbs",7,1);
				}
			}
			//Getting career stats
			if(playerA.areStatsFilled() == false) {
				try {
					String hostURL = "https://api-nba-v1.p.rapidapi.com/";
			        String host = "api-nba-v1.p.rapidapi.com";
			        String rAPIKey =""; //INSERT API KEY FROM RAPIDAPI IN THESE QUOTES!
			        String returned;
			        Request request;
			        Response response;
			        OkHttpClient client = new OkHttpClient();   
			        request = new Request.Builder()
			        		.url(hostURL+"statistics/players/playerId/"+playerA.getId())
			        		.get()
			        		.addHeader("x-rapidapi-key", rAPIKey)
			            	.addHeader("x-rapidapi-host", host)
			            	.build();
			        response = client.newCall(request).execute();
			        returned = response.body().string();
			        int gamesPlayed = Integer.parseInt(returned.substring(returned.indexOf("\"results\":")+10, returned.indexOf(",\"filters\"")).trim());
			        String[] playerStatsRaw = returned.substring(returned.indexOf(":[{")).split("gameId\":");
			        playerA.populateStats(gamesPlayed,playerStatsRaw);
				}
				catch(IOException ioe) {
					
				}
			}
			DecimalFormat deciFormat = new DecimalFormat("######.###");
			statTableModel.setValueAt(deciFormat.format(playerA.getGamesPlayed())+" games",0,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgMinutesPlayed())+ " mins",1,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgFieldGoalsMade()),2,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgFieldGoalsAttempted()),3,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgFreeThrowsMade()),4,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgFreeThrowsAttempted()),5,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvg3ptMade()),6,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvg3ptAttempted()),7,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgOffensiveRebounds())+" rebounds",8,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgDefensiveRebounds())+" rebounds",9,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgTotalRebounds())+" rebounds",10,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgAssists())+" assists",11,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgSteals())+" steals",12,1);
			statTableModel.setValueAt(deciFormat.format(playerA.getAvgBlocks())+" blocks",13,1);
			
		}
		
	});
	singlePlayerComboBox.setBounds(294, 11, 154, 22);
	singlePlayerComboBox.setModel(singlePlayerModel);
	singlePlayerPanel.add(singlePlayerComboBox);
	

	
	teamTable = new JTable();
	teamTable.setForeground(Color.WHITE);
	teamTable.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	teamTable.setBounds(729, 359, -714, -148);
	//teamTable.addColumn("Jersey #","First Name","Last Name","Position");
	singleTeamPanel.add(teamTable);
	
	JPanel panel = new JPanel();
	panel.setBackground(Color.DARK_GRAY);
	panel.setBounds(242, 84, 258, 90);
	singleTeamPanel.add(panel);
	panel.setLayout(null);
	
	JLabel lblNewLabel_1 = new JLabel("Conference:");
	lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblNewLabel_1.setForeground(Color.WHITE);
	lblNewLabel_1.setBackground(Color.DARK_GRAY);
	lblNewLabel_1.setBounds(16, 12, 75, 26);
	panel.add(lblNewLabel_1);
	
	JLabel lblNewLabel_2 = new JLabel("Division:");
	lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblNewLabel_2.setForeground(Color.WHITE);
	lblNewLabel_2.setBounds(16, 50, 75, 26);
	panel.add(lblNewLabel_2);
	
	JLabel singleTeamConfLabel = new JLabel("Eastern");
	singleTeamConfLabel.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamConfLabel.setForeground(Color.WHITE);
	singleTeamConfLabel.setBounds(107, 18, 58, 14);
	panel.add(singleTeamConfLabel);
	
	JLabel singleTeamDivLabel = new JLabel("Southwest");
	singleTeamDivLabel.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamDivLabel.setForeground(Color.WHITE);
	singleTeamDivLabel.setBounds(107, 56, 58, 14);
	panel.add(singleTeamDivLabel);
	
	JLabel singleTeamConfSeed = new JLabel("10th");
	singleTeamConfSeed.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamConfSeed.setFont(new Font("Tahoma", Font.BOLD, 11));
	singleTeamConfSeed.setForeground(Color.WHITE);
	singleTeamConfSeed.setBounds(181, 18, 58, 14);
	panel.add(singleTeamConfSeed);
	
	JLabel singleTeamDivSeed = new JLabel("10th");
	singleTeamDivSeed.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamDivSeed.setForeground(Color.WHITE);
	singleTeamDivSeed.setFont(new Font("Tahoma", Font.BOLD, 11));
	singleTeamDivSeed.setBounds(181, 56, 58, 14);
	panel.add(singleTeamDivSeed);
	
	JLabel lblNewLabel_2_1 = new JLabel("Latest Record:");
	lblNewLabel_2_1.setForeground(Color.WHITE);
	lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblNewLabel_2_1.setBounds(294, 185, 92, 26);
	singleTeamPanel.add(lblNewLabel_2_1);
	
	JLabel singleTeamRecord = new JLabel("50W - 50L");
	singleTeamRecord.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamRecord.setForeground(Color.WHITE);
	singleTeamRecord.setBounds(390, 191, 58, 14);
	singleTeamPanel.add(singleTeamRecord);
	
	

	singleTeamLogo = new JLabel();
	singleTeamLogo.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamLogo.setBounds(10, 88, 200, 200);
	singleTeamPanel.add(singleTeamLogo);
	
	

	
	JPanel singleRosterPane = new JPanel();
	singleRosterPane.setBackground(Color.DARK_GRAY);
	singleRosterPane.setBounds(510, 27, 223, 322);
	singleTeamPanel.add(singleRosterPane);
	singleRosterPane.setLayout(null);
	
	JLabel singleTeamRoster = new JLabel("Active Roster:");
	singleTeamRoster.setBounds(71, 11, 80, 14);
	singleTeamRoster.setHorizontalAlignment(SwingConstants.CENTER);
	singleTeamRoster.setForeground(Color.WHITE);
	singleTeamRoster.setFont(new Font("Tahoma", Font.BOLD, 11));
	singleTeamRoster.setBackground(Color.DARK_GRAY);
	singleRosterPane.add(singleTeamRoster);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPane.setBounds(10, 38, 203, 273);
	singleRosterPane.add(scrollPane);
	
	JList<Player> singleRosterList = new JList<Player>();
	singleRosterList.setToolTipText("Double click to jump to player.");
	singleRosterList.setForeground(Color.WHITE);
	singleRosterList.setBackground(Color.BLACK);
	scrollPane.setViewportView(singleRosterList);
	singleRosterList.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent evt) {
	        JList<?> rosterList = (JList<?>)evt.getSource();
	        if (evt.getClickCount() == 2) {
	        	
	            // Double-click detected
	            int index = rosterList.locationToIndex(evt.getPoint());
	            playerA = (Player) rosterList.getModel().getElementAt(index);
	            singlePlayerComboBox.setSelectedItem(playerA);
	            mode = 1;
	            singlePlayerSearch.setSelected(true);
	            cl_singleSearchPanel.show(singleSearchPanel, "singlePlayer");
	        }
	    }
	});
	
	
	JComboBox<Team> singleTeamCombo = new JComboBox<Team>();
	singleTeamCombo.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Team t = (Team) singleTeamCombo.getSelectedItem();
			//set image of singleTeamLogo as team logo
			rosterList = new DefaultListModel<Player>();
			Image image = t.logo.getImage(); // transform it 
			Image transformImg = image.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			singleTeamLogo.setIcon(new ImageIcon(transformImg));
			singleCity.setText(t.getCity());
			singleTeamName.setText(t.getNickname());
			singleTeamAbbrev.setText(t.getAbbrev());
			singleTeamConfLabel.setText(t.getConference());
			singleTeamConfSeed.setText(t.getConfSeedOrd());
			singleTeamDivLabel.setText(t.getDivision());
			singleTeamDivSeed.setText(t.getDivSeedOrd());
			singleTeamRecord.setText(t.getWins()+"W - "+t.getLosses()+"L");
			
			for(Player p : t.roster) {
				////System.out.println(p.getFullName()+" added to rosterList");
				rosterList.addElement(p);
			}
			
			singleRosterList.setModel(rosterList);
		}
	});
	singleTeamCombo.setBounds(294, 11, 154, 22);
	singleTeamCombo.setModel(singleTeamModel);
	singleTeamPanel.add(singleTeamCombo);
	

	
	JLabel singleAwayLogo = new JLabel("");
	singleAwayLogo.setHorizontalAlignment(SwingConstants.CENTER);
	singleAwayLogo.setForeground(Color.WHITE);
	singleAwayLogo.setBounds(105, 20, 125, 125);
	singleGamePanel.add(singleAwayLogo);
	
	JLabel singleHomeLogo = new JLabel("");
	singleHomeLogo.setHorizontalAlignment(SwingConstants.CENTER);
	singleHomeLogo.setForeground(Color.WHITE);
	singleHomeLogo.setBounds(512, 20, 125, 125);
	singleGamePanel.add(singleHomeLogo);
	
	JLabel lblNewLabel = new JLabel("@");
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
	lblNewLabel.setForeground(Color.WHITE);
	lblNewLabel.setBounds(345, 108, 53, 46);
	singleGamePanel.add(lblNewLabel);
	
	JLabel singleAwayCity = new JLabel("AwayAbbrev");
	singleAwayCity.setHorizontalAlignment(SwingConstants.CENTER);
	singleAwayCity.setForeground(Color.WHITE);
	singleAwayCity.setBounds(105, 156, 125, 22);
	singleGamePanel.add(singleAwayCity);
	
	JLabel singleHomeCity = new JLabel("HomeAbbrev");
	singleHomeCity.setHorizontalAlignment(SwingConstants.CENTER);
	singleHomeCity.setForeground(Color.WHITE);
	singleHomeCity.setBounds(512, 156, 125, 22);
	singleGamePanel.add(singleHomeCity);
	
	JLabel awayScore = new JLabel("Score");
	awayScore.setFont(new Font("Tahoma", Font.BOLD, 21));
	awayScore.setHorizontalAlignment(SwingConstants.CENTER);
	awayScore.setForeground(Color.WHITE);
	awayScore.setBounds(238, 165, 97, 46);
	singleGamePanel.add(awayScore);
	
	JLabel homeScore = new JLabel("Score");
	homeScore.setHorizontalAlignment(SwingConstants.CENTER);
	homeScore.setForeground(Color.WHITE);
	homeScore.setFont(new Font("Tahoma", Font.BOLD, 21));
	homeScore.setBounds(396, 165, 97, 46);
	singleGamePanel.add(homeScore);
	
	JLabel singleGameDate = new JLabel("Date");
	singleGameDate.setFont(new Font("Tahoma", Font.BOLD, 11));
	singleGameDate.setHorizontalAlignment(SwingConstants.CENTER);
	singleGameDate.setForeground(Color.WHITE);
	singleGameDate.setBounds(323, 44, 97, 22);
	singleGamePanel.add(singleGameDate);

	JLabel singleAwayName = new JLabel("AwayAbbrev");
	singleAwayName.setHorizontalAlignment(SwingConstants.CENTER);
	singleAwayName.setForeground(Color.WHITE);
	singleAwayName.setBounds(105, 181, 125, 22);
	singleGamePanel.add(singleAwayName);
	
	JLabel singleHomeName = new JLabel("HomeAbbrev");
	singleHomeName.setHorizontalAlignment(SwingConstants.CENTER);
	singleHomeName.setForeground(Color.WHITE);
	singleHomeName.setBounds(512, 181, 125, 22);
	singleGamePanel.add(singleHomeName);
	
	
	JPanel singleGameTablePanel = new JPanel();
	singleGameTablePanel.setBounds(10, 214, 723, 152);
	singleGamePanel.add(singleGameTablePanel);
	singleGameTablePanel.setLayout(null);
	

	
	gameTable = new JTable();
	gameTable.setForeground(Color.WHITE);
	gameTable.setModel(gameBoxScoreModel = new DefaultTableModel(
		new Object[][] {
			{"Total Points", null, null},
			{"Fastbreak Points", null, null},
			{"Points in Paint", null, null},
			{"Second Chance Points", null, null},
			{"Points From Turnovers", null, null},
			{"Biggest Lead", null, null},
			{"Longest Run", null, null},
			{"Field Goals Made", null, null},
			{"Field Goals Attempted", null, null},
			{"Field Goal Percentage", null, null},
			{"Free Throws Made", null, null},
			{"Free Throws Attempted", null, null},
			{"Free Throw Percentage", null, null},
			{"3 Pt Made", null, null},
			{"3 Pt Attempted", null, null},
			{"3 Pt Percentage", null, null},
			{"Total Rebounds", null, null},
			{"Offensive Rebounds", null, null},
			{"Defensive Rebounds", null, null},
			{"Assists", null, null},
			{"Steals", null, null},
			{"Blocks", null, null},
			{"Fouls", null, null},
			{"Turnovers", null, null},
		},
		new String[] {
			"Stat", "Away Team", "Home Team"
		}
	) {
		boolean[] columnEditables = new boolean[] {
			false, false, false
		};
		public boolean isCellEditable(int row, int column) {
			return columnEditables[column];
		}
	});
	gameTable.getColumnModel().getColumn(0).setResizable(false);
	gameTable.getColumnModel().getColumn(0).setPreferredWidth(128);
	gameTable.getColumnModel().getColumn(1).setPreferredWidth(66);
	gameTable.getColumnModel().getColumn(2).setPreferredWidth(66);
	gameTable.setBackground(Color.BLACK);
	gameTable.setBounds(-2, 151, 726, -154);
		
	JScrollPane gameScrollPane = new JScrollPane(gameTable);
	gameScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	gameScrollPane.setBounds(0, 0, 724, 152);
	singleGameTablePanel.add(gameScrollPane);
	
	JComboBox<Game> gameComboBox = new JComboBox<Game>();
	gameComboBox.addItemListener(new ItemListener() {
		public void itemStateChanged(ItemEvent arg0) {
			Game g = (Game) gameComboBox.getSelectedItem();
			singleGameDate.setText(g.dateString);
			//Away Team
			Team awayTeam = g.getAwayTeam();
			Image image = awayTeam.logo.getImage(); // transform it 
			Image transformedImg = image.getScaledInstance(125, 125,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			image.getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH);
			singleAwayLogo.setIcon(new ImageIcon(transformedImg));
			singleAwayCity.setText(awayTeam.getCity());
			singleAwayName.setText(awayTeam.getNickname());
			awayScore.setText(Integer.toString(g.getAwayScore()));
			//Home Team
			Team homeTeam = g.getHomeTeam();
			image = homeTeam.logo.getImage(); // transform it 
			transformedImg = image.getScaledInstance(125, 125,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			image.getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH);
			singleHomeLogo.setIcon(new ImageIcon(transformedImg));
			singleHomeCity.setText(homeTeam.getCity());
			singleHomeName.setText(homeTeam.getNickname());
			homeScore.setText(Integer.toString(g.getHomeScore()));
			if(g.isDetailed() == false) {
				try { 
					String hostURL = "https://api-nba-v1.p.rapidapi.com/";
			        String host = "api-nba-v1.p.rapidapi.com";
			        String rAPIKey =""; //INSERT API KEY FROM RAPIDAPI IN THESE QUOTES!
			        String returned;
			        Request request;
			        Response response;
			        OkHttpClient client = new OkHttpClient();   
			        request = new Request.Builder()
			        		.url(hostURL+"statistics/games/gameId/"+g.getGameId())
			        		.get()
			        		.addHeader("x-rapidapi-key", rAPIKey)
			            	.addHeader("x-rapidapi-host", host)
			            	.build();
			        response = client.newCall(request).execute();
			        returned = response.body().string();
			       // //System.out.println("\n"+returned);
			        //String[] playerStatsRaw = returned.substring(returned.indexOf(":[{")).split("gameId\":");
			        
						//String returned = new String("{\"api\":{\"status\":200,\"message\":\"GET statistics/games/gameId/8244\",\"results\":2,\"filters\":[\"gameId\"],\"statistics\":[{\"gameId\":\"8244\",\"teamId\":\"6\",\"fastBreakPoints\":\"6\",\"pointsInPaint\":\"58\",\"biggestLead\":\"11\",\"secondChancePoints\":\"11\",\"pointsOffTurnovers\":\"19\",\"longestRun\":\"11\",\"points\":\"133\",\"fgm\":\"50\",\"fga\":\"92\",\"fgp\":\"54.3\",\"ftm\":\"19\",\"fta\":\"25\",\"ftp\":\"76.0\",\"tpm\":\"14\",\"tpa\":\"36\",\"tpp\":\"38.9\",\"offReb\":\"9\",\"defReb\":\"35\",\"totReb\":\"44\",\"assists\":\"34\",\"pFouls\":\"27\",\"steals\":\"9\",\"turnovers\":\"15\",\"blocks\":\"3\",\"plusMinus\":\"3\",\"min\":\"240:00\"},{\"gameId\":\"8244\",\"teamId\":\"41\",\"fastBreakPoints\":\"2\",\"pointsInPaint\":\"36\",\"biggestLead\":\"9\",\"secondChancePoints\":\"18\",\"pointsOffTurnovers\":\"23\",\"longestRun\":\"11\",\"points\":\"130\",\"fgm\":\"42\",\"fga\":\"84\",\"fgp\":\"50.0\",\"ftm\":\"32\",\"fta\":\"41\",\"ftp\":\"78.0\",\"tpm\":\"14\",\"tpa\":\"29\",\"tpp\":\"48.3\",\"offReb\":\"9\",\"defReb\":\"28\",\"totReb\":\"37\",\"assists\":\"27\",\"pFouls\":\"27\",\"steals\":\"8\",\"turnovers\":\"14\",\"blocks\":\"4\",\"plusMinus\":\"-3\",\"min\":\"240:00\"}]}}");
					String[] gameStats = returned.substring(returned.lastIndexOf("statistics")).replaceAll("\"","").split(",");
					g.populateGameDetails(gameStats);
						
				}
				catch(IOException ioe) {
					
				}
			}
			populateBoxScoreTable(gameTable, g);
			
		}
	});
	gameComboBox.setBounds(284, 11, 174, 22);
	gameComboBox.setModel(singleGameModel);
	singleGamePanel.add(gameComboBox);	
	

	

	
	JPanel compPlayers = new JPanel();
	compPlayers.setBackground(Color.DARK_GRAY);
	compSearch.add(compPlayers, "comparePlayers");
	JPanel compTeams = new JPanel();
	compTeams.setBackground(Color.DARK_GRAY);
	compSearch.add(compTeams, "compareTeams");
	compTeams.setLayout(null);	
	
	JPanel teamBPanel = new JPanel();
	teamBPanel.setLayout(null);
	teamBPanel.setBackground(Color.DARK_GRAY);
	teamBPanel.setBounds(382, 11, 351, 186);
	compTeams.add(teamBPanel);
	
	JLabel TeamBLabel = new JLabel("Team B:");
	TeamBLabel.setForeground(Color.WHITE);
	TeamBLabel.setBounds(55, 8, 61, 14);
	teamBPanel.add(TeamBLabel);
	
	JLabel teamBLogo = new JLabel("");
	teamBLogo.setHorizontalAlignment(SwingConstants.CENTER);
	teamBLogo.setBounds(125, 43, 100, 100);
	teamBPanel.add(teamBLogo);
	
	JLabel teamBCityLabel = new JLabel("");
	teamBCityLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	teamBCityLabel.setHorizontalAlignment(SwingConstants.CENTER);
	teamBCityLabel.setForeground(Color.WHITE);
	teamBCityLabel.setBounds(56, 154, 125, 14);
	teamBPanel.add(teamBCityLabel);
	
	JLabel teamBNameLabel = new JLabel("");
	teamBNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
	teamBNameLabel.setForeground(Color.WHITE);
	teamBNameLabel.setBounds(191, 154, 125, 14);
	teamBPanel.add(teamBNameLabel);
	
	JPanel compTeamResults = new JPanel();
	compTeamResults.setBackground(Color.DARK_GRAY);
	compTeamResults.setBounds(10, 208, 723, 158);
	compTeams.add(compTeamResults);
	compTeamResults.setLayout(null);
	
	JLabel compConfLabel = new JLabel("Conference:");
	compConfLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	compConfLabel.setHorizontalAlignment(SwingConstants.CENTER);
	compConfLabel.setForeground(Color.WHITE);
	compConfLabel.setBounds(319, 29, 81, 14);
	compTeamResults.add(compConfLabel);
	
	JLabel compDivLabel = new JLabel("Division:");
	compDivLabel.setHorizontalAlignment(SwingConstants.CENTER);
	compDivLabel.setForeground(Color.WHITE);
	compDivLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	compDivLabel.setBounds(319, 72, 81, 14);
	compTeamResults.add(compDivLabel);
	
	JLabel compRecordLabel = new JLabel("Record:");
	compRecordLabel.setHorizontalAlignment(SwingConstants.CENTER);
	compRecordLabel.setForeground(Color.WHITE);
	compRecordLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	compRecordLabel.setBounds(321, 115, 81, 14);
	compTeamResults.add(compRecordLabel);
	
	JLabel teamAConfLabel = new JLabel("");
	teamAConfLabel.setForeground(Color.WHITE);
	teamAConfLabel.setBounds(174, 29, 81, 14);
	compTeamResults.add(teamAConfLabel);
	
	JLabel teamBConfPlaceLabel = new JLabel("");
	teamBConfPlaceLabel.setForeground(Color.WHITE);
	teamBConfPlaceLabel.setBounds(609, 29, 46, 14);
	compTeamResults.add(teamBConfPlaceLabel);
	
	JLabel teamBDivLabel = new JLabel("");
	teamBDivLabel.setForeground(Color.WHITE);
	teamBDivLabel.setBounds(464, 72, 81, 14);
	compTeamResults.add(teamBDivLabel);
	
	JLabel teamBRecordLabel = new JLabel("");
	teamBRecordLabel.setForeground(Color.WHITE);
	teamBRecordLabel.setBounds(522, 115, 81, 14);
	compTeamResults.add(teamBRecordLabel);
	
	JLabel teamBConfLabel = new JLabel("");
	teamBConfLabel.setForeground(Color.WHITE);
	teamBConfLabel.setBounds(464, 29, 81, 14);
	compTeamResults.add(teamBConfLabel);
	
	JLabel teamAConfPlaceLabel = new JLabel("");
	teamAConfPlaceLabel.setForeground(Color.WHITE);
	teamAConfPlaceLabel.setBounds(64, 29, 46, 14);
	compTeamResults.add(teamAConfPlaceLabel);
	
	JLabel teamADivPlaceLabel = new JLabel("");
	teamADivPlaceLabel.setForeground(Color.WHITE);
	teamADivPlaceLabel.setBounds(64, 72, 46, 14);
	compTeamResults.add(teamADivPlaceLabel);
	
	JLabel teamADivLabel = new JLabel("");
	teamADivLabel.setForeground(Color.WHITE);
	teamADivLabel.setBounds(174, 72, 81, 14);
	compTeamResults.add(teamADivLabel);
	
	JLabel teamARecordLabel = new JLabel("");
	teamARecordLabel.setForeground(Color.WHITE);
	teamARecordLabel.setBounds(120, 115, 81, 14);
	compTeamResults.add(teamARecordLabel);
	
	JLabel teamBDivPlaceLabel = new JLabel("");
	teamBDivPlaceLabel.setForeground(Color.WHITE);
	teamBDivPlaceLabel.setBounds(609, 72, 46, 14);
	compTeamResults.add(teamBDivPlaceLabel);
	
	JPanel teamAPanel = new JPanel();
	teamAPanel.setLayout(null);
	teamAPanel.setBackground(Color.DARK_GRAY);
	teamAPanel.setBounds(10, 11, 351, 186);
	compTeams.add(teamAPanel);
	
	JLabel TeamALabel = new JLabel("Team A:");
	TeamALabel.setForeground(Color.WHITE);
	TeamALabel.setBounds(55, 8, 61, 14);
	teamAPanel.add(TeamALabel);
	
	JLabel teamALogo = new JLabel("");
	teamALogo.setHorizontalAlignment(SwingConstants.CENTER);
	teamALogo.setBounds(125, 43, 100, 100);
	teamAPanel.add(teamALogo);
	
	JLabel teamACityLabel = new JLabel("");
	teamACityLabel.setHorizontalAlignment(SwingConstants.CENTER);
	teamACityLabel.setForeground(Color.WHITE);
	teamACityLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	teamACityLabel.setBounds(33, 154, 125, 14);
	teamAPanel.add(teamACityLabel);
	
	JLabel teamANameLabel = new JLabel("");
	teamANameLabel.setHorizontalAlignment(SwingConstants.CENTER);
	teamANameLabel.setForeground(Color.WHITE);
	teamANameLabel.setBounds(171, 154, 125, 14);
	teamAPanel.add(teamANameLabel);
	compPlayers.setLayout(null);
	
	JComboBox<Team> teamAComboBox = new JComboBox<Team>();
	teamAComboBox.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent arg0) {
			Team tb = (Team) teamAComboBox.getSelectedItem();
			Image image = tb.logo.getImage(); // transform it 
			Image transformImg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			teamALogo.setIcon(new ImageIcon(transformImg));
			teamACityLabel.setText(tb.getCity());
			teamANameLabel.setText(tb.getNickname());
			teamADivLabel.setText(tb.getDivision());
			teamADivPlaceLabel.setText(tb.getDivSeedOrd());
			teamAConfLabel.setText(tb.getConference());
			teamAConfPlaceLabel.setText(tb.getConfSeedOrd());
			teamARecordLabel.setText(tb.getWins()+"W - "+tb.getLosses()+"L");
		}
	});
	teamAComboBox.setModel(teamAModel);
	teamAComboBox.setBounds(171, 5, 125, 20);
	teamAPanel.add(teamAComboBox);
	
	JComboBox<Team> teamBcomboBox = new JComboBox<Team>();
	teamBcomboBox.addItemListener(new ItemListener(){
		public void itemStateChanged(ItemEvent arg0) {
			Team tb = (Team) teamBcomboBox.getSelectedItem();
			Image image = tb.logo.getImage(); // transform it 
			Image transformImg = image.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			teamBLogo.setIcon(new ImageIcon(transformImg));
			teamBCityLabel.setText(tb.getCity());
			teamBNameLabel.setText(tb.getNickname());
			teamBDivLabel.setText(tb.getDivision());
			teamBDivPlaceLabel.setText(tb.getDivSeedOrd());
			teamBConfLabel.setText(tb.getConference());
			teamBConfPlaceLabel.setText(tb.getConfSeedOrd());
			teamBRecordLabel.setText(tb.getWins()+"W - "+tb.getLosses()+"L");
		}
	});
	teamBcomboBox.setModel(teamBModel);
	teamBcomboBox.setBounds(171, 5, 125, 20);
	teamBPanel.add(teamBcomboBox);	
	
	JPanel playerBPanel = new JPanel();
	playerBPanel.setBackground(Color.DARK_GRAY);
	playerBPanel.setBounds(382, 11, 351, 186);
	compPlayers.add(playerBPanel);
	playerBPanel.setLayout(null);
	
	
	JLabel playerBLabel = new JLabel("Player B:");
	playerBLabel.setBounds(55, 8, 61, 14);
	playerBLabel.setForeground(Color.WHITE);
	playerBPanel.add(playerBLabel);
	
	JLabel playerBName = new JLabel("Player B Name");
	playerBName.setHorizontalAlignment(SwingConstants.CENTER);
	playerBName.setForeground(Color.WHITE);
	playerBName.setBounds(113, 132, 125, 14);
	playerBPanel.add(playerBName);
	
	JPanel playerAPanel = new JPanel();
	playerAPanel.setLayout(null);
	playerAPanel.setBackground(Color.DARK_GRAY);
	playerAPanel.setBounds(10, 11, 351, 186);
	compPlayers.add(playerAPanel);
	
	
	JLabel playerALabel = new JLabel("Player A:");
	playerALabel.setForeground(Color.WHITE);
	playerALabel.setBounds(55, 8, 61, 14);
	playerAPanel.add(playerALabel);
	
	JLabel playerAName = new JLabel("Player A Name");
	playerAName.setHorizontalAlignment(SwingConstants.CENTER);
	playerAName.setForeground(Color.WHITE);
	playerAName.setBounds(113, 132, 125, 14);
	playerAPanel.add(playerAName);
	
	JPanel compPlayerPanel = new JPanel();
	compPlayerPanel.setForeground(Color.WHITE);
	compPlayerPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
	compPlayerPanel.setBackground(Color.DARK_GRAY);
	compPlayerPanel.setBounds(10, 208, 723, 158);
	compPlayers.add(compPlayerPanel);
	compPlayerPanel.setLayout(null);
	
	

	
	String[][] compPlayerRows = {
			{"","Age",""},
			{"","Height (ft)",""},
			{"","Weight (lbs)",""},
			{"","Position",""},
			{"","Years Pro",""},
			{"","Games played",""},
			{"","Minutes Played",""},
			{"","Field Goals Made",""},
			{"","Field Goal Attempts",""},
			{"","Free Throws Made",""},
			{"","Free Throw Attempts",""},
			{"","3pt Made",""},
			{"","3pt Attempts",""},
			{"","Offensive Rebounds",""},
			{"","Def Rebounds",""},
			{"","Rebounds",""},
			{"","Assists",""},
			{"","Steals",""},
			{"","Blocks",""}			
	};
	String[] compPlayerCols = {"","Stat (per game)",""};
	
	compPlayerTable = new JTable(compPlayerRows,compPlayerCols);
	compPlayerTable.setRowSelectionAllowed(false);
	compPlayerTable.setBackground(Color.BLACK);
	compPlayerTable.setForeground(Color.WHITE);
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    compPlayerTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    compPlayerTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    compPlayerTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	compPlayerTable.setBounds(0, 0, 723, 158);

	JScrollPane scrollPane_1 = new JScrollPane(compPlayerTable);
	scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	scrollPane_1.setBounds(0, 0, 723, 158);
	compPlayerPanel.add(scrollPane_1);
	
	JComboBox<Player> playerAComboBox = new JComboBox<Player>();
	playerAComboBox.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			playerA = (Player) playerAComboBox.getSelectedItem();
			playerAName.setText(playerA.getFullName());
			compPlayerTable.getColumnModel().getColumn(0).setHeaderValue(playerA.getFullName());
			compPlayerTable.setValueAt(Integer.toString(playerA.getAge()),0,0);
			compPlayerTable.setValueAt(Integer.toString(playerA.getHeightFt())+" ft "+Integer.toString(playerA.getHeightIn())+" in",1,0);
			compPlayerTable.setValueAt(Double.toString(playerA.getWeight())+" lbs",2,0);
			compPlayerTable.setValueAt(playerA.getPosition(),3,0);
			if(!playerA.areStatsFilled()) {
				try {
					String hostURL = "https://api-nba-v1.p.rapidapi.com/";
			        String host = "api-nba-v1.p.rapidapi.com";
			        String rAPIKey =""; //INSERT API KEY FROM RAPIDAPI IN THESE QUOTES!
			        String returned;
			        Request request;
			        Response response;
			        OkHttpClient client = new OkHttpClient();   
			        request = new Request.Builder()
			        		.url(hostURL+"statistics/players/playerId/"+playerA.getId())
			        		.get()
			        		.addHeader("x-rapidapi-key", rAPIKey)
			            	.addHeader("x-rapidapi-host", host)
			            	.build();
			        response = client.newCall(request).execute();
			        returned = response.body().string();
			        int gamesPlayed = Integer.parseInt(returned.substring(returned.indexOf("\"results\":")+10, returned.indexOf(",\"filters\"")).trim());
			        String[] playerStatsRaw = returned.substring(returned.indexOf(":[{")).split("gameId\":");
			        playerA.populateStats(gamesPlayed,playerStatsRaw);
				}
				catch(IOException ioe) {
					
				}
			}
				DecimalFormat deciFormat = new DecimalFormat("######.###");
				compPlayerTable.setValueAt(Integer.toString(playerA.getYearsPro()),4,0);
				compPlayerTable.setValueAt(Integer.toString(playerA.getGamesPlayed()),5,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgMinutesPlayed()),6,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgFieldGoalsMade()),7,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgFieldGoalsAttempted()),8,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgFreeThrowsMade()),9,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgFreeThrowsAttempted()),10,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvg3ptMade()),11,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvg3ptAttempted()),12,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgOffensiveRebounds()),13,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgDefensiveRebounds()),14,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgTotalRebounds()),15,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgAssists()),16,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgSteals()),17,0);
				compPlayerTable.setValueAt(deciFormat.format(playerA.getAvgBlocks()),18,0);
			}
	});
	playerAComboBox.setModel(playerAModel);
	playerAComboBox.setBounds(171, 5, 125, 20);
	playerAPanel.add(playerAComboBox);

	JComboBox<Player> playerBComboBox = new JComboBox<Player>();
	playerBComboBox.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			playerB = (Player) playerBComboBox.getSelectedItem();
			playerBName.setText(playerB.getFullName());
			compPlayerTable.getColumnModel().getColumn(2).setHeaderValue(playerB.getFullName());
			compPlayerTable.setValueAt(Integer.toString(playerB.getAge()),0,2);
			compPlayerTable.setValueAt(Integer.toString(playerB.getHeightFt())+" ft "+Integer.toString(playerB.getHeightIn())+" in",1,2);
			compPlayerTable.setValueAt(Double.toString(playerB.getWeight())+" lbs",2,2);
			compPlayerTable.setValueAt(playerB.getPosition(),3,2);
			if(!playerB.areStatsFilled()) {
				try {
					String hostURL = "https://api-nba-v1.p.rapidapi.com/";
			        String host = "api-nba-v1.p.rapidapi.com";
			        String rAPIKey =""; //INSERT API KEY FROM RAPIDAPI IN THESE QUOTES!
			        String returned;
			        Request request;
			        Response response;
			        OkHttpClient client = new OkHttpClient();   
			        request = new Request.Builder()
			        		.url(hostURL+"statistics/players/playerId/"+playerB.getId())
			        		.get()
			        		.addHeader("x-rapidapi-key", rAPIKey)
			            	.addHeader("x-rapidapi-host", host)
			            	.build();
			        response = client.newCall(request).execute();
			        returned = response.body().string();
			        int gamesPlayed = Integer.parseInt(returned.substring(returned.indexOf("\"results\":")+10, returned.indexOf(",\"filters\"")).trim());
			        String[] playerStatsRaw = returned.substring(returned.indexOf(":[{")).split("gameId\":");
			        playerB.populateStats(gamesPlayed,playerStatsRaw);
				}
				catch(IOException ioe) {
					
				}
			}
				DecimalFormat deciFormat = new DecimalFormat("######.###");
				compPlayerTable.setValueAt(Integer.toString(playerB.getYearsPro()),4,2);
				compPlayerTable.setValueAt(Integer.toString(playerB.getGamesPlayed()),5,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgMinutesPlayed()),6,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgFieldGoalsMade()),7,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgFieldGoalsAttempted()),8,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgFreeThrowsMade()),9,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgFreeThrowsAttempted()),10,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvg3ptMade()),11,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvg3ptAttempted()),12,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgOffensiveRebounds()),13,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgDefensiveRebounds()),14,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgTotalRebounds()),15,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgAssists()),16,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgSteals()),17,2);
				compPlayerTable.setValueAt(deciFormat.format(playerB.getAvgBlocks()),18,2);
			}
	});
	playerBComboBox.setModel(playerBModel);
	playerBComboBox.setBounds(171, 5, 125, 20);
	playerBPanel.add(playerBComboBox);
}



	public void setPlayerComboBoxA(List<Player> players) {
		singlePlayerModel.addElement(null);
		playerAModel.addElement(null);
		playerBModel.addElement(null);		
		////System.out.println("Passed a list of "+players.size()+ " players");
		for(Player p : players) {
			singlePlayerModel.addElement(p);
			playerAModel.addElement(p);
			playerBModel.addElement(p);
		}
	}
		
	public void setTeamComboBox(List<Team> teams) {
		////System.out.println("Passed a list of "+teams.size()+ " teams");
		singleTeamModel.addElement(null);
		teamAModel.addElement(null);
		teamBModel.addElement(null);
		for(Team t : teams) {
			singleTeamModel.addElement(t);
			teamAModel.addElement(t);
			teamBModel.addElement(t);
		}
	}
	
	public void setGameComboBox(List<Game> games) {
		////System.out.println("Passed a list of "+games.size()+ " games");
		singleGameModel.addElement(null);
		for(Game g : games) {
			singleGameModel.addElement(g);
		}
	}
	
	public void populateBoxScoreTable(JTable boxScore, Game g) {
		Team awayTemp = g.getAwayTeam();
		Team homeTemp = g.getHomeTeam();
		Object[] columnNames = {"Stat",awayTemp.getAbbrev(),homeTemp.getAbbrev()};
		gameBoxScoreModel.setColumnIdentifiers(columnNames);
		gameBoxScoreModel.setValueAt(g.getAwayScore(),0,1);
		gameBoxScoreModel.setValueAt(g.getHomeScore(),0,2);
		gameBoxScoreModel.setValueAt(g.getAwayFBPts(),1,1);
		gameBoxScoreModel.setValueAt(g.getHomeFBPts(),1,2);
		gameBoxScoreModel.setValueAt(g.getAwayPtsinPaint(),2,1);
		gameBoxScoreModel.setValueAt(g.getHomePtsinPaint(),2,2);
		gameBoxScoreModel.setValueAt(g.getAway2ndChancePts(),3,1);
		gameBoxScoreModel.setValueAt(g.getHome2ndChancePts(),3,2);
		gameBoxScoreModel.setValueAt(g.getAwayPtsFrmTurnovers(),4,1);
		gameBoxScoreModel.setValueAt(g.getHomePtsFrmTurnovers(),4,2);
		gameBoxScoreModel.setValueAt(g.getAwayBiggestLead(),5,1);
		gameBoxScoreModel.setValueAt(g.getHomeBiggestLead(),5,2);
		gameBoxScoreModel.setValueAt(g.getAwayLongestRun(),6,1);
		gameBoxScoreModel.setValueAt(g.getHomeLongestRun(),6,2);
		gameBoxScoreModel.setValueAt(g.getAwayFGM(),7,1);
		gameBoxScoreModel.setValueAt(g.getHomeFGM(),7,2);
		gameBoxScoreModel.setValueAt(g.getAwayFGA(),8,1);
		gameBoxScoreModel.setValueAt(g.getHomeFGA(),8,2);
		gameBoxScoreModel.setValueAt(g.getAwayFGP()+"%",9,1);
		gameBoxScoreModel.setValueAt(g.getHomeFGP()+"%",9,2);
		gameBoxScoreModel.setValueAt(g.getAwayFTM(),10,1);
		gameBoxScoreModel.setValueAt(g.getHomeFTM(),10,2);
		gameBoxScoreModel.setValueAt(g.getAwayFTA(),11,1);
		gameBoxScoreModel.setValueAt(g.getHomeFTA(),11,2);
		gameBoxScoreModel.setValueAt(g.getAwayFTP()+"%",12,1);
		gameBoxScoreModel.setValueAt(g.getHomeFTP()+"%",12,2);
		gameBoxScoreModel.setValueAt(g.getAway3PtM(),13,1);
		gameBoxScoreModel.setValueAt(g.getHome3PtM(),13,2);
		gameBoxScoreModel.setValueAt(g.getAway3PtA(),14,1);
		gameBoxScoreModel.setValueAt(g.getHome3PtA(),14,2);
		gameBoxScoreModel.setValueAt(g.getAway3PtP()+"%",15,1);
		gameBoxScoreModel.setValueAt(g.getHome3PtP()+"%",15,2);
		gameBoxScoreModel.setValueAt(g.getAwayReb()+" rebounds",16,1);
		gameBoxScoreModel.setValueAt(g.getHomeReb()+" rebounds",16,2);
		gameBoxScoreModel.setValueAt(g.getAwayOffReb()+" rebounds",17,1);
		gameBoxScoreModel.setValueAt(g.getHomeOffReb()+" rebounds",17,2);
		gameBoxScoreModel.setValueAt(g.getAwayDefReb()+" rebounds",18,1);
		gameBoxScoreModel.setValueAt(g.getHomeDefReb()+" rebounds",18,2);
		gameBoxScoreModel.setValueAt(g.getAwayAssists()+" assists",19,1);
		gameBoxScoreModel.setValueAt(g.getHomeAssists()+" assists",19,2);
		gameBoxScoreModel.setValueAt(g.getAwaySteals()+" steals",20,1);
		gameBoxScoreModel.setValueAt(g.getHomeSteals()+" steals",20,2);
		gameBoxScoreModel.setValueAt(g.getAwayBlks()+" blocks",21,1);
		gameBoxScoreModel.setValueAt(g.getHomeBlks()+" blocks",21,2);
		gameBoxScoreModel.setValueAt(g.getAwayFouls()+" fouls",22,1);
		gameBoxScoreModel.setValueAt(g.getHomeFouls()+" fouls",22,2);
		gameBoxScoreModel.setValueAt(g.getAwayTO() + " turnovers",23,1);
		gameBoxScoreModel.setValueAt(g.getHomeTO()+" turnovers",23,2);
	}
}
