//package com.uit.demo.config.database.dialect;
//
//import org.hibernate.dialect.Oracle10gDialect;
//import org.hibernate.dialect.function.SQLFunctionTemplate;
//import org.hibernate.type.StandardBasicTypes;
//
//public class CustomOracle10gDialect extends Oracle10gDialect {
//	public CustomOracle10gDialect() {
//	     super();
//	     registerFunction("LISTAGG", new SQLFunctionTemplate(StandardBasicTypes.STRING,"LISTAGG(?1,' / ') WITHIN GROUP(ORDER BY ?1)"));
//	    }
//}
