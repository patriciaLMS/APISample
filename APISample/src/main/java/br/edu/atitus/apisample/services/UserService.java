package br.edu.atitus.apisample.services;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;



@Service //spring, gerencia objs dessa classe p mim

    
public class UserService {
	// Essa classe possui uma dependência de um objeto UserRepository
	private final UserRepository repository;
	//No método construtor existe a injeção de dependência
	public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}




	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
	
	
	
	
	public UserEntity save(UserEntity newUser) throws Exception {
		// TODO validar regras de negócio
		if(newUser == null)
			throw new Exception("Objeto nulo");
		if(newUser.getName() == null || newUser.getName().isEmpty())
			throw new Exception("Nome Inválido!");
		newUser.setName(newUser.getName().trim());
		
		if(newUser.getEmail() == null || newUser.getEmail().isEmpty())
			throw new Exception("Email Inválido!");
		newUser.setEmail(newUser.getEmail().trim());

		if(repository.existsByEmail(newUser.getEmail()))
			throw new Exception("Já existe usuário com este e-mail");
		
		//invocar método camada repository
		this.repository.save(newUser);
		return newUser;

	}
	
    public List<UserEntity> findAll() throws Exception {
        return repository.findAll();
    }
    
    
	
	
	public UserEntity update(UUID id, UserEntity updatedUser) throws Exception {
	    // Tenta encontrar o usuário pelo ID fornecido
	    UserEntity existingUser = repository.findById(id)
	            .orElseThrow(() -> new Exception("Usuário não encontrado com esse ID: " + id));

	    // Copia as propriedades de updatedUser para existingUser, exceto o campo 'id'
	    BeanUtils.copyProperties(updatedUser, existingUser, "id");

	    // Salva o usuário atualizado no repositório e retorna o resultado
	    return repository.save(existingUser);
	}
	
	public UserEntity findById(UUID id) throws Exception {
	    return repository.findById(id)
	            .orElseThrow(() -> new Exception("Usuário não encontrado com o ID: " + id));
	}
	
	
	  public void deleteById(UUID id) throws Exception {
	        if (!repository.existsById(id)) {
	            throw new Exception("Usuário não encontrado com o ID: " + id);
	        }
	        repository.deleteById(id);
	    }




	
	
	
}