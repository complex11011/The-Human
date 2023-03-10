package theHuman.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHuman.HumanMod;
import theHuman.util.TextureLoader;

import static theHuman.HumanMod.makePowerPath;

public class SilverGenerationPower extends TwoAmountPower
    implements CloneablePowerInterface {

    public static final String POWER_ID =
        HumanMod.makeID("SilverGenerationPower");
    private static final PowerStrings powerStrings =
        CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 =
        TextureLoader.getTexture(makePowerPath("SilverGenerationPower_84.png"));
    private static final Texture tex32 =
        TextureLoader.getTexture(makePowerPath("SilverGenerationPower_32.png"));
    public AbstractCreature source;

    public SilverGenerationPower(final AbstractCreature owner,
                                 final AbstractCreature source,
                                 final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        this.amount2 = 1;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        this.amount2++;
        if (this.amount2 == 3) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                this.addToBot(new ApplyPowerAction(mo, mo,
                                                   new AgeingPower(mo, mo,
                                                                   amount)));
            }
            this.amount2 = 0;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new SilverGenerationPower(owner, source, amount);
    }
}
