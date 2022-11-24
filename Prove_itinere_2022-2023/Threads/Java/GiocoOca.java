/**
 * 
 * Soluzione proposta -> Andrew R. Darnall
 * 
 */

import java.util.Random;

class Scoreboard {

    int[] wins;
    int position;

    public Scoreboard() {
        this.wins = new int[2];
        this.position = 0;
    }

    public synchronized void markVictory(int player) {
        this.wins[player] += 1;
        log(String.format("T0 Wins: %d, T1 Wins: %d", this.wins[0], this.wins[1]));
    }

    public synchronized void movePosition(int offset) {
        position += offset;
        log("New position value: " + position);
    }

    public synchronized void resetPosition() {
        log("Position reset");
        position = 0;
    }

    public synchronized int getWins(int player) {
        return wins[player];
    }

    public void log(String message) {
        System.out.println(String.format("[Scoreboard] %s", message));
    }

}

public abstract class GiocoOca implements Runnable {

    private boolean playing;
    private String playerId;
    private Scoreboard scoreboard;

    private Player opponent;

    public Player(String playerId, Scoreboard scoreboard) {
        this.playerId = playerId;
        this.scoreboard = scoreboard;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void log(String message) {
        System.out.println(String.format("[Thread %s] %s", this.playerId, message));
    }

    private synchronized void notifyOpponent() {
        notify();
    }

    private void waitForOpponentToAcceptDefeat() {

        log("I won ! Witing for the opponent to accept defeat!");
        

    }

    @Override
    public void run() {

        this.playing = true;

        Random rand = new Random();

        while(playing) {
            int rest = rand.nextInt(4);
            int strength = rand.nextInt(10);

            log(String.format("Resting for %s seconds...", rest));

            try {
                Thread.sleep(rest * 1000);
            } catch(InterruptedException e) {
                log("Can't sleep");
                return;
            }

            if(didOpponentWin()) {
                log("Opponent won!");
                markDefeat();
                this.scoreboard.resetPosition();
                notifyOpponent();
            } else {
                log(String.format("Applying %d strength", strength));
                applyStrength(strength);
                if(didWin()) waitForOpponentToAcceptDefeat();
            }

        }

   

    }

}