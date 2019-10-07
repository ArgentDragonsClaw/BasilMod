package basilMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basilMod.BasilMod;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

import static basilMod.BasilMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class WhiskeyPower extends AbstractPower implements CloneablePowerInterface, OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = BasilMod.makeID("Whiskey");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public WhiskeyPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {

            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new WhiskeyPower(owner, source, amount);
    }

    @Override
    public boolean onReceivePower(AbstractPower p, AbstractCreature owner, AbstractCreature source) {
        if (owner.isPlayer && p.ID.equals(InebriatedPower.POWER_ID) && !owner.hasPower(InebriatedPower.POWER_ID)) {
            // gain X temp strength and dex
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new StrengthPower(owner, amount)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new LoseStrengthPower(owner, amount)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new DexterityPower(owner, amount)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new LoseDexterityPower(owner, amount)));
        }

        return true;
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.player.hasPower(InebriatedPower.POWER_ID)) {
            // gain X temp strength and dex
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new StrengthPower(owner, amount)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new LoseStrengthPower(owner, amount)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new DexterityPower(owner, amount)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new LoseDexterityPower(owner, amount)));
        }
    }
}