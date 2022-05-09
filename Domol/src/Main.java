import java.util.Scanner;

public class Main {

    //ezzel íratjuk ki, hogy mely karakterlánc micsoda
    static StringBuilder kiirando = new StringBuilder();

    public static void main(String[] args) {

        /**
         * @author Békési Ádám
         * A kommenteket tanárúrank írtam.
         *
         * órai példa: 12domosi12:=(**domosi**){domosi}>=domosi$
         * <előjelnélküliegész><azonosító><értékadás><(**)kommentár><{}kommentár><nagyobbegyenlő><azonosító><eof>
         **/

        //bekérés
        Scanner be = new Scanner(System.in);
        System.out.println("Adjon meg egy szöveget:");
        String szoveg = be.next();

        //hogy mindig legyen end of file jel
        if (szoveg.charAt(szoveg.length() - 1) != '$')
            szoveg += "$";
        System.out.println(szoveg);

        //állapotokat tárolo változó
        int allapot = 1;

        //ez a ciklus megy végig a karaktereken
        for (int i = 0; i < szoveg.length(); ) {
            //a jelenlegi állapotnak be állitjuk a metódus által vissza addott értékre
            allapot = ujAllapot(szoveg.charAt(i), allapot);
            //ha end of file
            if (allapot == 21) {
                kiirando.append("<eof>");
            }
            //ha backup
            if (allapot == 3 || allapot == 5 || allapot == 20)
                i--;
                //hogyha nem vég állapot növeljük a ciklus változót
            else if (allapot == 0 || allapot == 1 || allapot == 2
                    || allapot == 4 || allapot == 6 || allapot == 8
                    || allapot == 9 || allapot == 10 || allapot == 12
                    || allapot == 14 || allapot == 17)
                i++;
        }
        //kiírjuk az eredményt
        System.out.println(kiirando);
    }

    // a szabályoknak megfelelően meg állapítja, hogy mi a kötkező állapot amibe lépünk
    private static int ujAllapot(char elem, int allapot) {
        switch (allapot) {
            case 1:
                if (Character.isAlphabetic(elem)) return 2;
                else if (Character.isDigit(elem)) return 4;
                else if (elem == ' ') return 1;
                else if (elem == '(') return 8;
                else if (elem == '{') return 6;
                else if (elem == ':') return 12;
                else if (elem == '<') return 14;
                else if (elem == '>') return 17;
                else if (elem == '$') return 21;
                return 19;
            case 2:
                if (Character.isAlphabetic(elem)) return 2;
                else if (Character.isDigit(elem)) return 2;
                else return 3;
            case 4:
                if (Character.isAlphabetic(elem)) return 5;
                else if (Character.isDigit(elem)) return 4;
                else return 5;
            case 6:
                if (elem == '}') return 7;
                if (elem == '$') return 19;
                return 6;
            case 8:
                if (elem == '*') return 9;
                if (elem == '$') return 19;
                return 20;
            case 9:
                if (elem == '*') return 10;
                if (elem == '$') return 19;
                return 9;
            case 10:
                if (elem == '*') return 10;
                else if (elem == ')') return 11;
                if (elem == '$') return 19;
                return 9;
            case 12:
                if (elem == '=') return 13;
                if (elem == '$') return 19;
                return 20;
            case 14:
                if (elem == '$') return 19;
                if (elem == '=') return 15;
                else if (elem == '>') return 16;
                return 20;
            case 17:
                if (elem == '$') return 19;
                if (elem == '=') return 18;
                return 20;

            //végállapotok és tokenek
            case 3:
                kiirando.append("<azonosító>");
                return 1;
            case 5:
                kiirando.append("<előjelnélküliegész>");
                return 1;
            case 7:
                kiirando.append("<{}komment>");
                return 1;
            case 11:
                kiirando.append("<(**)komment>");
                return 1;
            case 13:
                kiirando.append("<értékadás>");
                return 1;
            case 15:
                kiirando.append("<kissebbegyenlő>");
                return 1;
            case 16:
                kiirando.append("<nemegyenlő>");
                return 1;
            case 18:
                kiirando.append("<nagyobbegyenlő>");
                return 1;
            case 19:
            case 20:
                return 1;
        }
        return 0;
    }
}