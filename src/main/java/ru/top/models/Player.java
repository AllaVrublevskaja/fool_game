package ru.top.models;

import java.util.*;

public class Player {
    private final String name;
    private final List<Card> cards;

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    /**
     *
     * @param i номер карты в его списке, которую он хочет выкинуть
     * @return карту, которую игрок выкинул
     */
    public Card attack(int i) {
        Card card = cards.get(i);
        cards.remove(i);
        return card;
    }

    /**
     *
     * @param card - карта, которую надо побить
     * @param trumpSuit - козырь
     * @return карту, которой игрок побился в случае удачной защиты,
     * null - если так защититься нельзя
     */
    public Card defend(Card card, Suit trumpSuit) {
        Card defend;
        defend = cards.stream()
                .filter(c -> c.getSuit().equals(card.getSuit()) &&
                        c.getValue() > card.getValue())
                .min(Comparator.comparingInt(Card::getValue))
                .orElse(null);
        if (defend == null && card.getSuit() != trumpSuit )
            defend = cards.stream()
                    .filter(c -> c.getSuit().equals(trumpSuit))
                    .min(Comparator.comparingInt(Card::getValue))
                    .orElse(null);
        if (defend != null) {
            Suit suit = defend.getSuit();
            int num = defend.getValue();
            cards.removeIf(c -> c.getSuit().equals(suit) &&
                    c.getValue() == num );
        }
        return defend;
    }


    public void takeCard(Card card) {
        cards.add(card);
    }

    public void takeCards(Collection<Card> cards) {
        this.cards.addAll(cards);
    }
}
