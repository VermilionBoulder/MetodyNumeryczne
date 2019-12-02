/**
 * Przyklad rozwiazania problemu porowania dwoch metod numerycznych 
 * - tu metod sortowania listy o zadanej dlugosci
 */

public class Przyklad1 {

    public static void main(String[] args) {
        
        /*SEKCJA 1 - Przyklad sortowania tablic o zadanej dlugosci  
        * ustalam minimalna dlugosc listy, ktora da niezerowy czas
        * oraz maksymalna dlugosc listy, dla ktorej sortowanie nie bedzie trwalo 
        * zbyt dlugo (mniej niz 1 s) */ 
        
        int n = 20;                                         //okreslam dlugosc listy
        long pomiar;                                        //zmienna przechowujaca czas systemowy
        Sortowania test1 = new Sortowania(n);               //powoluje do istnienia obietk klasy Sortowania
        
        //testuje metode sortowania przez wstawianie
        test1.Losuj(n);                                     //losuje elementy listy
        test1.WyswietlListe(n, 0);                          //wyswietlam liste
        pomiar = System.currentTimeMillis();                //wlaczam stoper
        test1.SortujPrzezWstawianie(n);                     //wywoluje metode sortujaca liste
        pomiar = System.currentTimeMillis() - pomiar;       //zatrzymuje stoper
        test1.WyswietlListe(n, 1);                          //wyswietlam posortowana liste
        System.out.format("%4.3f s\n\n", pomiar / 1000.0);  //wystwietlam czas sortowania
        
        //testuje metode sortowania przez wybieranie
        pomiar = System.currentTimeMillis();                //wlaczam stoper
        test1.SortujPrzezWybieranie(n);                     //wywoluje metode sortujaca liste
        pomiar = System.currentTimeMillis() - pomiar;       //zatrzymuje stoper
        test1.WyswietlListe(n, 2);                          //wyswietlam posortowana liste
        System.out.format("%4.3f s\n\n", pomiar / 1000.0);  //wystwietlam czas sortowania
        
                
        /*SEKCJA 2 - Badamy zlozonosc obliczeniowa obu metod dla M = 7, N = 77, 
        * n_min = 5000, n_max = 10000*/
        /*
        int M = 7;
        int N = 77;
        int n_min = 5000;
        int n_max = 10000;
        Sortowania test2 = new Sortowania(M, N, n_max);
        System.out.println("Sortowanie przez wstawianie:");
        test2.BadajZlozonosc(1, n_min);
        System.out.println("Sortowanie przez wybieranie:");
        test2.BadajZlozonosc(2, n_min);
        */
        /*
        //SEKCJA 3 - Porownujemy obie metody dla M = 7, N = 77, n_max = 10000
        int M = 7;
        int N = 77;
        int n_max = 10000;
        Sortowania test3 = new Sortowania(M, N, n_max);
        test3.PorownajMetody();
        */
    }
    
    
}
