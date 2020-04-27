package Domino;

import java.util.ArrayList;
import java.util.Random;

public class board {
	
	//classe tabuleiro, representa o tabuleiro de jogo
	
	ArrayList<dominoes> dominoesInBoard = new ArrayList<dominoes>(); // a unica caracteristica do tabuleiro é os dominos que estão na mesa
																	 // inicialmente nunca há dominos

	
	public board() {
		
		
	}

	public void printBoard() {   //funcao que faz print de uma lista de dominos, neste caso vai ser o tabuleiro em si
		
		for(dominoes d : dominoesInBoard) {
		
			d.printDominoes();
			System.out.println();
			
			
		}
		
	}

	
	public String upperEdge() { // função que devolve o número superior do tabuleiro
		
		return dominoesInBoard.get(0).num1; 
		
	}
	
	
	public String bottomEdge() {// função que devolve o número inferior do tabuleiro
		
		return dominoesInBoard.get(dominoesInBoard.size()-1).num2;
		
	}
	
	
	public void addPiece(dominoes d, boolean human) { // função que recebe uma peça e verifica onde a tem de adicionar no tabuleiro
		
		print print = new print();
		
		String[] options = new String[2]; // as opções (top or bottom)
		options[0] = "1";
		options[1] = "2";
		
		
		if(dominoesInBoard.size()==0) { // se o tabuleiro estiver vazio, simplesmente adiciona a peça ao tabuleiro
			addTop(d);
		}
		
		else {							// se o tabuleiro já tiver peças
			
			String answer;
			
			boolean num1Up =upperEdge().equals(d.num1);			//verifica se o num1 é igual ao numero superior do tabuleiro
			boolean num1Bottom =bottomEdge().equals(d.num1);	//verifica se o num1 é igual ao numero inferior do tabuleiro
			
			boolean num2Up =upperEdge().equals(d.num2);			//verifica se o num2 é igual ao numero superior do tabuleiro
			boolean num2Bottom =bottomEdge().equals(d.num2);	//verifica se o num1 é igual ao numero inferior do tabuleiro
			
			if(upperEdge().equals(bottomEdge()) || (num1Up && num1Bottom) || (num2Up && num2Bottom) || (num1Up && num2Bottom) || (num2Up && num1Bottom)) { // se alguma destas condições se verificar a peça pode ir em simultaneo para cima ou para baixo
				
				if(human) { // se for humano pergunta onde quer colocar a peça
					System.out.println("Where do you want to place your domino? ");
					System.out.println("1) Top     2) Bottom \n \n");
					System.out.print(" ==> ");
					
					answer = print.inputVerification(options);
				}
				else { // se for um cp a jogar, escole aleatóriamente se quer jogar em cima ou em baixo
					
					Random rand = new Random();
					
					answer = options[rand.nextInt(2)];

				}
				
				if(answer.equals("1")) {
					
					if(num1Up) {  // se eles quiserem jogar em cima mas o num1 é o numero igual, então é preciso rodar a peça
						d.tradeNums();
					}
					addTop(d);    // adiciona a peça ao topo do tabuleiro
					
				}
				else {
					
					if(num2Bottom) { // se eles quiserem jogar em baixo mas o num2 é o numero igual, então é preciso rodar a peça
						d.tradeNums();
					}
					
					addBottom(d); // adiciona a peça ao fundo do tabuleiro
				
				}
				
			}
			
			// FIM DO IF COM AS CONDIÇÕES ONDE SE PODE JOGAR EM CIMA OU EM BAIXO
			
			else if(num1Up || num2Up) { // se a peça tiver de ser jogada em cima
				
				if(num1Up) {			// se eles quiserem jogar em cima mas o num1 é o numero igual, então é preciso rodar a peça
					d.tradeNums();
				}
				addTop(d);				// adiciona a peça ao topo do tabuleiro
				
			}
			
			else if(num1Bottom || num2Bottom) {
				
				if(num2Bottom) {		//se eles quiserem jogar em baixo mas o num2 é o numero igual, então é preciso rodar a peça
					d.tradeNums();
				}
				
				addBottom(d);			//adiciona a peça ao fundo do tabuleiro
			
			}
		}
		
		
	}
	
	
	private void addTop(dominoes d) { // função que adiciona uma peça ao topo do tabuleiro
		dominoesInBoard.add(0, d);
	}
	
	
	private void addBottom(dominoes d) { // função que adiciona uma peça no fundo do tabuleiro
		dominoesInBoard.add(d);
	}

	
	
	
	
	
	
	//Funcoes para o jogo Extra, NÃO SÃO ELEMENTOS DE AVALIAÇÃO PODEM SER IGNORADAS


	public int sumBoard() { // Soma o topo e o fundo do tabuleiro
		
		int upsum,downsum;
		
		if(dominoesInBoard.get(0).isDouble()) {
			upsum=dominoesInBoard.get(0).points;
		}
		else {
			upsum=Integer.parseInt(dominoesInBoard.get(0).num1);
		}
		
		if(dominoesInBoard.get(dominoesInBoard.size()-1).isDouble()) {
			
			downsum=dominoesInBoard.get(dominoesInBoard.size()-1).points;
		}
		else {
			downsum=Integer.parseInt(dominoesInBoard.get(dominoesInBoard.size()-1).num2);
		}
		
		if(dominoesInBoard.size()>1) {
			return upsum+downsum;
		}
		else {
			return upsum;
		}
		
		
		
	}
		
	
	public void printExtraBoard() {
		
		for(dominoes d : dominoesInBoard) {
		
			d.printExtraDominoes();
			System.out.println();
			
			
		}
		
	}


}
