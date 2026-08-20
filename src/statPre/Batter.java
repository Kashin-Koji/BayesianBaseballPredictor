// **************************************************
// Title: Batter Class
// File: Batter.java
// Authors: Usoff Samantar/Honors Mentor/Professor Frank Seidel
// Description: In this code I have 
// created basic characteristics of a 
// batter to use in my main file.
// **************************************************


package statPre;
import javax.swing.ImageIcon;

public class Batter {
   // Attributes:
   private int jerseyNumber;
   private String lastName;
   private String firstName;
   private String batDominance;
   //improvement 
   private ImageIcon image=null;
   
   // Constructors:
   public Batter() {
      this.jerseyNumber=0;
      this.lastName="";
      this.firstName="";
      this.batDominance="";
   }
   public Batter(int jersey, String last, String first, String bat) {
      this.jerseyNumber=jersey;
      this.lastName=last;
      this.firstName=first;
      this.batDominance=bat;
   }
   // Mutators:
   public void setJerseyNumber(int jerseyNumber) {
      this.jerseyNumber=jerseyNumber;
   }
   public void setLastName(String lastName) {
      this.lastName=lastName;
   }
   public void setFirstName(String firstName) {
      this.firstName=firstName;
   }
   public void setBatDominance(String batDominance) {
      this.batDominance=batDominance;
   }
   public void resetImage() {
      this.image=new ImageIcon("statPre/Player_Photos/"+getFirstName()+getLastName()+".jpeg");
   }
   // Accessors:
   public int getJerseyNumber() {
      return this.jerseyNumber;
   } 
   public String getLastName() {
      return this.lastName;
   } 
   public String getFirstName() {
      return this.firstName;
   }
   public String getBatDominance() {
      return this.batDominance;
   }
   public ImageIcon getImage() {
      if (this.image==null)
         this.image=new ImageIcon("statPre/Player_Photos/"+getFirstName()+getLastName()+".jpeg");
      return this.image;
   }
   public String toString() {
      String result="Jersey Number: "+getJerseyNumber()+"\n";
      result+="Last Name: "+getLastName()+"\n";
      result+="First Name: "+getFirstName()+"\n";
      result+="Batting Dominance: "+getBatDominance()+"\n";
      return result;
   }
 }