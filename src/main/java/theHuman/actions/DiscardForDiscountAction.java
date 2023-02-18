package theHuman.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardForDiscountAction extends AbstractGameAction {

    private final AbstractPlayer player;
    private final int toDiscard;

    public DiscardForDiscountAction(int toDiscard) {
        this.toDiscard = toDiscard;
        this.player = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = (this.startDuration = Settings.ACTION_DUR_FAST);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            AbstractDungeon.handCardSelectScreen.open("Discard", toDiscard, false,
                                                      true);
            this.addToTop(new WaitAction(0.25F));
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                if (player.hand.isEmpty()) {
                    this.isDone = true;
                    return;
                }
                this.addToTop(new SingleCardCostsXAction(player.hand.getRandomCard(true), false));
            }

            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.player.hand.moveToDiscardPile(c);
                GameActionManager.incrementDiscard(false);
                c.triggerOnManualDiscard();
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}

