/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Base.tablero6x6;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



/**
 *
 * @author Carlos Lopez
 */
public class Game6x6 extends javax.swing.JPanel implements ActionListener, MouseListener {

    // Inicialización de variables para el juego de 6x6
    private tablero6x6 juego6x6 = new tablero6x6();// Inicia el juego de memoria
    private ImageIcon crd1;// Imagen de la primera carta seleccionada
    private ImageIcon crd2;// Imagen de la segunda carta seleccionada
    private JToggleButton[] pbtn = new JToggleButton[2];// Guarda las cartas seleccionadas temporalmente
    private JToggleButton btns1 = new JToggleButton(); // Botón para la primera carta seleccionada
    private JToggleButton btns2 = new JToggleButton();// Botón para la segunda carta seleccionada
    JToggleButton[] buttons; // Todos los botones del juego
    JToggleButton[][] buttons1;// Organizamos los botones en filas y columnas (para poner imagenes)
  

    boolean up = false;// Indica si hay una carta volteada
    boolean fcard = false;// Indica si se ha volteado la primera carta
    int ganaste = 0;// Contador de pares encontrados
    int movimientos = 0;// contador de movimientos
    int puntos = 0;// contador de puntos
    int[] estadosBotones = new int[36];// Guardamos el estado de cada botón (habilitado o deshabilitado)
    int partida[][] = new int [6][6];// Estado del juego actual
    String ruta = "C:\\Temp\\estadísticas_nivel2.txt";//ruta del archivo de txt de estadisticas del juego
       
     
    /**
     * Creates new form Game6x6
     */
    public Game6x6() {
        initComponents();
        
        buttons = new JToggleButton[]{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16,
                                      jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24,jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,
                                      jbC32,jbC33,jbC34,jbC35,jbC36};
        
        //agregamos un addActionListener y un addMouseListener a todos los botones que conforman las cartas
        for(int i=0; i<36; i++){
            buttons[i].addActionListener(this);
            buttons[i].addMouseListener(this);
            
        }
        
    }

