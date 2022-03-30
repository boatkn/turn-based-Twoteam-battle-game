

public class Player {

	public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
	
	private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
	private double maxHP;		//Max HP of this player
	private double currentHP;	//Current HP of this player 
	private double atk;			//Attack power of this player

	private int numSpecialTurns;	//Turn that each player can use skill
	private int numCurrentTurns=0;	//Count tern
	private boolean IsSleeping;		//Create sleep for use to check sleep or not
	private boolean IsCursed;		//Create cause for use to check curse or not
	private boolean IsAlive;		//Create alive for use to check alive or not
	private boolean IsTaunting;		//Create taunt for use to check taunt or not
	private Player CursedTarget;	//Use to find cures target
	private Arena.Team team;
	private Arena.Row row;
	private int pos;
	
	/**
	 * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns, 
	 * as specified in the given table. It also reset the internal turn count of this player. 
	 * @param _type
	 */
	public Player(PlayerType _type)
	{	
		//INSERT YOUR CODE HERE
		this.type=_type;	//Set player type
		maxHP=0;			//Set maxHP
		atk=0;				//Set atk
		currentHP=0;		//Set currentHP
		IsSleeping=false;	//Set sleep
		IsCursed=false;		//Set Curse
		IsAlive=true;		//Set alive
		IsTaunting=false;	//Set taunt
		
		switch(this.type)	//Check player type
		{
			case Healer:	//If player is healer set maxHP,currentHP,atk,numSpecialTurns
				this.type=PlayerType.Healer;
				maxHP=4790;
				currentHP=4790;
				atk=238;
				numSpecialTurns=4;
				break;
			
			
			case Tank:		//If player is tank set maxHP,currentHP,atk,numSpecialTurns
				this.type=PlayerType.Tank;
				maxHP=5340;
				currentHP=5340;
				atk=255;
				numSpecialTurns=4;
				break;
			
			case Samurai:	//If player is Samurai set maxHP,currentHP,atk,numSpecialTurns
				this.type=PlayerType.Samurai;
				maxHP=4005;
				currentHP=4005;
				atk=368;
				numSpecialTurns=3;
				break;

			case BlackMage:	//If player is blackmage set maxHP,currentHP,atk,numSpecialTurns
				this.type=PlayerType.BlackMage;
				maxHP=4175;
				currentHP=4175;
				atk=303;
				numSpecialTurns=4;
				break;

			case Phoenix:	//If player is phoenix set maxHP,currentHP,atk,numSpecialTurns
				this.type=PlayerType.Phoenix;
				maxHP=4175;
				currentHP=4175;
				atk=209;
				numSpecialTurns=8;
				break;
				
			case Cherry:	//If player is cherry set maxHP,currentHP,atk,numSpecialTurns
				this.type=PlayerType.Cherry;
				maxHP=3560;
				currentHP=3560;
				atk=198;
				numSpecialTurns=4;
				break;
		}
	}
	
	/**
	 * Returns the current HP of this player
	 * @return
	 */
	public double getCurrentHP()
	{
		//INSERT YOUR CODE HERE
		return currentHP;	//return currenthp
	}
	
	/**
	 * Returns type of this player
	 * @return
	 */
	public Player.PlayerType getType()
	{
		//INSERT YOUR CODE HERE
		return this.type;	//return player type
	}
	
	/**
	 * Returns max HP of this player. 
	 * @return
	 */
	public double getMaxHP()
	{
		//INSERT YOUR CODE HERE
		return maxHP;		//return player maxhp
	}
	
