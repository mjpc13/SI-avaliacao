package exercicio3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * A simple greeting server. More information about sockets at:
 * http://download.oracle.com/javase/tutorial/networking/sockets/index.html
 * and in the book "Head First Java" - Chapter 15.
 */
public class Server {
	// 'throws IOException' enables us to write the code without try/catch blocks
	// but it also keeps us from handling possible IO errors 
	// (when for instance there is a problem when connecting with the other party)
	
	public static void main(String args[]) throws IOException {
		
		ArrayList<String> users_passwords= new ArrayList<>();
		users_passwords.add("mario-1234");
		users_passwords.add("micael-qwerty");
		users_passwords.add("admin-password");
		
		// Register service on port 1234
		ServerSocket s = new ServerSocket(1234);
		
		String separator = "\n"+"=".repeat(100)+"\n\n";
		String r="";
		while (true) {
			
			System.out.println("> Waiting for a client..."+"\n");
			
			// Wait and accept a connection
			try {
			
			Socket s1 = s.accept();
			
			System.out.println("> A connection with a client was establish"+"\n");

			// Build DataStreams (input and output) to send and receive messages to/from the client
			OutputStream out = s1.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);

			InputStream in = s1.getInputStream();
			DataInputStream dataIn = new DataInputStream(in);
			boolean connected=true;
			String credentials = null;
			
			String[] login_menu_options = {"1","2","3"};
			
			while(connected) {
				
				dataOut.writeUTF(loginMenu());
				dataOut.flush();
				
				String menu_answer = inputVerification(login_menu_options, dataIn, dataOut);
				float[][] stats = new float[8][3];
				for(int i=0;i<8;i++){
					stats[i][0]=0;
					stats[i][1]=0;
					stats[i][2]=0;
				}

				System.out.println("> the Client choose option "+menu_answer+") for the login menu"+"\n");
				
				if(menu_answer.equals("1") || menu_answer.equals("2") ) {
					
					if(menu_answer.equals("1")) {
						credentials = login(dataIn,dataOut);
					}
					
					else if(menu_answer.equalsIgnoreCase("2")) {
						users_passwords=signUp(users_passwords,dataIn,dataOut);
						credentials = users_passwords.get(users_passwords.size()-1);
					}

					if(users_passwords.contains(credentials)) {
						
						boolean running=true;
						String show_stats="";
						int[] game_conditions;
						String[] main_menu_options = {"1","2","3","4"};
						
						while(running) { // ciclo infinito até o jogador carregar no 3 ( Exit )
							
							dataOut.writeUTF(separator+r+show_stats+separator+mainMenu());
							dataOut.flush();
							r="";
							show_stats="";
							
							
							menu_answer = inputVerification(main_menu_options, dataIn, dataOut);
							System.out.println("> the Client choose option "+menu_answer+") for the main menu"+"\n");
							
							if(menu_answer.equals("1")) {
								
								game_conditions = newGame(dataIn,dataOut);
								
								long start = System.currentTimeMillis();
								Date resultdate = new Date(start);


								System.out.println("> A new game was initiated at "+ resultdate+"\n");
								System.out.println("> Initial Options:\n"
										+" ".repeat(20)+"-> # of disks: "+game_conditions[0]+"\n"+" ".repeat(20)+"-> Initial Pole: "+game_conditions[1]+"\n"+" ".repeat(20)+"-> Final Pole: "
												+ +game_conditions[2]+"\n");
								
								stats=firstPlay(game_conditions, dataIn, dataOut, stats);
								long end = System.currentTimeMillis();
								Date enddate = new Date(end);
								System.out.println("> The game was ended at "+ enddate+"\n");
								
								
								
							}

							else if(menu_answer.equalsIgnoreCase("2")) {
								
								r=separator+rules();	//mostra as regras do jogo
							}
							
							else if(menu_answer.equals("3")){

								// show stats
								show_stats="";

								for(int i=0;i<8;i++){

									if(stats[i][1]!=0){
										int j=i+3;
										show_stats+="For "+j+" you have in average "+String.format("%.02f", stats[i][0])+
										" moves, you played "+String.format("%.00f", stats[i][1])+" and quited "+String.format("%.00f", stats[i][2])+" times\n";

									}


								}

								if(!show_stats.equals("")){
									show_stats="Statistics:\n\n"+show_stats+"\n";
									
								}
								else{
									show_stats="Statistics:\n\n"+" Next time try to play first!! ;-) \n\n";
									
								}

								
								


							}

							else { //encerra o jogo
								
								System.out.println("> Client exit Main Menu\n");

								running=false;
								
							}
						}
						
					}
					else{

						dataOut.writeUTF("> Wrong username or password \n> You just disconnected");
						dataOut.flush();
					
					System.out.println("> Client Disconected, wrong credentials");
					System.out.println("-".repeat(100)+"\n");
					
					connected=false;

					}

				}
				else {
					
					dataOut.writeUTF("> You just disconnected");
					dataOut.flush();
					
					System.out.println("> Client Disconected");
					System.out.println("-".repeat(100)+"\n");
					
					connected=false;
				}
				
			}
			
			// Cleanup operations, close the streams, the connection, but don't close the ServerSocket
			dataOut.close();
			dataIn.close();
			s1.close();
			} catch( IOException io) {
				
				System.out.println("> Client Disconected");
				System.out.println("-".repeat(100));
				
			}
		}
	}
	
	public static String printBoard(String[][] board) {
		
		String string="\n\n";
		
		for (String[] str : board){ //forEach cicle para percorrer todos os valores das strings

		    for (String str1 : str){
		    	
		        string=string+str1; // Faz o print em linha dos valores
		    }
		    
		    string=string+"\n";// quando a linha acaba faz print de 1 paragrafo

		}
		string=string+"\n \n Chose possible Moviment (Use [Y] to Abort): \n" + "\n 1:A->B   2:A->C  3:B->A  4:B->C  5:C->A  6:C->B \n\n\n==> ";
		
		return string;
	}
	
	public static String[][] createBoard(int n, int startingPole, int endingPole){
		
		int height = n+3;
		int width = ((n*2+1)+2)*3;

		String[][] cleanBoard;
		cleanBoard = new String[height][width];
		
		for(int row=0;row<width;row++){ // percorre todos os indices do tabuleiro

		    for(int col=0;col<height;col++){
	    		
		    	
		        if(col==height-2){ 
		        	
		        	// desenha o fundo do tabuleiro
		            cleanBoard[col][row] = "#";
		        
		        }
		        
		        else if((row==(((endingPole)*(width/3))-(width/6+1))) & col!= height-1){
		            
		            cleanBoard[col][row] = "I"; // desenha os postes do poste final
		            
		        }
		        else if((col>0) & col<height-2 & col>height-row-3+width/3*(startingPole-1) & col>=row-(n+1+width/3*(startingPole-1)))  {
		        	
		        	cleanBoard[col][row] = "*"; // desenha a pirâmide inicial
		        	
		        }
		        
		        else if((row==(n+1) || row==(width/2) || row==width - (n+2)) & col!= height-1){
		            
		            cleanBoard[col][row] = "|"; // desenha os postes
		            
		        }
		        

		        
		        else{
		            cleanBoard[col][row] = " "; // preenche o resto da Matriz com espaços em branco
		        }

		    }
		    
		}
		
		// faz print das letras a identificar os postes;
		
		cleanBoard[height-1][n] = "[";
		cleanBoard[height-1][n+1] = "A";
		cleanBoard[height-1][n+2] = "]";
		
		cleanBoard[height-1][width/2-1] = "[";
		cleanBoard[height-1][width/2] = "B";
		cleanBoard[height-1][width/2+1] = "]";
		
		cleanBoard[height-1][width - (n+2)-1] = "[";
		cleanBoard[height-1][width - (n+2)] = "C";
		cleanBoard[height-1][width - (n+2)+1] = "]";
		
		
		return cleanBoard;
	}
	
	public static void loadingScreen() throws InterruptedException {
		
		// Feature para embelezar o programa, 
		// pode ser comentada para que o Loading não demore muito tempo
		
		System.out.println("=".repeat(100));
		System.out.println(" ".repeat(42)+"Hanoi Tower Game");
		System.out.println();
		System.out.println(" ".repeat(26)+"Game designed by Mário Cristóvão & Micael Pires \n \n");

		System.out.println("=".repeat(100));
		System.out.println("\n");
		
	}
	
	public static String mainMenu() {
		
		String main_menu = " ".repeat(46)+" Main Menu \n \n"+" ".repeat(46)+"1) New Game\n"+" ".repeat(46)+"2) Help \n"+" ".repeat(46)+"3) Stats \n"+" ".repeat(46)+"4) Log Out \n\n"
				+ "==> ";
		
		return main_menu;
	}

	public static String loginMenu() {
		
		String main_menu = " ".repeat(35)+" Welcome to our Hanoi tower game \n \n"+" ".repeat(46)+"1) Login\n"+" ".repeat(46)+"2) Sign Up \n"+" ".repeat(46)+"3) Exit \n\n"
				+ "==> ";
		
		return main_menu;
		
	}
	
	public static String rules() {
		
		String rules = " ".repeat(12)+"The Tower of Hanoi is a mathematical game or puzzle. It consists of three rods \n"
				+ " ".repeat(10) + "and a number of disks of different sizes, which can slide onto any rod.\n \n"
				+ " ".repeat(12) + "The puzzle starts with the disks in a neat stack in ascending order of size on one rod, \n"
				+ " ".repeat(10) + "the smallest at the top, thus making a conical shape.\n" + 
				"\n" + 
				" ".repeat(12) + "The objective of the puzzle is to move the entire stack to another rod,\n"
				+ " ".repeat(10) + "obeying the following simple rules:\n" + 
				"\n" + 
				" ".repeat(15)+"<> Only one disk can be moved at a time.\n \n" + 
				" ".repeat(15)+"<> Each move consists of taking the upper disk from one of the stacks and placing \n"
				+ " ".repeat(12) + "it on top of another stack or on an empty rod.\n \n" + 
				" ".repeat(15)+"<> No larger disk may be placed on top of a smaller disk.\n \n \n";
		
		return rules;
	}

	public static String inputVerification(String[] valid_options,DataInputStream dataIn, DataOutputStream dataOut) throws IOException{
		
		// valid_options é uma lista de Strings que contêm todos os inputs aceites
		
		String response = "";
		String separators = "\n"+"=".repeat(100)+"\n\n";
		boolean valid=false;
		
		
		
		while(!valid) { // ciclo que só para quando o utilizador inserir um input válido
			
			response=dataIn.readUTF(); // faz o scan da resposta do utilizador (assume que é String)

			for(String option : valid_options) {
				
				if(response.equals(option)) { 
					
					valid = true; // Se a resposta estiver dentro da lista cedida,
								  // assume que é válido e sai do ciclo.
				}
			}
			
			if(!valid) {
				
				String invalid_submission=" ".repeat(35)+"Please insert a valid option... ¯\\_(ツ)_/¯ \n\n ==> ";  // se não for válido o ciclo repete
				System.out.println("> Cliente inserted an invalid option");
				dataOut.writeUTF(separators+invalid_submission);
				dataOut.flush();

			}
			
		}
		return response;
	}

	public static int[] newGame(DataInputStream dataIn, DataOutputStream dataOut) throws IOException {
		String separator = "\n"+"=".repeat(100)+"\n\n";
		String[] number_disk_options;
		number_disk_options = new String[8];
		String[] pole_options = {"A","B","C"};
		
		String answer = "Insert the finishing pole [";
		
		for(int i=0; i<8;i++) {
			
			number_disk_options[i] = String.valueOf(i+3); // cria a lista de opções válidas
														  // neste caso {3,4,5,6,7,8,9,10}
		}
		
		dataOut.writeUTF(separator+"Insert the number of Rods: \n\n==> ");
		dataOut.flush();
		
		String disks = inputVerification(number_disk_options,dataIn,dataOut); // verifica se a resposta é válida
		
		dataOut.writeUTF(separator+"Insert the starting pole "+Arrays.toString(pole_options)+": \n \n==> ");
		dataOut.flush();
		
		char starting_pole =inputVerification(pole_options,dataIn,dataOut).charAt(0); // verifica se a resposta é válida
		
		for(int i=0; i<3;i++) {
			
			if(pole_options[i].equals(String.valueOf(starting_pole))) {
				pole_options[i]=null;
			}
			else {
				answer=answer+pole_options[i]+",";
			}
			

		}
		
		answer=answer.substring(0, answer.length() - 1);
		
		dataOut.writeUTF(separator+answer+"]: \n \n==> ");
		dataOut.flush();
		
		char finishing_pole = inputVerification(pole_options,dataIn,dataOut).charAt(0); // verifica se a resposta é válida
		
		int[] initial_conditions = {Integer.parseInt(disks),Character.getNumericValue(starting_pole)-9,Character.getNumericValue(finishing_pole)-9}; //see ASCII Table
		
		return initial_conditions;
	}

	public static int countDiskSize(String[][] board, int n,int pole) {
		
		int count=0; //função que conta o tamanho do primeiro do disco mais pequeno no posto em questão
		
		for(int i=0;i<board.length; i++) {

			if(board[i][pole].equalsIgnoreCase("*")) {
				
				for(int j=pole;j<=n+pole; j++) {
					
					if(board[i][j].equalsIgnoreCase("*")) {
						count=count+1;
					}
				}
				return count;
			}
		}
		
		return count;
	}

	
	public static String[][] moveDisks(String[][] board, int n, String move){

		int A = n+1;
		int B = board[1].length/2;
		int C = board[1].length-(n+2);
		
		int[] firstOut = {A,A,B,B,C,C}; //{"A","A","B","B","C","C"};
		int[] firstIn = {B,C,A,C,A,B};  //{"B","C","A","C","A","B"}
		
		int readPosition = firstOut[Integer.parseInt(move)-1];
		int writePosition = firstIn[Integer.parseInt(move)-1];
		
		if(move.equalsIgnoreCase("Y")) {
			
		}
		else {
			
			int sizeOfDisk = countDiskSize(board, n, readPosition);
			
			for(int i=0;i<board.length; i++) {
				
				if(board[i][readPosition].equalsIgnoreCase("*")) {
					
					board[i][readPosition]=board[i-1][readPosition];
					
					for(int p1 = 1; p1<=sizeOfDisk;p1++) {
						
						board[i][readPosition+p1]=" ";
						board[i][readPosition-p1]=" ";
						
					}
					break;
				}
				
			}
			
			for(int i=0;i<board.length; i++) {
				
				if(board[i][writePosition].equalsIgnoreCase("*") || board[i][writePosition].equalsIgnoreCase("#")) {
					
					board[i-1][writePosition]="*";
					
					for(int p2 = 1; p2<sizeOfDisk;p2++) {
						
						board[i-1][writePosition+p2]="*";
						board[i-1][writePosition-p2]="*";
					}				
					break;
				}
			}
			
		}
		return board;
	}
	
	
	public static String[] validPlayOptions(String[][] board, int n) {
		
		int A = n+1;
		int B = board[1].length/2;
		int C = board[1].length-(n+2);
		
		int[] firstOut = {A,A,B,B,C,C}; // posiçãões de onde sai o disco
		int[] firstIn = {B,C,A,C,A,B};  // posições de onde entra o disco
		
		String[] inGameOptions = new String[7];
		
		inGameOptions[6]="Y";
		
		for(int i=0; i<6; i++) {
			
			if((countDiskSize(board, n, firstOut[i])<countDiskSize(board, n, firstIn[i]) && countDiskSize(board, n, firstOut[i])!=0) || (countDiskSize(board, n, firstOut[i])>countDiskSize(board, n, firstIn[i]) && countDiskSize(board, n, firstIn[i])==0) ) {
				
				inGameOptions[i]=Integer.toString(i+1);
				
			}
			
		}
		return inGameOptions;
	}
	
	
	public static float[][] firstPlay(int[] initial_conditions,DataInputStream dataIn, DataOutputStream dataOut, float[][] stats) throws IOException {
		String separator = "\n"+"=".repeat(100)+"\n\n";
		int count1 = 0;
		

		String[][] newBoard = createBoard(initial_conditions[0],initial_conditions[1],initial_conditions[2]);
		
		dataOut.writeUTF(separator+printBoard(newBoard));
		dataOut.flush();
		
		
		stats=gamePlays(newBoard, initial_conditions, count1,dataIn,dataOut, stats);

		return stats;

	}
	
	
	public static float[][] gamePlays(String[][] board, int[] initial_conditions, int count1,DataInputStream dataIn, DataOutputStream dataOut, float[][] stats) throws IOException {
		
		String separator = "\n"+"=".repeat(100)+"\n\n";
		int numberDisks=initial_conditions[0];
		int ending_pole = initial_conditions[2];
		int width = ((numberDisks*2+1)+2)*3;

		String move = inputVerification(validPlayOptions(board, numberDisks),dataIn,dataOut);
			
			
			
		if(move.equalsIgnoreCase("Y")) {
			System.out.println("> Client just quit the game ["+count1+"] \n");
			stats[numberDisks-3][2]+=1;
			stats[numberDisks-3][1]+=1;
			return stats;
		}
		else {
			System.out.println("> Client chose option "+move+") to move the disk ["+count1+"] \n");
			board = moveDisks(board, numberDisks, move);
			count1++;
				
			if(board[1][ending_pole*(width/3)-(width/6+1)].equals("*")) {
				// o jogo acabou...	
				
				stats[numberDisks-3][1]+=1;
				stats[numberDisks-3][0]=(stats[numberDisks-3][0]*(stats[numberDisks-3][1]-stats[numberDisks-3][2]-1)+count1)/(stats[numberDisks-3][1]-stats[numberDisks-3][2]);

				if(count1==Math.pow(2, numberDisks)-1) {
					

					dataOut.writeUTF(separator+printBoard(board)+"\n\n Excelent Job! You won the game in the perfect amount of moves! I am proud of you ♥‿♥  \n Number of moves: "
					+ count1 + "\n Press any key to continue... \n\n==> ");
					dataOut.flush();
					dataIn.readUTF();
						
				}
				else {

					dataOut.writeUTF(separator+printBoard(board)+"\n\n You just won the game, but i believe you can do better! \\ (•◡•) /  \n Number of moves: "
					+ count1 + "\n Press any key to continue... \n\n==> ");
					dataOut.flush();
					dataIn.readUTF();
					
				}
				System.out.println("> Client just ended the game in "+count1+" moves\n");
				return stats;
			}
				
			else {
				dataOut.writeUTF(separator+printBoard(board));
				dataOut.flush();
					
				stats=gamePlays(board,initial_conditions,count1,dataIn,dataOut,stats);
				return stats;
			}
				
		}
			
}


	public static String login(DataInputStream dataIn, DataOutputStream dataOut) throws IOException {
		
		String separator = "\n"+"=".repeat(100)+"\n\n";
		dataOut.writeUTF(separator+"Insert your username: \n \n==> ");

		dataOut.flush();
		
		String username = dataIn.readUTF();
		System.out.println("> Client username: " + username+"\n");
		
		dataOut.writeUTF(separator+"Insert your password: \n \n==> ");

		// you can flush the stream to force writing
		dataOut.flush();
		
		String password = dataIn.readUTF();
		System.out.println("> Client password: " + password+"\n");
		
		String credentials = username + "-" + password;
		return credentials;
	}

	public static ArrayList<String> signUp(ArrayList<String> users_passwords,DataInputStream dataIn, DataOutputStream dataOut) throws IOException{
		
		String new_user =login(dataIn,dataOut);

		System.out.println("> New user register: "+new_user);
		users_passwords.add(new_user);
		
		return users_passwords;

	}
	
}
