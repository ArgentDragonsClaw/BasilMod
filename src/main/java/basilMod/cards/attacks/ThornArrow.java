package basilMod.cards.attacks;

import basilMod.BasilMod;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.combat.FlashPowerEffect;

import static basilMod.BasilMod.makeCardPath;

public class ThornArrow extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(ThornArrow.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("ThornArrow.png");// "public static final String IMG = makeCardPath("ThornArrow.png");
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

// /TEXT DECLARATION/


    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;

    private static final int MAGIC = 1;
    private static final int UPGRADED_MAGIC = 1;

// /STAT DECLARATION/


    public ThornArrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        damageType = DamageInfo.DamageType.HP_LOSS;

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(ThornsPower.POWER_ID)) return false;
        else return super.canUse(p, m);
    }

    @Override
    protected String getCantPlayMessage() {
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower thorn = p.getPower(ThornsPower.POWER_ID);
        int amt = thorn.amount;
        amt *= magicNumber;
        AbstractDungeon.effectList.add(new FlashPowerEffect(thorn));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, amt, damageType), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
