package com.bartolito.rrhh.service;

import com.bartolito.rrhh.repository.MaestrasRepository;
import com.bartolito.rrhh.repository.RRHHRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class MaestrasService {

    private final MaestrasRepository repository;

    public MaestrasService(MaestrasRepository repository) {
        this.repository = repository;
    }
    /* ====================== SECCIÓN EMPRESA ====================== */

    public List<Map<String, Object>> obtenerEmpresas() {
        return repository.obtenerEmpresa();
    }

    public Map<String, Object> seleccionarEmpresaPorCodigo(int codiEmpr) {

        List<Map<String, Object>> results = repository.seleccionarEmpresa(codiEmpr);

        if (results.isEmpty()) {
            throw new NoSuchElementException("La Empresa con código " + codiEmpr + " no fue encontrada.");
        }
        return results.get(0);
    }

    public int agregarEmpresa(String nombEmpr,

                              String rangEntrPrev, String rangEntrPost, String rangSaliPrev, String rangSaliPost,

                              String toleEntrPrev, String toleEntrPost, String toleSaliPrev, String toleSaliPost) {

        return repository.agregarEmpresa(nombEmpr,

                rangEntrPrev, rangEntrPost, rangSaliPrev, rangSaliPost,

                toleEntrPrev, toleEntrPost, toleSaliPrev, toleSaliPost);
    }

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

    /*====================== SECCIÓN DE PERSONA_MES  ======================*/

    public List<Map<String, Object>> listarContrato(Integer codiEmpr) {
        return repository.listarContrato(codiEmpr);
    }

    public Map<String, Object> seleccionarContrato(Integer codiCntr) {
        List<Map<String, Object>> results =
                repository.seleccionarContrato(codiCntr);

        if (results.isEmpty()) {
            throw new NoSuchElementException(
                    "No existe contrato con código " + codiCntr
            );
        }
        return results.get(0);
    }

    public int agregarContrato(
            Integer codiPers,
            String numCntr,
            String tipoCntr,
            Integer codiDepa,
            Integer codiCarg,
            Integer numeHora,
            String tipoJorn,
            String modalCntr,
            String fechInic,
            String fechFina,
            Double suelBase,
            String moneda,
            Integer usuaCrea) {

        int resultado = repository.agregarContrato(
                codiPers, numCntr, tipoCntr, codiDepa, codiCarg,
                numeHora, tipoJorn, modalCntr,
                fechInic, fechFina, suelBase, moneda, usuaCrea
        );

        if (resultado == 0) {
            throw new IllegalStateException(
                    "La persona ya tiene un contrato ACTIVO"
            );
        }

        if (resultado < 0) {
            throw new IllegalStateException(
                    "Error al registrar el contrato"
            );
        }

        return resultado;
    }

    public int modificarContrato(
            Integer codiCntr,
            Integer codiDepa,
            Integer codiCarg,
            Integer numeHora,
            String tipoJorn,
            String modalCntr,
            String fechFina,
            Double suelBase,
            String moneda,
            Integer usuaModi) {

        int resultado = repository.modificarContrato(
                codiCntr, codiDepa, codiCarg, numeHora,
                tipoJorn, modalCntr, fechFina, suelBase, moneda, usuaModi
        );

        if (resultado == 0) {
            throw new NoSuchElementException(
                    "No existe contrato para modificar"
            );
        }

        if (resultado < 0) {
            throw new IllegalStateException(
                    "No se pudo modificar el contrato"
            );
        }

        return resultado;
    }


    public int finalizarContrato(Integer codiCntr, String motivoFin, Integer usuaModi) {

        int resultado =
                repository.finalizarContrato(codiCntr, motivoFin, usuaModi);

        if (resultado < 0) {
            throw new IllegalStateException(
                    "No se pudo finalizar el contrato"
            );
        }

        return resultado;
    }

    /*====================== SECCIÓN DE FERIADOS  ======================*/
    public List<Map<String, Object>> listarFeriados(String codiMes) {
        return repository.listarFeriados(codiMes);
    }


    public Map<String, Object> seleccionarFeriado(Integer codiFeri) {

        List<Map<String, Object>> results =
                repository.seleccionarFeriado(codiFeri);

        if (results.isEmpty()) {
            throw new NoSuchElementException(
                    "No existe feriado con código " + codiFeri
            );
        }

        return results.get(0);
    }
    public int agregarFeriado(String fechFeri) {

        int resultado = repository.agregarFeriado(fechFeri);

        if (resultado == -1) {
            throw new IllegalStateException(
                    "Ya existe un feriado registrado para la fecha " + fechFeri
            );
        }

        if (resultado <= 0) {
            throw new IllegalStateException(
                    "Error al registrar el feriado"
            );
        }

        return resultado;
    }
    public int modificarFeriado(Integer codiFeri, String fechFeri) {

        int filasAfectadas =
                repository.modificarFeriado(codiFeri, fechFeri);

        if (filasAfectadas == 0) {
            throw new NoSuchElementException(
                    "No existe feriado para modificar con código " + codiFeri
            );
        }

        if (filasAfectadas == -1) {
            throw new IllegalStateException(
                    "Ya existe otro feriado registrado para la fecha " + fechFeri
            );
        }

        if (filasAfectadas < 0) {
            throw new IllegalStateException(
                    "No se pudo modificar el feriado"
            );
        }

        return filasAfectadas;
    }
    /*
     * ====================== SECCIÓN LICENCIAS  ======================
     */
    public List<Map<String, Object>> listarLicencia(
            Integer codiEmpr,
            Integer codiPers,
            String anio
    ) {
        return repository.listarLicencia(
                codiEmpr,
                codiPers,
                anio
        );
    }

    public int agregarLicencia(
            Integer codiEmpr,
            Integer codiPers,
            LocalDate fechLiceIni,
            LocalDate fechLiceFin,
            String cortLice
    ) {

        if (codiPers == null || fechLiceIni == null || fechLiceFin == null || cortLice == null) {
            throw new IllegalArgumentException("Parámetros obligatorios incompletos");
        }

        return repository.agregarLicencia(
                codiEmpr,
                codiPers,
                fechLiceIni,
                fechLiceFin,
                cortLice
        );
    }

    public int eliminarLicencia(
            Integer codiEmpr,
            Integer codiPers,
            LocalDate fechLiceIni,
            LocalDate fechLiceFin
    ) {

        if (codiPers == null || fechLiceIni == null || fechLiceFin == null) {
            throw new IllegalArgumentException("Parámetros obligatorios incompletos");
        }

        if (fechLiceIni.isAfter(fechLiceFin)) {
            throw new IllegalArgumentException("La fecha inicio no puede ser mayor que la fecha fin");
        }

        return repository.eliminarLicencia(
                codiEmpr,
                codiPers,
                fechLiceIni,
                fechLiceFin
        );
    }

    /*
     * ====================== SECCIÓN TIPO DE LICENCIA  ======================
     */
    public List<Map<String, Object>> listarTipoLicencia() {
        return repository.listarTipoLicencia();
    }

    /*
     * ====================== SECCIÓN PERSONA AÑO  ======================
     */
    public List<Map<String, Object>> listarPersonaAnio(
            Integer codiEmpr,
            String anio
    ) {
        return repository.listarPersonaAnio(
                codiEmpr,
                anio
        );
    }


    /*
     * ====================== SECCIÓN TIPO DE OBSERVACION ======================
     */
    public List<Map<String, Object>> listarTipoObservacion(

    ) {
        return repository.listarTipoObservacion(

        );
    }


}
