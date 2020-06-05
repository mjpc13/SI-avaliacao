package exameNormal;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) {
        try {

            System.out.println("\n"); // puramente estético

            ExamImpl obj = new ExamImpl(); // cria o objecto do SchaloarImplement, e adiciona a variável fm, à
                                           // implementação para o user ter o poder de ler e guardar alterações

            // we programmatically create the registry. The following method creates and
            // exports a Registry instance
            // on the local host that accepts requests on the specified port.
            // alternativaly we could execute "rmiregistry" at the command line
            // and use LocateRegistry.getRegistry(...)
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.bind("exame", obj);

        } catch (Exception e) // catching Exception means that we are handling all errors in the same block
        { // usually it is advisable to use multiple catch blocks and perform different
          // error handling actions
          // depending on the specific exception type caught
            System.err.println("Ocorreu um erro Server: ");
            e.printStackTrace(); // prints detailed information about the exception
        }
    }
}