package br.com.springboot.mysite.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.mysite.model.Usuario;
import br.com.springboot.mysite.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
    
	@Autowired //IC/CD ou CDI = Injeção de Dependencia
	private UsuarioRepository usuarioRepository;
	
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }
    
    @RequestMapping(value = "/cadastra-usuario/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String cadastraUsuario(@PathVariable String name) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(name);
    	
    	usuarioRepository.save(usuario);
    	
    	return "Usuário " + name + " cadastrado com sucesso!";
    }
    
    @GetMapping(value = "/lista-usuario") //RequestMapping mais enxuto
    @ResponseBody //Retornar os dados no corpo da resposta
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	List<Usuario> usuarios = usuarioRepository.findAll(); //Lista todos
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); //Retorna um JSON
    }
    
}
