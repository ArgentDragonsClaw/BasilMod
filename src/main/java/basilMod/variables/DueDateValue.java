package basilMod.variables;

import basemod.abstracts.DynamicVariable;
import basilMod.BasilMod;
import basilMod.powers.DueDatePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class DueDateValue extends DynamicVariable {
    private static float PERCENT = DueDatePower.PERCENT;

    @Override
    public String key() {
        return BasilMod.makeID("DueDateValue");
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if (AbstractDungeon.getCurrRoom().combatEvent) {
            AbstractMonster m = AbstractDungeon.getMonsters().hoveredMonster;
            if (m != null) {
                return (int) Math.ceil(m.maxHealth * PERCENT);
            }
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if (AbstractDungeon.getCurrRoom().combatEvent) {
            AbstractMonster m = AbstractDungeon.getMonsters().hoveredMonster;
            if (m != null) {
                return (int) Math.ceil(m.maxHealth * PERCENT);
            }
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return false;
    }
}
