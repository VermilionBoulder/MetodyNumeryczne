/*
 * Klasa realizujaca uproszczony algorytm Google Page Rank
 */

import java.util.Random;

public class PageRank extends Uklad {
    
    double[] lambda;    //wektor przechowujacy wartosci kolejnych przyblizen dominujacej wartosci wlasnej
    Uklad u;            //obiekt przechowujacy zmodyfikowany uklad, 
                        //ktory bedziemy rozwiazywac metodami iteracji prostej lub Seidela
       
    //Konstruktor zadajacy macierz przejsc o zadanym rozmiarze
    public PageRank(int nn){
        n = nn;
        A = new double[n][n];
        B = new double[n];
    }
    
    //Metoda losujaca macierz przejscia o zadanym prawdopodobienstwie wystepowania linku - gamma 
    //Parametr gamma przyjmuje wartosci z przedzialu (0; 1)
    public void Losuj(double gamma){
        Random rand = new Random();
        double los, licznik;
        for(int i = 0; i < n; i++){
            //wypelniam macierz przejscia wstawiajac jedynki tam, gdzie wystepuje link
            for(int j = 0; j< n; j++) {
                los = rand.nextDouble();
                if(los < gamma) A[j][i] = 1.0;
                else A[j][i] = 0.0;
            }
            //zliczam jedynki w kolumnie
            licznik = 0.0;
            for(int j = 0; j< n; j++) licznik += A[j][i];
            //wyznaczam kolumne macierzy P
            if(licznik == 0) for(int j = 0; j < n; j++) A[j][i] = 1.0/n;
            else for(int j = 0; j < n; j++) A[j][i] /= licznik;
            //zadaje wektor N, ktory bedzie wektorem startowym dla metody potegowej
            B[i] = 1.0;
        }
    }
    
    //Metoda losujaca macierz przejscia o domyslnej wartosci parametru gamma rownej 0.5
    public void Losuj(){
        Losuj(0.5);
    }
    
    //Tworze nowy uklad, ktory bedzie rozwiazywany iteracja prosta lub Seidela
    //- odejmuje jedynki na przekatnej
    //- usuwam ostatnie rownanie
    //- przyjmuje ostatnia wspolrzedna rowna 1.0
    public void PrzygotujDoIteracji(){
        u = new Uklad(n-1);
        for(int i = 0; i < n-1; i++) {
            System.arraycopy(A[i], 0, u.A[i], 0, n-1);
            u.A[i][i] -= 1.0;
            u.B[i] = -A[i][n-1];
        }
    }
    
    //Metoda wyswietlajaca ranking stron dla wektora wag
    public static void Ranking(double[] v){
        int m = v.length;
        int k, l;
        double max;
        //tworze tablice numerow stron
        int[] ind = new int[m];
        for(int i = 0; i < m; i++) ind[i] = i + 1;
        //sortowanie listy - sortowanie przez wybieranie -
        // - od najwiekszego elementu do najmniejszego
        for(int i = 0; i < m-1; i++) {
            max = v[i];
            k = i;
            for(int j = i+1; j < m; j++) {
                if(v[j] > max) {
                    max = v[j];
                    k = j;
                }
            }
            v[k] = v[i];
            v[i] = max;
            l = ind[k];
            ind[k] = ind[i];
            ind[i] = l;
        }
        for(int i = 0; i < m; i++) System.out.print(ind[i] + "\t");
        System.out.print("\n");
    }
    
    //Tworzenie rankingu dla wyniku z iteracji prostej - ostatnia wspolrzedna rowna 1
    public static void Ranking1(double[] v) {
        int m = v.length;
        double[] v1 = new double[m+1];
        System.arraycopy(v, 0, v1, 0, m);
        v1[m] = 1.0;
        Ranking(v1);
    }
}

