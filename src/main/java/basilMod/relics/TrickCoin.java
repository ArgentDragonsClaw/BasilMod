package basilMod.relics;

import basemod.abstracts.CustomRelic;
import basilMod.BasilMod;
import basilMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static basilMod.BasilMod.makeRelicOutlinePath;
import static basilMod.BasilMod.makeRelicPath;

public class TrickCoin extends CustomRelic implements ClickableRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = BasilMod.makeID("TrickCoin");

    private static final Texture IMG_HEADS = TextureLoader.getTexture(makeRelicPath("trick_coin_heads.png"));
    private static final Texture IMG_TAILS = TextureLoader.getTexture(makeRelicPath("trick_coin_tails.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png")); // im lazy and theyre both circles

    private boolean isPlayerTurn = false; // We should make sure the relic is only activateable during our turn, not the enemies'.

    public boolean isHeads = true;

    public TrickCoin() {
        super(ID, IMG_HEADS, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
        this.description = DESCRIPTIONS[0];
        tips.clear();
        tips.add(new PowerTip(name + " (Heads)", description));
    }

    @Override
    public void onEquip() {
        this.description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name + " (Heads)", description));
    }

    @Override
    public void onRightClick() {// On right click
        if (!isObtained || !isPlayerTurn) {
            // If it has been used this turn, the player doesn't actually have the relic (i.e. it's on display in the shop room), or it's the enemy's turn
            return; // Don't do anything.
        }

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) { // Only if you're in combat

            flash(); // Flash
            stopPulse(); // And stop the pulsing animation (which is started in atPreBattle() below)

            this.isHeads = !this.isHeads;

            this.description = getUpdatedDescription();
            if (isHeads) {
                this.setTexture(IMG_HEADS);
                this.tips.clear();
                this.tips.add(new PowerTip(name + " (Heads)", description));
            } else {
                this.setTexture(IMG_TAILS);
                this.tips.clear();
                this.tips.add(new PowerTip(name + " (Tails)", description));
            }

            this.update();


        }

    }

    @Override
    public void atPreBattle() {
        beginLongPulse();     // Pulse while the player can click on it.
    }

    public void atTurnStart() {
        isPlayerTurn = true; // It's our turn!
        beginLongPulse(); // Pulse while the player can click on it.
    }

    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false; // Not our turn now.
        stopPulse();
    }


    @Override
    public void onVictory() {
        stopPulse(); // Don't keep pulsing past the victory screen/outside of combat.
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        if (isHeads) {
            return DESCRIPTIONS[1];
        } else {
            return DESCRIPTIONS[2];
        }


    }

}
