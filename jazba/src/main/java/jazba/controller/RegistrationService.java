package jazba.controller;

import java.util.ArrayList;
import java.util.List;

import jazba.dao.RegistrationDAO;

public class RegistrationService {
    private final RegistrationDAO dao = new RegistrationDAO();
    private final List<RegistrationObserver> observers = new ArrayList<>();

    public boolean registerUser(String username, String email, String password, int height, int weight, int age, String fitnessLevel) {
        boolean success = dao.registerUser(username, email, password, height, weight, age, fitnessLevel);
        if (success) {
            notifyObservers(username, email);
        }
        return success;
    }

    public void addObserver(RegistrationObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(RegistrationObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String username, String email) {
        for (RegistrationObserver observer : observers) {
            observer.onRegistrationSuccess(username, email);
        }
    }
}