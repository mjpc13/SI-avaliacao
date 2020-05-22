package exercicio4;

import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ScholarImpl extends UnicastRemoteObject implements ScholarInterface {

    private static final long serialVersionUID = 357697766690449964L;
    private FileManagement fm;

    public ScholarImpl(FileManagement fm) throws RemoteException { // construtor
        this.fm = fm;
        // TODO Auto-generated constructor stub
    }

    public ArrayList<Publication> getPublications() throws Exception { // faz return da variável pub_list do objecto fm
        // TODO Auto-generated method stub

        ArrayList<Publication> pub_list = fm.getPub_list();
        return pub_list;

    }

    public boolean addNewPublication(ArrayList<String> autores, String titulo, int ano, String revista, String volume,
            int numero, String pagina, int citacoes, User myself) throws Exception {
        // TODO Auto-generated method stub

        ArrayList<Publication> pubs_list = fm.getPub_list(); // vai buscar a variável pub_list do objecto fm

        // cria o hashcode (aka DOI neste caso) para depois verificar se esta publicação
        // já se encontra no sistema
        String id = "";
        for (String str : autores) {
            id = id + str;
        }
        id = id + titulo + revista + volume + pagina;
        int doi = id.hashCode();

        for (Publication pub : pubs_list) { // verifica se já existe a publicação

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

        fm.updateUserInfo(myself); // faz o update do user na variavel user_list no objecto fm

        return true;
    }

    public User getUserData(String mail) throws Exception { // vai buscar o User com aquele mail
        // TODO Auto-generated method stub

        ArrayList<User> user_list = fm.getUser_list(); // carrega a user_list

        for (User user : user_list) {
            if (user.getMail().equals(mail)) {

                return user; // quando o mail for igual ao do user faz retur do user

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

        fm.addUser(user); // adiciona o user à variável user_list do objecto fm
        fm.writeUsers(); // guarda no ficheiro a variável user_list

        return true;
    }

    public boolean loginVerification(String mail, String password) {
        // TODO Auto-generated method stub

        ArrayList<User> user_list = fm.getUser_list(); // carrega a lista de users

        for (User user : user_list) { // verifica se existe algum user com aquelas credenciais
            if (user.getMail().equals(mail) && user.getPassword().equals(password)) {

                return true; // a autenticação é válida
            }
        }

        return false; // falhou na palavra pass ou no usuário
    }

    @Override
    public void writeToFiles() throws Exception {
        // TODO Auto-generated method stub

        fm.writePublications(); // guarda no ficheiro a variável pubs_list
        fm.writeUsers(); // guarda no ficheiro a variável user_list

    }

    @Override
    public void removePub(User myself, ArrayList<Integer> itemsToRemove) throws Exception {
        // TODO Auto-generated method stub
        ArrayList<Integer> DOIs = new ArrayList<>();
        ArrayList<User> user_list = fm.getUser_list(); // carrega a user_list

        DOIs = myself.removeSelfPubs(itemsToRemove); // remove as publicações desejadas do próprio user

        for (User user : user_list) { // remove as pubs desejadas de todos os outros utilizadores

            // isto pode não ser desejável pois se adicionarmos outros autores à
            // publicação, estes ficam com o poder de a remover de todo o lado, inclusive do
            // criador. Como não nos foi dado mais indicações sobre como proceder para
            // remover as publicações optámos por este método conscientes das falhas de
            // segurança que pode criar, mas no contexto do problema não pareceu ter
            // grande importância.

            user.removeUserPubs(DOIs);

        }

        fm.removePubs(DOIs); // remove as publicações da variável list_pubs do objecto fm
        fm.updateUserInfo(myself); // faz o update do user na variavel user_list no objecto fm
    }

    public void saveInformation(ScholarInterface sch, User myself) {

        fm.updateUserInfo(myself);// faz o update do user na variavel user_list no objecto fm
        // fm.writePublications(); // guarda no ficheiro a variável pubs_list
        // fm.writeUsers(); // guarda no ficheiro a variável user_list

    }

}
