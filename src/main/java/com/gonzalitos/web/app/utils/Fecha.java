package com.gonzalitos.web.app.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

public class Fecha {
	public static SimpleDateFormat FECHA_GUIONES = new SimpleDateFormat("dd-MM-yyyy", Locale.forLanguageTag("es"));
	public static SimpleDateFormat FECHA_GUIONES_INGLES = new SimpleDateFormat("yyyy-MM-dd",
			Locale.forLanguageTag("us"));

	public static SimpleDateFormat FECHA = new SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("es"));
	public static SimpleDateFormat FECHA_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.forLanguageTag("es"));
	public static SimpleDateFormat FECHA_CALENDAR = new SimpleDateFormat("yyyy-MM-dd HH:mm",
			Locale.forLanguageTag("es"));

	public static SimpleDateFormat FECHA_DIA = new SimpleDateFormat("EEEE dd", Locale.forLanguageTag("es"));

	private static final Fecha instance = new Fecha();

	public static NumberFormat FORMATO_INGLES;
	public static NumberFormat FORMATO_ESPANOL;

	static {
		FORMATO_INGLES = NumberFormat.getInstance(Locale.US);
		FORMATO_INGLES.setMinimumFractionDigits(2);
		FORMATO_INGLES.setMaximumFractionDigits(2);
		FORMATO_INGLES.setGroupingUsed(false);

		FORMATO_ESPANOL = NumberFormat.getInstance(Locale.US);
		FORMATO_ESPANOL.setMinimumFractionDigits(2);
		FORMATO_ESPANOL.setMaximumFractionDigits(2);
		FORMATO_ESPANOL.setGroupingUsed(true);
	}

	public static Fecha getInstance() {
		return instance;
	}

	public static Date parseFechaGuiones(String f) {
		try {
			return FECHA_GUIONES.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatFechaGuiones(Date f) {
		try {
			return FECHA_GUIONES.format(f);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseFechaGuionesIngles(String f) {
		try {
			return FECHA_GUIONES_INGLES.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatFechaGuionesIngles(Date f) {
		try {
			return FECHA_GUIONES_INGLES.format(f);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseFecha(String f) {
		try {
			return FECHA.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date parseHora(String f) {
		try {
			return FECHA_HORA.parse(f);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatFecha(Date f) {
		try {
			return FECHA.format(f);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatHora(Date f) {
		try {
			return FECHA_HORA.format(f);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date primerDiaAnio() {
		DateTime date = new DateTime().dayOfYear().withMinimumValue().withTimeAtStartOfDay();
		return date.toDate();
	}

	public static Date diaAnterior(Date date) {
		DateTime dateT = new DateTime(date).minusDays(1);
		return dateT.toDate();
	}

	public static Date diaPosterior(Date date) {
		DateTime dateT = new DateTime(date).plusDays(1);
		return dateT.toDate();
	}

	public static Date ultimoDiaAnio() {
		DateTime date = new DateTime().plusYears(1).dayOfYear().withMinimumValue().withTimeAtStartOfDay();
		return date.toDate();
	}

	public static String edad(Date dateBorn) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateBorn);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		LocalDate l1 = LocalDate.of(year, month, date);
		LocalDate now1 = LocalDate.now();
		Period diff1 = Period.between(l1, now1);
		return Integer.toString(diff1.getYears());
	}
}
