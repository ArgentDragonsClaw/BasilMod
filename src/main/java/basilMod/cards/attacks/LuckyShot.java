package basilMod.cards.attacks;

import basilMod.BasilMod;
import basilMod.CustomTags;
import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import basilMod.util.FickleHelper;
import basilMod.util.InCombatHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static basilMod.BasilMod.makeCardPath;

public class LuckyShot extends AbstractDynamicCard {


// TEXT DECLARATION

    public static final String ID = BasilMod.makeID(LuckyShot.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("LuckyShot.png");// "public static final String IMG = makeCardPath("LuckyShot.png");
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

// /TEXT DECLARATION/


    // STAT DECLARATION
    private static final String[] EXT = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;

    private static final int DAMAGE = 3;
    private static final int UPGRADED_DAMAGE = 1;

// /STAT DECLARATION/


    public LuckyShot(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        timesUpgraded = upgrades;
        tags.add(CustomTags.BASIL_FICKLE);
    }

    public LuckyShot() {
        this(0);
    }


    @Override
    public void triggerWhenDrawn() {
        AbstractCreature p = AbstractDungeon.player;
        if (FickleHelper.getFickle()) {
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(this));
            this.upgrade();
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(p.hand.group);
        cards.addAll(p.discardPile.group);
        cards.addAll(p.drawPile.group);
        cards.addAll(p.exhaustPile.group);
        cards.removeIf((x) -> !x.hasTag(CustomTags.BASIL_FICKLE));
        for (int i = 0; i < cards.size(); i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
    }


    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new LuckyShot(timesUpgraded);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        this.upgradeDamage(UPGRADED_DAMAGE);
        ++this.timesUpgraded;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeDescription();
        initializeTitle();
    }

    @Override
    public void update() {
        super.update();
        initializeDescription();
    }

    @Override
    public void initializeDescription() {
        rawDescription = cardStrings.DESCRIPTION;

        if (InCombatHelper.inCombat()) {
            AbstractPlayer p = AbstractDungeon.player;
            ArrayList<AbstractCard> cards = new ArrayList<>();
            cards.addAll(p.hand.group);
            cards.addAll(p.discardPile.group);
            cards.addAll(p.drawPile.group);
            cards.addAll(p.exhaustPile.group);
            cards.removeIf((x) -> !x.hasTag(CustomTags.BASIL_FICKLE));
            rawDescription += EXT[0] + cards.size() + EXT[1];
        }

        rawDescription += EXT[2];

        super.initializeDescription();
    }
}
