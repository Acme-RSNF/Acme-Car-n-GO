
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Deal;

@Component
@Transactional
public class DealToStringConverter implements Converter<Deal, String> {

	@Override
	public String convert(final Deal deal) {
		String result;

		if (deal == null)
			result = null;
		else
			result = String.valueOf(deal.getId());

		return result;
	}

}
