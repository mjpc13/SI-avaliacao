package exameNormal;

// classe que trata da maioria dos prints, o código é explicito por si só, não acredito que seja necessário comentar

public class Poly2 {

    private double a, b, c;

    public Poly2(double a, double b, double c) {

        this.a = a;
        this.b = b;
        this.c = c;

    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double computeDerivada(double x) {

        return (2 * a * x + b);

    }

    public String toString() {

        String str = "f(x) = " + a + "x^2 + " + b + "x +" + c;
        return str;

    }

}