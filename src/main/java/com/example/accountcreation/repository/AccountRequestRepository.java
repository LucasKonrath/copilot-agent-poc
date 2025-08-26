package com.example.accountcreation.repository;

import com.example.accountcreation.model.AccountRequest;
import com.example.accountcreation.model.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRequestRepository extends JpaRepository<AccountRequest, Long> {
    
    Optional<AccountRequest> findByProcessInstanceId(String processInstanceId);
    
    List<AccountRequest> findByStatus(AccountStatus status);
    
    List<AccountRequest> findByStatusIn(List<AccountStatus> statuses);
}