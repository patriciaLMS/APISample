package br.edu.atitus.apisample.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
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
import br.edu.atitus.apisample.dtos.SignupDTO;
import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.entities.TypeUser;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	// A classe AuthController possui uma dependência de "UserService"
	private final  UserService service;
	
	
	//injeção de dependêmcia via método construtor
	public AuthController(UserService service) {
		super();
		this.service = service;
	}



	@PostMapping("/signup")
	public ResponseEntity<UserEntity> createNewUser( @RequestBody SignupDTO  signup) throws Exception{
		UserEntity newUser = new UserEntity();
		BeanUtils.copyProperties(signup, newUser);
		newUser.setType(TypeUser.Common);
		service.save(newUser);
		return ResponseEntity.ok(newUser);
		
	}
	
	// READ ALL (GET)
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() throws Exception{
        List<UserEntity> users = service.findAll();
        return ResponseEntity.ok(users);
    }
    
    //READ BY ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable UUID id) throws Exception {
        UserEntity user = service.findById(id);
        return ResponseEntity.ok(user);
    }
    
 
	
	@PutMapping("/update/{id}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable UUID id, @RequestBody SignupDTO dto) throws Exception {
	    
	    UserEntity updatedUser = new UserEntity();
	    BeanUtils.copyProperties(dto, updatedUser);
	    service.save(updatedUser);
	    var user = service.update(id, updatedUser);
	    return ResponseEntity.ok(user);
	    }
	
	
	// DELETE (DELETE)
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) throws Exception {
        service.deleteById(id);
        return ResponseEntity.ok("Registro deletado!");
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handlerMethod(Exception ex){
		String msg = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(msg);
	}

}