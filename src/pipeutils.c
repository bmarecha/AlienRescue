
int	is_pipe(char c)
{
	if (c == '|' || c == ';')
		return (1);
	return (0);
}