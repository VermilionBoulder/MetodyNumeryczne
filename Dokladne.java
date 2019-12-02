/**
 * Przyklad zastosowania metod dokladnych do rozwiazania zadanego ukladu rownan
 * oraz pomiaru czasu wykonywania sie operacji dla losowego ukladu rownan
 */
public class Dokladne {

    public static void main(String[] args) {
        // zadaje uklad definiujac macierz A oraz wektor wyrazow wolnych B z zadania 4b
        double[][] A1 = {{-1.0, -2.0, 1.0, -3.0}, {1.0, 6.0, -5.0, 11.0}, {0.0, -2.0, 3.0, -5.0}, {-3.0, -8.0, 6.0, -12.0}};
        double[] B1 = {-1.0, 1.0, 1.0, 2.0};
        
        //konstruuje nowy obiekt klasy Uklad
        Uklad u1 = new Uklad(A1, B1);
        
        //wyswietlam uklad na wyjsciu
        System.out.print("Zadany uklad:\n");
        u1.WyswietlUklad();
        
        //rozwiazuje uklad metoda Gaussa - w tym celu powoluje do istnienia nowy obiekt klasy Gauss
        Gauss przyklad1 = new Gauss(u1);
        
        //metoda Gaussa dziala w 2 krokach - najpierw przeprowadzam eliminacje elementow pod przekatna...
        przyklad1.Eliminacja();
        System.out.print("Uklad po eliminacji Gaussa:\n");
        przyklad1.Wyswietl();
        
        //2 krok to rozwiazanie ukladu trojkatnego, po czym moge sprawdzic rozwiazanie
        przyklad1.RozwiazTrojkatny();
        System.out.print("Rozwiazanie tego ukladu uzyskane metoda Gaussa:\n");
        przyklad1.WyswietlRozwiazanie();
         
        //aby sprawdzic rozwiazanie mozna wywolac ponisza metode
        przyklad1.SprawdzRozwiazanie(1);
        
        //to samo rozwiazanie uzyskamy metoda Gaussa-Jordana
        GaussJordan przyklad2 = new GaussJordan(u1);
        
        //metoda Gaussa-Jordana ogranicza sie wylacznie do eliminacji
        przyklad2.Eliminacja();
        System.out.print("Uklad po eliminacji Gaussa-Jordana:\n");
        przyklad2.Wyswietl();
        
        //to samo rozwiazanie uzyskamy metoda Cholesky'ego
        Cholesky przyklad3 = new Cholesky(u1);
        
        //metoda Choleskyego rozpoczyna sie od rozkladu A=LU
        przyklad3.Rozklad();
        System.out.print("Rozklad A na L i U:\n");
        przyklad3.Wyswietl();
        
        //nastepnie rozwiazujemy odpowiednie uklady trojkatne
        przyklad3.RozwiazTrojkatnyDolny();
        przyklad3.RozwiazTrojkatnyGorny();
        System.out.print("Rozwiazanie tego ukladu uzyskane metoda Cholesky'ego:\n");
        przyklad3.WyswietlRozwiazanie();
        
        //aby moc zastosowac metode Banachiewicza potrzebujemy ukladu symetrycznego
        double[][] A2 = {{9.0, -6.0, 0.0, 3.0}, {-6.0, 5.0, 2.0, -5.0}, {0.0, 2.0, 5.0, -7.0}, {3.0, -5.0, -7.0, 20.0}};
        double[] B2 = {-6.0, 5.0, 3.0, 3.0};
        
        //konstruuje nowy obiekt klasy Uklad
        Uklad u2 = new Uklad(A2, B2);
        
        //wyswietlam uklad na wyjsciu
        System.out.print("Zadany uklad symetryczny:\n");
        u2.WyswietlUklad();
        
        //metoda Banachiewicza rozpoczyna sie od rozkladu A=U^TU
        Banachiewicz przyklad4 = new Banachiewicz(u2);
        przyklad4.Rozklad();
        System.out.print("Wypisujemy pierwiastek z macierzy A, tj. macierz U:\n");
        przyklad4.Wyswietl();
        
        //nastepnie rozwiazujemy odpowiednie uklady trojkatne
        przyklad4.RozwiazTrojkatnyDolny();
        przyklad4.RozwiazTrojkatnyGorny();
        System.out.print("Rozwiazaniem tego ukladu jest wektor:\n");
        przyklad4.WyswietlRozwiazanie();
        
        //porownamy teraz czas rozwiazywania ukladu rownan o 1000 niewiadomych trzema metodami
        int n = 1000;
        Uklad u3 = new Uklad(n);
        //wypelniam powyzszy obiekt losowymi liczbami
        u3.LosujUklad();
        
        //tworzymy odpowiednio 3 obiekty
        Gauss przyklad5 = new Gauss(u3);
        GaussJordan przyklad6 = new GaussJordan(u3);
        Cholesky przyklad7 = new Cholesky(u3);
        
        //uruchamiamy stoper poprzez zapisanie czasu systemowego - mierzymy czas metody Gaussa
        long stoper = System.currentTimeMillis();
        przyklad5.Eliminacja();
        przyklad5.RozwiazTrojkatny();
        stoper = System.currentTimeMillis() - stoper;
        System.out.format("Czas rozwiazywania ukladu metoda Gaussa: %4.3f s\n\n", stoper/1000.0);
        
        //mierzymy czas metody Gaussa-Jordana
        stoper = System.currentTimeMillis();
        przyklad6.Eliminacja();
        stoper = System.currentTimeMillis() - stoper;
        System.out.format("Czas rozwiazywania ukladu metoda Gaussa-Jordana: %4.3f s\n\n", stoper/1000.0);
        
        //mierzymy czas metody Cholesky'ego
        stoper = System.currentTimeMillis();
        przyklad7.Rozklad();
        przyklad7.RozwiazTrojkatnyDolny();
        przyklad7.RozwiazTrojkatnyGorny();        
        stoper = System.currentTimeMillis() - stoper;
        System.out.format("Czas rozwiazywania ukladu metoda Cholesky'ego: %4.3f s\n\n", stoper/1000.0);
        
        //aby moc zmierzyc czas wykonywania sie metody Banachiewicza, potrzebujemy ukladu symetrycznego dodatnio okreslonego
        Uklad u4 = new Uklad(n);
        //wypelniam powyzszy obiekt losowymi liczbami
        u4.LosujUkladSymetrycznyDodatnioOkreslony();
        Banachiewicz przyklad8 = new Banachiewicz(u4);
        
        //mierzymy czas metody Banachiewicza
        stoper = System.currentTimeMillis();
        przyklad8.Rozklad();
        przyklad8.RozwiazTrojkatnyDolny();
        przyklad8.RozwiazTrojkatnyGorny();        
        stoper = System.currentTimeMillis() - stoper;
        System.out.format("Czas rozwiazywania ukladu metoda Banachiewicza: %4.3f s\n\n", stoper/1000.0);
    
    }
}
