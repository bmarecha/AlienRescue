#include "../inc/slash.h"



int	execute_cmd(t_cmd *cmd)
{
	if (!cmd->name)
		return (0);
	if (!ft_strcmp(cmd->name, "pwd"))
	    return (built_pwd(cmd)); //TODO
    if (!ft_strcmp(cmd->name, "cd"))
	    return (built_cd(cmd)); // TODO
    if (!ft_strcmp(cmd->name, "exit"))
	    return (built_exit(cmd)); //TODO
	return (127); // Command not found exit code
}

int	start_chain(t_cmd *cmd)
{
	while (cmd->next) // if there is multiple commands on the command line
	{
		execute_cmd(cmd);
		cmd = cmd->next;
	}
	return (execute_cmd(cmd)); // return last cmd status
}