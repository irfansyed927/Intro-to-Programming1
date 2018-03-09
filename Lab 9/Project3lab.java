import java.awt.*;
import java.util.*;

/* This is a solution to Project 2 of CS 1063, Spring 2016
 * as described at 
 * http://www.cs.utsa.edu/~cs1063/projects/Spring2016/project2/project2.html
 * The solution implements parts 1 through 11.
*/
public class Project3lab {
   
   // General constants
   public static final Random RANDOM = new Random();
   
   public static final int PANEL_WIDTH = 500;
   public static final int PANEL_HEIGHT = 400;
   public static final int SLEEP_TIME = 50;
   public static final Color BACKGROUND_COLOR = Color.WHITE;
   
   // Shooter values
   public static final Color SHOOTER_COLOR = Color.RED;
   public static final int SHOOTER_SIZE = 20;
   public static final int GUN_SIZE = 10;
   public static final int SHOOTER_POSITION_Y = PANEL_HEIGHT - SHOOTER_SIZE;
   public static final int SHOOTER_INITIAL_POSITION_X = PANEL_WIDTH/2;
   public static int shooterPositionX;
   public static double gunAngle;  // in degrees
   
   // Target values
   public static final int TARGET_POSITION_Y = 50;
   public static final Color TARGET_COLOR = Color.BLUE;
   public static final Color TARGET_HIT_COLOR = Color.BLACK;
   public static final int TARGET_SIZE = 20;
   public static final int TARGET_DELTA_X = 1;
   public static int targetPositionX;
   public static int targetDeltaX;
   public static boolean targetMovement = true;
   public static int targetHitTimer; // target hit recently if greater than 0
   
   // Shield values
   public static final Color SHIELD_COLOR = Color.CYAN;
   public static final Color SHIELD_HIT_COLOR = Color.BLACK;
   public static boolean shieldActive = true;
   public static int shieldHitTimer; // shield hit recently if greater than 0
   
   
   // Missile values
   public static final int MISSILE_SIZE = 4;
   public static final Color MISSILE_COLOR = Color.BLACK;
   public static final double MISSILE_SPEED = 1.0;
   public static final int HIT_TIMER_MAX = 50;
   public static boolean missileActive;
   public static double missilePositionX;
   public static double missilePositionY;
   public static double oldMissilePositionX;
   public static double oldMissilePositionY;
   public static double missileDeltaX;
   public static double missileDeltaY;
   
   //The Target Shoots Back 
   public static Color TARGET_MISSILE_COLOR = Color.BLACK ;
public static double TARGET_MISSILE_SPEED = 1.0;
public static double TARGET_SHOOT_PROBABILITY = .02;
public static double targetMissilePositionX;
public static double targetMissilePositionY;
public static double targetMissileDeltaX;
public static double targetMissileDeltaY;
public static boolean targetMissileActive = false;


   // Key values
   public static final int KEY_SPACE = 32;
   public static final int KEY_LEFT_ARROW = 37;
   public static final int KEY_UP_ARROW = 38;
   public static final int KEY_RIGHT_ARROW = 39;
   public static final int KEY_DOWN_ARROW = 40;
   public static final int KEY_HOME = 36;
   public static final int KEY_PAGE_UP = 33;
      
   // scoring values
   public static int hitCount;

   // main method does initialization and calls startGrame
   public static void main(String[] args) {
      DrawingPanel panel = new DrawingPanel(PANEL_WIDTH, PANEL_HEIGHT);
      Graphics g = panel.getGraphics( );
      initialize();
      startGame(panel, g);
   }
   
   // start the main game loop which runs forever
   public static void startGame(DrawingPanel panel, Graphics g) {
      while(true) {
         panel.sleep(SLEEP_TIME);
         handleKeys(panel,g);
         moveTarget(g);
         drawAll(g);
         moveMissile(g);
         shieldHitTimer--;
         targetHitTimer--;
         shootTargetMissile(g); 
         moveTargetMissile(g);
      }
   }
      
   // reset all parameters to start over
   public static void reset(Graphics g) {
      g.setColor(BACKGROUND_COLOR);
      g.fillRect(0,0,PANEL_WIDTH,PANEL_HEIGHT);
      initialize();
   }
   
