package com.example.semproj.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Car {
    public StringProperty idProducts;
    public StringProperty CarName;
    public StringProperty Segment;
    public StringProperty Cost;
    public StringProperty Count;
    public StringProperty Grade;

    public Car(String idProducts, String CarName, String Segment, String Cost, String Count, String Grade){
       this.idProducts = new SimpleStringProperty(idProducts);
       this.CarName = new SimpleStringProperty(CarName);
       this.Count = new SimpleStringProperty(Count);
       this.Cost = new SimpleStringProperty(Cost);
       this.Segment = new SimpleStringProperty(Segment);
       this.Grade = new SimpleStringProperty(Grade);
    }

    public String getIdProducts() {
        return idProducts.get();
    }

    public StringProperty idProductsProperty() {
        return idProducts;
    }

    public void setIdProducts(String idProducts) {
        this.idProducts.set(idProducts);
    }

    public String getCarName() {
        return CarName.get();
    }

    public StringProperty carNameProperty() {
        return CarName;
    }

    public void setCarName(String carName) {
        this.CarName.set(carName);
    }

    public String getSegment() {
        return Segment.get();
    }

    public StringProperty segmentProperty() {
        return Segment;
    }

    public void setSegment(String segment) {
        this.Segment.set(segment);
    }

    public String getCost() {
        return Cost.get();
    }

    public StringProperty costProperty() {
        return Cost;
    }

    public void setCost(String cost) {
        this.Cost.set(cost);
    }

    public String getCount() {
        return Count.get();
    }

    public StringProperty countProperty() {
        return Count;
    }

    public void setCount(String count) {
        this.Count.set(count);
    }

    public String getGrade() {
        return Grade.get();
    }

    public StringProperty gradeProperty() {
        return Grade;
    }

    public void setGrade(String grade) {
        this.Grade.set(grade);
    }
}
