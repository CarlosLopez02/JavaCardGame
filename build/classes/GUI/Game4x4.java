/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;
import Base.tablero4x4;
import javax.swing.ImageIcon;
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
import javax.swing.JOptionPane;


/**
 *
 * @author Carlos Lopez
 */
public class Game4x4 extends javax.swing.JPanel implements ActionListener, MouseListener{

    // Inicialización de variables para el juego de 4x4
    private tablero4x4 juego4x4 = new tablero4x4(); // Inicia el juego de memoria
    private ImageIcon crd1; // Imagen de la primera carta seleccionada
    private ImageIcon crd2; // Imagen de la segunda carta seleccionada
    private JToggleButton[] pbtn = new JToggleButton[2]; // Guarda las cartas seleccionadas temporalmente
    private JToggleButton btns1 = new JToggleButton(); // Botón para la primera carta seleccionada
    private JToggleButton btns2 = new JToggleButton(); // Botón para la segunda carta seleccionada
    JToggleButton[] buttons; // Todos los botones del juego
    JToggleButton[][] buttons1; // Organizamos los botones en filas y columnas (para poner imagenes)
   

    boolean up = false; // Se ha seleccionado una carta
    boolean fcard = false; // ¿Es la primera o segunda carta?
    int ganaste = 0; // Contador de pares encontrados
    int movimientos = 0; // contador de movimientos
    int puntos = 0; // contador de puntos
    int[] estadosBotones = new int[16]; // Guardamos el estado de cada botón (habilitado o deshabilitado)
    int partida[][] = new int [4][4]; // Estado del juego actual
    String ruta = "C:\\Temp\\estadísticas_nivel1.txt"; //ruta del archivo de txt de estadisticas del juego
       
    
    
    /**
     * Creates new form Game4x4
     */
    
    
    public Game4x4() {
        initComponents();
        
        
        
        buttons = new JToggleButton[]{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16};
        
        //agregamos un addActionListener y un addMouseListener a todos los botones que conforman las cartas
        for(int i=0; i<16; i++){
            buttons[i].addActionListener(this);
            buttons[i].addMouseListener(this);
            
        }
        
        
        
        
        
    }

