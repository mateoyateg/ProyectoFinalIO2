package Controlador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class CreadorBotones implements ActionListener {
    
    //Se crean los labels, botones, Combo Box y demás elementos graficos que serán utilizados en el programa
    JLabel lbTitulo, lbNaturaleza, lbInfoVariables, lbInfoRestricciones, lbFuncionObjetivo, lbRestricciones, 
            lbTituloConfirmacion, lbProblemaGeneral, lbNombres, lbResultados, lbsTableros, lbInformacionJava, lbSolucion;
    JLabel[] lbsFuncionObjetivo;
    JLabel[][] lbsRestricciones;
    JButton btSiguiente, btEnviar, btConfirmacion;
    JComboBox cbMaximizarMinimizar, cbRestricciones, cbVariables;
    JComboBox[] cbSignos;
    JTextField[] tfFuncionObjetivo, tfLD;
    JTextField[][] tfRestricciones;
    
    //Se crean los objetos que serán utilizados para el metodo de Egon Balas
    MetodoAditivo egonBalas = new MetodoAditivo();
    int numVariables, numRestricciones, sumFObjetivoNV;;
    int[] fObjetivo, indexCoeficientesNV, sumCRestriccNV;
    int[][] maRestricciones = new int[numRestricciones][numVariables + 2];
    String stfObjetivo, natMaxMin;
    String[] stRestricciones;
    
    
    
    //Se crea el panel que contiene informacion como titulo y nombres de los desarrolladores
    public JPanel Titulo() {
        
        //Se inicializa el panel con sus propiedades
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 0, 1390, 50);
        Panel.setBackground(new Color(66,66,66));
        Panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        //Se crea y añade el label del título
        lbTitulo = new JLabel();
        lbTitulo.setText("Metodo aditivo de Egon Balas");
        lbTitulo.setBounds(50, 5, 450, 40);
        lbTitulo.setVisible(true);
        lbTitulo.setFont(new java.awt.Font("Roboto", 0, 32));
        lbTitulo.setForeground(Color.WHITE);
        Panel.add(lbTitulo);
        
        //Se crea y añade el label de los nombres e informacion de los desarrolladores y universidad
        lbNombres = new JLabel();
        lbNombres.setText("<html> Mateo Yate Gonzalez - Kevin Andres Malaver Cobos <p> "
                + "&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Universidad Distrital <p> "
                + "&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp IO 2 81 - Profesora: Lilian A. Bejarano");
        lbNombres.setBounds(1100, 5, 400, 40);
        lbNombres.setVisible(true);
        lbNombres.setFont(new java.awt.Font("Cambria", 2, 10));
        lbNombres.setForeground(Color.WHITE);
        Panel.add(lbNombres);
        
        //Se retorna el panel entero
        return Panel;
    }
    
    //Se crea el panel donde se consignan el numero de variables, restricciones y naturaleza del programa
    public JPanel InformacionGeneral(){
        //Se unicializa el panel con sus respectivas propiedades
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 50, 1000, 320);
        Panel.setBackground(new Color(97,97,97));
        Panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        //Se crea un label informativo de la naturaleza del problema
        lbNaturaleza = new JLabel();
        lbNaturaleza.setText("¿El problema es de Minimizar o Maximizar?");
        lbNaturaleza.setBounds(110, 20, 650, 40);
        lbNaturaleza.setFont(new java.awt.Font("Roboto", 0, 24));
        lbNaturaleza.setVisible(true);
        lbNaturaleza.setForeground(Color.WHITE);
        Panel.add(lbNaturaleza);
        
        //Se crea un combo box para seleccionar la naturaleza del problema
        cbMaximizarMinimizar = new JComboBox();
        cbMaximizarMinimizar.setBounds(700, 15, 150, 50);
        cbMaximizarMinimizar.setBackground(Color.WHITE);
        cbMaximizarMinimizar.addItem("Minimizar");
        cbMaximizarMinimizar.addItem("Maximizar");
        cbMaximizarMinimizar.setFont(new java.awt.Font("Roboto", 0, 16));
        cbMaximizarMinimizar.setVisible(true);
        Panel.add(cbMaximizarMinimizar);
        
        //Se crea el label con informacion general de las variables
        lbInfoVariables = new JLabel();
        lbInfoVariables.setText("¿Cuantas variables de decision tiene el problema?");
        lbInfoVariables.setBounds(80, 20, 650, 220);
        lbInfoVariables.setFont(new java.awt.Font("Roboto", 0, 24));
        lbInfoVariables.setForeground(Color.WHITE);
        lbInfoVariables.setVisible(true);
        Panel.add(lbInfoVariables);
        
        //Se crea el combo box con informacion de las variables
        cbVariables = new JComboBox();
        cbVariables.setBounds(700, 110, 150, 50);
        cbVariables.setBackground(Color.WHITE);
        cbVariables.addItem("1");
        cbVariables.addItem("2");
        cbVariables.addItem("3");
        cbVariables.addItem("4");
        cbVariables.addItem("5");
        cbVariables.setFont(new java.awt.Font("Roboto", 0, 16));
        cbVariables.setVisible(true);
        Panel.add(cbVariables);
        
        //Se crea el label con informacion general de las restricciones
        lbInfoRestricciones = new JLabel();
        lbInfoRestricciones.setText("¿Cuantas restricciones tiene el problema?");
        lbInfoRestricciones.setBounds(120, 120, 650, 220);
        lbInfoRestricciones.setFont(new java.awt.Font("Roboto", 0, 24));
        lbInfoRestricciones.setForeground(Color.WHITE);
        lbInfoRestricciones.setVisible(true);
        Panel.add(lbInfoRestricciones);
        
        //Se crea el combo box con informacion general de las variables
        cbRestricciones = new JComboBox();
        cbRestricciones.setBounds(700, 200, 150, 50);
        cbRestricciones.setBackground(Color.WHITE);
        cbRestricciones.addItem("1");
        cbRestricciones.addItem("2");
        cbRestricciones.addItem("3");
        cbRestricciones.addItem("4");
        cbRestricciones.addItem("5");
        cbRestricciones.setFont(new java.awt.Font("Roboto", 0, 16));
        cbRestricciones.setVisible(true);
        Panel.add(cbRestricciones);
        
        //Se crea el boton para avanzar de etapa
        btSiguiente = new JButton();
        btSiguiente.setBounds(400, 270, 150, 30);
        btSiguiente.setText("...Avanzar...");
        btSiguiente.setBackground(Color.WHITE);
        btSiguiente.setVisible(true);
        btSiguiente.addActionListener(this);
        Panel.add(btSiguiente);        
        
        //Se retorna el panel entero
        return Panel;
    }

    public JPanel Funciones(){
        //Se inicializa el panel con sus propiedades
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(0, 360, 550, 350);
        Panel.setBackground(new Color(189,189,189));
        Panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        //Label que dice "Función objetivo..."
        lbFuncionObjetivo = new JLabel();
        lbFuncionObjetivo.setText("Función objetivo");
        lbFuncionObjetivo.setBounds(20, 20, 650, 40);
        lbFuncionObjetivo.setFont(new java.awt.Font("Cambria", 1, 16));
        lbFuncionObjetivo.setVisible(false);
        Panel.add(lbFuncionObjetivo);
        
        //Numero maximo de variables y restricciones
        numVariables = 5;
        numRestricciones = 5;
        
        //Campos de texto para introducir los valores de las variables 
        tfFuncionObjetivo = new JTextField[numVariables];
        for (int i = 0; i < numVariables; i++) {
            tfFuncionObjetivo[i] = new JTextField();
            tfFuncionObjetivo[i].setBounds(new Rectangle((i+1) * 80, 70, 25, 25));
            tfFuncionObjetivo[i].setVisible(false);
            Panel.add(tfFuncionObjetivo[i]);
        }
        
        //Labels de las variables de la funcion objetivo
        int k = 125;
        lbsFuncionObjetivo = new JLabel[numVariables + 1];
        lbsFuncionObjetivo[0] = new JLabel();
        lbsFuncionObjetivo[0].setText("Z   = ");
        lbsFuncionObjetivo[0].setBounds(new Rectangle(50, 70, 50, 25));
        lbsFuncionObjetivo[0].setVisible(false);
        Panel.add(lbsFuncionObjetivo[0]);
        for (int i = 1; i < (numVariables + 1); i++){
            lbsFuncionObjetivo[i] = new JLabel();
            lbsFuncionObjetivo[i].setText("X" + i);
            lbsFuncionObjetivo[i].setBounds(new Rectangle(k + (80*(i-1)), 70, 50, 25));
            lbsFuncionObjetivo[i].setVisible(false);
            Panel.add(lbsFuncionObjetivo[i]);
        }
        
        //Label que dice "Restricciones..."
        lbRestricciones = new JLabel();
        lbRestricciones.setText("Restricciones");
        lbRestricciones.setBounds(20, 110, 650, 40);
        lbRestricciones.setFont(new java.awt.Font("Cambria", 1, 16));
        lbRestricciones.setVisible(false);
        Panel.add(lbRestricciones);
        
        //Combo box con los signos de desigualdades
        int F = 120;
        cbSignos = new JComboBox[numVariables];
        for (int i = 0; i < (numVariables); i++){
            cbSignos[i] = new JComboBox();
            cbSignos[i].setBackground(Color.WHITE);
            cbSignos[i].addItem("<=");
            cbSignos[i].addItem(">=");
            cbSignos[i].setBounds(new Rectangle(400, F + ((i+1) * 30), 50, 25));
            cbSignos[i].setVisible(false);
            Panel.add(cbSignos[i]);
        }

        //Campos de texto para introducir los coeficientes de las restricciones
        int K = 120;
        int L = -60;
        tfRestricciones = new JTextField[numRestricciones][numVariables];
        for (int i = 0; i < numRestricciones; i++) {
            for (int j = 0; j < numVariables; j++) {
                tfRestricciones[i][j] = new JTextField();
                tfRestricciones[i][j].setBounds(new Rectangle(L + ((j + 1) * 80), K + ((i + 1) * 30), 25, 25));
                tfRestricciones[i][j].setVisible(false);
                Panel.add(tfRestricciones[i][j]);
            }
        }

        //Labels de las variables en las restricciones
        int LDX = 50;
        int LDY = 150;
        lbsRestricciones = new JLabel[numRestricciones][numVariables];
        for (int i = 0; i < numRestricciones; i++) {
            for (int j = 0; j < numVariables; j++) {
                lbsRestricciones[i][j] = new JLabel();
                lbsRestricciones[i][j].setText("X" + (j + 1));
                lbsRestricciones[i][j].setBounds(new Rectangle( LDX + (80 * (j)), LDY + (30 * (i)), 50, 25));
                lbsRestricciones[i][j].setVisible(false);
                Panel.add(lbsRestricciones[i][j]);
            }
        }
        
        //Campos de texto para introducir los valores al lado derecho de la desigualdad
        tfLD = new JTextField[numVariables];
        for (int i = 0; i < (numVariables); i++){
            tfLD[i] = new JTextField();
            tfLD[i].setBackground(Color.WHITE);
            tfLD[i].setBounds(new Rectangle(470, F + ((i+1) * 30), 50, 25));
            tfLD[i].setVisible(false);
            Panel.add(tfLD[i]);
        }
        
        //Boton para enviar la informacion
        btEnviar = new JButton();
        btEnviar.setBounds(170, 310, 150, 30);
        btEnviar.setText("Enviar...");
        btEnviar.setBackground(Color.WHITE);
        btEnviar.setVisible(false);
        btEnviar.addActionListener(this);
        Panel.add(btEnviar);
        
        //Retornar el panel entero
        return Panel;
    }
    
    //Tercer panel, donde se confirman los datos introducidos por ultima vez
    public JPanel Confirmacion(){
        //Se inicializa el panel con sus respectivas propiedades
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(550, 360, 450, 350);
        Panel.setBackground(new Color(224,224,224));
        Panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        //Label que dice "Conmfirmacion del ejercicio..."
        lbTituloConfirmacion = new JLabel();
        lbTituloConfirmacion.setText("Confirmacion del ejercicio...");
        lbTituloConfirmacion.setBounds(90, 10, 650, 40);
        lbTituloConfirmacion.setFont(new java.awt.Font("Cambria", 1, 18));
        lbTituloConfirmacion.setVisible(false);
        Panel.add(lbTituloConfirmacion);
        
        //Label donde se almacenará finalmente el problema en forma general
        lbProblemaGeneral = new JLabel();
        lbProblemaGeneral.setBounds(0, 50, 650, 160);
        lbProblemaGeneral.setFont(new java.awt.Font("Cambria", 1, 18));
        lbProblemaGeneral.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        lbProblemaGeneral.setVisible(false);
        Panel.add(lbProblemaGeneral);
        
        //Boton que confirma los datos y envía la petición al metodo de Egon Balas
        btConfirmacion = new JButton();
        btConfirmacion.setBounds(130, 230, 150, 30);
        btConfirmacion.setText("Confirmado");
        btConfirmacion.setBackground(Color.WHITE);
        btConfirmacion.setVisible(false);
        btConfirmacion.addActionListener(this);
        Panel.add(btConfirmacion);
        
        return Panel;
    }
    
    //Cuarto panel donde se muestran los resultados
    public JPanel Resultados(){
        JPanel Panel = new JPanel();
        Panel.setLayout(null);
        Panel.setBounds(1000, 50, 370, 680);
        Panel.setBackground(new Color(238,238,238));
        Panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
              
        lbResultados = new JLabel();
        lbResultados.setText("Resultados");
        lbResultados.setBounds(120, 0, 450, 40);
        lbResultados.setVisible(false);
        lbResultados.setFont(new java.awt.Font("Roboto", 2, 24));
        Panel.add(lbResultados);
        
        lbsTableros = new JLabel();
        lbsTableros.setBounds(2, 40, 370, 560);
        lbsTableros.setVisible(false);
        Panel.add(lbsTableros);

        lbSolucion = new JLabel();
        lbSolucion.setBounds(55, 610 , 370, 20);
        lbSolucion.setText("Solucion dada: ");
        lbSolucion.setFont(new java.awt.Font("Roboto", 1, 12));
        lbSolucion.setVisible(false);
        Panel.add(lbSolucion);
        
        
        lbInformacionJava = new JLabel();
        lbInformacionJava.setBounds(35, 630 , 370, 20);
        lbInformacionJava.setText("Para mas informacion visite la consola de JAVA");
        lbInformacionJava.setFont(new java.awt.Font("Roboto", 1, 12));
        lbInformacionJava.setVisible(false);
        Panel.add(lbInformacionJava);
        
        return Panel;
    }

    //ActionListener de los botones
    @Override
    public void actionPerformed(ActionEvent e) {
        //Caso en el que se presione el primer boton
        if(e.getSource() == btSiguiente){
            //Captura de datos
            natMaxMin = cbMaximizarMinimizar.getSelectedItem().toString();
            numVariables = Integer.parseInt((String) cbVariables.getSelectedItem());
            numRestricciones = Integer.parseInt((String) cbRestricciones.getSelectedItem());
            
            //Inicializacion de variables para el metodo
            fObjetivo = new int[numVariables];
            maRestricciones = new int[numRestricciones][numVariables + 2];
            
            //Se muestran los respectivos labels, campos de texto y botones
            lbRestricciones.setVisible(true);
            lbFuncionObjetivo.setVisible(true);
            btEnviar.setVisible(true);
            lbsFuncionObjetivo[0].setVisible(true);
            for (int i = 0; i < numVariables; i++){
                tfFuncionObjetivo[i].setVisible(true);
                lbsFuncionObjetivo[i+1].setVisible(true);
            }
            for (int i = 0; i < numRestricciones; i++){
                for (int j = 0; j < numVariables; j++){
                    tfRestricciones[i][j].setVisible(true);
                    lbsRestricciones[i][j].setVisible(true);
                }
            }
            for (int i = 0; i < numRestricciones; i++){
                cbSignos[i].setVisible(true);
                tfLD[i].setVisible(true);
            }
        }
        
        //Caso en que se presione el segundo boton
        if (e.getSource() == btEnviar){

            //Se guardan los coeficientes de las variables de la funcion objetivo
            for (int i = 0; i < numVariables; i++) {
                fObjetivo[i] = Integer.parseInt(tfFuncionObjetivo[i].getText());
            }
            
            //Se crea una tabla Hash como diccionario para las desigualdades
            Hashtable<String, Integer> desigualdad = new Hashtable<String, Integer>();
            desigualdad.put("<=" , -1);
            desigualdad.put(">=" , 1);
            
            //Se almacenan los datos de las restricciones en una matriz
            for (int i = 0; i < numRestricciones; i++) {
                for (int j = 0; j < numVariables + 2; j++) {
                    //Signos, LD y coeficientes
                    if (j == numVariables) {
                        maRestricciones[i][j] = desigualdad.get(cbSignos[i].getSelectedItem());
                        
                    } else if (j == (numVariables + 1)) {
                        maRestricciones[i][j] = Integer.parseInt(tfLD[i].getText());
                    } else {
                        maRestricciones[i][j] = Integer.parseInt(tfRestricciones[i][j].getText());
                    }
                }
            }
            
            //Se muestra en consola el problema original en consola
            System.out.println("El problema es de la forma");
            System.out.print(natMaxMin + " Z = ");
            for (int i = 0; i < numVariables; i++) {
                if (fObjetivo[i] >= 0 && i > 0) {
                    System.out.print("+");
                }
                System.out.print(fObjetivo[i] + "X" + (i + 1) + " ");
            }
            System.out.println("");
            System.out.println("Sujeto a: ");
            for (int i = 0; i < numRestricciones; i++) {
                for (int j = 0; j < numVariables + 2; j++) {
                    if (j == numVariables) {
                        switch (maRestricciones[i][j]) {
                            case -1:
                                System.out.print("<=" + " ");
                                break;
                            case 1:
                                System.out.print(">=" + " ");
                                break;
                            default:
                                break;
                        }
                    } else if (j == (numVariables + 1)) {
                        System.out.print(maRestricciones[i][j]);
                    } else {
                        if (maRestricciones[i][j] >= 0 && j > 0) {
                            System.out.print("+");
                        }
                        System.out.print(maRestricciones[i][j] + "X" + (j + 1) + " ");
                    }
                }
                System.out.println(" ");
            }
            System.out.println(" ");
            
            //Se muestran los labels de la siguiente etapa
            lbTituloConfirmacion.setVisible(true);
            lbProblemaGeneral.setVisible(true);
            btConfirmacion.setVisible(true);
            
            //CSe constuyen las cadenas para mostrar el problema general
            stfObjetivo = "Z = ";
            for (int i = 0; i < numVariables; i++) {
                if (fObjetivo[i] >= 0 && i > 0) {
                    stfObjetivo = stfObjetivo + "+";
                }
                stfObjetivo = stfObjetivo + fObjetivo[i] + "X" + (i + 1) + " ";
            }
            
            //Construir el texto para las restricciones...
            stRestricciones = new String[5];
            //Llenarlo de vacios por si no son 5 restricciones
            for (int i = 0; i < 5; i++) {
                stRestricciones[i] = " ";
            }
            //Construir en si el texto de restricciones
            for (int i = 0; i < numRestricciones; i++) {
                for (int j = 0; j < numVariables + 2; j++) {
                    if (j == numVariables) {
                        switch (maRestricciones[i][j]) {
                            case -1:
                                stRestricciones[i] = stRestricciones[i] + "&lt" + "=" + " ";
                                break;
                            case 1:
                                stRestricciones[i] = stRestricciones[i] + "&gt" + "=" + " ";
                                break;
                            default:
                                break;
                        }
                    } else if (j == (numVariables + 1)) {
                        stRestricciones[i] = stRestricciones[i] + maRestricciones[i][j];
                    } else {
                        if (maRestricciones[i][j] >= 0 && j > 0) {
                            stRestricciones[i] = stRestricciones[i] + "+";
                        }
                        stRestricciones[i] = stRestricciones[i] + maRestricciones[i][j] + "X" + (j + 1) + " ";
                    }
                }
            }
            String stTodasRestricciones = "";
            for (int i = 0; i < numRestricciones; i++) {
                stTodasRestricciones = stTodasRestricciones + stRestricciones[i] + "<p>";
            }
            
            //Añadir texto al Label
            lbProblemaGeneral.setText( "<html>" + natMaxMin + " " + stfObjetivo + "<p>" +
                "Sujeto a: " + "<p>" + 
                stTodasRestricciones + "<html>");
            lbProblemaGeneral.setVisible(true);
        }
        
        //Boton de confirmacion
        if (e.getSource() == btConfirmacion){
            
            //Comprobacion de Minimizacion en la F.O
            if (natMaxMin.equals("Maximizar")){
                System.out.println("");
                System.out.println("Se detectó un problema de Maximizacion...");
                
                System.out.print("F. Objetivo original: ");
                for (int i = 0; i < numVariables; i++){
                    System.out.print(fObjetivo[i] + ", ");
                }
                System.out.println("");
                for (int i = 0; i < numVariables; i++){
                    fObjetivo[i] = fObjetivo[i]*-1;
                }
                System.out.print("Nueva F. Objetivo: ");
                for (int i = 0; i < numVariables; i++){
                    System.out.print(fObjetivo[i] + ", ");
                }
                natMaxMin = "Minimizar";
                System.out.println("");
            }
            
            //Comprobacion de Menor o Igual en las restricciones...
            System.out.println("");
            System.out.println("Comprobacion de las restricciones");
            for (int i = 0; i < numRestricciones; i++) {
                for (int j = 0; j < numVariables + 2; j++) {
                    if (j == numVariables) {
                        switch (maRestricciones[i][j]) {
                            case -1:
                                System.out.println("La restriccion es valida...");
                                break;
                            case 1:
                                System.out.println("La restriccion NO es valida...");
                                
                                System.out.print("Restriccion original: ");
                                for (int h = 0; h < maRestricciones[i].length ; h++) {
                                    System.out.print(maRestricciones[i][h] + ", ");
                                }
                                System.out.println("");
                                System.out.print("Restriccion invertida: ");
                                for (int h = 0; h < maRestricciones[i].length; h++) {
                                    maRestricciones[i][h] = maRestricciones[i][h] * -1;
                                    System.out.print(maRestricciones[i][h] + ", ");
                                }
                                System.out.println("");
                                
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            
            //Comprobacion de coeficientes en la F.O
            System.out.println("");
            System.out.println("Comprobacion de los coeficientes en la F. Objetivo");
            indexCoeficientesNV = new int[numVariables];
            sumFObjetivoNV = 0;
            int[] auxRestriccionCoefNV = new int[numRestricciones];
            for(int i = 0; i < numVariables; i++){
                indexCoeficientesNV[i] = 0;
                
                for (int j = 0; j < auxRestriccionCoefNV.length; j++){
                    auxRestriccionCoefNV[j] = 0;
                }
                
                if (fObjetivo[i] < 0){
                    System.out.println("Se ha detectado un coeficiente negativo en la funcion objetivo...");
                    System.out.println("Coeficiente: " + fObjetivo[i] + "X" + (i+1));
                    
                    System.out.println("Añadiendo a lista de indices no validos... Indice: " + i);
                    indexCoeficientesNV[i] = 1;
                    
                    sumFObjetivoNV = sumFObjetivoNV + fObjetivo[i];
                    System.out.println("Sumando a la lista de holgura de la F.O... Suma: " + sumFObjetivoNV);
                    
                    System.out.println("Reemplazando coeficiente no validos...");
                    fObjetivo[i] = fObjetivo[i]*-1;
                    
                    for(int j = 0; j < numRestricciones; j++){
                        System.out.println("Ajustando la restriccion " + j);
                        System.out.println("Sumando a la olgura de la restriccion");
                        auxRestriccionCoefNV[j] = auxRestriccionCoefNV[j] + maRestricciones[j][i];
                        System.out.println("Suma de la restriccion " + j + " Acomulada: " + auxRestriccionCoefNV[j]);
                        System.out.println("Invirtiendo los signos de la restriccion en la posicion " + (i+1));
                        maRestricciones[j][i] = maRestricciones[j][i] * -1;
                    }
                    
                    for(int j = 0; j < numRestricciones; j++){
                        System.out.println("Sumando/Restando al LD de la desigualdad " + j);
                        System.out.println("Valor ORIGINAL en la casilla restriccion:" +  j + ", " + (numVariables + 1) + " :" + maRestricciones[j][numVariables + 1]);
                        maRestricciones[j][numVariables + 1] = maRestricciones[j][numVariables + 1] - auxRestriccionCoefNV[j];
                    }
                    System.out.println("");
                }
            }
            
            //Imprimiendo el problema final en consola
            System.out.println("Imprimiendo el problema final...");
            System.out.print(natMaxMin + " Z = ");
            for (int i = 0; i < numVariables; i++) {
                if (fObjetivo[i] >= 0 && i > 0) {
                    System.out.print("+");
                }
                System.out.print(fObjetivo[i] + "X" + (i + 1) + " ");
            } System.out.println(" + " + sumFObjetivoNV);

            System.out.println("Sujeto a: ");
            for (int i = 0; i < numRestricciones; i++) {
                for (int j = 0; j < numVariables + 2; j++) {
                    if (j == numVariables) {
                        switch (maRestricciones[i][j]) {
                            case -1:
                                System.out.print("<=" + " ");
                                break;
                            case 1:
                                System.out.print(">=" + " ");
                                break;
                            default:
                                break;
                        }
                    } else if (j == (numVariables + 1)) {
                        System.out.print(maRestricciones[i][j]);

                    } else {

                        if (maRestricciones[i][j] >= 0 && j > 0) {
                            System.out.print("+");
                        }

                        System.out.print(maRestricciones[i][j] + "X" + (j + 1) + " ");
                    }
                }
                System.out.println(" ");
            }
            System.out.println(" ");
            
            //Llamada al metodo de Egon Balas de la clase MetodoAditivo
            System.out.println("Llamando al Metodo Aditivo de Egon Balas...");
            egonBalas.solucionar(fObjetivo, maRestricciones, numVariables, numRestricciones, sumFObjetivoNV, indexCoeficientesNV);
            
            ArrayList<String> tablerosFinales = egonBalas.getTableroFinal();
            Object[] arrayAuxiliar = tablerosFinales.toArray();
            String todosTableros = "<html>" + "<div style=\"text-align:center;\">" + "<table border=\"0\" style=\"margin: 0 auto;\">";
            
            for (Object o : arrayAuxiliar) {
                todosTableros =  todosTableros + (String) o;
            }
            
            todosTableros = todosTableros + "</table></div><html> ";
            
           
            
            lbSolucion.setText(egonBalas.getSolucionFinal());
            lbsTableros.setText(todosTableros);
            
            lbResultados.setVisible(true);
            lbsTableros.setVisible(true);
            lbInformacionJava.setVisible(true);
            lbSolucion.setVisible(true);
                    
            
            
            
        }
    }    
}

