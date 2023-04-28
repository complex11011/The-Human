package theHuman.cards.tokens;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import theHuman.HumanMod;
import theHuman.cards.AbstractDynamicCard;
import theHuman.characters.TheHuman;

import static theHuman.HumanMod.makeCardPath;

@AutoAdd.Ignore
public class MK3A2Grenade extends AbstractDynamicCard {

    public static final String ID =
        HumanMod.makeID(MK3A2Grenade.class.getSimpleName());
    public static final CardStrings cardStrings =
        CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.NAME;
    public static final String IMG = makeCardPath("MK3A2Grenade.png");

    public static final CardColor COLOR = TheHuman.Enums.COLOR_SKIN;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;

    private static final int DAMAGE = 20;

    public MK3A2Grenade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        isMultiDamage = true;
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                this.addToBot(new VFXAction(new ExplosionSmallEffect(mo.hb.cX, mo.hb.cY), 0.1F));
            }
        }
        this.addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL,
                                                 AbstractGameAction.AttackEffect.FIRE));
    }
}
