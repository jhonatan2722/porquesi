package models;

import java.math.BigDecimal;

public class Amigurumi {

    private int id;
    private String descripcion ;
    private BigDecimal precio;

    public Amigurumi(){
    }

    public Amigurumi(String descripcion, BigDecimal precio){
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Amigurumi(int id, String descripcion, BigDecimal precio){
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
