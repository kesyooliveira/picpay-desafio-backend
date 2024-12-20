package com.br.kesyodev.picpaysimplificado.services;

import com.br.kesyodev.picpaysimplificado.domain.transaction.Transaction;
import com.br.kesyodev.picpaysimplificado.domain.user.User;
import com.br.kesyodev.picpaysimplificado.dtos.TransactionDTO;
import com.br.kesyodev.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    UserService userService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RestTemplate restTemplate;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        userService.validateTransaction(sender,transactionDTO.amount());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.amount());
        if(!isAuthorized){
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.amount());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

//        var newTransaction = Transaction.builder().amount(transactionDTO.amount())
//                .sender(sender)
//                .receiver(receiver)
//                .timestamp(LocalDateTime.now()).build();

        sender.setBalance(sender.getBalance().subtract(transactionDTO.amount()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.amount()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso!");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso!");

        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal amount){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if(authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("status");
            return "success".equalsIgnoreCase(message);
        }else return false;

    }


}
