package YahiaLakrikba.GestioneEventi.exeptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Long id) {
        super("Entity with id " + id + " not found");
    }
}

