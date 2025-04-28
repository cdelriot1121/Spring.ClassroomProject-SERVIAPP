package com.example.ServiApp.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.ServiApp.model.ConsejosModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.ConsejosRepository;
import com.example.ServiApp.repository.UsuarioRepository;

/**
 * Clase para cargar datos iniciales en la base de datos.
 * Si quieres ejecutar solo una vez, comenta la anotación @Component
 * y usa la función cargarDatosManuales() desde un controlador.
 */
@Component  // Descomenta para cargar datos al iniciar la aplicación

public class DataLoader {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ConsejosRepository consejosRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        cargarDatosManuales();
    }
    
    public void cargarDatosManuales() {
        // 1. Crear administrador si no existe
        String adminEmail = "carlos123@email.com";
        Optional<UsuarioModel> adminExistente = usuarioRepository.findByEmail(adminEmail);
        
        UsuarioModel admin;
        if (adminExistente.isEmpty()) {
            admin = UsuarioModel.crearAdministrador(
                "Carlos Admin",
                adminEmail,
                passwordEncoder.encode("admin")  // Contraseña "admin" encriptada
            );
            admin = usuarioRepository.save(admin);
            System.out.println("✅ Administrador creado con ID: " + admin.getId());
        } else {
            admin = adminExistente.get();
            System.out.println("✅ Administrador existente con ID: " + admin.getId());
        }
        
        // 2. Cargar consejos predefinidos
        List<ConsejosModel> consejosParaCargar = Arrays.asList(
            // Consejos para Agua - Elevado
            crearConsejo("Elevado", "Instala cabezales de ducha de bajo flujo y aireadores en los grifos para reducir el caudal de agua.", "Agua", admin.getId()),
            crearConsejo("Elevado", "Verifica y repara fugas en tuberías y cisternas, ya que pueden desperdiciar cientos de litros al día.", "Agua", admin.getId()),
            crearConsejo("Elevado", "Reduce el tiempo en la ducha a menos de 5 minutos y apaga el grifo mientras te enjabonas.", "Agua", admin.getId()),
            
            // Consejos para Agua - Moderado
            crearConsejo("Moderado", "Lava frutas y verduras en un recipiente con agua en lugar de bajo el grifo, reutilizando esa agua para plantas.", "Agua", admin.getId()),
            crearConsejo("Moderado", "Riega las plantas temprano en la mañana o al atardecer para minimizar la evaporación.", "Agua", admin.getId()),
            crearConsejo("Moderado", "Utiliza la lavadora y el lavavajillas solo cuando estén completamente cargados.", "Agua", admin.getId()),
            
            // Consejos para Agua - Bajo
            crearConsejo("Bajo", "Usa agua de lluvia recolectada para tareas como lavar el coche o regar el jardín.", "Agua", admin.getId()),
            crearConsejo("Bajo", "Cambia a inodoros de doble descarga, que utilizan menos agua por cada uso.", "Agua", admin.getId()),
            crearConsejo("Bajo", "Fomenta la educación en el hogar sobre la importancia de cerrar bien los grifos después de usarlos.", "Agua", admin.getId()),
            
            // Consejos para Energía - Elevado
            crearConsejo("Elevado", "Sustituye los focos incandescentes por luces LED, que consumen hasta un 80% menos de energía.", "Energía", admin.getId()),
            crearConsejo("Elevado", "Desconecta los electrodomésticos y cargadores que no estés usando; los dispositivos en standby pueden representar hasta un 10% de tu factura eléctrica.", "Energía", admin.getId()),
            crearConsejo("Elevado", "Reduce el uso de aire acondicionado, manteniendo puertas y ventanas cerradas al usarlo, y limpiando sus filtros regularmente.", "Energía", admin.getId()),
            
            // Consejos para Energía - Moderado
            crearConsejo("Moderado", "Aprovecha la luz natural abriendo cortinas durante el día y pinta paredes en colores claros que reflejen mejor la luz.", "Energía", admin.getId()),
            crearConsejo("Moderado", "Planifica el uso de electrodomésticos como lavadoras y hornos, agrupando tareas para maximizar su eficiencia energética.", "Energía", admin.getId()),
            crearConsejo("Moderado", "Programa tu refrigerador a una temperatura adecuada (entre 3-5 °C) y evita sobrecargarlo.", "Energía", admin.getId()),
            
            // Consejos para Energía - Bajo
            crearConsejo("Bajo", "Revisa periódicamente el aislamiento de puertas y ventanas para evitar fugas de temperatura que aumenten el uso de calefacción o aire acondicionado.", "Energía", admin.getId()),
            crearConsejo("Bajo", "Utiliza regletas con interruptores para apagar varios dispositivos a la vez al salir de casa.", "Energía", admin.getId()),
            crearConsejo("Bajo", "Cambia a dispositivos electrónicos de clase energética A++ o superior al renovar equipos.", "Energía", admin.getId()),
            
            // Consejos para Gas - Elevado
            crearConsejo("Elevado", "Cocina con tapas en las ollas para reducir el tiempo y el gas necesario para calentar los alimentos.", "Gas", admin.getId()),
            crearConsejo("Elevado", "Revisa y limpia regularmente los quemadores para asegurarte de que las llamas sean azules (una llama amarilla indica ineficiencia).", "Gas", admin.getId()),
            crearConsejo("Elevado", "Reduce el uso del calentador de agua bajando su temperatura a 50 °C y apagándolo cuando no esté en uso por períodos prolongados.", "Gas", admin.getId()),
            
            // Consejos para Gas - Moderado
            crearConsejo("Moderado", "Usa calentadores de agua instantáneos o solares en lugar de calentadores de tanque.", "Gas", admin.getId()),
            crearConsejo("Moderado", "Planifica las comidas para cocinar varios platos al mismo tiempo y aprovechar el calor residual del horno.", "Gas", admin.getId()),
            crearConsejo("Moderado", "Aísla las tuberías de agua caliente para minimizar la pérdida de calor.", "Gas", admin.getId()),
            
            // Consejos para Gas - Bajo
            crearConsejo("Bajo", "Instala termostatos inteligentes para controlar automáticamente la temperatura y el uso de gas en calefacción.", "Gas", admin.getId()),
            crearConsejo("Bajo", "Cocina en utensilios de presión (como ollas rápidas) para reducir el tiempo de cocción y el consumo de gas.", "Gas", admin.getId()),
            crearConsejo("Bajo", "Mantén puertas cerradas en habitaciones que estén siendo calefaccionadas para conservar el calor.", "Gas", admin.getId())
        );
        
        consejosRepository.saveAll(consejosParaCargar);
        System.out.println("✅ " + consejosParaCargar.size() + " consejos cargados correctamente");
    }
    
    private ConsejosModel crearConsejo(String categoria, String contenido, String tipoServicio, String adminId) {
        ConsejosModel consejo = new ConsejosModel();
        consejo.setCategoriaConsumo(categoria);
        consejo.setContenido(contenido);
        consejo.setTipoServicio(tipoServicio);
        consejo.setAdministradorId(adminId);
        return consejo;
    }
}