package com.somto.walletapi.Controller;

import com.somto.walletapi.Models.Transaction;
import com.somto.walletapi.Models.Wallet;
import com.somto.walletapi.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/{userId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable Long userId){
        return new ResponseEntity<>(
                walletService.getWalletByUser(userId), HttpStatus.OK);
    }

    @PostMapping("/fund/{userId}")
    public ResponseEntity<Wallet> fundWallet(
            @PathVariable Long userId,
            @RequestParam BigDecimal amount) {
        return new ResponseEntity<>(
                walletService.fundWallet(userId, amount), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam BigDecimal amount) {
        return new ResponseEntity<>(
                walletService.transfer(senderId, receiverId, amount), HttpStatus.OK);
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<Transaction>> getTransactions(
            @PathVariable Long userId){
        return new ResponseEntity<>(
                walletService.getTransactionHistory(userId), HttpStatus.OK);

    }

}
