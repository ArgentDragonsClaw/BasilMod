package basilMod.cards.attacks;

import basilMod.BasilMod;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class CaffeineJitters extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(CaffeineJitters.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("CaffeineJitters.png");// "public static final String IMG = makeCardPath("CaffeineJitters.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;

    private static final int DAMAGE = 3;
    private static final int UPGRADED_DAMAGE = 1;

    public static int caff_amount = 0;

// /STAT DECLARATION/


    public CaffeineJitters() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (caff_amount > 0) {
            for (int i = 0; i < caff_amount; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
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


    @Override
    public void update() {
        if (caff_amount == 1)
            rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + caff_amount + cardStrings.EXTENDED_DESCRIPTION[1];
        else if (caff_amount > 1)
            rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + caff_amount + cardStrings.EXTENDED_DESCRIPTION[2];
        initializeDescription();
        super.update();
    }

}
