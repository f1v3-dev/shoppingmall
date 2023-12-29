package com.nhnacademy.shoppingmall.product.domain;

import java.util.Objects;

public class Product {

    /**
     * CREATE TABLE Products (
     *                           ProductID	INT	auto_increment,
     *                           ModelNumber	nvarchar(10),
     *                           ModelName	nvarchar(120),
     *                           ProductImage	nvarchar(30),
     *                           UnitCost	decimal(15),
     *                           Description	text,
     *
     *                           CONSTRAINT pk_Products PRIMARY KEY(ProductID),
     *                           CONSTRAINT fk_Products_Categories FOREIGN KEY(CategoryID) REFERENCES Categories(CategoryID)
     * );
     */

    private Integer productId;
    private String modelNumber;
    private String modelName;

    private String productImage;
    private Integer unitCost;
    private String description;

    public Product(String modelNumber, String modelName, String productImage, Integer unitCost,
                   String description) {
        this.productId = null;
        this.modelNumber = modelNumber;
        this.modelName = modelName;
        this.productImage = productImage;
        this.unitCost = unitCost;
        this.description = description;
    }

    public Product(Integer productId, String modelNumber, String modelName, String productImage,
                   Integer unitCost, String description) {
        this.productId = productId;
        this.modelNumber = modelNumber;
        this.modelName = modelName;
        this.productImage = productImage;
        this.unitCost = unitCost;
        this.description = description;
    }

    public int getProductId() {
        return productId;
    }


    public String getModelNumber() {
        return modelNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public String getProductImage() {
        return productImage;
    }

    public Integer getUnitCost() {
        return unitCost;
    }

    public String getDescription() {
        return description;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return productId.equals(product.productId) &&
                Double.compare(unitCost, product.unitCost) == 0 &&
                Objects.equals(modelNumber, product.modelNumber) &&
                Objects.equals(modelName, product.modelName) &&
                Objects.equals(productImage, product.productImage) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, modelNumber, modelName, productImage, unitCost, description);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", modelNumber='" + modelNumber + '\'' +
                ", modelName='" + modelName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", unitCost=" + unitCost +
                ", description='" + description + '\'' +
                '}';
    }


}
