package basilMod.cards.attacks;

import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static basilMod.BasilMod.makeCardPath;

public class Rebuttal extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Rebuttal.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Rebuttal.png");// "public static final String IMG = makeCardPath("Rebuttal.png");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 5;
    private static final int UPGRADED_COST = 4;

    // /STAT DECLARATION/


    public Rebuttal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.intent.name().contains("ATTACK")) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, m.getIntentDmg())));
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
