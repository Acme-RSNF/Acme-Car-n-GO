
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ApplyForRepository;
import domain.ApplyFor;

@Component
@Transactional
public class StringToApplyForConverter implements Converter<String, ApplyFor> {

	@Autowired
	ApplyForRepository	applyForRepository;


	@Override
	public ApplyFor convert(final String text) {
		ApplyFor result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.applyForRepository.findOne(id);
			}
		} catch (final Exception oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
