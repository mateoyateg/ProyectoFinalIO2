package Controlador;

import static Vista.Principal.ventana;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MetodoAditivo {
    
    //Se inicializan las variables globales
    int[][] matrizTablero;
    int X = 0;
    int Y = 0;
    
    int numVariablesGB;
    int numRestriccionesGB;
    int numIteracionesGB;

    public int getNumIteracionesGB() {
        return numIteracionesGB;
    }
    
    String solucionFinal = "";
    String[] valoresFinales;
    
    ArrayList<String> tableroFinal = new ArrayList();

    public ArrayList<String> getTableroFinal() {
        return tableroFinal;
    }

    public String getSolucionFinal() {
        return solucionFinal;
    }
    
    String p = "<p>";
    String html = "<html>";
    String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;";
    String nbsp2 = "&nbsp;&nbsp;";
    String nbsp3 = "&nbsp;";
    
    ArrayList<Integer> valoresZActuales = new ArrayList();
    
    //Algunas estructuras importantes
    //Estructura de una restriccion:   [ X1, X2, ... , XN, SIMBOLO         , Numero a la derecha]
    //Estructura de una restriccion:   [ numVariables    , numVariables + 1, numVariables + 2]
    //int[][] maRestricciones = new int[numRestricciones][numVariables + 2];
    //Estructura de la func. objetivo: [ X1, X2, ... , XN]
    
    //Metodo llamado para solucionar el problema
    public int solucionar(int[] fObjetivo, int[][] maRestricciones, int numVariables, int numRestricciones, int sumFObjetivoNV, int[] indexCoeficientesNV){
        
        numVariablesGB = numVariables;
        numRestriccionesGB = numRestricciones;
        
        //Mensaje de bienvenida
        System.out.println("...METODO ADITIVO DE EGON BALAS...");
        
        //Se inicializa el vector de valores de variables...
        int[] valoresVariables = new int[numVariables];
        
        //Se crea una matriz para el tablero del metodo
        matrizTablero = new int[numVariables][numVariables];
        
        //Se inicializa la variable donde se van guardando las infactibilidades
        int infactibilidad = 0;
        
        //Se imprime un formato de grilla para mostrar por consola la tabla del metodo
        imprimirGrilla(numVariables, numRestricciones);
        
        //La primera solucion candidata será donde todas las variables sean cero...
        for(int i = 0; i < numVariables; i++){
            valoresVariables[i] = 0;
        }
        
        //Se inicia calculando el cero y se retorna la infactibilidad
        infactibilidad = calcular(fObjetivo, maRestricciones, numVariables, numRestricciones, valoresVariables);
        
        tableroFinal.add(" ");
        
        //Si la solucion trivial es una solucion optima se detiene el proceso y se informa esto
        if (infactibilidad == 0){
            System.out.println("La solucion optima esta dada por:");
            solucionFinal = "Z = 0, X1,...,XN = 0";
            System.out.println("Z = 0, X1,...,XN = 0");
        }
        
        //Si no es optima, se inicializa un array donde se guardaran las infactibilidades de cada tablero
        int[] arrayInfactibilidades = new int[numVariables];
        
        //Se inicializa una variable donde se guardará el numero de iteracion...
        int numIteracion = 1;
        
        //Si la solucion candidata no es factible, se procede a probar la siguiente hasta hallar una infactibilidad de cero
        while (infactibilidad != 0){
            //Se limpia el arreglo donde se guarda la columna Z de la iteracion
            valoresZActuales.clear();
            
            //Numero de iteracion
            System.out.println("Iteracion " + numIteracion);
            tableroFinal.add(p + "Iteracion " + numIteracion + p);
            numIteracionesGB = numIteracion;
            //Se imprime nuevamente la grilla
            imprimirGrilla(numVariables, numRestricciones);
            
            //Se realiza la primera iteracion probando una diagonal de unos.
            arrayInfactibilidades = calcularTablero(fObjetivo, maRestricciones, numVariables, numRestricciones, valoresVariables);
            
            
            
            //Se busca si hay un cero en la infactibilidad registrada.
            int indiceInfactibilidad = busquedaInfactbilidad(arrayInfactibilidades, 0);
            
            //Si no se encuentra una infactibilidad de cero
            if (indiceInfactibilidad == -1){
                //Se busca el menor valor de infactibilidad
                int menorInfactibilidad = buscarMenor(arrayInfactibilidades);
                //Se ubica la posicion donde se encuentra dicho valor
                indiceInfactibilidad = busquedaInfactbilidad(arrayInfactibilidades, menorInfactibilidad);
                
                //Se cambia el valor de las variables a 1 en esa posicion
                valoresVariables[indiceInfactibilidad] = 1;
                
            } else {
                //Se notifica que hay mas de una solucion optima en el tablero
                System.out.println("");
                System.out.println("Se encontraron una o varias soluciones optimas en este tablero...");
                
                //Se inicializan los array list
                ArrayList<Integer> indicesInfactibilidadesCero = new ArrayList();
                ArrayList<Integer> valoresFinales = new ArrayList();
                
                //Hashtable para los valores de los indices y su clave
                Hashtable<Integer, Integer> valoresIndices = new Hashtable<Integer, Integer>();
                
                //Se añaden las infactibilidades al arreglo
                for (int i = 0; i < arrayInfactibilidades.length ; i++){
                    if(arrayInfactibilidades[i] == 0){
                        indicesInfactibilidadesCero.add(i);
                    }
                }
                
                //Se realiza una busqueda de los valores finales que sean candidatos
                for (int i = 0; i < indicesInfactibilidadesCero.size(); i++){
                    valoresFinales.add(valoresZActuales.get(indicesInfactibilidadesCero.get(i)));
                    
                    valoresIndices.put(valoresFinales.get(i), indicesInfactibilidadesCero.get(i));
                }
                
                //Se orgena el vector en orden ascendente
                Collections.sort(valoresFinales);
                
                //Se busca cual es la fila donde está el valor solucion menor
                int filaFinal = valoresIndices.get(valoresFinales.get(0));
                int[] solucionUno = new int[numVariables];
                
                //Se notifican los valores solucion
                System.out.print("Valores solucion: ");
                for (int i = 0; i < numVariables; i ++){
                    solucionFinal = solucionFinal + "X" + (i+1) + "=" + matrizTablero[filaFinal][i] + ", ";
                    System.out.print("X" + (i+1) + "=" + matrizTablero[filaFinal][i] + ", ");
                    solucionUno[i] = matrizTablero[filaFinal][i];
                }
                System.out.println(" Z = " + valoresZActuales.get(filaFinal));
                solucionFinal = solucionFinal + " Z = " + valoresZActuales.get(filaFinal);
                System.out.println("");
                
                //Si el problema no era de maximizacion, se realiza el siguiente proceso para mostrar la informacion
                if (sumFObjetivoNV != 0){
                    System.out.println("Valores solucion al problema original...");
                    System.out.print("Valores solucion: ");
                    System.out.print(" Z = " + ((valoresZActuales.get(filaFinal)+sumFObjetivoNV)*-1) + ", ");
                    solucionFinal = " Z = " + ((valoresZActuales.get(filaFinal)+sumFObjetivoNV)*-1) + ", ";
                    
                    for (int i=0; i<numVariables; i++){
                        if (indexCoeficientesNV[i]!=0){
                            System.out.print("X" + (i+1) + "=" + (1-solucionUno[i])+ " ,");
                            solucionFinal = solucionFinal + "X" + (i+1) + "=" + (1-solucionUno[i])+ ", ";
                        } else{
                            System.out.print("X" + (i+1) + "=" + (solucionUno[i])+ " ,");
                            solucionFinal = solucionFinal + "X" + (i+1) + "=" + (solucionUno[i])+ ", ";
                        }
                    }
                    
                    System.out.println("");
                    String nl = System.getProperty("line.separator");
                                        
                    JOptionPane.showMessageDialog(null,"Solucion al problema original" + nl + solucionFinal);
                    
                } else {
                    String nl = System.getProperty("line.separator");
                    JOptionPane.showMessageDialog(null,"Solucion al problema original" + nl + solucionFinal);
                    
                }
                infactibilidad = arrayInfactibilidades[indiceInfactibilidad];
                
            }
            numIteracion = numIteracion + 1;
            System.out.println("");
        } 
        return 0;
    }
    
    //Metodo utilizado para imprimir la grilla de los tableros
    public void imprimirGrilla(int numVariables, int numRestricciones){
        String auxGrilla = p;
        for(int i = 0; i < numVariables; i++){
            System.out.printf("%-7s","X" + (i+1));
            auxGrilla = auxGrilla + nbsp + nbsp3 + "X" + (i+1);
        }
        
        for(int i = 0; i < numRestricciones; i++){
            System.out.printf("%-7s","R" + (i+1));
            auxGrilla = auxGrilla + nbsp + nbsp3 + "R" + (i+1);
        }
        
        System.out.printf("%-7s", "Infact.");
        auxGrilla = auxGrilla + nbsp + nbsp3 +  "Infact.";
        System.out.printf("%9s", "Z");
        auxGrilla = auxGrilla + nbsp + nbsp3 + "Z";
        
        tableroFinal.add(auxGrilla + p);
        
        System.out.println("");
    }

    //Metodo donde se calcula para cada fila el metodo aditivo de Egon Balas
    public int calcular(int[] fObjetivo, int[][] maRestricciones, int numVariables, int numRestricciones, int[]valoresVariables){
        //Se inicializa el arreglo donde se guardan el valor que toman las variables...
        
        //Se inicializa el arreglo donde se guardan el valor que toman las restricciones...
        int[] valoresRestricciones = new int[numRestricciones];
        
        //Se inicializa una infactibilidad de cero y la funcion objetivo en cero
        int infactibilidad = 0;
        int z = 0;
        
        //Se remplaza el valor de las variables en las restricciones y se plazma en un arreglo unidimensional el resultado
        for(int i = 0; i < numRestricciones; i++){
            for(int j = 0; j < numVariables; j++){
                valoresRestricciones[i] = valoresRestricciones[i] + (valoresVariables[j]*maRestricciones[i][j]);
            }
        }
        
        //Se le suma o resta (depende el caso) el valor que está a la derecha del signo...
        for(int i = 0; i < numRestricciones; i++){
            valoresRestricciones[i] = valoresRestricciones[i] + (maRestricciones[i][numVariables + 1]*(-1));
        }
        
        //Se calcula la infactibilidad
        for(int i = 0; i < numRestricciones; i++){
            if (valoresRestricciones[i] > 0){
                infactibilidad = infactibilidad + valoresRestricciones[i];
            }
        }
        
        //Se calcula el valor de Z con los valores de las variables
        for(int i = 0; i < numVariables; i++){
            z = z + (valoresVariables[i]*fObjetivo[i]);
        }
        
        //Se añaden los valores a la matriz del tablero
        for (int i = 0; i < (numVariables) ; i++){
            matrizTablero[X][i] = valoresVariables[i];
        }
        
        String auxRow = p;
        
        //Se imprimen los resultados
        for (int i = 0; i < (numVariables) ; i++){
            System.out.printf("%-7s",valoresVariables[i]);
            auxRow = auxRow + nbsp + nbsp2 + valoresVariables[i] + " ";
        }
        for (int i = 0; i < numRestricciones ; i++){
            System.out.printf("%-7s",valoresRestricciones[i] + "<=0");
            auxRow = auxRow + nbsp + nbsp2 + valoresRestricciones[i] + " ";
        }
        
        System.out.printf("%-7s", infactibilidad);
        auxRow = auxRow + nbsp + nbsp2 + infactibilidad + " ";
        
        System.out.printf("%9s", z);
        auxRow = auxRow + nbsp + nbsp2 + z + " " + p;
        
        valoresZActuales.add(z);
        System.out.println("");
        
        tableroFinal.add(auxRow);
        
        //Se retorna la infactibilidad
        return infactibilidad;
    }
    
    //Metodo que busca la posicion de la infactibilidad
    public int busquedaInfactbilidad(int[] arreglo, int dato) {
        int posicion = -1;
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] == dato) {
                posicion = i;
                break;
            }
        }
        return posicion;
    }
    
    //Metodo que busca el menor valor de un arreglo
    public int buscarMenor(int[] arreglo){
        int min = arreglo[0];
        for (int i = 0; i < arreglo.length; i++) {
            if (min > arreglo[i]) {
                min = arreglo[i];
            }
        }
        return min;
    }
    
    //Metodo que retorna el indice del valor Z menor de un arreglo de Z
    public int buscarZMenor(int[] arreglo){
        int min = arreglo[0];
        int index = 0;
        for (int i = 0; i < arreglo.length; i++) {
            if (min > arreglo[i]) {
                min = arreglo[i];
                index = i;
            }
        }
        return index;
    }
    
    //Metodo para ordenar y calcular sobre el tablero
    public int[] calcularTablero(int[] fObjetivo, int[][] maRestricciones, int numVariables, int numRestricciones, int[]valoresVariables){
        int[] arrayInfactibilidades = new int[numVariables];
        int[] valoresVariablesAux = new int [numVariables];
        
        System.arraycopy(valoresVariables, 0, valoresVariablesAux, 0, numVariables);
        int aux = 0;
        for (int i = 0; i < numVariables; i++) {
            
            valoresVariables[i] = 1;
            arrayInfactibilidades[i] = calcular(fObjetivo, maRestricciones, numVariables, numRestricciones, valoresVariables);
            X = X + 1;
            System.arraycopy(valoresVariablesAux, 0, valoresVariables, 0, numVariables);
        }
        X = 0;
        return arrayInfactibilidades;
    } 
}
