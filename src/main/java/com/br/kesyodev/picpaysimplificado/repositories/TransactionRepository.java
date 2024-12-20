package com.br.kesyodev.picpaysimplificado.repositories;

import com.br.kesyodev.picpaysimplificado.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
}
