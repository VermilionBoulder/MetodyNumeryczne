/**
 *  Klasa odpowiedzialna za przechowywanie ukladu
 *  zawiera tez metody obliczania norm
 */

import java.util.Random;

public class Uklad {
    int n;              //rozmiar problemu
    double A[][];       //tablica przechowujaca macierz A ukladu
    double B[];         //tablica przechowujaca wektor wyrazow wolnych - B

    //Konstruktor zadajacy wylacznie rozmiar macierzy
    public Uklad(int nn){
        n = nn;
        A = new double[n][n];
        B = new double[n];
    }
    
    //Konstruktor przyjmujacy na wejsciu macierz A oraz wektor wyrazow wolnych
    public Uklad(double[][] tab_A, double[] tab_B){
        n = tab_B.length;
        A = new double[n][n];
        B = new double[n];
        for(int i = 0; i < n; i++) {
            System.arraycopy(tab_A[i], 0, A[i], 0, n);
        }
        System.arraycopy(tab_B, 0, B, 0, n);
    }
    
    //Konstruktor kopiujacy
    public Uklad(Uklad uk){
        n = uk.n;
        A = new double[n][n];
        B = new double[n];
        for(int i = 0; i < n; i++) {
            System.arraycopy(uk.A[i], 0, A[i], 0, n);
        }
        System.arraycopy(uk.B, 0, B, 0, n);
    }
    
    //Metoda losujaca macierz wspolczynnikow oraz wektor wyrazow wolnych
    public void LosujUklad(){
        Random rand = new Random();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j< n; j++) {
                A[i][j] = rand.nextDouble();
            }
            B[i] = rand.nextDouble();
        }
    }
    
    //Metoda losujaca uklad symetryczny i dodatnio okreslony
    public void LosujUkladSymetrycznyDodatnioOkreslony(){
        Random rand = new Random();
        double los, suma;
        for(int i = 0; i < n; i++) {
            for(int j = i+1; j< n; j++) {
                los = rand.nextDouble();
                A[i][j] = los;
                A[j][i] = los;
            }
            B[i] = rand.nextDouble();
            suma = 0.0;
            //Aby zapewnic sobie dominacje na przekatnej na przekatnej staje element silnie dominujacy w wierszu
            for(int j = 0; j < n; j++) suma += Math.abs(A[i][j])*(1+rand.nextDouble());
            A[i][i] = suma;
        }
    }
    
    //Metoda wyswietlajaca uklad
    public void WyswietlUklad(){
        for(int j = 0; j < n; j++) {
            for(int k = 0; k < n; k++) System.out.format("%4.3f\t",A[j][k]);
            System.out.format("\t | \t %4.3f \n",B[j]);
            }
        System.out.println();
    }
    
    //Metoda wyznaczajaca norme podanej macierzy M, przyjmuje parametr norma:
    //0 - norma nieskonczonosc
    //1 - norma kolumnowa
    //2 - norma euklidesowa
    public double NormaMacierzy(int norma, double[][] M) {
        double norm, suma;
        norm = 0.0;
        switch (norma) {
            case 0: for(int i = 0; i < n; i++) {
                        suma = 0.0;
                        for(int j = 0; j < n; j++) suma += Math.abs(M[i][j]);
                        if(suma > norm) norm = suma;
                    }
                    break;
            case 1: for(int i = 0; i < n; i++) {
                        suma = 0.0;
                        for(int j = 0; j < n; j++) suma += Math.abs(M[j][i]);
                        if(suma > norm) norm = suma;
                    }
                    break;
            case 2: suma = 0.0;
                    for(int i = 0; i < n; i++) {
                        for(int j = 0; j < n; j++) suma += M[i][j] * M[i][j];
                    }
                    norm = Math.sqrt(suma);
                    break;
            }     
        return norm;
    }
    
    //Metoda wyznaczajaca norme wektora, przyjmuje parametr norma:
    //0 - norma nieskonczonosc
    //1 - norma kolumnowa
    //2 - norma euklidesowa
    public double NormaWektora(int norma, double[] y) {
        double norm = 0.0;
        switch (norma) {
            case 0: norm = Math.abs(y[0]);   
                    for(int i = 1; i < y.length; i++) if(Math.abs(y[i]) > norm) norm = Math.abs(y[i]);
                    break;
            case 1: for(int i = 0; i < y.length; i++) norm += Math.abs(y[i]);
                    break;
            case 2: for(int i = 0; i < y.length; i++) norm += y[i] * y[i];
                    norm = Math.sqrt(norm);
                    break;
        }
        return norm;
    }
    
    //Metoda wyznaczajaca norme roznicy dwoch wektorow, argument norma - j.w.
    public double NormaRoznicy(int norma, double[] x1, double[] x2){
        double[] x0 = new double[x1.length];
        for(int i = 0; i < x1.length; i++) x0[i] = x1[i] - x2[i];
        return NormaWektora(norma, x0);
    }
    
    //Metoda wyznaczajaca norme odchylenia iloczynu Ax od wektora wyrazow wolnych b
    public void SprawdzRozwiazanie(int norma, double[] x) {
        double suma;
        double[] Ax = new double[n];
        for(int i = 0; i < n; i++){
            suma = 0.0;
            for(int j = 0; j < n; j++) suma += A[i][j]*x[j];
            Ax[i] = suma;
        }
        System.out.println("Blad rozwiazania: " + NormaRoznicy(norma, Ax, B) + "\n");
    }
}
