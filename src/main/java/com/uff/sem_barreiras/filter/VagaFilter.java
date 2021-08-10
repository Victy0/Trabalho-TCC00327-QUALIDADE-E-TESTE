package com.uff.sem_barreiras.filter;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.uff.sem_barreiras.model.Deficiencia;
import com.uff.sem_barreiras.model.Vaga;


public class VagaFilter<T>
	extends Filter<T>
{

    private static final String EMPRESA = "empresa";

	private static final String ESTADO = "estado";

	private static final String AREA = "area";

	private static final String ESCOLARIDADE = "escolaridade";

	private static final String RESUMO = "pesq";

    private static final String REMUNERACAO = "remuneracao";

    private static final String DEFICIENCIA = "deficiencia";


	public VagaFilter( Map<String, String[]> params )
	{
        super( params );
		this.customParams = new HashMap<String, String[]>();

		if ( params.containsKey( EMPRESA ) )
		{
			this.customParams.put( EMPRESA, params.get( EMPRESA ) );
			params.remove( EMPRESA );
		}
		if ( params.containsKey( ESTADO ) )
		{
			this.customParams.put( ESTADO, params.get( ESTADO ) );
			params.remove( ESTADO );
		}
		if ( params.containsKey( AREA ) )
		{
			this.customParams.put( AREA, params.get( AREA ) );
			params.remove( AREA );
		}
		if ( params.containsKey( ESCOLARIDADE ) )
		{
			this.customParams.put( ESCOLARIDADE, params.get( ESCOLARIDADE ) );
			params.remove( ESCOLARIDADE );
		}
		if ( params.containsKey( RESUMO ) )
		{
			this.customParams.put( RESUMO, params.get( RESUMO ) );
			params.remove( RESUMO );
		}
        if ( params.containsKey( REMUNERACAO ) )
		{
			this.customParams.put( REMUNERACAO, params.get( REMUNERACAO ) );
			params.remove( REMUNERACAO );
		}
        if ( params.containsKey( DEFICIENCIA ) )
		{
			this.customParams.put( DEFICIENCIA, params.get( DEFICIENCIA ) );
			params.remove( DEFICIENCIA );
		}
	}

	@Override
	public Predicate toPredicate( final Root<T> root, final CriteriaQuery< ? > query, final CriteriaBuilder cb )
	{
		Predicate predicate = super.toPredicate( root, query, cb );
        final String[] empresa = this.customParams.get( EMPRESA );
		final String[] estado = this.customParams.get( ESTADO );
		final String[] area = this.customParams.get( AREA );
		final String[] escolaridade = this.customParams.get( ESCOLARIDADE );
		final String[] resumo = this.customParams.get( RESUMO );
        final String[] remuneracao = this.customParams.get( REMUNERACAO );
		final String[] deficiencia = this.customParams.get( DEFICIENCIA );

        if ( empresa != null )
		{
			predicate = cb.and( predicate, root.get( EMPRESA ).get( "id" ).in( empresa[0] ) );
		}

        if ( estado != null )
		{
			predicate = cb.and( predicate, root.get( EMPRESA ).get("cidade").get( ESTADO ).get( "id" ).in( estado[0] ) );
		}

        if ( area != null )
		{
			predicate = cb.and( predicate, root.get( AREA ).get( "id" ).in( area[0] ) );
		}

        if ( escolaridade != null )
		{
			predicate = cb.and( predicate, root.get( ESCOLARIDADE ).get( "id" ).in( escolaridade[0] ) );
		}

        if ( resumo != null )
		{
			predicate = cb.and( predicate, cb.like(root.get( "resumo" ), resumo[0] ) );
		}

        if ( remuneracao != null )
		{
			predicate = cb.and( predicate, cb.greaterThanOrEqualTo(root.get( REMUNERACAO ), remuneracao[0] ) );
		}

        if ( deficiencia != null )
		{	
			Join<Vaga, Deficiencia> defJoin = root.join( "deficiencias" );
			predicate = cb.and( predicate, cb.equal( defJoin.get("id"), deficiencia[0] ) );
		}

		return predicate;
	}

	private Map<String, String[]> customParams;
}