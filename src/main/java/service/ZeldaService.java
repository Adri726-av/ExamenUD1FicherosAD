package service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.GestionaPartida;
import exceptions.ZeldaException;
import models.Mazmorra;
import repository.IRepoZelda;
import repository.RepoZelda;

public class ZeldaService implements IZeldaService{
	private static final Logger logger = LogManager.getLogger(ZeldaService.class);
	
	private List<Mazmorra> mazmorras;
	IRepoZelda repositorio;
	
	public ZeldaService(String ruta) {
		super();
		this.repositorio = new RepoZelda(ruta);
	}
	
	@Override
	public List<Mazmorra> findAll() {
		List<Mazmorra> mazmorras = null;
		mazmorras = repositorio.findAll();
		this.mazmorras = mazmorras;
		return this.mazmorras;
	}

	@Override
	public void completarMazmorra(String nombre, double tiempo){
		try {
			repositorio.completarMazmorra(nombre, tiempo);
		} catch (ZeldaException e) {
			logger.info(e.getMessage());;
		}
	}

	@Override
	public void exportarCSV(List<Mazmorra> mazmorras, String nombreFichero) {
		try {
			repositorio.exportarCSV(mazmorras, nombreFichero);
		} catch (ZeldaException e) {
			logger.error(e.getMessage());;
		}
	}
}
