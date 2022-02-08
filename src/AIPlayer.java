//-----------------------------------------
// NAME			  : Rutukumar Barvaliya 
// STUDENT NUMBER : 7870807
// COURSE		  : COMP 2150
// INSTRUCTOR	  : Mike Domaratzki
// ASSIGNMENT	  : assignment 2
// QUESTION		  : AIPlayer.java    
// 
// REMARKS        : This will implements all the methods of the Player classes
//					and other necessary methods to check for moving the AI.
//				  : It will take care of all the possible moves for the AI where human can be defeated.
//-----------------------------------------

//some useful imports
import java.util.Arrays;
import java.util.Random;

//implemented class of Player.
public class AIPlayer implements Player
{
	// internal board storage.
	private Status[][] board;
	//one single object sharing for all implemented class of player
    private GameLogic GL;
    //To make a random First move,
    private Random rd;
    //Constructor
    public AIPlayer () 
    {
        rd = new Random();
    }

    /**
     * verifyCol - private helper method to determine if an integer is a valid
     * column that still has spots left.
     * @param col - integer (potential column number)
     * @return - is the column valid?
     */
    private boolean verifyCol(int col) 
    {
        return (col >= 0 && col < board[0].length && board[0][col] == Status.NEITHER);
    }

    /**
     * lastMove - called to indicate the last move of the opponent.
     * Here is last move of opponent will be the human.
     * it will continue let the AI knows that there is still move
     * for getting the alternate turn it will be opposite than the human player.
     * ONE :- HUMAN PLAYER
     * TWO :- AI PLAYER
     * NEITHER :- EMPTY SPACE
     * @param lastCol - column where the human last played.
     */
    public void lastMove(int lastCol) 
    {
    	if (lastCol != -1) {
            int lastPosn = drop(lastCol);
            board[lastPosn][lastCol] = Status.ONE; // this is the HUMAN's move, so it's ONE.
        }
    	
    	//making a bound to check just within the board for AI.
    	int num = 0;
        if(lastCol>= 0 && lastCol<board.length) 
        {
        	//it will generate the next smart move for the AI
        	//see the method for detail.
        	 num = makeMoveForAI(drop(lastCol), lastCol);
        }else 
        {
        	//if there is the AI's first turn then AI will put the random
        	//in the board.
        	num = rd.nextInt(board.length);
        }
        
        //get the raw
        int posn = drop(num);
        board[posn][num] =  Status.TWO; // this is the AI's move, so it's TWO.
        GL.setAnswer(num); // tell the human class where the human person chose.
    }
    
    /**
     * gameOver - called when the game is over. See assignment
     * for more details
     * ONE :- HUMAN PLAYER
     * TWO :- AI PLAYER
     * NEITHER :- EMPTY SPACE
     * @param winner - who won the game or NEITHER if the game is a draw.
     */
    @Override
    public void gameOver(Status winner) 
    {
        System.out.println("Game over!");
        if (winner == Status.NEITHER) 
        {
            System.out.println("Game is a draw.");
        } 
        else if (winner == Status.ONE) 
        {
            System.out.println("You win.");
        } 
        else 
        {
            System.out.println("Computer wins.");
        }
    }

