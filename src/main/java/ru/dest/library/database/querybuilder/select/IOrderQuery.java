package ru.dest.library.database.querybuilder.select;

import ru.dest.library.database.querybuilder.query.IFinishableQuery;

public interface IOrderQuery extends IFinishableQuery {

	/**
	 * Order query by given column(s)
	 * @param firstColumn
	 * @param otherColumns
	 * @return
	 */
	IFinishableQuery orderBy(String firstColumn, String... otherColumns);

	/**
	 * Order query descending by given column(s)
	 * @param firstColumn
	 * @param otherColumns
	 * @return
	 */
	IFinishableQuery orderByDescending(String firstColumn, String... otherColumns);

}
