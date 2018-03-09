import java.util.Random; 
 import java.util.Scanner; 
 
 
 public class Playoffs { 
   private static final Scanner CONSOLE = new Scanner(System.in); 
    
   public static void main(String args[]) { 
     System.out.println("Lab 6 written by Irfan Syed"); 
      
     int chance = promptIntWithRange("Please enter the percentage chance team 1 will win :",0,100); 
     Random rand = new Random(); 
     championship(rand,chance); 
   } 
    
   //This method simulates the outcome of a single game. 
   private static boolean singleGame(Random r, int chance) { 
     int score = r.nextInt(100); 
     return score < chance; 
   } 
    
   /* This method simulates the outcome of a playoff. A team wins a playoff if they win 4 games before the other 
    * team does. 
    */ 
   private static boolean playoff(Random r, int chance) { 
     int team1Wins = 0; 
     int team2Wins = 0; 
     while(team1Wins < 4 && team2Wins < 4) { 
       if(singleGame(r,chance)) 
         team1Wins++; 
       else 
         team2Wins++; 
     } 
     return team1Wins > team2Wins; 
   } 
    
   //This method simulates a championship. A team wins the championship if they beat the other team by 10 playoffs. 
   private static void championship(Random r, int chance) { 
     int team1Wins = 0; 
     int team2Wins = 0; 
     while(team1Wins < team2Wins + 10 && team2Wins < team1Wins + 10) { 
       if(playoff(r,chance)) { 
         team1Wins++; 
         System.out.print(1); 
       } 
       else { 
         team2Wins++; 
         System.out.print(2); 
       } 
     } 
     //Print the results  
     System.out.println(); 
     System.out.println("Team 1 playoffs won: " + team1Wins); 
     System.out.println("Team 2 playoffs won: " + team2Wins); 
     if(team1Wins > team2Wins) 
       System.out.println("Team 1 won the championship!"); 
     else 
       System.out.println("Team 2 won the championship!"); 
   } 
    
   //This method prompts the user for an integer value between min and max 
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
 } 
