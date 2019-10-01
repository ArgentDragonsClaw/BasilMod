package basilMod.cards.skills;

import basilMod.CustomTags;
import basilMod.cards.AbstractDynamicCard;
import basilMod.cards.curses.CurseRune;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

import static basilMod.BasilMod.makeCardPath;

public class MixedBag extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(MixedBag.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("MixedBag.png");// "public static final String IMG = makeCardPath("MixedBag.png");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private ArrayList<AbstractCard> runes;

    // /STAT DECLARATION/


    public MixedBag() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        ArrayList<AbstractCard> runes = AbstractDungeon.uncommonCardPool.group;
        runes.removeIf((x) -> !x.hasTag(CustomTags.RUNE));
        this.runes = runes;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> to_draw = new ArrayList<>();

        //get three random runes
        for (int i = 0; i < 3; i++) {
            int choice = AbstractDungeon.cardRandomRng.random(runes.size() - 1);
            AbstractCard to_add = runes.get(choice);
            if (upgraded) {
                to_add.upgrade();
            }
            to_draw.add(to_add);
        }
        //rarely, draw the curse rune
        if (AbstractDungeon.cardRandomRng.random(0f, 1f) > .95) {
            to_draw.remove(0); //Arbitrarily remove the first card and replace it with the curse
            to_draw.add(new CurseRune());
        }


        for (AbstractCard card : to_draw) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
