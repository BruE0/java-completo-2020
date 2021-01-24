package models.exceptions;

public class IllegalWithdrawalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalWithdrawalException(String msg) {
        super(msg);
    }

}
