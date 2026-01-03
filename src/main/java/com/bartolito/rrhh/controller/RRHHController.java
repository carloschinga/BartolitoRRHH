package com.bartolito.rrhh.controller;

import com.bartolito.rrhh.service.RRHHService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/horarios/listarturnos/{codiEmpr}")
    public ResponseEntity<Map<String, Object>> obtenerHorarioTurnos(@PathVariable Integer codiEmpr) {

        List<Map<String, Object>> result = service.obtenerHorarioPorEmpresaTurnos(codiEmpr);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("horario", result);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/horarios/listar/{codiEmpr}")
    public ResponseEntity<Map<String, Object>> obtenerHorario(@PathVariable Integer codiEmpr) {

        List<Map<String, Object>> result = service.obtenerHorarioPorEmpresa(codiEmpr);

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
        Integer usuacrea = Integer.parseInt(requestBody.get("usuacrea").toString());
        Integer codiEmpr = Integer.parseInt(requestBody.get("codiEmpr").toString());

        int nuevoId = service.agregarHorario(nombHora, cortHora,  usuacrea, codiEmpr);

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
        Integer usuamodi = Integer.parseInt(requestBody.get("usuamodi").toString());
        Integer codiEmpr = Integer.parseInt(requestBody.get("codiEmpr").toString());
        Integer anulHora= Integer.parseInt(requestBody.get("anulHora").toString());

        int codigo =service.editarHorario(codiHora, nombHora, cortHora, usuamodi,codiEmpr, anulHora);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("codigo", codigo);
        if(codigo>0)
            response.put("mensaje", "Horario actualizado exitosamente.");
        else if (codigo==0) {
            response.put("mensaje", "No se encontro el horario.");
        }else {
            response.put("mensaje", "No se puede anular porque esta asignado");
        }


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
    public ResponseEntity<Map<String, Object>> listar(
            @RequestParam(required = false) String inicio,
            @RequestParam(required = false) String fin,
            @RequestParam(required = false) Integer codiGrup) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");

        if (inicio == null || fin == null || codiGrup == null) {
            response.put("data", List.of());
            return ResponseEntity.ok(response);
        }

        List<Map<String, Object>> data =
                service.listarProgramacionMensual(inicio, fin, codiGrup);

        // 🔒 CLAVE
        response.put("data", data == null ? List.of() : data);

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

    @PutMapping("/programacion/modificar")
    public ResponseEntity<Map<String, Object>> modificarProgramacion(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer nuevoCodiHora = Integer.parseInt(requestBody.get("codiHora").toString());
            Integer codiPers      = Integer.parseInt(requestBody.get("codiPers").toString());
            String  fechProg      = requestBody.get("fechProg").toString(); // yyyy-MM-dd
            Integer codiGrup = Integer.parseInt(requestBody.get("codiGrup").toString());
            Integer codiServ      = Integer.parseInt(requestBody.get("codiServ").toString());

            int resultado = service.modificarProgramacion(
                    nuevoCodiHora,
                    codiPers,
                    fechProg,
                    codiGrup,
                    codiServ
            );

            if (resultado > 0) {
                response.put("resultado", "ok");
                response.put("mensaje", "Programación modificada exitosamente");
                return ResponseEntity.ok(response);

            } else if (resultado == 0) {
                response.put("resultado", "error");
                response.put("mensaje", "No se encontró la programación a modificar");
                return ResponseEntity.ok(response);

            } else { // resultado == -1
                response.put("resultado", "error");
                response.put("mensaje",
                        "No se puede modificar la programación porque el horario ya fue procesado");
                return ResponseEntity.ok(response);
            }

        } catch (NumberFormatException e) {
            response.put("resultado", "error");
            response.put("mensaje", "Formato numérico inválido");
            response.put("error_tecnico", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            e.printStackTrace(); // 👈 correcto para debugging

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar modificar la programación");
            response.put("error_tecnico", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @PostMapping("/programacion/agregar")
    public ResponseEntity<Map<String, Object>> agregarProgramacion(@RequestBody Map<String, Object> requestBody) {

        Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
        String periodo = requestBody.get("periodo").toString();
        Integer codiGrup = Integer.parseInt(requestBody.get("codiGrup").toString());
        Integer codiServ = Integer.parseInt(requestBody.get("codiServ").toString());

        int nuevoId = service.agregarProgramacion(codiPers, periodo, codiGrup, codiServ);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("mensaje", "Programación agregada exitosamente.");
        response.put("nuevoId", nuevoId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/programacion/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarProgramacion(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
            String periodo   = requestBody.get("periodo").toString();
            Integer codiGrup = Integer.parseInt(requestBody.get("codiGrup").toString());

            int resultado = service.eliminarProgramacion(codiPers, periodo, codiGrup);

            if (resultado > 0) {
                response.put("resultado", "ok");
                response.put("mensaje", "Programación eliminada exitosamente.");

            } else if (resultado == 0) {
                response.put("resultado", "error");
                response.put("mensaje", "No existen registros a eliminar.");

            } else { // resultado == -1
                response.put("resultado", "error");
                response.put("mensaje",
                        "No se puede eliminar la programación porque existen días ya procesados.");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar eliminar la programación");
            response.put("error_tecnico", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @PostMapping("/programacion/listarCabecera")
    public ResponseEntity<Map<String, Object>> listarCabecera(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            String codiMes = requestBody.get("codiMes").toString();
            Boolean soloActivos   = Boolean.parseBoolean(requestBody.get("soloActivos").toString());

            List<Map<String, Object>> data = service.listarCabecera(codiMes, soloActivos);

            response.put("data", data); 
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar eliminar la programación");
            response.put("error_tecnico", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @PostMapping("/programacion/listarServiciosPorGrupo")
    public ResponseEntity<Map<String, Object>> listarServiciosPorGrupo(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiGrup = Integer.parseInt(requestBody.get("codiGrup").toString());

            List<Map<String, Object>> data = service.listarServiciosPorGrupo(codiGrup);

            response.put("data", data); 
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error");
            response.put("error_tecnico", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    @PostMapping("/programacion/agregarCabecera")
    public ResponseEntity<Map<String, Object>> agregarCabecera(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            String codiMes = requestBody.get("codiMes").toString();
            String listaServ   = requestBody.get("listaServ").toString();
            Integer usuaCrea = Integer.parseInt(requestBody.get("usuaCrea").toString());

            int resultado = service.agregarCabecera(codiMes, listaServ, usuaCrea);

            if (resultado > 0) {
                response.put("resultado", "ok");
                response.put("mensaje", "Programación creada exitosamente.");

            } else if (resultado == 0) {
                response.put("resultado", "error");
                response.put("mensaje", "No existen registros a agregar.");

            } else { // resultado == -1
                response.put("resultado", "error");
                response.put("mensaje",
                        "No se puede agregar la programación.");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar eliminar la programación");
            response.put("error_tecnico", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    
    @PostMapping("/programacion/eliminarCabecera")
    public ResponseEntity<Map<String, Object>> eliminarCabecera(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiProg = Integer.parseInt(requestBody.get("codiProg").toString());
            Integer usuaModi   = Integer.parseInt(requestBody.get("usuaModi").toString());

            int resultado = service.eliminarCabecera(codiProg, usuaModi);

            if (resultado > 0) {
                response.put("resultado", "ok");
                response.put("mensaje", "Programación agregada exitosamente.");

            } else if (resultado == 0) {
                response.put("resultado", "error");
                response.put("mensaje", "No existen registros a agregar.");

            } else { // resultado == -1
                response.put("resultado", "error");
                response.put("mensaje",
                        "No se puede agregar la programación");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar eliminar la programación");
            response.put("error_tecnico", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
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

    @GetMapping("/personal/listar/{codiServ}")
    public ResponseEntity<Map<String, Object>> listarPersonal(@PathVariable Integer codiServ ) {

        List<Map<String, Object>> result = service.listarPersonalPorServicio(codiServ);

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

    @GetMapping("/reportes/marcaciones/diaria")
    public ResponseEntity<Map<String, Object>> reporteMarcacionesDiario(@RequestParam String fecha, @RequestParam Integer idEmpleado) {

        List<Map<String, Object>> data = service.reporteMarcacionesDiaria(fecha, idEmpleado);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reportes/resumen/diario/servicio")
    public ResponseEntity<Map<String, Object>> reporteResumenDiarioServicio(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam Integer codiServ
    ) {

        List<Map<String, Object>> data =
                service.reporteResumenDiarioServicio(fechaInicio, fechaFin, codiServ);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }



    /* ====================== SECCIÓN EMPRESA ====================== */
    /* =========================
   LISTAR
   ========================= */
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

    /* =========================
       SELECCIONAR
       ========================= */
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

    /* =========================
       AGREGAR
       ========================= */
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

    /* =========================
       MODIFICAR
       ========================= */
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
/* ======================================
       1. LISTAR PARÁMETROS
       ====================================== */
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



    /* ======================================
       2. SELECCIONAR PARÁMETRO
       ====================================== */
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




    /* ======================================
       3. MODIFICAR PARÁMETRO
       ====================================== */
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
}