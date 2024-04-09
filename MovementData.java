  
/**
 * Project Sea Ludo
 * Course: ISTE-121-800
 * Team Name: Ihor Babenko & Varenichki Inc.
 * @author Evgeniya Samsonova
 * @author Luka Crnogorac
 * @author Ihor Berehovyi
 */

import java.awt.*;
import java.io.Serializable;

/**
 * class that takes information needed for updating token's position and puts it into a package
 */
public class MovementData implements Serializable {

   private Player player;
   private int index;
   private int color;
   private Point point;
   static final long serialVersionUID = -5109010579654190627L;

   /**
    * constructor 1
    * @param index index of the token that needs to be moved
    * @param color color of the token (as a number)
    * @param player player this token belongs to
    * @param point location where this token needs to be placed on
    */
   public MovementData (int index, int color, Player player, Point point) {
      this.index = index;
      this.color = color;
      this.player = player;
      this.point = point;
   }

   /**
    * constructor 2
    * @param index index of the token which location needs to be reset
    * @param color color of the token which location needs to be reset
    */
   public MovementData (int index, int color) {
      this.index = index;
      this.color = color;
   }

   /**
    *
    * @return index of the token
    */
   public int getIndex() {
      return index;
   }

   /**
    *
    * @return color of the token as a number
    */
   public int getColor() {
      return color;
   }

   /**
    *
    * @return Point object
    */
   public Point getPoint() {
      return point;
   }

}
