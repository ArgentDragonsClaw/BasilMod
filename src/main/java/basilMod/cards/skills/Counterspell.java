package basilMod.cards.skills;

import basilMod.BasilMod;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static basilMod.BasilMod.makeCardPath;

public class Counterspell extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Counterspell.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Counterspell.png");// "public static final String IMG = makeCardPath("Counterspell.png");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UP_MAGIC = 1;
    // /STAT DECLARATION/


    public Counterspell() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.intent.name().contains("DEBUFF")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, magicNumber), magicNumber));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UP_MAGIC);
            initializeDescription();
        }
    }
}
