//-----------------------------------------
// NAME			  : Rutukumar Barvaliya 
// STUDENT NUMBER : 7870807
// COURSE		  : COMP 2150
// INSTRUCTOR	  : Mike Domaratzki
// ASSIGNMENT	  : assignment 2
// QUESTION		  : ConnectFour.java    
// 
// REMARKS        : it will implement the GameLogic class and its methods
//				  : it will set the answer and all the winning logic.
//-----------------------------------------

import java.util.Arrays;
import java.util.Random;
//implemented class of the GameLogic
public class ConnectFour implements GameLogic
{
	//all the private variables.
	private Status[][] board; // internal board storage.
	private int selectPlayer;
    //all the private Objects
	private HumanPlayer HU;
    private AIPlayer AI;
    private Random rd;
    
    //constructor
    public ConnectFour() 
    {
        rd = new Random();
        HU = new HumanPlayer();
        AI = new AIPlayer();
    }

    //setting the answer for the both human and AI.
    @Override
	public void setAnswer(int col) {
		// TODO Auto-generated method stub
    	if(selectPlayer==1) 
		{
    		int rw =  drop(col);
    		
			board[rw][col] = Status.ONE;
			if((makeMove(rw, col))==true)
			{
				HU.gameOver(Status.ONE);
				System.out.println("----------------------HUMAN WINS---------------");
				System.exit(0);
			}
			if(checkDraw()==true)
			{
				HU.gameOver(Status.NEITHER);
				System.exit(0);
			}
			else
			{
				selectPlayer=2;
				AI.lastMove(col);
			}
		}
		else
		{	
			int rw =  drop(col);
			
				board[rw][col] = Status.TWO;
				if(makeMove(rw, col)==true)
				{	
					AI.gameOver(Status.TWO);
					System.out.println("---------------------AI WINS-------------------");
					System.exit(0);
					
				}
				if(checkDraw()==true)
				{
					AI.gameOver(Status.NEITHER);
					System.exit(0);
				}else
				{
					selectPlayer=1;
					HU.lastMove(col);
				}
		}
	}
	
    //it will print the random board between 6-12 number.
    public void getTheBoard()
	{
		Random rd = new Random();
		int min=6;
		int max=12;
		int size = rd.nextInt((max-min)+1)+min;
		board = new Status[size][size];
		for (Status[] s : board) {
            Arrays.fill(s, Status.NEITHER);
        }
		System.out.println("BOARD");
		System.out.println("The board has the width: "+size+", and heigth: "+size);
	}
	
    //get the human
	public Human getHuman()
	{
		return HU;
	}
	
	/**
     * drop - a private helper method that finds the position of a marker
     * when it is dropped in a column.
     * @param col the column where the piece is dropped
     * @return the row where the piece lands
     */
    private int drop(  int col) {
        int posn = 0;
        while (posn < board.length && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }
	
    //it will start the game
	public void runGame()
	{
		//start the game.
		//print the board fist
		getTheBoard();
		//set the information for the human
		HU.setInfo(board.length, this);
		//set the information for the AI.
		AI.setInfo(board.length, this);
		//set the range for the min and max.
		int min=0;
		int max=4;
		//generate the random number number 6-12.
		selectPlayer = rd.nextInt((max-min))+min;
		//select 1 for the human
		if(selectPlayer==1)
		{
			HU.lastMove(-1);
		}
		//select 2 for the AI.
		else
		{
			AI.lastMove(-1);
		}
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
	
	//check for the draw
	private boolean checkDraw()
	{
		//check for draw
		int counter=0;
		int t=0;
		while(t<board[0].length && (board[0][t]!=Status.NEITHER))
		{
			counter++;
			t++;
		}
		if(counter==board[0].length)
		{
			return true;
		}
		
		return false;
	}
}