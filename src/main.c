#include "../inc/slash.h"

char	*get_word(char *str, int *len, t_cmd *act)
{
	int		in_quote;
	int		start;
	char	*word;

	in_quote = 0;
	start = *len;
	word = NULL;
	while (str[*len]
		&& (!is_pipe(str[*len]) || in_quote)
		&& (!isspace(str[*len]) || in_quote))
	{
		in_quote = quote_check(str[*len], in_quote);
		*len = *len + 1;
	}
	if (in_quote != 0)
		return (NULL);
	if (*len != start)
		word = ft_substr(str, start, *len - start);
	word = delete_quotes(word);
	return (word);
}

void	take_command(char *line, int *i, t_cmd *act)
{
	act->name = get_word(line, i, act);
	if (act->name == NULL)
		return ;
	while (isspace(line[*i]))
		*i = *i + 1;
	act->args = calloc(calcul_arg(line, *i) + 3, sizeof(char *));
	act->args[0] = act->name;
	act->argc = 1;
	while (line[*i] && !is_pipe(line[*i]))
	{
		while (isspace(line[*i]))
			*i = *i + 1;
		if (is_pipe(line[*i]) || !line[*i])
			break ;
		act->args[act->argc] = get_word(line, i, act);
		if (act->args[act->argc] != NULL)
			act->argc = act->argc + 1;
		while (isspace(line[*i]))
			*i = *i + 1;
	}
	while (isspace(line[*i]))
		*i = *i + 1;
}

t_cmd	*get_line(char *line, char ***env)
{
	t_cmd	*act;
	t_cmd	*new;
	int		i;

	i = 0;
	act = create_cmd_malloc(env, g_return);
	if (!check_error(line, act))
		return (NULL);
	while (line[i])
	{
		while (isspace(line[i]))
			i++;
		take_command(line, &i, act);
		if (act->fail == 1)
			return (act);
		if (line[i])
		{
			new = create_cmd_malloc(env, g_return);
			new->prev = act;
			act->next = new;
			act = new;
		}
	}
	return (get_first_cmd(act));
}

int g_return = 0;

void main(int argc, char **argv,char **env) {
    char	*line;
	t_cmd	*tokens;

	line = reading(line, &env);
	while (line != NULL)
	{
		tokens = get_line(line, &env);
		free(line);
		if (tokens && !tokens->fail)
			g_return = start_chain(tokens);
		if (tokens && tokens->fail)
			g_return = 2; //syntax error code
		if (tokens)
			free_all_cmd(tokens);
		line = reading(line, &env);
	}
	rl_clear_history();
	ft_putstr("exit\n");
	return (g_return);
}