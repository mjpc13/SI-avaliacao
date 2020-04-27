package Domino;

import java.util.ArrayList;

//classe dos jogadores

public class player {
	
	String name;				//nome do jogador
	ArrayList<dominoes> hand;	//mao do jogador
	boolean human;				//boolean para ver se o jogador é humano ou não (se for humano fica com valor de TRUE)
	int setPoints=0;			// pontos adquiridos ao longo do set
	int gamePoints=0;			//pontos adquiridos ao longo do jogo
	
	boolean dealer=false;		// se é dealer
	boolean winner=false;		// se foi vencedor do ultimo set
	boolean gameWinner=false;	//se é o vencedor final do jogo
	
	
	public player(String playerName, boolean human) {
		
		this.name = playerName;
		this.hand = new ArrayList<dominoes>(); // cria o objeto mão, que inicialmente está vazia
		this.human =human;
	}
	
	
	
	
	
	
	
	public int totalSum() {  //faz a soma dos pontos que estão na mão do jogador
		
		int sum=0;
		
		for(dominoes d : hand) {
			
			sum=sum+d.points;
			
		}
		return sum;
	}
	
	
	public board play(board board) throws InterruptedException {
		
		dominoes cp_domino = null;
		
		if(human) { //caso em que o jogador é humano
			System.out.println("It's your turn player " + name +": \n"); // prints iniciais
			
			
			System.out.println("\n" +"-".repeat(45)+" Your Hand " + "-".repeat(44)+"\n");
			
			String[] validPlays = new String[8];
			validPlays[7]="P";					//acrescenta a opção para passar a lista das opções váldas
			
			for(int i=0;i<hand.size();i++) {
				
				System.out.println(i+1+")");
				
				hand.get(i).printDominoes();
				
				
				
				if(board.dominoesInBoard.size()!=0) {  //garantir que o tabuleiro não está vazio, se estiver todas as peças são admissiveis
					if(hand.get(i).num1.equals(board.bottomEdge()) || hand.get(i).num2.equals(board.upperEdge()) || hand.get(i).num2.equals(board.bottomEdge()) || hand.get(i).num1.equals(board.upperEdge())) {
						
						validPlays[i]=String.valueOf(i+1);	//se a peça puder ser jogada, adiciona o número correspondente a lista de opções válidas
						
					}
				}
				
				else {
					for(int j=1;j<8;j++) {
						validPlays[j-1]=String.valueOf(j); //adiciona todas as opções, pois o tabuleiro está vazio
					}
				}
				
			}
			
			if(board.dominoesInBoard.isEmpty()) {
				System.out.println("\n Choose the piece you want to play (press [P] to pass)");//print para quando está vazio 
			}
			else {//print que avisa o jogador qual os numeros jogáveis
				System.out.println("\n Choose the piece you want to play (press [P] to pass)"+": ( ↑"+board.upperEdge()+", ↓"+board.bottomEdge()+" )");
			}
			
			System.out.print(" ==> ");
			
			String answer = print.inputVerification(validPlays); // a opção que o jogador escolhe
			
			if(answer.equalsIgnoreCase("P")) {// se o jogador passar, faz return do board sem alterações
				return board;
			}
			else { 
				
				board.addPiece(hand.get(Integer.parseInt(answer)-1), human); // adiciona a peça ao tabuleiro
				
				hand.remove(hand.get(Integer.parseInt(answer)-1)); // remove a peça da mão do jogador
				
			}
			
		}
		
		
		else { // caso em que é o CP a jogar
			

			String[] validPlays = new String[8];
			validPlays[7]="P";
			
			
			for(int i=0;i<hand.size();i++) { // igual ao jogador, para verificar que opções são válidas (podia estar numa função a parte para ser lida mais facilmente)
				
				if(board.dominoesInBoard.size()!=0) {
					if(hand.get(i).num1.equals(board.bottomEdge()) || hand.get(i).num2.equals(board.upperEdge()) || hand.get(i).num2.equals(board.bottomEdge()) || hand.get(i).num1.equals(board.upperEdge())) {
						
						validPlays[i]=String.valueOf(i+1);
						
					}
				}
				
				else {
					for(int j=1;j<8;j++) {
						validPlays[j-1]=String.valueOf(j);
					}
				}
				
			}
			
			for(String i : validPlays) { //percorre as opções válidas (e irá escolher a que a soma dos pontos seja a maior [em caso de empate é a última])
				
				if(i == null) { 
					
				}
				
				else if(i.equals("P")) { // se for igual a P (significa que já percorreu a lista toda)
					
					if(cp_domino==null){ 
						return board;		// se cp_domino não tiver valor significa que não há peças válidas e o cp é obrigado a passar
					}
					
				}
				
				else {
					
					if(cp_domino==null) { // se o cp_domino ainda não tiver valor, e a opção for nula, vai ficar com esta peça
						
						cp_domino = hand.get(Integer.parseInt(i)-1);
					
					}
					
					else if(cp_domino.points<hand.get(Integer.parseInt(i)-1).points) { //sempre que a peça tenha uma soma superior ao cp_domino, o valor
																					  // do cp_domino é atualizado para a nova peça
						cp_domino = hand.get(Integer.parseInt(i)-1);
						
					}

				}
				
			}
			
			board.addPiece(cp_domino, human); // adiciona a peça do cp ao tabuleiro
			hand.remove(cp_domino);			 // remove a peça do cp da mao
			
			System.out.println("It's your turn player " + name +": \n");
			
			System.out.println("-".repeat(44)+" Table Game " + "-".repeat(44)+"\n");
			
			board.printBoard(); // faz print do tabuleiro
			
			System.out.println("\n" +"-".repeat(100)+"\n");
			
			Thread.sleep(1500); // delay para se conseguir ver cada cp a jogar
			
			
		}
		
		return board; // faz return do tabuleiro atualizado com a nova peça

	}
	
