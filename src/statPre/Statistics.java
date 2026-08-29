package statPre;

public class Statistics {

    public static double calculateOptimalTempProb(int totalGames, int optimalGames) {

        // Cast to double for floating-point division //
        return ((double) optimalGames) / totalGames;

    }

    public static double calculatePredictedAverage(NationalsPlayer np, double probOfOptTemp) {
        
        return ((((double) (np.getPlayer().getLyOptHits() + np.getPlayer().getTyOptHits()) /
               (np.getPlayer().getLyTotalHits() + np.getPlayer().getTyTotalHits())) *
               np.getPlayer().getTyBattingAverage()) / probOfOptTemp);


    }

}
