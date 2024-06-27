package VendingMachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class Change {

    public static BigDecimal getChange(BigDecimal amountDue, BigDecimal payment) {
        BigDecimal change = payment.subtract(amountDue);
        return change.setScale(2, RoundingMode.HALF_UP);
    }

    public static Map<Coin, Integer> calculateChangeCoins(BigDecimal change) {
        Map<Coin, Integer> changeCoins = new LinkedHashMap<>();
        BigDecimal remainingChange = change;
        for(Coin coin: Coin.values()){
            BigDecimal[] divideAndRemainder = remainingChange.divideAndRemainder(coin.getValue());
            int numCoins = divideAndRemainder[0].intValue();
            if(numCoins > 0){
                changeCoins.put(coin, numCoins);
                remainingChange = divideAndRemainder[1];
            }
        }
        return changeCoins;
    }

    public enum Coin {
        QUARTER(BigDecimal.valueOf(0.25)),
        DIME(BigDecimal.valueOf(0.10)),
        NICKEL(BigDecimal.valueOf(0.05)),
        PENNY(BigDecimal.valueOf(0.01));

        private BigDecimal value;
        private Coin(BigDecimal value){
            this.value = value;
        }
        public BigDecimal getValue(){
            return value;
        }
    }
}
