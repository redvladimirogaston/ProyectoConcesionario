package dto.validators;

import java.util.LinkedList;
import java.util.List;

public class StringValidator {

	private List<Validator<String>> validators;
	private String value;

	public StringValidator(String value) {
		validators = new LinkedList<>();
		this.value = value;
	}

	public StringValidator number(String message) {
		validators.add(new Number(message));
		return this;
	}

	public StringValidator min(int min, String message) {
		validators.add(new MinSizeValidator(min, message));
		return this;
	}

	public StringValidator max(int max, String message) {
		validators.add(new MaxSizeValidator(max, message));
		return this;
	}

	public StringValidator notNull(String message) {
		validators.add(new NotNullValidator(message));
		return this;
	}

	public StringValidator email(String message) {
		validators.add(new EmailValidator(message));
		return this;
	}

	public StringValidator notBlank(String message) {
		validators.add(new NotBlankValidator(message));
		return this;
	}

	public StringValidator regex(String message, String regxp) {
		validators.add(new RegexValidator(message, regxp));
		return this;
	}

	public StringValidator notInLowerCase(String message) {
		validators.add(new NotInLowercaseValidator(message));
		return this;
	}

	public StringValidator notSpaces(String message) {
		validators.add(new NotSpacesValidator(message));
		return this;
	}

	public StringValidator positiveInteger(String message) {
		validators.add(new PositiveInteger(message));
		return this;
	}
	
	public StringValidator positiveDouble(String message) {
		validators.add(new PositiveDouble(message));
		return this;
	}
	
	public List<String> validate() {
		return new CompositeValidator<String>(validators).validate(value);
	}	
}