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

    public List<Map<String, Object>> obtenerHorarioPorEmpresa(int codiEmpr) {
        String sql = "EXEC sp_bart_rrhh_asis_horario_listar ?";
        return sigoldJdbc.queryForList(sql,codiEmpr);
    }

    public List<Map<String, Object>> obtenerHorarioPorEmpresaTurnos(int codiEmpr) {
        String sql = "EXEC [sp_bart_rrhh_asis_horario_listar_turnos] ?";
        return sigoldJdbc.queryForList(sql,codiEmpr);
    }

    public List<Map<String, Object>> seleccionarHorarioPorCodigo(int codiHora) {
        String sql = "EXEC sp_bart_rrhh_asis_horario_seleccionar ?";

        return sigoldJdbc.queryForList(sql, codiHora);
    }

    public int agregarHorario(String nombHora, String cortHora, Integer usuacrea, Integer codiEmpr) {
        String sql = "EXEC sp_bart_rrhh_asis_horario_agregar ?, ?, ?, ?";
        return sigoldJdbc.queryForObject(sql, Integer.class, nombHora, cortHora, usuacrea, codiEmpr);
    }

    public int editarHorario(Integer codiHora, String nombHora, String cortHora, Integer usuamodi,Integer codiEmpr, Integer anulHora) {
        String sql = "EXEC sp_bart_rrhh_asis_horario_editar ?, ?, ?, ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiHora, nombHora, cortHora, usuamodi, codiEmpr,anulHora   );
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

    public List<Map<String, Object>> listarProgramacionMensual(String fechaInicio, String fechaFin , int codiGrup) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion ?, ?, ?";
        return sigoldJdbc.queryForList(sql, fechaInicio, fechaFin, codiGrup);
    }

    public List<Map<String, Object>> seleccionarProgramacionPorPersona(Integer codiPersona, String fechaInicio, String fechaFin, Integer codiServ) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion_seleccionar ?, ?, ?,? ";
        return sigoldJdbc.queryForList(sql, codiPersona, fechaInicio, fechaFin, codiServ);
    }

    public int agregarProgramacion(Integer codiPers,
                                   String periodo,
                                   int codiGrup,
                                   Integer codiServ) {

        String sql = "EXEC sp_bart_rrhh_horario_programacion_agregar ?, ?, ?, ?";

        return sigoldJdbc.update(sql,
                codiPers,     // INT
                periodo,      // CHAR(7)
                codiGrup,     // INT
                codiServ      // INT
        );
    }


    public int eliminarProgramacion(Integer codiPers, String periodo, int codiGrup, int codiServ) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion_eliminar ?, ?, ?, ?";
        return sigoldJdbc.queryForObject(sql, Integer.class, codiPers, periodo, codiGrup, codiServ);
    }

    public int modificarProgramacion(Integer nuevoCodiHora, Integer codiPers, String fechProg, int codiGrup, int codiServ) {
        // Agregamos el cuarto parámetro al SQL
        String sql = "EXEC sp_bart_rrhh_horario_programacion_modificar ?, ?, ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, nuevoCodiHora, codiPers, fechProg, codiGrup, codiServ);
    }

    public List<Map<String, Object>> listarPersonarPorMesYDepartamento(
            Integer codiDepa,
            String codiMes) {

        String sql = "EXEC sp_bart_rrhh_persona_mes_departamento_listar ?, ?";
        return sigoldJdbc.queryForList(sql, codiDepa, codiMes);
    }

    public int eliminarProgramaciondia(Integer codiPers, String fechProg) {
        String sql = "EXEC sp_bart_rrhh_asis_programacion_eliminar_dia ?, ?";
        return sigoldJdbc.update(sql, codiPers, fechProg);
    }


    public List<Map<String, Object>> listarCabecera(String codiMes, Boolean soloActivos) {
        String sql = "EXEC sp_bart_rrhh_asis_programacion_cabecera_listar ?, ?";
        return sigoldJdbc.queryForList(sql, codiMes, soloActivos);
    }
    
    public List<Map<String, Object>> listarServiciosPorGrupo(Integer codiGrup) {
        String sql = "EXEC sp_bart_rrhh_asis_programacion_servicios_por_grupo ?";
        return sigoldJdbc.queryForList(sql, codiGrup);
    }
    
    public int agregarCabecera(String codiMes, String listaServ, Integer usuaCrea) {
        String sql = "EXEC sp_bart_rrhh_asis_programacion_cabecera_agregar ?, ?, ?";
        return sigoldJdbc.queryForObject(sql, Integer.class, codiMes, listaServ, usuaCrea);
    }
    
    public int eliminarCabecera(Integer codiProg, Integer usuaModi) {
        String sql = "EXEC sp_bart_rrhh_asis_programacion_cabecera_eliminar ?, ?";
        return sigoldJdbc.queryForObject(sql, Integer.class, codiProg, usuaModi);
    }
    
    /// ==========================================================================
    
    // 1. LISTAR TODOS: sp_bart_rrhh_horario_personal_listar
    public List<Map<String, Object>> listarPersonal() {
        String sql = "EXEC sp_bart_rrhh_personal_listar";
        return sigoldJdbc.queryForList(sql);
    }


    // 2. SELECCIONAR UNO: sp_bart_rrhh_horario_personal_seleccionar
    public List<Map<String, Object>> seleccionarPersonal(Integer id) {
        String sql = "EXEC sp_bart_rrhh_personal_seleccionar ?";
        return sigoldJdbc.queryForList(sql, id);
    }

    /*====================== SECCIÓN REPORTES DE ASISTENCIA ======================*/
    // 0. MARCACIONES DIARIAS (Puede ser un empleado o todos)
    public List<Map<String, Object>> marcacionesDiarias( int codiGrup, String fecha_ini, String fecha_fin, int reprocesar) {
        // EXEC sp_bart_rrhh_asis_asistencia_diaria '2025-12-01', 123
        String sql = "EXEC sp_bart_rrhh_marcaciones_diaria ?, ?, ?, ?";

        return sigoldJdbc.queryForList(sql, codiGrup, fecha_ini, fecha_fin, reprocesar);
    }
    public List<Map<String, Object>> marcacionesDiariasXMes( int codiGrup, String fecha_ini, String fecha_fin, int reprocesar) {
        // EXEC sp_bart_rrhh_asis_asistencia_diaria '2025-12-01', 123
        String sql = "EXEC sp_bart_rrhh_marcaciones_diaria_x_mes ?, ?, ?, ?";
        return sigoldJdbc.queryForList(sql, codiGrup, fecha_ini, fecha_fin, reprocesar);
    }
    public int reprocesarMarcacionPorTurno(Integer codiPers,String fechProg,Integer codiTurn) {
        String sql = "EXEC sp_bart_rrhh_marcacion_reproceso_base_x_turno ?, ?, ?";
        return sigoldJdbc.update(sql,codiPers,fechProg,codiTurn);
    }


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

    // 3. REPORTE MARCACIONES (Puede ser un empleado o todos)
    public List<Map<String, Object>> reporteMarcacionesDiaria(String fecha, Integer idEmpleado) {
            // EXEC sp_bart_rrhh_asis_asistencia_diaria '2025-12-01', 123
        String sql = "EXEC [sp_bart_rrhh_asis_marcaciones] ?, ?";

        return sigoldJdbc.queryForList(sql,  idEmpleado,fecha);
    }

    //4.

    public List<Map<String, Object>> reporteResumenDiarioServicio(
            String fecha,
            Integer codiServ
    ) {
        // EXEC sp_bart_rrhh_asis_resumen_diario_servicio '2025-12-01','2025-12-31',10
        String sql = "EXEC sp_bart_rrhh_horario_tareo ?, ?,?";

        return sigoldJdbc.queryForList(sql, fecha,fecha,  codiServ);
    }

    public int modificarMarcaciones(Integer codiPers,String fechProg,int codiServ,int codiTurn,
                                    String hora, String tipo, int codiTipoObsv,int codiUsua) {
        String sql = "EXEC sp_bart_rrhh_asistencia_diaria_actualizar_marcacion ?, ?, ?, ?, ?, ?, ?, ?";

        Object horaParam = null;

        if (!"I".equals(tipo) && hora != null && !hora.isBlank()) {
            // Si viene HH:mm, completar segundos
            if (hora.length() == 5) { // ej: 08:30
                hora = hora + ":00";
            }
            horaParam = java.sql.Time.valueOf(hora);
        }


        try {
            return sigoldJdbc.queryForObject(sql, Integer.class, codiPers, java.sql.Date.valueOf(fechProg), codiServ, codiTurn,
                    horaParam, tipo, codiTipoObsv, codiUsua);
        } catch (Exception e) {
             return -1;
        }
    }




}
