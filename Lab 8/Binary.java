/* This program first prompts the user to enter a decimal number then converts that to binary. It then asks the user 
  * to enter a binary number and converts that to decimal. It then asks the user if they wish to continue, and does 
  * so if they say yes, and exits if they say no. 
  */ 
 import java.util.Scanner; 
 import java.util.Arrays; 
 
 
 public class Binary { 
   
   public static void main(String args[]) { 
     System.out.println("Lab 8 written by Irfan Syed"); 
     boolean contProgram = true; 
      
     //The master loop 
     while(contProgram) { 
       Scanner input = new Scanner(System.in); 
       boolean cont = true; 
        
       //Convert dec to bin 
       int dec = 0; 
       while(cont) { 
         System.out.print("Please enter a decimal number: "); 
         try { 
           dec = input.nextInt(); 
           if(dec < 0) 
             throw new Exception(); 
           cont = false; 
         } catch(Exception e) { 
           System.err.println("ERROR: Invalid input."); 
           input = new Scanner(System.in); 
         } 
       } 
        
       //Calculate and output the results 
       int[] bin = convertIntToBitArray(dec); 
       String binStr = bitArrayToBitString(bin); 
       System.out.println(dec + " in binary is " + binStr + "."); 
        
       //Convert bin to dec 
       cont = true; 
       binStr = ""; 
       while(cont) { 
         System.out.print("Please enter a binary number: "); 
         binStr = input.nextLine(); 
         if(binStr.length() == 0) 
           binStr = input.nextLine(); 
         if(!isBinaryString(binStr)) 
           System.err.println("ERROR: Invalid input."); 
         else 
           cont = false; 
       } 
        
       //Calculate and output the results 
       bin = bitStringToBitArray(binStr); 
       dec = convertBitArrayToInt(bin); 
       System.out.println(binStr + " in decimal is " + dec + "."); 
        
       //Ask the user if they want to continue 
       cont = true; 
       while(cont) { 
         System.out.print("Would you like to continue? (yes or no): "); 
         String contStr = input.nextLine(); 
         if("no".startsWith(contStr)) { 
          cont = false; 
           contProgram = false; 
         } 
         else if ("yes".startsWith(contStr)) 
           cont = false; 
         else 
           System.err.println("ERROR: Invalid input."); 
       } 
     } 
     System.out.println("Thank You! Have a nice day!"); 
   } 
    
   //Converts an int to a binary array 
   private static int[] convertIntToBitArray(int val) { 
     int[] bin = new int[31]; 
     int index = 0; 
     while(val != 0) { 
       if(val % 2 != 0) 
         bin[index] = 1; 
       else 
         bin[index] = 0; 
       val/=2; 
       index++; 
     } 
    return bin; 
   } 
    
   //Converts a binary array to an int 
   private static int convertBitArrayToInt(int[] bin) { 
     int increment = 1; 
     int val = 0; 
     for(int i = 0; i < bin.length; i++) { 
       if(bin[i] == 1) 
         val+=increment 
         ; 
       increment*=2; 
     } 
     return val; 
   } 
    
   //converts a bit String into an int array 
   private static int[] bitStringToBitArray(String bitString) { 
     int bLength = bitString.length(); 
     int[] bitArray = new int[bLength]; 
     int bIndex = bLength - 1; 
     for (int i = 0; i < bLength; i++) { 
       char c = bitString.charAt(bIndex); 
       if (c == '0') { 
         bitArray[i] = 0; 
       } else if (c == '1') { 
         bitArray[i] = 1; 
       } else { 
         throw new IllegalArgumentException 
           (bitString + " should only contain 0s and 1s"); 
       } 
       bIndex--; 
     } 
     return bitArray; 
   } 
    
   //converts an int array into a bit String 
   private static String bitArrayToBitString(int[] bitArray) { 
     int bLength = bitArray.length; 
     String bitString = ""; 
     for (int i = 0; i < bLength; i++) { 
       if (bitArray[i] == 0 || bitArray[i] == 1) { 
         bitString = bitArray[i] + bitString; 
       } else { 
         throw new IllegalArgumentException 
           (Arrays.toString(bitArray) + " should only contain 0s and 1s"); 
       } 
     } 
     //eliminate leading 0s 
     int firstOne = bitString.indexOf('1'); 
     if (firstOne == -1) { // true if bitString is all 0s 
       bitString = "0"; 
     } else { 
       bitString = bitString.substring(firstOne, bLength); 
     } 
     return bitString; 
   } 
    
   //Determines if a string consists of only 1s and 0s 
   private static boolean isBinaryString(String testStr) { 
     for(int i = 0; i < testStr.length(); i++) { 
       if(testStr.charAt(i) != '1' && testStr.charAt(i) != '0') 
         return false; 
     } 
     return true; 
   } 
 } 
