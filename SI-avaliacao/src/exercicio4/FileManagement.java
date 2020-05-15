package exercicio4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManagement implements java.io.Serializable {

    private ArrayList<Publication> pub_list;
    private ArrayList<User> user_list;

    /**
     *
     */
    private static final long serialVersionUID = -8121895196637993258L;

    public ArrayList<Publication> readPublications() {

        try (FileInputStream fis = new FileInputStream("pubs.tmp")) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.pub_list = (ArrayList<Publication>) ois.readObject();

            ois.close();
            return pub_list;

        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            // ficheiro nao encontrado ou dados corrompidos

            System.out.println("> Nao foram encontrados dados guardados: foi criado um novo ficheiro");
            writePublications(null); // escreve dados atuais no disco
            return null;
        }

    }

    public void writePublications(ArrayList<Publication> Pubs) {

        try (FileOutputStream fos = new FileOutputStream("pubs.tmp")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Pubs);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<User> readUsers() {

        try (FileInputStream fis = new FileInputStream("users.tmp")) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<User> user_list = (ArrayList<User>) ois.readObject();

            ois.close();
            return user_list;

        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            // ficheiro nao encontrado ou dados corrompidos

            System.out.println("> Nao foram encontrados dados guardados: foi criado um novo ficheiro");
            writePublications(null); // escreve dados atuais no disco
            return null;
        }

    }

    public void writeUsers(ArrayList<User> users) {

        try (FileOutputStream fos = new FileOutputStream("users.tmp")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();

        } catch (IOException e) { // erro na serializacao do objeto
            e.printStackTrace();
        }

    }

}