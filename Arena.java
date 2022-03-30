import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;


public class Arena {

	public enum Row {Front, Back};	//enum for specifying the front or back row
	public enum Team {A, B};		//enum for specifying team A or B
	
	private Player[][] teamA = null;	//two dimensional array representing the players of Team A
	private Player[][] teamB = null;	//two dimensional array representing the players of Team B
	private int numRowPlayers = 0;		//number of players in each row
	
	public static final int MAXROUNDS = 100;	//Max number of turn
	public static final int MAXEACHTYPE = 3;	//Max number of players of each type, in each team.
	private final Path logFile = Paths.get("battle_log.txt");
	
	private int numRounds = 0;	//keep track of the number of rounds so far
	//Team A
	private int HealerTeamA = 0;
	private int TankTeamA = 0;
	private int SamuraiTeamA = 0;
	private int BlackMageTeamA = 0;
	private int PhoenixTeamA = 0;
	private int CherryTeamA = 0;
	//Team B
	private int HealerTeamB = 0;
	private int TankTeamB = 0;
	private int SamuraiTeamB = 0;
	private int BlackMageTeamB = 0;
	private int PhoenixTeamB = 0;
	private int CherryTeamB = 0;
	/**
	 * Constructor. 
	 * @param _numRowPlayers is the number of players in each row.
	 */
	public Arena(int _numRowPlayers)
	{	
		//INSERT YOUR CODE HERE
		this.numRowPlayers = _numRowPlayers;
		this.teamA = new Player[2][_numRowPlayers];//Set team A
		this.teamB = new Player[2][_numRowPlayers];//Set team B
		////Keep this block of code. You need it for initialize the log file. 
		////(You will learn how to deal with files later)
		try {
			Files.deleteIfExists(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/////////////////////////////////////////
		
	}
	
	/**
	 * Returns true if "player" is a member of "team", false otherwise.
	 * Assumption: team can be either Team.A or Team.B
	 * @param player
	 * @param team
	 * @return
	 */
	public boolean isMemberOf(Player player, Team team)
	{
		//INSERT YOUR CODE HERE
		Player[][] players = team == Team.A ? teamA:teamB;//Set player can be team A or B
		for(Player[] row : players)//Check player in team A or B
		{
			for(Player a:row)
			{
				if(player==a)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	/**
	 * This methods receives a player configuration (i.e., team, type, row, and position), 
	 * creates a new player instance, and places him at the specified position.
	 * @param team is either Team.A or Team.B
	 * @param pType is one of the Player.Type  {Healer, Tank, Samurai, BlackMage, Phoenix}
	 * @param row	either Row.Front or Row.Back
	 * @param position is the position of the player in the row. Note that position starts from 1, 2, 3....
	 */
	public void addPlayer(Team team, Player.PlayerType pType, Row row, int position)
	{	
		//INSERT YOUR CODE HERE
		Player AddTeamA = null;//Add in team A
		Player AddTeamB = null;//Add in team B
		if(team == Arena.Team.A)
		{
			teamA[row.ordinal()][position-1] = new Player(pType);
			AddTeamA = teamA[row.ordinal()][position-1];
			AddTeamA.setPos(team, row, position);
		}
		else
		{
			teamB[row.ordinal()][position-1] = new Player(pType);
			AddTeamB = teamB[row.ordinal()][position-1];
			AddTeamB.setPos(team, row, position);
		}
	}

	public Player[][] getTeam(Team Mine){
		if(Mine.equals(Team.A) == true)//Checkt team A or B
		{
			return teamA;
		}
		else
		{
			return teamB;
		}
	}
	
	
	/**
	 * Validate the players in both Team A and B. Returns true if all of the following conditions hold:
	 * 
	 * 1. All the positions are filled. That is, there each team must have exactly numRow*numRowPlayers players.
	 * 2. There can be at most MAXEACHTYPE players of each type in each team. For example, if MAXEACHTYPE = 3
	 * then each team can have at most 3 Healers, 3 Tanks, 3 Samurais, 3 BlackMages, and 3 Phoenixes.
	 * 
	 * Returns true if all the conditions above are satisfied, false otherwise.
	 * @return
	 */
	public boolean validatePlayers()
	{
		//INSERT YOUR CODE HERE
		//TeamA
		for(int i=0; i<2; i++)//Check that in each team have more than MAXEACHTYPE or not
		{
			for(int j=0; j<numRowPlayers; j++)
			{
				if(teamA[i][j] != null)
				{
					switch(teamA[i][j].getType())
					{
						case Healer:
							HealerTeamA++;
							break;
						case Tank:
							TankTeamA++;
							break;
						case Samurai:
							SamuraiTeamA++;
							break;
						case BlackMage:
							BlackMageTeamA++;
							break;
						case Phoenix:
							PhoenixTeamA++;
							break;
						case Cherry:
							CherryTeamA++;
							break;
					}
				}
				else
				{
					return false;
				}
			}
		}
		//TeamB
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<numRowPlayers; j++)
			{
				if(teamB[i][j] != null)
				{
					switch(teamB[i][j].getType())
					{
						case Healer:
							HealerTeamB++;
							break;
						case Tank:
							TankTeamB++;
							break;
						case Samurai:
							SamuraiTeamB++;
							break;
						case BlackMage:
							BlackMageTeamB++;
							break;
						case Phoenix:
							PhoenixTeamB++;
							break;
						case Cherry:
							CherryTeamB++;
							break;
					}
				}
				else
				{
					return false;
				}
			}
		}
		if(HealerTeamA>MAXEACHTYPE || TankTeamA>MAXEACHTYPE || SamuraiTeamA>MAXEACHTYPE || BlackMageTeamA>MAXEACHTYPE || PhoenixTeamA>MAXEACHTYPE || CherryTeamA>MAXEACHTYPE || HealerTeamB>MAXEACHTYPE || TankTeamB>MAXEACHTYPE || SamuraiTeamB>MAXEACHTYPE || BlackMageTeamB>MAXEACHTYPE || PhoenixTeamB>MAXEACHTYPE || CherryTeamB>MAXEACHTYPE)//Check every type of player in both team
		{
			return false;
		}
		return true;
	}
	
	
	/**
	 * Returns the sum of HP of all the players in the given "team"
	 * @param team
	 * @return
	 */
	public static double getSumHP(Player[][] team)
	{
		//INSERT YOUR CODE HERE
		double AllHP = 0.0;
		for(Player[] row:team)
		{
			for(Player playerHP:row)
			{
				AllHP+=playerHP.getCurrentHP();
			}
		}
		return AllHP;
	}
	
	/**
	 * Return the team (either teamA or teamB) whose number of alive players is higher than the other. 
	 * 
	 * If the two teams have an equal number of alive players, then the team whose sum of HP of all the
	 * players is higher is returned.
	 * 
	 * If the sums of HP of all the players of both teams are equal, return teamA.
	 * @return
	 */
	public Player[][] getWinningTeam()
	{
		//INSERT YOUR CODE HERE	
		int TotalPlayerA=0;
		int TotalPlayerB=0;
		for(Player[] crowA:teamA)//Count Player in team A
		{
			for(Player count:crowA)
			{
				if(count.isAlive())
				{
					TotalPlayerA+=1;
				}
			}
		}
		for(Player[] crowB:teamB)//Count Player in team B
		{
			for(Player count:crowB)
			{
				if(count.isAlive())
				{
					TotalPlayerB+=1;
				}
			}
		}
		if(TotalPlayerA==TotalPlayerB)//Check player in team A & B equal or not
		{
			if(getSumHP(teamA)>getSumHP(teamB))//if HP teamA > teamB. teamA will win.
			{
				System.out.println("@@@ Team A won");
				return teamA;
			}
			else//if HP teamA < teamB. teamA will win.
			{
				System.out.println("@@@ Team B won");
				return teamB;
			}
		}

		if(TotalPlayerA>TotalPlayerB)//if number of player teamA > teamB. teamA will win.
		{
			System.out.println("@@@ Team A won");
			return teamA;
		}
		else//if number of player teamA < teamB. teamA will win.
		{
			System.out.println("@@@ Team B won");
			return teamB;
		}
	}
	
	/**
	 * This method simulates the battle between teamA and teamB. The method should have a loop that signifies
	 * a round of the battle. In each round, each player in teamA invokes the method takeAction(). The players'
	 * turns are ordered by its position in the team. Once all the players in teamA have invoked takeAction(),
	 * not it is teamB's turn to do the same. 
	 * 
	 * The battle terminates if one of the following two conditions is met:
	 * 
	 * 1. All the players in a team has been eliminated.
	 * 2. The number of rounds exceeds MAXROUNDS
	 * 
	 * After the battle terminates, report the winning team, which is determined by getWinningTeam().
	 */
	public void startBattle()
	{
		//INSERT YOUR CODE HERE
		while(AliveTeam(Arena.Team.A) && AliveTeam(Arena.Team.B) && this.numRounds<=MAXROUNDS)
		{
			this.numRounds++;
			System.out.println("@ Round " + this.numRounds);
			//teamA
			for(int i=0; i<2; i++)
			{
				for(int j=0; j<numRowPlayers; j++)
				{
					teamA[i][j].takeAction(this);
				}
			}
			//teamB
			for(int i=0; i<2; i++)
			{
				for(int j=0; j<numRowPlayers; j++)
				{
					teamB[i][j].takeAction(this);
				}
			}
			displayArea(this, true);
			logAfterEachRound();
		}
		getWinningTeam();
	}
	
	public boolean AliveTeam(Team alive){//Check which team is alive
		switch(alive)
		{
			//teamA
			case A:
				for(int i=0; i<2; i++)
				{
					for(int j=0; j<numRowPlayers; j++)
					{
						if(teamA[i][j].isAlive() == true)
						{
							return true;
						}
					}
				}
				break;
			//teamB
			case B:
				for(int i=0; i<2; i++)
				{
					for(int j=0; j<numRowPlayers; j++)
					{
						if(teamB[i][j].isAlive() == true)
						{
							return true;
						}
					}
				}
				break;
		}
		return false;
	}
	
	
	/**
	 * This method displays the current area state, and is already implemented for you.
	 * In startBattle(), you should call this method once before the battle starts, and 
	 * after each round ends. 
	 * 
	 * @param arena
	 * @param verbose
	 */
	public static void displayArea(Arena arena, boolean verbose)
	{
		StringBuilder str = new StringBuilder();
		if(verbose)
		{
			str.append(String.format("%43s   %40s","Team A","")+"\t\t"+String.format("%-38s%-40s","","Team B")+"\n");
			str.append(String.format("%43s","BACK ROW")+String.format("%43s","FRONT ROW")+"  |  "+String.format("%-43s","FRONT ROW")+"\t"+String.format("%-43s","BACK ROW")+"\n");
			for(int i = 0; i < arena.numRowPlayers; i++)
			{
				str.append(String.format("%43s",arena.teamA[1][i])+String.format("%43s",arena.teamA[0][i])+"  |  "+String.format("%-43s",arena.teamB[0][i])+String.format("%-43s",arena.teamB[1][i])+"\n");
			}
		}
	
		str.append("@ Total HP of Team A = "+getSumHP(arena.teamA)+". @ Total HP of Team B = "+getSumHP(arena.teamB)+"\n\n");
		System.out.print(str.toString());
		
		
	}
	
	/**
	 * This method writes a log (as round number, sum of HP of teamA, and sum of HP of teamB) into the log file.
	 * You are not to modify this method, however, this method must be call by startBattle() after each round.
	 * 
	 * The output file will be tested against the auto-grader, so make sure the output look something like:
	 * 
	 * 1	47415.0	49923.0
	 * 2	44977.0	46990.0
	 * 3	42092.0	43525.0
	 * 4	44408.0	43210.0
	 * 
	 * Where the numbers of the first, second, and third columns specify round numbers, sum of HP of teamA, and sum of HP of teamB respectively. 
	 */
	private void logAfterEachRound()
	{
		try {
			Files.write(logFile, Arrays.asList(new String[]{numRounds+"\t"+getSumHP(teamA)+"\t"+getSumHP(teamB)}), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
