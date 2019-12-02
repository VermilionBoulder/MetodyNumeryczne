/**
 * Przyklad zastosowania metod iteracyjnych do rozwiazania ukladow rownan
 * korzystamy z normy kolumnowej
 */
public class Iteracyjne {

    public static void main(String[] args) {
        // zadaje uklad definiujac macierz A1 oraz wektor wyrazow wolnych B1 z zadania 2a
        double[][] A1 = {{4.0, -1.0, -1.0}, {1.0, 3.0, -1.0}, {2.0, -1.0, 4.0}};
        double[] B1 = {-4.0, 12.0, 8.0};
        double[] X0 = {6.0, 4.0, 0.0};
        
        //konstruuje nowy obiekt klasy Uklad
        Uklad u1 = new Uklad(A1, B1);
        
        //wyswietlam uklad na wyjsciu
        System.out.println("Zadany uklad rownan:");
        u1.WyswietlUklad();
        
        System.out.println("---------------- ITERACJA PROSTA -----------------\n");
        //powoluje do istnienia obiekt klasy IteracjaProsta
        IteracjaProsta test1 = new IteracjaProsta(u1);
        
        //przygotowuje uklad do iteracji
        test1.Przygotuj();
        test1.Wyswietl();
        
        //wykonuje 10 pierwszych iteracji
        System.out.println("Pierwsze dziesiec przyblizen rozwiazania uzyskanych metoda iteracji prostej:");
        test1.Iteruj(10, 1, 1, X0);
        test1.SprawdzRozwiazanie(1);
        
        //wykonuje 20 i 30 iteracji 
        test1.Iteruj(20, 1, 0, X0);
        System.out.println("Rozwiazanie przyblizone uzyskane po 20 iteracjach:");
        test1.WyswietlRozwiazanie();
        test1.SprawdzRozwiazanie(1);
        test1.Iteruj(30, 1, 0, X0);
        System.out.println("Rozwiazanie przyblizone uzyskane po 30 iteracjach:");
        test1.WyswietlRozwiazanie();
        test1.SprawdzRozwiazanie(1);
        
        //wykonuje iteracje do momentu, kiedy kolejne przyblizenia rozwiazan
        //roznia sie co do normy nie wiecej niz o 0.001
        test1.IterujA(0.001, 1, X0);
        System.out.println("Rozwiazanie uzyskane w oparciu o kryterium stopu, eps = 0.001:");
        test1.WyswietlRozwiazanie();
        test1.SprawdzRozwiazanie(1);
        
        //wykonuje iteracje korzystajac z tw. Banacha
        test1.IterujB(0.001, 1, X0);
        System.out.println("Rozwiazanie uzyskane w oparciu o tw. Banacha, eps = 0.001:");
        test1.WyswietlRozwiazanie();
        test1.SprawdzRozwiazanie(1);
        
        System.out.println("---------------- ITERACJA SEIDELA -----------------\n");
        //powoluje do istnienia obiekt klasy IteracjaSeidela
        IteracjaSeidela test2 = new IteracjaSeidela(u1);
        
        //przygotowuje uklad do iteracji
        test2.Przygotuj();
        test2.Wyswietl();
        
        //wykonuje 10 iteracji metody Seidela
        System.out.println("Pierwsze dziesiec przyblizen rozwiazania uzyskanych metoda iteracji Seidela:");
        test2.Iteruj(10, 1, 1, X0);
        test2.SprawdzRozwiazanie(1);
        
        //wykonuje 20 i 30 iteracji 
        test2.Iteruj(20, 1, 0, X0);
        System.out.println("Rozwiazanie przyblizone uzyskane po 20 iteracjach:");
        test2.WyswietlRozwiazanie();
        test2.SprawdzRozwiazanie(1);
        test2.Iteruj(30, 1, 0, X0);
        System.out.println("Rozwiazanie przyblizone uzyskane po 30 iteracjach:");
        test2.WyswietlRozwiazanie();
        test2.SprawdzRozwiazanie(1);
        
        //wykonuje iteracje do momentu, kiedy kolejne przyblizenia rozwiazan
        //roznia sie co do normy nie wiecej niz o 0.001
        test2.IterujA(0.001, 1, X0);
        System.out.println("Rozwiazanie uzyskane w oparciu o kryterium stopu, eps = 0.001");
        test2.WyswietlRozwiazanie();
        test2.SprawdzRozwiazanie(1);
        
        //wykonuje iteracje korzystajac z tw. Banacha
        test2.IterujB(0.001, 1, X0);
        System.out.println("Rozwiazanie uzyskane w oparciu o tw. Banacha, eps = 0.001:");
        test2.WyswietlRozwiazanie();
        test2.SprawdzRozwiazanie(1);
        
        //porownanie czasow wykonywania sie obu metod dla ukladu rownan o 1000 niewiadomych
        System.out.println("--------------- POROWNANIE CZASOW ----------------\n");
        int n = 1000;
        Uklad u2 = new Uklad(n);
        double[] X_start = new double[n];
        u2.LosujUkladSymetrycznyDodatnioOkreslony();
        double eps = 1.0e-6;
        
        //tworzymy odpowiednio 2 obiekty
        IteracjaProsta test3 = new IteracjaProsta(u2);
        IteracjaSeidela test4 = new IteracjaSeidela(u2);
        
        //mierzymy czas metody iteracji prostej
        long stoper = System.currentTimeMillis();
        test3.Przygotuj();
        test3.IterujA(eps, 1, X_start);
        stoper = System.currentTimeMillis() - stoper;
        System.out.format("Czas rozwiazywania ukladu metoda iteracji prostej: %4.3f s\n\n", stoper/1000.0);
        test3.SprawdzRozwiazanie(1);
        
        //mierzymy czas metody Iteracji Seidela
        stoper = System.currentTimeMillis();
        test4.Przygotuj();
        test4.IterujA(eps, 1, X_start);
        stoper = System.currentTimeMillis() - stoper;
        System.out.format("Czas rozwiazywania ukladu metoda iteracji Seidela: %4.3f s\n\n", stoper/1000.0);
        test4.SprawdzRozwiazanie(1);
    }   
}
