package org.sid.bankaccountservice.service;

import lombok.AllArgsConstructor;
import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.mappers.AccountMapper;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional  @AllArgsConstructor
public class AccountServiceImpl implements AccountService {

  private BankAccountRepository bankAccountRepository;

  private AccountMapper accountMapper;

  @Override
  public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO) {
    BankAccount bankAccount = BankAccount.builder()
      .id(UUID.randomUUID().toString())
      .createdAt(new Date())
      .balance(bankAccountDTO.getBalance())
      .type(bankAccountDTO.getType())
      .currency(bankAccountDTO.getCurrency())
      .build();
    BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
    BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(saveBankAccount);
    return bankAccountResponseDTO;
  }

  @Override
  public BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO) {
    BankAccount bankAccount = BankAccount.builder()
      .id(id)
      .createdAt(new Date())
      .balance(bankAccountDTO.getBalance())
      .type(bankAccountDTO.getType())
      .currency(bankAccountDTO.getCurrency())
      .build();
    BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
    BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(saveBankAccount);
    return bankAccountResponseDTO;
  }
}
