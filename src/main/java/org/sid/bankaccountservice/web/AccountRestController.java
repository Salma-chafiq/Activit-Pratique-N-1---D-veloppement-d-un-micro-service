package org.sid.bankaccountservice.web;

import jakarta.transaction.Transactional;
import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.mappers.AccountMapper;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class AccountRestController {

  private BankAccountRepository bankAccountRepository;

  private AccountService accountService ;

  private AccountMapper accountMapper;

  public AccountRestController(BankAccountRepository bankAccountRepository, AccountService accountService) {
    this.bankAccountRepository = bankAccountRepository;
    this.accountService = accountService;

  }

  @GetMapping("/bankAccounts")
  public List<BankAccount> bankAccounts() {
    return bankAccountRepository.findAll();
  }

  @GetMapping("/bankAccounts/{id}")
  public BankAccount bankAccount(@PathVariable String id) {
    return bankAccountRepository.findById(id)
      .orElseThrow(() -> new RuntimeException(String.format("Account%s not found ", id)));
  }
  //SOAP cest obliger d'utiliser l'xml ms REST s'adepent de Client mais generalement JSON
  //une methode permet d'ajouter un compte

  @PostMapping("/bankAccounts")
  public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO requestDTO  ) {
    return accountService.addAccount(requestDTO);
  }
//Put: modifier tout les attrubies
  //Patch : modier que les attributs dans la requete

  @PutMapping("/bankAccounts/{id}")
  //@Transactional
  public BankAccount update(@PathVariable String id ,@RequestBody BankAccount bankAccount) {
    // Chercher un account
    BankAccount account = bankAccountRepository.findById(id).orElseThrow();

    // Mise à jour des champs si non null
    if (bankAccount.getBalance() != null) account.setBalance(bankAccount.getBalance());
    if (bankAccount.getCreatedAt() != null) account.setCreatedAt(bankAccount.getCreatedAt());
    if (bankAccount.getType() != null) account.setType(bankAccount.getType());
    if (bankAccount.getCurrency() != null) account.setCurrency(bankAccount.getCurrency());
    return bankAccountRepository.save(account);
  }

  @DeleteMapping("/bankAccounts/{id}")
  public void delete(@PathVariable String id) {
      bankAccountRepository.deleteById(id);
  }


}

