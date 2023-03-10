package theHuman.cards.tokens;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.actions.FireGunRandomlyActionWithEffect;
import theHuman.cards.AbstractDynamicCard;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

@AutoAdd.Ignore
public class PocketPistol extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(PocketPistol.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("PocketPistol.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;

    private static final int DAMAGE = 2;

    public PocketPistol() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 8;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = 3;
        exhaust = true;
        cardsToPreview = null;
        tags.add(HumanMod.HumanCardTags.SHOOTER_HUMAN);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new FireGunRandomlyActionWithEffect(damage,
                                                          defaultSecondMagicNumber,
                                                          magicNumber, 0.5f));
    }
}
