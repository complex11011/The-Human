package theHuman.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import theHuman.util.HumanUtils;

public class FindWeaponAction extends AbstractGameAction {

    private ArrayList<AbstractCard> cardsToShuffle = new ArrayList<>();

    public FindWeaponAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            cardsToShuffle = getWeapons(amount);
            findWeapon();
        }
        this.tickDuration();
    }

    private void findWeapon() {
        for (AbstractCard c : cardsToShuffle) {
            c.unhover();
            if (cardsToShuffle.size() < 6) {
                AbstractDungeon.effectList.add(
                    new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy(),
                                                   (float) Settings.WIDTH /
                                                   2.0f,
                                                   (float) Settings.HEIGHT /
                                                   2.0f));
            } else {
                AbstractDungeon.effectList.add(
                    new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy()));
            }
        }
        cardsToShuffle.clear();
    }

    private AbstractCard getRandomWeapon() {
        return HumanUtils.getRandomWeapon();
    }

    private ArrayList<AbstractCard> getWeapons(int amount) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        while (cards.size() < amount) {
            AbstractCard card = getRandomWeapon();
            cards.add(card);
        }
        return cards;
    }
}
