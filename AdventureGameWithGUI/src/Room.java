/*****************************************************************************\
|*                                                                           *|
\*****************************************************************************/

public class Room
{
   /**************************************************************************\
   |*                                                                        *|
   \**************************************************************************/

   public String description ;

   public boolean isLit ;
   public boolean hasKey ;
   public boolean hasLamp ;
   public boolean hasChest ;

   public Room north ;
   public Room south ;
   public Room east ;
   public Room west ;

   /**************************************************************************\
   |*                                                                        *|
   \**************************************************************************/

   public Room()
   {
      description = null ;

      isLit = false ;
      hasKey = false ;
      hasLamp = false ;
      hasChest = false ;

      north = null ;
      south = null ;
      east = null ;
      west = null ;
   }

   /**************************************************************************\
   |*                                                                        *|
   \**************************************************************************/

   public String toString()
   {
      String s = "" ;

      if(isLit)
      {
         s += description + "\n" ;
         s += "This room is lit.\n" ;

         if(hasKey)
         {
            s += "This room has a key.\n" ;
         }

         if(hasLamp)
         {
            s += "This room has a lamp.\n" ;
         }

         if(hasChest)
         {
            s += "This room has a chest.\n" ;
         }
      }
      else
      {
         s += "This room is pitch black.\n" ;
      }

      return s ;
   }
}
