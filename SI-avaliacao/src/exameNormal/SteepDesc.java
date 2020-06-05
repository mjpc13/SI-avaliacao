package exameNormal;

import java.util.function.DoubleBinaryOperator;

public class SteepDesc {

    private Poly2 p;
    private double it, passo, erro;
    private double result = 0;

    public SteepDesc(Poly2 p, double it, double passo, double erro) {

        this.p = p;
        this.it = it;
        this.passo = passo;
        this.erro = erro;

    }

    public void compute() {

        int count = 0;
        double xi = it;

        iteracoes(xi, count);

    }

    private void iteracoes(double xi, int count) {

        double new_x = xi - passo * (p.computeDerivada(xi));

        count = count + 1;

        System.out.println("Iteracao " + count + " --> x" + count + " " + new_x);

        if (Math.abs(new_x - xi) >= erro) {

            iteracoes(new_x, count);

        }

        else {
            result = new_x;
        }

    }

    public String toString() {

        String str = "o valor aprox. de x para o qual " + p + " é minima é " + result;
        return str;

    }

}