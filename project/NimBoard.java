
public class NimBoard{
	private int[] board;

	NimBoard(){
		board=new int[3];
		board[0]=3;
		board[1]=4;
		board[2]=5;
	}

	public int makeChange(int row, int count)//Takes in two ints
	//return 0 if the games continues, 1 if the games end, and 2 if there is an error
	{
		if (row>3 || row==0)
			return 2;
		if (board[row-1]<count || count==0)
			return 2;
		board[row-1]-=count;

		//checking if the board is empty
		count=0;
		for(int x=0; x<3;++x)
			count+=board[x];
		if(count==0)
			return 1;
		return 0;
	}
	public String toString()//this method can be used to print the board on the screen
	{
		String gameBoard="";
		for(int x=0;x<3;++x){
			for(int c=board[x];c>0;--c){
				gameBoard+="O";
			}
			gameBoard+="\n";
		}
		return gameBoard;
	}
}


