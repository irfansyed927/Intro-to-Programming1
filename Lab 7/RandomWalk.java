import java.util.Scanner; 
 import java.util.Random; 
import java.awt.*;  
  public class RandomWalk { 
   private static final Scanner CONSOLE = new Scanner(System.in); 
       public static void main(String args[]) { 
     System.out.println("Lab 7 written by Irfan Syed"); 
     int rad = promptIntWithRange("Please enter the circle's radius (50-400 pixels): ", 50, 400); 
      
     boolean cont = true; 
     Color color = null; 
     while(cont) { 
       String colorStr = promptString("Please enter the color of the circle (green [g] or blue [b]): "); 
       if(matchesChoice(colorStr, "green")) { 
         color = Color.GREEN; 
         cont = false; 
       } 
       else if(matchesChoice(colorStr, "blue")) { 
         color = Color.BLUE; 
         cont = false; 
       } 
       else { 
         System.err.println("ERROR: \"" + colorStr +  
                            "\" is an invalid choice. Please enter \"green\", \"blue\", \"g\", or \"b\" (Not case-sensitive)."); 
       } 
     } 
      
     DrawingPanel panel = new DrawingPanel(rad * 2 + 32, rad * 2 + 32); 
     Graphics g = panel.getGraphics(); 
     g.setColor(color); 
     g.drawOval(16,16,rad * 2,rad * 2); 
     g.setColor(Color.BLUE); 
      
     int x = 16 + rad; 
     int y = 16 + rad; 
     g.drawLine(x,y,x,y); 
     Random rand = new Random(); 
     int dir = 0; 
     int cycles = 0; 
     while(pointInsideCircle(x,y,rad+16,rad+16,rad)) { 
       dir = rand.nextInt(4); 
       switch(dir) { 
         case 0: 
           g.drawLine(x,--y,x,y); 
           break; 
         case 1: 
           g.drawLine(++x,y,x,y); 
           break; 
         case 2: 
           g.drawLine(x,++y,x,y); 
           break; 
         case 3: 
           g.drawLine(--x,y,x,y); 
           break; 
       } 
       cycles++; 
       panel.sleep(1); 
     } 
     System.out.println("After " + cycles + " steps, the walk is finished."); 
   } 
    
   private static String promptString(String prompt) { 
     System.out.print(prompt); 
     String str = CONSOLE.nextLine(); 
     if(str.length() == 0) 
       str = CONSOLE.nextLine(); 
     return str; 
      
   } 
    
   private static int promptIntWithRange(String prompt, int min, int max) { 
     int n = 0; 
     boolean cont = true; 
      
     while(cont) { 
       System.out.print(prompt); 
       if(CONSOLE.hasNextInt()) { 
         n = CONSOLE.nextInt(); 
         if(n >= min && n <= max) 
           cont = false; 
         else 
           //Handle errors where the user enters an integer outside the range 
           System.err.println("ERROR: " + n + " is not between " + min + " and " + max + "."); 
       } 
       else { 
         //Handle errors where the user enters something besides an int 
         String str = CONSOLE.nextLine(); 
          
         //This check avoids posting an error with an empty string (leftover data in RAM) 
         if(str.length() == 0) 
           str = CONSOLE.nextLine(); 
          
         if (str.length() >= 22 && str.toLowerCase().substring(0,22).equals("open the pod bay doors")) 
           System.err.println("I'm sorry, Dave. I'm afraid I can't do that."); 
         else 
           System.err.println("ERROR: \"" + str + "\" is not an integer value."); 
       } 
     } 
     return n; 
   } 
    
   private static boolean matchesChoice(String str1, String str2) { 
     str1 = str1.toLowerCase().trim(); 
     str2 = str2.toLowerCase().trim(); 
     if(str1.length() > 1) 
       return str1.equals(str2); 
     else if(str1.length() == 1) 
       return str1.charAt(0) == str2.charAt(0); 
     else 
       return false; 
   } 
    
   private static boolean pointInsideCircle(int x, int y, int cx, int cy, int r) { 
     return Math.pow(x-cx,2) + Math.pow(y-cy,2) < Math.pow(r,2); 
   } 
 } 
