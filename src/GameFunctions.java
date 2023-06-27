public class GameFunctions {
    int number;
    int attempts = 5;
    int score = 0;

    public void generateNewAnswer() {
        number = 1 + (int) (30 * Math.random());
        System.out.println(number + " Is Answer");
    }

    public int getAnswer() {
        return number;
    }

    public String getAttempts() {
        return attempts + "";
    }

    public void decreaseAttempts() {
        if (attempts > 1) {
            attempts--;
            System.out.println(attempts + " attempts left");

        } else {
            System.out.println("Attempts exhausted.");
        }

    }

    public void resetAttempts() {
        attempts = 5;
    }

    public String getScore() {
        return score + "";
    }

    public void resetScore() {
        score = 0;
    }

    public void increaseScore() {
        score = score + 5;
        System.out.println("New score is " + score);
    }
}
