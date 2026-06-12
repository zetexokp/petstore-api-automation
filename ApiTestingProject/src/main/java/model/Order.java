package model;

public class Order {

    private Long id;
    private Long petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

    public Order() {
    }

    public Order(Long id,
                 Long petId,
                 Integer quantity,
                 String shipDate,
                 String status,
                 Boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    public Long getId() {
        return id;
    }

    public Long getPetId() {
        return petId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}