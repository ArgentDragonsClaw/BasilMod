package basilMod.cards.skills;

import basilMod.BasilMod;
import basilMod.CustomTags;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.powers.RunescarredPower;
import basilMod.util.InCombatHelper;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.SlowPower;

import static basilMod.BasilMod.makeCardPath;

public class FrostRune extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(FrostRune.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("FrostRune.png");// "public static final String IMG = makeCardPath("LightningRune.png");


    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;

    // /STAT DECLARATION/


    public FrostRune() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        retain = true;
        exhaust = true;
        damage = baseDamage = 1;
        tags.add(CustomTags.BASIL_RUNE);

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower pow = p.getPower(RunescarredPower.POWER_ID);
        int amount = 1;
        if (pow != null) {
            amount += pow.amount;
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlowPower(m, amount), amount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RunescarredPower(p, p, 1), 1));
    }


    @Override
    public void initializeDescription() {
        rawDescription = cardStrings.DESCRIPTION;

        if (!InCombatHelper.inCombat()) rawDescription += EXTENDED_DESCRIPTION[0];
        else rawDescription += EXTENDED_DESCRIPTION[1];
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
