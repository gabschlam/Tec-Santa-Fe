/*
    Listas Polinomios: Clase Polinomios
    Estructura de Datos
 
    Gabriel Schlam
*/

public class Polinomio {
    
    private double exp;
    private double coef;
    private Polinomio sig;

    public Polinomio(double exp, double coef) {
        this.exp = exp;
        this.coef = coef;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }

    public Polinomio getSig() {
        return sig;
    }

    public void setSig(Polinomio sig) {
        this.sig = sig;
    }

    @Override
    public String toString() {
        if(coef>=0)
            if(exp == 0)
                return "+ " + coef + " ";
            else if(exp == 1)
                return "+ " + coef + "x ";
            else
                return "+ " + coef + "x^" + exp + " ";
        else
            if(exp == 0)
                return coef + " ";
            else if(exp == 1)
                return coef + "x ";
            else
                return coef + "x^" + exp + " ";
    }
}
