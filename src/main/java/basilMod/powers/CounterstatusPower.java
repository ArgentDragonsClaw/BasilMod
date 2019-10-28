package basilMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import basilMod.BasilMod;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static basilMod.BasilMod.makePowerPath;

public class CounterstatusPower extends AbstractPower implements CloneablePowerInterface {

    public static final Logger logger = LogManager.getLogger(BasilMod.class.getName());
    public AbstractCreature source;

    public static final String POWER_ID = BasilMod.makeID("Counterstatus");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("counterstatus84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("counterstatus32.png"));
    private boolean upgraded;
    private int cost = 0;


    public CounterstatusPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this(owner, source, amount, false);
    }


    public CounterstatusPower(final AbstractCreature owner, final AbstractCreature source, final int amount, boolean upgraded) {

        if (!upgraded) {
            name = NAME;
            ID = POWER_ID;
        } else {
            name = NAME + "+";
            ID = POWER_ID + "+";
        }

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.upgraded = upgraded;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (!upgraded) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new CounterstatusPower(owner, source, amount, upgraded);
    }


    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.STATUS) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, amount));
            if (upgraded)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new ThornsPower(owner, amount), amount));
        }
    }
}
