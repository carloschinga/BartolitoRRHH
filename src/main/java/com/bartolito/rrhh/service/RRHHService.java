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
            //return Collections.singletonMap("mensaje", "No existe turno con código " + codiTurn);
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

    /*====================== SECCIÓN DE LA GESTIÓN HORARIO ======================*/

    public List<Map<String, Object>> obtenerHorario() {
        return repository.obtenerHorario();
    }

    public Map<String, Object> seleccionarHorarioPorCodigo(int codiHora) {
        List<Map<String, Object>> results = repository.seleccionarHorarioPorCodigo(codiHora);

        if (results.isEmpty()) {
            throw new NoSuchElementException("El horario con código " + codiHora + " no fue encontrado.");
            //return Collections.singletonMap("mensaje", "No existe turno con código " + codiTurn);
        }
        return results.get(0);
    }

    public int agregarHorario(String nombHora, String cortHora, String toleMarc, String toleDesc, Integer usuacrea) {
        return repository.agregarHorario(nombHora, cortHora, toleMarc, toleDesc, usuacrea);
    }

    public void editarHorario(Integer codiHora, String nombHora, String cortHora, String toleMarc, String toleDesc, Integer usuamodi) {

        int filasAfectadas = repository.editarHorario(codiHora, nombHora, cortHora, toleMarc, toleDesc, usuamodi);

        if (filasAfectadas == 0) {
            throw new NoSuchElementException("No se pudo editar el horario. El código " + codiHora + " no existe.");
        }
    }

    /*====================== SECCIÓN DE GESTION DETALLE HORARIO ======================*/

    public List<Map<String, Object>> obtenerHorarioDetalle() {
        return repository.obtenerHorarioDetalle();
    }

    public List<Map<String, Object>> seleccionarHorarioDetallePorCodigo(Integer codiHora) {

        List<Map<String, Object>> results = repository.seleccionarHorarioDetallePorCodigo(codiHora);

        return results;
    }

    public int agregarHorarioDetalle(Integer codiHora,
                                     Integer codiTurn,
                                     Integer anulTurn,
                                     Integer usuacrea) {
        return repository.agregarHorarioDetalle(codiHora, codiTurn, anulTurn, usuacrea);
    }

    public void editarHorarioDetalle(Integer codiHoraDeta, Integer codiHora, Integer codiTurn, Integer anulTurn, Integer usuamodi) {

        int filasAfectadas = repository.editarHorarioDetalle(codiHoraDeta, codiHora, codiTurn, anulTurn, usuamodi);

        if (filasAfectadas == 0) {
            throw new NoSuchElementException("No se pudo editar el detalle. El código " + codiHoraDeta + " no existe.");
        }
    }

    public void eliminarHorarioDetalle(Integer codiHoraDeta, Integer codiHora, Integer codiTurn, Integer usuamodi) {

        int filasAfectadas = repository.editarHorarioDetalle(codiHoraDeta, codiHora, codiTurn, 1, usuamodi);

        if (filasAfectadas == 0) {
            throw new NoSuchElementException("No se pudo eliminar (anular) el detalle. El código " + codiHoraDeta + " no existe.");
        }
    }

    /*====================== SECCIÓN PROGRAMACIÓN MENSUAL ======================*/

    public List<Map<String, Object>> listarProgramacionMensual(String inicio, String fin) {
        return repository.listarProgramacionMensual(inicio, fin);
    }

    public List<Map<String, Object>> seleccionarProgramacionPorPersona(Integer codiPersona, String fechaInicio, String fechaFin) {
        return repository.seleccionarProgramacionPorPersona(codiPersona, fechaInicio, fechaFin);
    }

    public int agregarProgramacion(Integer codiHora, Integer codiPers, String fechProg) {
        return repository.agregarProgramacion(codiHora, codiPers, fechProg);
    }

    public List<Map<String, Object>> listarPersonal() {
        return repository.listarPersonal();
    }

    public Map<String, Object> seleccionarPersonal(Integer id) {
        List<Map<String, Object>> results = repository.seleccionarPersonal(id);

        if (results.isEmpty()) {
            throw new NoSuchElementException("El personal con ID " + id + " no fue encontrado.");
        }
        return results.get(0);
    }

    public void modificarProgramacion(Integer nuevoCodiHora, Integer codiPers, String fechProg, Integer anulPersHora) {

        // Recibimos el int directamente
        int filasAfectadas = repository.modificarProgramacion(nuevoCodiHora, codiPers, fechProg, anulPersHora);

        // Validamos si se actualizó algo
        if (filasAfectadas == 0) {
            throw new NoSuchElementException("No se pudo actualizar la programación. Verifique que la persona y la fecha existan.");
        }
    }

    public void eliminarProgramacion(Integer codiHora, Integer codiPers, String fechProg) {

        int filasAfectadas = repository.modificarProgramacion(codiHora, codiPers, fechProg, 1);

        if (filasAfectadas == 0) {
            throw new NoSuchElementException("No se encontró el registro para eliminar.");
        }
    }

    /*====================== SECCIÓN REPORTES DE ASISTENCIA ======================*/

    public List<Map<String, Object>> reporteAsistenciaMensual(String fechaInicio, String fechaFin, int codiServ) {
        return repository.reporteAsistenciaMensual(fechaInicio, fechaFin, codiServ);
    }

    public List<Map<String, Object>> reporteAsistenciaDiaria(String fecha, Integer idEmpleado) {
        // Si no envían ID, asumimos 0 (para que el SP traiga a todos)
        Integer idFinal = (idEmpleado != null) ? idEmpleado : 0;

        return repository.reporteAsistenciaDiaria(fecha, idFinal);
    }


    /*====================== SECCIÓN EMPRESA ======================*/

    public List<Map<String, Object>> obtenerEmpresas() {
        return repository.obtenerEmpresa();
    }

    public Map<String, Object> seleccionarEmpresaPorCodigo(int codiEmpr) {
        List<Map<String, Object>> results = repository.seleccionarEmpresa(codiEmpr);

        if (results.isEmpty()) {
            throw new NoSuchElementException("El Empresa con código " + codiEmpr + " no fue encontrado.");
            //return Collections.singletonMap("mensaje", "No existe turno con código " + codiTurn);
        }
        return results.get(0);
    }


    /*====================== SECCIÓN DEPARTAMENTO ======================*/

    public List<Map<String, Object>> obtenerDepartamentoPorEmpresa(int codiEmpr) {
        return repository.obtenerDepartamentoXEmpresa(codiEmpr);
    }

    public Map<String, Object> seleccionarDepartamentoPorCodigo(int codiDepar) {
        List<Map<String, Object>> results = repository.seleccionarEmpresa(codiDepar);

        if (results.isEmpty()) {
            throw new NoSuchElementException("El Departamento con código " + codiDepar + " no fue encontrado.");
            //return Collections.singletonMap("mensaje", "No existe turno con código " + codiTurn);
        }
        return results.get(0);
    }


    /*====================== SECCIÓN SERVICIOS ======================*/

    public List<Map<String, Object>> obtenerServiciosPorDepartamento(int codiServ) {
        return repository.obtenerServiciosXDepartamento(codiServ);
    }

    public Map<String, Object> seleccionarServiciosPorCodigo(int codiServ) {
        List<Map<String, Object>> results = repository.seleccionaServicioPorCodigo(codiServ);

        if (results.isEmpty()) {
            throw new NoSuchElementException("El Servicio con código " + codiServ + " no fue encontrado.");
            //return Collections.singletonMap("mensaje", "No existe turno con código " + codiTurn);
        }
        return results.get(0);
    }
}