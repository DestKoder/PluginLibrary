package ru.dest.library.database.querybuilder.update;


import ru.dest.library.database.querybuilder.query.IFinishableQuery;

public interface IWhereQuery {

	IFinishableQuery where(String clause);

	IFinishableQuery whereEqual(String column, String value);

	IFinishableQuery whereEqual(String column, int value);
}