   // initialize parameters for the start of the program
   public static void initialize() {
      shooterPositionX = SHOOTER_INITIAL_POSITION_X;
      gunAngle = 0;
      targetPositionX = PANEL_WIDTH/2;
      missileActive = false;
      hitCount = 0;
      targetDeltaX = 0;
      targetHitTimer = 0;
      shieldHitTimer = 0;
      targetMissileActive = false;
   }
   
   // draw everything in its current position
   public static void drawAll(Graphics g) {
      g.setColor(Color.BLACK);
      g.drawString("Project 3 by Irfan Syed",10,15);
      String hitString = "Hits: ";
      for (int i=0;i<hitCount;i++)
         hitString = hitString + "*";
      g.drawString(hitString,10,30);
      drawShooter(g,SHOOTER_COLOR);
      if (targetHitTimer > 0)
         drawTarget(g,TARGET_HIT_COLOR);
      else
         drawTarget(g,TARGET_COLOR);
      Color shieldColor = BACKGROUND_COLOR; // default: do not draw
      if (shieldActive) {
         if (shieldHitTimer > 0)
            shieldColor  = SHIELD_HIT_COLOR;
        
         else 
            shieldColor = SHIELD_COLOR;
      }
      drawShield(g, shieldColor);
   }
   
   // draw the shooter (and gun) in a given color
   public static void drawShooter(Graphics g, Color c) {
      g.setColor(c);
      g.fillOval(shooterPositionX - SHOOTER_SIZE/2,
                 SHOOTER_POSITION_Y - SHOOTER_SIZE/2,
                 SHOOTER_SIZE,SHOOTER_SIZE);
      // draw gun
      int gunx1 = shooterPositionX + (int)Math.round(triangleX(gunAngle,SHOOTER_SIZE/2));
      int guny1 = SHOOTER_POSITION_Y - (int)Math.round(triangleY(gunAngle,SHOOTER_SIZE/2));
      int gunx2 = shooterPositionX + (int)Math.round(triangleX(gunAngle,SHOOTER_SIZE/2 + GUN_SIZE));
      int guny2 = SHOOTER_POSITION_Y - (int)Math.round(triangleY(gunAngle,SHOOTER_SIZE/2 + GUN_SIZE));                            
      g.drawLine(gunx1,guny1,gunx2,guny2);
   }
   
   // draw the target in a given color
   public static void drawTarget(Graphics g, Color c) {
      g.setColor(c);
      g.fillOval(targetPositionX - TARGET_SIZE/2,
                 TARGET_POSITION_Y - TARGET_SIZE/2,
                 TARGET_SIZE, TARGET_SIZE);
   }
   
   // draw the shield in a given color
   public static void drawShield(Graphics g, Color c) {
      g.setColor(c);
      g.drawLine(targetPositionX - TARGET_SIZE,
                 TARGET_POSITION_Y + TARGET_SIZE,
                 targetPositionX + TARGET_SIZE,
                 TARGET_POSITION_Y + TARGET_SIZE);
   }
   
   // move the shooter by a given amount
   public static void moveShooter(Graphics g, int deltaX) {
      drawShooter(g,BACKGROUND_COLOR);
      shooterPositionX = shooterPositionX + deltaX;
      if (shooterPositionX + SHOOTER_SIZE/2 > PANEL_WIDTH)
         shooterPositionX = PANEL_WIDTH - SHOOTER_SIZE/2;
      if (shooterPositionX - SHOOTER_SIZE/2 < 0)
         shooterPositionX = SHOOTER_SIZE/2;
      drawShooter(g,SHOOTER_COLOR);
   }
   
   // change the gun angle by the given amount (in degrees)
   // make sure gun points at least a little bit up
   public static void moveGun(Graphics g, double gunDelta) {
      drawShooter(g,BACKGROUND_COLOR);
      gunAngle += gunDelta;
      if (gunAngle > 85)
         gunAngle = 85;
      if (gunAngle < -85)
         gunAngle = -85;
      drawShooter(g,SHOOTER_COLOR);
   }
   
