package com.example.invmgnt.invmgnt.domain;

import javax.persistence.*;

@Entity
@Table(name= "STOCK_BALANCE")
public class StockBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

//    @Column(name = "ITEM_ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private ItemMaster item;

    @Column(name = "QTY")
    private Double qty;

    @Column(name = "AVG_WIGHT_PRICE")
    private String avgWeightPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemMaster getItem() {
        return item;
    }

    public void setItem(ItemMaster item) {
        this.item = item;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getAvgWeightPrice() {
        return avgWeightPrice;
    }

    public void setAvgWeightPrice(String avgWeightPrice) {
        this.avgWeightPrice = avgWeightPrice;
    }
}
