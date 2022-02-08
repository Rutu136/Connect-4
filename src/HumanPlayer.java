//-----------------------------------------
// NAME			  : Rutukumar Barvaliya 
// STUDENT NUMBER : 7870807
// COURSE		  : COMP 2150
// INSTRUCTOR	  : Mike Domaratzki
// ASSIGNMENT	  : assignment 2
// QUESTION		  : HumanPlayer.java    
//
// REMARKS        : it will just interact with the UI interface.
//-----------------------------------------

//implemented class of player and human
public class HumanPlayer implements Player,Human
{
	//private objects
	private TextUI TU;
	private SwingGUI SG;
	private GameLogic GL;
	//constructor
	public HumanPlayer()
	{
		TU = new TextUI();
		SG = new SwingGUI();
	}
	//set the answer into the GameLogic class
	@Override
	public void setAnswer(int col) {
		// TODO Auto-generated method stub
		GL.setAnswer(col);
	}
	//inform the last move of the AI
	@Override
	public void lastMove(int lastCol) {
		SG.lastMove(lastCol);
		//TU.lastMove(lastCol);
	}
	
	//gives the information to the AI
	@Override
	public void gameOver(Status winner) {
		// TODO Auto-generated method stub
		SG.gameOver(winner);
		//TU.gameOver(winner);
		
	}
	
	//set the info of the human
	@Override
	public void setInfo(int size, GameLogic gl) {
		// TODO Auto-generated method stub
		GL = gl;
		
		ConnectFour g = (ConnectFour) GL;  
		SG.setInfo((Human)g.getHuman(), size);
		//TU.setInfo((Human)g.getHuman(), size);
	}
}
