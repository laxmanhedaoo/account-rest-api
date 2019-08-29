package laxman.task.enums;

public enum ConstantEnum {

	ACCOUNTS_KEY("accounts"), TRANSFER_KEY("transfers"), HOST_PORT("8090"), HOST("localhost");
	

	private ConstantEnum(String val) {
		this.val = val;
	}

	private String val;

	public String val() {
		return val;
	}
}
