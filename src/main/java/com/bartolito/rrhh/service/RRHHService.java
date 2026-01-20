package com.bartolito.rrhh.service;

import com.bartolito.rrhh.repository.RRHHRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class RRHHService {

	private final RRHHRepository repository;

	public RRHHService(RRHHRepository repository) {
		this.repository = repository;
	}

	public List<Map<String, Object>> obtenerdTurnos() {
		return repository.obtenerTurnos();
	}

	public Map<String, Object> seleccionarTurnoPorCodigo(int codiTurn) {
		List<Map<String, Object>> results = repository.seleccionarTurnoPorCodigo(codiTurn);

		if (results.isEmpty()) {
			throw new NoSuchElementException("El turno con código " + codiTurn + " no fue encontrado.");
			// return Collections.singletonMap("mensaje", "No existe turno con código " +
			// codiTurn);
		}
		return results.get(0);
	}

	public int agregarTurno(String nombTurn, String ingrTurn, String saldTurn) {
		return repository.agregarTurno(nombTurn, ingrTurn, saldTurn);
	}

	public void editarTurno(int codiTurn, String nombTurn, String ingrTurn, String saldTurn) {

		int filasAfectadas = repository.editarTurno(codiTurn, nombTurn, ingrTurn, saldTurn);

		if (filasAfectadas == 0) {
			throw new NoSuchElementException("No se pudo editar el turno. El código " + codiTurn + " no existe.");
		}
	}

	/*
	 * ====================== SECCIÓN DE LA GESTIÓN HORARIO ======================
	 */

	public List<Map<String, Object>> obtenerHorarioPorEmpresa(int codiEmpr) {
		return repository.obtenerHorarioPorEmpresa(codiEmpr);
	}

	public List<Map<String, Object>> obtenerHorarioPorEmpresaTurnos(int codiEmpr) {
		return repository.obtenerHorarioPorEmpresaTurnos(codiEmpr);
	}

	public Map<String, Object> seleccionarHorarioPorCodigo(int codiHora) {
		List<Map<String, Object>> results = repository.seleccionarHorarioPorCodigo(codiHora);

		if (results.isEmpty()) {
			throw new NoSuchElementException("El horario con código " + codiHora + " no fue encontrado.");
			// return Collections.singletonMap("mensaje", "No existe turno con código " +
			// codiTurn);
		}
		return results.get(0);
	}

	public int agregarHorario(String nombHora, String cortHora, Integer usuacrea, Integer codiEmpr) {
		return repository.agregarHorario(nombHora, cortHora, usuacrea, codiEmpr);
	}

	public int editarHorario(Integer codiHora, String nombHora, String cortHora, Integer usuamodi, Integer codiEmpr,
			Integer anulHora) {

		return repository.editarHorario(codiHora, nombHora, cortHora, usuamodi, codiEmpr, anulHora);

	}

	/*
	 * ====================== SECCIÓN DE GESTION DETALLE HORARIO
	 * ======================
	 */

	public List<Map<String, Object>> obtenerHorarioDetalle() {
		return repository.obtenerHorarioDetalle();
	}

	public List<Map<String, Object>> seleccionarHorarioDetallePorCodigo(Integer codiHora) {

		List<Map<String, Object>> results = repository.seleccionarHorarioDetallePorCodigo(codiHora);

		return results;
	}

	public int agregarHorarioDetalle(Integer codiHora, Integer codiTurn, Integer anulTurn, Integer usuacrea) {
		return repository.agregarHorarioDetalle(codiHora, codiTurn, anulTurn, usuacrea);
	}

	public void editarHorarioDetalle(Integer codiHoraDeta, Integer codiHora, Integer codiTurn, Integer anulTurn,
			Integer usuamodi) {

		int filasAfectadas = repository.editarHorarioDetalle(codiHoraDeta, codiHora, codiTurn, anulTurn, usuamodi);

		if (filasAfectadas == 0) {
			throw new NoSuchElementException("No se pudo editar el detalle. El código " + codiHoraDeta + " no existe.");
		}
	}

	public void eliminarHorarioDetalle(Integer codiHoraDeta, Integer codiHora, Integer codiTurn, Integer usuamodi) {

		int filasAfectadas = repository.editarHorarioDetalle(codiHoraDeta, codiHora, codiTurn, 1, usuamodi);

		if (filasAfectadas == 0) {
			throw new NoSuchElementException(
					"No se pudo eliminar (anular) el detalle. El código " + codiHoraDeta + " no existe.");
		}
	}

	/* ====================== SECCIÓN PROGRAMACIÓN MENSUAL ====================== */

	public List<Map<String, Object>> listarProgramacionMensual(String inicio, String fin, int codiGrup) {

		List<Map<String, Object>> data = repository.listarProgramacionMensual(inicio, fin, codiGrup);

		if (data == null || data.isEmpty()) {
			return List.of();
		}

		if (data.size() == 1 && data.get(0).containsKey("Mensaje")) {
			return List.of();
		}

		return data;
	}

	public List<Map<String, Object>> seleccionarProgramacionPorPersona(Integer codiPersona, String fechaInicio,
			String fechaFin) {
		return repository.seleccionarProgramacionPorPersona(codiPersona, fechaInicio, fechaFin);
	}

	public int modificarProgramacion(Integer nuevoCodiHora, Integer codiPers, String fechProg, Integer codiGrup) {
		return repository.modificarProgramacion(nuevoCodiHora, codiPers, fechProg, codiGrup);

	}

	public int agregarProgramacion(Integer codiPers, String periodo, Integer codiGrup, Integer codiServ) {
		return repository.agregarProgramacion(codiPers, periodo, codiGrup, codiServ);
	}

	public int eliminarProgramacion(Integer codiPers, String periodo, Integer codiGrup) {
		return repository.eliminarProgramacion(codiPers, periodo, codiGrup);
	}
	
	public int eliminarProgramacionSemana(Integer codiPers, Integer codiGrup, Integer codiHora, String fechProg) {
		return repository.eliminarProgramacionSemana(codiPers, codiGrup, codiHora, fechProg);
	}

	public List<Map<String, Object>> listarPersonal() {
		return repository.listarPersonal();
	}

	public List<Map<String, Object>> listarPersonalPorServicio(int codiServ) {
		return repository.listarPersonalPorServicio(codiServ);
	}

	public Map<String, Object> seleccionarPersonal(Integer id) {
		List<Map<String, Object>> results = repository.seleccionarPersonal(id);

		if (results.isEmpty()) {
			throw new NoSuchElementException("El personal con ID " + id + " no fue encontrado.");
		}
		return results.get(0);
	}

    public List<Map<String, Object>> listarPersonarPorMesYDepartamento(
            Integer codiDepa,
            String codiMes) {

        List<Map<String, Object>> result =
                repository.listarPersonarPorMesYDepartamento(codiDepa, codiMes);

        if (result == null || result.isEmpty()) {
            // Mantengo tu estilo: no lanzar excepción, solo lista vacía
            return result;
        }

        return result;
    }
	public List<Map<String, Object>> listarCabecera(String codiMes, Boolean soloActivos) {

		List<Map<String, Object>> data = repository.listarCabecera(codiMes, soloActivos);

		if (data == null || data.isEmpty()) {
			return List.of();
		}

		if (data.size() == 1 && data.get(0).containsKey("Mensaje")) {
			return List.of();
		}

		return data;
	}

	public List<Map<String, Object>> listarServiciosPorGrupo(Integer codiGrup) {

		List<Map<String, Object>> data = repository.listarServiciosPorGrupo(codiGrup);

		if (data == null || data.isEmpty()) {
			return List.of();
		}

		if (data.size() == 1 && data.get(0).containsKey("Mensaje")) {
			return List.of();
		}

		return data;
	}

	public int agregarCabecera(String codiMes, String listaServ, Integer usuaCrea) {
		return repository.agregarCabecera(codiMes, listaServ, usuaCrea);
	}

	public int eliminarCabecera(Integer codiProg, Integer usuaModi) {
		return repository.eliminarCabecera(codiProg, usuaModi);
	}

	/*
	 * ====================== SECCIÓN REPORTES DE ASISTENCIA ======================
	 */

	public List<Map<String, Object>> reporteAsistenciaMensual(String fechaInicio, String fechaFin, int codiServ) {
		return repository.reporteAsistenciaMensual(fechaInicio, fechaFin, codiServ);
	}

	public List<Map<String, Object>> reporteAsistenciaDiaria(String fecha, Integer idEmpleado) {
		// Si no envían ID, asumimos 0 (para que el SP traiga a todos)
		Integer idFinal = (idEmpleado != null) ? idEmpleado : 0;

		return repository.reporteAsistenciaDiaria(fecha, idFinal);
	}

	public List<Map<String, Object>> reporteMarcacionesDiaria(String fecha, Integer idEmpleado) {
		return repository.reporteMarcacionesDiaria(fecha, idEmpleado);
	}

	public List<Map<String, Object>> reporteResumenDiarioServicio(String fecha,
			Integer codiServ) {
		return repository.reporteResumenDiarioServicio(fecha,  codiServ);
	}



}