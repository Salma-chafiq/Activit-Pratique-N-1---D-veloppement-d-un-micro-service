package org.sid.bankaccountservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.entities.Customer;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.repositories.CustomerRepository;
import org.sid.bankaccountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class BankAccountGraphQLController {
  //Injecter
  @Autowired
  private BankAccountRepository bankAccountRepository;
  @Autowired
  private AccountService accountService ;
  @Autowired
  private CustomerRepository customerRepository ;
  //Tout les requette est de type post
  //Query :Consulter
  //Querynotation : pour nmodifier
  //projection
  @QueryMapping
  public List<BankAccount> accountsList() {
    return bankAccountRepository.findAll();
  }

  @QueryMapping
  public BankAccount bankAccountById(@Argument String id) {
    return bankAccountRepository.findById(id)
      .orElseThrow(()-> new RuntimeException(String.format("Account not found",id)));
  }

  @MutationMapping
  public BankAccountResponseDTO addAccount (@Argument BankAccountRequestDTO bankAccount){
    return accountService.addAccount(bankAccount);
  }


  @MutationMapping
  public BankAccountResponseDTO updateAccount (@Argument String id , @Argument BankAccountRequestDTO bankAccount){
    return accountService.updateAccount(id,bankAccount);
  }

  @MutationMapping
  public Boolean deleteAccount (@Argument String id ){
    bankAccountRepository.deleteById(id);
    return true;
  }





  //Customer
  @QueryMapping
  public List<Customer> customers(){
    return customerRepository.findAll();
  }
}


//@Data @NoArgsConstructor @AllArgsConstructor
//class BankAccountDTO {
//  private String type ;
//  private Double balance ;
//  private String currency ;
//}


//record BankAccountDTO (Double Balance , String type , String currency ){}

