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
			throw new IllegalArgumentException("No se permiten stocks negativos.");
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
		if (codigo == null) {
			throw new IllegalArgumentException("Ningún campo puede ser null.");
		}
		this.codigo = codigo;
	}

	public void setNombre(String nombre) {
		if (nombre == null) {
			throw new IllegalArgumentException("Ningún campo puede ser null.");
		}
		this.nombre = nombre;
	}

	public void setCategoria(Categoria categoria) {
		if (categoria == null) {
			throw new IllegalArgumentException("Ningún campo puede ser null.");
		}
		this.categoria = categoria;
	}

	public void setStock(int stock) {
		if (stock < 0) {
			throw new IllegalArgumentException("No se permite stocks negativos.");
		}
		this.stock = stock;
	}

	public void setPrecio(BigDecimal precio) {
		if (precio == null) {
			throw new IllegalArgumentException("Ningún campo puede ser null.");
		}
		if (precio.signum() < 0) {
			throw new IllegalArgumentException("No se permite precios negativos.");
		}
		this.precio = precio;
	}
    
}