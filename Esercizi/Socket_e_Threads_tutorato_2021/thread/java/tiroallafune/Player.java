import java.util.Random;

public abstract class Player implements Runnable {
    boolean playing;
    String playerId;
    Scoreboard scoreboard;

    Player opponent;

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

    @Override
    public void run() {
        this.playing = true;

        Random random = new Random();

        while (playing) {
            int rest = random.nextInt(4);
            int strength = random.nextInt(6);

            log(String.format("Resting for %s seconds...", rest));

            try {
                Thread.sleep(rest * 1000);
            } catch (InterruptedException e) {
                log("Can't sleep");
                return;
            }

            if (didOpponentWin()) {
                log("Opponent win");
                markDefeat();
                this.scoreboard.resetPosition();
                notifyOpponent();
            } else {
                log(String.format("Applying %d strength", strength));
                applyStrength(strength);
                if (didWin())
                    waitForOpponentToAcceptDefeat();
            }

        }
    }

    private synchronized void notifyOpponent() {
        notify();
    }

    private void waitForOpponentToAcceptDefeat() {
        log("I won! Waiting for opponent to accept the defeat");
        synchronized (this.opponent) {
            try {
                this.opponent.wait();
            } catch (InterruptedException e) {
                log("Can't wait on opponent");
            }
        }
    }

    public abstract boolean didWin();

    public abstract boolean didOpponentWin();

    public abstract void markDefeat();

    public abstract void applyStrength(int strength);
}
