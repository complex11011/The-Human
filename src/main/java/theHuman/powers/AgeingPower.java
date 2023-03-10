package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class AgeingPower extends AbstractPower
    implements CloneablePowerInterface {
    public static final String POWER_ID = HumanMod.makeID("AgeingPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("AgeingPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("AgeingPower_32.png"));
    public AbstractCreature source;

    public AgeingPower(final AbstractCreature owner,
                       final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (float) Math.pow(0.96, amount);
        } else {
            return damage;
        }
    }

    @Override
    public void atStartOfTurn() {
        if (this.amount >= 12) {
            if (owner instanceof AbstractMonster) {
                this.flash();
                AbstractMonster mo = (AbstractMonster) owner;
                try {
                    this.addToBot(new InstantKillAction(mo));
                } catch (Exception e) {
                    this.addToBot(new DamageAction(mo,
                                                   new DamageInfo(mo, 100000000,
                                                                  DamageInfo.DamageType.HP_LOSS)));
                    this.addToBot(new DamageAction(mo,
                                                   new DamageInfo(mo, 100000000,
                                                                  DamageInfo.DamageType.THORNS)));
                }
            }
        }
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (!isPlayer) {
            this.flash();
            this.addToBot(new ApplyPowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new AgeingPower(owner, source, amount);
    }

    public static boolean checkIfAlive(AbstractCreature creature) {
        return !(creature.isDying || creature.isDead);
    }
}
