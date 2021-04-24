package com.duarbd.duarclientapp.model;

public class ModelShopItem /*ref DuarUserApp->ModelMenuCart*/ {
    private String itemID;// ref: modelMenuCardId
    private int type; //category type=1 & menuItem type=2; if no category available root
    private String itemCategoryName; //ref: menuCategoryName
    private String itemName; //ref: menuItemName
    private Double itemPrice;// ref: menuItemPrice;
    private String  itemDescription;// ref:menuItemKeyNote;
    private boolean hasAddon;
    private boolean hasDiscount;
    private int discountPercentage;

    private boolean isAvailable;

    public ModelShopItem(String itemID, int type, String itemCategoryName, String itemName, Double itemPrice, String itemDescription, boolean hasAddon, boolean hasDiscount, boolean isAvailable ,int discountPercentage) {
        this.itemID = itemID;
        this.type = type;
        this.itemCategoryName = itemCategoryName;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDescription = itemDescription;
        this.hasAddon = hasAddon;
        this.hasDiscount = hasDiscount;
        this.discountPercentage = discountPercentage;
        this.isAvailable = isAvailable;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public boolean isHasAddon() {
        return hasAddon;
    }

    public void setHasAddon(boolean hasAddon) {
        this.hasAddon = hasAddon;
    }

    public boolean isHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