    //empezamos un juego desde 0
     public void cartas(){
         
        try{
        // Obtiene un nuevo tablero del juego
        int [][]numbers = juego6x6.devolverTablero();
        
        // Copia los valores del tablero al estado actual del juego
        for(int a = 0; a<6; a++){
             for(int b = 0; b<6; b++){
                partida[a][b] = numbers[a][b];
           }
        }
                
        // Configuramos las cartas en el tablero de la interfaz gráfica
        buttons1 = new JToggleButton[][]{{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6},{jbC7,jbC8,jbC9,jbC10,jbC11,jbC12},
                                        {jbC13,jbC14,jbC15,jbC16,jbC17,jbC18},{jbC19,jbC20,jbC21,jbC22,jbC23,jbC24},
                                        {jbC25,jbC26,jbC27,jbC28,jbC29,jbC30},{jbC31,jbC32,jbC33,jbC34,jbC35,jbC36}};
        
         // asignamos imagenes cada boton en estado disabled
        for(int i = 0; i < 6; i++){
            for(int j = 0; j<6; j++){
            buttons1[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/Cards/card"+numbers[i][j]+".png")));
            }
         }
          
         ganaste = 0;// Reinicia el contador de pares encontrados
         
        } catch(Exception e){
             JOptionPane.showMessageDialog(this, e.getStackTrace());
        }
    }
     
      //inicializamos el juego pero de leyendo la informacion guardada
     public void Juegoguardado(){
         
         try{
             
         // Habilitamos todos los botones para comenzar un nuevo juego    
         for(int i = 0; i<36; i++){
            buttons[i].setEnabled(true);
         }
         // Importamos la información guardada
         importarInfo();
         
          // Variable para contar los botones y actualizar su estado
        int contar = 0;
       
         
         // Creamos una matriz de botones 4x4 
        buttons1 = new JToggleButton[][]{{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6},{jbC7,jbC8,jbC9,jbC10,jbC11,jbC12},
                                        {jbC13,jbC14,jbC15,jbC16,jbC17,jbC18},{jbC19,jbC20,jbC21,jbC22,jbC23,jbC24},
                                        {jbC25,jbC26,jbC27,jbC28,jbC29,jbC30},{jbC31,jbC32,jbC33,jbC34,jbC35,jbC36}};
        
        // Asignamos la imagen correspondiente a cada botón según la información de la partida
        for(int i = 0; i < 6; i++){
            for(int j = 0; j<6; j++){
            buttons1[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/Cards/card"+partida[i][j]+".png")));
            
            }
        }
         // Actualizamos el estado de los botones según la información guardada
        for(int a = 0; a < 6; a++){
            for(int b = 0; b < 6; b++){
                if(estadosBotones[contar]== 1){
                    buttons1[a][b].setEnabled(false);
                }
                contar++;
            }
        }
        // Actualizamos la información de movimientos y puntos en la interfaz gráfica
        jTXmovements.setText(String.valueOf(movimientos));
        jTXpoints.setText(String.valueOf(puntos));
         }
         catch(Exception e){
             JOptionPane.showMessageDialog(this, e.getStackTrace());
         }
        
    }
     
     
    public void reiniciar(){
        try{
      
        buttons = new JToggleButton[]{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16,
                                      jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24,jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,
                                      jbC32,jbC33,jbC34,jbC35,jbC36};
        //habilitamos los botones para tapar las cartas
        for(int i = 0; i<36; i++){
            buttons[i].setEnabled(true);
        }
          // Reiniciamos las variables de control del juego
        fcard = false;
        up = false;
        jTXmovements.setText(String.valueOf(0));
        jTXpoints.setText(String.valueOf(0));
        movimientos = 0;
        puntos = 0;
        // Llamamos al método cartas() para iniciar un nuevo juego
        cartas();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getStackTrace());
        }
        
    } 
     
     //desabilitamos botones al tocar las cartas
    public void JTbuttonDisable(JToggleButton btn){
        
           try{
            if (!up) {
                 // Si no hay una carta volteada
                 btn.setEnabled(false); // Deshabilitamos el botón
                 crd1 = (ImageIcon) btn.getDisabledIcon(); // Guardamos la imagen de la primera carta
                 btns1 = btn; // Guardamos el botón de la primera carta
                 fcard = false; // Indicamos que es la primera carta
                 movimientos += 1; // Aumentamos el contador de movimientos
                 jTXmovements.setText(String.valueOf(movimientos)); // Actualizamos el contador en la interfaz
             } else {
                 // Si ya hay una carta volteada
                 btn.setEnabled(false); // Deshabilitamos el botón
                 crd2 = (ImageIcon) btn.getDisabledIcon(); // Guardamos la imagen de la segunda carta
                 btns2 = btn; // Guardamos el botón de la segunda carta
                 fcard = true; // Indicamos que es la segunda carta
                 movimientos += 1; // Aumentamos el contador de movimientos
                 jTXmovements.setText(String.valueOf(movimientos)); // Actualizamos el contador en la interfaz
             }

            up = true; // Indicamos que hay una carta volteada
           }
           catch(Exception e){
               JOptionPane.showMessageDialog(this, e.getStackTrace());
           }
        
    }
    
    //comparamos las cartas
    private void compararCartas(){
        
        try{
        if (up && fcard) {
              // Si ya hay dos cartas volteadas
              if (crd1.getDescription().compareTo(crd2.getDescription()) != 0) {
                  // Si las cartas no son iguales
                  btns1.setEnabled(true); // Habilita de nuevo la primera carta
                  btns2.setEnabled(true); // Habilita de nuevo la segunda carta
                  if (puntos > 0) {
                      puntos -= 1; // Resta un punto si es mayor a 0
                  }
              } else {
                  // Si las cartas son iguales
                  puntos += 5; // Suma 5 puntos
                  ganaste += 2; // Aumenta el contador de cartas encontradas
              }

              jTXpoints.setText(String.valueOf(puntos)); // Actualiza la puntuación en pantalla
              up = false; // Resetea el estado de las cartas volteadas

              if (ganaste == 36) {
                  // Si se han encontrado todos los pares
                  JOptionPane.showMessageDialog(this, "Ganaste"); // Muestra un mensaje de victoria
              }
          }
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getStackTrace());
        }
    }
     
     
     //exportamos y guardamos la informacion de partida
    public void guardarInfo(){
        
       try {
            // Creamos un objeto filewriter
            File escritor = new File (ruta);
            // creamos un objeto de BufferedWriter
            BufferedWriter bufferEscritor = new BufferedWriter(new FileWriter(escritor));

            // Escribimos la info de los botones "numeros"
            for (int i = 0; i < 6; i++) {
                for(int j = 0; j<6; j++){
                bufferEscritor.write(String.valueOf(partida[i][j]));
                bufferEscritor.newLine(); // separamos con linea
                }
            }
            bufferEscritor.newLine();
            
            // Escribimos el estado de los botones
            for(int i = 0; i<36; i++){
             
                bufferEscritor.write(String.valueOf(estadosBotones[i]));
                bufferEscritor.newLine();
                
            }
            //puntos
            bufferEscritor.newLine();
            bufferEscritor.write(String.valueOf(puntos));
            //movimientos
            bufferEscritor.newLine();
            bufferEscritor.write(String.valueOf(movimientos));
            //valor de ganaste
            bufferEscritor.newLine();
            bufferEscritor.write(String.valueOf(ganaste));
            
            

            // Cerrar el BufferedWriter para liberar recursos
            bufferEscritor.close();

            JOptionPane.showMessageDialog(this, "Se guardo correctamente");
        } catch (IOException e) {
             e.printStackTrace();
        }
       
       
    
    }
    
