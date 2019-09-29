package basilMod.cards.skills;

import basemod.helpers.BaseModCardTags;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basilMod.BasilMod;

import static basilMod.BasilMod.makeCardPath;

public class Defend extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Defend.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Defend.png");// "public static final String IMG = makeCardPath("Strike.png");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    // /STAT DECLARATION/


    public Defend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;

        this.tags.add(BaseModCardTags.BASIC_DEFEND);


    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom( new GainBlockAction(p, p, block));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
