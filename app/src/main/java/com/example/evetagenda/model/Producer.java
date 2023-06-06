package com.example.evetagenda.model;

public class Producer{
    public int prodID;
    public String prodFLname;
    public String prodPhone;
    public String prodEmail;
    public String prodCodeEktrofis;
    public String prodAFM;
    public String prodDOY;
    public String prodArea;
    public int prodNumAnimals;
    public String prodTypeAnimals;
    public String prodFiliAnimals;
    public String registerDate;
    public int uid;

    public Producer(int prodID, String prodFLname, String prodPhone, String prodEmail, String prodCodeEktrofis, String prodAFM, String prodDOY, String prodArea, int prodNumAnimals, String prodTypeAnimals, String prodFiliAnimals, String registerDate, int uid) {
        this.prodID = prodID;
        this.prodFLname = prodFLname;
        this.prodPhone = prodPhone;
        this.prodEmail = prodEmail;
        this.prodCodeEktrofis = prodCodeEktrofis;
        this.prodAFM = prodAFM;
        this.prodDOY = prodDOY;
        this.prodArea = prodArea;
        this.prodNumAnimals = prodNumAnimals;
        this.prodTypeAnimals = prodTypeAnimals;
        this.prodFiliAnimals = prodFiliAnimals;
        this.registerDate = registerDate;
        this.uid = uid;
    }

    public Producer() {
    }

    public int getProdID() {
        return prodID;
    }

    public String getProdFLname() {
        return prodFLname;
    }

    public String getProdPhone() {
        return prodPhone;
    }

    public String getProdEmail() {
        return prodEmail;
    }

    public String getProdCodeEktrofis() {
        return prodCodeEktrofis;
    }

    public String getProdAFM() {
        return prodAFM;
    }

    public String getProdDOY() {
        return prodDOY;
    }

    public String getProdArea() {
        return prodArea;
    }

    public int getProdNumAnimals() {
        return prodNumAnimals;
    }

    public String getProdTypeAnimals() {
        return prodTypeAnimals;
    }

    public String getProdFiliAnimals() {
        return prodFiliAnimals;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public int getUid() {
        return uid;
    }
}



