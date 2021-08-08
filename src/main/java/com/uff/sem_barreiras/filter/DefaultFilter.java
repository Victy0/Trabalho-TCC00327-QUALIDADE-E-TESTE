package com.uff.sem_barreiras.filter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class DefaultFilter<T> implements Specification<T>{

    public DefaultFilter( final Map<String, String[]> params )
	{
		this.params = params;
	}
	
	private final Map<String, String[]> params;

	protected Map<String, String[]> getParams()
	{
		return this.params;
	}

    // construir clausulas dinamicamente
	public Predicate buildClause(
		Predicate clauseForPossibleValues,
		final String currentFilterParam,
		final CriteriaBuilder cb,
		final Root<T> root,
		final String paramValue )
	{
		final Path<Object> field = this.getCompoundField( root, currentFilterParam );

		Predicate equal = paramValue.equals("null") ? cb.isNull(field) : cb.equal( field, this.parseValue( paramValue, field ) );
		
		if ( clauseForPossibleValues == null )
		{
			clauseForPossibleValues = equal;
		}
		else
		{
			clauseForPossibleValues = cb.or( equal, clauseForPossibleValues );
		}

		return clauseForPossibleValues;
	}

	protected <C> Path<C> getCompoundField( final Root<T> root, final String currentFilterParam )
	{
		String[] attributeChain = currentFilterParam.split( "\\." );

		Path<C> field = root.<C> get( attributeChain[0] );

		attributeChain = Arrays.copyOfRange( attributeChain, 1, attributeChain.length );

		for ( final String currentAttribute : attributeChain )
		{
			field = field.get( currentAttribute );
		}

		return field;
	}

    // parse do nome do campo para o nome do atributo no Java
	private Object parseValue( final String paramValueStr, final Path<Object> field )
	{
		final Class< ? extends Object> javaType = field.getJavaType();

		if ( javaType.equals( Boolean.class ) )
		{
			if ( !"true".equals( paramValueStr ) && !"false".equals( paramValueStr ) )
			{
				throw new ClassCastException();
			}

			return "true".equals( paramValueStr );

		}
		else if ( javaType.equals( Integer.class ) )
		{
			return Integer.valueOf( paramValueStr );
		}
		else if ( javaType.equals( Double.class ) )
		{
			return Double.valueOf( paramValueStr );
		}

		return javaType.cast( paramValueStr );
	}

	@Override
	public Predicate toPredicate( final Root<T> root, final CriteriaQuery< ? > query, final CriteriaBuilder cb )
	{
		final Map<String, String[]> params = this.getParams();
		final Set<String> keySet = params.keySet();

		Predicate conditionals = cb.conjunction();

		// itera entre os campos para filtrar

		for ( final String currentFilterParam : keySet )
		{

			if ( !"null".equals( currentFilterParam ) )
			{
				final String[] paramValues = params.get( currentFilterParam );
				Predicate clauseForPossibleValues = null;
				final Path<Object> field = getCompoundField( root, currentFilterParam );

				if ( paramValues.length == 0 )
				{
					clauseForPossibleValues = cb.disjunction();
				}
				if ( ( paramValues.length == 1 ) )
				{
					if ( paramValues[0].equals( "" ) )
					{
						clauseForPossibleValues = cb.isNotNull( field );
					}
					else if ( paramValues[0].equals( "NULL" ) )
					{
						clauseForPossibleValues = cb.isNull( field );
					}
					else
					{
						for ( final String paramValue : paramValues )
						{
							clauseForPossibleValues = this.buildClause( clauseForPossibleValues, currentFilterParam, cb, root, paramValue );
						}
					}
				}
				else
				{
					final List<Object> paramsList = Arrays
						.asList( paramValues )
						.stream()
						.map( value -> this.parseValue( value, field ) )
						.collect( Collectors.toList() );

					clauseForPossibleValues = field.in( paramsList );
				}

				conditionals = cb.and( conditionals, clauseForPossibleValues );
			}
		}

		return conditionals;
	}
    
}
