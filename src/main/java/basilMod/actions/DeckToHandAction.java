//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package basilMod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class DeckToHandAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    public DeckToHandAction(int numberOfCards, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
    }

    public DeckToHandAction(int numberOfCards) {
        this(numberOfCards, false);
    }

    public void update() {
        ArrayList<AbstractCard> currentDeck = new ArrayList<>();
        currentDeck.addAll(this.player.drawPile.group);
        currentDeck.addAll(this.player.discardPile.group);

        CardGroup deck = new CardGroup(CardGroupType.UNSPECIFIED);
        deck.group = currentDeck;

        deck.sortAlphabetically(true);
        deck.sortByRarityPlusStatusCardType(false);

        if (this.duration == this.startDuration) {
            if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                AbstractCard c;
                Iterator var6;
                if (deck.group.size() <= this.numberOfCards && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();
                    var6 = deck.group.iterator();

                    while (var6.hasNext()) {
                        c = (AbstractCard) var6.next();
                        cardsToMove.add(c);
                    }

                    var6 = cardsToMove.iterator();

                    while (var6.hasNext()) {
                        c = (AbstractCard) var6.next();
                        if (this.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                            if (player.drawPile.contains(c)) {
                                this.player.drawPile.moveToDiscardPile(c);
                            } else {
                                this.player.discardPile.moveToDiscardPile(c);
                            }
                            this.player.createHandIsFullDialog();
                        } else {
                            if (player.drawPile.contains(c)) {
                                this.player.drawPile.moveToHand(c, this.player.drawPile);
                            } else {
                                this.player.discardPile.moveToHand(c, this.player.discardPile);
                            }
                        }
                    }

                    this.isDone = true;
                } else {
                    //
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(deck, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(deck, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(deck, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(deck, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while (var1.hasNext()) {
                    AbstractCard c = (AbstractCard) var1.next();
                    if (this.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        if (player.drawPile.contains(c)) {
                            this.player.drawPile.moveToDiscardPile(c);
                        } else {
                            this.player.discardPile.moveToDiscardPile(c);
                        }
                        this.player.createHandIsFullDialog();
                    } else {
                        c.triggerWhenDrawn(); //Manually make this count as drawing the card. not sure why I have to do this?
                        if (player.drawPile.contains(c)) {
                            this.player.drawPile.moveToHand(c, this.player.drawPile);
                        } else {
                            this.player.discardPile.moveToHand(c, this.player.discardPile);
                        }
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}