   // move the target horizontally by targetDeltaX
   // reset targetDeltaX with probabilty .06.
   // If changed, it is changed to 0, TARGET_DELTA_X, or -TARGET_DELTA_X with equal probability
   public static void moveTarget(Graphics g) {
      if (!targetMovement)
         return;
      drawTarget(g, BACKGROUND_COLOR);
      drawShield(g, BACKGROUND_COLOR);
      targetPositionX += targetDeltaX;
      if (targetPositionX + TARGET_SIZE/2 > PANEL_WIDTH)
         targetDeltaX = -TARGET_DELTA_X;
      if (targetPositionX - TARGET_SIZE/2 < 0)
         targetDeltaX = TARGET_DELTA_X;
      double randomMove = RANDOM.nextDouble();
      if (randomMove > .98)
         targetDeltaX = TARGET_DELTA_X;
      else if (randomMove > .96)
         targetDeltaX = -TARGET_DELTA_X;
      else if (randomMove > .94)
         targetDeltaX = 0;
   }

   // shoot a missile unless one is already active
   public static void shootMissile(Graphics g) {
      if (missileActive)
         return;
      missileActive = true;
      double gunx1 = shooterPositionX + triangleX(gunAngle,SHOOTER_SIZE/2);
      double guny1 = SHOOTER_POSITION_Y - triangleY(gunAngle,SHOOTER_SIZE/2);
      double gunx2 = shooterPositionX + triangleX(gunAngle,SHOOTER_SIZE/2 + GUN_SIZE);
      double guny2 = SHOOTER_POSITION_Y - triangleY(gunAngle,SHOOTER_SIZE/2 + GUN_SIZE);                            
      missilePositionX = gunx1;
      missilePositionY = guny1;
      missileDeltaX = (gunx2-gunx1) * MISSILE_SPEED;
      missileDeltaY = (guny2-guny1) * MISSILE_SPEED;
      oldMissilePositionX = missilePositionX;
      oldMissilePositionY = missilePositionY;
   }
   
   // Method for shooting Target Missile
   public static void shootTargetMissile(Graphics g) {
     if (targetMissileActive == true) return;
     
     double shoot = RANDOM.nextDouble();
     if (shoot <= TARGET_SHOOT_PROBABILITY ) {
       targetMissileActive = true;
  
targetMissilePositionX = targetPositionX; 
targetMissilePositionY = TARGET_POSITION_Y;
 targetMissileDeltaX =0;
targetMissileDeltaY = TARGET_SIZE/2;
     }
   }
   

   // draw the missile in the given color
   public static void drawMissile(Graphics g, Color c) {
      int x = (int)(missilePositionX + .5) - MISSILE_SIZE/2;
      int y = (int)(missilePositionY + .5) - MISSILE_SIZE/2;
      g.setColor(c);
      g.fillOval(x,y,MISSILE_SIZE,MISSILE_SIZE);     
   }
   
