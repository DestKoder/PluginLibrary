package ru.dest.library.database.querybuilder.query;

public class FinishableQuery extends Query implements IFinishableQuery {

	public FinishableQuery(String query) {
		this.previousQuery = query;
	}

	@Override
	public String query() {
		return previousQuery;
	}

}
