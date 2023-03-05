package ru.dest.library.database.querybuilder.select;

import ru.dest.library.database.querybuilder.select.JoinMethods.IInnerJoinMethods;
import ru.dest.library.database.querybuilder.select.JoinMethods.ILeftJoinMethods;
import ru.dest.library.database.querybuilder.select.JoinMethods.IRightJoinMethods;

public interface IJoinQuery extends
		IInnerJoinMethods,
		ILeftJoinMethods,
		IRightJoinMethods,
	IWhereQuery {

}
