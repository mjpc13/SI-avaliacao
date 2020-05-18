package exercicio4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class User implements Serializable {

    private static final long serialVersionUID = 8306809467553336903L;

    private String nome, mail, password, afi;
    public ArrayList<Publication> listPubs;
    public int totalCitations;

    public User(String nome2, String mail2, String password2, String afi2) {

        this.afi = afi2;
        this.mail = mail2;
        this.nome = nome2;
        this.password = password2;
        this.totalCitations = 0;
        this.listPubs = new ArrayList<>();

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
        totalCitations += pub.getCitacoes();

    }

    public void printPublications(boolean by_year) {

        if (listPubs == null) {
            System.out.println("> You don't have anything published yet.");
        }

        else {
            if (by_year) {
                Collections.sort(listPubs, new SortbyYear());
            } else {
                Collections.sort(listPubs, new SortbyCitation());
            }

            for (int i = 0; i < listPubs.size(); i++) {

                System.out.println(i + ") " + listPubs.get(i) + "\n");

            }
        }

    }

    public void removePublication(ArrayList<Integer> listOfDoi) {

        for (Publication pub : listPubs) {

            if (listOfDoi.contains(pub.getDOI())) {

                listPubs.remove(pub); // posso ter que meter um for normal

            }

        }

    }

    public void showStats() {

        int i10 = 0;

        for (Publication pub : listPubs) {

            if (pub.getCitacoes() >= 10) {
                i10 += 1;
            }

        }

        // E não entendo o que é o H

        System.out.println(i10);
    }

    /**
     * @param listPubs the listPubs to set
     */
    public void setListPubs(ArrayList<Publication> listPubs) {
        this.listPubs = listPubs;
    }

    class SortbyYear implements Comparator<Publication> {

        public int compare(Publication a, Publication b) {
            return b.getAno() - a.getAno();
        }
    }

    class SortbyCitation implements Comparator<Publication> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Publication a, Publication b) {

            return b.getCitacoes() - a.getCitacoes();

        }
    }

}