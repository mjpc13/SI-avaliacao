package exercicio1;


import java.util.Random;
import java.util.concurrent.*;
import java.util.Scanner;


public class MainGame {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		boolean running=true;
		
		String[] menu_options = {"1","2","3"};
		int numberDisks;
		
		loadingScreen();
		
		while(running) { // ciclo infinito até o jogador carregar no 3 ( Exit )
			
			mainMenu();
			
			String menu_answer = inputVerification(menu_options);
			
			if(menu_answer.equalsIgnoreCase("1")) {
				
				numberDisks=newGame();
				long start = System.currentTimeMillis();
				firstPlay(numberDisks);
				long elapsedTimeMillis = System.currentTimeMillis()-start;
				
				float elapsedTimeMin = elapsedTimeMillis/(60*1000F);
				
				int minutes=(int) Math.round(Math.floor(elapsedTimeMin));
				
				int seconds =(int) Math.round((elapsedTimeMin-minutes)*60*10)/10;
				
				System.out.println("In the last game you took " + minutes +" minutes "+ "and " + seconds + " seconds to complete or quit the game! \n \n");
				
				System.out.println("=".repeat(100) + "\n");
				
			}

			else if(menu_answer.equalsIgnoreCase("2")) {
				
				rules();	//mostra as regras do jogo
			}
			
			else { //encerra o jogo
				System.out.println(" ".repeat(40)+"Thanks for playing! \n \n");
				System.out.println("=".repeat(100));
				
				running=false;
			}
		}
	}
	
	
	

	public static void printBoard(String[][] board) {
		
		for (String[] str : board){ //forEach cicle para percorrer todos os valores das strings

		    for (String str1 : str){
		    	
		        System.out.print(str1); // Faz o print em linha dos valores
		    }
		    
		    System.out.println(); // quando a linha acaba faz print de 1 paragrafo

		}
		System.out.println("\n \n Chose possible Moviment (Use [Y] to Abort):");
		System.out.println("\n 1:A->B   2:A->C  3:B->A  4:B->C  5:C->A  6:C->B \n \n");
		System.out.print(" ==> ");
	}
	
	public static String[][] createBoard(int n){

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
		        
		        else if(col>0 & col<height-2 & col>height-row-3 & col>=row-(n+1)) {
		        	
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
	
	public static void mainMenu() {
		
		System.out.println(" ".repeat(46)+"Main Menu \n");
		System.out.println(" ".repeat(46)+"1) New Game");
		System.out.println(" ".repeat(46)+"2) Help ");
		System.out.println(" ".repeat(46)+"3) Exit \n");
		System.out.print(" ==> ");
	}

	public static void rules() {
		
		System.out.println(" ".repeat(12)+"The Tower of Hanoi is a mathematical game or puzzle. It consists of three rods \n"
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
				" ".repeat(15)+"<> No larger disk may be placed on top of a smaller disk.\n \n");
		
		System.out.println("=".repeat(100));
		System.out.println("\n".repeat(2));
	}

	public static String inputVerification(String[] valid_options){
		
		// valid_options é uma lista de Strings que contêm todos os inputs aceites
		
		Scanner scan = new Scanner(System.in);
		String response = "";
		
		boolean valid=false;
		
		while(!valid) { // ciclo que só para quando o utilizador inserir um input válido
			
			response=scan.next(); // faz o scan da resposta do utilizador (assume que é String)
			
			for(String option : valid_options) {
				
				if(response.equalsIgnoreCase(option)) { 
					
					valid = true; // Se a resposta estiver dentro da lista cedida,
								  // assume que é válido e sai do ciclo.
				}
			}
			
			if(!valid) {
				System.out.println("=".repeat(100)+"\n");
				System.out.println(" ".repeat(35)+"Please insert a valid option... ¯\\_(ツ)_/¯ \n");
				System.out.print("==> ");
				
				// se não for válido o ciclo repete

			}
			
		}
		
		System.out.println("=".repeat(100));
		System.out.println("\n".repeat(2));
		
		return response;
		
	}

	public static int newGame() {
		
		String[] number_disk_options;
		number_disk_options = new String[8];
		
		for(int i=0; i<8;i++) {
			
			number_disk_options[i] = String.valueOf(i+3); // cria a lista de opções válidas
														  // neste caso {3,4,5,6,7,8,9,10}
			
		}
		
		System.out.println("Insert the number of Rods: \n");
		System.out.print(" ==> ");
		
		String answer = inputVerification(number_disk_options); // verifica se a resposta é válida
		
		
		return Integer.parseInt(answer);
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
					
					board[i][readPosition]="|";
					
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
	
	public static void firstPlay(int numberDisks){
	
		int count1 = 0;
		
		if(numberDisks==10) {
			System.out.println(" ".repeat(35) + "Damn, you're tough ᕦ(ò_óˇ)ᕤ \n \n");	
		}
		String[][] newBoard = createBoard(numberDisks);
		
		printBoard(newBoard);
		
		gamePlays(newBoard, numberDisks, count1);
	
	}
	
	public static void gamePlays(String[][] board, int numberDisks, int count1) {
		
		//number of disks da para calcular com o board, mudar para ficar mais simples
		if(board[1][board[1].length-(numberDisks+2)].equals("*")) {
					// o jogo acabou...	
			
			if(count1==Math.pow(2, numberDisks)-1) {
				
				System.out.println("\n Excelent Job! You won the game in the perfect amount of moves! I am proud of you ♥‿♥  \n Number of moves: " + count1 + "\n \n");
				
			}
			else {
				
				System.out.println("\n You just won the game, but i believe you can do better! \\ (•◡•) /  \n Number of moves: " + count1 + "\n \n");
				
			}
		}
		else {
			
			String move = inputVerification(validPlayOptions(board, numberDisks));
			
			if(move.equalsIgnoreCase("Y")) {
				System.out.println(" ".repeat(30)+"Why did you give up ? ◉_◉ \n \n");
			}
			else {
				
				board = moveDisks(board, numberDisks, move);
				count1++;
				
				printBoard(board);
				
				gamePlays(board,numberDisks,count1);
			
			}
		}	
	}
}