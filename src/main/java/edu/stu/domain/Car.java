package edu.stu.domain;

public class Car {

    private String brand;

    // 轮胎的半径，单位dm
    private Double tire;

    private Integer price;

    private Integer maxSpeed;

    public Car() {
    }

    public Car(String brand, Double tire, Integer price) {
        this.brand = brand;
        this.tire = tire;
        this.price = price;
    }

    public Car(String brand, Integer price, Integer maxSpeed) {
        this.brand = brand;
        this.price = price;
        this.maxSpeed = maxSpeed;
    }

    public Car(String brand, Double tire, Integer price, Integer maxSpeed) {
        this.brand = brand;
        this.tire = tire;
        this.price = price;
        this.maxSpeed = maxSpeed;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getTire() {
        return tire;
    }

    public void setTire(Double tire) {
        this.tire = tire;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", tire=" + tire +
                ", price=" + price +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}
