package basilMod.cards.skills;

import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.powers.Caffeine;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basilMod.BasilMod;

import static basilMod.BasilMod.makeCardPath;

public class Sip extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Sip.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Sip.png");// "public static final String IMG = makeCardPath("Sip.png");


    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;

    // /STAT DECLARATION/


    public Sip() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO:: What does this card do?
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Caffeine(p,p,1),1));
        if(this.upgraded) AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 1));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
