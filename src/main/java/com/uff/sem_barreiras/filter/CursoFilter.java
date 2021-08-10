package com.uff.sem_barreiras.filter;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CursoFilter<T> 
    extends Filter<T>
{
    private static final String NOME = "nome";

	private static final String PRECO = "preco";

	public CursoFilter( Map<String, String[]> params )
	{
        super( params );
		this.customParams = new HashMap<String, String[]>();

		if ( params.containsKey( NOME ) )
		{
			this.customParams.put( NOME, params.get( NOME ) );
			params.remove( NOME );
		}
		if ( params.containsKey( PRECO ) )
		{
			this.customParams.put( PRECO, params.get( PRECO ) );
			params.remove( PRECO );
		}
	}

	@Override
	public Predicate toPredicate( final Root<T> root, final CriteriaQuery< ? > query, final CriteriaBuilder cb )
	{
		Predicate predicate = super.toPredicate( root, query, cb );
        final String[] nome = this.customParams.get( NOME );
		final String[] preco = this.customParams.get( PRECO );

        if ( nome != null )
		{
			predicate = cb.and( predicate, root.get( NOME ).get( "id" ).in( nome[0] ) );
		}

        if ( preco != null )
		{
			predicate = cb.and( predicate, cb.lessThanOrEqualTo(root.get( PRECO ), preco[0] ) );
		}
        
		return predicate;
	}

	private Map<String, String[]> customParams;
}
