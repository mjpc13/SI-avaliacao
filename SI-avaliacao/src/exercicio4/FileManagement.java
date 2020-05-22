package exercicio4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManagement {

    private ArrayList<Publication> pub_list; // variável que contêm a lista de publicações atualizada
    private ArrayList<User> user_list; // variável que contêm a lista de users atualizada

    public void readPublications() { // função que lé o ficheiro pubs.tmp e atualiza a variável pub_list segundo o
                                     // Objecto presente no ficheiro;

        try (FileInputStream fis = new FileInputStream("SI-avaliacao/src/exercicio4/pubs.tmp")) { // tenta aceder ao
                                                                                                  // ficheiro, pode
                                                                                                  // ocorrer IOException
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.pub_list = (ArrayList<Publication>) ois.readObject(); // atualiza o valor de pub_list, segundo o
                                                                       // ficheiro, pode ocorrer ClassCastException ou
                                                                       // ClassNotFoundException

            ois.close();// fecha o ios

        } catch (IOException e) {// ficheiro nao encontrado

            this.pub_list = new ArrayList<>(); // cria uma lista vazia
            System.out.println("> Nao foram encontrados dados guardados: foi criado um novo ficheiro de Publicações");
            writePublications(); // escreve dados atuais no disco (neste caso a lista vazia)

        } catch (ClassCastException | ClassNotFoundException d) {
            d.printStackTrace(); // trata dos restantes erros
        }

    }

    public void writePublications() { // função que escreve a variável pub_list no ficheiro pubs.tmp e atualiza

        try (FileOutputStream fos = new FileOutputStream("SI-avaliacao/src/exercicio4/pubs.tmp")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pub_list);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readUsers() { // igual à readPublication() mas para os Users

        try (FileInputStream fis = new FileInputStream("SI-avaliacao/src/exercicio4/users.tmp")) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.user_list = (ArrayList<User>) ois.readObject();

            ois.close();

        } catch (IOException e) {
            // ficheiro nao encontrado ou dados corrompidos
            this.user_list = new ArrayList<>();
            System.out.println("> Nao foram encontrados dados guardados: foi criado um novo ficheiro de Users");
            writeUsers(); // escreve dados atuais no disco
        } catch (ClassCastException | ClassNotFoundException d) {
            d.printStackTrace();
        }

    }

    public void writeUsers() { // igual à writePublication() mas para os Users

        try (FileOutputStream fos = new FileOutputStream("SI-avaliacao/src/exercicio4/users.tmp")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user_list);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return the pub_list
     */
    public ArrayList<Publication> getPub_list() {
        return pub_list;
    }

    /**
     * @return the user_list
     */
    public ArrayList<User> getUser_list() {
        return user_list;
    }

    public void addPublication(Publication pub) { // adiciona uma nova publicação à variável pub_list

        this.pub_list.add(pub);

    }

    public void addUser(User u) { // adiciona um novo user à variável user_list
        this.user_list.add(u);
    }

    public void updateUserInfo(User u) { // atualiza as caracteristicas do user u, na variável user_list

        for (User user : user_list) { // percorre todos os Users presentes no ArrayList (variável user_list)

            if (user.getMail().equals(u.getMail())) { // quando o mail do user é igual ao do u, então o user é o objecto
                                                      // a que queremos dar update

                // user.setListPubs(u.getListPubs());
                // user.setListDois(u.getListDois());
                // user.setTotalCitations(u.getTotalCitations());

                user = u; // actualiza o user

            }

        }

    }

    public void removePubs(ArrayList<Integer> DOIs) { // remove uma publicação do ArrayList pubs_list

        // A variável requerida por esta função DOIs é a lista com os DOIs das
        // publicações a eliminar

        for (int i = 0; i < DOIs.size(); i++) { // percorre todos os doi na ArrayList pub_list

            for (int j = 0; j < pub_list.size(); j++) { // percorre todos os doi na ArrayList DOIs

                if (DOIs.get(i) == pub_list.get(j).getDOI()) { // quando o valor do DOI for idêntico então elimina essa
                                                               // pub da pub_list
                    pub_list.remove(j);
                }

            }
        }

    }

}