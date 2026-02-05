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

        response.put("data", data == null ? List.of() : data);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/programacion/seleccionar")
    public ResponseEntity<Map<String, Object>> seleccionarProgramacionPorPersona(@RequestParam Integer codiPersona, @RequestParam String inicio, @RequestParam String fin,@RequestParam Integer codiServ) {

        List<Map<String, Object>> result = service.seleccionarProgramacionPorPersona(codiPersona, inicio, fin, codiServ);

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
            Integer codiServ = Integer.parseInt(requestBody.get("codiServ").toString());

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
    public ResponseEntity<Map<String, Object>> agregarProgramacion(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
            String periodo = requestBody.get("periodo").toString();
            Integer codiGrup = Integer.parseInt(requestBody.get("codiGrup").toString());
            Integer codiServ = Integer.parseInt(requestBody.get("codiServ").toString());

            int filas = service.agregarProgramacion(codiPers, periodo, codiGrup, codiServ);

            response.put("resultado", "ok");
            response.put("filas_insertadas", filas);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace(); // 👈 REVISA ESTO EN LA CONSOLA

            response.put("resultado", "error");
            response.put("mensaje", e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }


    @PutMapping("/programacion/eliminar")
    public ResponseEntity<Map<String, Object>> eliminarProgramacion(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
            String periodo   = requestBody.get("periodo").toString();
            Integer codiGrup = Integer.parseInt(requestBody.get("codiGrup").toString());
            Integer codiServ = Integer.parseInt(requestBody.get("codiServ").toString());

            int resultado = service.eliminarProgramacion(codiPers, periodo, codiGrup, codiServ);

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

    @DeleteMapping("/programacion/eliminardia")
    public ResponseEntity<Map<String, Object>> eliminarProgramacionSemana(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
            String fechProg = requestBody.get("fechProg").toString();

            int filas = service.eliminarProgramaciondia(codiPers, fechProg);

            response.put("resultado", filas > 0 ? "ok" : "sin_cambios");
            response.put("filas_afectadas", filas);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

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
                response.put("mensaje", "La programación no existe o ya fue anulada.");

            } else { // resultado == -1
                response.put("resultado", "error");
                response.put("mensaje",
                        "No se puede eliminar la programación porque ya está asignada a personal.");
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
    // GET: /api/marcaciones/diarias?codiPers=60&fecha_ini=2026-01-02&fecha_fin=2026-01-02&codiGroup=2
    @GetMapping("/marcaciones/diarias")
    public ResponseEntity<Map<String, Object>> reporteDiario(@RequestParam int codiGrup,@RequestParam String fecha_ini,@RequestParam String fecha_fin,@RequestParam int reprocesar) {
        List<Map<String, Object>> data=service.marcacionesDiarias(codiGrup, fecha_ini, fecha_fin, reprocesar );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }
    // GET: /api/marcaciones/diarias?codiPers=60&fecha_ini=2026-01-02&fecha_fin=2026-01-02&codiGroup=2
    @GetMapping("/marcaciones/diariasXMes")
    public ResponseEntity<Map<String, Object>> reporteDiarioXMes(@RequestParam int codiGrup,@RequestParam String fecha_ini,@RequestParam String fecha_fin,@RequestParam int reprocesar) {
        List<Map<String, Object>> data=service.marcacionesDiariasXMes(codiGrup, fecha_ini, fecha_fin, reprocesar );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/marcacion/reprocesar-por-turno")
    public ResponseEntity<Map<String, Object>> reprocesarMarcacionPorTurno(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {
            Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
            String  fechProg = requestBody.get("fechProg").toString();
            Integer codiTurn = Integer.parseInt(requestBody.get("codiTurn").toString());

            service.reprocesarMarcacionPorTurno(
                    codiPers,
                    fechProg,
                    codiTurn
            );

            // ✅ SI LLEGÓ AQUÍ → OK
            response.put("resultado", "ok");
            response.put("mensaje", "Marcación reprocesada correctamente por turno");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            response.put("resultado", "error");
            response.put("mensaje", "Error al reprocesar la marcación");
            response.put("error_tecnico", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }






    // GET: /api/reportes/asistencia/mensual?inicio=2025-12-01&fin=2025-12-31&codiGrup=1
    @GetMapping("/reportes/asistencia/mensual")
    public ResponseEntity<Map<String, Object>> reporteMensual(@RequestParam String inicio, @RequestParam String fin, @RequestParam int codiGrup ) {

        List<Map<String, Object>> data = service.reporteAsistenciaMensual(inicio, fin, codiGrup);

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
            @RequestParam String fecha,
            @RequestParam Integer codiGrup
    ) {

        List<Map<String, Object>> data =
                service.reporteResumenDiarioServicio(fecha, codiGrup);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("resultado", "ok");
        response.put("data", data);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/programacion/modificarMarcaciones")
    public ResponseEntity<Map<String, Object>> modificarMarcaciones(
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new LinkedHashMap<>();

        try {

            Integer codiPers = Integer.parseInt(requestBody.get("codiPers").toString());
            String  fechProg = requestBody.get("fechProg").toString(); // yyyy-MM-dd
            Integer codiServ = Integer.parseInt(requestBody.get("codiServ").toString());
            Integer codiTurn = Integer.parseInt(requestBody.get("codiTurn").toString());
            String  tipo     = requestBody.get("tipo").toString();
            Integer codiTipoObsv = Integer.parseInt(requestBody.get("codiTipoObsv").toString());
            Integer codiUsua = Integer.parseInt(requestBody.get("codiUsua").toString());

            // =============================
            // HORA OPCIONAL (I no la usa)
            // =============================
            String hora = null;
            if (requestBody.containsKey("hora") && requestBody.get("hora") != null) {
                String h = requestBody.get("hora").toString();
                if (!h.isBlank()) {
                    hora = h;
                }
            }

            // =============================
            // VALIDACIÓN BÁSICA CONTROLLER
            // =============================
            if (!tipo.equals("E") && !tipo.equals("S") && !tipo.equals("I")) {
                response.put("resultado", "error");
                response.put("mensaje", "Tipo de marcación inválido (E, S, I)");
                return ResponseEntity.badRequest().body(response);
            }

            if ((tipo.equals("E") || tipo.equals("S")) && hora == null) {
                response.put("resultado", "error");
                response.put("mensaje", "La hora es obligatoria para tipo " + tipo);
                return ResponseEntity.badRequest().body(response);
            }

            // =============================
            // LLAMADA AL SERVICE
            // =============================
            int resultado = service.modificarMarcaciones(
                    codiPers,
                    fechProg,
                    codiServ,
                    codiTurn,
                    hora,
                    tipo,
                    codiTipoObsv,
                    codiUsua
            );

            if (resultado > 0) {
                response.put("resultado", "ok");
                response.put("mensaje", "Marcación modificada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                response.put("resultado", "error");
                response.put("mensaje", "No se encontró la marcación a modificar");
                return ResponseEntity.ok(response);
            }

        } catch (NumberFormatException e) {

            response.put("resultado", "error");
            response.put("mensaje", "Formato numérico inválido");
            response.put("error_tecnico", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            e.printStackTrace(); // debugging

            response.put("resultado", "error");
            response.put("mensaje", "Error al intentar modificar la marcación");
            response.put("error_tecnico", e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
    }





}