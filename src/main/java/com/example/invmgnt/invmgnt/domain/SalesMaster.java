package com.example.invmgnt.invmgnt.domain;

import javax.persistence.*;

@Entity
@Table(name= "STOCK_MASTER")
public class SalesMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "INVOICE_NO")
    private String invoiceNo;

    @ManyToOne(cascade = CascadeType.ALL)
    private ItemMaster item;

    @Column(name = "QTY")
    private Double qty;

    @Column(name = "UNIT_PRICE")
    private Double unitPrice;

    @Column(name = "AMOUNT")
    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
