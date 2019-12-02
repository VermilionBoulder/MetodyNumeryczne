/**
 *  Klasa realizujaca metode iteracji Seidela
 */

import java.util.*;

public class IteracjaSeidela {
    
    Uklad u;                //uklad do rozwiazania
    int n;                  //rozmiar problemu
    double[] x;             //wektor biezacego rozwiazania
    double[] x_st;          //wektor poprzedniego rozwiazania
    List<Double> norma_x;   //lista norm 
    double[][] D;           //macierz D
    double[] C;             //wektor C
      
    //Konstruktor zadjacy uklad wejsciowy
    public IteracjaSeidela(Uklad uk) {
        n = uk.n;
        u = new Uklad(uk);
    }
    
    //Metoda obliczajaca macierz D oraz wektor C
    public void Przygotuj(){
        D = new double[n][n];
        C = new double[n];
        x = new double[n];
        x_st = new double[n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++) D[i][j] = - u.A[i][j] / u.A[i][i];
            D[i][i] = 0.0;
            C[i] = u.B[i] / u.A[i][i];
        }
    }
    
    //Metoda wykonujaca zadana liczbe iteracji, zaczyna iteracje od wektora X0
    //parametr norma - opisany jest w klasie Uklad
    //parametr wyswietl - czy maja byc wyswietlane kolejne iteracje
    public void Iteruj(int iteracje, int norma, int wyswietl, double[] X0) {
        int k = 0;
        System.arraycopy(X0, 0, x, 0, n);
        norma_x = new ArrayList<>();
        norma_x.add(u.NormaWektora(norma, x));
        System.out.format("%d \t %f\n",k, norma_x.get(k));
        while(k < iteracje){
            for(int i = 0; i < n; i++) {
                x[i] = C[i];
                for(int j = 0; j < n; j++) x[i] += D[i][j] * x[j];
            }
            k++;
            norma_x.add(u.NormaWektora(norma, x));
            System.out.format("%d \t %f\n",k, norma_x.get(k));
            if(wyswietl == 1) WyswietlRozwiazanie();
        }
    }
    
    //Metoda wykonujaca iteracje do momentu, gdy norma roznicy kolejnych przyblizen 
    //jest nie wieksza niz eps, iteracje zaczynamy od wektora X0
    //parametr norma jest opisany w klasie Uklad
    public void IterujA(double eps, int norma, double[] X0) {
        System.arraycopy(X0, 0, x, 0, n);
        System.arraycopy(X0, 0, x_st, 0, n);
        double blad = 1000.0;
        int k = 0;
        norma_x = new ArrayList<>();
        norma_x.add(u.NormaWektora(norma, x));
        System.out.format("%d \t %f\n",k, norma_x.get(k));
        while(blad > eps){
            k++;
            for(int i = 0; i < n; i++) {
                x[i] = C[i];
                for(int j = 0; j < n; j++) x[i] += D[i][j] * x[j];
            }  
            blad = u.NormaRoznicy(norma, x, x_st);
            norma_x.add(u.NormaWektora(norma, x));
            System.out.format("%d \t %f\n",k, norma_x.get(k));
            System.arraycopy(x, 0, x_st, 0, n);
        }
    }
    
    //Metoda wykonujaca iteracje wyznaczajc oszacowanie na podstawie twierdzenia
    //Banacha o punkcie stalym, zaczyna od wektora X0
    //parametr norma jest opisany w klasie Uklad
    public void IterujB(double eps, int norma, double[] X0) {
        int k = 0;
        System.arraycopy(X0, 0, x_st, 0, n);
        norma_x = new ArrayList<>();
        norma_x.add(u.NormaWektora(norma, x_st));
        System.out.format("%d \t %f\n",k, norma_x.get(k));
        //zadaje na poczatek liczbe iteracji n_min=1
        //wartosc n_min obliczam przy obliczaniu pierwszej iteracji
        int n_min = 1;
        //obliczam norme macierzy D
        double norma_D = u.NormaMacierzy(norma, D);
        //wykouje iteracje, o ile norma D jest mniejsza od 1
        if(norma_D < 1){
            //wykonuje iteracje
            while(k < n_min){
                for(int i = 0; i < n; i++) {
                    x[i] = C[i];
                    for(int j = 0; j < n; j++) x[i] += D[i][j] * x[j];
                }
                //przy pierwszej iteracji obliczam n_min wynikajace z tw. Banacha
                if(k == 0){
                    double norma_X1_X0;
                    norma_X1_X0 = u.NormaRoznicy(norma, x, x_st);
                    n_min = (int)Math.ceil(Math.log((eps*(1-norma_D))/norma_X1_X0)/Math.log(norma_D) - 1);
                }  
                k++;
                norma_x.add(u.NormaWektora(norma, x));
                System.out.format("%d \t %f\n",k, norma_x.get(k));
            }
        }
        else System.out.println("Metoda nie moze byc zastosowana, ||D|| >= 1");
    }
    
    //Metoda wyswietlajaca macierz D oraz wektor C
    public void Wyswietl() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) System.out.format("%4.4f\t",D[i][j]);
            System.out.format("\t | \t %4.4f\t",C[i]);
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    
    //Metoda wypisujaca aktualne rozwiazanie
    public void WyswietlRozwiazanie() {
        for(int i = 0; i < n; i++) System.out.format("%4.3f\t", x[i]);
        System.out.print("\n");
    }
    
    //Metoda sprawdzajaca poprawnosc aktualnego rozwiazania
    public void SprawdzRozwiazanie(int norma){
        u.SprawdzRozwiazanie(norma, x);
    }
}
