package basilMod.cards.skills;

import basilMod.BasilMod;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.powers.RunescarredPower;
import basilMod.util.InCombatHelper;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static basilMod.BasilMod.makeCardPath;

public class GreenRune extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(GreenRune.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("GreenRune.png");// "public static final String IMG = makeCardPath("RedRune.png");
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

// /TEXT DECLARATION/


    // STAT DECLARATION
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;

// /STAT DECLARATION/


    public GreenRune() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        retain = true;
        exhaust = true;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower pow = p.getPower(RunescarredPower.POWER_ID);
        int amount = 1;
        if (pow != null) {
            amount += pow.amount;
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, amount));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RunescarredPower(p, p, 1), 1));
    }

    @Override
    public void initializeDescription() {
        rawDescription = cardStrings.DESCRIPTION;

        if (!InCombatHelper.inCombat()) rawDescription += EXTENDED_DESCRIPTION[0];
        else {
            rawDescription += EXTENDED_DESCRIPTION[1];

            AbstractPower pow = AbstractDungeon.player.getPower(RunescarredPower.POWER_ID);
            int amount = 1;
            if (pow != null) {
                amount += pow.amount;
            }


            if (amount == 1) rawDescription += EXTENDED_DESCRIPTION[3];
            else rawDescription += EXTENDED_DESCRIPTION[4];
        }
        if (!upgraded) rawDescription += EXTENDED_DESCRIPTION[2];


        super.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
            exhaust = false;
        }
    }


    @Override
    public void update() {
        super.update();
        initializeDescription();
        retain = true;
    }
}
