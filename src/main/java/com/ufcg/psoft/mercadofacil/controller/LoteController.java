package com.ufcg.psoft.mercadofacil.controller;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.LoteService;
import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteController {

    @Autowired
    private LoteService loteService;
    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(value = "/produto/{idProduto}/lote/", method = RequestMethod.POST)
    public ResponseEntity<?> criarLote(@RequestBody LoteDTO loteDTO, UriComponentsBuilder ucBuilder) throws LoteNotFoundException {
        Produto produto;
        try {
            produto = produtoService.getProdutoById(loteDTO.getIdProduto());
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NO_CONTENT);
        }

        String lotID = loteService.addLote(loteDTO);
        return new ResponseEntity<String>("Lote cadastrado com ID:" + lotID, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/lotes", method = RequestMethod.GET)
    public ResponseEntity<?> listarLotes() {
        Collection<Lote> lotes = loteService.listarLotes();

        return new ResponseEntity<Collection<Lote>>(lotes, HttpStatus.OK);
    }

    @RequestMapping(value = "/lote/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removerLote(@PathVariable("id") String id) throws LoteNotFoundException {

        Lote lote;
        try {
            lote = loteService.getLoteById(id);
        } catch (LoteNotFoundException e) {
            return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
        }

        loteService.delLote(id);
        return new ResponseEntity<Produto>(HttpStatus.OK);
    }

    @RequestMapping(value = "/lote/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarLoteId(@PathVariable("id") String id) {

        Lote lote;
        try {
            lote = loteService.getLoteById(id);
        } catch (LoteNotFoundException e) {
            return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Lote>(lote, HttpStatus.OK);
    }

    @RequestMapping(value = "/lote/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizarLote(@PathVariable("id") String id, @RequestBody LoteDTO loteDTO) {

        Lote lote;
        try {
            lote = loteService.getLoteById(id);
        } catch (LoteNotFoundException e) {
            return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NO_CONTENT);
        }

        loteService.atualizaLote(loteDTO, lote);
        return new ResponseEntity<Lote>(lote, HttpStatus.OK);
    }
}
