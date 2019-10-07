package basilMod.relics;

import basemod.abstracts.CustomRelic;
import basilMod.BasilMod;
import basilMod.actions.DeckToHandAction;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import static basilMod.BasilMod.makeRelicOutlinePath;
import static basilMod.BasilMod.makeRelicPath;

public class ScholarsThesis extends CustomRelic {
    public static final String ID = BasilMod.makeID("ScholarsThesis");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png"));

    private static final int TURNS_TO_ACTIVATE = 3;

    public ScholarsThesis() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(ScholarsNotes.ID);
    }

    @Override
    public void atTurnStartPostDraw() {
        setCounter(counter - 1);
        if (counter <= 0) {
            doCardDraw();
            setCounter(TURNS_TO_ACTIVATE);
        }

    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.loseRelic(ScholarsNotes.ID);
    }

    @Override
    public void atBattleStart() {
        setCounter(0);
    }


    private void doCardDraw() {
        flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new DeckToHandAction(1, true));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + TURNS_TO_ACTIVATE + DESCRIPTIONS[1];
    }

}