package ru.dest.library.database.querybuilder.insert;

public interface IInsertQuery {

	IInsertRestOfValuesQuery value(String column, String value);

	IInsertRestOfValuesQuery value(String column, int value);

	IInsertRestOfValuesQuery value(String column, double value);

	IInsertRestOfValuesQuery value(String column, boolean value);

}
