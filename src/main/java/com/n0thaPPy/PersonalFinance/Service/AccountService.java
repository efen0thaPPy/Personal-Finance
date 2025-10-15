package com.n0thaPPy.PersonalFinance.Service;

import com.n0thaPPy.PersonalFinance.Dtos.TransferDto;
import com.n0thaPPy.PersonalFinance.Exception.BalanceInsufficient;
import com.n0thaPPy.PersonalFinance.Exception.UserNotFound;
import com.n0thaPPy.PersonalFinance.Model.AccountModel;
import com.n0thaPPy.PersonalFinance.Model.User;
import com.n0thaPPy.PersonalFinance.Repo.AccountRepo;
import com.n0thaPPy.PersonalFinance.Repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final MapReactiveUserDetailsService reactiveUserDetailsService;
    private AccountRepo accountRepo;

    private UserRepo userRepo;

    public AccountService(MapReactiveUserDetailsService reactiveUserDetailsService) {
        this.reactiveUserDetailsService = reactiveUserDetailsService;
    }

    private boolean doesUserExist(String receiver)
   {
        User existingUser=userRepo.findByUsername(receiver);

        return existingUser!=null;



   }
   private User findUserByUsername(String username)
   {
      return userRepo.findByUsername(username);
   }
   private AccountModel findAccountByUser(User user)
   {
       return accountRepo.findAccountModelByUser(user);
   }
   public void processTransfer(TransferDto transferDto)
   {
       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

       String receiverUsername=transferDto.getReceiver().getUsername();

       if (doesUserExist(receiverUsername))
       {
           String currentUsername=authentication.getName();

          User currentUser=findUserByUsername(currentUsername);
          User receiverUser=findUserByUsername(receiverUsername);

          AccountModel currentAccount=findAccountByUser(currentUser);
           AccountModel receiverAccount=findAccountByUser(receiverUser);

           if(currentAccount.getBalance().compareTo(transferDto.getAmount())>=0)
           {
               currentAccount.setBalance(currentAccount.getBalance().subtract(transferDto.getAmount()));
               receiverAccount.setBalance(receiverAccount.getBalance().add(transferDto.getAmount()));
           }
           else
           {
               throw new BalanceInsufficient(currentAccount.getBalance(),transferDto.getAmount());
           }

       }
       else
           throw new UserNotFound(transferDto.getReceiver().getUsername());



   }

}
