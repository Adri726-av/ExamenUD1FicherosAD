package controller;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.Mazmorra;
import service.IZeldaService;
import service.ZeldaService;

public class GestionaPartida {
	private static final Logger logger = LogManager.getLogger(GestionaPartida.class);
	public static void main(String[] args) {
		IZeldaService servicio = new ZeldaService("src/main/resources/ocarina.json");
//		Set<Mazmorra> lista = servicio.findAll();
//		no se porque no funcina el logger.info
//		logger.info(lista);
//		System.out.println(lista.size());
		List<Mazmorra> lista = servicio.findAll();
		servicio.completarMazmorra("Templo del Fuego", 48.3);
		servicio.completarMazmorra("Gran Árbol Deku", 15.0);
		servicio.completarMazmorra("Caverna de los Dodongos", 40.0);
		servicio.completarMazmorra("Templo de la Luz", 32.5);
		lista = servicio.findAll();
		servicio.exportarCSV(lista, "src/main/resources/exportaciones/ocarina.csv");
	}
}
