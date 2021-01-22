package com.bluesgao.java.study.future.inquiry;

public class Offer {
    private Long merchantId;
    private Long skuId;
    private Integer price;

    public Offer(Long merchantId, Long skuId, Integer price) {
        this.merchantId = merchantId;
        this.skuId = skuId;
        this.price = price;
    }

    public Offer(Long merchantId, Long skuId) {
        this.merchantId = merchantId;
        this.skuId = skuId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "merchantId='" + merchantId + '\'' +
                ", skuId=" + skuId +
                ", price=" + price +
                '}' + "\n";
    }
}