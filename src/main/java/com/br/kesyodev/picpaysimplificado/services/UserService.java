package com.br.kesyodev.picpaysimplificado.services;

import com.br.kesyodev.picpaysimplificado.domain.user.User;
import com.br.kesyodev.picpaysimplificado.domain.user.UserType;
import com.br.kesyodev.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public void saveUser(User user){
        this.userRepository.save(user);
    }

}
