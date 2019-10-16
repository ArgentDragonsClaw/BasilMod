package basilMod.cards.attacks;

import basilMod.cards.AbstractDynamicCard;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;

import basilMod.powers.InebriatedPower;
import basilMod.util.FickleHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import static basilMod.BasilMod.makeCardPath;

public class NockOneBack extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(NockOneBack.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("NockOneBack.png");// "public static final String IMG = makeCardPath("NockOneBack.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DAMAGE = 7;
    private static final int UPGRADED_DAMAGE = 2;

    private static final int MAGIC = 1;
    private static final int UPGRADED_MAGIC = 1;

// /STAT DECLARATION/


    public NockOneBack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = damage = DAMAGE;

    }

    @Override
    public void triggerWhenDrawn() {
        //Fickle effect
        AbstractCreature p = AbstractDungeon.player;
        if (FickleHelper.getFickle()) {
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(this));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new InebriatedPower(p, p, magicNumber), magicNumber));
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADED_MAGIC);
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }
}
