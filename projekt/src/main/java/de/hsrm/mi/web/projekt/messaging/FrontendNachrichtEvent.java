package de.hsrm.mi.web.projekt.messaging;


public class FrontendNachrichtEvent {
    public enum Nachrichtentyp {
        TOUR
    }
    
    public enum Operation {
        CREATE,
        UPDATE,
        DELETE
    }
    private Nachrichtentyp typ;
    private long id;
    private Operation operation;

    public FrontendNachrichtEvent(Nachrichtentyp typ, long id, Operation operation) {
        this.typ = typ;
        this.id = id;
        this.operation = operation;
    }

    public Nachrichtentyp getTyp() {
        return typ;
    }

    public void setTyp(Nachrichtentyp typ) {
        this.typ = typ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

}
