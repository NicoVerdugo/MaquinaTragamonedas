package org.example;

import javax.swing.*;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Icono_2 extends Thread {
    boolean activo=true;
    Random img = new Random();
    Random tiem = new Random();
    int i=0;
    int tiempo=0;
    int c=0;
    @Override
    public void run(){
        while(activo==true){
            i=img.nextInt(4);
            tiempo=tiem.nextInt(90);
            if(i==1){
                Interfaz.label2.setIcon(new ImageIcon(Interfaz.img1));
                c=1;
            }
            if(i==2){
                Interfaz.label2.setIcon(new ImageIcon(Interfaz.img2));
                c=2;
            }
            if(i==3){
                Interfaz.label2.setIcon(new ImageIcon(Interfaz.img3));
                c=3;
            }
            try{
                sleep(tiempo);
            }catch(InterruptedException e){}
        }
    }
}
