public class Poems {
  public static void main(String[] args) {
    System.out.println("Lab 1 written by Irfan Syed");
    
    // print title
    System.out.println("Mary had a Little Lamb");
    System.out.println();
    
    // print first verse with method calls
    firstVerse();
    
    /* Print title */
    
    System.out.println("This Old Man");
    
      // This Old Man first verse with method call
    firstVerseofOld();
    
    // Second verse with method call
    secondVerseofOld();
    
    // Third verse with method call
    thirdVerseofOld();
  }
  
  // This method prints the Mary had a little lamb line 
  public static void MaryHadaLittleLine() {
  System.out.println("Mary had a little lamb,");
}
  
  // This method prints little lamb
  public static void LittleLambLine() {
    System.out.println(" little lamb,");
  }
  
    // This method prints the first verse of Mary had a little lamb
  public static void firstVerse() {
    MaryHadaLittleLine();
    System.out.print("Little lamb,");
    LittleLambLine();
    MaryHadaLittleLine();
    System.out.println("Its fleece was white as snow");
    System.out.println();
  }
    
    
  
  // this method prints refrain 
  public static void refrain() {
    System.out.println("With a knick-knack patty-whack, give a dog a bone");
    System.out.println("This old man came rolling home");
  }
  
  // method for old man 
  public static void Thisoldman() {
    System.out.print("This old man");
  }
 
  // Method for he played knick knack
  public static void Knickknack() {
    System.out.print("He played knick-knack");
  }
  
  // This method prints the first verse of This Old Man
  public static void firstVerseofOld() {
  Thisoldman();
  System.out.println(", he played one");
  Knickknack();
  System.out.println(" on my thumb");
  refrain();
  System.out.println();
  }
  
  // This method prints the Second verse of this Old Man
  public static void secondVerseofOld() {
    Thisoldman();
    System.out.println(", he played two");
    Knickknack();
    System.out.println(" on my shoe");
    refrain();
    System.out.println();
  }
  
  // This method prints the Third verse of This Old Man
  public static void thirdVerseofOld() {
    Thisoldman();
    System.out.println(", he played three");
    Knickknack();
    System.out.println(" on my knee");
    refrain();
  }
    
    
}



