package dto;
/**
 * Clase DTO que representa a la entidad libro, contiene atributos, constructor,
 * propiedades y los getter & setter.
 */
public class LibroDto {

	//Atributos
	private long idLibro;
	private String titulo;
	private String autor;
	private String isbn;
	private int edicion;
	
	//Constructores
	public LibroDto(long idLibro, String titulo, String autor, String isbn, int edicion) {
		this.idLibro = idLibro;
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.edicion = edicion;
	}
	
	public LibroDto() {
		super();
	}
	
	public LibroDto(String titulo, String autor, String isbn, int edicion) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
		this.edicion = edicion;
	}
	
	//Getters y setters
	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getEdicion() {
		return edicion;
	}

	public void setEdicion(int edicion) {
		this.edicion = edicion;
	}
	
	//Métodos
	@Override
	public String toString() {
		return "\n--- Datos del libro con id "+idLibro+" ---\nidLibro: " + idLibro + "\nTitulo: " + titulo + "\nAutor: " + autor + "\nISBN: " + isbn
				+ "\nEdición: " + edicion;
	}
}
