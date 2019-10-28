package basilMod.cards.powers;

import basemod.helpers.BaseModCardTags;
import basilMod.BasilMod;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.powers.FaeFormPower;
import basilMod.util.FickleHelper;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class FaeForm extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(FaeForm.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("FaeForm.png");// "public static final String IMG = makeCardPath("FaeForm.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;


// /STAT DECLARATION/


    @Override
    public void triggerWhenDrawn() {
        //Fickle effect
        if (FickleHelper.getFickle()) {
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(this));
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    public FaeForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(BaseModCardTags.FORM);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FaeFormPower(p, p, 1)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
