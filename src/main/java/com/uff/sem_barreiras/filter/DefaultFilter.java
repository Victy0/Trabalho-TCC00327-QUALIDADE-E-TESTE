package com.uff.sem_barreiras.filter;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DefaultFilter<T> 
 extends Filter<T>
 {
    private static final String NOME = "nome";

	private static final String DESCRICAO = "descricao";

    private static final String ESTADO = "estado";

    private static final String CIDADE = "cidade";

	private static final String UF = "uf";

    public DefaultFilter(Map<String, String[]> params) {
        super(params);

        this.customParams = new HashMap<String, String[]>();

		if ( params.containsKey( NOME ) )
		{
			this.customParams.put( NOME, params.get( NOME ) );
			params.remove( NOME );
		}
		if ( params.containsKey( DESCRICAO ) )
		{
			this.customParams.put( DESCRICAO, params.get( DESCRICAO ) );
			params.remove( DESCRICAO );
		}

        if ( params.containsKey( ESTADO ) )
		{
			this.customParams.put( ESTADO, params.get( ESTADO ) );
			params.remove( ESTADO );
		}

        if ( params.containsKey( CIDADE ) )
		{
			this.customParams.put( CIDADE, params.get( CIDADE ) );
			params.remove( CIDADE );
		}

		if ( params.containsKey( UF ) )
		{
			this.customParams.put( UF, params.get( UF ) );
			params.remove( UF );
		}

    }

    @Override
	public Predicate toPredicate( final Root<T> root, final CriteriaQuery< ? > query, final CriteriaBuilder cb )
	{
		Predicate predicate = super.toPredicate( root, query, cb );
        final String[] nome = this.customParams.get( NOME );
		final String[] descricao = this.customParams.get( DESCRICAO );
        final String[] estado = this.customParams.get( ESTADO );
        final String[] cidade = this.customParams.get( CIDADE );
		final String[] uf = this.customParams.get( UF );

        if ( nome != null )
		{
			predicate = cb.and( predicate, root.get( NOME ).in( nome[0] ) );
		}

        if ( descricao != null )
		{
			predicate = cb.and( predicate, root.get( DESCRICAO ).in( descricao[0] ) );
		}

        if ( estado != null )
		{
			predicate = cb.and( predicate, root.get( ESTADO ).get( "id" ).in( estado[0] ) );
		}

        if ( cidade != null )
		{
			predicate = cb.and( predicate, root.get( CIDADE ).get( "id" ).in( cidade[0] ) );
		}

		if ( uf != null )
		{
			predicate = cb.and( predicate, root.get( UF ).get( "id" ).in( uf[0] ) );
		}

		return predicate;
	}

    private Map<String, String[]> customParams;
    
}
