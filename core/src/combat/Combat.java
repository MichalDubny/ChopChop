package combat;


public class Combat<T,P> {
    private CombatEntity attacker;
    private CombatEntity defender;

    public<T extends CombatEntity,P extends CombatEntity>  Combat(T attacker, P defender) {
        this.attacker = attacker;
        this.defender = defender;
        setResult();
    }

    private void setResult() {
        int AD = attacker.getAttackDamage();
        int HP = defender.getHealPoints();
        System.out.println( HP + " - " +AD + " = " + (HP-AD) );
        defender.setHealthPoints(defender.getHealPoints() - attacker.getAttackDamage());
        if(defender.getHealPoints() <= 0 ){
            defender.setDead(true);
        }


    }


}
