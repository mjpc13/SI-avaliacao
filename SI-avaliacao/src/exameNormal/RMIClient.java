package exameNormal;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class RMIClient {

    public static void main(String[] args) {
        try {
            // Returns a reference to the remote object Registry on the specified host and
            // port.
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // 'lookup' returns the remote reference bound to the specified name in this
            // registry.
            ExamInterface sch = (ExamInterface) registry.lookup("exame");

            // let's execute our remote operation and keep the return value in 'result'!

            Scanner scan = new Scanner(System.in);

            System.out.println("Insira o a \n");
            double a = inputVerification(scan);

            System.out.println("Insira o b \n");
            double b = inputVerification(scan);

            System.out.println("Insira o c \n");
            double c = inputVerification(scan);

            System.out.println("Insira o xi \n");
            double xi = inputVerification(scan);

            System.out.println("Insira o passo \n");
            double passo = inputVerification(scan);

            System.out.println("Insira o erro \n");
            double erro = inputVerification(scan);

            String resposta = sch.createObjects(a, b, c, xi, passo, erro);

            System.out.println(resposta);

        } catch (Exception e) {

        }
    }

    private static double inputVerification(Scanner scan) throws IOException {

        double final1 = 0;

        boolean incorrect = true;

        while (incorrect) {

            try {

                String response = scan.next();
                final1 = Double.parseDouble(response);

                incorrect = false;

            } catch (Exception e) {

                System.out.println("> Nao inseriu um double, tente outra vez: \n ==>");

                e.printStackTrace();

            }

        }
        return final1;

    }

}