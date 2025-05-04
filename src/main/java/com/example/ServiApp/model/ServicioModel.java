package com.example.ServiApp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;


// La serialización es el proceso de convertir un objeto en una secuencia de bytes,
// lo que permite que dicho objeto sea almacenado (por ejemplo, en Redis) o transmitido (por ejemplo, a través de la red).
// En Java, un objeto debe implementar la interfaz java.io.Serializable para que pueda ser serializado.
// Cuando usamos Redis como almacén de sesiones (como con Spring Session),
// los objetos que se guardan en la sesión —como usuarios autenticados— deben ser serializables.
// Si intentamos guardar un objeto no serializable, se lanzará una excepción como NotSerializableException.
// Al registrar un serializador (como RedisSerializer.java()), le decimos a Spring cómo convertir los objetos en bytes
// para almacenarlos en Redis y luego reconstruirlos cuando sea necesario.
// En este caso, estamos utilizando RedisSerializer.java() para serializar objetos Java de forma predeterminada.

public class ServicioModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Field(name = "servicio_id")
    private String id;

    @Field(name = "tipo_servicio")
    private String tipoServicio;

    @Field(name = "empresa")
    private String empresa;

    @Field(name = "poliza")
    private long poliza;

    @Field(name = "habitantes_hogar")
    private long habitantes;

    // Referencias a periodos
    @Field(name = "periodos_ids")
    private List<String> periodosIds = new ArrayList<>();

    public ServicioModel() {
    }

    public ServicioModel(String id, String tipo_servicio, String empresa, long poliza, long habitantes) {
        this.id = id;
        this.tipoServicio = tipo_servicio;
        this.empresa = empresa;
        this.poliza = poliza;
        this.habitantes = habitantes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo_servicio() {
        return tipoServicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipoServicio = tipo_servicio;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public long getPoliza() {
        return poliza;
    }

    public void setPoliza(long poliza) {
        this.poliza = poliza;
    }

    public long getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(long habitantes) {
        this.habitantes = habitantes;
    }

    public List<String> getPeriodosIds() {
        return periodosIds;
    }

    public void setPeriodosIds(List<String> periodosIds) {
        this.periodosIds = periodosIds;
    }

    public void addPeriodoId(String periodoId) {
        if (this.periodosIds == null) {
            this.periodosIds = new ArrayList<>();
        }
        // Verificar que no exista ya
        if (!this.periodosIds.contains(periodoId)) {
            this.periodosIds.add(periodoId);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServicioModel{");
        sb.append("id=").append(id);
        sb.append(", tipo_servicio=").append(tipoServicio);
        sb.append(", empresa=").append(empresa);
        sb.append(", poliza=").append(poliza);
        sb.append(", habitantes=").append(habitantes);
        sb.append(", periodos=").append(periodosIds != null ? periodosIds.size() : 0);
        sb.append('}');
        return sb.toString();
    }
}
