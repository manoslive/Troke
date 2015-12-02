package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.entity.*;
import com.tilf.troke.repository.*;
import com.tilf.troke.service.SmtpMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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

    @Autowired
    private CustomChatMessageRepository customChatMessageRepository;

    @Autowired
    private TransactionMoneyRepository transactionMoneyRepository;

    @Autowired
    private CustomTransactionMoneyRepository customTransactionMoneyRepository;

    @Autowired
    private SmtpMailSender smtpMailSender;

    @PersistenceContext
    private EntityManager entityManager;

    //StartTrade - lorsqu'on click sur un item de la recherche et on veut commencer une transaction
    @RequestMapping(value = "/startTrade", method = RequestMethod.GET)
    public String openNewTrade(@RequestParam("itemID") int itemID, Model model, HttpSession session) {
        UsersEntity currentUser = authUserContext.getUser();
        if(currentUser != null) {
            UsersEntity opponentID = customUserRepository.getUserFromItem(itemID);
            model.addAttribute("startTradeOpponent", opponentID);
            model.addAttribute("currentItem", customObjectRepository.getCustomObjectImageEntityByIdObject(itemID));
            model.addAttribute("currentItemID", itemID);
            model.addAttribute("inventory", customObjectRepository.getObjectsByUserID(itemID, customUserRepository.getUserFromItem(itemID).getIduser()));
            model.addAttribute("userActif", currentUser);
            // TODO THYMELEAF HACK
            if (false) {
                WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
                context.setVariable("currentItemID", itemID);
                context.setVariable("startTradeOpponent", customUserRepository.getUserFromItem(itemID));
                context.setVariable("currentItem", customObjectRepository.getCustomObjectImageEntityByIdObject(itemID));
                context.setVariable("inventory", customObjectRepository.getObjectsByUserID(itemID, customUserRepository.getUserFromItem(itemID).getIduser()));
                context.setVariable("userActif", currentUser);
            }
            return "fragments/home/startTrade";
        }else{
            session.removeAttribute("error");
            return "redirect:#openModalConnexion";
        }
    }

    //OpenTrade - Lorsqu'on click sur un échange de la page myTrades pour y répondre
    @RequestMapping(value = "/openTrade", method = RequestMethod.GET)
    public String openTrade(@RequestParam("transactionID") int tradeID, Model model, HttpSession session) {
        UsersEntity currentUser = authUserContext.getUser();
        UsersEntity opponent = customUserRepository.findOpponentUser(tradeID, currentUser.getIduser());
        String opponentID = opponent.getIduser();
        if (currentUser != null) {
            model.addAttribute("userActif", currentUser);
            model.addAttribute("opponent", opponent);
            model.addAttribute("transactionID", tradeID);

            //Get les 2 items d'argent
            model.addAttribute("UserMoneyItem", customTransactionMoneyRepository.getTransactionMoney(tradeID, currentUser.getIduser()));
            model.addAttribute("OpponentMoneyItem", customTransactionMoneyRepository.getTransactionMoney(tradeID, opponentID));

            //Get les message du Chat
            model.addAttribute("ChatLog", customChatMessageRepository.getChatLogByTransactionID(tradeID));

            //Get les objets+images des items dans l'inventaire du trade des 2 users
            model.addAttribute("listImageUserInventory", customObjectRepository.getListObjectTradeInventory(tradeID, currentUser.getIduser()));
            model.addAttribute("listImageOpponentInventory", customObjectRepository.getListObjectTradeInventory(tradeID, opponentID));

            //Get les objets+images des items en trade des 2 users
            model.addAttribute("listImageUserTradeZone", customObjectRepository.getTradeObjects(tradeID, currentUser.getIduser()));
            model.addAttribute("listImageOpponentTradeZone", customObjectRepository.getTradeObjects(tradeID, opponentID));
        } else {
            session.removeAttribute("error");
            return "redirect:#openModalConnexion";
        }
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("userActif", currentUser);
            context.setVariable("ChatLog", customChatMessageRepository.getChatLogByTransactionID(tradeID));
            context.setVariable("UserMoneyItem", customTransactionMoneyRepository.getTransactionMoney(tradeID, currentUser.getIduser()));
            context.setVariable("OpponentMoneyItem", customTransactionMoneyRepository.getTransactionMoney(tradeID, opponentID));
            context.setVariable("opponent", opponent);
            context.setVariable("transactionID", tradeID);
            context.setVariable("listImageUserInventory", customObjectRepository.getListObjectTradeInventory(tradeID, currentUser.getIduser()));
            context.setVariable("listImageOpponentInventory", customObjectRepository.getListObjectTradeInventory(tradeID, opponentID));
            context.setVariable("listImageUserTradeZone", customObjectRepository.getTradeObjects(tradeID, currentUser.getIduser()));
            context.setVariable("listImageOpponentTradeZone", customObjectRepository.getTradeObjects(tradeID, opponentID));
        }
        return "fragments/home/trade";
    }
    //addTrade - Ajout d'un trade lorsqu'on envoie un échange de la page startTrade
    //Ajout à Transaction
    //Ajout à Chat
    //Ajout à ChatMessage
    //Ajout à ObjectTransaction
    //Ajout à transactionMoney
    @RequestMapping(value = "/addTrade", method = RequestMethod.POST)
    public String addNewTrade(@RequestParam("iduser1")String idUser1,
                              @RequestParam("iduser2")String idUser2,
                              @RequestParam("chatLog")String chatLog,
                              @RequestParam("tradeObjects")String tradeObjects,
                              @RequestParam("newTradeMoneyValue")String opponentMoney,
                              HttpSession session)
    {

        UsersEntity currentUser = authUserContext.getUser();
        if (currentUser != null) {
            //Set une nouvelle transaction avec ses informations
            TransactionsEntity newTransaction = new TransactionsEntity();
            newTransaction.setIduser1(idUser1);
            newTransaction.setIduser2(idUser2);
            newTransaction.setDatetransaction(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            newTransaction.setTurn(idUser2);
            newTransaction.setIscompleted("F");
            transactionRepository.save(newTransaction);

            //Get le id de la transaction créée ^^
            String queryIdTransaction = "select t.idtransaction from TransactionsEntity t ORDER  BY t.idtransaction Desc";
            Query queryObject = entityManager.createQuery(queryIdTransaction).setMaxResults(1);
            int idTransaction = (Integer)queryObject.getSingleResult();

            //Nouveau chat
            ChatEntity newChat = new ChatEntity();
            newChat.setIdtransaction(idTransaction);
            chatRepository.save(newChat);

            //Get le id du chat créée ^^
            String queryIdChat = "select c.idchat from ChatEntity c ORDER  BY c.idchat Desc";
            Query queryObject2 = entityManager.createQuery(queryIdChat).setMaxResults(1);
            int idChat = (Integer)queryObject2.getSingleResult();

            //Set les messages du Chat dans le chat créée^^
            ChatmessageEntity newChatMessage = new ChatmessageEntity();
            newChatMessage.setIdchat(idChat);
            newChatMessage.setDateTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            newChatMessage.setMsg(chatLog);
            newChatMessage.setIsread("T");
            chatmessageRepository.save(newChatMessage);

            //Met les objets recu dans une liste, les objets sont reçu concaténé séparé pas des ';'
            ObjecttransactionEntity addTransactionsObjects = new ObjecttransactionEntity();
            String[] objectIDs = tradeObjects.split(";");

            //Ajout de chacun des items dans la bd
            for(int i = 0; i < objectIDs.length; i++)
            {
                addTransactionsObjects.setIdobject(Integer.parseInt(objectIDs[i]));
                addTransactionsObjects.setIdtransaction(idTransaction);
                objectsTransactionRepository.save(addTransactionsObjects);
            }

            //Ajout de l'item d'Argent de l'opposant
            TransactionmoneyEntity addTransactionMoney = new TransactionmoneyEntity();
            addTransactionMoney.setIdtransaction(idTransaction);
            addTransactionMoney.setIduser(idUser2);
            addTransactionMoney.setValue(Integer.parseInt(opponentMoney));
            transactionMoneyRepository.save(addTransactionMoney);

            //Ajout de l'item d'Argent du User à 0
            TransactionmoneyEntity addUserTransactionMoney = new TransactionmoneyEntity();
            addUserTransactionMoney.setIdtransaction(idTransaction);
            addUserTransactionMoney.setIduser(idUser1);
            addUserTransactionMoney.setValue(0);
            transactionMoneyRepository.save(addUserTransactionMoney);
        } else {
            session.removeAttribute("error");
            return "redirect:#openModalConnexion";
        }
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
        }
        return "redirect:/myTrades";
    }

    //update - update d'un trade lorsqu'on envoie une contre-offre
    //update à Transaction
    //update à ChatMessage
    //update à ObjectTransaction
    //update à transactionMoney
    @RequestMapping(value = "/updateTrade", method = RequestMethod.POST)
    public String updateTrade(@RequestParam("idTransaction")int transactionID,
                                @RequestParam("currentUser")String currentUser,
                                @RequestParam("iduser2")String idUser2,
                                @RequestParam("chatID")int chatID,
                                @RequestParam("chatLog")String chatLog,
                                @RequestParam("tradeObjects")String tradeObjects,
                                @RequestParam("tradeState")String tradeState,
                                @RequestParam("userMoneyValue")String userMoney,
                                @RequestParam("opponentMoneyValue")String opponentMoney,
                                HttpSession session) throws MessagingException
    {

        UsersEntity currentLoggedUser = authUserContext.getUser();
        if (currentLoggedUser != null) {
            TransactionsEntity updateTransaction = new TransactionsEntity();
            updateTransaction.setIdtransaction(transactionID);
            updateTransaction.setIduser1(currentUser);
            updateTransaction.setIduser2(idUser2);
            updateTransaction.setDatetransaction(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            updateTransaction.setTurn(idUser2);
            updateTransaction.setIscompleted(tradeState);
            transactionRepository.save(updateTransaction);

            String queryIdChat = "select c.idchatmessage from ChatmessageEntity c where c.idchat = :idChat";
            Query queryObject = entityManager.createQuery(queryIdChat);
            queryObject.setParameter("idChat", chatID);
            int idChatMessage = (Integer)queryObject.getSingleResult();

            ChatmessageEntity updateChatMessage = new ChatmessageEntity();
            updateChatMessage.setIdchatmessage(idChatMessage);
            updateChatMessage.setIdchat(chatID);
            updateChatMessage.setDateTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            updateChatMessage.setMsg(chatLog);
            updateChatMessage.setIsread("T");
            chatmessageRepository.save(updateChatMessage);

            Query queryObject2 = entityManager.createQuery("select o from ObjecttransactionEntity o where o.idtransaction = :idTransaction");
            queryObject2.setParameter("idTransaction",transactionID);
            List<ObjecttransactionEntity> listObjets = queryObject2.getResultList();

            for(Iterator<ObjecttransactionEntity> i = listObjets.iterator(); i.hasNext(); ) {
                objectsTransactionRepository.delete(i.next());
            }

            ObjecttransactionEntity updateTransactionsObjects = new ObjecttransactionEntity();
            String[] objectIDs = tradeObjects.split(";");

            for(int i = 0; i < objectIDs.length; i++)
            {
                updateTransactionsObjects.setIdobject(Integer.parseInt(objectIDs[i]));
                updateTransactionsObjects.setIdtransaction(transactionID);
                objectsTransactionRepository.save(updateTransactionsObjects);
            }

            if(userMoney.equals("")){
                userMoney = "0";
            }
            if(opponentMoney.equals("")){
                opponentMoney = "0";
            }
            //Ajout de l'item d'Argent du User
            TransactionmoneyEntity updateTransactionMoneyUser = new TransactionmoneyEntity();
            updateTransactionMoneyUser.setIdtransaction(transactionID);
            updateTransactionMoneyUser.setIduser(currentUser);
            updateTransactionMoneyUser.setValue(Integer.parseInt(userMoney));
            transactionMoneyRepository.save(updateTransactionMoneyUser);

            //Ajout de l'item d'Argent de l'Opposant
            TransactionmoneyEntity updateTransactionMoneyOpposant = new TransactionmoneyEntity();
            updateTransactionMoneyOpposant.setIdtransaction(transactionID);
            updateTransactionMoneyOpposant.setIduser(idUser2);
            updateTransactionMoneyOpposant.setValue(Integer.parseInt(opponentMoney));
            transactionMoneyRepository.save(updateTransactionMoneyOpposant);

            if(updateTransaction.getIscompleted().equals("T")){
                //
                UsersEntity user1 = customUserRepository.findUserById(currentUser);
                UsersEntity user2 = customUserRepository.findUserById(idUser2);
                List<CustomObjetImageEntity> listUser1 = customObjectRepository.getTradeObjects(transactionID, currentUser);
                List<CustomObjetImageEntity> listUser2 = customObjectRepository.getTradeObjects(transactionID, idUser2);
                // Message body du user1
                String bodyUser1 = "";
                bodyUser1 += "<a href=\"http://troke.me\"><img src=\"http://imgh.us/trok_fini.jpg\"/></a><br/>" +
                        "Bonjour " + user1.getFirstname() + " " + user1.getLastname() +
                        ", <br/>" +
                        " Votre échange est maintenant prête à être complétée.<br/> " +
                        "Voici la liste de vos items mis en échange: <br/>" +
                        "<ul>";
                for(CustomObjetImageEntity obj : listUser1){
                    bodyUser1 += "<li>" + obj.getNameObject() + "</li>";
                }
                bodyUser1 += "</ul>" +
                "Contre :<br/><ul>";
                for(CustomObjetImageEntity obj : listUser2){
                    bodyUser1 += "<li>" + obj.getNameObject() + "</li>";
                }
                bodyUser1 += "</ul><br/>" +
                        " Vous devez contacter " + user2.getFirstname() + " " + user2.getFirstname() + "<br/>" +
                        " au " + user2.getTelephone() +  "<br/>" +
                        " ou au " + user2.getEmail() + ". <br/>"+
                        " <a href='http://www.troke.me'>Allez sur troké</a>";

                smtpMailSender.send(user1.getEmail(), "Trok-é : Trok #" + transactionID + "complété.",bodyUser1); // Envoie du courriel au user1

                // Message body du user2
                String bodyUser2 = "";
                bodyUser2 += "<a href=\"http://troke.me\"><img src=\"http://imgh.us/trok_fini.jpg\"/></a><br/>" +
                        "Bonjour " + user2.getFirstname() + " " + user2.getLastname() +
                        ", <br/>" +
                        " Votre échange est maintenant prête à être complétée.<br/> " +
                        "Voici la liste de vos items mis en échange: <br/>" +
                        "<ul>";
                for(CustomObjetImageEntity obj : listUser2){
                    bodyUser2 += "<li>" + obj.getNameObject() + "</li>";
                }
                bodyUser2 += "</ul><br/>" +
                        "Contre :<br/><ul>";
                for(CustomObjetImageEntity obj : listUser1){
                    bodyUser2 += "<li>" + obj.getNameObject() + "</li>";
                }
                bodyUser2 += "</ul>" +
                        " Vous devez contacter " + user1.getFirstname() + " " + user1.getFirstname() +  "<br/>" +
                        " au " + user1.getTelephone() +  "<br/>" +
                        " ou au " + user1.getEmail() + ". <br/>"+
                        " <a href='http://www.troke.me'>Allez sur troké</a>";

                smtpMailSender.send(user2.getEmail(), "Trok-é : Trok #" + transactionID + "complété.", bodyUser2);
            }
        } else {
            session.removeAttribute("error");
            return "redirect:#openModalConnexion";
        }
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
        }
        return "redirect:/myTrades";
    }
}
