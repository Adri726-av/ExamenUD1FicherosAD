package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import exceptions.ZeldaException;
import models.Mazmorra;

public interface IRepoZelda {
	public List<Mazmorra> findAll();
	public void completarMazmorra(String nombre, double tiempo) throws ZeldaException;
	public void save(Mazmorra mazmorra) throws ZeldaException;
	public void exportarCSV(List<Mazmorra> mazmorras, String nombreFichero) throws ZeldaException;
}
