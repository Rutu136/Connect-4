//make class by using the Software Factory design pattern.
public class PlayerFactory 
{
	public static Player getPlayerObj(String typeOfPlayer,GameLogic Gl,int sizeOfBoard)
	{
		Player getPlayer = null;
		if(typeOfPlayer.equalsIgnoreCase("AIplayer"))
		{
			getPlayer = new AIPlayer();
			getPlayer.setInfo(sizeOfBoard, Gl);
		}
		else if(typeOfPlayer.equalsIgnoreCase("HumanPlayer"))
		{
			getPlayer = new HumanPlayer();
			getPlayer.setInfo(sizeOfBoard,Gl);
		}
		return getPlayer;
	}
}
