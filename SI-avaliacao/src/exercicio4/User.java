package exercicio4;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

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

}