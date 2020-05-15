package exercicio4;
import java.util.Scanner;

public class ClientScratch {
    
    public static void main(String[] args) throws InterruptedException {
    
        Scanner scan = new Scanner(System.in);
        // menu 1
        System.out.println("=".repeat(10) + "Scholar System" + "=".repeat(10));
        System.out.println("1 - Register new Author \n2 - Login \n3 - Exit\n");
        String[] options_menu1 = {"1","2","3"};
        String menu1 = inputVerification(options_menu1);

        if(menu1.equals("1")){
            System.out.println("=".repeat(20) + "Register:" + "=".repeat(20));
            System.out.println("Email:");
            String email = scan.nextLine();

            while((email.contains("@") && email.contains(".")) == false){
                System.out.println("You introduced an invalid email address. Please try again.");
                email = scan.nextLine();
            }
            System.out.println("Password: ");
            String password = scan.nextLine();

            // addNewuser(email,password);
        } 
        else if(menu1.equals("2")){
            System.out.println("=".repeat(20) + "Login:" + "=".repeat(20));
        
            System.out.println("Email: ");
            String email = scan.nextLine();

            while((email.contains("@") && email.contains(".")) == false){
                System.out.println("You introduced an invalid email address. Please try again.");
                email = scan.nextLine();
            }
            
            System.out.println("Password: ");
            String password = scan.nextLine();

            // while(!loginVerification(email, password)) {
            //     System.out.println("Invalid user. Please try again.");

            //     System.out.println("Email: ");
            //     email = scan.nextLine();
            
            //     System.out.println("Password: ");
            //     password = scan.nextLine();
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

            String[] options_menu2 = {"1","2","3","4","5","6","7"};
            String menu2 = inputVerification(options_menu2);

        }

        else if (menu1.equals("3")){
            
            
        }


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

}