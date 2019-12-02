/*
 * Klasa realizujaca metode potegowa
 */

import java.util.*;

public class MetodaPotegowa {
    
    Uklad u;                //macierz bedzie reprezentowana przez odpowiedni uklad
    int n;                  //rozmiar problemu
    double[] v_nast;        //wektor nastepnego przyblizenia wektora wlasnego
    double[] v;             //wektor aktualnego przyblizenia wektora wlasnego
    List<Double> lambda;    //lista kolejnych przyblizen najwiekszej wartosci wlasnej
    
    //Konstruktor zadajacy macierz oraz wektor startowy (samych jedynek)
    public MetodaPotegowa(Uklad uk) {
        n = uk.n;
        u = new Uklad(uk);
        v_nast = new double[n];
        v = new double[n];
        for(int i = 0; i < n; i++) v[i] = 1.0;
    }
    
    //Moteda wykonujaca zadana liczbe iteracji dla macierzy u.A
    public void Iteruj(int k){
        double wynik;
        lambda = new ArrayList<>();
        int iter = 0;
        while(iter < k) {
            for(int i = 0; i < n; i++){
                wynik = 0.0;
                for(int j = 0; j < n; j++) wynik += u.A[i][j]*v[j];
                v_nast[i] = wynik;
            }
            wynik = 0.0;
            for(int i = 0; i < n; i++) wynik += v_nast[i]/v[i];
            wynik /= n;
            lambda.add(wynik);
            System.out.format("%4.10f\n", wynik);
            System.arraycopy(v_nast, 0, v, 0, n);
            iter++;
        }
    }
    
    //Moteda wykonujaca iteracje do momentu, gdy kolejne dwa przyblizenia 
    //roznia sie o nie wiecej niz eps
    public void IterujA(double eps){
        double wynik, roznica;
        roznica = 100.0;
        lambda = new ArrayList<>();
        //wykonuje osobno pierwszy krok, aby miec pierwsze przyblizenie wartosci wlasnej
        for(int i = 0; i < n; i++){
            wynik = 0.0;
            for(int j = 0; j < n; j++) wynik += u.A[i][j]*v[j];
            v_nast[i] = wynik;
        }
        wynik = 0.0;
        for(int i = 0; i < n; i++) wynik += v_nast[i]/v[i];
        wynik /= n;
        lambda.add(wynik);
        System.out.format("%4.10f\n", wynik);
        System.arraycopy(v_nast, 0, v, 0, n);
        while(roznica > eps) {
            for(int i = 0; i < n; i++){
                wynik = 0.0;
                for(int j = 0; j < n; j++) wynik += u.A[i][j]*v[j];
                v_nast[i] = wynik;
            }
            wynik = 0.0;
            for(int i = 0; i < n; i++) wynik += v_nast[i]/v[i];
            wynik /= n;
            roznica = Math.abs(wynik - lambda.get(lambda.size() - 1));
            lambda.add(wynik);
            System.out.format("%4.10f\n", wynik);
            System.arraycopy(v_nast, 0, v, 0, n);
        }
    }
    
    //Metoda wypisujaca aktualny wektor wlasny i wartosc wlasna
    public void WyswietlRozwiazanie() {
        System.out.print("Wektor wlasny odpowieadajacy dominujacej wartosci wlasnej:\n");
        for(int i = 0; i < n; i++) System.out.format("%8.4e\t", v[i]);
        System.out.print("\n");
        System.out.format("Dominujaca wartosc wlasna: %f\n", lambda.get(lambda.size()-1));
        System.out.print("\n");

    }
}
