import java.util.Scanner;

public class Permutation
{
    public static void main(String[] args)
    {
        String palabra;
        int n;
        Permutation permutation = new Permutation();

        Scanner lector = new Scanner(System.in);

        System.out.println("Introduce la palabra:");
        palabra = lector.nextLine();

        n = palabra.length();
        permutation.anagramas(palabra, 0, n-1);
    }

    public void anagramas(String palabra, int l, int r)
    {
        if (l == r)
            System.out.println(palabra);
        else
        {
            for (int i = l; i <= r; i++)
            {
                palabra = swap(palabra,l,i);
                anagramas(palabra, l+1, r);
                palabra = swap(palabra,l,i);
            }
        }
    }

    public String swap(String palabra, int l, int r)
    {
        char temp;
        char[] charArray = palabra.toCharArray();
        temp = charArray[l];
        charArray[l] = charArray[r];
        charArray[r] = temp;
        return String.valueOf(charArray);
    }
}