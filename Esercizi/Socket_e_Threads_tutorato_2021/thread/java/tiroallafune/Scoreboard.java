public class Scoreboard {
    int[] wins;
    int position;

    public Scoreboard() {
        this.wins = new int[2];
        this.position = 0;
    }

    public synchronized void markVictory(int player) {
        this.wins[player] += 1;
        log(String.format("T0 Wins: %d, T1 wins: %d", this.wins[0], this.wins[1]));
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

    public synchronized int getPosition() {
        return position;
    }

    public void log(String message) {
        System.out.println(String.format("[Scoreboard] %s", message));
    }
    
}
