package approdo.exception;

public class MerceAlreadyExistsException extends  Exception{

    public MerceAlreadyExistsException(){
        System.out.println("Stai inserendo una merce con codice uguale ad una merce già presente");
    }

}
