package de.stingrey97.telegramtapebot.service;

import de.stingrey97.telegramtapebot.dao.DAOFactory;
import de.stingrey97.telegramtapebot.dao.TapeDAO;
import de.stingrey97.telegramtapebot.exceptions.DatabaseException;
import de.stingrey97.telegramtapebot.model.Tape;

import java.util.List;

public class TapeService {

    private final TapeDAO tapeDAO = DAOFactory.getTapeDAO();

    TapeService() {
    }

    public int addTape(String title, String byUsername, String forUsername) throws DatabaseException {
        return tapeDAO.addTape(title, byUsername, forUsername);
    }

    public Tape getTapeById(int id) throws DatabaseException {
        return tapeDAO.getTapeById(id);
    }

    public List<Tape> getAll() throws DatabaseException {
        return tapeDAO.getAllTapes();
    }

    public List<Tape> tapesFor(String username) throws DatabaseException {
        return tapeDAO.getTapesForUser(username);
    }

    public List<Tape> tapesBy(String username) throws DatabaseException {
        return tapeDAO.getTapesByUser(username);
    }

    public Tape getLast() throws DatabaseException {
        return tapeDAO.getLastAddedTape();
    }

    public boolean deleteTape(int id) throws DatabaseException {
        return tapeDAO.markTapeAsDeleted(id) == 1;
    }

    public boolean tapeExists(int id) throws DatabaseException {
        Tape tape = tapeDAO.getTapeById(id);
        if (tape == null) return false;
        return !tape.isDeleted();
    }
}
