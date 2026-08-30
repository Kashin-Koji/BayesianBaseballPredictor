// **************************************************
// Title: BatterwStats Class
// File: BatterwStats.java
// Authors: Usoff Samantar/Honors Mentor/Professor Frank Seidel
// Description: In this code I have 
// created a class called BatterwStats which
// brings in attributes from the batter 
// class as well as added the basis statistics
// to use for an ArrayList in the main file.
// **************************************************

package statPre;

public class BatterwStats extends Batter {

   // Attributes
   private static int numberOfPlayers = 0;
   private int lyTotalHits;
   private int lyTotalOptTempHits;
   private int lyAtBats;
   private double lyBattingAverage;
   private int tyTotalHits;
   private int tyTotalOptTempHits;
   private int tyAtBats;
   private double tyBattingAverage;

   public BatterwStats() {
      super(); // Call Batter() constructor.
      this.lyTotalHits = 0;
      this.lyTotalOptTempHits = 0;
      this.lyAtBats = 0;
      this.lyBattingAverage = 0.0;
      this.tyTotalHits = 0;
      this.tyTotalOptTempHits = 0;
      this.tyAtBats = 0;
      this.tyBattingAverage = 0.0;
      numberOfPlayers++;
   }

   public BatterwStats(int jersey, String last, String first, String hand, int lyHits, int lyOpt, int lyBats,
         int tyHits,
         int tyOpt, int tyBats) {
      super(jersey, last, first, hand);
      this.lyTotalHits = lyHits;
      this.lyTotalOptTempHits = lyOpt;
      this.lyAtBats = lyBats;
      setLyBattingAverage();
      this.tyTotalHits = tyHits;
      this.tyAtBats = tyBats;
      this.tyTotalOptTempHits = tyOpt;
      setTyBattingAverage();
      ;
   }

   public BatterwStats(int jersey, String last, String first, String hand, int lyHits, int lyBats, int tyHits,
         int tyBats) {
      super(jersey, last, first, hand);
      this.lyTotalHits = lyHits;
      this.lyTotalOptTempHits = 0;
      this.lyAtBats = lyBats;
      setLyBattingAverage();
      this.tyTotalHits = tyHits;
      this.tyAtBats = tyBats;
      this.tyTotalOptTempHits = 0;
      setTyBattingAverage();
      ;
   }

   // Mutators:
   public void setLyTotalHits(int lyTotalHits) {
      this.lyTotalHits = lyTotalHits;
      setLyBattingAverage();
   }

   public void setLyOptHits(int hits) {
      this.lyTotalOptTempHits = hits;
   }

   public void setLyAtBats(int lyAtBats) {
      this.lyAtBats = lyAtBats;
      setLyBattingAverage();
   }

   public void setLyBattingAverage() {
      this.lyBattingAverage = (((double) getLyTotalHits()) / ((double) getLyAtBats()));
   }

   public void setLyBattingAverage(double lyBattingAverage) {
      this.lyBattingAverage = lyBattingAverage;
   }

   public void setTyTotalHits(int tyTotalHits) {
      this.tyTotalHits = tyTotalHits;
      setTyBattingAverage();
   }

   public void setTyOptHits(int hits) {
      this.tyTotalOptTempHits = hits;
   }

   public void setTyAtBats(int tyAtBats) {
      this.tyAtBats = tyAtBats;
      setTyBattingAverage();
   }

   public void setTyBattingAverage() {
      this.tyBattingAverage = (((double) getTyTotalHits()) / ((double) getTyAtBats()));
   }

   public void setTyBattingAverage(double tyBattingAverage) {
      this.tyBattingAverage = tyBattingAverage;
   }

   // Accessors:
   public int getLyTotalHits() {
      return this.lyTotalHits;
   }

   public int getLyOptHits() {
      return this.lyTotalOptTempHits;
   }

   public int getLyAtBats() {
      return this.lyAtBats;
   }

   public double getLyBattingAverage() {
      return this.lyBattingAverage;
   }

   public int getTyTotalHits() {
      return this.tyTotalHits;
   }

   public int getTyOptHits() {
      return this.tyTotalOptTempHits;
   }

   public int getTyAtBats() {
      return this.tyAtBats;
   }

   public double getTyBattingAverage() {
      return this.tyBattingAverage;
   }

   public String toString() {
      String result = super.toString();
      //// private int jerseyNumber;
      // private String lastName;
      // private String firstName;
      // private String batDominance;
      result += "Last Years Total Hits: " + getLyTotalHits() + "\n";
      result += "Last Years Optimal Temp Hits: " + getLyOptHits() + "\n";
      result += "Last Years Total At Bats: " + getLyAtBats() + "\n";
      result += "Last Years BA(Batting Average): " + String.format("%3.3f", getLyBattingAverage()) + "\n";
      result += "This Years Total Hits: " + getTyTotalHits() + "\n";
      result += "This Years Optimal Temp Hits: " + getTyOptHits() + "\n";
      result += "This Years At Bats: " + getTyAtBats() + "\n";
      result += "This Years Current BA(Batting Average): " + String.format("%3.3f", getTyBattingAverage()) + "\n";
      return result;
   }
}
