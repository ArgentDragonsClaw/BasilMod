package basilMod.relics;

import basemod.abstracts.CustomRelic;
import basilMod.BasilMod;
import basilMod.actions.DrawRuneAction;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static basilMod.BasilMod.makeRelicOutlinePath;
import static basilMod.BasilMod.makeRelicPath;

public class RuneBagRelic extends CustomRelic {


    public static final String ID = BasilMod.makeID("RuneBag");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("rune_bag.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("rune_bag.png"));
    private static final int COUNTER_MAX = 3;

    public RuneBagRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (++counter >= COUNTER_MAX) {
            AbstractDungeon.actionManager.addToBottom(new DrawRuneAction(1, false, false));
            counter = 0;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + COUNTER_MAX + DESCRIPTIONS[1];
    }


}
