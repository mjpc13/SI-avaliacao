package exercicio4;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ScholarInterface extends Remote {

    // Creating Remote interface for our application
    public ArrayList<Publication> getPublications() throws Exception;

    public boolean addNewPublication(ArrayList<String> autores, String titulo, int ano, String revista, String volume,
            int numero, String pagina, int citacoes, User myself) throws Exception;

    public User getUserData(String mail) throws Exception;

    public boolean addNewUser(String nome, String mail, String password, String afi) throws Exception;

    public boolean loginVerification(String mail, String password) throws Exception;

    public void writeToFiles() throws Exception;

    public void removePub(User myself, ArrayList<Integer> itemsToRemove) throws Exception;

}
