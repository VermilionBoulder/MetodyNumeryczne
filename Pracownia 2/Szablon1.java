/**
 *  Klasa rysujaca szablon wykresu dla jednej zmiennej
 */

import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

public class Szablon1 extends JPanel {
    
    int width;
    int height; 
    int MAX_n;
    int MIN_n;
    double MAX_T;
    double MIN_T;
    int[] n;        //tablica numerow iteracji
    double[] T;     //tablica danych
    int N;          //liczba wyswietlanych danych 
    
    /*Konstruktor przyjmujacy:
     *  dane - lista danych
     *  n0 - numer pierwszej wyswietlanej danej
    */
    public Szablon1(java.util.List dane, int n0) {
        width = 800;
        height = 600;
        N = dane.size() - n0;
        n = new int[N];
        T = new double[N];
        MIN_n = n0;
        MAX_n = dane.size();
        for(int i = 0; i < N; i++) {
            n[i] = n0 + i;
            T[i] = (double) dane.get(n[i]);
        }
        setPreferredSize(new Dimension(width,height));
    }
    
    public void getMax(){
        MAX_T = T[0];
        MIN_T = T[0];
        for(int i = 1; i < N; i++) {
            if(T[i] > MAX_T) MAX_T = T[i];
            if(T[i] < MIN_T) MIN_T = T[i];
        }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        super.setBackground(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        int dist = 10;
        
        //Osie
        Line2D osx = new Line2D.Double(4*dist, height-4*dist, width-2*dist, height-4*dist); 
        Line2D osxau = new Line2D.Double(width-3*dist, height-5*dist,width-2*dist, height-4*dist); 
        Line2D osxad = new Line2D.Double(width-3*dist, height-3*dist,width-2*dist, height-4*dist); 
        Line2D osy = new Line2D.Double(5*dist, height-3*dist, 5*dist, 2*dist);
        Line2D osyal = new Line2D.Double(4*dist, 3*dist, 5*dist, 2*dist);
        Line2D osyar = new Line2D.Double(6*dist, 3*dist, 5*dist, 2*dist);   
        g2d.draw(osx);
        g2d.draw(osxau);
        g2d.draw(osxad);
        g2d.draw(osy);
        g2d.draw(osyal);
        g2d.draw(osyar);
        
        //Podzialka
        double dx = (width - 9*dist)/5.0;
        double dy = (height - 8*dist)/5.0;
        Line2D podzx = new Line2D.Double();
        Line2D podzy = new Line2D.Double();
        for(int i = 1; i < 6; i++){
            podzx.setLine(5*dist + i*dx, height-3.5*dist, 5*dist + i*dx, height-4.5*dist);
            podzy.setLine(4.5*dist, height-4*dist - i*dy, 5.5*dist, height-4*dist - i*dy);
            g2d.draw(podzx);
            g2d.draw(podzy);
        }
        
        //Opisy podzialki
        getMax();
        int dn = (int)Math.ceil((MAX_n-MIN_n)/5.0);
        double dt = (MAX_T-MIN_T)/5.0;
        for(int i = 0; i < 6; i++) g2d.drawString(Integer.toString(MIN_n + dn*i), (float)(4.5*dist + i*dx), height-2*dist);
        for(int i = 0; i < 6; i++) g2d.drawString(String.format("%4.2f",MIN_T + dt*i), 0, (float)(height-3.5*dist-i*dy));
                
        //Rysujemy wykres 
        Ellipse2D punkt = new Ellipse2D.Double();
        double scn = (width-9.0*dist)/(MAX_n-MIN_n);
        double scT = (height-8.0*dist)/(MAX_T-MIN_T);
        g2d.setPaint(Color.RED);
        for(int i = 0; i<N; i++){
            punkt.setFrame(scn*(n[i]-MIN_n) + 5*dist-3,height-4*dist-scT*(T[i]-MIN_T)-3,6,6);
            g2d.fill(punkt);
            g2d.draw(punkt); 
        }   
    }
}

