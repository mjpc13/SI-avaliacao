package exercicio4;

import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ScholarImpl extends UnicastRemoteObject implements ScholarInterface {

    private static final long serialVersionUID = 357697766690449964L;
    private FileManagement fm;

    protected ScholarImpl(FileManagement fm) throws RemoteException {
        this.fm = fm;
        // TODO Auto-generated constructor stub
    }

    public ArrayList<Publication> getPublications() throws Exception {
        // TODO Auto-generated method stub

        ArrayList<Publication> pub_list = fm.getPub_list();
        return pub_list;

    }

    public boolean addNewPublication(ArrayList<String> autores, String titulo, int ano, String revista, String volume,
            int numero, String pagina, int citacoes, User myself) throws Exception {
        // TODO Auto-generated method stub

        ArrayList<Publication> pubs_list = fm.getPub_list();

        String id = "";
        for (String str : autores) {
            id = id + str;
        }
        id = id + titulo + revista + volume + pagina;
        int doi = id.hashCode();

        for (Publication pub : pubs_list) {

            if (pub.getDOI() == doi) { // significa que já existe uma pub com este doi ou seja a publicação já existe
                                       // nesse caso rejeita a adição e dá return de False para dps ser processado pelo
                                       // código no Cliente

                System.out.println("> A publicação que inseriu já se encontra no sistema");
                return false;
            }

        }

        Publication pub = new Publication(autores, titulo, ano, revista, volume, numero, pagina, citacoes); // cria a
                                                                                                            // publicação
                                                                                                            // a ser
                                                                                                            // adicionada
        fm.addPublication(pub); // adiciona a publicação a lista que contem TODAS as pubs, e envia TRUE;
        myself.addPublication(pub); // adiciona a publicação a lista de pubs daquele usuário;
        fm.updateUserInfo(myself);

        // fm.writePublications(); // escreve no ficheiro a nova lista de Publications
        return true;
    }

    public User getUserData(String mail) throws Exception {
        // TODO Auto-generated method stub

        ArrayList<User> user_list = fm.getUser_list();

        for (User user : user_list) {
            if (user.getMail().equals(mail)) {

                return user;

            }
        }

        return null;
    }

    public boolean addNewUser(String nome, String mail, String password, String afi) throws Exception {
        // TODO Auto-generated method stub

        ArrayList<User> user_list = fm.getUser_list();

        for (User user : user_list) {

            if (user.getMail().equals(mail)) { // se já existir um user com aquele email, não é permitido criar um novo
                                               // user com estas informações
                return false;
            }

        }

        User user = new User(nome, mail, password, afi); // caso contrário criamos um usuário e adicionamos ao ficheiro

        fm.addUser(user);
        fm.writeUsers();

        return true;
    }

    public boolean loginVerification(String mail, String password) {
        // TODO Auto-generated method stub

        ArrayList<User> user_list = fm.getUser_list();

        for (User user : user_list) {
            if (user.getMail().equals(mail) && user.getPassword().equals(password)) {

                return true; // a autenticação é válida
            }
        }

        return false; // falhou na palavra pass ou no usuário
    }

    @Override
    public void writeToFiles() throws Exception {
        // TODO Auto-generated method stub

        fm.writePublications();
        fm.writeUsers();

    }

}
