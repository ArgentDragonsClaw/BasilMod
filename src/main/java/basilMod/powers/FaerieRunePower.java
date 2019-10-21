package basilMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basilMod.BasilMod;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;

import static basilMod.BasilMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class FaerieRunePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BasilMod.makeID("FaerieRune");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    private int rs = 0;
    private AbstractCreature player;

    public FaerieRunePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        player = owner;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        if (player.hasPower(RunescarredPower.POWER_ID)) {
            rs = player.getPower(RunescarredPower.POWER_ID).amount;
        } else {
            rs = 0;
        }


        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > player.currentHealth + player.currentBlock) {
            //you would die!

            if (player.hasPower(RunescarredPower.POWER_ID)) {
                rs = player.getPower(RunescarredPower.POWER_ID).amount;
                int overheal = rs - 100;
                int to_recover = (int) Math.ceil((rs / 100.0) * player.maxHealth);
                if (overheal > 0) { // if you overheal, get temp hp
                    int overheal_amount = to_recover - player.maxHealth;

                    AbstractDungeon.effectList.add(new HealEffect(player.hb_x, player.hb_y, to_recover));
                    AbstractDungeon.effectList.add(new HealEffect(player.hb_x, player.hb_y, overheal_amount));
                    AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(player, player, overheal_amount));
                    player.currentHealth = player.maxHealth;
                } else {
                    AbstractDungeon.effectList.add(new HealEffect(player.hb_x, player.hb_y, to_recover));
                    player.currentHealth = to_recover;
                }
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, this));

                return 0;

            }


        }
        return damageAmount; // let damage pass through normally
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (player.hasPower(RunescarredPower.POWER_ID)) {
            rs = player.getPower(RunescarredPower.POWER_ID).amount;
        } else {
            rs = 0;
        }

        description = DESCRIPTIONS[0] + rs + DESCRIPTIONS[1];
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        updateDescription();
        super.renderIcons(sb, x, y, c);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new FaerieRunePower(owner, source, amount);
    }
}
