package Domino;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class mainGame {

	public static void main(String[] args) throws InterruptedException {

		boolean running = true;

		String[] menu_options = { "1", "2", "3", "4" }; // lista com as opcoes possiveis para o menu inicial
		print print = new print();

		while (running) { // ciclo infinito até o jogador carregar no 4 ( Exit )

			print.mainMenu(); // faz print do Menu inicial

			String menu_answer = print.inputVerification(menu_options);

			if (menu_answer.equalsIgnoreCase("1")) {

				player winner = newGame(false); // começa um jogo regular

				if (winner == null) {
					// se está null é porque o jogador desistiu a meio do jogo
				} else {
					print.printWinner(winner);// faz print do vencedor do jogo
				}

			}

			else if (menu_answer.equalsIgnoreCase("2")) {

				player winner = newGame(true); // começa um jogo Extra

				if (winner.name.isBlank()) {

				} else {
					print.printWinner(winner);
				}

			}

			else if (menu_answer.equalsIgnoreCase("3")) {

				print.rules(); // mostra as regras do jogo
			}

			else { // encerra o jogo
				print.exitMessage();

				running = false;
			}
		}
	}

	public static ArrayList<dominoes> alldominoes() { // funçao que cria as 27 peças do domino e as coloca numa lista

		ArrayList<dominoes> alldominoess = new ArrayList<dominoes>();

		dominoes zero_zero = new dominoes("0", "0");

		dominoes zero_one = new dominoes("0", "1");

		dominoes one_one = new dominoes("1", "1");
		dominoes zero_two = new dominoes("0", "2");
		dominoes one_two = new dominoes("1", "2");
		dominoes two_two = new dominoes("2", "2");
		dominoes zero_three = new dominoes("0", "3");
		dominoes one_three = new dominoes("1", "3");
		dominoes two_three = new dominoes("2", "3");
		dominoes three_three = new dominoes("3", "3");
		dominoes zero_four = new dominoes("0", "4");
		dominoes one_four = new dominoes("1", "4");
		dominoes two_four = new dominoes("2", "4");
		dominoes three_four = new dominoes("3", "4");
		dominoes four_four = new dominoes("4", "4");
		dominoes zero_five = new dominoes("0", "5");
		dominoes one_five = new dominoes("1", "5");
		dominoes two_five = new dominoes("2", "5");
		dominoes three_five = new dominoes("3", "5");
		dominoes four_five = new dominoes("4", "5");
		dominoes five_five = new dominoes("5", "5");
		dominoes zero_six = new dominoes("0", "6");
		dominoes one_six = new dominoes("1", "6");
		dominoes two_six = new dominoes("2", "6");
		dominoes three_six = new dominoes("3", "6");
		dominoes four_six = new dominoes("4", "6");
		dominoes five_six = new dominoes("5", "6");
		dominoes six_six = new dominoes("6", "6");

		alldominoess.add(zero_zero);
		alldominoess.add(zero_one);
		alldominoess.add(one_one);
		alldominoess.add(zero_two);
		alldominoess.add(one_two);
		alldominoess.add(two_two);
		alldominoess.add(zero_three);
		alldominoess.add(one_three);
		alldominoess.add(two_three);
		alldominoess.add(three_three);
		alldominoess.add(zero_four);
		alldominoess.add(one_four);
		alldominoess.add(two_four);
		alldominoess.add(three_four);
		alldominoess.add(four_four);
		alldominoess.add(zero_five);
		alldominoess.add(one_five);
		alldominoess.add(two_five);
		alldominoess.add(three_five);
		alldominoess.add(four_five);
		alldominoess.add(five_five);
		alldominoess.add(zero_six);
		alldominoess.add(one_six);
		alldominoess.add(two_six);
		alldominoess.add(three_six);
		alldominoess.add(four_six);
		alldominoess.add(five_six);
		alldominoess.add(six_six);

		return alldominoess;

	}

	public static player newGame(boolean extra) throws InterruptedException { // funcao que inicia um jogo, cria os
																				// objectos necessário para se jogar

		player winner;
		Scanner sc = new Scanner(System.in);
		board board = new board();

		ArrayList<player> listofPlayers = new ArrayList<player>();

		System.out.println(" ".repeat(5) + "What's your name?\n \n");
		System.out.print(" ==> ");

		// creating the list of players
		String name = sc.nextLine();
		player human = new player(name, true);
		player cp1 = new player("CP1", false);
		player cp2 = new player("CP2", false);
		player cp3 = new player("CP3", false);

		listofPlayers.add(human);
		listofPlayers.add(cp1);
		listofPlayers.add(cp2);
		listofPlayers.add(cp3);

		// chama a função para o jogo

		if (extra) {
			winner = gameExtra(listofPlayers, 0, board);
		} else {
			winner = game(listofPlayers, 0, board);
		}

		// quando o jogo acabar devolve o vencedor
		return winner;
	}

	public static player game(ArrayList<player> listofPlayers, int set, board board) throws InterruptedException {

		print print = new print();
		print.set(set); // Faz print do número de set em que se vai (game>set>round)
		boolean endgame = false; // booleano que passa a true quando o jogo acaba
		int game_points = 100;

		ArrayList<player> listofDealers = dealPieces(setDealer(listofPlayers)); // ajusta a lista de dealers e destribui
																				// as peças pelos jogadores.

		listofPlayers = set(listofPlayers, listofDealers, 0, board); // joga 1 set inteiro

		print.printSetPoints(listofPlayers); // faz print dos pontos de cada jogador naquele set

		for (player pl : listofPlayers) { // ciclo para adicionar os pontos do set aos pontos do jogo de cada jogador

			pl.gamePoints = pl.gamePoints + pl.setPoints;

			pl.setPoints = 0; // faz reset para preparar o inicio do novo set

			if (pl.gamePoints >= 50) { // se algum jogador tiver mais de 50pt, o jogo acaba, então a variavel endgame
										// para a true
				endgame = true;

			}

		}

		print.printGamePoints(listofPlayers); // mostra o total de pontos de cada jogador

		String answer = print.printExitOption(); // opção para sair do jogo. O jogador só consegue desistir no final de
													// cada set

		if (answer.equalsIgnoreCase("Y")) {

			print.exitGame(); // jogador desistiu do jogo

		}

		else {

			if (!endgame) { // se o jogo ainda não acabou

				board = new board(); // reset no tabuleiro
				game(listofPlayers, set + 1, board); // chama-se outra vez a função game (isto entra num ciclo de chamar
														// esta função até engame==true)

			}

			else {// se o jogo acabou

				for (int i = 0; i < 4; i++) { // ciclo que vê qual dos jogadores tem MENOS pontos (este será o vencedor
												// do jogo)

					if (listofPlayers.get(i).gamePoints <= game_points) { // se o jogador actual tiver menos pontos que
																			// uma variavel (inicialmente de valor 100,
																			// e dps toma os pontos do jogador se este
																			// tiver menos pontos que a mesma)

						for (player p1 : listofPlayers) { // faz reset do gameWinner anterior

							p1.gameWinner = false;

						}

						game_points = listofPlayers.get(i).gamePoints; // atribui o numero de pontos a variavel de
																		// comparação e depois assume que este jogador
																		// ganhou (se não tiver ganho, a linha 242 do
																		// código acaba por meter false)

						listofPlayers.get(i).gameWinner = true;

					}

				}

			}

			for (player pl1 : listofPlayers) {

				if (pl1.gameWinner) { // retorna o vencedor
					return pl1;
				}

			}

		}

		return null; // return para o caso em que o jogador desiste

	}

	public static ArrayList<player> set(ArrayList<player> listofPlayers, ArrayList<player> listofDealers, int round,
			board board) throws InterruptedException {

		print print = new print();
		print.round(round); // faz print da ronda actual
		boolean endset = false;

		int counter = 0, tie = 100;

		if (round == 0) { // se for a primeira ronda é preciso definir a ordem de jogada

			print.dealer(listofDealers); // faz print da lista de dealers (nota que esta só é gerada no inicio do jogo e
											// ordem mantem/se igual)

			listofPlayers = setPlayers(listofPlayers); // define a ordem de jogada

			print.playsOrder(listofPlayers);

		}

		for (player pl : listofPlayers) { // lista que percorre todos os jogadores e estes vão jogando

			Object board_tie = board.dominoesInBoard.clone(); // objecto clonado do tabuleiro (igual)

			board = pl.play(board); // novo tabuleiro (depois do jogador ter jogado a sua peça)

			if (board.dominoesInBoard.equals(board_tie)) { // se o novo tabuleiro for igual ao board_tie, significa que
															// o pl passou a sua vez, e o counter incrementa por 1;

				counter = counter + 1;

			} else { // faz reset ao counter se alguém jogar uma pedra
				counter = 0;
			}

			if (pl.hand.isEmpty()) { // se o jogador depois de jogar ficar com a mão vazia significa que ganhou e
										// sendo assim o set acabou, endset=true

				endset = true;

				for (player pl1 : listofPlayers) { // iguala o numero de pontos na mão de cada jogador ao numero final
													// do set do jogador

					pl1.winner = false;
					// pl1.setPoints=pl1.setPoints+pl1.totalSum()
					pl1.setPoints = pl1.totalSum();

				}

				pl.winner = true; // coloca este jogador como vencedor do set

				break;// faz break para garantir que mais nenhum jogador jogar depois de alguém ganhar
						// (break do ciclo da linha 291)

			}

		}

		if (counter == 4) { // se counter chegar a 4, significa que os jogadores passaram todos, logo o jogo
							// empata e acaba
			endset = true;

			for (int i = 0; i < 4; i++) { // adiciona os pontos dos jogadores e ve o vencedor da ronda

				// listofPlayers.get(i).setPoints=listofPlayers.get(i).setPoints+listofPlayers.get(i).totalSum();
				listofPlayers.get(i).setPoints = listofPlayers.get(i).totalSum();

				if (listofPlayers.get(i).totalSum() < tie) {

					if (i != 0) {
						listofPlayers.get(i - 1).winner = false;
					}

					tie = listofPlayers.get(i).totalSum();

					listofPlayers.get(i).winner = true;

				}

			}

		}

		if (!endset) { // se o set ainda não acabou faz mais 1 ronda com o novo tabuleiro...
			set(listofPlayers, listofDealers, round + 1, board);
		}

		return listofPlayers; // quando acabar o set dá return da lista de jogadores

	}

	@SuppressWarnings("unchecked")
	public static ArrayList<player> dealPieces(ArrayList<player> listofPlayers) { // função que destribui as peças

		Random rand = new Random();
		ArrayList<dominoes> list = alldominoes();
		ArrayList<dominoes> newlist = new ArrayList<dominoes>();

		for (int i = 0; i < 4; i++) { // ciclo que percorre todos os jogadores

			for (int j = 0; j < 7; j++) {

				int randomIndex = rand.nextInt(list.size()); // apanha uma peça aleatória

				newlist.add(list.get(randomIndex)); // adiciona a mão do jogador

				list.remove(randomIndex); // e remove da lista onde estão todas as peças

			}
			listofPlayers.get(i).hand = (ArrayList<dominoes>) newlist.clone();
			newlist = new ArrayList<dominoes>();
		}

		return listofPlayers;

	}

	public static ArrayList<player> setDealer(ArrayList<player> listofPlayers) { // função que define os dealers

		Random rand = new Random();

		if (listofPlayers.get(0).dealer) { // se o primeiro elemento for um dealer (significa que esta função já foi
											// chamada), assume como o 2 elemento o dealer, remove o primeiro e
											// adidiona-o no fim da lista

			listofPlayers.get(0).dealer = false;
			listofPlayers.get(1).dealer = true;

			listofPlayers.add(listofPlayers.get(0));
			listofPlayers.remove(listofPlayers.get(0));

		}

		else { // se o primeiro elemento não for dealer, significa que este ainda não foi
				// atribuido
			int rnd = rand.nextInt(4);
			listofPlayers.get(rnd).dealer = true; // escolhe um dealer aleatóriamente

			while (!listofPlayers.get(0).dealer) { // enquanto o 1 elemento não for dealer, iremos remover o 1 elemento
													// e colocar no fim da lista

				listofPlayers.add(listofPlayers.get(0));
				listofPlayers.remove(listofPlayers.get(0));

			}

		}

		return listofPlayers; // faz return da lista com um novo dealer na posição 0

	}

	public static ArrayList<player> setPlayers(ArrayList<player> listofPlayers) { // define a ordem dos jogadores

		Random rand = new Random();

		boolean firstRound = true;

		for (int i = 0; i < 4; i++) { // se ainda não existir um vencedor é porque é a primeira ronda

			if (listofPlayers.get(i).winner) {

				firstRound = false;
				break;
			}

		}

		if (firstRound) {
			int rnd = rand.nextInt(4); // se for a primeira ronda um jogador aleatório é considerado "vencedor"
			listofPlayers.get(rnd).winner = true;
		}

		while (!listofPlayers.get(0).winner) { // como o vencedor tem de ser o primeiro a jogar, retiramos o primeiro
												// elemento da lista e passamos para ultimo até que o elemento 0 seja o
												// vencedor

			listofPlayers.add(listofPlayers.get(0));
			listofPlayers.remove(listofPlayers.get(0));

		}

		return listofPlayers;

	}

	// Funções para o Extra... Funcionam como as outras, mas têm pequenas diferenças
	// no que toca a pontuação e prints, NÃO SÃO ELEMENTOS DE AVALIAÇÃO PODEM SER
	// IGNORADAS

	public static player gameExtra(ArrayList<player> listofPlayers, int set, board board) throws InterruptedException {

		print print = new print();
		print.set(set);
		boolean endgame = false;
		int game_points = 51;

		ArrayList<player> listofDealers = dealPieces(setDealer(listofPlayers));

		listofPlayers = setExtra(listofPlayers, listofDealers, 0, board);

		print.printSetPoints(listofPlayers);

		for (player pl : listofPlayers) {

			pl.gamePoints = pl.gamePoints + pl.setPoints;

			pl.setPoints = 0;

			if (pl.gamePoints >= 51) {
				endgame = true;

			}

		}

		print.printGamePoints(listofPlayers);

		String answer = print.printExitOption();

		if (answer.equalsIgnoreCase("Y")) {

			print.exitGame();

		}

		else {

			if (!endgame) {

				board = new board();
				gameExtra(listofPlayers, set + 1, board);

			}

			else {

				for (int i = 0; i < 4; i++) {

					if (listofPlayers.get(i).gamePoints > game_points) {

						listofPlayers.get(i).gameWinner = true;

					}

				}

			}

			for (player pl1 : listofPlayers) {

				if (pl1.gameWinner) {
					return pl1;
				}

			}

		}

		return null;

	}

	public static ArrayList<player> setExtra(ArrayList<player> listofPlayers, ArrayList<player> listofDealers,
			int round, board board) throws InterruptedException {

		print print = new print();
		print.round(round);
		boolean endset = false;

		int counter = 0, tie = 100;

		if (round == 0) {

			print.dealer(listofDealers);

			listofPlayers = setPlayers(listofPlayers);

			print.playsOrder(listofPlayers);

		}

		for (player pl : listofPlayers) {

			Object board_tie = board.dominoesInBoard.clone();

			board = pl.playExtra(board);

			if (board.dominoesInBoard.equals(board_tie)) {

				counter = counter + 1;

			}

			if (pl.hand.isEmpty()) {

				endset = true;

				for (player pl1 : listofPlayers) {

					pl1.winner = false;

					if (!pl1.equals(pl)) {
						pl.setPoints = pl.setPoints + 5 * (Math.round(pl1.totalSum() / 5));
					}

				}

				pl.winner = true;
				break;

			}

		}

		if (counter == 4) {

			endset = true;

			for (int i = 0; i < 4; i++) {

				if (listofPlayers.get(i).totalSum() < tie) {

					for (player p1 : listofPlayers) { // faz reset do gameWinner anterior

						p1.gameWinner = false;

					}

					tie = listofPlayers.get(i).totalSum();

					listofPlayers.get(i).winner = true;

				}

			}

			for (player pl2 : listofPlayers) {

				if (pl2.winner) {

					for (player pl3 : listofPlayers) {

						if (!pl3.equals(pl2)) {
							pl2.setPoints = pl2.setPoints + 5 * (Math.round(pl3.totalSum() / 5));
						}
					}
				}

			}

		}

		if (!endset) {
			setExtra(listofPlayers, listofDealers, round + 1, board);
		}

		return listofPlayers;

	}

}
