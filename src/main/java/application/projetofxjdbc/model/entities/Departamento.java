package application.projetofxjdbc.model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;


    public Departamento(){}

    public Departamento(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departamento)) return false;
        Departamento that = (Departamento) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", name='" + nome + '\'' +
                '}';
    }
}