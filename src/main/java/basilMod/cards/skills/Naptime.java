package basilMod.cards.skills;

import basilMod.BasilMod;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class Naptime extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Naptime.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Naptime.png");// "public static final String IMG = makeCardPath("Naptime.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;


// /STAT DECLARATION/


    public Naptime() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster unused) {
        //MAKE THEM SLEEP
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            byte i = m.moveHistory.get(m.moveHistory.size() - 1);
            m.setMove(i, AbstractMonster.Intent.SLEEP, 0);
            m.createIntent();
            m.refreshIntentHbLocation();
            m.nextMove = (byte) -1;
        }
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
