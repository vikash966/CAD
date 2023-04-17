
import java.net.Socket;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ClientHandler implements Runnable
{
	private Socket sender = null, receiver=null;
	private NimBoard board= null;

	ClientHandler(Socket sender, Socket receiver, NimBoard board)
	{
		this.sender = sender;
		this.receiver=receiver;
		this.board=board;
	}

	public void run(){
		Socket temp=null;
		String input="";
		int row=0, count=0, result=0;
		BufferedWriter outputToSender=null, outputToReceiver=null;
		BufferedReader clientInput=null;

		try{
			while(true){
				//get the output to sender and to receiver
				outputToSender = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));
				outputToReceiver = new BufferedWriter(new OutputStreamWriter(receiver.getOutputStream()));
				clientInput = new BufferedReader(new InputStreamReader(sender.getInputStream()));
				// connection made with sender
				System.out.println("Connection made with " + sender);

					input = clientInput.readLine();
					System.out.println("Received: " + input);
					try{
						row=Integer.parseInt(input);
					}
					catch(NumberFormatException e){
						row=0;
					}
					input = clientInput.readLine();
					System.out.println("Received: " + input);
					try{
						count=Integer.parseInt(input);
					}
					catch(NumberFormatException e){
						count=0;
					}

				result= board.makeChange(row,count);
				//if result equals 2, the move is invalid 
				if(result==2){
					System.out.println("Invalid move was made.");
					outputToSender.write("2\n");
					outputToSender.flush();
					outputToSender.write(board.toString());
					outputToSender.flush();
					continue;
				}
				//when the result is 1, player wins
				else if(result==1){
					System.out.println(sender+" has won the game.");
					outputToSender.write("1\n");
					outputToSender.flush();
					outputToSender.write(board.toString());
					outputToSender.flush();
					outputToReceiver.write("1\n");
					outputToReceiver.flush();
					outputToReceiver.write(board.toString());
					outputToReceiver.flush();


					outputToReceiver.close();
					outputToSender.close();
					clientInput.close();
					sender.close();
					receiver.close();
					break;
				}
				else{
				//updated board message
					System.out.println("Updated the board according to the changes.");
					outputToSender.write("0\n");
					outputToSender.flush();
					outputToSender.write(board.toString());
					outputToSender.flush();
					outputToReceiver.write("0\n");
					outputToReceiver.flush();
					outputToReceiver.write(board.toString());
					outputToReceiver.flush();
					System.out.println("Switching between sender and reciever...");
					temp=sender;
					sender=receiver;
					receiver=temp;
				}
			}
		}
		//exception handling
		catch (Exception e){
			System.out.println("Error: " + e.toString());
		}
		System.out.println("Ending a game between "+sender+" and "+receiver);
	}
} // ClientHandler for NimServer.java

