package combat;


import creatures.Creature;
import javafx.print.PageLayout;
import player.Player;

public class Combat<T,P> {
    private T attacker;
    private P defender;
    private Player player;
    private Creature creature;

    public Combat(T attacker, P defender) {
        this.attacker = attacker;
        this.defender = defender;
        setResult();
    }

    private void setResult() {
        String sss = attacker.getClass().getName();
        if(attacker.getClass().getName() == "Player"){
            System.out.println("Player");
        }else {
            System.out.println("Creature");
        }
    }
}
