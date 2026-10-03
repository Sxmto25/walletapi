package com.somto.walletapi.Service;

import com.somto.walletapi.Models.Transaction;
import com.somto.walletapi.Models.User;
import com.somto.walletapi.Models.Wallet;
import com.somto.walletapi.Repository.TransactionRepository;
import com.somto.walletapi.Repository.UserRepository;
import com.somto.walletapi.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Wallet getWalletByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }
    public Wallet fundWallet(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero!");
        }
        Wallet wallet = getWalletByUser(userId);

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setReceiverId(wallet.getUserId());
        transaction.setAmount(amount);
        transaction.setType("CREDIT");
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        return wallet;
    }
    public Transaction transfer(Long senderId, Long receiverId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater thn zero!");
        }
        Wallet senderWallet = getWalletByUser(senderId);

        if (senderWallet.getBalance().compareTo(amount) <= 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        Wallet receiverWallet = getWalletByUser(receiverId);

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        walletRepository.save(senderWallet);

        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));
        walletRepository.save(receiverWallet);

        Transaction transaction = new Transaction();
        transaction.setSenderId(senderWallet.getUserId());
        transaction.setReceiverId(receiverWallet.getUserId());
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");
        transaction.setStatus("SUCCESS");
        transaction.setDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findBySenderId(userId);
    }



}
