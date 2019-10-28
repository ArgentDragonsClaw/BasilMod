package basilMod.cards.skills;

import basilMod.BasilMod;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static basilMod.BasilMod.makeCardPath;

public class Procrastinate extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Procrastinate.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Procrastinate.png");// "public static final String IMG = makeCardPath("Procrastinate.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 0;

    private static final int MAGIC = 1;
    private static final int UP_MAG = 1;

    // /STAT DECLARATION/


    public Procrastinate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        misc = 0;
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenPower(p, misc), misc));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, misc), misc));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, misc), misc));


    }


    @Override
    public void triggerWhenDrawn() {
        misc += magicNumber;
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UP_MAG);
            initializeDescription();
        }
    }
}