   //Importamos la informacion de partida
    public void importarInfo(){
          
        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;
            int valor;
            
            //leemos las primeras 16 lineas del valor del arreglobidimensional
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    
                    linea = br.readLine();
                    valor = Integer.parseInt(linea);
                    partida[i][j] = valor;
                    
                       
                    
                }
            }

            
             linea = br.readLine();
             //leemos las 16 lineas siguientes de los estados de los botones
                for(int a = 0; a < 36; a++){
                linea = br.readLine();
                valor = Integer.parseInt(linea);
                estadosBotones[a] =  valor;
               }
                
             linea = br.readLine();
             //puntos
             linea = br.readLine();
             valor = Integer.parseInt(linea);
             puntos = valor;
             
             //movimientos
             linea = br.readLine();
             valor = Integer.parseInt(linea);
             movimientos = valor;
            
             //estado de ganaste
             linea = br.readLine();
             valor = Integer.parseInt(linea);
             ganaste = valor;

        
            br.close();

            

        } catch (IOException e) {
            e.printStackTrace();
        }
            
        
         
    }
     
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbC1 = new javax.swing.JToggleButton();
        jbC2 = new javax.swing.JToggleButton();
        jbC3 = new javax.swing.JToggleButton();
        jbC4 = new javax.swing.JToggleButton();
        jbC5 = new javax.swing.JToggleButton();
        jbC6 = new javax.swing.JToggleButton();
        jbC7 = new javax.swing.JToggleButton();
        jbC8 = new javax.swing.JToggleButton();
        jbC9 = new javax.swing.JToggleButton();
        jL4x4Game = new javax.swing.JLabel();
        jbC17 = new javax.swing.JToggleButton();
        jbC18 = new javax.swing.JToggleButton();
        jbC16 = new javax.swing.JToggleButton();
        jbC15 = new javax.swing.JToggleButton();
        jbC14 = new javax.swing.JToggleButton();
        jbC13 = new javax.swing.JToggleButton();
        jbC12 = new javax.swing.JToggleButton();
        jbC11 = new javax.swing.JToggleButton();
        jbC10 = new javax.swing.JToggleButton();
        jbC19 = new javax.swing.JToggleButton();
        jbC20 = new javax.swing.JToggleButton();
        jbC21 = new javax.swing.JToggleButton();
        jbC22 = new javax.swing.JToggleButton();
        jbC23 = new javax.swing.JToggleButton();
        jbC24 = new javax.swing.JToggleButton();
        jbC25 = new javax.swing.JToggleButton();
        jbC26 = new javax.swing.JToggleButton();
        jbC27 = new javax.swing.JToggleButton();
        jbC28 = new javax.swing.JToggleButton();
        jbC29 = new javax.swing.JToggleButton();
        jbC30 = new javax.swing.JToggleButton();
        jbC31 = new javax.swing.JToggleButton();
        jbC32 = new javax.swing.JToggleButton();
        jbC33 = new javax.swing.JToggleButton();
        jbC34 = new javax.swing.JToggleButton();
        jbC35 = new javax.swing.JToggleButton();
        jbC36 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jTXmovements = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTXpoints = new javax.swing.JTextField();
        jbtnGuardarPartida = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 0, 51));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        jbC1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC1.setBorder(null);
        jbC1.setBorderPainted(false);
        jbC1.setContentAreaFilled(false);
        jbC1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC1.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC1.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC1.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC2.setBorder(null);
        jbC2.setBorderPainted(false);
        jbC2.setContentAreaFilled(false);
        jbC2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC2.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC2.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC2.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC3.setBorder(null);
        jbC3.setBorderPainted(false);
        jbC3.setContentAreaFilled(false);
        jbC3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC3.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC3.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC3.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC4.setBorder(null);
        jbC4.setBorderPainted(false);
        jbC4.setContentAreaFilled(false);
        jbC4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC4.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC4.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC4.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC5.setBorder(null);
        jbC5.setBorderPainted(false);
        jbC5.setContentAreaFilled(false);
        jbC5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC5.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC5.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC5.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC6.setBorder(null);
        jbC6.setBorderPainted(false);
        jbC6.setContentAreaFilled(false);
        jbC6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC6.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC6.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC6.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC6.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC7.setBorder(null);
        jbC7.setBorderPainted(false);
        jbC7.setContentAreaFilled(false);
        jbC7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC7.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC7.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC7.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC8.setBorder(null);
        jbC8.setBorderPainted(false);
        jbC8.setContentAreaFilled(false);
        jbC8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC8.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC8.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC8.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC8.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC9.setBorder(null);
        jbC9.setBorderPainted(false);
        jbC9.setContentAreaFilled(false);
        jbC9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC9.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC9.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC9.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC9.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jL4x4Game.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/6x6-1.png"))); // NOI18N

        jbC17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC17.setBorder(null);
        jbC17.setBorderPainted(false);
        jbC17.setContentAreaFilled(false);
        jbC17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC17.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC17.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC17.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC17.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC18.setBorder(null);
        jbC18.setBorderPainted(false);
        jbC18.setContentAreaFilled(false);
        jbC18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC18.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC18.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC18.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC18.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC16.setBorder(null);
        jbC16.setBorderPainted(false);
        jbC16.setContentAreaFilled(false);
        jbC16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC16.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC16.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC16.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC16.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC15.setBorder(null);
        jbC15.setBorderPainted(false);
        jbC15.setContentAreaFilled(false);
        jbC15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC15.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC15.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC15.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC15.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC14.setBorder(null);
        jbC14.setBorderPainted(false);
        jbC14.setContentAreaFilled(false);
        jbC14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC14.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC14.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC14.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC14.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC13.setBorder(null);
        jbC13.setBorderPainted(false);
        jbC13.setContentAreaFilled(false);
        jbC13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC13.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC13.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC13.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC13.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC12.setBorder(null);
        jbC12.setBorderPainted(false);
        jbC12.setContentAreaFilled(false);
        jbC12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC12.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC12.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC12.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC12.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC11.setBorder(null);
        jbC11.setBorderPainted(false);
        jbC11.setContentAreaFilled(false);
        jbC11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC11.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC11.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC11.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC11.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC10.setBorder(null);
        jbC10.setBorderPainted(false);
        jbC10.setContentAreaFilled(false);
        jbC10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC10.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC10.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC10.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC10.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC19.setBorder(null);
        jbC19.setBorderPainted(false);
        jbC19.setContentAreaFilled(false);
        jbC19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC19.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC19.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC19.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC19.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC20.setBorder(null);
        jbC20.setBorderPainted(false);
        jbC20.setContentAreaFilled(false);
        jbC20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC20.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC20.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC20.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC20.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC21.setBorder(null);
        jbC21.setBorderPainted(false);
        jbC21.setContentAreaFilled(false);
        jbC21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC21.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC21.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC21.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC21.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC22.setBorder(null);
        jbC22.setBorderPainted(false);
        jbC22.setContentAreaFilled(false);
        jbC22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC22.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC22.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC22.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC22.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC23.setBorder(null);
        jbC23.setBorderPainted(false);
        jbC23.setContentAreaFilled(false);
        jbC23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC23.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC23.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC23.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC23.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC24.setBorder(null);
        jbC24.setBorderPainted(false);
        jbC24.setContentAreaFilled(false);
        jbC24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC24.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC24.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC24.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC24.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC25.setBorder(null);
        jbC25.setBorderPainted(false);
        jbC25.setContentAreaFilled(false);
        jbC25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC25.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC25.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC25.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC25.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC26.setBorder(null);
        jbC26.setBorderPainted(false);
        jbC26.setContentAreaFilled(false);
        jbC26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC26.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC26.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC26.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC26.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC27.setBorder(null);
        jbC27.setBorderPainted(false);
        jbC27.setContentAreaFilled(false);
        jbC27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC27.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC27.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC27.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC27.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC28.setBorder(null);
        jbC28.setBorderPainted(false);
        jbC28.setContentAreaFilled(false);
        jbC28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC28.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC28.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC28.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC28.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC29.setBorder(null);
        jbC29.setBorderPainted(false);
        jbC29.setContentAreaFilled(false);
        jbC29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC29.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC29.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC29.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC29.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC30.setBorder(null);
        jbC30.setBorderPainted(false);
        jbC30.setContentAreaFilled(false);
        jbC30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC30.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC30.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC30.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC30.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC31.setBorder(null);
        jbC31.setBorderPainted(false);
        jbC31.setContentAreaFilled(false);
        jbC31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC31.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC31.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC31.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC31.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC32.setBorder(null);
        jbC32.setBorderPainted(false);
        jbC32.setContentAreaFilled(false);
        jbC32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC32.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC32.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC32.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC32.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC33.setBorder(null);
        jbC33.setBorderPainted(false);
        jbC33.setContentAreaFilled(false);
        jbC33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC33.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC33.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC33.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC33.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC34.setBorder(null);
        jbC34.setBorderPainted(false);
        jbC34.setContentAreaFilled(false);
        jbC34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC34.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC34.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC34.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC34.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC35.setBorder(null);
        jbC35.setBorderPainted(false);
        jbC35.setContentAreaFilled(false);
        jbC35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC35.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC35.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC35.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC35.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC36.setBorder(null);
        jbC36.setBorderPainted(false);
        jbC36.setContentAreaFilled(false);
        jbC36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC36.setMaximumSize(new java.awt.Dimension(70, 110));
        jbC36.setMinimumSize(new java.awt.Dimension(70, 110));
        jbC36.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC36.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbC10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jbC15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbC19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jbC24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbC28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jbC33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jL4x4Game)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jbC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jbC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jbC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jL4x4Game)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbC18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jbC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbC16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbC25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbC34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/movimientos1.png"))); // NOI18N
        jLabel1.setText("\n");

        jTXmovements.setEditable(false);
        jTXmovements.setBackground(new java.awt.Color(255, 255, 255));
        jTXmovements.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/points1.png"))); // NOI18N

        jTXpoints.setEditable(false);
        jTXpoints.setBackground(new java.awt.Color(255, 255, 255));
        jTXpoints.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jbtnGuardarPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save1.png"))); // NOI18N
        jbtnGuardarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarPartidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTXpoints, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTXmovements, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jbtnGuardarPartida))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTXpoints, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTXmovements, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jLabel2)
                    .addComponent(jbtnGuardarPartida))
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnGuardarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarPartidaActionPerformed
        // TODO add your handling code here:

        guardarInfo();
    }//GEN-LAST:event_jbtnGuardarPartidaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jL4x4Game;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTXmovements;
    private javax.swing.JTextField jTXpoints;
    private javax.swing.JToggleButton jbC1;
    private javax.swing.JToggleButton jbC10;
    private javax.swing.JToggleButton jbC11;
    private javax.swing.JToggleButton jbC12;
    private javax.swing.JToggleButton jbC13;
    private javax.swing.JToggleButton jbC14;
    private javax.swing.JToggleButton jbC15;
    private javax.swing.JToggleButton jbC16;
    private javax.swing.JToggleButton jbC17;
    private javax.swing.JToggleButton jbC18;
    private javax.swing.JToggleButton jbC19;
    private javax.swing.JToggleButton jbC2;
    private javax.swing.JToggleButton jbC20;
    private javax.swing.JToggleButton jbC21;
    private javax.swing.JToggleButton jbC22;
    private javax.swing.JToggleButton jbC23;
    private javax.swing.JToggleButton jbC24;
    private javax.swing.JToggleButton jbC25;
    private javax.swing.JToggleButton jbC26;
    private javax.swing.JToggleButton jbC27;
    private javax.swing.JToggleButton jbC28;
    private javax.swing.JToggleButton jbC29;
    private javax.swing.JToggleButton jbC3;
    private javax.swing.JToggleButton jbC30;
    private javax.swing.JToggleButton jbC31;
    private javax.swing.JToggleButton jbC32;
    private javax.swing.JToggleButton jbC33;
    private javax.swing.JToggleButton jbC34;
    private javax.swing.JToggleButton jbC35;
    private javax.swing.JToggleButton jbC36;
    private javax.swing.JToggleButton jbC4;
    private javax.swing.JToggleButton jbC5;
    private javax.swing.JToggleButton jbC6;
    private javax.swing.JToggleButton jbC7;
    private javax.swing.JToggleButton jbC8;
    private javax.swing.JToggleButton jbC9;
    private javax.swing.JButton jbtnGuardarPartida;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
       try{
         Object evt = e.getSource();
         
        
        
        buttons = new JToggleButton[]{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16,
                                      jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24,jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,
                                      jbC32,jbC33,jbC34,jbC35,jbC36};
        for(int i = 0; i<36; i++){
            if(evt.equals(buttons[i])){
                JTbuttonDisable(buttons[i]);
               
            }
            
             
         }
        }catch(Exception a){
             JOptionPane.showMessageDialog(this, a.getStackTrace());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
        try{
        Object evt = e.getSource();
         JToggleButton[] buttons;
        
        
        buttons = new JToggleButton[]{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16,
                                      jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24,jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,
                                      jbC32,jbC33,jbC34,jbC35,jbC36};
        
        for(int i = 0; i<36; i++){
            if(evt.equals(buttons[i])){
                compararCartas();
            
               
            }
            if(buttons[i].isEnabled() == false){
                estadosBotones[i] = 1;
           } else if(buttons[i].isEnabled() == true){
               estadosBotones[i] = 0;
           }
            
          
           }
        }
        catch(Exception a){
             JOptionPane.showMessageDialog(this, a.getStackTrace());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
}
