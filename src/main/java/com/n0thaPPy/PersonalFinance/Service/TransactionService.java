package com.n0thaPPy.PersonalFinance.Service;

import com.n0thaPPy.PersonalFinance.Dtos.TransactionReceiveDto;
import com.n0thaPPy.PersonalFinance.Dtos.TransactionUpdateReceiverDto;
import com.n0thaPPy.PersonalFinance.Exception.BalanceInsufficient;
import com.n0thaPPy.PersonalFinance.Model.AccountModel;
import com.n0thaPPy.PersonalFinance.Model.TransactionModel;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Repo.AccountRepo;
import com.n0thaPPy.PersonalFinance.Repo.TransactionRepository;
import com.n0thaPPy.PersonalFinance.Repo.UserRepo;
import com.n0thaPPy.PersonalFinance.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    public List<TransactionModel> getTransactions() {
        return repo.findAll();
    }

    public TransactionModel createTransaction(TransactionReceiveDto transactionReceiveDto)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userRepo.findByUsername(userName);
        AccountModel accountModel=accountRepo.findByUserUsername(userName);
        TransactionModel transactionModel=new TransactionModel(transactionReceiveDto);
        transactionModel.setUser(user);
        if(transactionModel.getType()== TransactionType.EXPENSE)
        {
            if(accountModel.getBalance().compareTo(transactionModel.getMoney())>=1)
            {
                accountModel.setBalance(accountModel.getBalance().subtract(transactionModel.getMoney()));
            }
            else
                throw new BalanceInsufficient(accountModel.getBalance(),transactionModel.getMoney());

        }
        else
        {
            accountModel.setBalance(accountModel.getBalance().add(transactionModel.getMoney()));
        }


        return repo.save(transactionModel);

    }
    public TransactionModel updateTransaction(TransactionUpdateReceiverDto transactionReceiveDto)
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userRepo.findByUsername(userName);
        TransactionModel transactionModel=new TransactionModel(transactionReceiveDto);
        transactionModel.setUser(user);

        return repo.save(transactionModel);

    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }

    public List<TransactionModel> getTransaction(String username)
    {
    return repo.getTransactionsByUsername(username);
    }

    public Optional<TransactionModel> getTransactionById(int id) {

        return repo.findById(id);
    }
}
