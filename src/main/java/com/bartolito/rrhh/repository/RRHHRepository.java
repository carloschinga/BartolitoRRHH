package com.bartolito.rrhh.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RRHHRepository {

    @Autowired
    @Qualifier("sigoldJdbcTemplate")
    private JdbcTemplate sigoldJdbc;
    private String string;


    /*====================== SECCIÓN DE LA GESTIÓN TURNOS ======================*/

    public List<Map<String, Object>> obtenerTurnos() {
        String sql = "EXEC sp_bart_rrhh_asis_turno_listar";
        return sigoldJdbc.queryForList(sql);
    }

    public List<Map<String, Object>> seleccionarTurnoPorCodigo(int codiTurn) {
        String sql = "EXEC sp_bart_rrhh_asis_turno_seleccionar ?";

        return sigoldJdbc.queryForList(sql, codiTurn);
    }

    public int agregarTurno(String nombTurn, String ingrTurn, String saldTurn) {
        String sql = "EXEC sp_bart_rrhh_asis_turno_agregar ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, nombTurn, ingrTurn, saldTurn);
    }

    public int editarTurno(int codiTurn, String nombTurn, String ingrTurn, String saldTurn) {
        String sql = "EXEC sp_bart_rrhh_asis_turno_editar ?, ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiTurn, nombTurn, ingrTurn, saldTurn);
    }

    /*====================== SECCIÓN DE LA GESTIÓN HORARIO ======================*/

    public List<Map<String, Object>> obtenerHorario() {
        String sql = "EXEC sp_bart_rrhh_asis_horario_listar";
        return sigoldJdbc.queryForList(sql);
    }

    public List<Map<String, Object>> seleccionarHorarioPorCodigo(int codiHora) {
        String sql = "EXEC sp_bart_rrhh_asis_horario_seleccionar ?";

        return sigoldJdbc.queryForList(sql, codiHora);
    }

    public int agregarHorario(String nombHora, String cortHora, String toleMarc, String toleDesc, Integer usuacrea) {
        String sql = "EXEC sp_bart_rrhh_asis_horario_agregar ?, ?, ?, ?, ?";
        return sigoldJdbc.queryForObject(sql, Integer.class, nombHora, cortHora, toleMarc, toleDesc, usuacrea);
    }

    public int editarHorario(Integer codiHora, String nombHora, String cortHora, String toleMarc, String toleDesc, Integer usuamodi) {
        String sql = "EXEC sp_bart_rrhh_asis_horario_editar ?, ?, ?, ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiHora, nombHora, cortHora, toleMarc, toleDesc, usuamodi);
    }

    /*====================== SECCIÓN DE GESTION DETALLE HORARIO ======================*/

    public List<Map<String, Object>> obtenerHorarioDetalle() {
        String sql = "SELECT * FROM view_bart_rrhh_asis_horario_detalle";
        return sigoldJdbc.queryForList(sql);
    }

    public List<Map<String, Object>> seleccionarHorarioDetallePorCodigo(Integer codiHora) {
        String sql = "SELECT * FROM view_bart_rrhh_asis_horario_detalle WHERE codiHora = ? AND anulTurn = 0";

        return sigoldJdbc.queryForList(sql, codiHora);
    }

    public int agregarHorarioDetalle(Integer codiHora, Integer codiTurn, Integer anulTurn, Integer usuacrea) {
        String sql = "EXEC sp_bart_rrhh_horario_detalle_agregar ?, ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiHora, codiTurn, anulTurn, usuacrea);
    }

    public int editarHorarioDetalle(Integer codiHoraDeta,
                                    Integer codiHora,
                                    Integer codiTurn,
                                    Integer anulTurn,
                                    Integer usuamodi) {
        String sql = "EXEC sp_bart_rrhh_horario_detalle_editar ?, ?, ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiHoraDeta, codiHora, codiTurn, anulTurn, usuamodi);
    }


    /*====================== SECCIÓN PROGRAMACIÓN MENSUAL ======================*/

    public List<Map<String, Object>> listarProgramacionMensual(String fechaInicio, String fechaFin) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion ?, ?";
        return sigoldJdbc.queryForList(sql, fechaInicio, fechaFin);
    }

    public List<Map<String, Object>> seleccionarProgramacionPorPersona(Integer codiPersona, String fechaInicio, String fechaFin) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion_seleccionar ?, ?, ?";
        return sigoldJdbc.queryForList(sql, codiPersona, fechaInicio, fechaFin);
    }

    public int agregarProgramacion(Integer codiHora, Integer codiPers, String fechProg) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion_agregar ?, ?, ?";
        return sigoldJdbc.queryForObject(sql, Integer.class, codiHora, codiPers, fechProg);
    }

    // 1. LISTAR TODOS: sp_bart_rrhh_horario_personal_listar
    public List<Map<String, Object>> listarPersonal() {
        String sql = "EXEC sp_bart_rrhh_horario_personal_listar";
        return sigoldJdbc.queryForList(sql);
    }

    // 2. SELECCIONAR UNO: sp_bart_rrhh_horario_personal_seleccionar
    public List<Map<String, Object>> seleccionarPersonal(Integer id) {
        String sql = "EXEC sp_bart_rrhh_horario_personal_seleccionar ?";
        return sigoldJdbc.queryForList(sql, id);
    }

    public int modificarProgramacion(Integer nuevoCodiHora, Integer codiPers, String fechProg, Integer anulPersHora) {
        // Agregamos el cuarto parámetro al SQL
        String sql = "EXEC sp_bart_rrhh_horario_programacion_modificar ?, ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, nuevoCodiHora, codiPers, fechProg, anulPersHora);
    }

    /*====================== SECCIÓN REPORTES DE ASISTENCIA ======================*/

    // 1. REPORTE MENSUAL (Columnas dinámicas)
    public List<Map<String, Object>> reporteAsistenciaMensual(String fechaInicio, String fechaFin, int codiServ) {
        // EXEC sp_bart_rrhh_asis_asistencia_mensual '2025-12-01','2025-12-31'
        String sql = "EXEC sp_bart_rrhh_asis_asistencia_mensual ?, ?, ?";

        // JdbcTemplate mapeará las columnas de fechas (2025-12-01, etc.) automáticamente al Map
        return sigoldJdbc.queryForList(sql, fechaInicio, fechaFin, codiServ);
    }

    // 2. REPORTE DIARIO (Puede ser un empleado o todos)
    public List<Map<String, Object>> reporteAsistenciaDiaria(String fecha, Integer idEmpleado) {
        // EXEC sp_bart_rrhh_asis_asistencia_diaria '2025-12-01', 123
        String sql = "EXEC sp_bart_rrhh_asis_asistencia_diaria ?, ?";

        return sigoldJdbc.queryForList(sql, fecha, idEmpleado);
    }

    /*====================== SECCIÓN EMPRESA ======================*/

    public List<Map<String, Object>> obtenerEmpresa() {
        String sql = "EXEC sp_bart_rrhh_empresa_listar";
        return sigoldJdbc.queryForList(sql);
    }

    public List<Map<String, Object>> seleccionarEmpresa(int codiEmpr) {
        String sql = "EXEC [sp_bart_rrhh_empresa_seleccionar] ?";

        return sigoldJdbc.queryForList(sql, codiEmpr);
    }

    /*====================== SECCIÓN DEPARTAMENTO ======================*/

    public List<Map<String, Object>> obtenerDepartamentoXEmpresa(int codiEmpr) {
        String sql = "EXEC [sp_bart_rrhh_departamento_listar] ?";
        return sigoldJdbc.queryForList(sql, codiEmpr);
    }

    public List<Map<String, Object>> seleccionarDepartameto(int codiDepa) {
        String sql = "EXEC [sp_bart_rrhh_departamento_seleccionar] ?";

        return sigoldJdbc.queryForList(sql, codiDepa);
    }

    /*====================== SECCIÓN DE SERVICIOS ======================*/

    public List<Map<String, Object>> obtenerServiciosXDepartamento(int  codiDepa) {
        String sql = "SELECT * FROM view_bart_rrhh_servicio where codiDepa=?";
        return sigoldJdbc.queryForList(sql, codiDepa);
    }

    public List<Map<String, Object>> seleccionaServicioPorCodigo(Integer codiServ) {
        String sql = "SELECT * FROM view_bart_rrhh_servicio WHERE codiServ = ? ";

        return sigoldJdbc.queryForList(sql, codiServ);
    }
}
