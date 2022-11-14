#ifndef SLASH_H
# define SLASH_H

#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include <errno.h>

typedef struct s_cmd
{
	struct s_cmd	*next;
	struct s_cmd	*prev;

	char			*name;
	char			**args;
    char            ***env;
	int				exit;
	int				argc;
	int				fail;
}					t_cmd;

char	*reading(char *line, char ***env);
char	*delete_quotes(char *word);
int		quote_check(char c, int in_quote);
int		quote_in_word(char *str, int i);
t_cmd	*get_first_cmd(t_cmd *cmd);
t_cmd	*get_last_cmd(t_cmd *cmd);
t_cmd	*create_cmd_malloc(char ***env, int exit);
int		is_pipe(char c);

#endif