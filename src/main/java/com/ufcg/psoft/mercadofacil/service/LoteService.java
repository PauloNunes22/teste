package com.ufcg.psoft.mercadofacil.service;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;

import java.util.Collection;

@Service
public class LoteService {

	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository produtoRep;
	
	private Gson gson = new Gson();
	
	public String addLote(LoteDTO loteDTO) throws LoteNotFoundException {
		Produto prod = this.produtoRep.getProd(loteDTO.getIdProduto());
		
		if(prod == null) throw new LoteNotFoundException("Produto: " + loteDTO.getIdProduto() + " não encontrado");
		Lote lote = new Lote(prod, loteDTO.getQuantidade());
		this.loteRep.addLote(lote);

		return lote.getId();
	}

	public Collection<Lote> listarLotes() {
		return loteRep.getAll();
	}

	public Lote getLoteById(String id) throws LoteNotFoundException {
		Lote lote = this.loteRep.getLote(id);
		if(lote == null) throw new LoteNotFoundException("Lote: " + id + " não encontrado");

		return(lote);
	}

	public void delLote(String id) throws LoteNotFoundException {
		Lote lote = this.loteRep.getLote(id);
		if (lote == null) throw new LoteNotFoundException("Lote: " + id + " não encontrado");
		this.loteRep.delLote(id);
	}

	public Lote atualizaLote(LoteDTO loteDTO, Lote lote) {
		lote.setQuantidade(loteDTO.getQuantidade());
		return lote;
	}
}
