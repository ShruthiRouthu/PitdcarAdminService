
package edu.wctc.srt.pitdcaradminservice.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Shruthi Routhu
 */
public class Part {
    
    private int part_id;
    private Date eff_date;
    private String part_name;
    private String part_description;
    private String manufacturer;
    private String part_image;
    private double salePrice;
    private int qty;
    
    //CONSTRUCTOR
    public Part() {
    }
    
    public Part(int part_id, Date eff_date) {
        this.part_id = part_id;
        this.eff_date = eff_date;
    }

    public Part(int part_id, Date eff_date, String part_name, String part_description, String manufacturer, String part_image, double salePrice, int qty) {
        this.part_id = part_id;
        this.eff_date = eff_date;
        this.part_name = part_name;
        this.part_description = part_description;
        this.manufacturer = manufacturer;
        this.part_image = part_image;
        this.salePrice = salePrice;
        this.qty = qty;
    }
    
    public Part(int part_id, String part_name, String part_description, String manufacturer, String part_image, double salePrice, int qty) {
        this.part_id = part_id;
        this.eff_date = eff_date;
        this.part_name = part_name;
        this.part_description = part_description;
        this.manufacturer = manufacturer;
        this.part_image = part_image;
        this.salePrice = salePrice;
        this.qty = qty;
    }

    //SETTERS
    public final void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public final void setPart_description(String part_description) {
        this.part_description = part_description;
    }

    public final void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public final void setPart_image(String part_image) {
        this.part_image = part_image;
    }

    public final void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public final void setQty(int qty) {
        this.qty = qty;
    }

    public  final void setPart_id(int part_id) {
        this.part_id = part_id;
    }

    public final void setEff_date(Date eff_date) {
        this.eff_date = eff_date;
    }
    
    //GETTERS

    public final int getPart_id() {
        return part_id;
    }

    public final Date getEff_date() {
        return eff_date;
    }

    public final String getPart_name() {
        return part_name;
    }

    public final String getPart_description() {
        return part_description;
    }

    public final String getManufacturer() {
        return manufacturer;
    }

    public final String getPart_image() {
        return part_image;
    }

    public final double getSalePrice() {
        return salePrice;
    }

    public final int getQty() {
        return qty;
    }
    
    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.part_id);
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Part other = (Part) obj;
        if (!Objects.equals(this.part_id, other.part_id)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "Part{" + "part_id=" + part_id + ",   eff_date=" + eff_date 
                +  ", part_image=" + this.part_image+ ",   salePrice=" + salePrice  + ",  qty=" + qty 
                +  ", part_name=" + part_name + ",       manufacturer=" + manufacturer
                +  ",     part_description=" + part_description  + '}';
    }
 
//UNIT TEST    
//    public static void main(String[] args) {
//        Date date = new Date();
//        Part p = new Part(1,date,"sonu","soooooonu description","ratna","blah",5000,1);
//        System.out.println(p);
//
//    }
    
}
