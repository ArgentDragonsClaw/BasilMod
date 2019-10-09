package basilMod.variables;

import basemod.abstracts.DynamicVariable;
import basilMod.BasilMod;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class MiscValue extends DynamicVariable {
    @Override
    public String key() {
        return BasilMod.makeID("MiscValue");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        return card.misc;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card.misc;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
