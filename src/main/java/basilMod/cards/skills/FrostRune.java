package basilMod.cards.skills;

import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.powers.Runescarred;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.powers.AbstractPower;
import basilMod.CustomTags;
import com.megacrit.cardcrawl.powers.SlowPower;

import static basilMod.BasilMod.makeCardPath;

public class FrostRune extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(FrostRune.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("FrostRune.png");// "public static final String IMG = makeCardPath("LightningRune.png");


    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    // /STAT DECLARATION/


    public FrostRune() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        retain = true;
        exhaust = true;
        damage = baseDamage = 1;
        tags.add(CustomTags.RUNE);

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower pow = p.getPower(Runescarred.POWER_ID);
        int amount = 1;
        if (pow != null) {
            amount += pow.amount;
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlowPower(m, amount), amount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Runescarred(p, p, 1), 1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            rawDescription = UPGRADE_DESCRIPTION;
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();

            exhaust = false;
        }
    }
}