    //empezamos un juego desde 0
    public void cartas(){
                       
        try{
            
        // Obtiene un nuevo tablero del juego
        int[][] numbers = juego4x4.devolverTablero();
        
         // Copia los valores del tablero al estado actual del juego
        for(int a = 0; a<4; a++){
             for(int b = 0; b<4; b++){
                partida[a][b] = numbers[a][b];
           }
        }
        
        // Configuramos las cartas en el tablero de la interfaz gráfica
        buttons1 = new JToggleButton[][]{{jbC1, jbC2,jbC3,jbC4},{jbC5,jbC6,jbC7,jbC8},{jbC9,jbC10,jbC11,jbC12},{jbC13,jbC14,jbC15,jbC16}};
        
         // asignamos imagenes cada boton en estado disabled
        for(int i = 0; i < 4; i++){
            for(int j = 0; j<4; j++){
            buttons1[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/Cards/card"+numbers[i][j]+".png")));
            }
        }
        ganaste = 0;// Reinicia el contador de pares encontrados
        } 
        catch(Exception e){
            JOptionPane.showMessageDialog(this, e.getStackTrace());
        }
        
    }
    
    //inicializamos el juego pero de leyendo la informacion guardada
     public void Juegoguardado(){
         
         try{
         // Habilitamos todos los botones para comenzar un nuevo juego
         for(int i = 0; i<16; i++){
            buttons[i].setEnabled(true);
         }
         // Importamos la información guardada
         importarInfo();
         
         // Variable para contar los botones y actualizar su estado
        int contar = 0;
       
       // Creamos una matriz de botones 4x4 
        buttons1 = new JToggleButton[][]{{jbC1, jbC2,jbC3,jbC4},{jbC5,jbC6,jbC7,jbC8},{jbC9,jbC10,jbC11,jbC12},{jbC13,jbC14,jbC15,jbC16}};
        
       // Asignamos la imagen correspondiente a cada botón según la información de la partida
        for(int i = 0; i < 4; i++){
            for(int j = 0; j<4; j++){
            buttons1[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/Cards/card"+partida[i][j]+".png")));
            
            }
        }
        // Actualizamos el estado de los botones según la información guardada
        for(int a = 0; a < 4; a++){
            for(int b = 0; b < 4; b++){
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
     
     // Reniciamos el estado del juego
      public void reiniciar(){
        try{
       
        buttons = new JToggleButton[]{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16};
        
        for(int i = 0; i<16; i++){
            buttons[i].setEnabled(true);
        }
        
        fcard = false;
        up = false;
        jTXmovements.setText(String.valueOf(0));
        jTXpoints.setText(String.valueOf(0));
        movimientos = 0;
        puntos = 0;
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
                btn.setEnabled(false);
                crd1 = (ImageIcon) btn.getDisabledIcon();
                btns1 = btn;
                fcard = false;
                movimientos += 1;
                 jTXmovements.setText(String.valueOf(movimientos));
            } else {
                btn.setEnabled(false);
                crd2 = (ImageIcon) btn.getDisabledIcon();
                btns2 = btn;
                fcard = true;
                movimientos += 1;
                 jTXmovements.setText(String.valueOf(movimientos));
            }

       
           
          
            up = true;
           }
           catch(Exception e){
               JOptionPane.showMessageDialog(this, e.getStackTrace());
           }
        
    }
    
    
    
    
    
    //comparamos las cartas
    private void compararCartas(){
        
        try{
       if(up && fcard){
            
            if(crd1.getDescription().compareTo(crd2.getDescription()) != 0){
                btns1.setEnabled(true);
                btns2.setEnabled(true);
                if(puntos > 0){
                puntos -= 1;
                }
                
            }else{
              puntos += 5;
             ganaste += 2;
            }
            
            jTXpoints.setText(String.valueOf(puntos));
            up = false;
            
            if(ganaste == 16){
             JOptionPane.showMessageDialog(this, "Ganaste");
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
            for (int i = 0; i < 4; i++) {
                for(int j = 0; j<4; j++){
                bufferEscritor.write(String.valueOf(partida[i][j]));
                bufferEscritor.newLine(); // separamos con linea
                }
            }
            bufferEscritor.newLine();
            
            // Escribimos el estado de los botones
            for(int i = 0; i<16; i++){
             
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
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    
                    linea = br.readLine();
                    valor = Integer.parseInt(linea);
                    partida[i][j] = valor;
                    
                       
                    
                }
            }

            
             linea = br.readLine();
             //leemos las 16 lineas siguientes de los estados de los botones
                for(int a = 0; a < 16; a++){
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
        jbC10 = new javax.swing.JToggleButton();
        jbC9 = new javax.swing.JToggleButton();
        jbC12 = new javax.swing.JToggleButton();
        jbC11 = new javax.swing.JToggleButton();
        jbC16 = new javax.swing.JToggleButton();
        jbC13 = new javax.swing.JToggleButton();
        jbC15 = new javax.swing.JToggleButton();
        jbC14 = new javax.swing.JToggleButton();
        jL4x4Game = new javax.swing.JLabel();
        jTXmovements = new javax.swing.JTextField();
        jbtnGuardarPartida = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTXpoints = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 0, 51));
        setPreferredSize(new java.awt.Dimension(570, 650));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        jbC1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC1.setBorder(null);
        jbC1.setBorderPainted(false);
        jbC1.setContentAreaFilled(false);
        jbC1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC1.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

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

        jbC10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC10.setBorder(null);
        jbC10.setBorderPainted(false);
        jbC10.setContentAreaFilled(false);
        jbC10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC10.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC10.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC9.setBorder(null);
        jbC9.setBorderPainted(false);
        jbC9.setContentAreaFilled(false);
        jbC9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC9.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC9.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC12.setBorder(null);
        jbC12.setBorderPainted(false);
        jbC12.setContentAreaFilled(false);
        jbC12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC12.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC12.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC11.setBorder(null);
        jbC11.setBorderPainted(false);
        jbC11.setContentAreaFilled(false);
        jbC11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC11.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC11.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC16.setBorder(null);
        jbC16.setBorderPainted(false);
        jbC16.setContentAreaFilled(false);
        jbC16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC16.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC16.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC13.setBorder(null);
        jbC13.setBorderPainted(false);
        jbC13.setContentAreaFilled(false);
        jbC13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC13.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC13.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC15.setBorder(null);
        jbC15.setBorderPainted(false);
        jbC15.setContentAreaFilled(false);
        jbC15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC15.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC15.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jbC14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta.png"))); // NOI18N
        jbC14.setBorder(null);
        jbC14.setBorderPainted(false);
        jbC14.setContentAreaFilled(false);
        jbC14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbC14.setPreferredSize(new java.awt.Dimension(70, 110));
        jbC14.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/carta_hover.png"))); // NOI18N

        jL4x4Game.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/4x4-1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbC13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbC15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jbC16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbC3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jbC8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbC12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(jL4x4Game)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jL4x4Game)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbC11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbC12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbC13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbC16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jTXmovements.setEditable(false);
        jTXmovements.setBackground(new java.awt.Color(255, 255, 255));
        jTXmovements.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jbtnGuardarPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/save1.png"))); // NOI18N
        jbtnGuardarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarPartidaActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/movimientos1.png"))); // NOI18N
        jLabel1.setText("\n");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/points1.png"))); // NOI18N

        jTXpoints.setEditable(false);
        jTXpoints.setBackground(new java.awt.Color(255, 255, 255));
        jTXpoints.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jTXmovements, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jTXpoints, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jbtnGuardarPartida)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTXmovements, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTXpoints, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jbtnGuardarPartida)))
                .addContainerGap(81, Short.MAX_VALUE))
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
    private javax.swing.JToggleButton jbC2;
    private javax.swing.JToggleButton jbC3;
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
         
        
        
        buttons = new JToggleButton[]{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16};
        
        for(int i = 0; i<16; i++){
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
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
        try{
        Object evt = e.getSource();
         JToggleButton[] buttons;
        
        
        buttons = new JToggleButton[]{jbC1, jbC2,jbC3,jbC4,jbC5,jbC6,jbC7,jbC8,jbC9,jbC10,jbC11,jbC12,jbC13,jbC14,jbC15,jbC16};
        
        for(int i = 0; i<16; i++){
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
      //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  

   
}
