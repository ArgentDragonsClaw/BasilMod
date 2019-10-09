package basilMod.cards.skills;

import basilMod.cards.AbstractDynamicCard;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static basilMod.BasilMod.makeCardPath;
import static java.util.Collections.shuffle;

public class AnotherBook extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(AnotherBook.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("AnotherBook.png");// "public static final String IMG = makeCardPath("AnotherBook.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;


// /STAT DECLARATION/


    public AnotherBook() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO:: What does this card do?
        ArrayList<AbstractCard> library = CardLibrary.getAllCards();
        library.removeIf((x) -> x.type != CardType.SKILL);
        shuffle(library);
        int index = AbstractDungeon.cardRandomRng.random(library.size() - 1);


        AbstractCard to_draw = library.get(index).makeCopy();

        if (upgraded) {
            to_draw.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(to_draw));
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
