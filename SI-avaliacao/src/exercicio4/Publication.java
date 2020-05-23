package exercicio4;

import java.io.Serializable;
import java.util.ArrayList;

public class Publication implements Serializable {

    private static final long serialVersionUID = -8919133980077941736L; // auto-generated serialVersionUID

    // variáveis que cada publicação tem
    private String titulo, revista, volume, paginas;
    private int DOI;
    private int ano, numero, citacoes;
    private ArrayList<String> listaAutores = new ArrayList<>();

    public Publication(ArrayList<String> autores, String titulo, int ano, String revista, String volume, int numero,
            String pagina, int citacoes) {

        this.listaAutores = autores;
        this.titulo = titulo;
        this.revista = revista;
        this.volume = volume;
        this.ano = ano;
        this.numero = numero;
        this.paginas = pagina;
        this.citacoes = citacoes;

        // para fazer o DOI

        String id = ""; // id vai ser a String usada para criar o hashCode (que basicamente vai
                        // funcionar como um Identificador unico de uma publicação)

        for (String str : autores) { // adiciona todos os autores ao id
            id = id + str;
        }
        id = id + titulo + revista + volume + pagina + String.valueOf(ano); // adiciona algumas das restantes variáveis
        this.DOI = id.hashCode(); // cria o DOI (que vai ser o hash code da string id)

    }

    public int getCitacoes() {
        return citacoes;
    }

    public void setCitacoes(int citacoes) {
        this.citacoes = citacoes;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return the revista
     */
    public String getRevista() {
        return revista;
    }

    /**
     * @return the volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * @return the paginas
     */
    public String getPaginas() {
        return paginas;
    }

    /**
     * @return the ano
     */
    public int getAno() {
        return ano;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @return the listaAutores
     */
    public ArrayList<String> getListaAutores() {
        return listaAutores;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @param paginas the paginas to set
     */
    public void setPaginas(String paginas) {
        this.paginas = paginas;
    }

    /**
     * @param revista the revista to set
     */
    public void setRevista(String revista) {
        this.revista = revista;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the dOI
     */
    public int getDOI() {
        return DOI;
    }

    public String toString() { // Returns a string representation of the object

        String print = "";

        for (String string : listaAutores) {
            print = print + string + "; ";
        }

        print += titulo + ", " + String.valueOf(ano) + ", " + revista + ", " + volume + "(" + String.valueOf(numero)
                + "), " + paginas + ", (Times Cited = " + citacoes + ").";

        return print;
    }
}