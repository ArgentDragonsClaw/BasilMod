package basilMod.cards.skills;

import basilMod.BasilMod;
import basilMod.CustomTags;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.powers.RunescarredPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static basilMod.BasilMod.makeCardPath;

public class BlankRune extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(BlankRune.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("BlankRune.png");// "public static final String IMG = makeCardPath("LightningRune.png");


    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;

    // /STAT DECLARATION/
    private ArrayList<AbstractCard> runes;


    public BlankRune() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        retain = true;
        exhaust = true;
        // We're not tagging this card as a rune, because it'd be pretty dumb to draw randomly.
        // Also, you could recursively loop playing this card. Bad.

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> runes = (ArrayList<AbstractCard>) AbstractDungeon.player.masterDeck.group.clone();
        runes.removeIf((x) -> !x.hasTag(CustomTags.BASIL_RUNE));

        if (runes.size() >= 1) {
            // random
            int index = AbstractDungeon.cardRandomRng.random(0, runes.size() - 1);
            AbstractCard c = runes.get(index).makeCopy();
            c.purgeOnUse = true;
            c.freeToPlayOnce = true;
            c.use(p, m);
        } else {
            // stll get runescarred, even though there's no rune to copy....
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RunescarredPower(p, p, 1), 1));
        }


    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();

            exhaust = false;
        }
    }

    @Override
    public void update() {
        super.update();
        retain = true;
    }

}
