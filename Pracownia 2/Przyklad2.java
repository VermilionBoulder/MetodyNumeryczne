/*
 * Przyklad wykorzystania metod iteracyjnych 
 */

public class Przyklad2 {

    public static void main(String[] args) {
        
        //tworzymy nowy obiekt klasy Przyklad
        Iteracyjne test = new Iteracyjne(10);
        
        /* SEKCJA 1 - badamy zbieznosc metody iteracji prostej */
        
        test.BadajZbieznosc();
        
        /* SEKCJA 2 - wyznaczamy dominujaca wartosc wlasna metoda potegowa */
        /*
        test.WartoscWlasna();
        */
        
        /* SEKCJA 3 - wyznaczamy ranking stron wykorzystujac metode Seidela */
        /*
        test.PageRankIteracja();
        */
        
        /* SEKCJA 4 - realizacja przykladowego zadania 
         * - sprawdzamy wplyw skali wektora poczatkowego na zbieznosc metody
         * - tu dla parametru skala = 5
         */
        /*
        Zadanie zad1 = new Zadanie(5.0);
        zad1.BadajZbieznosc();
        */
    }
}
