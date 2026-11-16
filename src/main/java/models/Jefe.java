package models;

public class Jefe {
	private String nombre;
	private boolean contenedorCorazon;
	public Jefe(String nombre, boolean contenedorCorazon) {
		super();
		this.nombre = nombre;
		this.contenedorCorazon = contenedorCorazon;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isContenedorCorazon() {
		return contenedorCorazon;
	}
	public void setContenedorCorazon(boolean contenedorCorazon) {
		this.contenedorCorazon = contenedorCorazon;
	}
	@Override
	public String toString() {
		return "Jefe [nombre=" + nombre + "]";
	}
}
