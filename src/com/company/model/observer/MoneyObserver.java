package com.company.model.observer;

import com.company.model.GameDisplay;
import com.company.model.component.Player;

import java.util.Map;

public class MoneyObserver implements PlayerObserver {
    public static final String DEFAULT_NAME = "Money Change Per Turn";
    public final Map<Player, Integer> money;

    public MoneyObserver(Map<Player, Integer> money) {
        this.money = money;
    }

    public void update(Player player) {
        int amount = player.getAmount();
        if (!money.containsKey(player)) {
            compare(player, Player.DEFAULT_AMOUNT, amount);
            money.put(player, amount);
            return;
        }

        if (!money.get(player).equals(amount)) {
            compare(player, money.get(player), amount);
            money.replace(player, amount);
        }
    }

    private void compare(Player player, int oldAmount, int newAmount) {
        int diff = newAmount - oldAmount;
        if (diff > 0) {
            GameDisplay.infoMessage(String.format("%s gain %d HKD in this turn", player, diff));
        }
        if (diff < 0) {
            GameDisplay.infoMessage(String.format("%s loss %d HKD in this turn", player, -diff));
        }

    }
}
