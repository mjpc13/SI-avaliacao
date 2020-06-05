package exameNormal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String args[]) throws IOException {

        // Register service on port 1234
        ServerSocket s = new ServerSocket(1234);

        while (true) {

            System.out.println("> Waiting for a client..." + "\n");

            // Wait and accept a connection
            try {

                Socket s1 = s.accept();

                System.out.println("> A connection with a client was establish" + "\n");

                // Build DataStreams (input and output) to send and receive messages to/from the
                // client
                OutputStream out = s1.getOutputStream();
                DataOutputStream dataOut = new DataOutputStream(out);

                InputStream in = s1.getInputStream();
                DataInputStream dataIn = new DataInputStream(in);

                boolean connected = true;

                while (connected) {

                    dataOut.writeUTF("\nn> Please insert the parameter a :\n ===> ");
                    dataOut.flush();

                    double a = inputVerification(dataIn, dataOut);

                    dataOut.writeUTF("\n> Please insert the parameter b :\n ===> ");
                    dataOut.flush();

                    double b = inputVerification(dataIn, dataOut);

                    dataOut.writeUTF("\n> Please insert the parameter c : \n ===> ");
                    dataOut.flush();

                    double c = inputVerification(dataIn, dataOut);

                    Poly2 poly = new Poly2(a, b, c);

                    dataOut.writeUTF("\n> Please insert the parameter xi :\n ===> ");
                    dataOut.flush();

                    double xi = inputVerification(dataIn, dataOut);

                    dataOut.writeUTF("\n> Please insert the parameter passo : \n ===> ");
                    dataOut.flush();

                    double passo = inputVerification(dataIn, dataOut);

                    dataOut.writeUTF("\n> Please insert the parameter erro : \n ===> ");
                    dataOut.flush();

                    double erro = inputVerification(dataIn, dataOut);

                    SteepDesc steep = new SteepDesc(poly, xi, passo, erro);

                    steep.compute();

                    dataOut.writeUTF("\n> " + steep + "\n Do you want to continue [Y/N]? \n ==>");
                    dataOut.flush();

                    String response = dataIn.readUTF();

                    if (response.equals("N")) {

                        connected = false;

                    }

                }

                // Cleanup operations, close the streams, the connection, but don't close the
                // ServerSocket
                dataOut.close();
                dataIn.close();
                s1.close();

            } catch (IOException io) {

                System.out.println("> Client Disconected");
                System.out.println("-".repeat(100));

            }
        }
    }

    private static double inputVerification(DataInputStream dataIn, DataOutputStream dataOut) throws IOException {

        double final1 = 0;

        boolean incorrect = true;

        while (incorrect) {

            try {

                String response = dataIn.readUTF();
                final1 = Double.parseDouble(response);

                incorrect = false;

            } catch (Exception e) {

                dataOut.writeUTF("> Nao inseriu um double, tente outra vez: \n ==>");
                dataOut.flush();

                e.printStackTrace();

            }

        }
        return final1;

    }

}