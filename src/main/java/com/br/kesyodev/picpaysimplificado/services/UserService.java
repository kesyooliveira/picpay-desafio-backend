package com.br.kesyodev.picpaysimplificado.services;

import com.br.kesyodev.picpaysimplificado.domain.user.User;
import com.br.kesyodev.picpaysimplificado.domain.user.UserType;
import com.br.kesyodev.picpaysimplificado.dtos.UserDTO;
import com.br.kesyodev.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário do tipo lojista não está autorizado a realizar transação");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Usuário não possui saldo suficiente");
        }

    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(()-> new Exception("Usuário não encontrado"));
    }

    public User createUser(UserDTO data){

        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
//        var newUser = User.builder()
//                .firstName(data.firstName())
//                .lastName(data.lastName())
//                .balance(data.balance())
//                .userType(data.userType())
//                .password(data.password())
//                .email(data.email()).build();
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

}
