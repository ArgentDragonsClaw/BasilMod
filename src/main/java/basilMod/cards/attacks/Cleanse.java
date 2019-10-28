package basilMod.cards.attacks;

import basilMod.BasilMod;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.powers.RunescarredPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class Cleanse extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Cleanse.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Cleanse.png");// "public static final String IMG = makeCardPath("Cleanse.png");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 2;

    private static final int MAGIC = 5;
    private static final int MAGIC_UP = 3;

    // /STAT DECLARATION/


    public Cleanse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int amount = p.getPower(RunescarredPower.POWER_ID).amount;
        for (; amount > 0; amount--) {
            AbstractMonster target = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(target, new DamageInfo(p, magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, RunescarredPower.POWER_ID));

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return p.hasPower(RunescarredPower.POWER_ID);
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(MAGIC_UP);
            initializeDescription();
        }
    }
}
