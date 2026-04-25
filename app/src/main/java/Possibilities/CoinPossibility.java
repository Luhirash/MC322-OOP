package Possibilities;

public class CoinPossibility extends Possibility {
    
    private int coin;

    public CoinPossibility(String description, int coin) {
        super(description);
        this.coin = coin;
    }

    public int getCoins() {
        return this.coin;
    }
}
