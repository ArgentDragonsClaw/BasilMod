package basilMod.cards.attacks;

import basilMod.cards.AbstractDynamicCard;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basilMod.BasilMod;
import basilMod.characters.TheScholar;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.util.ArrayList;

import static basilMod.BasilMod.makeCardPath;

public class Rebuttal extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = BasilMod.makeID(Rebuttal.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Rebuttal.png");// "public static final String IMG = makeCardPath("Rebuttal.png");


    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String BASE_DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheScholar.Enums.BASIL_PURPLE;

    private static final int COST = 4;
    private static final int UPGRADED_COST = 3;
    public static ArrayList<Integer> DAMAGE_TAKEN = new ArrayList<>();

    // /STAT DECLARATION/


    public Rebuttal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int d : DAMAGE_TAKEN) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, d), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
        }
        DAMAGE_TAKEN.clear();
    }

    @Override
    public void update() {
        this.initializeDescription();
        super.update();
    }

    @Override
    public void initializeDescription() {
        if (DAMAGE_TAKEN.size() == 0) {
            // default description
            rawDescription = BASE_DESCRIPTION;
        } else {
            int total = 0;
            for (int d : DAMAGE_TAKEN) {
                total += d;
            }

            rawDescription = BASE_DESCRIPTION + EXTENDED_DESCRIPTION[0] + total + EXTENDED_DESCRIPTION[1] + DAMAGE_TAKEN.size() + EXTENDED_DESCRIPTION[2];
        }
        super.initializeDescription();
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        return DAMAGE_TAKEN.size() > 0;
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
