package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.*;
import com.tilf.troke.repository.*;
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
import java.util.Calendar;

/**
 * Created by Emmanuel on 2015-10-21.
 */
@Controller
public class TransactionController {

    @Autowired
    private CustomObjectRepository customObjectRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private AuthUserContext authUserContext;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatmessageRepository chatmessageRepository;

    @Autowired
    private ObjectsTransactionRepository objectsTransactionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/startTrade", method = RequestMethod.GET)
    public String getUserFromItem(@RequestParam("itemID") int itemID, Model model) {
        model.addAttribute("startTradeOpponent", customUserRepository.getUserFromItem(itemID));
        model.addAttribute("currentItem", customObjectRepository.getObjectNameByItemID(itemID));
        model.addAttribute("currentItemID", itemID);
        model.addAttribute("inventory", customObjectRepository.getObjectsByUserID(itemID, customUserRepository.getUserFromItem(itemID).getIduser()));
        UsersEntity currentUser = authUserContext.getUser();
        model.addAttribute("userActif", currentUser);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("currentItemID", itemID);
            context.setVariable("startTradeOpponent", customUserRepository.getUserFromItem(itemID));
            context.setVariable("currentItem", customObjectRepository.getObjectNameByItemID(itemID));
            context.setVariable("inventory", customObjectRepository.getObjectsByUserID(itemID, customUserRepository.getUserFromItem(itemID).getIduser()));
            context.setVariable("userActif", currentUser);
        }
        return "fragments/home/startTrade";
    }

    @RequestMapping(value = "/addTrade", method = RequestMethod.POST)
    public String addNewTrade(@RequestParam("iduser1")String idUser1,
                              @RequestParam("iduser2")String idUser2,
                              @RequestParam("chatLog")String chatLog,
                              @RequestParam("tradeObjects")String tradeObjects)
    {
        TransactionsEntity newTransaction = new TransactionsEntity();
        newTransaction.setIduser1(idUser1);
        newTransaction.setIduser2(idUser2);
        newTransaction.setDatetransaction(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        newTransaction.setTurn(idUser2);
        newTransaction.setIscompleted("N");
        transactionRepository.save(newTransaction);

        String queryIdTransaction = "select t.idtransaction from TransactionsEntity t ORDER  BY t.idtransaction Desc";
        Query queryObject = entityManager.createQuery(queryIdTransaction).setMaxResults(1);
        int idTransaction = (Integer)queryObject.getSingleResult();

        ChatEntity newChat = new ChatEntity();
        newChat.setIdtransaction(idTransaction);
        chatRepository.save(newChat);

        String queryIdChat = "select c.idchat from ChatEntity c ORDER  BY c.idchat Desc";
        Query queryObject2 = entityManager.createQuery(queryIdChat).setMaxResults(1);
        int idChat = (Integer)queryObject2.getSingleResult();

        ChatmessageEntity newChatMessage = new ChatmessageEntity();
        newChatMessage.setIdchat(idChat);
        newChatMessage.setDateTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        newChatMessage.setMsg(chatLog);
        newChatMessage.setIsread("T");
        chatmessageRepository.save(newChatMessage);

        ObjecttransactionEntity addTransactionsObjects = new ObjecttransactionEntity();
        String[] objectIDs = tradeObjects.split(";");

        for(int i = 0; i < objectIDs.length; i++)
        {
            addTransactionsObjects.setIdobject(Integer.parseInt(objectIDs[i]));
            addTransactionsObjects.setIdtransaction(idTransaction);
            objectsTransactionRepository.save(addTransactionsObjects);
        }

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
        }
        return "fragments/home/myTrades";
    }

    // Boutton envoyer l'offre
    /*@RequestMapping(value = "/sendTrade", method = RequestMethod.GET)
    public String getItemByIdObject(@RequestParam("idObject") int idobject, Model model) {
        model.addAttribute("singleobject", customObjectRepository.getObjectEntityByIdObject(idobject));
        model.addAttribute("adrItem", "/item?idObject=");
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("singleobject", customObjectRepository.getObjectEntityByIdObject(idobject));
            context.setVariable("adrItem", "/item?objectName=");
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade", "/startTrade?itemID=");
        }

        return "fragments/home/search";
    }*/
}
