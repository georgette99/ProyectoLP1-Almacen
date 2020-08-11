package pckClases;

public class clsInventario {
    private int Codigo_Inv;
    private String Stock;
    private String Descripcion;
    private String Precio;       
    private String Linea;       
    private String Unidad;

    public int getCodigo_Inv() {
        return Codigo_Inv;
    }

    public void setCodigo_Inv(int Codigo_Inv) {
        this.Codigo_Inv = Codigo_Inv;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String Stock) {
        this.Stock = Stock;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String Precio) {
        this.Precio = Precio;
    }

    public String getLinea() {
        return Linea;
    }

    public void setLinea(String Linea) {
        this.Linea = Linea;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String Unidad) {
        this.Unidad = Unidad;
    }
         
}
