/*
 * Przyklad badania zbieznosci metody iteracji prostej 
 * w zaleznosci od wybranego wektora poczatkowego
 */

import java.util.Random;

public class Zadanie {
    int n = 100;            //rozmiar macierzy
    double skala;           //rzad wielkosci normy wektora poczatkowego
    int norma = 1;          //norma, z ktorej bede korzystal
    
    //Konstruktor
    public Zadanie(double sk) {
        skala = sk;
    }
    
    //Metoda badajaca zbieznosc
    public void BadajZbieznosc() {
        
        //definiuje obiekt rand, ktory posluzy do losowania wektora poczatkowego
        Random rand = new Random();
                      
        //zadaje uklad o rozmiarze n
        Uklad u1 = new Uklad(n);
        
        //losuje uklad symetryczny, dodatnio okreslony
        u1.LosujUkladSymetrycznyDodatnioOkreslony(0.5);
        
        //powoluje do istnienia obiekt klasy IteracjaProsta
        IteracjaProsta test1 = new IteracjaProsta(u1);
        
        //przygotowuje macierz D i wektor C potrzebne do iteracji
        test1.Przygotuj();
       
        //losuje wektor poczatkowy 
        double[] X0 = new double[n];
        for(int i = 0; i < n; i++) X0[i] = rand.nextDouble();
        
        //wyznaczam norme wylosowanego wektora poczatkowego i skaluje go
        double norma_X0 = u1.NormaWektora(norma, X0); 
        for(int i = 0; i < n; i++) X0[i] = Math.pow(10,skala) * X0[i] / norma_X0;
        
        //wykonuje iteracje startujac od wektora X0 do momentu, kiedy kolejne 
        //dwa przyblizenia rozwiazania bede odlegle o mniej niz 1.0E-7
        test1.IterujA(1.0E-7, norma, X0);
        
        //wyswietlam na konsoli norme wektora poczatkowego
        System.out.println("Norma wektora poczatkowego:" + u1.NormaWektora(norma, X0));
        
        //wyswietlam norme macierzy
        System.out.println("Norma wierszowa: " + u1.NormaMacierzy(0, test1.D));
        System.out.println("Norma kolumnowa: " + u1.NormaMacierzy(1, test1.D));
        System.out.println("Norma euklidesowa: " + u1.NormaMacierzy(2, test1.D));
        
        //sprawdzam dokladnosc uzyskanego rozwiazania
        test1.SprawdzRozwiazanie(norma);
    }
}
