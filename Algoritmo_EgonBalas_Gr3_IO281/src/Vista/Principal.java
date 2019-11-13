package Vista;

import Controlador.CreadorBotones;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Principal {
    
    //Se define un JFrame global
    public static JFrame ventana;
    
    //Clase principal del sistema.
    public static void main(String[] args) {
        
        //Se crea la clase principal del Controlador
        CreadorBotones creadorBotones = new CreadorBotones();
        
        //Se crean los diferentes paneles que serán utilzados
        JPanel PaTitulo = creadorBotones.Titulo();
        JPanel PaInfoGeneral = creadorBotones.InformacionGeneral();
        JPanel PaFunciones = creadorBotones.Funciones();
        JPanel PaConfirmacion = creadorBotones.Confirmacion();
        JPanel PaResultados = creadorBotones.Resultados();
        
        //Se crea la ventana del Programa
        ventana = new JFrame("");

        //Se añaden los elementos guardados en el Frame a la ventana
        ventana.add(PaTitulo);
        ventana.add(PaInfoGeneral);
        ventana.add(PaFunciones);
        ventana.add(PaConfirmacion);
        ventana.add(PaResultados);
        
        //Se definen las propiedades de la ventana
        ventana.setSize(1390, 745);
        ventana.setTitle("Metodo aditivo de Egon Balas 5x5 - Mateo Yate & Kevin Malaver - UDistrital");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(null);

        //Se muestra la ventana
        ventana.setVisible(true);
    }  
}