   // Draw the target Missle in the given color
    public static void drawTargetMissile(Graphics g, Color c) {
      int x = (int)(targetMissilePositionX + .5) - MISSILE_SIZE/2;
      int y = (int)(targetMissilePositionY + .5) - MISSILE_SIZE/2;
      g.setColor(c);
      g.fillOval(x,y,MISSILE_SIZE,MISSILE_SIZE);
    }
    
    
   // move the missile if it is active
   // check to see if the missile: 
   //   hits the shield
   //   bounces off the top or sides
   //   disappears off the bottom of the screen
   public static void moveMissile(Graphics g) {
      if (!missileActive)
         return;
      drawMissile(g,BACKGROUND_COLOR);
      oldMissilePositionX = missilePositionX;
      oldMissilePositionY = missilePositionY;
      missilePositionX += missileDeltaX;
      missilePositionY += missileDeltaY;
      drawMissile(g,MISSILE_COLOR);
      if (missilePositionY > PANEL_HEIGHT) {
         drawMissile(g,BACKGROUND_COLOR);
         missileActive = false;
      }
      if (missilePositionY - MISSILE_SIZE/2 < 0)
         missileDeltaY = Math.abs(missileDeltaY);
      if (missilePositionX - MISSILE_SIZE/2 < 0)
         missileDeltaX = Math.abs(missileDeltaX);
      if (missilePositionX + MISSILE_SIZE/2 > PANEL_WIDTH)
         missileDeltaX = -Math.abs(missileDeltaX);
      if (missileActive && shieldActive && (shieldHitTimer <= 0) && detectShieldHit()) {
         drawMissile(g,BACKGROUND_COLOR);
         missileActive = false;
         shieldHitTimer = HIT_TIMER_MAX;
      }
      if (missileActive && detectHitTarget()) {
         drawMissile(g,BACKGROUND_COLOR);
         missileActive = false;
         targetHitTimer = HIT_TIMER_MAX;
         hitCount++;
      }
   }
   public static void moveTargetMissile(Graphics g){
    if (!targetMissileActive)
         return;
      drawTargetMissile(g,BACKGROUND_COLOR);
     
     
      targetMissilePositionX += targetMissileDeltaX;
      targetMissilePositionY += targetMissileDeltaY;
      drawTargetMissile(g,MISSILE_COLOR);
      if (targetMissilePositionY > PANEL_HEIGHT) {
         drawTargetMissile(g,BACKGROUND_COLOR);
         targetMissileActive = false;
      }

      if (targetMissilePositionX - MISSILE_SIZE/2 < 0)
         targetMissileDeltaX = Math.abs(targetMissileDeltaX);
      if (targetMissilePositionX + MISSILE_SIZE/2 > PANEL_WIDTH)
         targetMissileDeltaX = -Math.abs(targetMissileDeltaX);
   
//      
//      if (missileActive && detectHitTarget()) {
//         drawMissile(g,BACKGROUND_COLOR);
//         missileActive = false;
//        
//         hitCount++;
//      }
   }
   
   
   // returns tru of the circle at (x1,y1) with radius r1 intersects the circle at (x2,y2) with radius r2
   public static boolean detectIntersection(int x1, int y1, int r1, int x2, int y2, int r2) {
      return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) < (r1+r2)*(r1+r2);
   }
   
   // retuyrns true of the missile has hit the target
   public static boolean detectHitTarget() {
      return detectIntersection(targetPositionX, TARGET_POSITION_Y, TARGET_SIZE/2,
                                (int)(missilePositionX + .5),(int)(missilePositionY + .5),
                                MISSILE_SIZE/2);
   }  
   
   // return true if the missile has hit the shield
   // Use the old missile position and the new missile position to see
   // if the line segment between these two intersects the shield
   // Only check if the missile is moving up
   public static boolean detectShieldHit() {
      double intersectX;
      int shieldPositionY = TARGET_POSITION_Y + TARGET_SIZE;
      if ((oldMissilePositionY < shieldPositionY) ||
          (missilePositionY > shieldPositionY))
         return false;
      if (oldMissilePositionX == missilePositionX)
         intersectX = missilePositionX;
      else {
         double slope = (oldMissilePositionY-missilePositionY)/(oldMissilePositionX-missilePositionX);
         intersectX = (shieldPositionY-missilePositionY)/slope + missilePositionX;
      }
      return (intersectX >= targetPositionX - TARGET_SIZE) &&
         (intersectX <= targetPositionX + TARGET_SIZE);
   }
   
   // return the side opposite the given angle of a right triangle with given hypotenuse
   public static double triangleX(double angleDegrees, double hypotenuse) {
      double angleRadians = angleDegrees * Math.PI/180;
      return hypotenuse * Math.sin(angleRadians);
   }
   
   // return the side adjacent to the given angle of a right triangle with the given hypotenuse
   public static double triangleY(double angleDegrees, double hypotenuse) {
      double angleRadians = angleDegrees * Math.PI/180;
      return hypotenuse * Math.cos(angleRadians);
   }
   
   // take action based on which key is pressed
   public static void handleKeys(DrawingPanel panel, Graphics g) {
      int keyCode = panel.getKeyCode();
      if (keyCode == KEY_SPACE)
         reset(g);
      else if (keyCode == KEY_RIGHT_ARROW)
         moveShooter(g,1);
      else if (keyCode == KEY_LEFT_ARROW)
         moveShooter(g,-1);
      else if (keyCode == KEY_HOME)
         moveGun(g,-1);
      else if (keyCode == KEY_PAGE_UP)
         moveGun(g,1);
      else if (keyCode == KEY_UP_ARROW)
         shootMissile(g);
      else if (keyCode == 'S')
         shieldActive = !shieldActive;
      else if (keyCode == 'M')
         targetMovement = !targetMovement;
   }   
   
}