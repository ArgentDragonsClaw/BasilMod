package basilMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basilMod.BasilMod;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basilMod.BasilMod.makePowerPath;

public class DueDatePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BasilMod.makeID("DueDate");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("duedate84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("duedate32.png"));
    private int healing;

    private static int idOffset = 0;
    public static float PERCENT = 0.1f;


    public DueDatePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID + idOffset;
        idOffset++;

        healing = (int) Math.ceil(PERCENT * owner.maxHealth);
        this.owner = owner;
        this.source = source;
        this.amount = amount;


        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, source, this, 1));
    }

    @Override
    public void onDeath() {
        //if the creature dies and still has this debuff,
        //give the player max hp ( and heal)
        AbstractDungeon.player.increaseMaxHp(healing, true);
    }


    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + healing + DESCRIPTIONS[3];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + healing + DESCRIPTIONS[3];
        }
    }


    @Override
    public AbstractPower makeCopy() {
        return new DueDatePower(owner, source, amount);
    }
}
