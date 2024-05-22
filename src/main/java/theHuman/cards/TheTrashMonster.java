package theHuman.cards;

import static theHuman.HumanMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHuman.HumanMod;
import theHuman.characters.TheHuman;
import theHuman.powers.TrashMonsterPower;

public class TheTrashMonster extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(TheTrashMonster.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG = makeCardPath("TheTrashMonster.png");
    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 3;
    public static final int DAMAGE = 5;

    private static final int BLOCK = 8;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public TheTrashMonster() {
        super(ID, IMG, COST,
            DESCRIPTION[0] + MAGIC_NUMBER + DESCRIPTION[1], TYPE, COLOR,
            RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(-1);
            this.isInnate = true;
            this.rawDescription = DESCRIPTION[0] + magicNumber + DESCRIPTION[1];
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(TrashMonsterPower.POWER_ID)) {
            this.addToBot(new ApplyPowerAction(p, p,
                new TrashMonsterPower(p, p, 1, 0,
                    magicNumber)));
        } else {
            int limit = ((TrashMonsterPower) p.getPower(TrashMonsterPower.POWER_ID)).getLimit();
            this.addToBot(new ApplyPowerAction(p, p,
                new TrashMonsterPower(p, p, 1, 0,
                    limit)));
        }
        this.addToBot(new GainBlockAction(p, p, block));
    }
}
