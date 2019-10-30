package basilMod.cards.attacks;

import basilMod.BasilMod;
import basilMod.CustomTags;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.util.FickleHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class FinalsWeek extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(FinalsWeek.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("FinalsWeek.png");// "public static final String IMG = makeCardPath("FinalsWeek.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 7;

    private static final int DAMAGE = 7;


    private static final int MAGIC = 1;
    private static final int UPGRADED_MAGIC = 1;

// /STAT DECLARATION/


    public FinalsWeek() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = damage = DAMAGE;
        exhaust = true;

        tags.add(CustomTags.BASIL_FICKLE);
    }


    @Override
    public void setCostForTurn(int amt) {
        // no cost modification allowed
    }

    @Override
    public void updateCost(int amt) {
        // no cost modification allowed
    }

    @Override
    public void modifyCostForCombat(int amt) {
        // no cost modification allowed
    }

    @Override
    public void modifyCostForTurn(int amt) {
        // no cost modification allowed
    }

    @Override
    public void triggerWhenDrawn() {
        if (FickleHelper.getFickle()) {
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(this));
            // I've prevented the 'correct' way to modify these costs, so we do it manually.
            this.cost -= magicNumber;
            if (this.cost < 0) this.cost = 0;
            this.costForTurn -= magicNumber;
            if (this.costForTurn < 0) this.costForTurn = 0;
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster ignored) {
        for (int i = 0; i < 7; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, new int[]{damage}, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
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
