package com.fatec.produto.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" }) // manipula - lazy loaded properties
@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long produtoId;
	private String descricao;
	private double custo;
	private int quantidadeNoEstoque;

	public Produto(String descricao, double custo, int quantidade) {
		setDescricao(descricao);
		setQuantidadeNoEstoque(quantidade);
		setCusto(custo);
	}

	public Produto() {
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		if (descricao == null || descricao.isBlank())
			throw new IllegalArgumentException("A descricao não deve estar em branco");
		else
			this.descricao = descricao;
	}

	public int getQuantidadeNoEstoque() {
		return quantidadeNoEstoque;
	}

	public void setQuantidadeNoEstoque(int quantidade) {
		try {
			if (quantidade <= 0)
				throw new IllegalArgumentException("A quantidade deve ser maior que zero");
			else
				this.quantidadeNoEstoque = quantidade;
		} catch (Exception e) {
			throw new IllegalArgumentException("A quantidade deve ser maior que zero");
		}
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		try {
			if (custo <= 0)
				throw new IllegalArgumentException("O custo deve ser maior que zero");
			else
				this.custo = custo;
		} catch (Exception e) {
			throw new IllegalArgumentException("O custo deve ser maior que zero");
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(custo, descricao, quantidadeNoEstoque);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Double.doubleToLongBits(custo) == Double.doubleToLongBits(other.custo)
				&& Objects.equals(descricao, other.descricao) && quantidadeNoEstoque == other.quantidadeNoEstoque;
	}

	@Override
	public String toString() {
		return "Produto [produtoId=" + produtoId + ", descricao=" + descricao + ", custo=" + custo
				+ ", quantidadeNoEstoque=" + quantidadeNoEstoque + "]";
	}
	
}