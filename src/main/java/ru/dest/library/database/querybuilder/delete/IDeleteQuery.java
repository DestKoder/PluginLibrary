package ru.dest.library.database.querybuilder.delete;

import ru.dest.library.database.querybuilder.query.IFinishableQuery;

public interface IDeleteQuery {

	IFinishableQuery where(String clause);

	IFinishableQuery whereEquals(String column, String value);

	IFinishableQuery whereEquals(String column, int value);
}
