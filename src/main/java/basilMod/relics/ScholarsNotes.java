package basilMod.relics;

import basemod.abstracts.CustomRelic;
import basilMod.BasilMod;
import basilMod.actions.DeckToHandAction;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static basilMod.BasilMod.makeRelicOutlinePath;
import static basilMod.BasilMod.makeRelicPath;

public class ScholarsNotes extends CustomRelic {
    public static final String ID = BasilMod.makeID("ScholarsNotes");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("scholars_notes.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("scholars_notes.png"));

    public ScholarsNotes() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }


    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new DeckToHandAction(1, true));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
