#include "../inc/slash.h"

char	*delete_quotes(char *word)
{
	int		i;
	int		j;
	int		in_quote;
	char	*new;

	if (!word)
		return (NULL);
	i = 0;
	j = 0;
	in_quote = 0;
	new = ft_calloc(ft_strlen(word) + 1, sizeof(char));
	while (word[i])
	{
		in_quote = quote_check(word[i], in_quote);
		if ((word[i] != '\'' || in_quote == 2)
			&& (word[i] != '\"' || in_quote == 1) && word[i])
		{
			new[j] = word[i];
			j++;
		}
		i++;
	}
	free(word);
	return (new);
}

int	quote_check(char c, int in_quote)
{
	if (c == '\'')
	{
		if (in_quote == 1)
			return (0);
		if (in_quote == 0)
			return (1);
	}
	if (c == '\"')
	{
		if (in_quote == 2)
			return (0);
		if (in_quote == 0)
			return (2);
	}
	return (in_quote);
}

int	quote_in_word(char *str, int i)
{
	while (str[i] && str[i] != ' ' && str[i] != '|' && !is_redirect(str[i]))
	{
		if (str[i] == '\'' || str[i] == '\"')
			return (1);
		i++;
	}
	return (0);
}