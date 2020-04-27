package Domino;

import java.util.ArrayList;
import java.util.Scanner;

// classe que trata da maioria dos prints, o código é explicito por si só, não acredito que seja necessário comentar

public class print {
	
	public print() {}
	
	public void mainMenu() {
		
		System.out.println(" ".repeat(46)+"Main Menu \n");
		System.out.println(" ".repeat(46)+"1) New Normal Game");
		System.out.println(" ".repeat(46)+"2) Extra Game");
		System.out.println(" ".repeat(46)+"3) Help ");
		System.out.println(" ".repeat(46)+"4) Exit \n");
		System.out.print(" ==> ");
	}
	
	public void invalidInput() {
		
		System.out.println("=".repeat(100)+"\n");
		System.out.println(" ".repeat(35)+"Please insert a valid option... ¯\\_(ツ)_/¯ \n");
		System.out.print("==> ");
	
	}
	
	public void exitMessage() {
		System.out.println(" ".repeat(40)+"Thanks for playing! \n \n");
		System.out.println("=".repeat(100));
	}

	public void rules() {
																																//
		System.out.println(" ".repeat(5) + "Rules: \n \n" +
				
				" ".repeat(5) + "<> A game is formed by 4 playes (3 CPU & 1 Human) that play a undefined number of sets.  \n"
				+ " ".repeat(5)+"   The game ends when a player gets to 50 points \n \n"
				
				+ " ".repeat(5)+"<> In each set the players start with 7 dominoes \n \n"
				
				+ " ".repeat(5)+ "<> The players play one at a time in a clock-wise direction.The first to play\n"
				+ " ".repeat(5)+ "   is the last set winner, or randomly if it's the first round \n \n"
				+ " ".repeat(5)+ "<> A set ends when one players gets rid of every dominoe on his hand, if the game get's stuck, \n"
				+ " ".repeat(5)+ "   the player with less points win\n \n"
				+ " ".repeat(5)+ "<> At the end of each set the players gain the points they have in hand. \n \n"
				+ " ".repeat(5)+ "<> The points of each dominoe are given by the sum of both numbers, \n"
				+ " ".repeat(5)+ "   the only exception is the [0 | 0 ] in that case the sum is 10. \n \n"
				+ " ".repeat(5)+ "<> Each player can only play 1 dominoe per turn, and the dominoe played \n"
				+ " ".repeat(5)+ "   must have 1 of his numbers equal to the numbers in the edges of the board. \n \n"
				+ " ".repeat(5)+ "<> If there is no available dominoe the player must pass the turn \n \n"
				+ " ".repeat(5)+ "<> The winner is the player that gathered less points at the end of the game \n \n \n"
				+ " ".repeat(5)+ "EXTRA GAME Rules: (Not for evaluation) \n \n"
				+ " ".repeat(5)+ "<> The player receive points each time he plays a piece that makes the sum of the edges \n"
				+ " ".repeat(5)+ "   of the board multiple of 5 (and receives this number in points) \n \n"
				+ " ".repeat(5)+ "<> At the end of each set, the winner receives the sum of points (rounded to the closest \n"
				+ " ".repeat(5)+ "   multiple of 5) of each players hands \n \n"
				+ " ".repeat(5)+ "<> The game ends when a player (the winner) gathers 100 points or more \n \n"
				);
		
		System.out.println("=".repeat(100));
		System.out.println("\n".repeat(2));
	}
	
	public void set(int num) {
		
		System.out.println("\n");
		System.out.println("=".repeat(47)+ " Set "+num+" "+"=".repeat(46));
		
	}
	
	public void round(int num) {
		
		System.out.println("\n");
		System.out.println("=".repeat(46)+ " Round "+num+" "+"=".repeat(45));
		
	}
	
	public void printSetPoints (ArrayList<player> players) {
		
		System.out.println("\n");
		System.out.println("=".repeat(44)+ " Set Points "+"=".repeat(44)+"\n");
		
		for(player p : players) {
			
			System.out.println(" ".repeat(38)+"Player "+p.name+ " => "+ p.setPoints+ " points");
			
		}
		
	}

	public void printGamePoints (ArrayList<player> players) {
		
		System.out.println("\n");
		System.out.println("=".repeat(44)+ " Game Points "+"=".repeat(43)+"\n");
		
		for(player p : players) {
			
			System.out.println(" ".repeat(38)+"Player "+p.name+ " => "+ p.gamePoints+ " points");
			
		}
		
	}

	public String printExitOption() {
		Scanner scan = new Scanner(System.in);
		System.out.println("\n If you want to quit the game press [Y], to continue press any key \n");
		System.out.print(" ==> ");
		return scan.next();
		
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

				// se não for válido o ciclo repete
				System.out.println("Please insert a valid option ");
				System.out.print(" ==> ");
			}
			
		}
		
		System.out.println("=".repeat(100));
		System.out.println("\n".repeat(2));
		
		return response;
		
	}
	
	public void dealer(ArrayList<player> listofDealers) {
		
		for(player pl : listofDealers) {
			
			if(pl.dealer) {
				System.out.println("\n"+" ".repeat(30)+"Player "+pl.name+" is the dealer in this round!"+"\n");
			}

		}
	}
	
	public void playsOrder(ArrayList<player> listofPlayers) {

		System.out.println("-".repeat(43)+ " Playing Order "+"-".repeat(42)+"\n");
		
		for(player pl : listofPlayers) {
			
			System.out.println(" ".repeat(49)+pl.name);
			
		}
		
		System.out.println();
		
		System.out.println("=".repeat(100));
		System.out.println("\n".repeat(2));
		
	}

	public void printWinner(player winner) {
		
		System.out.println("=".repeat(45)+ " GAME OVER "+"=".repeat(44)+"\n \n");
		
		System.out.println(" ".repeat(20)+ "Congrats player "+winner.name+" you just won the Game!\n");
		
		System.out.println("=".repeat(100)+"\n");
		
	}

	public void exitGame() {
		
		System.out.println("=".repeat(45)+ " GAME OVER "+"=".repeat(44)+"\n \n");
		
		System.out.println(" ".repeat(15)+ "You just quit. True Losers are the ones that give up before they try.\n");
		
		System.out.println("=".repeat(100)+"\n");
		
	}


}
