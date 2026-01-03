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

    public List<Map<String, Object>> listarProgramacionMensual(String fechaInicio, String fechaFin , int codiServ) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion ?, ?, ?";
        return sigoldJdbc.queryForList(sql, fechaInicio, fechaFin, codiServ);
    }

    public List<Map<String, Object>> seleccionarProgramacionPorPersona(Integer codiPersona, String fechaInicio, String fechaFin) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion_seleccionar ?, ?, ?";
        return sigoldJdbc.queryForList(sql, codiPersona, fechaInicio, fechaFin);
    }

    public int agregarProgramacion(Integer codiPers, String periodo, int codiServ) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion_agregar ?, ?, ?";
        return sigoldJdbc.queryForObject(sql, Integer.class, codiPers, periodo,codiServ);
    }

    public int eliminarProgramacion(Integer codiPers, String periodo, int codiServ) {
        String sql = "EXEC sp_bart_rrhh_horario_programacion_eliminar ?, ?, ?";
        return sigoldJdbc.queryForObject(sql, Integer.class, codiPers, periodo, codiServ);
    }

    public int modificarProgramacion(Integer nuevoCodiHora, Integer codiPers, String fechProg, Integer codiServ) {
        // Agregamos el cuarto parámetro al SQL
        String sql = "EXEC sp_bart_rrhh_horario_programacion_modificar ?, ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, nuevoCodiHora, codiPers, fechProg, codiServ);
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
        String sql = "EXEC sp_bart_rrhh_horario_personal_listar";
        return sigoldJdbc.queryForList(sql);
    }
    public List<Map<String, Object>> listarPersonalPorServicio(int codiServ) {
        String sql = "EXEC [sp_bart_rrhh_horario_personal_listar_x_servicio] ?";
        return sigoldJdbc.queryForList(sql, codiServ);
    }

    // 2. SELECCIONAR UNO: sp_bart_rrhh_horario_personal_seleccionar
    public List<Map<String, Object>> seleccionarPersonal(Integer id) {
        String sql = "EXEC sp_bart_rrhh_horario_personal_seleccionar ?";
        return sigoldJdbc.queryForList(sql, id);
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

    // 3. REPORTE MARCACIONES (Puede ser un empleado o todos)
    public List<Map<String, Object>> reporteMarcacionesDiaria(String fecha, Integer idEmpleado) {
        // EXEC sp_bart_rrhh_asis_asistencia_diaria '2025-12-01', 123
        String sql = "EXEC [sp_bart_rrhh_asis_marcaciones] ?, ?";

        return sigoldJdbc.queryForList(sql,  idEmpleado,fecha);
    }

    //4.

    public List<Map<String, Object>> reporteResumenDiarioServicio(
            String fechaInicio,
            String fechaFin,
            Integer codiServ
    ) {
        // EXEC sp_bart_rrhh_asis_resumen_diario_servicio '2025-12-01','2025-12-31',10
        String sql = "EXEC sp_bart_rrhh_asis_resumen_diario_servicio ?, ?, ?";

        return sigoldJdbc.queryForList(sql, fechaInicio, fechaFin, codiServ);
    }



    /* ====================== SECCIÓN EMPRESA ====================== */

    /* =========================
    LISTAR
    ========================= */
    public List<Map<String, Object>> obtenerEmpresa() {
        String sql = "EXEC sp_bart_rrhh_empresa_listar";
        return sigoldJdbc.queryForList(sql);
    }

    /* =========================
       SELECCIONAR
       ========================= */
    public List<Map<String, Object>> seleccionarEmpresa(int codiEmpr) {
        String sql = "EXEC sp_bart_rrhh_empresa_seleccionar ?";
        return sigoldJdbc.queryForList(sql, codiEmpr);
    }

    /* =========================
       AGREGAR
       ========================= */
    public int agregarEmpresa(
            String nombEmpr,

            String rangEntrPrev,
            String rangEntrPost,
            String rangSaliPrev,
            String rangSaliPost,

            String toleEntrPrev,
            String toleEntrPost,
            String toleSaliPrev,
            String toleSaliPost
    ) {

        String sql = "EXEC sp_bart_rrhh_empresa_agregar ?,?,?,?,?,?,?,?,?";

        return sigoldJdbc.queryForObject(
                sql,
                Integer.class,

                nombEmpr,

                rangEntrPrev,
                rangEntrPost,
                rangSaliPrev,
                rangSaliPost,

                toleEntrPrev,
                toleEntrPost,
                toleSaliPrev,
                toleSaliPost
        );
    }

    /* =========================
       MODIFICAR
       ========================= */
    public int modificarEmpresa(
            Integer codiEmpr,
            String nombEmpr,

            String rangEntrPrev,
            String rangEntrPost,
            String rangSaliPrev,
            String rangSaliPost,

            String toleEntrPrev,
            String toleEntrPost,
            String toleSaliPrev,
            String toleSaliPost
    ) {

        String sql = "EXEC sp_bart_rrhh_empresa_modificar ?,?,?,?,?,?,?,?,?,?";

        return sigoldJdbc.queryForObject(
                sql,
                Integer.class,

                codiEmpr,
                nombEmpr,

                rangEntrPrev,
                rangEntrPost,
                rangSaliPrev,
                rangSaliPost,

                toleEntrPrev,
                toleEntrPost,
                toleSaliPrev,
                toleSaliPost
        );
    }

    /* ====================== SECCIÓN DEPARTAMENTO ====================== */

    public List<Map<String, Object>> obtenerDepartamentoXEmpresa(int codiEmpr) {
        String sql = "EXEC [sp_bart_rrhh_departamento_listar] ?";
        return sigoldJdbc.queryForList(sql, codiEmpr);
    }

    public List<Map<String, Object>> seleccionarDepartameto(int codiDepa) {
        String sql = "EXEC [sp_bart_rrhh_departamento_seleccionar] ?";

        return sigoldJdbc.queryForList(sql, codiDepa);
    }

    public int agregarDepartamento(String nombDepa, Integer codiEmpr) {
        String sql = "EXEC sp_bart_rrhh_departamento_agregar ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, nombDepa, codiEmpr);
    }

    public int modificarDepartamento(Integer codiDepa, String nombDepa, Integer codiEmpr) {
        // Agregamos el cuarto parámetro al SQL
        String sql = "EXEC sp_bart_rrhh_departamento_modificar ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiDepa, nombDepa, codiEmpr);
    }

    /* ====================== SECCIÓN DE SERVICIOS ====================== */

    public List<Map<String, Object>> obtenerServiciosXDepartamento(int codiDepa) {
        String sql = "SELECT * FROM view_bart_rrhh_servicio where codiDepa=?";
        return sigoldJdbc.queryForList(sql, codiDepa);
    }

    public List<Map<String, Object>> seleccionaServicioPorCodigo(Integer codiServ) {
        String sql = "SELECT * FROM view_bart_rrhh_servicio WHERE codiServ = ? ";

        return sigoldJdbc.queryForList(sql, codiServ);
    }

    public int agregarServicio(String nombServ, Integer codiDepa, Integer codiUsua) {
        String sql = "EXEC sp_bart_rrhh_servicio_agregar ?, ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, nombServ, codiDepa, codiUsua);
    }

    public int editarServicio(Integer codiServ, String nombServ, Integer codiDepa, Integer anulServ, Integer usuamodi) {
        // Agregamos el cuarto parámetro al SQL
        String sql = "EXEC sp_bart_rrhh_servicio_editar ?, ?, ?,?,?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiServ, nombServ, codiDepa, anulServ, usuamodi);
    }

    /* ====================== SECCIÓN DE CARGOS ====================== */

    public List<Map<String, Object>> obtenerCargos(Integer codiEmpr) {
        String sql = "exec [sp_bart_rrhh_cargo_listar] ?";
        return sigoldJdbc.queryForList(sql, codiEmpr);
    }

    public List<Map<String, Object>> seleccionaCargp(Integer codiCarg) {
        String sql = "exec [sp_bart_rrhh_cargo_seleccionar] ?";

        return sigoldJdbc.queryForList(sql, codiCarg);
    }

    public int agregarCargo(String nombCarg, Integer codiEmpr) {
        String sql = "EXEC sp_bart_rrhh_cargo_agregar ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, nombCarg, codiEmpr);
    }

    public int editarCargo(Integer codiCarg, String nombCarg, Integer codiEmpr) {
        // Agregamos el cuarto parámetro al SQL
        String sql = "EXEC sp_bart_rrhh_cargo_modificar ?, ? , ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiCarg, nombCarg, codiEmpr);
    }

    /*====================== SECCIÓN PARAMETRO ======================*/
    public List<Map<String, Object>> listarParametros(int codiEmpr) {
        String sql = "EXEC [sp_bart_rrhh_parametro_listar] ?";
        return sigoldJdbc.queryForList(sql, codiEmpr);
    }

    public List<Map<String, Object>> seleccionarParametro(int codiPara, int codiEmpr) {
        String sql = "EXEC [sp_bart_rrhh_parametro_seleccionar] ?, ?";
        return sigoldJdbc.queryForList(sql, codiPara, codiEmpr);
    }

    public int modificarParametro(int codiPara, String nombPara, String valuPara, int codiEmpr) {
        String sql = "EXEC [sp_bart_rrhh_parametro_modificar] ?, ?, ?, ?";
        return sigoldJdbc.update(sql, codiPara, nombPara, valuPara, codiEmpr);
    }

    public int toggleParametro(int codiPara, int codiEmpr, String valuPara) {
        return sigoldJdbc.update(
                "EXEC sp_bart_rrhh_parametro_toggle ?, ?, ?",
                codiPara, codiEmpr, valuPara
        );
    }

    /*====================== SECCIÓN DE CAP ======================*/

    public List<Map<String, Object>> obtenerCAPPorServicio(int codiServ) {
        String sql =  "exec [sp_bart_rrhh_cap_listar] ?";
        return sigoldJdbc.queryForList(sql,codiServ);
    }

    public List<Map<String, Object>> seleccionaCAPPorPersona(Integer codiPers) {
        String sql = "exec [sp_bart_rrhh_cap_seleccionar] ?";

        return sigoldJdbc.queryForList(sql, codiPers);
    }

    public int agregarCAP(int codiPers, int codiServ) {
        String sql = "EXEC [sp_bart_rrhh_cap_agregar] ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiPers, codiServ);
    }

    public int agregarCAPCargo(int  codiServ, int codiCargo) {
        String sql = "EXEC sp_bart_rrhh_cap_agregar_por_cargo ?,?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiServ,codiCargo);
    }

    public int eliminarCAP(int codiPers) {
        String sql = "EXEC [sp_bart_rrhh_cap_eliminar] ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiPers);
    }

    /*====================== SECCIÓN DE ASIGNACION DE CARGO ======================*/

    public List<Map<String, Object>> obtenerTrabajadoresconCargo() {
        String sql =  "exec [sp_bart_rrhh_personal_cargo_listar] ";
        return sigoldJdbc.queryForList(sql);
    }

    public List<Map<String, Object>> seleccionarTrabajadoresconCargo(Integer codiPers) {
        String sql = "exec [sp_bart_rrhh_personal_cargo_seleccionar] ?";

        return sigoldJdbc.queryForList(sql, codiPers);
    }

    public int asignarTrabajadoresconCargo(Integer codiPers, Integer codiCarg) {
        // Agregamos el cuarto parámetro al SQL
        String sql = "EXEC sp_bart_rrhh_personal_cargo_asignar ?, ?";

        return sigoldJdbc.queryForObject(sql, Integer.class, codiPers, codiCarg);
    }

}
