/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Base.tablero8x8;
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
public class Game8x8 extends javax.swing.JPanel implements ActionListener, MouseListener {

    // Inicialización de variables para el juego de 8x8
    private tablero8x8 juego8x8 = new tablero8x8();// Inicia el juego de memoria
    private ImageIcon crd1;// Imagen de la primera carta seleccionada
    private ImageIcon crd2;// Imagen de la segunda carta seleccionada
    private JToggleButton[] pbtn = new JToggleButton[2];// Guarda las cartas seleccionadas temporalmente
    private JToggleButton btns1 = new JToggleButton();// Botón para la primera carta seleccionada
    private JToggleButton btns2 = new JToggleButton();// Botón para la segunda carta seleccionada
    JToggleButton[] buttons;// Todos los botones del juego
    JToggleButton[][] buttons1;// Organizamos los botones en filas y columnas (para poner imagenes)
   

    boolean up = false;// Indica si hay una carta volteada
    boolean fcard = false;// Indica si se ha volteado la primera carta
    int ganaste = 0;// Contador de pares encontrados
    int movimientos = 0;// contador de movimientos
    int puntos = 0;// contador de puntos
    int[] estadosBotones = new int[64];// Guardamos el estado de cada botón (habilitado o deshabilitado)
    int partida[][] = new int [8][8];// Estado del juego actual
    String ruta = "C:\\Temp\\estadísticas_nivel3.txt";//ruta del archivo de txt de estadisticas del juego
    
    
    /**
     * Creates new form Game8x8
     */
    public Game8x8() {
        initComponents();
        
        
        
         buttons = new JToggleButton[]{jbC1,jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16,
                                       jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24,jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,jbC32,
                                       jbC33,jbC34,jbC35,jbC36,jbC37,jbC38,jbC39,jbC40,jbC41,jbC42,jbC43,jbC44,jbC45,jbC46,jbC47,jbC48,
                                       jbC49,jbC50,jbC51,jbC52,jbC53,jbC54,jbC55,jbC56,jbC57,jbC58,jbC59,jbC60,jbC61,jbC62,jbC63,jbC64};
        
         //agregamos un addActionListener y un addMouseListener a todos los botones que conforman las cartas
         for(int i=0; i<64; i++){
            buttons[i].addActionListener(this);
            buttons[i].addMouseListener(this);
            
        }
    }
    
    
    //empezamos un juego desde 0
    public void cartas(){
        
        try{
        // Obtiene un nuevo tablero del juego
        int [][]numbers = juego8x8.devolverTablero();
        
        // Copia los valores del tablero al estado actual del juego
        for(int a = 0; a<8; a++){
             for(int b = 0; b<8; b++){
                partida[a][b] = numbers[a][b];
           }
        }
           
        // Configuramos las cartas en el tablero de la interfaz gráfica        
        buttons1 = new JToggleButton[][]{{jbC1,jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8},{jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16},
                                        {jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24},{jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,jbC32},
                                        {jbC33,jbC34,jbC35,jbC36,jbC37,jbC38,jbC39,jbC40},{jbC41,jbC42,jbC43,jbC44,jbC45,jbC46,jbC47,jbC48},
                                        {jbC49,jbC50,jbC51,jbC52,jbC53,jbC54,jbC55,jbC56},{jbC57,jbC58,jbC59,jbC60,jbC61,jbC62,jbC63,jbC64}};
        
        // asignamos imagenes cada boton en estado disabled
        for(int i = 0; i < 8; i++){
            for(int j = 0; j<8; j++){
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
         for(int i = 0; i<64; i++){
            buttons[i].setEnabled(true);
         }
         // Importamos la información guardada
         importarInfo();
         
         // Variable para contar los botones y actualizar su estado
        int contar = 0;
       
         // Creamos una matriz de botones 4x4 
        buttons1 = new JToggleButton[][]{{jbC1,jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8},{jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16},
                                        {jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24},{jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,jbC32},
                                        {jbC33,jbC34,jbC35,jbC36,jbC37,jbC38,jbC39,jbC40},{jbC41,jbC42,jbC43,jbC44,jbC45,jbC46,jbC47,jbC48},
                                        {jbC49,jbC50,jbC51,jbC52,jbC53,jbC54,jbC55,jbC56},{jbC57,jbC58,jbC59,jbC60,jbC61,jbC62,jbC63,jbC64}};
        
         // Asignamos la imagen correspondiente a cada botón según la información de la partida
        for(int i = 0; i < 8; i++){
            for(int j = 0; j<8; j++){
            buttons1[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/Cards/card"+partida[i][j]+".png")));
            
            }
        }
         // Actualizamos el estado de los botones según la información guardada
        for(int a = 0; a < 8; a++){
            for(int b = 0; b < 8; b++){
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
  
         buttons = new JToggleButton[]{jbC1,jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16,
                                       jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24,jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,jbC32,
                                       jbC33,jbC34,jbC35,jbC36,jbC37,jbC38,jbC39,jbC40,jbC41,jbC42,jbC43,jbC44,jbC45,jbC46,jbC47,jbC48,
                                       jbC49,jbC50,jbC51,jbC52,jbC53,jbC54,jbC55,jbC56,jbC57,jbC58,jbC59,jbC60,jbC61,jbC62,jbC63,jbC64};
        
         //habilitamos los botones para tapar las cartas
         for(int i = 0; i<64; i++){
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

              if (ganaste == 64) {
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
            for (int i = 0; i < 8; i++) {
                for(int j = 0; j<8; j++){
                bufferEscritor.write(String.valueOf(partida[i][j]));
                bufferEscritor.newLine(); // separamos con linea
                }
            }
            bufferEscritor.newLine();
            
            // Escribimos el estado de los botones
            for(int i = 0; i<64; i++){
             
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
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    
                    linea = br.readLine();
                    valor = Integer.parseInt(linea);
                    partida[i][j] = valor;
                    
                       
                    
                }
            }

            
             linea = br.readLine();
             //leemos las 16 lineas siguientes de los estados de los botones
                for(int a = 0; a < 64; a++){
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
        jL4x4Game = new javax.swing.JLabel();
        jbC2 = new javax.swing.JToggleButton();
        jbC3 = new javax.swing.JToggleButton();
        jbC4 = new javax.swing.JToggleButton();
        jbC5 = new javax.swing.JToggleButton();
        jbC6 = new javax.swing.JToggleButton();
        jbC7 = new javax.swing.JToggleButton();
        jbC8 = new javax.swing.JToggleButton();
        jbC9 = new javax.swing.JToggleButton();
        jbC10 = new javax.swing.JToggleButton();
        jbC11 = new javax.swing.JToggleButton();
        jbC12 = new javax.swing.JToggleButton();
        jbC13 = new javax.swing.JToggleButton();
        jbC14 = new javax.swing.JToggleButton();
        jbC15 = new javax.swing.JToggleButton();
        jbC16 = new javax.swing.JToggleButton();
        jbC17 = new javax.swing.JToggleButton();
        jbC18 = new javax.swing.JToggleButton();
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
        jbC37 = new javax.swing.JToggleButton();
        jbC38 = new javax.swing.JToggleButton();
        jbC39 = new javax.swing.JToggleButton();
        jbC40 = new javax.swing.JToggleButton();
        jbC41 = new javax.swing.JToggleButton();
        jbC42 = new javax.swing.JToggleButton();
        jbC43 = new javax.swing.JToggleButton();
        jbC44 = new javax.swing.JToggleButton();
        jbC45 = new javax.swing.JToggleButton();
        jbC46 = new javax.swing.JToggleButton();
        jbC47 = new javax.swing.JToggleButton();
        jbC48 = new javax.swing.JToggleButton();
        jbC49 = new javax.swing.JToggleButton();
        jbC50 = new javax.swing.JToggleButton();
        jbC51 = new javax.swing.JToggleButton();
        jbC52 = new javax.swing.JToggleButton();
        jbC53 = new javax.swing.JToggleButton();
        jbC54 = new javax.swing.JToggleButton();
        jbC55 = new javax.swing.JToggleButton();
        jbC56 = new javax.swing.JToggleButton();
        jbC57 = new javax.swing.JToggleButton();
        jbC58 = new javax.swing.JToggleButton();
        jbC59 = new javax.swing.JToggleButton();
        jbC60 = new javax.swing.JToggleButton();
        jbC61 = new javax.swing.JToggleButton();
        jbC62 = new javax.swing.JToggleButton();
        jbC63 = new javax.swing.JToggleButton();
        jbC64 = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jTXpoints = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTXmovements = new javax.swing.JTextField();
        jbtnGuardarPartida = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 0, 51));
        setMaximumSize(new java.awt.Dimension(950, 690));
        setMinimumSize(new java.awt.Dimension(950, 690));
        setPreferredSize(new java.awt.Dimension(1350, 650));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setPreferredSize(new java.awt.Dimension(1360, 500));

        jbC1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC1.setBorder(null);
        jbC1.setBorderPainted(false);
        jbC1.setContentAreaFilled(false);
        jbC1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC1.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jL4x4Game.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/8x8-1.png"))); // NOI18N

        jbC2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC2.setBorder(null);
        jbC2.setBorderPainted(false);
        jbC2.setContentAreaFilled(false);
        jbC2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC2.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC3.setBorder(null);
        jbC3.setBorderPainted(false);
        jbC3.setContentAreaFilled(false);
        jbC3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC3.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC4.setBorder(null);
        jbC4.setBorderPainted(false);
        jbC4.setContentAreaFilled(false);
        jbC4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC4.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC5.setBorder(null);
        jbC5.setBorderPainted(false);
        jbC5.setContentAreaFilled(false);
        jbC5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC5.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC6.setBorder(null);
        jbC6.setBorderPainted(false);
        jbC6.setContentAreaFilled(false);
        jbC6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC6.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC6.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC7.setBorder(null);
        jbC7.setBorderPainted(false);
        jbC7.setContentAreaFilled(false);
        jbC7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC7.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC7.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC8.setBorder(null);
        jbC8.setBorderPainted(false);
        jbC8.setContentAreaFilled(false);
        jbC8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC8.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC8.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC9.setBorder(null);
        jbC9.setBorderPainted(false);
        jbC9.setContentAreaFilled(false);
        jbC9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC9.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC9.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC10.setBorder(null);
        jbC10.setBorderPainted(false);
        jbC10.setContentAreaFilled(false);
        jbC10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC10.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC10.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC11.setBorder(null);
        jbC11.setBorderPainted(false);
        jbC11.setContentAreaFilled(false);
        jbC11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC11.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC11.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC12.setBorder(null);
        jbC12.setBorderPainted(false);
        jbC12.setContentAreaFilled(false);
        jbC12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC12.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC12.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC13.setBorder(null);
        jbC13.setBorderPainted(false);
        jbC13.setContentAreaFilled(false);
        jbC13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC13.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC13.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC14.setBorder(null);
        jbC14.setBorderPainted(false);
        jbC14.setContentAreaFilled(false);
        jbC14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC14.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC14.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC15.setBorder(null);
        jbC15.setBorderPainted(false);
        jbC15.setContentAreaFilled(false);
        jbC15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC15.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC15.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC16.setBorder(null);
        jbC16.setBorderPainted(false);
        jbC16.setContentAreaFilled(false);
        jbC16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC16.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC16.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC17.setBorder(null);
        jbC17.setBorderPainted(false);
        jbC17.setContentAreaFilled(false);
        jbC17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC17.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC17.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC18.setBorder(null);
        jbC18.setBorderPainted(false);
        jbC18.setContentAreaFilled(false);
        jbC18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC18.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC18.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC19.setBorder(null);
        jbC19.setBorderPainted(false);
        jbC19.setContentAreaFilled(false);
        jbC19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC19.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC19.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC20.setBorder(null);
        jbC20.setBorderPainted(false);
        jbC20.setContentAreaFilled(false);
        jbC20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC20.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC20.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC21.setBorder(null);
        jbC21.setBorderPainted(false);
        jbC21.setContentAreaFilled(false);
        jbC21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC21.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC21.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC22.setBorder(null);
        jbC22.setBorderPainted(false);
        jbC22.setContentAreaFilled(false);
        jbC22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC22.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC22.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC23.setBorder(null);
        jbC23.setBorderPainted(false);
        jbC23.setContentAreaFilled(false);
        jbC23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC23.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC23.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC24.setBorder(null);
        jbC24.setBorderPainted(false);
        jbC24.setContentAreaFilled(false);
        jbC24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC24.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC24.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC25.setBorder(null);
        jbC25.setBorderPainted(false);
        jbC25.setContentAreaFilled(false);
        jbC25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC25.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC25.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC26.setBorder(null);
        jbC26.setBorderPainted(false);
        jbC26.setContentAreaFilled(false);
        jbC26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC26.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC26.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC27.setBorder(null);
        jbC27.setBorderPainted(false);
        jbC27.setContentAreaFilled(false);
        jbC27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC27.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC27.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC28.setBorder(null);
        jbC28.setBorderPainted(false);
        jbC28.setContentAreaFilled(false);
        jbC28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC28.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC28.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC29.setBorder(null);
        jbC29.setBorderPainted(false);
        jbC29.setContentAreaFilled(false);
        jbC29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC29.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC29.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC30.setBorder(null);
        jbC30.setBorderPainted(false);
        jbC30.setContentAreaFilled(false);
        jbC30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC30.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC30.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC31.setBorder(null);
        jbC31.setBorderPainted(false);
        jbC31.setContentAreaFilled(false);
        jbC31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC31.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC31.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC32.setBorder(null);
        jbC32.setBorderPainted(false);
        jbC32.setContentAreaFilled(false);
        jbC32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC32.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC32.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC33.setBorder(null);
        jbC33.setBorderPainted(false);
        jbC33.setContentAreaFilled(false);
        jbC33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC33.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC33.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC34.setBorder(null);
        jbC34.setBorderPainted(false);
        jbC34.setContentAreaFilled(false);
        jbC34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC34.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC34.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC35.setBorder(null);
        jbC35.setBorderPainted(false);
        jbC35.setContentAreaFilled(false);
        jbC35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC35.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC35.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC36.setBorder(null);
        jbC36.setBorderPainted(false);
        jbC36.setContentAreaFilled(false);
        jbC36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC36.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC36.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC37.setBorder(null);
        jbC37.setBorderPainted(false);
        jbC37.setContentAreaFilled(false);
        jbC37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC37.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC37.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC38.setBorder(null);
        jbC38.setBorderPainted(false);
        jbC38.setContentAreaFilled(false);
        jbC38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC38.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC38.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC39.setBorder(null);
        jbC39.setBorderPainted(false);
        jbC39.setContentAreaFilled(false);
        jbC39.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC39.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC39.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC40.setBorder(null);
        jbC40.setBorderPainted(false);
        jbC40.setContentAreaFilled(false);
        jbC40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC40.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC40.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC41.setBorder(null);
        jbC41.setBorderPainted(false);
        jbC41.setContentAreaFilled(false);
        jbC41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC41.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC41.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC42.setBorder(null);
        jbC42.setBorderPainted(false);
        jbC42.setContentAreaFilled(false);
        jbC42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC42.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC42.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC43.setBorder(null);
        jbC43.setBorderPainted(false);
        jbC43.setContentAreaFilled(false);
        jbC43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC43.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC43.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC44.setBorder(null);
        jbC44.setBorderPainted(false);
        jbC44.setContentAreaFilled(false);
        jbC44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC44.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC44.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC45.setBorder(null);
        jbC45.setBorderPainted(false);
        jbC45.setContentAreaFilled(false);
        jbC45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC45.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC45.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC46.setBorder(null);
        jbC46.setBorderPainted(false);
        jbC46.setContentAreaFilled(false);
        jbC46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC46.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC46.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC47.setBorder(null);
        jbC47.setBorderPainted(false);
        jbC47.setContentAreaFilled(false);
        jbC47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC47.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC47.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC48.setBorder(null);
        jbC48.setBorderPainted(false);
        jbC48.setContentAreaFilled(false);
        jbC48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC48.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC48.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC49.setBorder(null);
        jbC49.setBorderPainted(false);
        jbC49.setContentAreaFilled(false);
        jbC49.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC49.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC49.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC50.setBorder(null);
        jbC50.setBorderPainted(false);
        jbC50.setContentAreaFilled(false);
        jbC50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC50.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC50.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC51.setBorder(null);
        jbC51.setBorderPainted(false);
        jbC51.setContentAreaFilled(false);
        jbC51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC51.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC51.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC52.setBorder(null);
        jbC52.setBorderPainted(false);
        jbC52.setContentAreaFilled(false);
        jbC52.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC52.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC52.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC53.setBorder(null);
        jbC53.setBorderPainted(false);
        jbC53.setContentAreaFilled(false);
        jbC53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC53.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC53.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC54.setBorder(null);
        jbC54.setBorderPainted(false);
        jbC54.setContentAreaFilled(false);
        jbC54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC54.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC54.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC55.setBorder(null);
        jbC55.setBorderPainted(false);
        jbC55.setContentAreaFilled(false);
        jbC55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC55.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC55.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC56.setBorder(null);
        jbC56.setBorderPainted(false);
        jbC56.setContentAreaFilled(false);
        jbC56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC56.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC56.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC57.setBorder(null);
        jbC57.setBorderPainted(false);
        jbC57.setContentAreaFilled(false);
        jbC57.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC57.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC57.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC58.setBorder(null);
        jbC58.setBorderPainted(false);
        jbC58.setContentAreaFilled(false);
        jbC58.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC58.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC58.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC59.setBorder(null);
        jbC59.setBorderPainted(false);
        jbC59.setContentAreaFilled(false);
        jbC59.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC59.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC59.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC60.setBorder(null);
        jbC60.setBorderPainted(false);
        jbC60.setContentAreaFilled(false);
        jbC60.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC60.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC60.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC61.setBorder(null);
        jbC61.setBorderPainted(false);
        jbC61.setContentAreaFilled(false);
        jbC61.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC61.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC61.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC62.setBorder(null);
        jbC62.setBorderPainted(false);
        jbC62.setContentAreaFilled(false);
        jbC62.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC62.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC62.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC63.setBorder(null);
        jbC63.setBorderPainted(false);
        jbC63.setContentAreaFilled(false);
        jbC63.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC63.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC63.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC64.setBorder(null);
        jbC64.setBorderPainted(false);
        jbC64.setContentAreaFilled(false);
        jbC64.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC64.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC64.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(586, 586, 586)
                        .addComponent(jL4x4Game))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbC17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbC33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbC49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbC64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jL4x4Game)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbC17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbC33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbC49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/points1.png"))); // NOI18N

        jTXpoints.setEditable(false);
        jTXpoints.setBackground(new java.awt.Color(255, 255, 255));
        jTXpoints.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/movimientos1.png"))); // NOI18N
        jLabel1.setText("\n");

        jTXmovements.setEditable(false);
        jTXmovements.setBackground(new java.awt.Color(255, 255, 255));
        jTXmovements.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

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
                .addContainerGap(69, Short.MAX_VALUE)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTXpoints, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTXmovements, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jLabel2)
                    .addComponent(jbtnGuardarPartida))
                .addContainerGap(124, Short.MAX_VALUE))
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
    private javax.swing.JToggleButton jbC37;
    private javax.swing.JToggleButton jbC38;
    private javax.swing.JToggleButton jbC39;
    private javax.swing.JToggleButton jbC4;
    private javax.swing.JToggleButton jbC40;
    private javax.swing.JToggleButton jbC41;
    private javax.swing.JToggleButton jbC42;
    private javax.swing.JToggleButton jbC43;
    private javax.swing.JToggleButton jbC44;
    private javax.swing.JToggleButton jbC45;
    private javax.swing.JToggleButton jbC46;
    private javax.swing.JToggleButton jbC47;
    private javax.swing.JToggleButton jbC48;
    private javax.swing.JToggleButton jbC49;
    private javax.swing.JToggleButton jbC5;
    private javax.swing.JToggleButton jbC50;
    private javax.swing.JToggleButton jbC51;
    private javax.swing.JToggleButton jbC52;
    private javax.swing.JToggleButton jbC53;
    private javax.swing.JToggleButton jbC54;
    private javax.swing.JToggleButton jbC55;
    private javax.swing.JToggleButton jbC56;
    private javax.swing.JToggleButton jbC57;
    private javax.swing.JToggleButton jbC58;
    private javax.swing.JToggleButton jbC59;
    private javax.swing.JToggleButton jbC6;
    private javax.swing.JToggleButton jbC60;
    private javax.swing.JToggleButton jbC61;
    private javax.swing.JToggleButton jbC62;
    private javax.swing.JToggleButton jbC63;
    private javax.swing.JToggleButton jbC64;
    private javax.swing.JToggleButton jbC7;
    private javax.swing.JToggleButton jbC8;
    private javax.swing.JToggleButton jbC9;
    private javax.swing.JButton jbtnGuardarPartida;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
         Object evt = e.getSource();
         
        
        
        buttons = new JToggleButton[]{jbC1,jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16,
                                       jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24,jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,jbC32,
                                       jbC33,jbC34,jbC35,jbC36,jbC37,jbC38,jbC39,jbC40,jbC41,jbC42,jbC43,jbC44,jbC45,jbC46,jbC47,jbC48,
                                       jbC49,jbC50,jbC51,jbC52,jbC53,jbC54,jbC55,jbC56,jbC57,jbC58,jbC59,jbC60,jbC61,jbC62,jbC63,jbC64};
        
        for(int i = 0; i<64; i++){
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
        
        
        buttons = new JToggleButton[]{jbC1,jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16,
                                       jbC17,jbC18,jbC19,jbC20,jbC21,jbC22,jbC23,jbC24,jbC25,jbC26,jbC27,jbC28,jbC29,jbC30,jbC31,jbC32,
                                       jbC33,jbC34,jbC35,jbC36,jbC37,jbC38,jbC39,jbC40,jbC41,jbC42,jbC43,jbC44,jbC45,jbC46,jbC47,jbC48,
                                       jbC49,jbC50,jbC51,jbC52,jbC53,jbC54,jbC55,jbC56,jbC57,jbC58,jbC59,jbC60,jbC61,jbC62,jbC63,jbC64};
        
        for(int i = 0; i<64; i++){
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
