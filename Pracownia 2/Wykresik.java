/**
 *  Klasa realizujaca ramke wykresu
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

public class Wykresik extends JFrame {
      
    //Konstruktor przyjmujacy na wejsciu liste danych, miejsce, od ktorego dane maja byc wyswietlone oraz tytul
    public Wykresik(List dane, int n0, String opis){
        super(opis);
        JPanel panel;
        panel = new Szablon1(dane, n0);
        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
     //Konstruktor przyjmujacy na wejsciu dwie listy danych, miejsce, od ktorego dane maja byc wyswietlone oraz tytul
    public Wykresik(List dane1, List dane2, int n0, String opis){
        super(opis);
        JPanel panel;
        panel = new Szablon2(dane1, dane2, n0);
        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}