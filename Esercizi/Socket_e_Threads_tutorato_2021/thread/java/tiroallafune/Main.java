public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scoreboard scoreboard = new Scoreboard();

        Player player0 = new Player("T0", scoreboard) {
            @Override
            public boolean didWin() {
                return scoreboard.getPosition() <= -10;
            }

            @Override
            public boolean didOpponentWin() {
                return scoreboard.getPosition() >= 10;
            }

            @Override
            public void markDefeat() {
                scoreboard.markVictory(1);
            }

            @Override
            public void applyStrength(int strength) {
                scoreboard.movePosition(-strength);
            }
        };

        Player player1 = new Player("T1", scoreboard) {
            @Override
            public boolean didWin() {
                return scoreboard.getPosition() >= 10;
            }

            @Override
            public boolean didOpponentWin() {
                return scoreboard.getPosition() <= -10;
            }

            @Override
            public void markDefeat() {
                scoreboard.markVictory(0);
            }

            @Override
            public void applyStrength(int strength) {
                scoreboard.movePosition(strength);
            }
        };

        player0.setOpponent(player1);
        player1.setOpponent(player0);

        Thread t0 = new Thread(player0);
        Thread t1 = new Thread(player1);

        t0.start();
        t1.start();

        t0.join();
        t1.join();

        System.out.println("I'm the main thread, the game is finished");
    }
}
