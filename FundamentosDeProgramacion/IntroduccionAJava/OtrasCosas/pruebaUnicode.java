public class pruebaUnicode
{
    public static void main(String[] args)
    {
        String p = "niño € 𝒫"; //MATHEMATICAL SCRIPT CAPITAL P (HTML &#119979;)
        int caracter;
        int codePoint;
  
        System.out.println(p);
   
        for(int i=0; i<p.length(); i++)
        {
            caracter = p.charAt(i); //ñ es 241 o 0xf1
            System.out.printf("%d\t",caracter); 
        }
        System.out.println();
 
        for(int i=0; i<p.length(); i++)
        {
            caracter = p.charAt(i); //ñ es 241 o 0xf1
            System.out.printf("%c\t",caracter); 
        }
        System.out.println();

        for(int i=0; i< p.codePointCount(0, p.length() ); i++)
        {
            codePoint = p.codePointAt(i); //ñ es 241 o 0xf1
            System.out.printf("%d\t",codePoint); 
        }
        System.out.println();
  
        for(int i=0; i< p.codePointCount(0, p.length() ); i++)
        {
            codePoint = p.codePointAt(i); //ñ es 241 o 0xf1
            System.out.printf("%c\t",codePoint); 
        }
        System.out.println();
    }
}