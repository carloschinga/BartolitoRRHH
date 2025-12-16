package com.bartolito.rrhh.controller;

import com.bartolito.rrhh.service.RRHHService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class RRHHController {

    private final RRHHService service;
    private Map<String, Object> response;

    public RRHHController(RRHHService service) {

        this.service = service;
    }

    @GetMapping("/turnos/listar")
    public ResponseEntity<Map<String, Object>> obtenerdTurnos() {

        try {
            List<Map<String, Object>> result = service.obtenerdTurnos();

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("turnos", result);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("resultado", "ok");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 2. SI FALLA: Imprimimos el error completo en la consola (Importante para ti)
            e.printStackTrace();

            // 3. Devolvemos el mensaje de error a Postman
            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar listar horarios");
            // Aquí enviamos el error técnico real:
            response.put("error_tecnico", e.getMessage());
            response.put("causa_raiz", e.getCause() != null ? e.getCause().toString() : "Desconocida");

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/turnos/seleccionar/{codiTurn}")
    public ResponseEntity<Map<String, Object>> seleccionarTurnoPorCodigo(@PathVariable Integer codiTurn) {

        Map<String, Object> horarioData = service.seleccionarTurnoPorCodigo(codiTurn);

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("turno", horarioData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/turnos/agregar")
    public ResponseEntity<Map<String, Object>> agregarTurno(@RequestBody Map<String, String> requestBody) {

        String nombTurn = requestBody.get("nombTurn");
        String ingrTurn = requestBody.get("ingrTurn");
        String saldTurn = requestBody.get("saldTurn");

        int nuevoId = service.agregarTurno(nombTurn, ingrTurn, saldTurn);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Turno insertado exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/turnos/editar/{codiTurn}")
    public ResponseEntity<Map<String, Object>> editarTurno(@PathVariable Integer codiTurn, @RequestBody Map<String, String> requestBody) {

        String nombTurn = requestBody.get("nombTurn");
        String ingrTurn = requestBody.get("ingrTurn");
        String saldTurn = requestBody.get("saldTurn");

        service.editarTurno(codiTurn, nombTurn, ingrTurn, saldTurn);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Turno actualizado exitosamente.");

        return ResponseEntity.ok(response);
    }



    /*====================== SECCIÓN DE LA GESTIÓN HORARIO ======================*/

    @GetMapping("/horarios/listar")
    public ResponseEntity<Map<String, Object>> obtenerHorario() {

        List<Map<String, Object>> result = service.obtenerHorario();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("horario", result);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/horarios/seleccionar/{codiHora}")
    public ResponseEntity<Map<String, Object>> seleccionarHorarioPorCodigo(@PathVariable Integer codiHora) {

        Map<String, Object> horarioData = service.seleccionarHorarioPorCodigo(codiHora);

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("horario", horarioData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/horarios/agregar")
    public ResponseEntity<Map<String, Object>> agregarHorario(@RequestBody Map<String, Object> requestBody) {

        String nombHora = requestBody.get("nombHora").toString();
        String cortHora = requestBody.get("cortHora").toString();
        String toleMarc = requestBody.get("toleMarc").toString();
        String toleDesc = requestBody.get("toleDesc").toString();
        Integer usuacrea = Integer.parseInt(requestBody.get("usuacrea").toString());

        int nuevoId = service.agregarHorario(nombHora, cortHora, toleMarc, toleDesc, usuacrea);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Horario creado exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/horarios/editar/{codiHora}")
    public ResponseEntity<Map<String, Object>> editarHorario(@PathVariable Integer codiHora, @RequestBody Map<String, Object> requestBody) {

        String nombHora = requestBody.get("nombHora").toString();
        String cortHora = requestBody.get("cortHora").toString();
        String toleMarc = requestBody.get("toleMarc").toString();
        String toleDesc = requestBody.get("toleDesc").toString();
        Integer usuamodi = Integer.parseInt(requestBody.get("usuamodi").toString());

        service.editarHorario(codiHora, nombHora, cortHora, toleMarc, toleDesc, usuamodi);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Horario actualizado exitosamente.");

        return ResponseEntity.ok(response);
    }

    /*====================== SECCIÓN DE GESTION DETALLE HORARIO ======================*/

    @GetMapping("/horarios/detalle/listar")
    public ResponseEntity<Map<String, Object>> obtenerHorarioDetalle() {

        List<Map<String, Object>> result = service.obtenerHorarioDetalle();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("detalles", result);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/horarios/detalle/seleccionar/{codiHora}")
    public ResponseEntity<Map<String, Object>> seleccionarHorarioDetallePorCodigo(@PathVariable Integer codiHora) {

        List<Map<String, Object>> listaDetalles = service.seleccionarHorarioDetallePorCodigo(codiHora);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("detalles", listaDetalles);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/horarios/detalle/agregar")
    public ResponseEntity<Map<String, Object>> agregarHorarioDetalle(@RequestBody Map<String, Object> requestBody) {

        Integer codiHora = Integer.parseInt(requestBody.get("codiHora").toString());
        Integer codiTurn = Integer.parseInt(requestBody.get("codiTurn").toString());
        Integer anulTurn = Integer.parseInt(requestBody.get("anulTurn").toString()); // 1 o 0
        Integer usuacrea = Integer.parseInt(requestBody.get("usuacrea").toString());

        int nuevoId = service.agregarHorarioDetalle(codiHora, codiTurn, anulTurn, usuacrea);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Detalle de horario agregado exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/horarios/detalle/editar/{codiHoraDeta}")
    public ResponseEntity<Map<String, Object>> editarHorarioDetalle(@PathVariable Integer codiHoraDeta, @RequestBody Map<String, Object> requestBody) {

        Integer codiHora = Integer.parseInt(requestBody.get("codiHora").toString());
        //Integer codiDia = Integer.parseInt(requestBody.get("codiDia"));
        Integer codiTurn = Integer.parseInt(requestBody.get("codiTurn").toString());
        Integer anulTurn = Integer.parseInt(requestBody.get("anulTurn").toString());
        Integer usuamodi = Integer.parseInt(requestBody.get("usuamodi").toString());

        service.editarHorarioDetalle(codiHoraDeta, codiHora, codiTurn, anulTurn, usuamodi);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Detalle de horario actualizado exitosamente.");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/horarios/detalle/eliminar/{codiHoraDeta}")
    public ResponseEntity<Map<String, Object>> eliminarHorarioDetalle(@PathVariable Integer codiHoraDeta, @RequestBody Map<String, Integer> requestBody) {

        Integer codiHora = (requestBody.get("codiHora"));
        Integer codiTurn = (requestBody.get("codiTurn"));
        Integer usuamodi = (requestBody.get("usuamodi"));

        service.eliminarHorarioDetalle(codiHoraDeta, codiHora, codiTurn, usuamodi);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Detalle de horario eliminado (anulado) exitosamente.");

        return ResponseEntity.ok(response);
    }



    /*====================== SECCIÓN PROGRAMACIÓN MENSUAL ======================*/

    @GetMapping("/programacion/listar")
    public ResponseEntity<Map<String, Object>> listar(@RequestParam String inicio, @RequestParam String fin) {

        List<Map<String, Object>> data = service.listarProgramacionMensual(inicio, fin);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/programacion/seleccionar")
    public ResponseEntity<Map<String, Object>> seleccionarProgramacionPorPersona(@RequestParam Integer codiPersona, @RequestParam String inicio, @RequestParam String fin) {

        List<Map<String, Object>> result = service.seleccionarProgramacionPorPersona(codiPersona, inicio, fin);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("programacion", result);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/programacion/agregar")
    public ResponseEntity<Map<String, Object>> agregarProgramacion(@RequestBody Map<String, Object> requestBody) {

        Integer codiHora = Integer.parseInt(requestBody.get("codiHora").toString());
        Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
        String fechProg = requestBody.get("fechProg").toString();

        int nuevoId = service.agregarProgramacion(codiHora, codiPers, fechProg);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Programación agregada exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    // GET: http://localhost:8080/api/personal/listar
    @GetMapping("/personal/listar")
    public ResponseEntity<Map<String, Object>> listarPersonal() {

        List<Map<String, Object>> result = service.listarPersonal();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("personal", result);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    // GET: http://localhost:8080/api/personal/seleccionar/123
    @GetMapping("/personal/seleccionar/{id}")
    public ResponseEntity<Map<String, Object>> seleccionarPersonal(@PathVariable Integer id) {

        Map<String, Object> personaData = service.seleccionarPersonal(id);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("persona", personaData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/programacion/modificar")
    public ResponseEntity<Map<String, Object>> modificarProgramacion(@RequestBody Map<String, Object> requestBody) {

        Integer nuevoCodiHora = Integer.parseInt(requestBody.get("codiHora").toString());
        Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
        String fechProg = requestBody.get("fechProg").toString();

        Integer anulPersHora = Integer.parseInt(requestBody.get("anulPersHora").toString());

        service.modificarProgramacion(nuevoCodiHora, codiPers, fechProg, anulPersHora);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Programación modificada exitosamente.");

        return ResponseEntity.ok(response);
    }


    @PutMapping("/programacion/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarProgramacion(@RequestBody Map<String, Object> requestBody) {
        // Solo necesitamos las llaves para identificar el registro
        Integer codiHora = Integer.parseInt(requestBody.get("codiHora").toString());
        Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
        String fechProg = requestBody.get("fechProg").toString();

        // Llamamos al servicio de eliminar (ya no enviamos el 1, el servicio lo sabe)
        service.eliminarProgramacion(codiHora, codiPers, fechProg);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Programación eliminada (anulada) exitosamente.");

        return ResponseEntity.ok(response);
    }


    /*====================== SECCIÓN REPORTES DE ASISTENCIA ======================*/

    // GET: /api/reportes/asistencia/mensual?inicio=2025-12-01&fin=2025-12-31&codiServ=1
    @GetMapping("/reportes/asistencia/mensual")
    public ResponseEntity<Map<String, Object>> reporteMensual(@RequestParam String inicio, @RequestParam String fin, @RequestParam int codiServ ) {

        List<Map<String, Object>> data = service.reporteAsistenciaMensual(inicio, fin, codiServ);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        // El frontend recibirá un JSON donde las llaves son las fechas ("2025-12-01": "A")
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    // GET: /api/reportes/asistencia/diaria?fecha=2025-12-01&idEmpleado=123
    // Si omites idEmpleado, trae a todos
    @GetMapping("/reportes/asistencia/diaria")
    public ResponseEntity<Map<String, Object>> reporteDiario(@RequestParam String fecha, @RequestParam(required = false) Integer idEmpleado) {

        List<Map<String, Object>> data = service.reporteAsistenciaDiaria(fecha, idEmpleado);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    /*====================== SECCIÓN EMPRESA ======================*/
    @GetMapping("/empresas/listar")
    public ResponseEntity<Map<String, Object>> obtenerEmpresa() {

        try {
            List<Map<String, Object>> result = service.obtenerEmpresas();

            Map<String, Object> data = new LinkedHashMap<>();
            data.put("empresas", result);

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

    @GetMapping("/empresas/seleccionar/{codiEmpr}")
    public ResponseEntity<Map<String, Object>> seleccionarEmpresaPorCodigo(@PathVariable Integer codiEmpr) {

        Map<String, Object> horarioData = service.seleccionarEmpresaPorCodigo(codiEmpr);

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("empresa", horarioData);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }


    /*====================== SECCIÓN DEPARTAMENTO ======================*/
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


    /*====================== SECCIÓN SERVICIO ======================*/
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

}