	//Funcoes para o jogo Extra, NÃO SÃO ELEMENTOS DE AVALIAÇÃO PODEM SER IGNORADAS
	
	public board playExtra(board board) throws InterruptedException {
		
		dominoes cp_domino = null;
		
		if(human) {
			
			System.out.println("It's your turn player " + name +": \n");
			
			System.out.println("\n" +"-".repeat(45)+" Your Hand " + "-".repeat(44)+"\n");
			
			String[] validPlays = new String[8];
			validPlays[7]="P";
			
			for(int i=0;i<hand.size();i++) {
				
				System.out.println(i+1+")");
				
				hand.get(i).printDominoes();
				
				
				
				if(board.dominoesInBoard.size()!=0) {
					if(hand.get(i).num1.equals(board.bottomEdge()) || hand.get(i).num2.equals(board.upperEdge()) || hand.get(i).num2.equals(board.bottomEdge()) || hand.get(i).num1.equals(board.upperEdge())) {
						
						validPlays[i]=String.valueOf(i+1);
						
					}
				}
				
				else {
					for(int j=1;j<8;j++) {
						validPlays[j-1]=String.valueOf(j);
					}
				}
				
			}
			
			System.out.println("\n You have "+ setPoints +" points");
			
			if(board.dominoesInBoard.isEmpty()) {
				System.out.println("\n Choose the piece you want to play ");
			}
			else {
				System.out.println("\n Choose the piece you want to play (press [P] to pass)"+": ( ↑"+board.upperEdge()+", ↓"+board.bottomEdge()+" )");
			}
			
			System.out.print(" ==> ");
			
			String answer = print.inputVerification(validPlays);
			
			if(answer.equalsIgnoreCase("P")) {
				return board;
			}
			else {
				
				board.addPiece(hand.get(Integer.parseInt(answer)-1), human);
				
				hand.remove(hand.get(Integer.parseInt(answer)-1));
				
				if(board.sumBoard()%5==0) {
					
					setPoints=setPoints+board.sumBoard();
					
				}
				
			}
			
		}
		
		
		else {
			
			boolean five_multiple=false;
			String[] validPlays = new String[8];
			validPlays[7]="P";
			
			
			for(int i=0;i<hand.size();i++) {
				
				if(board.dominoesInBoard.size()!=0) {
					if(hand.get(i).num1.equals(board.bottomEdge()) || hand.get(i).num2.equals(board.upperEdge()) || hand.get(i).num2.equals(board.bottomEdge()) || hand.get(i).num1.equals(board.upperEdge())) {
						
						validPlays[i]=String.valueOf(i+1);
						
					}
				}
				
				else {
					for(int j=1;j<8;j++) {
						validPlays[j-1]=String.valueOf(j);
					}
				}
				
			}
			
			for(String i : validPlays) {
				
				if(i == null) {
					//does nothing if the null is in the i position
				}
				
				else if(i.equals("P")) {
					
					if(cp_domino==null){
						return board;
					}
					
				}
				
				else {
					
					// da maneira como construi o código é complicado eu dizer ao jogador para tentar fazer 0/5/10 pontos de preferencia, vou deixar assim...
					
					if(cp_domino==null) {
						
						cp_domino = hand.get(Integer.parseInt(i)-1);
						
					
					}
					
					else if(cp_domino.points<hand.get(Integer.parseInt(i)-1).points) {
						
						cp_domino = hand.get(Integer.parseInt(i)-1);
						
					}

				}
				
			}
			
			board.addPiece(cp_domino, human);
			hand.remove(cp_domino);
			
			if(board.sumBoard()%5==0) {
				
				setPoints=setPoints+board.sumBoard();
				
			}
			
			System.out.println("It's your turn player " + name +": \n");
			
			System.out.println("-".repeat(44)+" Table Game " + "-".repeat(44)+"\n");
			
			board.printExtraBoard();
			
			System.out.println("\n" +"-".repeat(100)+"\n");
			
			Thread.sleep(1500);
			
			
		}
		
		return board;

	}
	
	
}
