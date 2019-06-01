package MyBusinessDb.business;

public enum ErrorCodeEnum {
	
	USER_WRONG(-200),
    PASSWORD_WRONG(-201),
    OPERATION_WRONG(-202),
	USER_EXISTED(-300),
	USER_NOT_EXISTED(-301),
	NO_PERMISSIONS(-302),
	TIME_WRONG(-303);
	
	private int code;

    private ErrorCodeEnum(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }
}
