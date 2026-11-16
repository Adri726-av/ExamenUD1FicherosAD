package service;

import java.util.List;
import java.util.Set;

import exceptions.ZeldaException;
import models.Mazmorra;

public interface IZeldaService {
	public List<Mazmorra> findAll();
	public void completarMazmorra(String nombre, double tiempo);
	public void exportarCSV(List<Mazmorra> mazmorras, String nombreFichero);
}
