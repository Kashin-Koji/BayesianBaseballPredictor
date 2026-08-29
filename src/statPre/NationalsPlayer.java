// **************************************************
// Title: NationalsPlayer Class
// File: NationalsPlayer.java
// Authors: Usoff Samantar/Honors Mentor/Professor Frank Seidel
// Description: In this code I have 
// created a NationalsPlayer class to store nats player game data
// **************************************************


package statPre;


public class NationalsPlayer {
   // Attributes:
   private double gameTemp;
   private int newGame;
   private BatterwStats player;
   private double predictedPlayerAvgTy;
   private String teamAgainst;
   
   // Constructors:
   public NationalsPlayer() {
      this.gameTemp=0.0;
      this.newGame=0;
      this.predictedPlayerAvgTy=0.0;
      this.player=new BatterwStats();
      this.teamAgainst="";
      
   }
   
   public NationalsPlayer(double temp, int game, double preAvg, String team, BatterwStats player) {
      this.gameTemp=temp;
      this.newGame=game;
      this.player=player;
      this.predictedPlayerAvgTy=preAvg;
      this.teamAgainst=team;

   }
   // Mutators:
    public void setGameTemp(double gameTemp) {
      this.gameTemp=gameTemp;
   }
    public void setNewGame(int newGame) {
      this.newGame=newGame;
   }
    public void setPredictedPlayerAvgTy(double predictedPlayerAvgTy) {
      this.predictedPlayerAvgTy=predictedPlayerAvgTy;
   }
    public void setTeamAgainst(String teamAgainst) {
      this.teamAgainst=teamAgainst;
   }
   public void setPlayer(BatterwStats player) {
      this.player=player;
   }
   // Accessors:
    public double getGameTemp() {
      return this.gameTemp;
   }
    public int getNewGame() {
      return this.newGame;
   }
    public double getPredictedPlayerAvgTy() {
      return this.predictedPlayerAvgTy;
   } 
    public String getTeamAgainst() {
      return this.teamAgainst;
   } 
   public BatterwStats getPlayer() {
      return this.player;
   }
   
   public String toString() {
      String info ="Game Number: "+getNewGame()+"\n";
      info+="Nationals vs: "+getTeamAgainst()+"\n";
      info+="Current Temperature: "+getGameTemp()+"\n";
      info+="Batting AVG From Last Year: "+String.format("%3.3f",this.player.getLyBattingAverage())+"\n";
      info+="Batting Avg Of Current: "+String.format("%3.3f",this.player.getTyBattingAverage())+"\n";
      info+="Predicted Batting Avg Per/Game Of Current: "+String.format("%3.3f",getPredictedPlayerAvgTy())+"\n";
      return info;
   }
}
                 

