package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.TransactionsEntity;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomMyTradeRepository;
import com.tilf.troke.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private TransactionRepository transactionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/myTrades", method = RequestMethod.GET)
    public String getMyTrades(Model model,
                              HttpSession session) {
        UsersEntity currentUser = authUserContext.getUser();
        if (currentUser != null) {
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
        } else {
            session.removeAttribute("error");
            return "redirect:#openModalConnexion";
        }
    }
    @RequestMapping(value = "/deleteTrade", method = RequestMethod.POST)
    public String deleteTrade(@RequestParam("tradeID")Integer tradeID) {
        String queryTrade = "select t from TransactionsEntity t where t.idtransaction = :idTrade";
        Query queryObject = entityManager.createQuery(queryTrade);
        queryObject.setParameter("idTrade", tradeID);
        TransactionsEntity deleteTrade = (TransactionsEntity)queryObject.getSingleResult();
        transactionRepository.delete(deleteTrade);
        return "redirect:/myTrades";
    }
}
