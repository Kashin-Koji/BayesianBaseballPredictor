// **********************************************************
// Title: Input Validation Methods Library
// File: JOptionVCheck
// Author: Frank Seidel
// Course: CMIS106-HYB6 (Seidel) 
// Description: 
// **********************************************************
package statPre;
import javax.swing.JOptionPane;

public class JOptionVCheck {

   // Modified validate text input with Regex function matches //
   public static String getTextDialog(String message, String title, String err) {
      String text="";
      boolean flag=true;
      do {
         text=JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
         if (text==null || text.trim().length()==0 || !text.matches("[a-zA-Z'-]+")) {
            JOptionPane.showMessageDialog(null, "ERROR: Invalid Text Entered!\n\n"+err, "ERROR", 
                                          JOptionPane.ERROR_MESSAGE);
            flag=true;
         }
         else
            flag=false;      
      } while(flag);
      return text;
   }

   public static int getIntDialog(String message, String title, String err) {
      int result=0;
      boolean flag=true;
      do {
         try {
            result=Integer.parseInt(JOptionPane.showInputDialog(null, message, title, 
                   JOptionPane.QUESTION_MESSAGE));
            flag=false;
         }
         catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR: An Integer was not entered!\n"+e.getMessage()+"\n"+err,
                "ERROR", JOptionPane.ERROR_MESSAGE);
            flag=true;
         }
      } while(flag);
      return result;
   }
   
}
