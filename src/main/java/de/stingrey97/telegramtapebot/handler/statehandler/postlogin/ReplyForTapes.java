package de.stingrey97.telegramtapebot.handler.statehandler.postlogin;

import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.model.Tape;
import de.stingrey97.telegramtapebot.service.TapeService;

import java.util.List;

public class ReplyForTapes extends AbstractReplyTapesHandler {
    @Override
    protected List<Tape> getTapes(TapeService tapeService, String subject) throws DatabaseException {
        return tapeService.tapesFor(subject);
    }
}