package com.example.starterexcel.converters;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author gengweiweng
 * @time 2021/7/27
 * @desc
 */
public class LocalDateTimeStringConverter implements Converter<LocalDateTime> {


	private static final String MINUS = "-";

	@Override
	public Class supportJavaTypeKey() {
		return LocalDateTime.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public LocalDateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                           GlobalConfiguration globalConfiguration) throws ParseException {
		String stringValue = cellData.getStringValue();
		String pattern;
		if(stringValue != null) {
			try {
				if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
					pattern = switchDateFormat(stringValue);
				} else {
					pattern = contentProperty.getDateTimeFormatProperty().getFormat();
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
				return LocalDateTime.parse(cellData.getStringValue(), formatter);
			}catch (Exception e){
				return null;
			}
		}
		return null;
	}

	@Override
	public CellData<String> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
		if(value != null) {
			String pattern;
			if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
				pattern = DateUtils.DATE_FORMAT_19;
			} else {
				pattern = contentProperty.getDateTimeFormatProperty().getFormat();
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
			return new CellData<>(value.format(formatter));
		}else {
			return new CellData<>();
		}
	}

	/**
	 * switch date format
	 * @param dateString dateString
	 * @return pattern
	 */
	private static String switchDateFormat(String dateString) {
		int length = dateString.length();
		switch (length) {
		case 19:
			if (dateString.contains(MINUS)) {
				return DateUtils.DATE_FORMAT_19;
			}
			else {
				return DateUtils.DATE_FORMAT_19_FORWARD_SLASH;
			}
		case 17:
			return DateUtils.DATE_FORMAT_17;
		case 14:
			return DateUtils.DATE_FORMAT_14;
		case 10:
			return DateUtils.DATE_FORMAT_10;
		default:
			throw new IllegalArgumentException("can not find date format for：" + dateString);
		}
	}
}
