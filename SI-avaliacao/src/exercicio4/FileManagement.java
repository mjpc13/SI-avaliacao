package exercicio4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class FileManagement implements Serializable {

    private static final long serialVersionUID = 9183947707057785900L;

    private ArrayList<Publication> pub_list;
    private ArrayList<User> user_list;

    public void readPublications() {

        try (FileInputStream fis = new FileInputStream("SI-avaliacao/src/exercicio4/pubs.tmp")) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.pub_list = (ArrayList<Publication>) ois.readObject();

            ois.close();

        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            // ficheiro nao encontrado ou dados corrompidos
            this.pub_list = new ArrayList<>();
            System.out.println("> Nao foram encontrados dados guardados: foi criado um novo ficheiro de Publicações");
            writePublications(); // escreve dados atuais no disco

        }

    }

    public void writePublications() {

        try (FileOutputStream fos = new FileOutputStream("SI-avaliacao/src/exercicio4/pubs.tmp")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pub_list);
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readUsers() {

        try (FileInputStream fis = new FileInputStream("SI-avaliacao/src/exercicio4/users.tmp")) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.user_list = (ArrayList<User>) ois.readObject();

            ois.close();

        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            // ficheiro nao encontrado ou dados corrompidos
            this.user_list = new ArrayList<>();
            System.out.println("> Nao foram encontrados dados guardados: foi criado um novo ficheiro de Users");
            writeUsers(); // escreve dados atuais no disco
        }

    }

    public void writeUsers() {

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

    public void addPublication(Publication pub) {

        this.pub_list.add(pub);

    }

    public void addUser(User u) {
        this.user_list.add(u);
    }

    public void updateUserInfo(User u) {

        for (User user : user_list) {

            if (user.getMail().equals(u.getMail())) {

                user.setListPubs(u.getListPubs());

            }

        }

    }

    public void removePubs(ArrayList<Integer> DOIs) {

        for (int i = 0; i < DOIs.size(); i++) {

            for (int j = 0; j < pub_list.size(); j++) {

                if (DOIs.get(i) == pub_list.get(j).getDOI()) {
                    pub_list.remove(j);
                }

            }
        }

    }

}