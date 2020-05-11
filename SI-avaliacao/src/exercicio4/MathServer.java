package teorica.rmi.code;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MathServer
{
	public static void main(String[] args)
	{
		try
		{
			MathImpl obj = new MathImpl();

			// we programmatically create the registry. The following method creates and exports a Registry instance 
			// on the local host that accepts requests on the specified port.
			// alternativaly we could execute "rmiregistry" at the command line
			// and use LocateRegistry.getRegistry(...)
			Registry registry = LocateRegistry.createRegistry(1099);

			//Question: could we use rebind here?
			registry.bind("calculadora", obj);

		}
		catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{                   // usually it is advisable to use multiple catch blocks and perform different error handling actions
		                    // depending on the specific exception type caught
			System.err.println("Ocorreu um erro:");
			e.printStackTrace(); // prints detailed information about the exception
		}
	}
}