package exercicio4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class User implements Serializable {

    private static final long serialVersionUID = 8306809467553336903L;

    private String nome, mail, password, afi;
    private ArrayList<Publication> listPubs;

    public User(String nome2, String mail2, String password2, String afi2) {

        this.afi = afi2;
        this.mail = mail2;
        this.nome = nome2;
        this.password = password2;

    }

    /**
     * @return the afi
     */
    public String getAfi() {
        return afi;
    }

    /**
     * @return the listPubs
     */
    public ArrayList<Publication> getListPubs() {
        return listPubs;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param afi the afi to set
     */
    public void setAfi(String afi) {
        this.afi = afi;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void addPublication(Publication pub) {

        listPubs.add(pub);

    }

    public void printPublications(boolean by_year) {

        if (by_year) {
            Collections.sort(listPubs, new SortbyYear());
        } else {
            Collections.sort(listPubs, new SortbyCitation());
        }

        for (int i = 0; i < listPubs.size(); i++) {

            System.out.println(i + ") " + listPubs.get(i));

        }

    }

    public void removePublication() {

        Scanner sc = new Scanner(System.In);

        for (int i = 0; i < listPubs.size(); i++) {

            System.out.println(i + ") " + listPubs.get(i));

        }

    }

    class SortbyYear implements Comparator<Publication> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Publication a, Publication b) {
            return a.getAno() - b.getAno();
        }
    }

    class SortbyCitation implements Comparator<Publication> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Publication a, Publication b){

                return a.getCitacoes() b.getCitacoes(); 

            }
    }

}