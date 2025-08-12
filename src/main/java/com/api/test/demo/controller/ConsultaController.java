package com.api.test.demo.controller;

import com.api.test.demo.NoticiaDTO;
import com.api.test.demo.excepciones.*;
import com.api.test.demo.service.AbcService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsultaController {

    private final AbcService service;

    public ConsultaController(AbcService service) { this.service = service; }
    
    @GetMapping("/consulta")
    public List<NoticiaDTO> consulta(@RequestParam(value = "q", required = false) String q) {
        if (q == null || q.trim().isEmpty()) {
            throw new ExcepcionBadRequest();
        }

        try {
            List<NoticiaDTO> noticias = service.buscar(q.trim());

            if (noticias == null || noticias.isEmpty()) {
                throw new ExcepcionNotFound(q.trim());
            }
            return noticias;

        } catch (ExcepcionBadRequest | ExcepcionNotFound e) {
            throw e; 
        } catch (Exception e) {
            throw new ExcepcionInterna(e);
        }
    }
}
