package basilMod.actions;

import basilMod.CustomTags;
import basilMod.cards.curses.CurseRune;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class DrawRuneAction extends AbstractGameAction {
    private static ArrayList<AbstractCard> runes = new ArrayList<>();
    private static float curseChance = 0.95f;
    private int amount;
    private boolean upgraded;
    private boolean curseAllowed;

    public DrawRuneAction(int runesToDraw, boolean upgraded, boolean curseAllowed) {
        this.amount = runesToDraw;
        this.upgraded = upgraded;
        this.curseAllowed = curseAllowed;

        if (runes.isEmpty()) {
            //We only need to do this once per game

            //3x common runes
            runes.addAll(AbstractDungeon.commonCardPool.group);
            runes.addAll(AbstractDungeon.commonCardPool.group);
            runes.addAll(AbstractDungeon.commonCardPool.group);
            //2x uncommon runes
            runes.addAll(AbstractDungeon.uncommonCardPool.group);
            runes.addAll(AbstractDungeon.uncommonCardPool.group);
            //1x rare runes
            runes.addAll(AbstractDungeon.rareCardPool.group);
            runes.removeIf((x) -> !x.hasTag(CustomTags.BASIL_RUNE));

            Collections.shuffle(runes); // probably not really necessary to shuffle the list, but I'm gonna do it anyway
        }
    }


    @Override
    public void update() {
        ArrayList<AbstractCard> to_draw = new ArrayList<>();

        //get random runes
        for (int i = 0; i < amount; i++) {
            int choice = AbstractDungeon.miscRng.random(runes.size() - 1);
            AbstractCard to_add = runes.get(choice);
            if (upgraded) {
                to_add.upgrade();
            }
            to_draw.add(to_add);
        }

        if (curseAllowed) {
            //rarely, draw the curse rune
            if (AbstractDungeon.cardRandomRng.random(0f, 1f) > curseChance) {
                to_draw.remove(0); //Arbitrarily remove the first card and replace it with the curse
                to_draw.add(new CurseRune());
            }
        }


        for (AbstractCard card : to_draw) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
        }

        this.isDone = true;
    }
}
