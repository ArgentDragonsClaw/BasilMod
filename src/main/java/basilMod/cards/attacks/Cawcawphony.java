package basilMod.cards.attacks;

import basilMod.cards.AbstractDynamicCard;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class Cawcawphony extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Cawcawphony.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Cawcawphony.png");// "public static final String IMG = makeCardPath("Cawcawphony.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 2;

    private static final int DAMAGE = 2;
    private static final int UPGRADED_DAMAGE = 1;

    private static final int MAGIC = 10;

// /STAT DECLARATION/


    public Cawcawphony() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = damage = DAMAGE;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster ignored) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "CAW CAW!", 1.0F, 2.0F));
        for (int i = 0; i < magicNumber; i++) {
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage), true));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }
}
