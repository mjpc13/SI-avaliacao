package exercicio4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class User implements Serializable {

    private static final long serialVersionUID = 8306809467553336903L; // auto-generated serialVersionUID

    // variáveis que cada User tem
    private String nome, mail, password, afi;
    public ArrayList<Publication> listPubs;
    public ArrayList<Integer> listDois;
    public int totalCitations;

    public User(String nome2, String mail2, String password2, String afi2) { // construtor do user

        this.afi = afi2;
        this.mail = mail2;
        this.nome = nome2;
        this.password = password2;
        this.totalCitations = 0;
        this.listPubs = new ArrayList<>();
        this.listDois = new ArrayList<>();

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
     * @param totalCitations the totalCitations to set
     */
    public void setTotalCitations(int totalCitations) {
        this.totalCitations = totalCitations;
    }

    /**
     * @return the totalCitations
     */
    public int getTotalCitations() {
        return totalCitations;
    }

    /**
     * @param listDois the listDois to set
     */
    public void setListDois(ArrayList<Integer> listDois) {
        this.listDois = listDois;
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
     * @return the listDois
     */
    public ArrayList<Integer> getListDois() {
        return listDois;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void addPublication(Publication pub) { // método para adicionar uma pub à lista de publicações do Autor

        listPubs.add(pub); // adiciona a pub
        listDois.add(pub.getDOI()); // adiciona o DOI da pub à lista de DOI
        totalCitations += pub.getCitacoes(); // adiciona o numero de citações da nova publicação ao numero total de
                                             // citações do Autor

    }

    public void printPublications(boolean by_year) { // ordena a lista de pubs E faz print da mesma;

        if (listPubs.size() == 0) {
            System.out.println("\n> You don't have anything published yet.");
        }

        else {
            if (by_year) { // se quiser ordenar por ano

                Collections.sort(listPubs, new SortbyYear()); // usa o comparator feito na class SortbyYear()

            }

            else { // se quiser ordenar por citações

                Collections.sort(listPubs, new SortbyCitation()); // usa o comparator feito na class SortbyCitation()

            }

            for (int i = 0; i < listPubs.size(); i++) { // dá print das publicações

                System.out.println(i + ") " + listPubs.get(i) + "\n");

            }

        }
    }

    public void showStats() {

        int i10 = 0;

        for (Publication pub : listPubs) { // percorre a lista de publicações

            if (pub.getCitacoes() >= 10) { // sempre que a publicação tiver um numero de citaçnoes superior a 10
                                           // incrementa o i10 por 1
                i10 += 1;
            }

        }

        int H = 0;

        for (int i = 0; i <= totalCitations; i++) {

            int nPubs = 0;
            for (Publication pub : listPubs) { // percorre a lista de publicações

                if (pub.getCitacoes() >= i) { // se a pub tiver mais citações que i

                    nPubs++; // incrementa o numero de publicações com citações maior ou igual a i

                }

            }

            if (nPubs >= i) { // quando o numero de publicações com citações maior ou igual a i for maior ou
                              // igual que i

                H = i; // por definição o H é igual a i

            }
        }
        // prints das estatisticas
        System.out.println("\nThe author's stats are:");
        System.out.println("i10 = " + i10);
        System.out.println("H = " + H);
        System.out.println("Total Citations = " + totalCitations + "\n");
    }

    /**
     * @param listPubs the listPubs to set
     */
    public void setListPubs(ArrayList<Publication> listPubs) {
        this.listPubs = listPubs;
    }

    public ArrayList<Integer> removeSelfPubs(ArrayList<Integer> itemsToRemove) { // remove publicações da lista de pubs
                                                                                 // do autor em questão

        // INPUT: items to remove é o indice das publicações a remover na lista de pubs
        // do autor

        // OUTPUT: Array de dois DOIs que foram removidos (para posteriormente serem
        // removidos na váriavel pub_list do objecto fm)

        ArrayList<Integer> doiToRemove = new ArrayList<>();
        int tr;

        for (int i = 0; i < itemsToRemove.size(); i++) { // percorrer a lista de indices e remover as pubs

            tr = itemsToRemove.get(i);
            doiToRemove.add(listPubs.get(tr).getDOI()); // adiciona o DOI da pub a remover na lista de DOIs

            totalCitations -= listPubs.get(tr).getCitacoes(); // subtrai o numero de total citacoes do autor
            listPubs.remove(tr); // remove a pub
            listDois.remove(tr); // remove o doi associado à pub

        }

        return doiToRemove;

    }

    public void removeUserPubs(ArrayList<Integer> DOIs) {

        for (int i = 0; i < DOIs.size(); i++) { // percorre todos os doi na ArrayList pub_list

            for (int j = 0; j < listPubs.size(); j++) { // percorre todos os doi na ArrayList DOIs

                if (DOIs.get(i) == listPubs.get(j).getDOI()) { // quando o valor do DOI for idêntico então elimina essa
                                                               // pub da pub_list
                    listPubs.remove(j);
                }

            }
        }

    }

}

class SortbyYear implements Comparator<Publication> { // class criada para ordenar a lista, neste caso por ano
                                                      // decrescente

    public int compare(Publication a, Publication b) {
        return b.getAno() - a.getAno(); // compara o valor de getAno() entre dois objectos da lista
    }
}

class SortbyCitation implements Comparator<Publication> { // class criada para ordenar a lista, neste caso por citações
                                                          // decrescente

    public int compare(Publication a, Publication b) {

        return b.getCitacoes() - a.getCitacoes(); // compara o valor de getPublication() entre dois objectos da lista

    }
}
