package shop.mihalen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mihalen.entity.AccountEntity;
import shop.mihalen.model.Account;
import shop.mihalen.servive.AccountService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ObjectMapper mapper;

    @GetMapping("/api/accounts")
    List<Account> getAllAccounts() {
        return accountService.findAllAccounts();
    }

    @GetMapping("/api/accounts/pages")
    ResponseEntity<Page<Account>> getPageAccounts(
            @RequestParam("index") Optional<Integer> index,
            @RequestParam("size") Optional<Integer> size) {
        return ResponseEntity.ok(
                accountService.findPageAccounts(index.orElse(10), index.orElse(0)));
    }

    @GetMapping("/api/account/{id}")
    Account getAllAccount(@PathVariable("id") String username) {
        return accountService.findByUsername(username).get();
    }

    // @PutMapping("")
    // public ResponseEntity<Account> putMethodName(@PathVariable String id,
    // @RequestBody AccountEntity account) {
    // return new ResponseEntity<>(HttpStatus.OK);
    // }
}
