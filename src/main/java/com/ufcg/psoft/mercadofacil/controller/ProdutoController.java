package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;

	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder ucBuilder) {

		String prodID = produtoService.addProduto(produtoDTO);
		return new ResponseEntity<String>("Produto cadastrado com ID:" + prodID, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/produto/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProdutoId(@PathVariable("id") String id) {

		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}
			
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/nome/{nome}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProdutoNome(@PathVariable("nome") String nome) {

		Produto produto;
		Lote lote;
		produto = produtoService.getProdutoByName(nome);

		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/produto/{nome}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProdutoComLoteByNome(@PathVariable("nome") String nome) {

		Produto produto;
		produto = produtoService.getProdutoByName(nome);
		List<Produto> prodsComLote = produtoService.getProdsWithLote();
		if (prodsComLote.contains(produto)){
			return new ResponseEntity<Produto>(produto, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Produto não possui lote", HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutos() {
		List<Produto> produtos = produtoService.listarProdutos();
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerProduto(@PathVariable("id") String id) throws ProductNotFoundException {

		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}

		produtoService.delProduto(id);
		return new ResponseEntity<Produto>(HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarProduto(@PathVariable("id") String id, @RequestBody ProdutoDTO produtoDTO) {

		Produto produto;
		try {
			produto = produtoService.getProdutoById(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
		}

		produtoService.atualizaProduto(produtoDTO, produto);
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
}
