package exercicio4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ScholarClient {
	public static void main(String[] args) {
		try {
			// Returns a reference to the remote object Registry on the specified host and
			// port.
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);

			// 'lookup' returns the remote reference bound to the specified name in this
			// registry.
			ScholarInterface sch = (ScholarInterface) registry.lookup("test");

			// let's execute our remote operation and keep the return value in 'result'!

			Scanner scan = new Scanner(System.in);

			System.out.println("=".repeat(10) + "Scholar System" + "=".repeat(10));
			System.out.println("1 - Register new Author \n2 - Login \n3 - Exit\n");
			String[] options_menu1 = { "1", "2", "3" };
			String menu1 = inputVerification(options_menu1, scan);

			if (menu1.equals("1")) {
				System.out.println("=".repeat(20) + "Register:" + "=".repeat(20));

				System.out.println("Name: ");
				String name = scan.nextLine();

				System.out.println("Email: ");
				String email = scan.nextLine();

				while ((email.contains("@") && email.contains(".")) == false) {
					System.out.println("You introduced an invalid email address. Please try again.");
					email = scan.nextLine();
				}
				System.out.println("Password: ");
				String password = scan.nextLine();

				System.out.println("Afi: ");
				String afi = scan.nextLine();

				// addNewuser(name,email,password,afi);
			} else if (menu1.equals("2")) {
				System.out.println("=".repeat(20) + "Login:" + "=".repeat(20));

				System.out.println("Email: ");
				String email = scan.nextLine();

				while ((email.contains("@") && email.contains(".")) == false) {
					System.out.println("You introduced an invalid email address. Please try again.");
					email = scan.nextLine();
				}

				System.out.println("Password: ");
				String password = scan.nextLine();

				// while(!loginVerification(email, password)) {
				// System.out.println("Invalid user. Please try again.");

				// System.out.println("Email: ");
				// email = scan.nextLine();

				// System.out.println("Password: ");
				// password = scan.nextLine();
				// }

				// menu 3
				System.out.println("=".repeat(55));
				System.out.println("1 - List Publications by year (newest first)");
				System.out.println("2 - List Publications by year (Most cited first)");
				System.out.println("3 - Add Publication");
				System.out.println("4 - Look for author publications in database");
				System.out.println("5 - Remove publication");
				System.out.println("6 - Show author statistics");
				System.out.println("7 - Logout");
				System.out.println("=".repeat(55));

				String[] options_menu2 = { "1", "2", "3", "4", "5", "6", "7" };
				String menu2 = inputVerification(options_menu2, scan);

			}

			else if (menu1.equals("3")) {

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

}