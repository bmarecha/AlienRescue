#include "../inc/slash.h"

char	*reading(char *line, char ***env)
{
	char	*prompt = "$>";

//	prompt = get_prompt(env); TODO
//  prompt = add_color(); TODO
	line = readline(prompt);
	add_history(line);
	free(prompt);
	return (line);
}