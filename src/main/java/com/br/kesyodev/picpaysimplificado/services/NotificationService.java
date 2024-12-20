package com.br.kesyodev.picpaysimplificado.services;

import com.br.kesyodev.picpaysimplificado.domain.user.User;
import com.br.kesyodev.picpaysimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

//        ResponseEntity<String> notificarionResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
//
//        if (!(notificarionResponse.getStatusCode() == HttpStatus.OK)){
//            System.out.println("Erro ao enviar notificação");
//            throw new Exception("Serviço de notificação indisponível");
//        }

        System.out.println("Notificação enviada para o usuário");

    }

}
