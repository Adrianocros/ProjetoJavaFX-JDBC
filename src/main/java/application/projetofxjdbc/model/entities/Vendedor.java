package application.projetofxjdbc.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Vendedor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String email;
	private Date dataNasc;
	private Double salarioBase;
	
	private Departamento departamento;
	
	public Vendedor() {
	}


	public Vendedor(Integer id, String nome, String email, Date dataNasc, Double salarioBase, Departamento departamento) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataNasc = dataNasc;
		this.salarioBase = salarioBase;
		this.departamento = departamento;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Double getSalarioBase() {
		return salarioBase;
	}

	public void setSalarioBase(Double salarioBase) {
		this.salarioBase = salarioBase;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Vendedor)) return false;
		Vendedor vendedor = (Vendedor) o;
		return getId().equals(vendedor.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public String toString() {
		return "Vendedor{" +
				"id=" + id +
				", name='" + nome + '\'' +
				", email='" + email + '\'' +
				", dataNasc=" + dataNasc +
				", salarioBase=" + salarioBase +
				", departamento=" + departamento +
				'}';
	}
}
