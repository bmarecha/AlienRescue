SRCS	=	src/Launcher.java \
			src/Environnement.java \
			src/Ecran.java \
			src/Case.java \
			src/AffichageNiv.java \
			src/AffichageSelect.java \
			src/Plateau.java \
			src/Niveau.java

NAME = AlienRescue

OBJS = ${SRCS:.java=.class}

$(OBJS)	: ${SRCS}
	javac ${SRCS}

$(NAME)	: ${OBJS}
	java -Dfile.encoding=UTF-8 -classpath ./src Launcher
	

all		:	${NAME}

clean	:
	rm -f src/*.class* 

re		: clean all 

.PHONY	: all clean fclean re
