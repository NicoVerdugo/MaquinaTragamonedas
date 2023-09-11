package org.example;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Interfaz extends JFrame {

    JButton iniciar = new JButton("Iniciar");
    JButton detener = new JButton("Stop");

    public static String img1="img1.jpg", img2="img2.jpg";
    public static String img3="img3.jpg";

    public static JLabel label1 = new JLabel();
    public static JLabel label2 = new JLabel();
    public static JLabel label3 = new JLabel();
    JLabel label1_1 = new JLabel("Casino Sogamoso");
    JLabel label2_2 = new JLabel("Traga Monedas");

    Icono_1 ic1 = new Icono_1();
    Icono_2 ic2 = new Icono_2();
    Icono_3 ic3 = new Icono_3();

    JPanel iconos = new JPanel();
    JPanel botones = new JPanel();
    JPanel encabezado = new JPanel();

    int x= 0;
    int credito = 0;

    public Interfaz(){
        JOptionPane.showMessageDialog(this, "Bienvenido a la maquina traga monedas, recuerde que cada tiro tiene un costo de 80 créditos.");

        boolean xContinue = false;
        do {
            String res = JOptionPane.showInputDialog(this, "Ingrese el numero de créditos");
            try {
                credito = Integer.parseInt(res);
                if (credito>0){

                    xContinue = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showInputDialog(this, "El valor no es válido", JOptionPane.ERROR_MESSAGE);
                if (credito<0) {
                    xContinue = false;
                }
            }
        } while (!xContinue);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JLabel label4 = new JLabel(String.valueOf(credito));
        JLabel label5 = new JLabel("Total Créditos:");

        label1.setIcon(new ImageIcon(img1));
        label2.setIcon(new ImageIcon(img2));
        label3.setIcon(new ImageIcon(img3));

        iconos.setLayout(new FlowLayout());
        iconos.add(label1);
        iconos.add(label2);
        iconos.add(label3);
        iconos.add(label5);
        iconos.add(label4);
        label4.setForeground(Color.BLUE);
        label5.setForeground(Color.BLUE);

        botones.setLayout(new GridLayout(2,2));
        botones.add(iniciar);
        botones.add(detener);
        //botones.add(label5);
        //botones.add(label4);
        iniciar.setForeground(Color.BLACK);
        iniciar.setBackground(Color.GREEN);
        detener.setBackground(Color.RED);

        encabezado.setLayout(new GridLayout(2,1));
        int panelX = (getWidth() - encabezado.getWidth() - getInsets().left - getInsets().right) / 2;
        int panelY = ((getHeight() - encabezado.getHeight() - getInsets().top - getInsets().bottom) / 2);

        encabezado.setLocation(panelX, panelY);
        encabezado.add(label1_1);
        encabezado.add(label2_2);
        encabezado.setBackground(Color.BLACK);

        label1_1.setForeground(Color.BLUE);
        label1_1.setFont(new Font("Arial",Font.BOLD+Font.ITALIC,25));
        label2_2.setForeground(Color.BLUE);
        label2_2.setFont(new Font("Arial",Font.BOLD+Font.ITALIC,25));

        add("Center",iconos);
        add("South",botones);
        add("North", encabezado);

        iconos.setBackground(Color.BLACK);

        iniciar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                credito = credito-80;
                label4.setText(String.valueOf(credito));
                if (credito>0) {
                    if (ic1.isAlive() == false) {
                        if (ic2.isAlive() == false) {
                            if (ic3.isAlive() == false) {
                                ic1 = new Icono_1();
                                ic2 = new Icono_2();
                                ic3 = new Icono_3();

                                ic1.activo = true;
                                ic2.activo = true;
                                ic3.activo = true;

                                ic1.start();
                                ic2.start();
                                ic3.start();
                                x = 1;
                            }
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(Interfaz.this, "Lo sentimos tus creditos son insuficientes para jugar, recarga para continuar jugando.");
                }
            }
        });
        detener.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {

                Gson gson = new Gson();
                String fichero = "";

                try (BufferedReader br = new BufferedReader(new FileReader("datos.json"))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        fichero += linea;
                    }

                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                Properties properties = gson.fromJson(fichero, Properties.class);
                if (credito > 0) {

                    if (x == 1) {
                        ic1.activo = false;
                        ic2.activo = false;
                        ic3.activo = false;
                        if (ic1.c == ic2.c && ic2.c == ic3.c && ic1.c == ic3.c) {
                            JOptionPane.showMessageDialog(Interfaz.this, "¡¡¡ Ganador ! ! ! " + properties.get("caso1"));
                            credito = credito + 500;
                        } else if (ic1.c == ic2.c) {
                            JOptionPane.showMessageDialog(Interfaz.this, " Por poco !!! Ganas " + properties.get("caso2"));

                            credito = credito + 80;
                        } else if (ic1.c == ic3.c) {
                            JOptionPane.showMessageDialog(Interfaz.this, " Por poco !!!  Ganas " + properties.get("caso3"));
                            credito = credito + 25;
                        } else if (ic2.c == ic3.c) {
                            JOptionPane.showMessageDialog(Interfaz.this, " Por poco !!!  Ganas " + properties.get("caso2"));
                            credito = credito + 80;
                        } else {
                            JOptionPane.showMessageDialog(Interfaz.this, " Perdiste :(  !!! " + properties.get("perdida"));
                            credito = credito - 500;
                        }
                    }
                } else {
                        JOptionPane.showMessageDialog(Interfaz.this, "Lo sentimos tus creditos son insuficientes para jugar, recarga para continuar jugando.");
                    }

                    label4.setText(String.valueOf(credito));
            }
        });
    }
}