package com.example.ServiApp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ServiApp.model.ConsejosModel;
import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.ConsejosService;
import com.example.ServiApp.services.PeriodoService;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PeriodoController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PeriodoService periodoService;

    @Autowired
    private ConsejosService consejosService;

    @PostMapping("/calcular-consumo")
    public String calcularConsumo(@ModelAttribute PeriodoModel periodo, Model model, HttpSession session) {
        try {
            UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null) {
                model.addAttribute("error", "No se encontró la sesión del usuario. Por favor inicie sesión nuevamente.");
                return "gestionar_serv";
            }

            List<ServicioModel> servicios = usuarioService.obtenerServiciosPorUsuario(usuarioLogueado.getId());
            model.addAttribute("servicios", servicios);
            
            if (servicios.isEmpty()) {
                model.addAttribute("error", "No tiene servicios registrados. Por favor registre un servicio primero.");
                return "gestionar_serv";
            }
            
            // Debug para verificar qué servicio se está buscando
            System.out.println("Buscando servicio con ID: " + periodo.getServicioId());
            System.out.println("Servicios disponibles: " + servicios.size());
            for (ServicioModel s : servicios) {
                System.out.println("ID: " + s.getId() + ", Tipo: " + s.getTipo_servicio());
            }
            
            Optional<ServicioModel> servicioOpt = servicios.stream()
                    .filter(servicio -> servicio.getId().equals(periodo.getServicioId()))
                    .findFirst();
                    
            if (!servicioOpt.isPresent()) {
                model.addAttribute("error", "No se encontró el servicio seleccionado para el usuario actual.");
                return "gestionar_serv";
            }
            
            ServicioModel servicioSeleccionado = servicioOpt.get();

            long habitantes = servicioSeleccionado.getHabitantes();
            if (habitantes <= 0) {
                model.addAttribute("error", "El número de habitantes debe ser mayor a cero. Por favor actualice su servicio.");
                return "gestionar_serv";
            }
            
            // Definición única de constantes de promedios
            final float PROMEDIO_AGUA = 4.3f;
            final float PROMEDIO_ENERGIA = 80.7f;
            final float PROMEDIO_GAS = 3.9f;
            
            // Normalización única del tipo de servicio
            String tipoServicioNormalizado = servicioSeleccionado.getTipo_servicio() != null ? 
                                              servicioSeleccionado.getTipo_servicio().trim().toLowerCase() : "";
            
            float consumoRegistrado = periodo.getConsumo();
            float consumoEsperado = 0;
            float margenPermitido = 0;
            float promedioCartagena = 0;
            String unidad = "";
            String unidadMedida = "";
            String categoriaConsumo = "";
            
            // Un único switch para todos los cálculos basados en el tipo de servicio
            switch (tipoServicioNormalizado) {
                case "agua":
                    consumoEsperado = PROMEDIO_AGUA * habitantes;
                    margenPermitido = 30;
                    unidadMedida = "m³";
                    unidad = "m3";
                    promedioCartagena = PROMEDIO_AGUA * habitantes;
                    categoriaConsumo = categorizarConsumo(consumoRegistrado, promedioCartagena, 2);
                    break;
                case "energía", "energia":
                    consumoEsperado = PROMEDIO_ENERGIA * habitantes;
                    margenPermitido = 400;
                    unidadMedida = "kWh";
                    unidad = "kWh";
                    promedioCartagena = PROMEDIO_ENERGIA * habitantes;
                    categoriaConsumo = categorizarConsumo(consumoRegistrado, promedioCartagena, 8);
                    break;
                case "gas":
                    consumoEsperado = PROMEDIO_GAS * habitantes;
                    margenPermitido = 30;
                    unidadMedida = "m³";
                    unidad = "m3";
                    promedioCartagena = PROMEDIO_GAS * habitantes;
                    categoriaConsumo = categorizarConsumo(consumoRegistrado, promedioCartagena, 2);
                    break;
                default:
                    model.addAttribute("error", "Tipo de servicio no válido: " + servicioSeleccionado.getTipo_servicio());
                    return "gestionar_serv";
            }
            
            // Verificar si el consumo está dentro del rango permitido
            if (consumoRegistrado > consumoEsperado + margenPermitido) {
                float maxConsumoPermitido = consumoEsperado + margenPermitido;
                
                model.addAttribute("error", "El consumo registrado (" + consumoRegistrado + " " + unidadMedida + 
                                  ") parece inusualmente alto para un hogar con " + habitantes + " habitantes. " +
                                  "El consumo recomendado no debería superar " + String.format("%.1f", maxConsumoPermitido) + 
                                  " " + unidadMedida + ".");
                
                model.addAttribute("servicios", servicios);
                return "gestionar_serv";
            }

            // Resto del código de conversión de fecha
            String mesYAnio = periodo.getMes(); // "2025-04"
            if (mesYAnio != null && mesYAnio.matches("\\d{4}-\\d{2}")) {
                String[] partes = mesYAnio.split("-");
                int ano = Integer.parseInt(partes[0]);
                String mesNumero = partes[1];

                String mesTxt = switch (mesNumero) {
                    case "01" -> "Enero";
                    case "02" -> "Febrero";
                    case "03" -> "Marzo";
                    case "04" -> "Abril";
                    case "05" -> "Mayo";
                    case "06" -> "Junio";
                    case "07" -> "Julio";
                    case "08" -> "Agosto";
                    case "09" -> "Septiembre";
                    case "10" -> "Octubre";
                    case "11" -> "Noviembre";
                    case "12" -> "Diciembre";
                    default -> "Desconocido";
                };

                periodo.setMes(mesTxt);
                periodo.setAno(ano);
            } else {
                model.addAttribute("error", "Formato de fecha incorrecto. Use el formato YYYY-MM.");
                return "gestionar_serv";
            }

            boolean yaExiste = false;
            try {
                yaExiste = periodoService.existePeriodoRegistrado(
                    usuarioLogueado.getId(), 
                    periodo.getMes(), 
                    periodo.getAno(), 
                    servicioSeleccionado.getId()
                );
            } catch (Exception e) {
                System.err.println("Error al verificar existencia de periodo: " + e.getMessage());
                model.addAttribute("error", "Error al verificar existencia de periodo: " + e.getMessage());
                return "gestionar_serv";
            }

            if (yaExiste) {
                model.addAttribute("error", "Ya existe un registro de consumo para este mes. Si deseas cambiar la información de este Consumo puedes dirigirte al apartado de mi Perfil");
                return "gestionar_serv"; 
            }

            // Cálculos (reutilizando variables ya definidas)
            float promedioHogar = consumoRegistrado;
            float promedioHabitante = promedioHogar / habitantes;
            float promedioSemanal = promedioHogar / 4;
            
            periodo.setUnidad(unidad);
            String clasePromedioCartagena = promedioHogar > promedioCartagena ? "alto" : "bajo";

            // Asignar usuario y servicio
            periodo.setUsuarioId(usuarioLogueado.getId());
            periodo.setServicioId(servicioSeleccionado.getId());
            
            // Registrar periodo
            PeriodoModel periodoGuardado = periodoService.registrarPeriodo(periodo);
            if (periodoGuardado == null) {
                model.addAttribute("error", "Error al guardar el período de consumo. Inténtelo nuevamente.");
                return "gestionar_serv";
            }

            // Añadir el ID del periodo al servicio usando el nuevo método específico
            boolean periodoAsignado = usuarioService.añadirPeriodoAServicio(
                usuarioLogueado.getId(), 
                servicioSeleccionado.getId(),
                periodoGuardado.getId()
            );

            if (!periodoAsignado) {
                System.err.println("El período se guardó pero no se pudo asignar al servicio");
                // Opcionalmente se podría eliminar el período si no se pudo asignar
            }

            // Consejos - Con mejor manejo de errores
            List<ConsejosModel> consejosPersonalizados = Collections.emptyList();
            try {
                System.out.println("Buscando consejos para: " + categoriaConsumo + ", " + tipoServicioNormalizado);
                consejosPersonalizados = consejosService.obtenerConsejosTipoServ_TipoCateg(
                    categoriaConsumo, 
                    tipoServicioNormalizado
                );
                
                if (consejosPersonalizados != null && !consejosPersonalizados.isEmpty()) {
                    System.out.println("Consejos encontrados: " + consejosPersonalizados.size());
                    for (ConsejosModel consejo : consejosPersonalizados) {
                        try {
                            consejosService.asignarConsejoAPeriodo(periodoGuardado.getId(), consejo.getId());
                        } catch (Exception e) {
                            System.err.println("Error al asignar consejo " + consejo.getId() + " al período: " + e.getMessage());
                        }
                    }
                } else {
                    System.out.println("No se encontraron consejos para esta categoría y servicio");
                }
            } catch (Exception e) {
                System.err.println("Error al obtener consejos: " + e.getMessage());
                consejosPersonalizados = Collections.emptyList();
            }

            // Agregando todos los datos al modelo (independientemente de consejos)
            model.addAttribute("promedioCartagena", promedioCartagena);
            model.addAttribute("promedioHogar", promedioHogar);
            model.addAttribute("promedioHabitante", promedioHabitante);
            model.addAttribute("promedioSemanal", promedioSemanal);
            model.addAttribute("unidad", unidad);
            model.addAttribute("consejos", consejosPersonalizados);
            model.addAttribute("clasePromedioCartagena", clasePromedioCartagena);

            return "gestionar_serv";
            
        } catch (Exception e) {
            // Captura cualquier excepción no manejada y proporciona información de depuración
            e.printStackTrace();
            model.addAttribute("error", "Error inesperado: " + e.getMessage());
            model.addAttribute("servicios", usuarioService.obtenerServiciosPorUsuario(
                ((UsuarioModel) session.getAttribute("usuarioLogueado")).getId()
            ));
            return "gestionar_serv";
        }
    }

    private String categorizarConsumo(float consumoHogar, float promedio, float margen) {
        if (consumoHogar < promedio - margen) {
            return "Bajo";
        } else if (consumoHogar > promedio + margen) {
            return "Elevado";
        } else {
            return "Moderado";
        }
    }

    @GetMapping("/gestionar-servicio")
    public String gestionarServicio(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login"; 
        }

        List<ServicioModel> servicios = usuarioService.obtenerServiciosPorUsuario(usuarioLogueado.getId());
        model.addAttribute("servicios", servicios);
        return "gestionar_serv";
    }

    @GetMapping("/consejos-personzalidos")
    public String consejosPersonalizados(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login"; 
        }

        List<ServicioModel> servicios = usuarioService.obtenerServiciosPorUsuario(usuarioLogueado.getId());
        List<Map<String, Object>> periodosConData = new ArrayList<>();

        try {
            // Obtener todos los periodos de todos los servicios
            for (ServicioModel servicio : servicios) {
                List<PeriodoModel> periodosServicio = periodoService.obtenerPeriodosPorServicio(servicio.getId());
                
                for (PeriodoModel periodo : periodosServicio) {
                    Map<String, Object> periodoMap = new HashMap<>();
                    
                    // Datos básicos del periodo
                    periodoMap.put("id", periodo.getId());
                    periodoMap.put("mes", periodo.getMes());
                    periodoMap.put("ano", periodo.getAno());
                    periodoMap.put("consumo", periodo.getConsumo());
                    periodoMap.put("unidad", periodo.getUnidad());
                    
                    // Datos del servicio (ya que no está embebido directamente)
                    periodoMap.put("tipoServicio", servicio.getTipo_servicio());
                    periodoMap.put("empresa", servicio.getEmpresa());
                    
                    // Obtener los consejos asociados a este periodo
                    List<ConsejosModel> consejos = consejosService.obtenerConsejosPorPeriodo(periodo.getId());
                    periodoMap.put("consejos", consejos);
                    
                    periodosConData.add(periodoMap);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar periodos y consejos: " + e.getMessage());
            e.printStackTrace();
        }
        
        model.addAttribute("periodosConData", periodosConData);
        return "consejos_personalizados"; 
    }

    @GetMapping("/mis-consumos")
    public String misConsumos(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login"; 
        }

        List<ServicioModel> servicios = usuarioService.obtenerServiciosPorUsuario(usuarioLogueado.getId());
        
        // Lista para almacenar periodos con su información de servicio
        List<Map<String, Object>> periodosConInfo = new ArrayList<>();

        // Obtener todos los periodos de todos los servicios del usuario
        for (ServicioModel servicio : servicios) {
            List<PeriodoModel> periodos = periodoService.obtenerPeriodosPorServicio(servicio.getId());
            
            for (PeriodoModel periodo : periodos) {
                Map<String, Object> periodoInfo = new HashMap<>();
                periodoInfo.put("id", periodo.getId());
                periodoInfo.put("mes", periodo.getMes());
                periodoInfo.put("ano", periodo.getAno());
                periodoInfo.put("consumo", periodo.getConsumo());
                periodoInfo.put("unidad", periodo.getUnidad());
                periodoInfo.put("servicioId", servicio.getId());
                periodoInfo.put("tipoServicio", servicio.getTipo_servicio());
                
                periodosConInfo.add(periodoInfo);
            }
        }

        model.addAttribute("periodosConInfo", periodosConInfo);
        model.addAttribute("section", "mis-consumos");
        return "perfil_datos";
    }

    @GetMapping("/editar-consumo/{id}")
    public String editarConsumo(@PathVariable String id, Model model, HttpSession session) {
        periodoService.obtenerPeriodoPorId(id).orElseThrow(() ->
                new IllegalArgumentException("Consumo no encontrado con id: " + id));

        model.addAttribute("editarConsumoId", id);
        
        // Redirigimos a mis-consumos para mostrar todos los periodos con el modo edición activado
        return misConsumos(model, session);
    }

    @GetMapping("/api/consumos/{tipoServicio}/{orden}")
    @ResponseBody
    public Map<String, Object> obtenerConsumosPorTipoServicio(
            @PathVariable String tipoServicio,
            @PathVariable String orden,
            HttpSession session) {

        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            throw new IllegalArgumentException("Usuario no logueado.");
        }

        List<ServicioModel> servicios = usuarioService.obtenerServiciosPorUsuario(usuarioLogueado.getId());

        final Map<String, Float> promediosPorPersona = Map.of(
            "agua", 4.3f,
            "energía", 80.7f,
            "gas", 3.9f
        );

        List<PeriodoModel> periodos = servicios.stream()
            .filter(servicio -> servicio.getTipo_servicio().equalsIgnoreCase(tipoServicio))
            .flatMap(servicio -> periodoService.obtenerPeriodosPorServicio(servicio.getId()).stream())
            .collect(Collectors.toList());

        Map<String, Integer> meses = Map.ofEntries(
            Map.entry("Enero", 1), Map.entry("Febrero", 2), Map.entry("Marzo", 3),
            Map.entry("Abril", 4), Map.entry("Mayo", 5), Map.entry("Junio", 6),
            Map.entry("Julio", 7), Map.entry("Agosto", 8), Map.entry("Septiembre", 9),
            Map.entry("Octubre", 10), Map.entry("Noviembre", 11), Map.entry("Diciembre", 12)
        );

        periodos.sort((p1, p2) -> {
            int cAno = Integer.compare(p1.getAno(), p2.getAno());
            return cAno != 0 ? cAno : Integer.compare(meses.get(p1.getMes()), meses.get(p2.getMes()));
        });

        if ("desc".equalsIgnoreCase(orden)) {
            Collections.reverse(periodos);
        }

        List<String> labels = new ArrayList<>();
        List<Float> consumosTotales = new ArrayList<>();
        List<Float> consumosPorHabitante = new ArrayList<>();
        List<Long> habitantesPorMes = new ArrayList<>();

        float totalConsumo = 0f;

        for (PeriodoModel p : periodos) {
            labels.add(p.getMes() + " " + p.getAno());
            float consumo = p.getConsumo();
            
            // Obtener habitantes del servicio vinculado al periodo
            Optional<ServicioModel> servicioOpt = usuarioService.obtenerServicioPorId(
                usuarioLogueado.getId(), 
                p.getServicioId()
            );
            long habitantes = servicioOpt.map(ServicioModel::getHabitantes).orElse(1L);

            totalConsumo += consumo;
            habitantesPorMes.add(habitantes);
            consumosTotales.add(consumo);
            consumosPorHabitante.add(habitantes > 0 ? consumo / habitantes : 0f);
        }

        float promedioPorPersona = promediosPorPersona.getOrDefault(tipoServicio.toLowerCase(), 0f);
        float promedioPorHogar = 0f;

        if (!habitantesPorMes.isEmpty()) {
            long promedioHabitantes = Math.round(habitantesPorMes.stream().mapToLong(i -> i).average().orElse(1));
            promedioPorHogar = promedioHabitantes * promedioPorPersona;
        }

        float promedioConsumoHogar = !consumosTotales.isEmpty()
                ? (float) consumosTotales.stream().mapToDouble(Float::doubleValue).average().orElse(0f)
                : 0f;

        Map<String, Object> response = new HashMap<>();
        response.put("labels", labels);
        response.put("consumosTotales", consumosTotales);
        response.put("consumosPorHabitante", consumosPorHabitante);
        response.put("habitantesPorMes", habitantesPorMes);
        response.put("promedioPorHogar", promedioPorHogar);
        response.put("promedioConsumoHogar", promedioConsumoHogar);

        return response;
    }

    @PostMapping("/actualizar-consumo/{id}")
    public String actualizarConsumo(@PathVariable String id, @ModelAttribute PeriodoModel periodo) {
        PeriodoModel periodoExistente = periodoService.obtenerPeriodoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Consumo no encontrado con id: " + id));

        periodoExistente.setConsumo(periodo.getConsumo());
        
        // No actualizamos mes y año para evitar duplicados
        // periodoExistente.setMes(periodo.getMes());
        // periodoExistente.setAno(periodo.getAno());
        
        periodoService.guardarPeriodo(periodoExistente);

        return "redirect:/mis-consumos";
    }

    @PostMapping("/eliminar-consumo/{id}")
    public String eliminarConsumo(@PathVariable String id) {
        try {
            System.out.println("Intentando eliminar consumo con ID: " + id);
            periodoService.eliminarPeriodo(id);
            return "redirect:/mis-consumos";
        } catch (Exception e) {
            System.err.println("Error al eliminar consumo: " + e.getMessage());
            e.printStackTrace();
            // Redireccionar con un parámetro de error
            return "redirect:/mis-consumos?error=true";
        }
    }
}
