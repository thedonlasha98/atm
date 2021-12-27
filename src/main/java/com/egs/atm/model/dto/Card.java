package com.egs.atm.model.dto;

import com.egs.atm.exception.EGSException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Component
@Scope("session")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card implements Serializable {

    Long cardId;

    boolean isAuthorized;

    public static Card getAuthCard(HttpSession session, boolean needAuth){
        Card card = (Card) session.getAttribute("card");
        if (card == null || needAuth && !card.isAuthorized()){
            throw new EGSException("Card is not Authorized!", HttpStatus.UNAUTHORIZED);
        }

        return card;
    }
}