    /**
     * drop - a private helper method that finds the position of a marker
     * when it is dropped in a column.
     * @param col the column where the piece is dropped
     * @return the row where the piece lands
     */
    private int drop( int col) 
    {
        int posn = 0;
        while (posn < board.length && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }
    
    /**
     * setInfo - it will set the information and initiate the board.
     * @param size will get the board size and set into board.
     * @param gl just passing the GameLogic object.
     * @return void
     */
	@Override
	public void setInfo(int size, GameLogic gl) {
		// TODO Auto-generated method stub
		
		 board = new Status[size][size];
		
	        for (Status[] s : board) {
	            Arrays.fill(s, Status.NEITHER);
	        }
	       
	        GL=gl;
	}
	
	/**
     * makeMoveForAI - Basically, 
     * 				i) make the AI to move where human can be win
     * 				ii) make the AI to move where AI can be win
     * 				iii) put the random move if nothing is possible. 
     * Priority wise
     * 1) it will break the 4 possible connect for the human.
     * 2) it will make its own possible 4 connections.
     * 3) 1 and 2 will not satisfy then it will put random
     * @param rows will get the row of the last move of the AI.
     * @param cols will take the last move of the human.
     * @return blockMoved will return the possible move as per the priority.
     */
	public int makeMoveForAI(int rows,int cols)
	{
		int blockMoved=0;
		int i = rows;
		int j=cols;
		
		//this will be the first priority for AI to block the human's move
		//if nothing will be possible then it will go to the AI's possible move
		for(i=0;i<board.length;i++)
		{
			for(j=0;j<board.length;j++)
			{
				if(board[i][j]==Status.NEITHER)
				{
					board[i][j]=Status.ONE;
					if(makeMove(i, j)==true)
					{
						blockMoved=j;
						board[i][j]=Status.NEITHER;
						System.out.println("-------------TRYING TO DEFEND AI--------------");
						return blockMoved;
					}
					board[i][j]=Status.NEITHER;
				}
			}
		}
		
		//this will try to make its own moves if there is nothing to block the Human's move
		if(blockMoved==0)
		{
			for(i=0;i<board.length;i++)
			{
				for(j=0;j<board.length;j++)
				{
					if(board[i][j]==Status.NEITHER)
					{
						board[i][j]=Status.TWO;
						if(makeMove(i, j)==true)
						{
							blockMoved=j;
							board[i][j]=Status.NEITHER;
							System.out.println("-------------TRYING TO WIN AI--------------");
							return blockMoved;
						}
						board[i][j]=Status.NEITHER;
					}
				}
			}
		}
		
		//it will put the randomly within the range of the board.
		if(blockMoved==0)
		{
			//generating random number within the board
			blockMoved = rd.nextInt(board.length);
			//verify the conditions for the AI's move.
			while(!verifyCol(blockMoved)) 
			{
				blockMoved = rd.nextInt(board.length);
			}
			
			return blockMoved;
		}
		
		return blockMoved;
	}
	
	//check for all the conditions of winning
	public boolean makeMove(int row,int col)
	{
		//check the vertical for human and AI
		if(checkVertical(row,col,Status.ONE)==true)
		{
			return true;
		}
	    if(checkVertical(row,col,Status.TWO)==true)
		{
	    	return true;
		}
		
	    //check the horizontal for human and AI
	    if(checkHorizontal(row,col,Status.ONE)==true)
		{
	    	return true;
		}
		 if(checkHorizontal(row,col,Status.TWO)==true)
		{
			return true;
		}
		
		//check the forward diagonal for human and AI
		if(checkForDiagonal(row,col,Status.ONE)==true)
		{
			return true;
		}
	    if(checkForDiagonal(row,col,Status.TWO)==true)
		{
	    	return true;
		}
		
	    //check the backward diagonal for Human and AI
	    if(checkBackDiagonal(row,col,Status.ONE)==true)
		{
	    	return true;
		}
		if(checkBackDiagonal(row,col,Status.TWO)==true)
		{
			return true;
		}
		
		return false;
	}
	
	//checking the vertical
	private boolean checkVertical(int row,int col,Status ss)
	{
		for(int i = 0; i < board.length - 3; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				int count=0;
				for(int k=0;k<4;k++)
				{
					if(board[i+k][j]==ss)
					{
						count++;
					}
				}
				
				if(count==4)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	//check the horizontal
	private boolean checkHorizontal(int row,int col,Status ss)
	{
		for(int i = 0; i < board.length ; i++)
		{
			for(int j = 0; j < board.length-3; j++)
			{
				int count=0;
				for(int k=0;k<4;k++)
				{
					if(board[i][j+k]==ss)
					{
						count++;
					}
				}
				
				if(count==4)
				{
					return true;
				}
			}
		}
		return false;
	}
	//check forward diagonal
	private boolean checkForDiagonal(int row,int col,Status ss)
	{
		int i=row;
		int j=col;
		for( i = 3; i < board.length; i++)
		{
			for( j = 0; j < board.length - 3; j++)
			{
				int count=0;
				for(int k=0;k<4;k++)
				{
					if(board[i-k][j+k]==ss)
					{
						count++;
					}
				}
				
				if(count==4)
				{
					return true;
				}
			}
		}
		return false;
	}
	//check for the back diagonal
	private boolean checkBackDiagonal(int row,int col,Status ss)
	{
		int i=row;
		int j=col;
		for( i = 0; i < board.length - 3; i++)
		{
			for( j = 0; j < board.length - 3; j++)
			{
				int count=0;
				for(int k=0;k<4;k++)
				{
					if(board[i+k][j+k]==ss)
					{
						count++;
					}
				}
				
				if(count==4)
				{
					return true;
				}
			}
		}
		return false;
	}
}
