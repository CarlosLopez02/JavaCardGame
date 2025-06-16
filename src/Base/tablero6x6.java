/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Base;

import java.util.Random;

/**
 *
 * @author Carlos Lopez
 */
public class tablero6x6 extends tablero {
    
    
    
    
    
    
    
    
    
    
    
    
    
    //metodos
    public int [][] devolverTablero(){
        
       
        
        
      int cards[][] = new int [6][6]; 
      int [] cds = new int[36];
      int a = 0;

      Random rand = new Random();
      
      int count1 = 0;
      int count2 = 0;
      
      //llenar arreglo
      for(int i = 0; i<36; i++){
          if(count1 < 18){
          count1++;
          cds[i] = count1;
          } else{
              count2++;
              cds[i] = count2;
          }
         
      }
      
      //mezclar arreglo
      for (int i = cds.length - 1; i > 0; i--) {
            int indiceAleatorio = rand.nextInt(i + 1);
            // cambiamos el arr1[i] con el elemento en el indiceAleatorio
            int temp = cds[i];
            cds[i] = cds[indiceAleatorio];
            cds[indiceAleatorio] = temp;
        }
      
      
      for(int i = 0; i<6; i++){
          for(int j = 0; j<6; j++){
              cards[i][j] = cds[a];
              a++;
          }
      }
        
      
        
        
        
        
        return cards;
    }
    
}
