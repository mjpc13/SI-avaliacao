package exercicio4;

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

			boolean connected = true, logged = false;
			String[] options_menu1 = { "1", "2", "3" };
			String[] options_menu2 = { "1", "2", "3", "4", "5", "6", "7" };
			int fails = 0;
			User myself;

			while (connected) {

				menu1Print();
				String menu1 = inputVerification(options_menu1, scan);
				String email = "";

				if (menu1.equals("1")) {

					email = registerMenu(sch, scan);

					if (email == null) {
						logged = false;
					} else {

						logged = true;
					}
				}

				else if (menu1.equals("2")) {

					email = loginMenu(sch, scan);

					if (email == null) {
						logged = false;
					} else {

						logged = true;
					}

				}

				else {
					sch.writeToFiles();
					connected = false;
				}

				while (logged) {

					while (true) {

						try {

							myself = sch.getUserData(email);
							break;

						} catch (Exception e) {
							System.out.println("> Connection with lost, please try again in 3 seconds.");
							fails++;
							Thread.sleep(3000);
						}

						if (fails == 10) {
							System.out.println("> Program terminated. Cause: Lost Connection with Server");
							System.exit(0);
						}

					}

					menu2Print();
					String menu2 = inputVerification(options_menu2, scan);
					System.out.println("=".repeat(55));

					if (menu2.equals("1")) {
						myself.printPublications(true);
					}

					else if (menu2.equals("2")) {
						myself.printPublications(false);
					}

					else if (menu2.equals("3")) {

						addPub(sch, scan, myself);

					}

					else if (menu2.equals("4")) {

						lookForPubs(sch, scan, myself);// look for author publications in the pub list

					}

					else if (menu2.equals("5")) {
						removePublications(sch, scan, myself);
					}

					else if (menu2.equals("6")) {
						myself.showStats();
					}

					else if (menu2.equals("7")) {
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

		while ((email.contains("@") && email.contains(".")) == false) {
			System.out.println("The email format is not valid. Please try again (check for spelling).\n");
			System.out.print("==> ");
			email = scan.nextLine();
			// scan.next();
		}
		return email;
	}

	public static String loginMenu(ScholarInterface sch, Scanner scan) throws Exception {
		int fails = 0;
		System.out.println("=".repeat(20) + " Login: " + "=".repeat(20));

		System.out.println("> Program terminated. Cause: Lost Connection with Server");

		String email = emailInput(scan);

		System.out.println("Password: ");
		System.out.print("==> ");
		String password = scan.nextLine();
		// scan.next();

		while (true) {

			try {
				if (!sch.loginVerification(email, password)) {
					System.out.println("> Invalid user. If you are a new user please register first.");
					return null;
				} else {
					System.out.println("> Login Successful.");
					return email;
				}
			} catch (Exception e) {
				System.out.println("> Connection with lost, please try again in 3 seconds.");
				fails++;
				Thread.sleep(3000);
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

		System.out.println("Name: ");
		System.out.print("==> ");
		String name = scan.nextLine();
		// scan.next();

		String email = emailInput(scan);

		System.out.println("Password: ");
		System.out.print("==> ");
		String password = scan.nextLine();
		// scan.next();

		System.out.println("Afiliações: ");
		System.out.print("==> ");
		scan.next();
		String afi = scan.nextLine();
		// scan.next();

		while (true) {

			try {
				if (sch.addNewUser(name, email, password, afi)) {
					System.out.println("> User Successfully Registered!");
					return email;
				} else {
					System.out.println("> This user already exists.");
					return null;
				}
			} catch (Exception e) {
				System.out.println("> Connection with lost, please try again in 3 seconds.");
				fails++;
				Thread.sleep(3000);
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

	public static int validateInt(Scanner scan) {
		int number;
		do {
			// System.out.println("Please enter a positive number!");
			while (!scan.hasNextInt()) {
				System.out.println("That's not a number!");
				scan.next(); // this is important!
			}
			number = scan.nextInt();
		} while (number <= 0);

		return number;

	}

	public static void addPub(ScholarInterface sch, Scanner scan, User myself) throws Exception {
		int fails = 0;
		System.out.println("Authors: (ex: lastName1, FirstName1;lastName2, FirstName2)");
		System.out.print("==> ");
		String string_autores = scan.nextLine();
		// scan.next();
		ArrayList<String> autores = new ArrayList<String>(Arrays.asList(string_autores.split(";")));

		for (int i = 0; i < autores.size(); i++) { // se o utilizador escrever os nomes a começar por um
													// espaço, elimina
													// esse espaço
			if (autores.get(i).substring(0, 1).equals(" ")) {
				autores.set(i, autores.get(i).substring(1));
			}
		}

		System.out.println("Title: ");
		System.out.print("==> ");
		String titulo = scan.nextLine();
		// scan.next();

		// scan.next();
		System.out.println("Year: ");
		System.out.print("==> ");
		int ano = validateInt(scan);

		System.out.println("Magazine: ");
		System.out.print("==> ");
		scan.next();
		String revista = scan.nextLine();

		System.out.println("Volume: ");
		System.out.print("==> ");
		String volume = scan.nextLine();

		System.out.println("Number: ");
		System.out.print("==> ");
		int numero = validateInt(scan);

		System.out.println("Page: ");
		System.out.print("==> ");
		scan.next();
		String pagina = scan.nextLine();

		System.out.println("Citations: ");
		System.out.print("==> ");
		int citacoes = validateInt(scan);
		scan.nextLine();

		while (true) {

			try {

				sch.addNewPublication(autores, titulo, ano, revista, volume, numero, pagina, citacoes, myself);

				break;

			} catch (Exception e) {
				System.out.println("> Connection with lost, please try again in 3 seconds.");
				fails++;
				Thread.sleep(3000);
			}

			if (fails == 10) {
				System.out.println("> Program terminated. Cause: Lost Connection with Server");
				System.exit(0);
			}

		}

	}

	public static void removePublications(ScholarInterface sch, Scanner scan, User myself) throws Exception {

		int fails = 0;
		myself.printPublications(true);

		if (myself.getListPubs() == null || myself.getListPubs().size() == 0) {

		}

		else {

			String[] options = new String[myself.getListPubs().size()];

			ArrayList<Integer> itemsToRemove = new ArrayList<>();

			for (int i = 0; i < myself.getListPubs().size(); i++) {
				options[i] = String.valueOf(i);
			}

			System.out.println("Choose what publications you want to remove: (ex: 1,3,4)");
			System.out.print("==> ");
			String resposta = scan.nextLine();

			String[] stripedAnswers = resposta.split(",");

			for (int i = 0; i < stripedAnswers.length; i++) {

				if (isNumber(stripedAnswers[i])) {

					if (Integer.parseInt(stripedAnswers[i]) >= 0
							&& Integer.parseInt(stripedAnswers[i]) <= stripedAnswers.length) {

						itemsToRemove.add(Integer.parseInt(stripedAnswers[i]));

					}

				}

			}

			Collections.sort(itemsToRemove);
			Collections.reverse(itemsToRemove);

			while (true) {

				try {

					sch.removePub(myself, itemsToRemove);

					break;

				} catch (Exception e) {
					System.out.println("> Connection with lost, please try again in 3 seconds.");
					fails++;
					Thread.sleep(3000);
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
			if (str == null) {
				return false;
			}

			if (str.substring(0, 1).equals(" ")) {
				str = str.substring(1);
			}

			int n = Integer.parseInt(str);
			return true;

		} catch (NumberFormatException e) {

			return false;
		}

	}

	public static void lookForPubs(ScholarInterface sch, Scanner scan, User myself) throws Exception {

		int fails = 0;
		ArrayList<Publication> listOfPublications = sch.getPublications();
		ArrayList<Integer> options = new ArrayList<>();
		ArrayList<String> autores = new ArrayList<>();
		int doi, value;

		for (int i = 0; i < listOfPublications.size(); i++) {

			autores = listOfPublications.get(i).getListaAutores();
			doi = listOfPublications.get(i).getDOI();

			if (autores.contains(myself.getNome()) && !myself.getListDois().contains(doi)) {

				System.out.println(i + ") " + listOfPublications.get(i));
				options.add(i);

			}

		}

		System.out.println("Choose what publications you want to add: (ex: 1,3,4)");
		System.out.print("==> ");
		String resposta = scan.nextLine();

		String[] stripedAnswers = resposta.split(",");

		for (int i = 0; i < stripedAnswers.length; i++) {

			if (isNumber(stripedAnswers[i])) {

				value = Integer.parseInt(stripedAnswers[i]);

				if (options.contains(value)) {

					myself.addPublication(listOfPublications.get(i));

				}

			}

		}

		while (true) {

			try {

				sch.saveInformation(sch, myself);

				break;

			} catch (Exception e) {
				System.out.println("> Connection with lost, please try again in 3 seconds.");
				fails++;
				Thread.sleep(3000);
			}

			if (fails == 10) {
				System.out.println("> Program terminated. Cause: Lost Connection with Server");
				System.exit(0);
			}

		}

	}

}