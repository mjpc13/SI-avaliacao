package exercicio4;

import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ScholarImpl implements ScholarInterface {

    public ArrayList<Publication> getPublications() throws Exception {
        // TODO Auto-generated method stub

        FileManagement fm = new FileManagement();

        ArrayList<Publication> pub_list = fm.readPublications();

        return pub_list;
    }

    public boolean addNewPublication(ArrayList<String> autores, String titulo, int ano, String revista, String volume,
            int numero, String pagina, int citacoes) throws Exception {
        // TODO Auto-generated method stub
        FileManagement fm = new FileManagement();

        ArrayList<Publication> pubs_list = fm.readPublications();

        String id = "";
        for (String str : autores) {
            id = id + str;
        }
        id = id + titulo + revista + volume + pagina;
        int doi = id.hashCode();

        for (Publication pub : pubs_list) {

            if (pub.getDOI().equals(doi)) {
                return false;
            }

        }

        Publication pub = new Publication(autores, titulo, ano, revista, volume, numero, pagina, citacoes);

        pubs_list.add(pub);

        fm.writePublications(pubs_list);

        return true;
    }

    public User getUserData(String mail) throws Exception {
        // TODO Auto-generated method stub

        FileManagement fm = new FileManagement();

        ArrayList<User> user_list = fm.readUsers();

        for (User user : user_list) {
            if (user.getMail().equals(mail)) {

                return user;

            }
        }

        return null;
    }

    public boolean addNewUser(String nome, String mail, String password, String afi) throws Exception {
        // TODO Auto-generated method stub

        FileManagement fm = new FileManagement();

        ArrayList<User> user_list = fm.readUsers();

        for (User user : user_list) {

            if (user.getMail().equals(mail)) {
                return false;
            }

        }

        User user = new User(nome, mail, password, afi);

        user_list.add(user);

        fm.writeUsers(user_list);

        return true;
    }

    public boolean loginVerification(String mail, String password) throws Exception {
        // TODO Auto-generated method stub

        FileManagement fm = new FileManagement();

        ArrayList<User> user_list = fm.readUsers();

        for (User user : user_list) {
            if (user.getMail().equals(mail) && user.getPassword().equals(password)) {

                return true;
            }
        }

        return false;

    }

}