	/**
	 * Returns whether this player is sleeping.
	 * @return
	 */
	public boolean isSleeping()
	{
		//INSERT YOUR CODE HERE
		if(IsSleeping)		//Check that player sleep or not
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether this player is being cursed.
	 * @return
	 */
	public boolean isCursed()
	{
		//INSERT YOUR CODE HERE
		if(IsCursed)		//Check that player curse or not
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * @return
	 */
	public boolean isAlive()
	{
		//INSERT YOUR CODE HERE
		if(IsAlive)			//Check that player alive or not
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether this player is taunting the other team.
	 * @return
	 */
	public boolean isTaunting()
	{
		//INSERT YOUR CODE HERE
		if(IsTaunting)		//Check that player tant or not
		{
			return true;
		}
		return false;
	}
	
	
	public void attack(Player target)
	{	
		//INSERT YOUR CODE HERE
		target.currentHP-=this.atk;		//if target get atk HP will decrease
		if(target.currentHP <= 0)		//if target hp <= 0 it will be negative value so I set it to 0
		{
			target.currentHP=0;
			target.IsAlive=false;
			target.IsCursed=false;
			target.IsSleeping=false;
			target.IsTaunting=false;
		}
	}
	
	public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam)
	{	
		//INSERT YOUR CODE HERE
		switch(this.type)
		{
			case Healer: //heal player that have low HP
				Player targetheal = lowHP(myTeam);
				if(targetheal == null)
				{
					;
				}
				double Healing = targetheal.getMaxHP()*0.25;
				if(targetheal.currentHP > targetheal.maxHP)
				{
					targetheal.currentHP = targetheal.maxHP;
				}
				targetheal.currentHP += Healing;
				targetheal.currentHP = Math.min(targetheal.maxHP, targetheal.currentHP);
				System.out.println("# " + this.PlayerInfo() + " Heals " + targetheal.PlayerInfo());
				break;
			
			case Tank://call taunt
				this.IsTaunting = true;
				System.out.println("# " + this.PlayerInfo() + " is Taunting ");
				break;
				
			case Samurai://hit 2 time;
				Player targethit = FindTarget(theirTeam);
				if(targethit != null)
				{
					attack(targethit);
					attack(targethit);
					System.out.println("# " + this.PlayerInfo() + "Double-Slashes " + targethit.PlayerInfo());
				}
				break;

			case BlackMage://make enemy have curse
				Player targetcurse = FindTarget(theirTeam);
				if(targetcurse != null)
				{
					this.CursedTarget = targetcurse;
					targetcurse.IsCursed=true;
					System.out.println("# " + this.PlayerInfo() + " Curses " + targetcurse.PlayerInfo());
				}
				break;

			case Phoenix://revive player
				Player targetrevive = HeroNeverDie(myTeam);
				if(targetrevive != null)
				{
					targetrevive.IsAlive=true;
					targetrevive.currentHP=0.30*targetrevive.maxHP;
					targetrevive.numCurrentTurns=0;
					System.out.println("# "+ this.PlayerInfo() + " Revives " + targetrevive.PlayerInfo());
				}
				break;

			case Cherry://make enemy sleep
				for(Player[] play:theirTeam)
				{
					for(Player enemy: play)
					{
						if(enemy.IsAlive)
						{
							enemy.IsSleeping = true;
							System.out.println("# " + this.PlayerInfo() + " Feeds a Fortune Cookkie to " + enemy.PlayerInfo());
						}
					}
				}
				break;
		}
		
	}
	public static Player FindTarget(Player[][] enemy){//to find target
		Player target = null;
		if(TauntedEnemy(enemy)!=null)
		{
			target = TauntedEnemy(enemy);
		}
		else if(!CheckDeadF(enemy))
		{
			for(int i=0; i<enemy[0].length; i++)
			{
				if(enemy[0][i].isAlive())
				{
					target = enemy[0][i];
					break;
				}
			}
			for(int i=0; i<enemy[0].length; i++)
			{
				if(enemy[0][i].isAlive() && target.getCurrentHP() > enemy[0][i].getCurrentHP())
				{
					target = enemy[0][i];
				}
			}
		}
		else
		{
			for(int i=0; i<enemy[1].length; i++)
			{
				if(enemy[1][i].isAlive())
				{
					target = enemy[1][i];
				}
			}
			for(int i=0; i<enemy[1].length; i++)
			{
				if(enemy[1][i].isAlive() && target.getCurrentHP() > enemy[1][i].getCurrentHP())
				{
					target = enemy[1][i];
				}
			}
		}
		return target;
	}
	public static Player TauntedEnemy(Player[][] team) {//to find enemy taunt
		Player target = null;
		boolean TauntFound = false;
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<team[i].length; j++)
			{
				if(team[i][j].isTaunting() && team[i][j].isAlive() == true)
				{
					target = team[i][j];
					TauntFound = true;
					break;
				}
			}
			if(TauntFound == true)
			{
				break;
			}
		}
		return target;
	}
	public static boolean CheckDeadF(Player[][] team){//to check player that dead in the front line
		for(int i=0; i<team[0].length; i++)
		{
			if(team[0][i].isAlive()==true)
			{
				return false;
			}
		}
		return true;
	}
	public Player lowHP(Player[][] team){//find player that have low HP
		Player target = null;
		double HPmax = 100.0;
		for(int i=0; i<2; i++)
		{
			for(Player play : team[i])
			{
				if(!play.IsAlive)
				{
					continue;
				}
				double lowestHP = (play.getCurrentHP()/play.getMaxHP())*100;
				if(lowestHP<=HPmax)
				{
					target = play;
					HPmax = lowestHP;
				}
			}
		}
		return target;
	}
	public Player HeroNeverDie(Player[][] Team){//revive player that die
		Player target = null;
		boolean PlayerDeadInTeam = false;
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<Team[i].length; j++)
			{
				if(Team[i][j].isAlive()==false)
				{
					target = Team[i][j];
					PlayerDeadInTeam = true;
					break;
				}
			}
			if(PlayerDeadInTeam==true)
			{
				break;
			}
		}
		return target;
	}
	public void setPos(Arena.Team team, Arena.Row row, int position)//to set position in team
	{
		this.team = team;
		this.row = row;
		this.pos = position;
	}
	
	/**
	 * This method is called by Arena when it is this player's turn to take an action. 
	 * By default, the player simply just "attack(target)". However, once this player has 
	 * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
	 * where each player type performs his own special move. 
	 * @param arena
	 */
	public void takeAction(Arena arena)
	{	
		//INSERT YOUR CODE HERE
		Player target = null;
		if(this.type == PlayerType.Tank)//check tank or not
		{
			this.IsTaunting = false;
		}
		if(this.type == PlayerType.BlackMage && this.CursedTarget != null)//check blackmage or not
		{
			this.CursedTarget.IsCursed = false;
			this.CursedTarget = null;
		}
		if((!this.IsSleeping) && this.IsAlive)//if player not sleep and alive. It can hit 
		{
			this.numCurrentTurns++;//count turn
			
			switch(this.team)
			{
			case A:
				if(this.numCurrentTurns == this.numSpecialTurns)//Check special turn or not
				{
					switch(this.type)
					{
					case Healer:
						useSpecialAbility(arena.getTeam(Arena.Team.A) , arena.getTeam(Arena.Team.B));
						break;
					
					case Tank:
						useSpecialAbility(arena.getTeam(Arena.Team.A) , arena.getTeam(Arena.Team.B));
						break;
						
					case Samurai:
						useSpecialAbility(arena.getTeam(Arena.Team.A) , arena.getTeam(Arena.Team.B));
						break;
						
					case BlackMage:
						useSpecialAbility(arena.getTeam(Arena.Team.A) , arena.getTeam(Arena.Team.B));
						break;
						
					case Phoenix:
						useSpecialAbility(arena.getTeam(Arena.Team.A) , arena.getTeam(Arena.Team.B));
						break;
						
					case Cherry:
						useSpecialAbility(arena.getTeam(Arena.Team.A) , arena.getTeam(Arena.Team.B));
						break;
						
					default:
						break;
						
					}
					this.numCurrentTurns = 0;
				}
				else
				{
					target = FindTarget(arena.getTeam(Arena.Team.B));
					if(target != null)
					{
						attack(target);
						System.out.println("# "+this.PlayerInfo()+" Attacks "+target.PlayerInfo());
					}
				}
				break;
				
			case B:
				if(this.numCurrentTurns == this.numSpecialTurns)//Check special turn or not
				{
					switch(this.type)
					{
					case Healer:
						useSpecialAbility(arena.getTeam(Arena.Team.B) , arena.getTeam(Arena.Team.A));
						break;
						
					case Tank:
						useSpecialAbility(arena.getTeam(Arena.Team.B) , arena.getTeam(Arena.Team.A));
						break;
						
					case Samurai:
						useSpecialAbility(arena.getTeam(Arena.Team.B) , arena.getTeam(Arena.Team.A));
						break;
						
					case BlackMage:
						useSpecialAbility(arena.getTeam(Arena.Team.B) , arena.getTeam(Arena.Team.A));
						break;
						
					case Phoenix:
						useSpecialAbility(arena.getTeam(Arena.Team.B) , arena.getTeam(Arena.Team.A));
						break;
						
					case Cherry:
						useSpecialAbility(arena.getTeam(Arena.Team.B) , arena.getTeam(Arena.Team.A));
						break;
						
					default:
						break;
						
					}
					this.numCurrentTurns = 0;
				}
				else
				{
					target = FindTarget(arena.getTeam(Arena.Team.A));
					if(target != null)
					{
						attack(target);
						System.out.println("# "+this.PlayerInfo()+" Attacks "+target.PlayerInfo());
					}
				}
				break;
			}
		}
		if(this.IsSleeping == true)
		{
			this.IsSleeping = false;
		}
		else
		{
			;
		}
	}
	
	/**
	 * This method overrides the default Object's toString() and is already implemented for you. 
	 */
	@Override
	public String toString()
	{
		return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
				+((this.isCursed())?"C":"")
				+((this.isTaunting())?"T":"")
				+((this.isSleeping())?"S":"")
				+"]";
	}
	
	private String PlayerInfo(){
		return this.team + "{" + this.row + "} {" + this.pos + "} {" + this.type + "}";
	}
	
}
