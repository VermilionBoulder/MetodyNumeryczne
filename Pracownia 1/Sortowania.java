/*
 * Klasa realizujaca wszystkie metody zwiazane z sortowaniem, 
 * porownaniem metod i badaniem zlozonosci obliczeniowej
 */

import java.util.Random;

public class Sortowania {
    int[] lista;    //tablica, ktora bedzie sortowana
    int[] lista1;   //tablica, ktora bedzie sortowana przez wstawianie
    int[] lista2;   //tablica, ktora bedzie sortowana przez wybieranie
    int n_max;      //maksymalny rozmiar listy
    double[] T;     //tablica czasow do badania zbieznosci
    double[][] T2;  //tablica czasow do porownywania metod
    int[] rozm;     //tablica rozmiarow list
    int M;          //liczba pomiarow w ramach jednego testu
    int N;          //liczba testow do wykonania
    
    //Konstruktor zadajacy wylacznie dlugosc listy
    Sortowania(int nn){
        n_max = nn;
        lista = new int[n_max];
        lista1 = new int[n_max];
        lista2 = new int[n_max];
    } 
    
    //Konstruktor zadajacy parametry eksperymentu
    Sortowania(int MM, int NN, int nn){
        n_max = nn;
        lista = new int[n_max];
        lista1 = new int[n_max];
        lista2 = new int[n_max];
        M = MM;
        N = NN;
        rozm = new int[N];          //definiuje tablice dlugosci list
        T = new double[N];          //definiuje tablice srednich czasow sortowania
        T2 = new double[N][2];      //definiuje tablice srednich czasow sortowania dla 2 metod
    }
    
    //Metoda losujaca n pierwszych elementow listy
    //Losujemy z przedzialu [0; 10 * n]
    public void Losuj(int n){
        Random rand = new Random();
        for(int i =  0; i < n; i++) {
            lista[i] = rand.nextInt(10 * n);
            lista1[i] = lista[i];
            lista2[i] = lista[i];
        }
    }
    
    //Metoda mierzaca czas M sortowan losowych tablic o n elementach wybrana metoda
    public double MierzCzas(int n, int metoda){
        double czas = 0.0;
        long pomiar;
        
        //mierze czas wykonywania sie M sortowan
        for(int i = 0; i < M; i++){
            this.Losuj(n);                                      //losuje elementy listy
            pomiar = System.currentTimeMillis();                //wlaczam stoper
            switch(metoda){                                     //wybieram metode, ktora bedzie wykorzystana
                case 1: SortujPrzezWstawianie(n);
                        break;
                case 2: SortujPrzezWybieranie(n);
                        break;
            }
            pomiar = System.currentTimeMillis() - pomiar;       //zatrzymuje stoper
            czas += pomiar;
        }
        return czas / (M * 1000.0);                             //podaje wynik w sekundach
    }
    
    /*Metoda badajaca zlozonosc sortowania przez wstawianie dla N roznych rozmiarow listy
    * dodatkowy parametr n_min okreslajacy minimalna dlugosc listy pozwala uniknac
    * problemow z wyznaczaniem stopnia zlozonosci (korzystamy z logarytmow 
    * - czasy nie moga byc zerowe)*/
    public void BadajZlozonosc(int metoda, int n_min){        
        double krok = (n_max - n_min) / (N - 1.0);              //wyznaczam krok zmiany dlugosci listy
        for(int i = 0; i < N; i++){
            rozm[i] = (int) Math.round(n_min + i * krok);       //najmniejszy rozmiar listy to n_min
            T[i] = this.MierzCzas(rozm[i], metoda);             //zapisuje srednie czasy do tablicy
            System.out.format("%d \t %4.3f\n", rozm[i], T[i]);  //wyswietlam rozmiar i czas obliczen na wyjsciu
        }
        //tworze wykres obrazujacy zlozonosc danej metody
        Wykresik wykresik;
        switch(metoda){                                     //wybieram metode, ktora bedzie wykorzystana
                case 1: wykresik = new Wykresik(N, rozm, T, "Wstawianie");
                        break;
                case 2: wykresik = new Wykresik(N, rozm, T, "Wybieranie");
                        break;
            }
    }
    
    //Metoda porownujaca dwie metody sortowania
    public void PorownajMetody(){
        double krok = 1.0 * n_max / N;           //wyznaczam krok zmiany dlugosci listy
        System.out.println("Rozmiar | Wstawianie | Wybieranie");
        for(int i = 0; i < N; i++){
            rozm[i] = rozm[i] = (int) Math.round((i + 1) * krok);                           //najmniejszy rozmiar listy to krok
            T2[i][0] = this.MierzCzas(rozm[i], 1);              //zapisuje srednie czasy do tablicy
            T2[i][1] = this.MierzCzas(rozm[i], 2);
            System.out.format("%d \t %4.3f \t %4.3f\n", rozm[i], T2[i][0], T2[i][1]);  //wyswietlam rozmiar i czas obliczen na wyjsciu
        }
        //tworze wykres umozliwiajacy porownanie obu metod
        Wykresik wykresik = new Wykresik(N, rozm, T2, "Porownanie metod sortowania");
    }
    
    //Metoda sortujaca przez wstawianie
    public void SortujPrzezWstawianie(int n){
        int elem;                               //wybrany element listy
        int k;                                  //iterator
        for(int i = 1; i < n; i++){
            elem = lista1[i];
            k = 0;
            //przegladma dolna czesc listy
            while(lista1[k] < elem) k++;
            //przestawiam elementy posortowanej listy, by wstawic wybrany element listy
            for(int j = i; j > k; j--) lista1[j] = lista1[j-1];   
            lista1[k] = elem;
        }
    }
    
    //Metoda sortujaca przez wybieranie
    public void SortujPrzezWybieranie(int n){
        int elem;                               //najmniejszy z elementow nieposortowanej czesci listy
        int k;                                  //indeks tego najmniejszego elementu
        for(int i = 0; i < n; i++){
            elem = lista2[i];
            k = i;
            for(int j = i + 1; j < n; j++){
                if(lista2[j] < elem) {
                    elem = lista2[j];
                    k = j;
                }
            }
            lista2[k] = lista2[i];
            lista2[i] = elem;
        }
    }
    
    //Metoda wyswietlajaca liste o dlugosci n
    public void WyswietlListe(int n, int typ){
        System.out.print("[");
        switch(typ){
            case 1:
               for(int i = 0; i < n - 1; i++)
                    System.out.format("%d, ", lista1[i]);
                System.out.format("%d", lista1[n-1]);
                System.out.println("]"); 
                break;
            case 2:
                for(int i = 0; i < n - 1; i++)
                    System.out.format("%d, ", lista2[i]);
                System.out.format("%d", lista2[n-1]);
                System.out.println("]"); 
                break;
            default:  
                for(int i = 0; i < n - 1; i++)
                    System.out.format("%d, ", lista[i]);
                System.out.format("%d", lista[n-1]);
                System.out.println("]");
                break;
        }
    } 
}
