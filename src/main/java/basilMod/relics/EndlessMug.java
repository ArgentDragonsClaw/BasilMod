package basilMod.relics;

import basemod.abstracts.CustomRelic;
import basilMod.BasilMod;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

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
