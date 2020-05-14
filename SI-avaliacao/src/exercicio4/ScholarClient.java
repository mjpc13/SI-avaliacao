package exercicio4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MathClient {
	public static void main(String[] args) {
		try {
			// Returns a reference to the remote object Registry on the specified host and
			// port.
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);

			// 'lookup' returns the remote reference bound to the specified name in this
			// registry.
			IMath myCalc = (IMath) registry.lookup("calculadora");

			// let's execute our remote operation and keep the return value in 'result'!
			int result = myCalc.add(2, 3);

			System.out.println("result: " + result);
		} catch (Exception e) // catching Exception means that we are handling all errors in the same block
		{ // usually it is advisable to use multiple catch blocks and perform different
			// error handling actions
			// depending on the specific exception type caught
			System.err.println("Ocorreu um erro: ");
			e.printStackTrace(); // prints detailed information about the exception
		}
	}
}