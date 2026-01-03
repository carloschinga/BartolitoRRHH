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

	public List<Map<String, Object>> listarProgramacionMensual(String inicio, String fin, int codiServ) {

		List<Map<String, Object>> data = repository.listarProgramacionMensual(inicio, fin, codiServ);

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

	public int modificarProgramacion(Integer nuevoCodiHora, Integer codiPers, String fechProg, Integer codiServ) {
		return repository.modificarProgramacion(nuevoCodiHora, codiPers, fechProg, codiServ);

	}

	public int agregarProgramacion(Integer codiPers, String periodo, Integer codiServ) {
		return repository.agregarProgramacion(codiPers, periodo, codiServ);
	}

	public int eliminarProgramacion(Integer codiPers, String periodo, Integer codiServ) {
		return repository.eliminarProgramacion(codiPers, periodo, codiServ);
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

	public List<Map<String, Object>> reporteResumenDiarioServicio(String fechaInicio, String fechaFin,
			Integer codiServ) {
		return repository.reporteResumenDiarioServicio(fechaInicio, fechaFin, codiServ);
	}

	/* ====================== SECCIÓN EMPRESA ====================== */

	/*
	 * ========================= LISTAR =========================
	 */
	public List<Map<String, Object>> obtenerEmpresas() {
		return repository.obtenerEmpresa();
	}

	/*
	 * ========================= SELECCIONAR =========================
	 */
	public Map<String, Object> seleccionarEmpresaPorCodigo(int codiEmpr) {

		List<Map<String, Object>> results = repository.seleccionarEmpresa(codiEmpr);

		if (results.isEmpty()) {
			throw new NoSuchElementException("La Empresa con código " + codiEmpr + " no fue encontrada.");
		}
		return results.get(0);
	}

	/*
	 * ========================= AGREGAR =========================
	 */
	public int agregarEmpresa(String nombEmpr,

			String rangEntrPrev, String rangEntrPost, String rangSaliPrev, String rangSaliPost,

			String toleEntrPrev, String toleEntrPost, String toleSaliPrev, String toleSaliPost) {

		return repository.agregarEmpresa(nombEmpr,

				rangEntrPrev, rangEntrPost, rangSaliPrev, rangSaliPost,

				toleEntrPrev, toleEntrPost, toleSaliPrev, toleSaliPost);
	}

	/*
	 * ========================= MODIFICAR =========================
	 */
	public void modificarEmpresa(Integer codiEmpr, String nombEmpr,

			String rangEntrPrev, String rangEntrPost, String rangSaliPrev, String rangSaliPost,

			String toleEntrPrev, String toleEntrPost, String toleSaliPrev, String toleSaliPost) {

		int filas = repository.modificarEmpresa(codiEmpr, nombEmpr,

				rangEntrPrev, rangEntrPost, rangSaliPrev, rangSaliPost,

				toleEntrPrev, toleEntrPost, toleSaliPrev, toleSaliPost);

		if (filas == 0) {
			throw new NoSuchElementException("Empresa no encontrada");
		}
	}

	/* ====================== SECCIÓN DEPARTAMENTO ====================== */

	public List<Map<String, Object>> obtenerDepartamentoPorEmpresa(int codiEmpr) {
		return repository.obtenerDepartamentoXEmpresa(codiEmpr);
	}

	public Map<String, Object> seleccionarDepartamentoPorCodigo(int codiDepar) {
		List<Map<String, Object>> results = repository.seleccionarEmpresa(codiDepar);

		if (results.isEmpty()) {
			throw new NoSuchElementException("El Departamento con código " + codiDepar + " no fue encontrado.");
			// return Collections.singletonMap("mensaje", "No existe turno con código " +
			// codiTurn);
		}
		return results.get(0);
	}

	public int agregarDepartamento(String nombDepa, Integer codiEmpr) {
		return repository.agregarDepartamento(nombDepa, codiEmpr);
	}

	public void modificarDepartamento(Integer codiDepa, String nombDepa, Integer codiEmpr) {

		int filasAfectadas = repository.modificarDepartamento(codiDepa, nombDepa, codiEmpr);

		if (filasAfectadas == 0) {
			throw new NoSuchElementException("No se pudo editar el Servicio. El código " + codiDepa + " no existe.");
		}
	}

	/* ====================== SECCIÓN SERVICIOS ====================== */

	public List<Map<String, Object>> obtenerServiciosPorDepartamento(int codiServ) {
		return repository.obtenerServiciosXDepartamento(codiServ);
	}

	public Map<String, Object> seleccionarServiciosPorCodigo(int codiServ) {
		List<Map<String, Object>> results = repository.seleccionaServicioPorCodigo(codiServ);

		if (results.isEmpty()) {
			throw new NoSuchElementException("El Servicio con código " + codiServ + " no fue encontrado.");
			// return Collections.singletonMap("mensaje", "No existe turno con código " +
			// codiTurn);
		}
		return results.get(0);
	}

	public int agregarServicio(String nombServ, int codiDepa, int codiUsua) {
		return repository.agregarServicio(nombServ, codiDepa, codiUsua);
	}

	public void editarServicio(Integer codiServ, String nombServ, Integer codiDepa, Integer anulServ,
			Integer usuamodi) {

		int filasAfectadas = repository.editarServicio(codiServ, nombServ, codiDepa, anulServ, usuamodi);

		if (filasAfectadas == 0) {
			throw new NoSuchElementException("No se pudo editar el Servicio. El código " + codiServ + " no existe.");
		}
	}

	/* ====================== SECCIÓN CARGO ====================== */

	public List<Map<String, Object>> obtenerCargos(Integer codiEmpr) {
		return repository.obtenerCargos(codiEmpr);
	}

	public Map<String, Object> seleccionarCargosPorCodigo(Integer codiCarg) {
		List<Map<String, Object>> results = repository.seleccionaCargp(codiCarg);

		if (results.isEmpty()) {
			throw new NoSuchElementException("El Cargo con código " + codiCarg + " no fue encontrado.");
			// return Collections.singletonMap("mensaje", "No existe turno con código " +
			// codiTurn);
		}
		return results.get(0);
	}

	public int agregarCargo(String nombCarg, Integer codiEmpr) {
		return repository.agregarCargo(nombCarg, codiEmpr);
	}

	public void modificarCargo(Integer codiCarg, String nombCarg, Integer codiEmpr) {

		int filasAfectadas = repository.editarCargo(codiCarg, nombCarg, codiEmpr);

		if (filasAfectadas == 0) {
			throw new NoSuchElementException("No se pudo editar el Servicio. El código " + codiCarg + " no existe.");
		}
	}

	/* ====================== SECCIÓN Parametros ====================== */

	public List<Map<String, Object>> listarParametros(Integer codiEmpr) {
		return repository.listarParametros(codiEmpr);
	}

	public List<Map<String, Object>> seleccionarParametro(Integer codiPara, Integer codiEmpr) {
		return repository.seleccionarParametro(codiPara, codiEmpr);
	}

	public int modificarParametro(Integer codiPara, String nombPara, String valuPara, Integer codiEmpr) {
		return repository.modificarParametro(codiPara, nombPara, valuPara, codiEmpr);
	}

	public int toggleParametro(Integer codiPara, int codiEmpr, String valuPara) {
		return repository.toggleParametro(codiPara, codiEmpr, valuPara);
	}
	/* ====================== SECCIÓN CAP ====================== */

	public List<Map<String, Object>> obtenerCAPPorServicio(int codiServ) {
		return repository.obtenerCAPPorServicio(codiServ);
	}

	public Map<String, Object> seleccionarCAPPorPersona(int codiPers) {
		List<Map<String, Object>> results = repository.seleccionaCAPPorPersona(codiPers);

		if (results.isEmpty()) {
			throw new NoSuchElementException("El Cargo con código " + codiPers + " no fue encontrado.");
			// return Collections.singletonMap("mensaje", "No existe turno con código " +
			// codiTurn);
		}
		return results.get(0);
	}

	public int agregarCAP(int codiPers, int codiServ) {
		return repository.agregarCAP(codiPers, codiServ);
	}

	public int agregarCAPCargo(int codiServ, int codiCarg) {
		return repository.agregarCAPCargo(codiServ, codiCarg);
	}

	public void eliminarCAP(int codiPers) {

		int filasAfectadas = repository.eliminarCAP(codiPers);

		if (filasAfectadas == 0) {
			throw new NoSuchElementException("No se pudo editar el persona. El código " + codiPers + " no existe.");
		}
	}

	/* ====================== ASIGNAR CARGO ====================== */

	public List<Map<String, Object>> obtenerTrabajadoresconCargo() {
		return repository.obtenerTrabajadoresconCargo();
	}

	public Map<String, Object> seleccionarTrabajadoresconCargo(int codiPers) {
		List<Map<String, Object>> results = repository.seleccionarTrabajadoresconCargo(codiPers);

		if (results.isEmpty()) {
			throw new NoSuchElementException("La asignacion de Cargo con código " + codiPers + " no fue encontrado.");
			// return Collections.singletonMap("mensaje", "No existe turno con código " +
			// codiTurn);
		}
		return results.get(0);
	}

	public void asignarTrabajadoresconCargo(Integer codiPers, Integer codiCarg) {

		int filasAfectadas = repository.asignarTrabajadoresconCargo(codiPers, codiCarg);

		if (filasAfectadas == 0) {
			throw new NoSuchElementException("No se pudo la asignacion. El código " + codiPers + " no existe.");
		}
	}

}