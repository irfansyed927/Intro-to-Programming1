/* This program prompts the user to enter the center coordinate and radius of three different circles. It then draws 
  * the circles and tells the user how their sizes compare and whether or not they intersect. 
  */ 
 import static java.lang.System.out; 
 import java.awt.*; 
 
 
 public class Circles { 
   private static final int WIDTH = 400; 
   private static final int HEIGHT = 300; 
    
   public static void main(String args[]) { 
     System.out.println("Lab 5 written by Irfan Syed"); 
      
     System.out.println("Please enter data for the red circle:"); 
     int coord0[] = promptCoord("Center (x,y): "); 
     int r0 = promptInt("Radius: "); 
      
     System.out.println("Please enter data for the green circle:"); 
    int coord1[] = promptCoord("Center (x,y): "); 
     int r1 = promptInt("Radius: "); 
      
     System.out.println("Please enter data for the blue circle:"); 
     int coord2[] = promptCoord("Center (x,y): "); 
     int r2 = promptInt("Radius: "); 
   
     DrawingPanel panel = new DrawingPanel(WIDTH,HEIGHT); 
     Graphics g = panel.getGraphics(); 
      
     g.setColor(Color.RED); 
     g.fillOval(coord0[0] - r0, coord0[1] - r0, r0*2, r0*2); 
      
     g.setColor(Color.GREEN); 
     g.fillOval(coord1[0] - r1, coord1[1] - r1, r1*2, r1*2); 
     
    g.setColor(Color.BLUE); 
     g.fillOval(coord2[0] - r2, coord2[1] - r2, r2*2, r2*2); 
      
     switch(compareSizes(r0,r1)) { 
       case -1: 
         out.println("The red circle is smaller than the green circle."); 
         break; 
       case 0: 
         System.out.println("The red circle and the green circle are the same size."); 
        break; 
       case 1: 
         System.out.println("The green circle is smaller than the red circle."); 
     } 
      
     switch(compareSizes(r0,r2)) { 
       case -1: 
      System.out.println("The red circle is smaller than the blue circle.");          break; 
      case 0: 
         System.out.println("The red circle and the blue circle are the same size."); 
         break; 
       case 1: 
         System.out.println("The blue circle is smaller than the red circle."); 
     } 
      
     switch(compareSizes(r1,r2)) { 
       case -1: 
         System.out.println("The green circle is smaller than the blue circle."); 
         break; 
       case 0: 
         System.out.println("The green circle and the blue circle are the same size."); 
        break; 
       case 1: 
         System.out.println("The blue circle is smaller than the green circle."); 
     } 
      
     if(circlesIntersect(coord0[0], coord0[1], r0, coord1[0], coord1[1], r1)) 
       System.out.println("The red circle intersects the green circle."); 
  else 
       System.out.println("The red circle does not intersect the green circle."); 
    
     if(circlesIntersect(coord0[0], coord0[1], r0, coord2[0], coord2[1], r2)) 
       System.out.println("The red circle intersects the blue circle."); 
     else 
       System.out.println("The red circle does not intersect the blue circle."); 
      
     if(circlesIntersect(coord1[0], coord1[1], r1, coord2[0], coord2[1], r2)) 
      System.out.println("The green circle intersects the blue circle."); 
     else 
       System.out.println("The green circle does not intersect the blue circle."); 
} 
    
    
   //This method prompts the user for an integer value and retuns that value. 
   private static int promptInt(String prompt) { 
     while(true) { 
       java.util.Scanner input = new java.util.Scanner(System.in); 
        
       try { 
      System.out.print(prompt); 
         return input.nextInt(); 
       } 
       catch (Exception e) { 
         System.out.println("ERROR: Input must be an integer value."); 
       } 
     } 
   } 
    
  //This method prompts the user to enter an x,y coordiante and returns that as an integer array. 
   private static int[] promptCoord(String prompt) { 
     while(true) { 
       java.util.Scanner input = new java.util.Scanner(System.in); 
       int returnVal[] = {0,0}; 
       System.out.print(prompt); 
       String coordString = input.nextLine(); 
        
       int i = 0; 
       try { 
         while(coordString.charAt(i) != ','){ 
           i++; 
         } 
          
         returnVal[0] = Integer.parseInt(coordString.substring(0,i).trim()); 
         returnVal[1] = Integer.parseInt(coordString.substring(i+1,coordString.length()).trim()); 
         return returnVal; 
          
       } catch(Exception e) { 
     System.out.println("ERROR: Input must be in the format x,y where x and y are integer values."); 
       } 
     } 
   } 
    
       /*This method compares the sizes of two circles and returns -1 if the first is smaller, 0 if they are the same 
    * size, and 1 if the first is larger. 
    */ 
   private static int compareSizes(int r0, int r1){ 
     if(r0 < r1) 
       return -1; 
     if(r0 == r1) 
       return 0; 
     return 1;  
   } 
    
   //This method returns true if the circles intersect, and false if they don't. 
   private static boolean circlesIntersect(int x0, int y0, int r0, int x1, int y1, int r1) { 
     return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2)) <= r0 + r1; 
   } 
 } 
