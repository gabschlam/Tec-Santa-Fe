/*
    Listas Polinomios: Main
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Main {

    public static void main(String[] args) {
        Polinomio P = new Polinomio(0, 10);
        P.setSig(new Polinomio(2, 4));
        P.getSig().setSig(new Polinomio(40, 20));
        P.getSig().getSig().setSig(new Polinomio(86, 8));
        
        Polinomio Q = new Polinomio(1, 7);
        Q.setSig(new Polinomio(2, 10));
        Q.getSig().setSig(new Polinomio(86, -8));
        
        System.out.println("Polinomio P:");
        printPolinomio(P);

        System.out.println("Polinomio Q:");
        printPolinomio(Q);

        System.out.println("Resultado de suma de polinomios:");
        printPolinomio(SumaPoli(P,Q));

    }

    private static void printPolinomio(Polinomio pol) {
        System.out.print(pol);

        while(pol.getSig()!= null){
            pol = pol.getSig();
            System.out.print(pol);
        }
        System.out.println();
    }
    
    private static Polinomio SumaPoli(Polinomio P, Polinomio Q)
    {
        Polinomio R = null;
        
        if(P==null||Q==null){
            return R;
        }
        
        if(P.getExp() < Q.getExp()){
            R = P;
            R.setSig(SumaPoli(P.getSig(), Q));
        }
        else if(P.getExp() > Q.getExp()){
            R = Q;
            R.setSig(SumaPoli(P, Q.getSig()));
        }
        else if(P.getExp() == Q.getExp()){
            R = P;
            R.setCoef(P.getCoef() + Q.getCoef());
            if(R.getCoef() == 0){
                R = SumaPoli(P.getSig(), Q.getSig());
            }
            else {
                R.setSig(SumaPoli(P.getSig(), Q.getSig()));
            }
        }
        return R;
    }
}
