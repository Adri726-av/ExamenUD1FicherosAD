package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import exceptions.ZeldaException;
import models.Mazmorra;
import service.ZeldaService;

public class RepoZelda implements IRepoZelda{
	
	private static final Logger logger = LogManager.getLogger(RepoZelda.class);
	
	private File archivo;
	private List<Mazmorra> mazmorras; 
	
	public RepoZelda(String ruta) {
		super();
		this.archivo = new File(ruta);
		this.mazmorras = new ArrayList<Mazmorra>(); // Eligo HashSet para que no haya repetidos por nombre
	}
	
	private List<Mazmorra> leerJson(){
		try {
			Gson gson = new Gson();
			FileReader fichero = new FileReader(this.archivo);
			Mazmorra[] lista = gson.fromJson(fichero, Mazmorra[].class);
			List<Mazmorra> mazmorras = new ArrayList<>();
			for (int i = 0; i < lista.length; i++) {
				if (lista[i].getNombre() == null || lista[i].getNombre().isBlank()) {
					//throw new ZeldaException("Dato en fichero no válido. No se ha podido cargar la mazmorra " + i + ": debido a que el nombre esta vacio.");
					// No se como hacer que esta excepcion no corte la aplicacion
					logger.error("Dato en fichero no válido. No se ha podido cargar la mazmorra " + i + ": debido a que el nombre esta vacio");
				} else if (lista[i].getEpoca() == null || lista[i].getEpoca().equals("ADULTO") || lista[i].getEpoca().equals("AMBAS") || lista[i].getEpoca().equals("NINO")) {
					//throw new ZeldaException("Dato en fichero no válido. No se ha podido cargar la mazmorra " + i + ": debido a que el epoca debe ser ADULTO, AMBAS O NINO.");
					logger.error("Dato en fichero no válido. No se ha podido cargar la mazmorra " + i + ": debido a que el epoca debe ser ADULTO, AMBAS O NINO.");
				}else {
					mazmorras.add(lista[i]);
				}
			}
			this.mazmorras = mazmorras;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this.mazmorras;
	}
	
	private void cargarSiHaceFalta(){
		if (this.mazmorras == null || this.mazmorras.isEmpty() || this.mazmorras.size() == 0) {
			this.mazmorras = leerJson();
		}
	}
	
	@Override
	public List<Mazmorra> findAll() {
		cargarSiHaceFalta();
		return this.mazmorras;
	}

	@Override
	public void completarMazmorra(String nombre, double tiempo) throws ZeldaException {
		Mazmorra mazmorra = null;
		for (Mazmorra m : this.mazmorras) {
			if (m.getNombre().equals(nombre)) {
//				System.out.println("Entro en " + m.getNombre());
				if (m.getMejorTiempo() > -1) {
//					System.out.println("Entro tiempo");
					if (!m.isCompletada()) {
//						System.out.println("Completao a true");
						m.setCompletada(true);
						if (tiempo < m.getMejorTiempo()) {
							m.setMejorTiempo(tiempo);
							save(m);
						}
					} else {
//						System.out.println("Completao esta en true");
						if (tiempo < m.getMejorTiempo()) {
//							System.out.println("el tiempo es menor");
							m.setMejorTiempo(tiempo);
							save(m);
						}
					}
				}
			} 
		}
		if (mazmorra == null) {
			logger.error("La mazmorra no existe");
		}
	}
	
	private void escribirJson(List<Mazmorra> mazmorras) throws ZeldaException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(mazmorras);
		FileWriter fichero = null;
		try {
			fichero = new FileWriter(this.archivo);
			fichero.write(json);
		} catch (IOException e) {
			throw new ZeldaException("No se ha podido escribir mazmorras");
		}finally {
			if (fichero != null) {
				try {
					fichero.close();
				} catch (IOException e) {
					throw new ZeldaException("No se ha podido cerrar al escribir mazmorras");
				}
			}
		}
	}
	
	public int buscarPosicion (Mazmorra mazmorra) {
		int pos = -1;
		for (int i = 0; i < mazmorras.size(); i++) {
			if (mazmorras.get(i) == mazmorra) {
				pos = i;
			}
		}
		return pos;
	}

	@Override
	public void save(Mazmorra mazmorra) throws ZeldaException {
		cargarSiHaceFalta();
		List<Mazmorra> copia = this.mazmorras;
		int pos = buscarPosicion(mazmorra);
		if (pos == -1) {
			copia.add(mazmorra);
		}
		else {
			copia.set(pos, mazmorra);
		}
		escribirJson(copia);
		this.mazmorras = copia;
	}
	
	public void exportarCSV(List<Mazmorra> mazmorras, String nombreFichero) throws ZeldaException {
		try {
			FileWriter fw = new FileWriter(nombreFichero);
			PrintWriter pw = new PrintWriter(fw);
			for (Mazmorra v: mazmorras) {
				pw.println(v.getNombre() + ";" + v.getEpoca() + ";" + v.getJefe().getNombre() + ";" + v.getMejorTiempo());
			}
			pw.close();
		} catch (IOException e) {
			throw new ZeldaException("No se ha podido encontrar el fichero");
		}
	}
}
