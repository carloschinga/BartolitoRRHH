package com.bartolito.rrhh.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MaestrasRepository {
    @Autowired
    @Qualifier("sigoldJdbcTemplate")
    private JdbcTemplate sigoldJdbc;
    private String string;


    /* ====================== SECCIÓN EMPRESA ====================== */

    public List<Map<String, Object>> obtenerEmpresa() {
        String sql = "EXEC sp_bart_rrhh_empresa_listar";
        return sigoldJdbc.queryForList(sql);
    }

    public List<Map<String, Object>> seleccionarEmpresa(int codiEmpr) {
        String sql = "EXEC sp_bart_rrhh_empresa_seleccionar ?";
        return sigoldJdbc.queryForList(sql, codiEmpr);
    }

    public int agregarEmpresa(String nombEmpr,
                              String rangEntrPrev,String rangEntrPost,String rangSaliPrev,String rangSaliPost,
                              String toleEntrPrev,String toleEntrPost,String toleSaliPrev,String toleSaliPost) {
        String sql = "EXEC sp_bart_rrhh_empresa_agregar ?,?,?,?,?,?,?,?,?";
        return sigoldJdbc.queryForObject(sql,Integer.class,
                nombEmpr,
                rangEntrPrev,rangEntrPost,rangSaliPrev,rangSaliPost,
                toleEntrPrev,toleEntrPost,toleSaliPrev,toleSaliPost);
    }

    public int modificarEmpresa(Integer codiEmpr,String nombEmpr,
                                String rangEntrPrev,String rangEntrPost,String rangSaliPrev,String rangSaliPost,
                                String toleEntrPrev,String toleEntrPost,String toleSaliPrev,String toleSaliPost) {
        String sql = "EXEC sp_bart_rrhh_empresa_modificar ?,?,?,?,?,?,?,?,?,?";
        return sigoldJdbc.queryForObject(sql,Integer.class,
                codiEmpr,nombEmpr,
                rangEntrPrev,rangEntrPost,rangSaliPrev,rangSaliPost,
                toleEntrPrev,toleEntrPost,toleSaliPrev,toleSaliPost);
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

    /*====================== SECCIÓN DE PERSONA_MES  ======================*/

    public List<Map<String, Object>> listar(Integer codiEmpr, String codiMes) {
        String sql = "EXEC sp_bart_rrhh_persona_mes_listar ?, ?";
        return sigoldJdbc.queryForList(sql,codiEmpr,codiMes);
    }

    public List<Map<String, Object>> seleccionar(Integer codiPers, String codiMes) {
        String sql = "EXEC sp_bart_rrhh_persona_mes_seleccionar ?, ?";
        return sigoldJdbc.queryForList(sql,codiPers,codiMes);
    }

    public int agregar(Integer codiPers,String codiMes,Integer codiDepa,Integer numeHora,
                    Integer codiCarg,String fechInic,String fechFina,Integer usuaCrea) {
        String sql = "EXEC sp_bart_rrhh_persona_mes_agregar ?, ?, ?, ?, ?, ?, ?, ?";
        return sigoldJdbc.queryForObject(
                sql,
                Integer.class,
                codiPers,codiMes,codiDepa,numeHora,
                codiCarg,fechInic,fechFina,usuaCrea
        );
    }

    public int modificar(Integer codiPers,String codiMes,Integer codiDepa,Integer numeHora,
                        Integer codiCarg,String fechInic,String fechFina,Integer usuaModi) {
        String sql = "EXEC sp_bart_rrhh_persona_mes_modificar ?, ?, ?, ?, ?, ?, ?, ?";
        return sigoldJdbc.queryForObject(
                sql,
                Integer.class,
                codiPers,codiMes,codiDepa,numeHora,
                codiCarg,fechInic,fechFina,usuaModi
        );
    }
    public int eliminar(Integer codiPers, String codiMes) {
        String sql = "EXEC sp_bart_rrhh_persona_mes_eliminar ?, ?";
        return sigoldJdbc.queryForObject(sql,Integer.class,codiPers,codiMes);
    }





}
