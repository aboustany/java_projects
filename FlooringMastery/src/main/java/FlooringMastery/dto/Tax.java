package FlooringMastery.dto;

import java.math.BigDecimal;

public class Tax {

    private String stateAbbrev;
    private String stateName;
    private BigDecimal taxRate;

    public Tax(String stateAbbrev, String stateName, BigDecimal taxRate) {
        this.stateAbbrev = stateAbbrev;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbrev() {
        return stateAbbrev;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setStateAbbrev(String stateAbbrev) {
        this.stateAbbrev = stateAbbrev;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        return stateAbbrev + " (" + stateName + ") - Tax Rate: " + taxRate + "%";
    }
}
