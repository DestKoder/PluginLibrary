package ru.dest.library.database.querybuilder;

import ru.dest.library.database.querybuilder.delete.DeleteQuery;
import ru.dest.library.database.querybuilder.delete.IDeleteQuery;
import ru.dest.library.database.querybuilder.insert.IInsertQuery;
import ru.dest.library.database.querybuilder.insert.InsertQuery;
import ru.dest.library.database.querybuilder.select.ISelectQuery;
import ru.dest.library.database.querybuilder.select.SelectQuery;
import ru.dest.library.database.querybuilder.update.IUpdateQuery;
import ru.dest.library.database.querybuilder.update.UpdateQuery;
import ru.dest.library.utils.StringHelper;

public class SqlQueryBuilder {

	/**
	 * Select given columns. If not columns are passed, all columns will be retrieved
	 * @param columns
	 * @return
	 */
	public ISelectQuery select(String... columns){
		String columnQuery = columns.length == 0 ? "*" : StringHelper.join(", ", columns);
		String formattedQuery = String.format("SELECT %s", columnQuery);
		return new SelectQuery(formattedQuery);
	}

	/**
	 * Select distinct given columns. If not columns are passed, all columns will be retrieved
	 * @param columns
	 * @return
	 */
	public ISelectQuery selectDistinct(String... columns){
		String columnQuery = columns.length == 0 ? "*" : StringHelper.join(", ", columns);
		String formattedQuery = String.format("SELECT DISTINCT %s", columnQuery);
		return new SelectQuery(formattedQuery);
	}

	public IUpdateQuery update(String table){
		String formattedQuery = String.format("UPDATE %s", table);
		return new UpdateQuery(formattedQuery);
	}

	public IDeleteQuery delete(String table){
		String formattedQuery = String.format("DELETE FROM %s", table);
		return new DeleteQuery(formattedQuery);
	}

	public IInsertQuery insertInto(String table){
		String formattedQuery = String.format("INSERT INTO %s", table);
		return new InsertQuery(formattedQuery);
	}
}
