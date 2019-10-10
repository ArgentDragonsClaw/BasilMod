package basilMod.cards.powers;

import basilMod.cards.AbstractDynamicCard;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;

import basilMod.powers.DueDatePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class DueDate extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(DueDate.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("DueDate.png");// "public static final String IMG = makeCardPath("DueDate.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;
    private static final float PERCENT = DueDatePower.PERCENT;


// /STAT DECLARATION/


    public DueDate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = (int) Math.ceil(PERCENT * m.maxHealth);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DueDatePower(m, p, amount), amount));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            return m.currentHealth == m.maxHealth;
        } else return false;
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
