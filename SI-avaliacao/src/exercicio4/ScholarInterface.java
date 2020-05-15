package exercicio4;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ScholarInterface {

    // Creating Remote interface for our application
    public ArrayList<Publication> getPublications() throws Exception;

    public void addNewPublication() throws Exception;

    public User getUserData() throws Exception;

}