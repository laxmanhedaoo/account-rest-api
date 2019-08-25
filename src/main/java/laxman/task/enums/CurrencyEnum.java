package laxman.task.enums;

public enum CurrencyEnum {

	USD("$"), EUR("€");

	private CurrencyEnum(String currency) {
		this.currency = currency;
	}

	private String currency;

	public String currency() {
		return this.currency;
	}

	public String currencyCode() {
		return this.name();
	}
}
