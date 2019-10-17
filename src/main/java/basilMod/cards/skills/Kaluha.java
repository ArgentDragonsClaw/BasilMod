package basilMod.cards.skills;

import basilMod.cards.AbstractDynamicCard;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;

import basilMod.powers.CaffeinePower;
import basilMod.powers.InebriatedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class Kaluha extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Kaluha.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Kaluha.png");// "public static final String IMG = makeCardPath("Kaluha.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;

    private static final int MAGIC = 1;
    private static final int UPGRADED_MAGIC = 1;

// /STAT DECLARATION/


    public Kaluha() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CaffeinePower(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InebriatedPower(p, p, magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}
