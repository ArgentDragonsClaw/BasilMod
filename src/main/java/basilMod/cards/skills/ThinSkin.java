package basilMod.cards.skills;

import basilMod.BasilMod;
import basilMod.actions.ThinSkinAction;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static basilMod.BasilMod.makeCardPath;

public class ThinSkin extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(ThinSkin.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("ThinSkin.png");// "public static final String IMG = makeCardPath("ThinSkin.png");
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

// /TEXT DECLARATION/


    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = -1;

    private static final int MAGIC = 3;
    private static final int UPGRADED_MAGIC = 2;

// /STAT DECLARATION/


    public ThinSkin() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ThinSkinAction(p, -1, magicNumber, freeToPlayOnce));
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
