package basilMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.interfaces.PostPowerApplySubscriber;
import basilMod.BasilMod;
import basilMod.powers.CaffeinePower;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static basilMod.BasilMod.makeRelicOutlinePath;
import static basilMod.BasilMod.makeRelicPath;

public class EndlessMug extends CustomRelic {


    public static final String ID = BasilMod.makeID("EndlessMug");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("endless_mug.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("endless_mug.png"));

    public EndlessMug() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
