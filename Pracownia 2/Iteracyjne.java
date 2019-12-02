/*
 * Klasa zawiera (poza konstruktorem) trzy metody
 * - BadajZbieznosc - bada zbieznosc wybranej metody w zaleznosci od wybranego parametru
 * - WartoscWlasna - metoda wyznaczajaca dominujaca wartosc wlasna macierzy metoda potegowa
 * - PageRankIteracja - algorytm Google Page Rank z zastosowaniem iteracji Seidela do rozwiazania odpowiedniego ukladu
 */

public class Iteracyjne {
    int n;          //rozmiar macierzy
    
    //Konstruktor
    public Iteracyjne(int nn) {
        n = nn;
    }
    
    //Metoda do badania zbieznosci wybranej metody
    public void BadajZbieznosc() {
        //wybieram rodzaj normy - np. norme wierszowa
        int norma = 0;
        //zadaje uklad o rozmiarze n
        Uklad u1 = new Uklad(n);
        //losuje uklad symetryczny, dodatniookreslony z parametrem alfa = 0.2 
        //parametr alfa (z przedzialu (0; 1) odpowiada za norme macierzy - 
        // - im wiekszy parametr alfa, tym mniejsza norma macierzy 
        u1.LosujUkladSymetrycznyDodatnioOkreslony(0.2);
        //rozwiazuje uklad z wykorzystaniem metody iteracji prostej A
        IteracjaProsta test1 = new IteracjaProsta(u1);
        //wyznaczam macierz D i wektor C
        test1.Przygotuj();
        //wyswietlam norme macierzy
        System.out.format("Norma macierzy: %f\n",u1.NormaMacierzy(norma,test1.D));
        //okreslam wektor poczatkowy jako zerowy
        double X0[] = new double[n];
        //wykonuje iteracje do momentu, gdy norma roznicy kolejnych rozwiazan jest mniejsza niz 0.00001
        test1.IterujA(1.0E-5, norma, X0);
        //tworze wykres obrazujacy zbieznosc, rysuje normy wektorow od 0 iteracji
        Wykresik wykresik = new Wykresik(test1.norma_x, 0, "Iteracja prosta - zbieznosc ciagu norm");
        //sprawdzam blad rozwiazania, tj. norme roznicy AX - B
        test1.SprawdzRozwiazanie(norma);
    }
    
    //Metoda wyznaczajaca dominujaca wartosc wlasna
    public void WartoscWlasna() {
        //zadaje uklad o rozmiarze n
        Uklad u1 = new Uklad(n);
        u1.LosujUklad();
        //tworze nowy obiekt klasy MetodaPotegowa
        MetodaPotegowa test2 = new MetodaPotegowa(u1); 
        //wykonuje metode potegowa do momentu, gdy kolejne wartosci wlasne beda sie roznily o mniej niz 1.0E-10
        test2.IterujA(1.0E-10);
        //wyznaczam wykres poczawszy od 3 przyblizenia wartosci wlasnej
        Wykresik wykresik = new Wykresik(test2.lambda, 3, "Wykres kolejnych przyblizen wartosci wlasnej");
        //wyswietlam rozwiazanie (wektor wlasny i wartosc wlasna)
        test2.WyswietlRozwiazanie();
    }
    
    //Metoda wyznaczajaca ranking stron z wykorzystaniem iteracji Seidela 
    public void PageRankIteracja(){
        //wybieram rodzaj normy
        int norma = 1;
        //definiuje liczbe iteracji
        int N = 100;
        //zadaje uklad o rozmiarze n
        PageRank P = new PageRank(n);
        //losuje macierz przejscia podajac parametr gamma - prawdopodobienstwo wystepowania odnosnika do i-tej strony na danej stronie  
        P.Losuj(0.4);
        
        /* Rozwiazuje problem metoda iteracji Seidela wykonujac zadana liczbe iteracji
         * Najpierw odejmuje jedynki na przekatnej, potem usuwam ostatnie z rownan,
         * a ostatnia z niewiadomych przyjmuje za 1.0
         */
        P.PrzygotujDoIteracji();
        //powoluje do istnienia obiekt klasy IteracjaSeidela
        IteracjaSeidela test = new IteracjaSeidela(P.u);
        //przygotowuje macierz D i wektor C
        test.Przygotuj();
        //okreslam wektor poczatkowy jako zerowy
        double X0[] = new double[n];
        //wykonuje zadana liczbe iteracji
        test.Iteruj(N, norma, 0, X0);
        //wyswietlam rozwiazanie
        System.out.print("Wektor wlasny: ");
        test.WyswietlRozwiazanie();
        //wyswietlam blad rozwiazania
        test.SprawdzRozwiazanie(norma);
        //wyswietlam ranking stron
        System.out.print("Ranking stron: ");
        P.Ranking(test.x);
    }
}
