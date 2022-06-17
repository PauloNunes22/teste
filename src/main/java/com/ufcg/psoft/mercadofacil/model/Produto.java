package com.ufcg.psoft.mercadofacil.model;
import java.util.UUID;

public class Produto {
	
	private String id;
	
	private String nome; 
	
	private String fabricante;
	
	private double preco;

	public Produto(String nome, String fabricante, double preco) {
		this.id = UUID.randomUUID().toString();
		this.nome = nome;
		this.fabricante = fabricante;
		this.preco = preco;
	}
		
	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getFabricante() {
		return fabricante;
	}
	
	public double getPreco() {
		return preco;
	}
	
	public String toString() {
		return "Produto: " + getNome() + " - Fabricante: " + getFabricante();
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
}
