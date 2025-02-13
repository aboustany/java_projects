package FlooringMastery.dto;

import java.math.BigDecimal;

public class Product {
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }
    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }
    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }
    public void setLaborCostPerSquareFoot(BigDecimal LaborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = LaborCostPerSquareFoot;
    }

    @Override
    public String toString() {
        return productType + " - Cost per Sq Ft: $" + costPerSquareFoot + ", Labor Cost per Sq Ft: $" + laborCostPerSquareFoot;
    }
}
