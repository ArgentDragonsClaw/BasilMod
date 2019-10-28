package basilMod.util;

import basilMod.BasilMod;
import basilMod.relics.TrickCoin;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FickleHelper {

    public static boolean getFickle() {
        float rand = AbstractDungeon.cardRandomRng.random();

        double base_chance = 0.5;
        if (AbstractDungeon.player.hasRelic(TrickCoin.ID)) {
            TrickCoin relic = (TrickCoin) AbstractDungeon.player.getRelic(TrickCoin.ID);
            double trick_inc = 0.25;
            if (relic.isHeads) {
                BasilMod.logger.info("Fickle effect, when heads");
                return (rand <= base_chance - trick_inc);
            } else {

                BasilMod.logger.info("Fickle effect, when tails");
                return (rand <= base_chance + trick_inc);
            }
        }

        BasilMod.logger.info("Fickle effect, no coin");
        return (rand <= base_chance);
    }

}
