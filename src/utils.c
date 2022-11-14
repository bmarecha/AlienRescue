#include "../inc/slash.h"

t_cmd	*get_first_cmd(t_cmd *cmd)
{
	while (cmd->prev)
		cmd = cmd->prev;
	return (cmd);
}

t_cmd	*get_last_cmd(t_cmd *cmd)
{
	while (cmd->next)
		cmd = cmd->next;
	return (cmd);
}

t_cmd	*create_cmd_malloc(char ***env, int exit)
{
	t_cmd	*cmd;

	cmd = malloc(sizeof(t_cmd));
    bzero(cmd, sizeof(t_cmd));
	cmd->env = env;
	cmd->exit = exit;
	return (cmd);
}

int	calcul_arg(char *line, int i)
{
	int		arg;
	int		in_quote;
	int		in_word;

	arg = 0;
	in_quote = 0;
	in_word = 0;
	while (line[i] && (!is_pipe(line[i]) || in_quote != 0))
	{
		in_quote = quote_check(line[i], in_quote);
		if (!isspace(line[i]) && !in_word)
			in_word = 1;
		if (isspace(line[i]) && in_word && !in_quote)
		{
			in_word = 0;
			arg++;
		}
		i++;
	}
	if (in_word == 1)
		arg++;
	return (arg);
}