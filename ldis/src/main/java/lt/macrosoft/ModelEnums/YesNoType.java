package lt.macrosoft.ModelEnums;

public enum YesNoType {

	T(true),
	N(false);
	
	private boolean value;
	
	private YesNoType(boolean value){
		this.value = value;
	}
	
	public boolean getBoolean(){
		return value;
	}
	
	public static YesNoType toEnumValue(String value){
		if(YesNoType.T.name().equals(value)){
			return YesNoType.T;
		}
		return YesNoType.N;
	}
	
	public static YesNoType toEnumValue(boolean value){
		if(value){
			return YesNoType.T;
		}
		return YesNoType.N;
	}
}
