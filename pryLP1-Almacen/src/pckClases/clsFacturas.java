
package pckClases;


public class clsFacturas {
    
    private String num_factura;
    private String fecha;
    private String codigo_cliente;
    private double subtotal;
    private static double total;
    private double igv;

    public String getNum_factura() {
        return num_factura;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public static double getTotal() {
        return total;
    }

    public double getIgv() {
        return igv;
    }

    public void setNum_factura(String num_factura) {
        this.num_factura = num_factura;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public static void setTotal(double total) {
        clsFacturas.total = total;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }
   
    
    
}
