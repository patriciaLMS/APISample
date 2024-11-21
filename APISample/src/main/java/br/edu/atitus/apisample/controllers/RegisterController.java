package br.edu.atitus.apisample.controllers;

import java.beans.BeanProperty;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.apisample.dtos.RegisterDTO;
import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.RegisterService;
import br.edu.atitus.apisample.services.UserService;

@RestController
@RequestMapping("/registers")
public class RegisterController {
	
	private final RegisterService service;
	private final UserService userService;
	
	//Cria um novo recurso no sistema com base nos dados fornecidos.
	@PostMapping
	public ResponseEntity<RegisterEntity> createRegister(@RequestBody RegisterDTO registerDTO) throws Exception {
		
		RegisterEntity newRegister = new RegisterEntity();
		BeanUtils.copyProperties(registerDTO, newRegister);
		UserEntity user = userService.findAll().get(0);
		newRegister.setUser(user);
		service.save(newRegister);
		return ResponseEntity.status(HttpStatus.CREATED).body(newRegister);
		
	}

	public RegisterController(RegisterService service, UserService userService) {
		super();
		this.service = service;
		this.userService = userService;
		
		
	}
	
	
	// Obter todos os registros
	@GetMapping
	public ResponseEntity<List<RegisterEntity>> getRegisters() throws Exception{
		var registers = service.findAll();
		
		return ResponseEntity.ok(registers);
	}
	
	// Obter registros por id
	 @GetMapping("/{id}")
	 public ResponseEntity<RegisterEntity> getOneRegister(@PathVariable UUID id) throws Exception{
		 var register = service.findById(id);
		 return ResponseEntity.ok(register);
	 }
	 
	
	// Atualizar registros por id
	@PutMapping("/{id}")
	    public ResponseEntity<RegisterEntity> updateRegister(@PathVariable UUID id, @RequestBody RegisterDTO dto) throws Exception {
		RegisterEntity register = service.findById(id);
		BeanUtils.copyProperties(dto, register);
		
		service.save(register);
	    return ResponseEntity.ok(register);
	    }
	
	// Deletar um registro pelo ID usando DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegister(@PathVariable UUID id) throws Exception {
        service.deleteById(id);
        return ResponseEntity.ok("Registro deletado!");
    }
    
    @ExceptionHandler(Exception.class)
    	public ResponseEntity<String> handlerException(Exception ex){
    	String message = ex.getMessage().replaceAll("\r\n", "");
    	return ResponseEntity.badRequest().body(message);
    }

	
	
}