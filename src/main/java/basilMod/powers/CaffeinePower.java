package basilMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basilMod.BasilMod;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static basilMod.BasilMod.makePowerPath;

public class CaffeinePower extends AbstractPower implements CloneablePowerInterface, OnReceivePowerPower {

    public static final Logger logger = LogManager.getLogger(BasilMod.class.getName());
    public AbstractCreature source;

    public static final String POWER_ID = BasilMod.makeID("Caffeine");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public CaffeinePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }



    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (true) { //If we don't have the coffee cup //TODO:: CHANGE ONCE THE COFFEE CUP EXISTS
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new CaffeinePower(owner, source, amount);
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower.ID.equals(POWER_ID)) {
            // We're applying Caffeine
            if (abstractCreature.hasPower(CaffeinePower.POWER_ID)) {
                if (abstractCreature.getPower(CaffeinePower.POWER_ID).amount + abstractPower.amount >= 3) {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(abstractCreature, source, CaffeinePower.POWER_ID, 3));
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1)); //TODO:: The coffee cup relic doubles this
                }
            }
        }
        return true;
    }
}
