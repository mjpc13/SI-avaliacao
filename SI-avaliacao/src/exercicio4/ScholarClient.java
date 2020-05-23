package exercicio4;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ScholarClient {
	public static void main(String[] args) {
		try {
			// Returns a reference to the remote object Registry on the specified host and
			// port.
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);

			// 'lookup' returns the remote reference bound to the specified name in this
			// registry.
			ScholarInterface sch = (ScholarInterface) registry.lookup("Scholar");

			// let's execute our remote operation and keep the return value in 'result'!

			Scanner scan = new Scanner(System.in);

			String[] options_menu1 = { "1", "2", "3" }; // cria a lista de opções válidas para o primeiro menu
			String[] options_menu2 = { "1", "2", "3", "4", "5", "6", "7" };// cria a lista de opções válidas para o
																			// segundo menu

			// inicializa variáveis
			int fails = 0;
			User myself;
			boolean connected = true, logged = false;
			String email = "";

			while (connected) { // loop do primeiro menu

				menu1Print(); // faz o print do menu 1

				String menu1 = inputVerification(options_menu1, scan); // recebe uma String do User, e só aceita se for
																		// uma opção válida

				if (menu1.equals("1")) { // se o user optar por fazer Register

					email = registerMenu(sch, scan); // Cria um novo User, dá Return null, se já existir um User com
														// aquele mail

					if (email == null) {
						logged = false; // como já existe um novo user, não o deixa fazer login e volta para print do
										// menu1
					} else {

						logged = true; // foi criado um novo user, e automaticamente faz log in do mesmo
					}
				}

				else if (menu1.equals("2")) { // se o user optar por fazer o Log In

					email = loginMenu(sch, scan); // verifica se o User inseriu correctamente as credenciais; dá return
													// de null, se as credenciais forem inválidas

					if (email == null) {
						logged = false; // não o deixa fazer login e volta para print do menu1
					} else {

						logged = true; // autoriza o log IN
					}

				}

				else { // O cliente optou por encerrar o programa e a conexão

					while (true) { // ciclo para fazer o handling de falha de conexão com o Servidor

						try { // tenta usar o método addNewPublication(...) que está no obj sch

							sch.writeToFiles(); // guarda as variáveis do objecto fm nos ficheiros préviamente criados

							break;

						} catch (IOException e) {

							System.out.println("> Connection lost, trying again in 3 seconds");
							fails++;
							Thread.sleep(3000);

							try {
								registry = LocateRegistry.getRegistry("localhost", 1099);
								sch = (ScholarInterface) registry.lookup("Scholar");

							} catch (Exception d) {
								System.out.println("> Failed to establish connection");
							}

						} catch (Exception idk) {
							System.out.print("> An error has occurred: ");
							idk.printStackTrace();
						}

						if (fails == 10) {
							System.out.println("> Program terminated. Cause: Lost Connection with Server");
							System.exit(0);
						}

					}

					connected = false; // vai encerrar o programa

				}

				while (logged) { // ciclo correspondente ao menu do cliente/ user (menu 2)

					while (true) { // ciclo para fazer o handling de falha de conexão com o Servidor

						try { // tenta obter as informações do User

							myself = sch.getUserData(email);
							fails = 0;
							break; // se for bem sucedido sai do while()

						} catch (IOException e) { // se houver uma falha na conexão trata dessa falha

							System.out.println("> Connection lost, trying again in 3 seconds");
							fails++; // incrementa em 1 as tentativas de conexão
							Thread.sleep(3000); // espera 3s para tentar outra vez

							try {

								sch = (ScholarInterface) registry.lookup("Scholar"); // tenta procurar novamente o
																						// registry
								sch.writeToFiles(); // tenta gravar os ficheiros (é uma maneira de tentar salvaguardar
													// usuários que tenham problemas de conexão sem alterar a
													// performance do programa)

							} catch (Exception d) { // caso não encontre o Registry
								System.out.println("> Failed to establish connection");
							}

						} catch (Exception idk) { // apanha outros possiveis erros
							System.out.print("> An error has occurred: ");
							idk.printStackTrace();
						}

						if (fails == 10) { // quando tenta conectar-se 10x sem sucesso, desliga o programa
							System.out.println("> Program terminated. Cause: Lost Connection with Server");
							System.exit(0);
						}

					}

					menu2Print(); // faz o print do menu 2

					String menu2 = inputVerification(options_menu2, scan); // recebe uma String do User, e só aceita se
																			// for
																			// uma opção válida

					System.out.println("=".repeat(55));// separador usado por motivos estéticos

					if (menu2.equals("1")) { // faz o print das pubs do User por ano Decrescente
						myself.printPublications(true);

					}

					else if (menu2.equals("2")) { // faz o print das pubs do User por citações Decrescente
						myself.printPublications(false);
					}

					else if (menu2.equals("3")) { // adiciona uma nova publicação (na lista geral e na lista do user)

						addPub(sch, scan, myself);

					}

					else if (menu2.equals("4")) { // look for author publications in the pub list

						lookForPubs(sch, scan, myself);

					}

					else if (menu2.equals("5")) { // remove uma nova publicação (na lista geral e na lista do user)

						removePublications(sch, scan, myself);

					}

					else if (menu2.equals("6")) { // mostra os stats do User
						myself.showStats();
					}

					else if (menu2.equals("7")) { // Termina a sessão deste user

						while (true) {

							try {

								sch.writeToFiles();
								break;
							} catch (IOException e) {

								System.out.println("> Connection lost, trying again in 3 seconds");
								fails++;
								Thread.sleep(3000);

								try {
									registry = LocateRegistry.getRegistry("localhost", 1099);
									sch = (ScholarInterface) registry.lookup("Scholar");

								} catch (Exception d) {
									System.out.println("> Failed to establish connection");
								}

							} catch (Exception idk) {
								System.out.print("> An error has occurred: ");
								idk.printStackTrace();
							}

							if (fails == 10) {
								System.out.println("> Program terminated. Cause: Lost Connection with Server");
								System.exit(0);
							}

						}
						logged = false;
					}

				}

			}

		} catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{ // usually it is advisable to use multiple catch blocks and perform different
			// error handling actions
			// depending on the specific exception type caught
			System.err.println("Ocorreu um erro: ");
			e.printStackTrace(); // prints detailed information about the exception
		}
	}

	public static String inputVerification(String[] valid_options, Scanner scan) {

		// valid_options é uma lista de Strings que contêm todos os inputs aceites

		String response = "";

		boolean valid = false;

		while (!valid) { // ciclo que só para quando o utilizador inserir um input válido

			response = scan.nextLine(); // faz o scan da resposta do utilizador (assume que é String)

			for (String option : valid_options) {

				if (response.equalsIgnoreCase(option)) {

					valid = true; // Se a resposta estiver dentro da lista cedida,
									// assume que é válido e sai do ciclo.
				}
			}

			if (!valid) {

				// se não for válido o ciclo repete
				System.out.println("Please insert a valid option ");
				System.out.print(" ==> ");
			}

		}

		return response;

	}

	public static String emailInput(Scanner scan) {

		System.out.println("Email: ");
		System.out.print("==> ");
		String email = scan.nextLine();

		// para ser um endereço válido tem de conter um "@" e um "."
		while ((email.contains("@") && email.contains(".")) == false) { // enquanto não for válido, o email não é aceite

			System.out.println("The email format is not valid. Please try again (check for spelling).\n");
			System.out.print("==> ");
			email = scan.nextLine();

		}
		return email; // faz return do email
	}

	public static String loginMenu(ScholarInterface sch, Scanner scan) throws Exception {

		int fails = 0;
		System.out.println("=".repeat(20) + " Login: " + "=".repeat(20));

		String email = emailInput(scan); // verifica que o email introduzido está num formato válido

		System.out.println("Password: ");
		System.out.print("==> ");
		String password = scan.nextLine();

		while (true) {

			try {

				if (!sch.loginVerification(email, password)) { // verifica se as credenciais inseridas são válidas.
																// return falso se não forem válidas

					System.out.println("> Invalid user. If you are a new user please register first.");
					return null; // return null se as credenciais forem invalidas
				} else {
					System.out.println("> Login Successful.");
					return email; // faz return do email se as credenciais estiverem corretas

				}
			} catch (IOException e) {

				System.out.println("> Connection lost, trying again in 3 seconds");
				fails++;
				Thread.sleep(3000);

				try {
					Registry registry = LocateRegistry.getRegistry("localhost", 1099);
					sch = (ScholarInterface) registry.lookup("Scholar");
					sch.writeToFiles(); // tenta gravar os ficheiros (é uma maneira de tentar salvaguardar usuários que
										// tenham problemas de conexão sem alterar a performance do programa)

				} catch (Exception d) {
					System.out.println("> Failed to establish connection");
				}

			} catch (Exception idk) {
				System.out.print("> An error has occurred: ");
				idk.printStackTrace();
			}

			if (fails == 10) {
				System.out.println("> Program terminated. Cause: Lost Connection with Server");
				System.exit(0);
			}

		}

	}

	public static String registerMenu(ScholarInterface sch, Scanner scan) throws Exception {
		int fails = 0;
		System.out.println("=".repeat(20) + "Register:" + "=".repeat(20));

		System.out.println("Name: (ex: Pires, Michel)");
		System.out.print("==> ");
		String name = scan.nextLine();

		String email = emailInput(scan);

		System.out.println("Password: ");
		System.out.print("==> ");
		String password = scan.nextLine();
		// scan.nextLine();

		System.out.println("Afiliações: ");
		System.out.print("==> ");
		String afi = scan.nextLine();

		while (true) {

			try { // tenta adicionar um novo utilizador

				if (sch.addNewUser(name, email, password, afi)) { // se o utilizador não existir devolve verdadeiro e
																	// cria 1 novo

					System.out.println("> User Successfully Registered!");
					return email; // dá return do mail do novo user

				}

				else { // o User já existe (Já existe um User com o mesmo email)

					System.out.println("> This user already exists.");
					return null; // faz return de null

				}

			} catch (IOException e) {

				System.out.println("> Connection lost, trying again in 3 seconds");
				fails++;
				Thread.sleep(3000);

				try {
					Registry registry = LocateRegistry.getRegistry("localhost", 1099);
					sch = (ScholarInterface) registry.lookup("Scholar");
					sch.writeToFiles(); // tenta gravar os ficheiros (é uma maneira de tentar salvaguardar usuários que
										// tenham problemas de conexão sem alterar a performance do programa)

				} catch (Exception d) {
					System.out.println("> Failed to establish connection");
				}

			} catch (Exception idk) {
				System.out.print("> An error has occurred: ");
				idk.printStackTrace();
			}

			if (fails == 10) {
				System.out.println("> Program terminated. Cause: Lost Connection with Server");
				System.exit(0);
			}

		}

	}

	public static void menu1Print() {
		System.out.println("\n");
		System.out.println("=".repeat(10) + "Scholar System" + "=".repeat(10));
		System.out.println("1 - Register new Author \n2 - Login \n3 - Exit\n");
		System.out.print("==> ");
	}

	public static void menu2Print() {

		System.out.println("\n");
		System.out.println("=".repeat(55));
		System.out.println("1 - List Publications by year (newest first)");
		System.out.println("2 - List Publications by year (Most cited first)");
		System.out.println("3 - Add Publication");
		System.out.println("4 - Look for author publications in database");
		System.out.println("5 - Remove publication");
		System.out.println("6 - Show author statistics");
		System.out.println("7 - Logout");
		System.out.print("==> ");

	}

	public static int validateInt(Scanner scan) { // vê se é um número positivo

		int number;

		do { // enquanto o number não for maior ou igual a 0, repete este ciclo

			while (!scan.hasNextInt()) { // enquanto o User não inserir um caracter que posso ser convertido em Inteiro
											// repete este ciclo
				System.out.println("> Damn it! That's not a number, go back to school...");
				scan.next();
			}
			number = scan.nextInt();
		} while (number <= 0);

		return number; // return do número

	}

	public static void addPub(ScholarInterface sch, Scanner scan, User myself) throws Exception {

		int fails = 0;
		System.out.println("Authors: (ex: lastName1, FirstName1;lastName2, FirstName2)");
		System.out.print("==> ");

		String string_autores = scan.nextLine(); // String que contem os autores da publicação

		ArrayList<String> autores = new ArrayList<String>(Arrays.asList(string_autores.split(";"))); // Cria um novo
																										// ArrayList
																										// que divide a
																										// string com os
																										// autores pelo
																										// caracter ";"
																										// para que
																										// tenha 1 nome
																										// autor por
																										// indice

		for (int i = 0; i < autores.size(); i++) { // se o utilizador escrever os nomes a começar por um
													// espaço, elimina
													// esse espaço
			if (autores.get(i).substring(0, 1).equals(" ")) { // se o primeiro elemento da substring for um espaço

				autores.set(i, autores.get(i).substring(1)); // eliminamos esse espaço
			}
		}

		System.out.println("Title: ");
		System.out.print("==> ");
		String titulo = scan.nextLine();

		System.out.println("Year: ");
		System.out.print("==> ");
		int ano = validateInt(scan);
		scan.nextLine();

		System.out.println("Magazine: ");
		System.out.print("==> ");
		String revista = scan.nextLine();

		System.out.println("Volume: ");
		System.out.print("==> ");
		String volume = scan.nextLine();

		System.out.println("Number: ");
		System.out.print("==> ");
		int numero = validateInt(scan);
		scan.nextLine();

		System.out.println("Page: (Ex: [pg inicial] - [pg final])");
		System.out.print("==> ");
		String pagina = scan.nextLine();

		System.out.println("Citations: ");
		System.out.print("==> ");
		int citacoes = validateInt(scan);
		scan.nextLine();

		while (true) { // ciclo para fazer o handling de falha de conexão com o Servidor

			try { // tenta usar o método addNewPublication(...) que está no obj sch

				sch.addNewPublication(autores, titulo, ano, revista, volume, numero, pagina, citacoes, myself);
				break;

			} catch (IOException e) {

				System.out.println("> Connection lost, trying again in 3 seconds");
				fails++;
				Thread.sleep(3000);

				try {
					Registry registry = LocateRegistry.getRegistry("localhost", 1099);
					sch = (ScholarInterface) registry.lookup("Scholar");
					sch.writeToFiles(); // tenta gravar os ficheiros (é uma maneira de tentar salvaguardar usuários que
										// tenham problemas de conexão sem alterar a performance do programa)

				} catch (Exception d) {
					System.out.println("> Failed to establish connection");
				}

			} catch (Exception idk) {
				System.out.print("> An error has occurred: ");
				idk.printStackTrace();
			}

			if (fails == 10) {
				System.out.println("> Program terminated. Cause: Lost Connection with Server");
				System.exit(0);
			}

		}

	}

	public static void removePublications(ScholarInterface sch, Scanner scan, User myself) throws Exception {

		String str = "";
		int fails = 0;
		myself.printPublications(true); // faz print das publicações do autor (que são tmb as que podem ser removidas)

		if (myself.getListPubs() == null || myself.getListPubs().size() == 0) {

		}

		else {

			ArrayList<Integer> itemsToRemove = new ArrayList<>(); // Criar o array para futuramente guardar os indices a
																	// remover

			System.out.println("Choose what publications you want to remove: (ex: 1,3,4)");
			System.out.print("==> ");
			String resposta = scan.nextLine();

			String[] stripedAnswers = resposta.split(","); // cria um arraylist que a cada indice tem a posição da pub a
															// remover (neste caso faz split por ",")

			for (int i = 0; i < stripedAnswers.length; i++) {

				if (stripedAnswers[i].substring(0, 1).equals(" ")) { // se o caracter começar com um espaço remove esse
																		// espaço
					str = stripedAnswers[i].substring(1);
				} else {
					str = stripedAnswers[i];
				}

				if (isNumber(str)) { // verifica que de facto é um número inteiro

					if (Integer.parseInt(str) >= 0 && Integer.parseInt(str) <= stripedAnswers.length) { // vê se o
																										// número está
																										// dentro dos
																										// limites
																										// possiveis

						itemsToRemove.add(Integer.parseInt(str)); // adiciona o indice na lista que contêm todos os
																	// indices a remover

					}

				}

			}

			Collections.sort(itemsToRemove); // temos de ordenar a lista de modo a que o indice mais alto seja o
												// primeiro a ser removido
			Collections.reverse(itemsToRemove); // pois caso contrário os indices da lista vão alterar e acabamos por
												// remover items que não eram desejados

			while (true) {

				try {

					sch.removePub(myself, itemsToRemove); // chama o método para eliminar as pubs desejadas

					break;

				} catch (IOException e) {

					System.out.println("> Connection lost, I'll be back...");
					fails++;
					Thread.sleep(3000);

					try {
						Registry registry = LocateRegistry.getRegistry("localhost", 1099);
						sch = (ScholarInterface) registry.lookup("Scholar");
						sch.writeToFiles(); // tenta gravar os ficheiros (é uma maneira de tentar salvaguardar usuários
											// que tenham problemas de conexão sem alterar a performance do programa)

					} catch (Exception d) {
						System.out.println("> Failed to establish connection");
					}

				} catch (Exception idk) {
					System.out.print("> An error has occurred: ");
					idk.printStackTrace();
				}

				if (fails == 10) {
					System.out.println("> Program terminated. Cause: Lost Connection with Server");
					System.exit(0);
				}

			}

		}

	}

	private static boolean isNumber(String str) {

		try {
			if (str == null) { // verifica se é null
				return false;
			}

			int n = Integer.parseInt(str); // se não for convertivel para inteiro vai dar um erro de
											// NumberFormatException que é posteriormente tratado no catch block
			return true;

		} catch (NumberFormatException e) {
			System.out.println("> Error:" + str + "cannot be converted to a number.");
			return false;
		} catch (Exception idk) {
			System.out.print("> An error has occurred: ");
			idk.printStackTrace();
			return false;
		}

	}

	public static void lookForPubs(ScholarInterface sch, Scanner scan, User myself) throws Exception {

		// declaração de variáveis
		int fails = 0;
		ArrayList<Publication> listOfPublications;
		ArrayList<Integer> options = new ArrayList<>();
		ArrayList<String> autores = new ArrayList<>();
		int doi, value;
		String str = "";

		while (true) {

			try {

				listOfPublications = sch.getPublications(); // saca todas as publicações na "base de dados" aka ficheiro

				break;

			} catch (IOException e) {

				System.out.println("> Connection lost, I'll be back...");
				fails++;
				Thread.sleep(3000);

				try {
					Registry registry = LocateRegistry.getRegistry("localhost", 1099);
					sch = (ScholarInterface) registry.lookup("Scholar");
					sch.writeToFiles(); // tenta gravar os ficheiros (é uma maneira de tentar salvaguardar usuários que
										// tenham problemas de conexão sem alterar a performance do programa)

				} catch (Exception d) {
					System.out.println("> Failed to establish connection");
				}

			} catch (Exception idk) {
				System.out.print("> An error has occurred: ");
				idk.printStackTrace();
			}

			if (fails == 10) {
				System.out.println("> Program terminated. Cause: Lost Connection with Server");
				System.exit(0);
			}

		}

		for (int i = 0; i < listOfPublications.size(); i++) {

			autores = listOfPublications.get(i).getListaAutores(); // fica com os autores da presente publicação
			doi = listOfPublications.get(i).getDOI(); // fica com os DOIs da presente publicação

			if (autores.contains(myself.getNome()) && !myself.getListDois().contains(doi)) { // se o Autor tiver o seu
																								// nome na lista de pubs
																								// E não tem o DOI da
																								// pub na sua lista de
																								// DOIs significa que
																								// esta é uma candidacta

				System.out.println(i + ") " + listOfPublications.get(i)); // faz print da publicação
				options.add(i); // adiciona o indice à lista de opções

			}

		}

		if (options.size() == 0) {
			System.out.println("\n> There is no candidates");
		}

		else {
			System.out.println("Choose what publications you want to add: (ex: 1,3,4)");
			System.out.print("==> ");
			String resposta = scan.nextLine();

			String[] stripedAnswers = resposta.split(","); // cria um arraylist que a cada indice tem a posição da pub a
															// remover (neste caso faz split por ",")

			for (int i = 0; i < stripedAnswers.length; i++) {

				if (stripedAnswers[i].substring(0, 1).equals(" ")) { // se o caracter começar com um espaço remove esse
																		// espaço
					str = stripedAnswers[i].substring(1);
				} else {
					str = stripedAnswers[i];
				}

				if (isNumber(str)) { // verifica que a string pode ser convertida num número

					value = Integer.parseInt(str); // é o valor inteiro da string str

					if (options.contains(value)) { // verifica se o número está nas opções (ou seja vê se a opção
													// inserida é
													// de uma publicação válida)

						myself.addPublication(listOfPublications.get(value)); // adiciona esta nova pub

					}

				}

			}

			while (true) {

				try {

					sch.saveInformation(sch, myself);

					break;

				} catch (IOException e) {

					System.out.println("> Connection lost, I'll be back...");
					fails++;
					Thread.sleep(3000);

					try {
						Registry registry = LocateRegistry.getRegistry("localhost", 1099);
						sch = (ScholarInterface) registry.lookup("Scholar");
						sch.writeToFiles(); // tenta gravar os ficheiros (é uma maneira de tentar salvaguardar usuários
											// que
											// tenham problemas de conexão sem alterar a performance do programa)

					} catch (Exception d) {
						System.out.println("> Failed to establish connection");
					}

				} catch (Exception idk) {
					System.out.print("> An error has occurred: ");
					idk.printStackTrace();
				}

				if (fails == 10) {
					System.out.println("> Program terminated. Cause: Lost Connection with Server");
					System.exit(0);
				}

			}

		}

	}

}