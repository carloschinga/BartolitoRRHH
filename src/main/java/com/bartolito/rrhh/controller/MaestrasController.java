package com.bartolito.rrhh.controller;

import com.bartolito.rrhh.service.MaestrasService;
import com.bartolito.rrhh.service.RRHHService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MaestrasController {
    private final MaestrasService service;
    private Map<String, Object> response;

    public MaestrasController(MaestrasService service) {

        this.service = service;
    }

    /* ====================== SECCIÓN EMPRESA ====================== */

    @GetMapping("/empresas/listar")
    public ResponseEntity<Map<String, Object>> obtenerEmpresa() {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            List<Map<String, Object>> result = service.obtenerEmpresas();

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("empresas", result);

            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar listar empresas");
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz", e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }


    @GetMapping("/empresas/seleccionar/{codiEmpr}")
    public ResponseEntity<Map<String, Object>> seleccionarEmpresaPorCodigo(
            @PathVariable Integer codiEmpr) {

        Map<String, Object> empresaData = service.seleccionarEmpresaPorCodigo(codiEmpr);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("empresa", empresaData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/empresas/agregar")
    public ResponseEntity<Map<String, Object>> agregar(
            @RequestBody Map<String, String> r) {

        int id = service.agregarEmpresa(
                r.get("nombEmpr"),

                r.get("rangEntrPrev"),
                r.get("rangEntrPost"),
                r.get("rangSaliPrev"),
                r.get("rangSaliPost"),

                r.get("toleEntrPrev"),
                r.get("toleEntrPost"),
                r.get("toleSaliPrev"),
                r.get("toleSaliPost")
        );

        return ResponseEntity.ok(Map.of(
                "resultado", "ok",
                "nuevoId", id
        ));
    }


    @PostMapping("/empresas/modificar")
    public ResponseEntity<?> modificar(@RequestBody Map<String,String> r) {

        if (r.get("codiEmpr") == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "resultado", "error",
                    "mensaje", "codiEmpr es obligatorio para modificar"
            ));
        }

        service.modificarEmpresa(
                Integer.parseInt(r.get("codiEmpr")),
                r.get("nombEmpr"),
                r.get("rangEntrPrev"),
                r.get("rangEntrPost"),
                r.get("rangSaliPrev"),
                r.get("rangSaliPost"),
                r.get("toleEntrPrev"),
                r.get("toleEntrPost"),
                r.get("toleSaliPrev"),
                r.get("toleSaliPost")
        );

        return ResponseEntity.ok(Map.of(
                "resultado", "ok",
                "mensaje", "Empresa modificada"
        ));
    }



    /* ====================== SECCIÓN DEPARTAMENTO ====================== */
    @GetMapping("/departamentos/listar/{codiEmpr}")
    public ResponseEntity<Map<String, Object>> obtenerDepartamento(@PathVariable Integer codiEmpr) {

        try {
            List<Map<String, Object>> result = service.obtenerDepartamentoPorEmpresa(codiEmpr);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("departamentos", result);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 2. SI FALLA: Imprimimos el error completo en la consola (Importante para ti)
            e.printStackTrace();

            // 3. Devolvemos el mensaje de error a Postman
            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar listar empresas");
            // Aquí enviamos el error técnico real:
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz", e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/departamentos/seleccionar/{codiDepa}")
    public ResponseEntity<Map<String, Object>> seleccionarDepartamentosPorCodigo(@PathVariable Integer codiDepa) {

        Map<String, Object> horarioData = service.seleccionarDepartamentoPorCodigo(codiDepa);

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("departamentos", horarioData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/departamentos/agregar")
    public ResponseEntity<Map<String, Object>> agregarDepartamentos(@RequestBody Map<String, String> requestBody) {

        String nombDepa = requestBody.get("nombDepa").toString();
        int codiEmpr = Integer.parseInt(requestBody.get("codiEmpr"));

        int nuevoId = service.agregarDepartamento(nombDepa, codiEmpr);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Departamento guardado exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/departamentos/modificar")
    public ResponseEntity<Map<String, Object>> modificarDepartamentos(@RequestBody Map<String, String> requestBody) {

        int codiDepa = Integer.parseInt(requestBody.get("codiDepa"));
        String nombDepa = requestBody.get("nombDepa").toString();
        int codiEmpr = Integer.parseInt(requestBody.get("codiEmpr"));

        service.modificarDepartamento(codiDepa, nombDepa, codiEmpr);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Departamento modificado exitosamente.");

        return ResponseEntity.ok(response);
    }

    /* ====================== SECCIÓN SERVICIO ====================== */
    @GetMapping("/servicios/listar/{codiDepa}")
    public ResponseEntity<Map<String, Object>> obtenerServicios(@PathVariable Integer codiDepa) {

        try {
            List<Map<String, Object>> result = service.obtenerServiciosPorDepartamento(codiDepa);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("servicios", result);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 2. SI FALLA: Imprimimos el error completo en la consola (Importante para ti)
            e.printStackTrace();

            // 3. Devolvemos el mensaje de error a Postman
            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar listar empresas");
            // Aquí enviamos el error técnico real:
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz", e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/servicios/seleccionar/{codiServ}")
    public ResponseEntity<Map<String, Object>> seleccionarServiciosPorCodigo(@PathVariable Integer codiServ) {

        Map<String, Object> horarioData = service.seleccionarServiciosPorCodigo(codiServ);

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("servicios", horarioData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/servicios/agregar")
    public ResponseEntity<Map<String, Object>> agregarServicios(@RequestBody Map<String, String> requestBody) {

        String nombServ = requestBody.get("nombServ").toString();
        int codiDepa = Integer.parseInt(requestBody.get("codiDepa"));
        int codiUsua = Integer.parseInt(requestBody.get("codiUsua"));

        int nuevoId = service.agregarServicio(nombServ, codiDepa, codiUsua);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Servicio guardado exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/servicios/modificar")
    public ResponseEntity<Map<String, Object>> modificarServicios(@RequestBody Map<String, String> requestBody) {

        int codiServ = Integer.parseInt(requestBody.get("codiServ"));
        String nombServ = requestBody.get("nombServ").toString();
        int codiDepa = Integer.parseInt(requestBody.get("codiDepa"));
        int anulServ = Integer.parseInt(requestBody.get("anulServ"));
        int usuamodi = Integer.parseInt(requestBody.get("usuamodi"));

        service.editarServicio(codiServ, nombServ, codiDepa, anulServ, usuamodi);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Servicio modificado exitosamente.");

        return ResponseEntity.ok(response);
    }

    /* ====================== SECCIÓN CARGO ====================== */
    @GetMapping("/cargos/listar/{codiEmpr}")
    public ResponseEntity<Map<String, Object>> obtenerCargos(@PathVariable Integer codiEmpr) {

        try {
            List<Map<String, Object>> result = service.obtenerCargos(codiEmpr);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("cargos", result);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 2. SI FALLA: Imprimimos el error completo en la consola (Importante para ti)
            e.printStackTrace();

            // 3. Devolvemos el mensaje de error a Postman
            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar listar empresas");
            // Aquí enviamos el error técnico real:
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz", e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/cargos/seleccionar/{codiCarg}")
    public ResponseEntity<Map<String, Object>> seleccionarCargoPorCodigo(@PathVariable Integer codiCarg) {

        Map<String, Object> horarioData = service.seleccionarCargosPorCodigo(codiCarg);

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("cargo", horarioData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cargos/agregar")
    public ResponseEntity<Map<String, Object>> agregarCargo(@RequestBody Map<String, String> requestBody) {

        String nombCarg = requestBody.get("nombCarg").toString();
        Integer codiEmpr=Integer.parseInt(requestBody.get("codiEmpr").toString());

        int nuevoId = service.agregarCargo(nombCarg,codiEmpr);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Cargo guardado exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cargos/modificar")
    public ResponseEntity<Map<String, Object>> modificarCargo(@RequestBody Map<String, String> requestBody) {

        int codiCarg = Integer.parseInt(requestBody.get("codiCarg"));
        String nombCarg = requestBody.get("nombCarg").toString();
        int codiEmpr = Integer.parseInt(requestBody.get("codiEmpr"));

        service.modificarCargo(codiCarg, nombCarg, codiEmpr);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Cargo modificado exitosamente.");

        return ResponseEntity.ok(response);
    }
    /*====================== SECCIÓN PARAMETRO ======================*/

    @GetMapping("/parametros/listar/{codiEmpr}")
    public ResponseEntity<Map<String, Object>> listarParametros(
            @PathVariable Integer codiEmpr) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            List<Map<String, Object>> result = service.listarParametros(codiEmpr);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("parametros", result);

            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar listar parámetros");
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz",
                    e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/parametros/seleccionar/{codiEmpr}/{codiPara}")
    public ResponseEntity<Map<String, Object>> seleccionarParametro(
            @PathVariable Integer codiEmpr,
            @PathVariable Integer codiPara) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            List<Map<String, Object>> parametroData =
                    service.seleccionarParametro(codiPara, codiEmpr);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("parametro", parametroData);

            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar seleccionar el parámetro");
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz",
                    e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/parametros/modificar")
    public ResponseEntity<Map<String, Object>> modificarParametro(
            @RequestBody Map<String, String> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
        /* =========================
           Extraer datos del body
           ========================= */
            Integer codiPara = Integer.parseInt(requestBody.get("codiPara"));
            Integer codiEmpr = Integer.parseInt(requestBody.get("codiEmpr"));
            String nombPara = requestBody.get("nombPara");
            String valuPara = requestBody.get("valuPara");

        /* =========================
           Llamar al service
           ========================= */
            int filas = service.modificarParametro(
                    codiPara,
                    nombPara,
                    valuPara,
                    codiEmpr
            );

            if (filas == 0) {
                response.put("resultado", "error");
                response.put("mensaje", "No se pudo actualizar el parámetro");
                return ResponseEntity.ok(response);
            }

            response.put("resultado", "ok");
            response.put("mensaje", "Parámetro actualizado correctamente");

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            response.put("resultado", "error");
            response.put("mensaje", "Datos numéricos inválidos");
            response.put("error_tecnico", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar modificar el parámetro");
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz",
                    e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }


    @PostMapping("/parametros/toggle")
    public ResponseEntity<Map<String, Object>> toggleParametro(
            @RequestBody Map<String, String> body) {

        Map<String, Object> resp = new LinkedHashMap<>();

        try {
            Integer codiPara = Integer.parseInt(body.get("codiPara"));
            Integer codiEmpr = Integer.parseInt(body.get("codiEmpr"));
            String valuPara = body.get("valuPara");

            service.toggleParametro(codiPara, codiEmpr, valuPara);

            resp.put("resultado", "ok");
            return ResponseEntity.ok(resp);

        } catch (Exception e) {
            resp.put("resultado", "error");
            resp.put("mensaje", e.getMessage());
            return ResponseEntity.internalServerError().body(resp);
        }
    }


    /*====================== SECCIÓN ASIGNACION CARGO ======================*/
    @GetMapping("/asigcargo/listar")
    public ResponseEntity<Map<String, Object>> obtenerTrabajadoresconCargo() {

        try {
            List<Map<String, Object>> result = service.obtenerTrabajadoresconCargo();

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("asigcargos", result);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 2. SI FALLA: Imprimimos el error completo en la consola (Importante para ti)
            e.printStackTrace();

            // 3. Devolvemos el mensaje de error a Postman
            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar listar empresas");
            // Aquí enviamos el error técnico real:
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz", e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/asigcargo/seleccionar/{codiPers}")
    public ResponseEntity<Map<String, Object>> asignarTrabajadoresconCargo(@PathVariable Integer codiPers) {

        Map<String, Object> horarioData = service.seleccionarTrabajadoresconCargo(codiPers);

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("asigcargo", horarioData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/asigcargo/asignar")
    public ResponseEntity<Map<String, Object>> asignarTrabajadoresconCargo(@RequestBody Map<String, String> requestBody) {


        int codiPers =Integer.parseInt(requestBody.get("codiPers"));
        int codiCarg;
        try {
            codiCarg = Integer.parseInt(requestBody.get("codiCarg"));
            service.asignarTrabajadoresconCargo(codiPers,  codiCarg);

        }
        catch(Exception ex){
            service.asignarTrabajadoresconCargo(codiPers,null );
        }



        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Cargo asignado correctamente.");

        return ResponseEntity.ok(response);
    }


    /*====================== SECCIÓN CAP ======================*/
    @GetMapping("/caps/listar/{codiServ}")
    public ResponseEntity<Map<String, Object>> obtenerCAPorServicio(@PathVariable Integer codiServ) {

        try {
            List<Map<String, Object>> result = service.obtenerCAPPorServicio(codiServ);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("caps", result);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 2. SI FALLA: Imprimimos el error completo en la consola (Importante para ti)
            e.printStackTrace();

            // 3. Devolvemos el mensaje de error a Postman
            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar listar empresas");
            // Aquí enviamos el error técnico real:
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz", e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/caps/seleccionar/{codiPers}")
    public ResponseEntity<Map<String, Object>> seleccionarCargoPorPersona(@PathVariable Integer codiPers) {

        Map<String, Object> horarioData = service.seleccionarCAPPorPersona(codiPers);

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("cap", horarioData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/cap/agregar")
    public ResponseEntity<Map<String, Object>> agregarCAP(@RequestBody Map<String, String> requestBody) {


        int codiPers =Integer.parseInt(requestBody.get("codiPers"));
        int codiServ =Integer.parseInt(requestBody.get("codiServ"));

        int nuevoId = service.agregarCAP(codiPers, codiServ);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Turno insertado exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/cap/agregarcargo")
    public ResponseEntity<Map<String, Object>> agregarCAPCargo(@RequestBody Map<String, String> requestBody) {


        int codiServ =Integer.parseInt(requestBody.get("codiServ"));
        int codiCarg =Integer.parseInt(requestBody.get("codiCarg"));

        int nuevoId = service.agregarCAPCargo(codiServ, codiCarg);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Turno insertado exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cap/eliminar/{codiPers}")
    public ResponseEntity<Map<String, Object>> eliminarCAP(@PathVariable Integer codiPers, @RequestBody Map<String, String> requestBody) {


        service.eliminarCAP(codiPers);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Turno actualizado exitosamente.");

        return ResponseEntity.ok(response);
    }

    /* ====================== SECCIÓN PERSONA MES ====================== */
    @GetMapping("/persona-mes/listar")
    public ResponseEntity<Map<String, Object>> listar(
            @RequestParam(required = false) Integer codiEmpr,
            @RequestParam(required = false) String codiMes) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            List<Map<String, Object>> result =
                    service.listarPM(codiEmpr, codiMes);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("personaMes", result);

            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al listar asignaciones mensuales");
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz",
                    e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/persona-mes/seleccionar")
    public ResponseEntity<Map<String, Object>> seleccionar(
            @RequestParam Integer codiPers,
            @RequestParam String codiMes) {

        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();

        Map<String, Object> personaMes =
                service.seleccionarPM(codiPers, codiMes);

        data.put("personaMes", personaMes);

        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/persona-mes/agregar")
    public ResponseEntity<Map<String, Object>> agregarPM(
            @RequestBody Map<String, Object> body) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiPers = Integer.parseInt(body.get("codiPers").toString());
            String  codiMes  = body.get("codiMes").toString();
            Integer codiDepa = Integer.parseInt(body.get("codiDepa").toString());
            Integer numeHora = Integer.parseInt(body.get("numeHora").toString());
            Integer codiCarg = Integer.parseInt(body.get("codiCarg").toString());
            String  fechInic = body.get("fechInic").toString();
            String fechFina = null;
            if (body.containsKey("fechFina")
                    && body.get("fechFina") != null
                    && !body.get("fechFina").toString().trim().isEmpty()) {
                fechFina = body.get("fechFina").toString();
            }
            Integer usuaCrea = Integer.parseInt(body.get("usuaCrea").toString());

            int resultado = service.agregarPM(
                    codiPers, codiMes, codiDepa, numeHora,
                    codiCarg, fechInic, fechFina, usuaCrea
            );

            response.put("resultado", "ok");
            response.put("mensaje", "Asignación mensual registrada correctamente");
            response.put("codigo", resultado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/persona-mes/modificar")
    public ResponseEntity<Map<String, Object>> modificarPM(
            @RequestBody Map<String, Object> body) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiPers = Integer.parseInt(body.get("codiPers").toString());
            String  codiMes  = body.get("codiMes").toString();
            Integer codiDepa = Integer.parseInt(body.get("codiDepa").toString());
            Integer numeHora = Integer.parseInt(body.get("numeHora").toString());
            Integer codiCarg = Integer.parseInt(body.get("codiCarg").toString());
            String  fechInic = body.get("fechInic").toString();
            String fechFina = null;
            if (body.containsKey("fechFina")
                    && body.get("fechFina") != null
                    && !body.get("fechFina").toString().trim().isEmpty()) {
                fechFina = body.get("fechFina").toString();
            }
            Integer usuaModi = Integer.parseInt(body.get("usuaModi").toString());

            int resultado = service.modificarPM(
                    codiPers, codiMes, codiDepa, numeHora,
                    codiCarg, fechInic, fechFina, usuaModi
            );

            response.put("resultado", "ok");
            response.put("mensaje", "Asignación mensual modificada correctamente");
            response.put("codigo", resultado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/persona-mes/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarPM(
            @RequestBody Map<String, Object> body) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiPers = Integer.parseInt(body.get("codiPers").toString());
            String  codiMes  = body.get("codiMes").toString();

            int resultado = service.eliminarPM(codiPers, codiMes);

            response.put("resultado", "ok");
            response.put("mensaje", "Asignación mensual eliminada correctamente");
            response.put("codigo", resultado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }
    /* ====================== SECCIÓN FERIADO ====================== */

    @GetMapping("/feriados/listar/{codiMes}")
    public ResponseEntity<Map<String, Object>> listarFeriados(
            @PathVariable String codiMes) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            List<Map<String, Object>> result =
                    service.listarFeriados(codiMes);

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("feriados", result);

            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al listar feriados del período " + codiMes);
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz",
                    e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }



    @GetMapping("/feriados/seleccionar/{codiFeri}")
    public ResponseEntity<Map<String, Object>> seleccionarFeriado(
            @PathVariable Integer codiFeri) {

        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> response = new LinkedHashMap<>();

        Map<String, Object> feriado =
                service.seleccionarFeriado(codiFeri);

        data.put("feriado", feriado);

        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/feriados/agregar")
    public ResponseEntity<Map<String, Object>> agregarFeriado(
            @RequestBody Map<String, Object> body) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            String fechFeri = body.get("fechFeri").toString();

            int resultado = service.agregarFeriado(fechFeri);

            response.put("resultado", "ok");
            response.put("mensaje", "Feriado registrado correctamente");
            response.put("codigo", resultado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/feriados/modificar")
    public ResponseEntity<Map<String, Object>> modificarFeriado(
            @RequestBody Map<String, Object> body) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiFeri = Integer.parseInt(body.get("codiFeri").toString());
            String  fechFeri = body.get("fechFeri").toString();

            int resultado = service.modificarFeriado(codiFeri, fechFeri);

            response.put("resultado", "ok");
            response.put("mensaje", "Feriado modificado correctamente");
            response.put("codigo", resultado);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }




}
