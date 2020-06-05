package exameNormal;

import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ExamImpl extends UnicastRemoteObject implements ExamInterface {

    protected ExamImpl() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String createObjects(double a, double b, double c, double xi, double passo, double erro) throws Exception {
        // TODO Auto-generated method stub

        Poly2 poly = new Poly2(a, b, c);

        SteepDesc steep = new SteepDesc(poly, xi, passo, erro);

        steep.compute();

        return " " + steep;

    }

}