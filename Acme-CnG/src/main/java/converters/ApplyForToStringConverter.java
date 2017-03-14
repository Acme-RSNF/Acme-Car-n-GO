
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ApplyFor;

@Component
@Transactional
public class ApplyForToStringConverter implements Converter<ApplyFor, String> {

	@Override
	public String convert(final ApplyFor applyFor) {
		String result;

		if (applyFor == null)
			result = null;
		else
			result = String.valueOf(applyFor.getId());

		return result;
	}

}
