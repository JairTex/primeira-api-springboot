package br.com.springboot.mysite.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired // IC/CD ou CDI = Injeção de Dependencia
	private UsuarioRepository usuarioRepository;

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Hello " + name + "!";
	}

	/*
	 * @RequestMapping(value = "/cadastra-usuario/{name}", method =
	 * RequestMethod.GET)
	 * 
	 * @ResponseStatus(HttpStatus.OK) public String cadastraUsuario(@PathVariable
	 * String name) {
	 * 
	 * Usuario usuario = new Usuario(); usuario.setNome(name);
	 * 
	 * usuarioRepository.save(usuario);
	 * 
	 * return "Usuário " + name + " cadastrado com sucesso!"; }
	 */

	@GetMapping(value = "/lista-usuario") // RequestMapping mais enxuto
	@ResponseBody // Retornar os dados no corpo da resposta
	public ResponseEntity<List<Usuario>> listaUsuario() {
		List<Usuario> usuarios = usuarioRepository.findAll(); // Lista todos

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // Retorna um JSON
	}

	@PostMapping(value = "/cadastra-usuario")
	@ResponseBody // Descreve a resposta
	public ResponseEntity<Usuario> cadastraUsuario(@RequestBody Usuario usuario) {
		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);

	}

	/*
	 * @PutMapping(value = "/atualiza-usuario")
	 * 
	 * @ResponseBody // Descreve a resposta public ResponseEntity<Usuario>
	 * atualizaaUsuario(@RequestBody Usuario usuario) { Usuario user =
	 * usuarioRepository.saveAndFlush(usuario);
	 * 
	 * return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	 * 
	 * }
	 */
	@PutMapping(value = "/atualiza-usuario")
	@ResponseBody // ResponseENtity<?> para indicar que pode ser retornada qualquer coisa
	public ResponseEntity<?> atualizaaUsuario(@RequestBody Usuario usuario) {
		if (usuario.getId() == null) {
			return new ResponseEntity<String>("É necessário informar o id do usuario", HttpStatus.OK);
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);

	}

	@DeleteMapping(value = "/deleta-usuario")
	@ResponseBody // Descreve a resposta //RequestParam intercepta a request
	public ResponseEntity<String> deletaUsuario(@RequestParam Long idUser) {
		usuarioRepository.deleteById(idUser);

		return new ResponseEntity<String>("Usuario deletado com sucesso!", HttpStatus.OK);

	}

	@GetMapping(value = "/buscar-usuario-id") // RequestMapping mais enxuto
	@ResponseBody // Retornar os dados no corpo da resposta
	public ResponseEntity<Usuario> buscarUsuarioId(@RequestParam(name = "idUser") Long idUser) {
		Usuario usuario = usuarioRepository.findById(idUser).get(); // Retorna o usuário

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK); // Retorna um JSON
	}
	
	@GetMapping(value = "/buscar-usuario-nome") // RequestMapping mais enxuto
	@ResponseBody // Retornar os dados no corpo da resposta
	public ResponseEntity<List<Usuario>> buscarUsuarioNome(@RequestParam(name = "nomeUser") String nomeUser) {
		List<Usuario> usuarios = usuarioRepository.buscarUsuarioPorNome(nomeUser.trim().toLowerCase()); // Retorna o usuário

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // Retorna um JSON
	}

}
