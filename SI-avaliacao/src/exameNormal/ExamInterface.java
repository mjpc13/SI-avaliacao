package exameNormal;

import java.rmi.Remote;
import java.util.*;

public interface ExamInterface extends Remote {

    public String createObjects(double a, double b, double c, double xi, double passo, double erro) throws Exception;

}