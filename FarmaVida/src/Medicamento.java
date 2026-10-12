import java.math.BigDecimal;

public class Medicamento {

    private String codigo;
    private String nombre;
    private Categoria categoria;
    private int stock;
    private BigDecimal precio;

	public Medicamento(String codigo, String nombre, Categoria categoria, int stock, BigDecimal precio) {
		if (codigo == null || nombre == null || categoria == null || precio == null) {
			throw new IllegalArgumentException("Ningún campo puede ser null.");
		}
		this.codigo = codigo;
		this.nombre = nombre;
		this.categoria = categoria;
		if (stock < 0) {
			throw new IllegalArgumentException("No se permite stocks negativos.");
		}
		this.stock = stock;
		if (precio.signum() < 0) {
			throw new IllegalArgumentException("No se permite precios negativos.");
		}
		this.precio = precio;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public int getStock() {
		return stock;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
    
}