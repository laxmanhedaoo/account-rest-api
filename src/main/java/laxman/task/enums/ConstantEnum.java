package laxman.task.enums;

public enum ConstantEnum {

	ACCOUNTS_KEY("accounts"), TRANSFER_KEY("transfers"), SERVER_PORT("8080"), SERVER_IP("0.0.0.0");

	private ConstantEnum(String val) {
		this.val = val;
	}

	private String val;

	public String val() {
		return val;
	}
}
