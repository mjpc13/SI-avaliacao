package exercicio4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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

			while (connected) {

				menu1Print();
				String menu1 = inputVerification(options_menu1, scan);
				String email = "";

				if (menu1.equals("1")) {
					logged = registerMenu(sch, scan);
				}
				
				else if (menu1.equals("2")) {

					email = loginMenu(sch, scan);
					
					if (email.equals(null)){
						logged = false;
					}
					else {
						logged = true;
					}

				}

				else {
					sch.writeToFiles();
					connected = false;
				}
				
				while (logged) {

					User myself = sch.getUserData(email);
					menu2Print();
					String menu2 = inputVerification(options_menu2, scan);
					System.out.println("=".repeat(55));

					if (menu2.equals("1")){
						myself.printPublications(true);
					}
	
					else if (menu2.equals("2")){
						myself.printPublications(false);
					}
	
					else if (menu2.equals("3")){
						System.out.println("Authors: ");
						ArrayList<String> autores = new ArrayList<String>();

						System.out.println("How many authors wrote this publication? ");
						int n = scan.nextInt();

						System.out.println("Please introduce the name of the authors and press enter, one name at a time. ");
						for (int i = 0; i <= n; i++) {
							String author = scan.nextLine();
							autores.add(author);
						}

						for(String author : autores){
							System.out.println(author);
						}
						System.out.println("Title: ");
						String titulo = scan.nextLine();

						System.out.println("Year: ");
						int ano = scan.nextInt();

						System.out.println("Magazine: ");
						String revista = scan.nextLine();

						System.out.println("Volume: ");
						String volume = scan.nextLine();

						System.out.println("Number: ");
						int numero = scan.nextInt();

						System.out.println("Page: ");
						String pagina = scan.nextLine();

						System.out.println("Citations: ");
						int citacoes = scan.nextInt();

						User user = myself;
						sch.addNewPublication(autores, titulo, ano, revista, volume, numero, pagina, citacoes, user);
					}
	
					else if (menu2.equals("4")){
						
					}
	
					else if (menu2.equals("5")){
						ArrayList<Integer> listOfDoi = new ArrayList<Integer>();
						System.out.println("How many publications do you wish to remove? ");
						int n = scan.nextInt();

						System.out.println("Please introduce the DOIs and press enter, one DOI at a time. ");
						for (int i = 0; i < n; i++) {
							int doi = scan.nextInt();
							listOfDoi.add(doi);
						}
						
						myself.removePublication(listOfDoi);
					}
	
					else if (menu2.equals("6")){
						myself.showStats();
					}
	
					else if (menu2.equals("7")){
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

			response = scan.next(); // faz o scan da resposta do utilizador (assume que é String)

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

		System.out.println("=".repeat(100));
		System.out.println("\n".repeat(2));

		return response;

	}

	public static String emailInput(Scanner scan) {

		System.out.println("Email: ");
		System.out.print("==> ");
		String email = scan.next();

		while ((email.contains("@") && email.contains(".")) == false) {
			System.out.println("You introduced an invalid email address. Please try again.\n");
			System.out.println("=".repeat(55));
			email = scan.next();
		}
		return email;
	}

	public static String loginMenu(ScholarInterface sch, Scanner scan) throws Exception {

		System.out.println("=".repeat(20) + " Login: " + "=".repeat(20));

		String email = emailInput(scan);

		System.out.println("Password: ");
		System.out.print("==> ");
		String password = scan.next();

		if (!sch.loginVerification(email, password)) {
			System.out.println("Invalid user. If you are a new user please register first.");

			return null;
		} else {
			System.out.println("Login Successful.");
			return email;
		}

	}

	public static boolean registerMenu(ScholarInterface sch, Scanner scan) throws Exception {

		System.out.println("=".repeat(20) + "Register:" + "=".repeat(20));

		System.out.println("Name: ");
		System.out.print("==> ");
		String name = scan.next();

		String email = emailInput(scan);

		System.out.println("Password: ");
		System.out.print("==> ");
		String password = scan.next();

		System.out.println("Afiliações: ");
		System.out.print("==> ");
		String afi = scan.next();

		if (sch.addNewUser(name, email, password, afi)) {
			System.out.println("> User Successfully Registered!");
			return true;
		} else {
			System.out.println("> This user already exists.");
			return false;
		}

	}

	public static void menu1Print() {

		System.out.println("=".repeat(10) + "Scholar System" + "=".repeat(10));
		System.out.println("1 - Register new Author \n2 - Login \n3 - Exit\n");
	}

	public static void menu2Print() {
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

}