package basilMod.cards.powers;

import basilMod.BasilMod;
import basilMod.CustomTags;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.powers.FaerieRunePower;
import basilMod.powers.RunescarredPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlightPower;

import static basilMod.BasilMod.makeCardPath;

public class CrowRune extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(CrowRune.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("CrowRune.png");// "public static final String IMG = makeCardPath("LightningRune.png");


    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 2;

    // /STAT DECLARATION/


    public CrowRune() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        //retain = true;
        damage = baseDamage = 1;
        tags.add(CustomTags.BASIL_RUNE);
        isEthereal = true;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower pow = p.getPower(RunescarredPower.POWER_ID);
        int amount = 1;
        if (pow != null) {
            amount += pow.amount;
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlightPower(p, amount), amount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RunescarredPower(p, p, 1), 1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            isEthereal = false;
            retain = true;
        }
    }

    @Override
    public void update() {
        super.update();
        if (upgraded) retain = true;
    }

}
