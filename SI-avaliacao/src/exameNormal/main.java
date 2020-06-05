package exameNormal;

public class main {
    public static void main(String[] args) {

        Poly2 poly = new Poly2(1, 1, 1);

        System.out.println("Vamos avaliar " + poly);

        SteepDesc steep = new SteepDesc(poly, 0, 0.2, 0.001);

        steep.compute();

        System.out.println(steep);

    }
}