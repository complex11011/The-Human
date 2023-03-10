package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class SingleCardCostsXAction extends AbstractGameAction {

    private final AbstractCard c;
    private final int cost;
    private final boolean forCombat;

    public SingleCardCostsXAction(AbstractCard c, boolean forCombat) {
        this(c, 0, forCombat);
    }

    public SingleCardCostsXAction(AbstractCard c, int cost, boolean forCombat) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.c = c;
        this.forCombat = forCombat;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cost = cost;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (c.cost != -1 && c.cost != -2) {
                if (forCombat) {
                    c.modifyCostForCombat(-cost);
                } else {
                    c.setCostForTurn(cost);
                }
            }
        }
        this.tickDuration();
    }
}
