package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.TransactionsEntity;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomMyTradeRepository;
import com.tilf.troke.repository.CustomMyTradeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import java.util.List;

/**
 * Created by Shaun Cooper on 2015-10-26.
 */
@Controller
public class myTradesController {
    @Autowired
    private AuthUserContext authUserContext;

    @Autowired
    private CustomMyTradeRepository customMyTradeRepository;

    @RequestMapping(value = "/myTrades", method = RequestMethod.GET)
    public String getMyTrades(Model model) {
        UsersEntity currentUser = authUserContext.getUser();
        model.addAttribute("userActif", currentUser);
        //Get les transactions qui sont prêts à êtres répondues
        List<TransactionsEntity> pendingTrades = customMyTradeRepository.getPendingTransactionsByUserID(currentUser.getIduser());
        model.addAttribute("pendingTransactions", pendingTrades);
        //Get les transactions qui ont été envoyés ou déjà répondu
        List<TransactionsEntity> sentTrades = customMyTradeRepository.getSentTransactionsByUserID(currentUser.getIduser());
        model.addAttribute("sentTransactions", sentTrades);
        //Get les transactions qui ont été complétés
        List<TransactionsEntity> completedTrades = customMyTradeRepository.getCompletedTransactionsByUserID(currentUser.getIduser());
        model.addAttribute("completedTransactions", completedTrades);
        model.addAttribute("adrOpenTrade", "/openTrade?transactionID=");
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("userActif", currentUser);
            context.setVariable("pendingTransactions", pendingTrades);
            context.setVariable("sentTransactions", sentTrades);
            context.setVariable("completedTransactions", completedTrades);
            context.setVariable("adrOpenTrade", "/openTrade?transactionID=");
        }
        return "fragments/home/myTrades";
    }

}
