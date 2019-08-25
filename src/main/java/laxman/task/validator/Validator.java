package laxman.task.validator;

public class Validator {

	public Boolean isNull(Object instance) {
		return null == instance;
	}

	public Boolean isNumeric(String stringNumbers) {
		return stringNumbers.matches("-?\\d+(\\.\\d+)?");
	}

	public Boolean validateId(String id) {
		if (!isNull(id) && !id.isEmpty()) {
			return isNumeric(id);
		}
		return Boolean.FALSE;
	}
}
